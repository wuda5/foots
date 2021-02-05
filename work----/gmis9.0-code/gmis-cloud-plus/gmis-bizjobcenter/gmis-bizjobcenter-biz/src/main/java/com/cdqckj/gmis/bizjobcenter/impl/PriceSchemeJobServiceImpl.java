package com.cdqckj.gmis.bizjobcenter.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.bizjobcenter.PriceSchemeJobService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 价格方案任务调度业务类（以每天00:00:00触发定时任务判断当天是否有生效的方案、调价是否清零、是否周期清零）
 * @author Mr.Yang
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PriceSchemeJobServiceImpl  implements PriceSchemeJobService {

    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;

    /**
     * 是否有生效的价格方案
     */
    @Override
    public void isActivatePriceScheme() {
        System.out.println(BaseContextHandler.getTenant());
        //查询所有价格方案
        List<PriceScheme> priceSchemeList = priceSchemeBizApi.queryAllPriceScheme().getData();
        //根据用气类型id分组价格方案
        if(priceSchemeList==null||priceSchemeList.size()==0){
             return ;
        }
        Map<Long, List<PriceScheme>> listMap = priceSchemeList.stream().
                collect(Collectors.groupingBy(PriceScheme::getUseGasTypeId));
        if(listMap!=null&&listMap.size()>0){
            listMap.forEach((k,v)->{
                if(v==null||v.size()==0){return;}
                v.stream().forEach(e->{
                    //判断是否周期清零
                    if(e.getCycle()!=null&&isClear(e.getCycleEnableDate(),LocalDate.now(),e.getCycle())){
                        if(e.getIsHeating()==1){
                            cycleClear(v,1,e);
                        }else{
                            cycleClear(v,0,e);
                        }
                    }
                    //是否有新方案生效及调价是否清零
                    if(e.getStartTime().isEqual(LocalDate.now())&&e.getDataStatus()==0){
                        e.setDataStatus(1);
                        e.setStartTime(LocalDate.now());
                        if(e.getIsHeating()==1){
                            isActivatePrice(v,1,e);
                        }else{
                            isActivatePrice(v,0,e);
                        }
                    }
                });
            });
        }
    }

    /**
     * 判断并更新价格方案
     * @param priceSchemeList 价格方案列表
     * @param status 是否采暖：1-采暖 0-非采暖
     * @param e 将生效价格方案
     */
    private void isActivatePrice(List<PriceScheme> priceSchemeList,int status,PriceScheme e){
        List<PriceScheme> activateList= priceSchemeList.stream()
                .filter(price -> price.getDataStatus()==1&&price.getIsHeating()==status)
                .collect(Collectors.toList());
        //判断新增方案之前是否有方案生效
        if(activateList.size()>0){
            PriceScheme price = activateList.get(0);
            price.setDataStatus(0);
            price.setEndTime(LocalDate.now());
            priceSchemeBizApi.updateByPriceId(price);
            //判断当前价格方案是否存在调价清零
            if(e.getPriceChangeIsClear()==1&&e.getCycle()!=null){
                MeterInfoVO meterInfoVO = new MeterInfoVO();
                meterInfoVO.setPriceSchemeNewId(e.getId());
                meterInfoVO.setPriceSchemeOldId(price.getId());
                gasMeterInfoBizApi.updateByPriceId(meterInfoVO);
            }
        }
        priceSchemeBizApi.updateByPriceId(e);
    }

    /**
     * 周期清零
     * @param priceSchemeList 价格方案列表
     * @param status 是否采暖：1-采暖 0-非采暖
     * @param e 将生效的价格方案
     * @return
     */
    private void cycleClear(List<PriceScheme> priceSchemeList,int status,PriceScheme e){
        List<PriceScheme> activateList= priceSchemeList.stream()
                //.filter(price -> price.getDataStatus()==1)
                .filter(price->price.getIsHeating()==status)
                .collect(Collectors.toList());
        if(activateList.size()>0){
            PriceScheme price = activateList.get(0);
            MeterInfoVO meterInfoVO = new MeterInfoVO();
            meterInfoVO.setPriceSchemeNewId(e.getId());
            meterInfoVO.setPriceSchemeOldId(price.getId());
            gasMeterInfoBizApi.updateCycleByPriceId(meterInfoVO);
        }
    }

    /**
     * 周期累积量是否清零
     * @param begin 周期开始时间
     * @param end 冻结时间
     * @param cycle 计费周期-单位月
     * @return
     */
    private Boolean isClear(LocalDate begin,LocalDate end,int cycle){
        int month = (end.getYear() - begin.getYear())*12 + (end.getMonthValue()-begin.getMonthValue());
        return (month%cycle==0)&&(begin.getDayOfMonth()==end.getDayOfMonth());
    }
}
