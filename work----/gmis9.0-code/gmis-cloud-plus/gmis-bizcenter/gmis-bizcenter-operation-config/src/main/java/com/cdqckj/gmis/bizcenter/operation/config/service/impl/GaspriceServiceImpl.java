package com.cdqckj.gmis.bizcenter.operation.config.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.operation.config.service.GaspriceService;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import com.cdqckj.gmis.operateparam.PenaltyBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.dto.*;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GaspriceServiceImpl extends SuperCenterServiceImpl implements GaspriceService {
    @Autowired
    public PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    public RedisService redisService;
    @Autowired
    public PenaltyBizApi penaltyBizApi;
    @Autowired
    public GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    public UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    public GasMeterBizApi gasMeterBizApi;
    @Autowired
    I18nUtil i18nUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<PriceScheme> saveLadderPriceScheme(PriceSchemeSaveDTO priceSchemeSaveDTO) {
        R<PriceScheme> res = R.fail(getLangMessage(MessageConstants.PRICE_GAS_ERROR));
        if(priceSchemeSaveDTO.getStartTime().isBefore(LocalDate.now())){ return res;}
        //判断阶梯气量是否正确
        R<PriceSchemeSaveDTO> priceScheme = setLadderPriceScheme(priceSchemeSaveDTO);
        if(priceScheme.getCode()!=0){return res;}
        priceSchemeSaveDTO = priceScheme.getData();
        String cycleDateStr = getCycleDateStr(priceSchemeSaveDTO);
        LocalDate cycleDate = LocalDate.parse(cycleDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        priceSchemeSaveDTO.setCycleEnableDate(cycleDate);
        //如果生效日期为当前将前一条数据置为无效
        return isActivatePrice(priceSchemeSaveDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<PriceScheme> saveFixedPriceScheme(PriceSchemeSaveDTO priceSchemeSaveDTO) {
        R<PriceScheme> res = R.fail(getLangMessage(MessageConstants.PRICE_GAS_ERROR));
        if(priceSchemeSaveDTO.getFixedPrice()==null){return res;}
        //如果生效日期为当前将前一条数据置为无效
        return isActivatePrice(priceSchemeSaveDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<PriceScheme> saveHeatingPriceScheme(List<PriceSchemeSaveDTO> list) {
        R<PriceScheme> res = R.fail(getLangMessage(MessageConstants.PRICE_GAS_ERROR));
        R<PriceScheme> schemeR = null;
        for(int i=0;i<list.size();i++){
            PriceSchemeSaveDTO e = list.get(i);
            //判断阶梯气量是否正确保存方案
            R<PriceSchemeSaveDTO> priceScheme = setLadderPriceScheme(e);
            if(priceScheme.getCode()!=0){ return res;}
            String cycleDateStr = getCycleDateStr(e);
            LocalDate cycleDate = LocalDate.parse(cycleDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            e.setCycleEnableDate(cycleDate);
            schemeR = isActivatePrice(e);
        }
        if(schemeR!=null){
            return schemeR;
        }else{
            return res;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Penalty> savePenalty(PenaltySaveDTO penaltySaveDTO) {
        if(penaltySaveDTO.getCompoundInterest()==null){
            penaltySaveDTO.setCompoundInterest(0);
        }
        List<Penalty> useGasTypes = penaltyBizApi.query(new Penalty()).getData();
        List<String> usetypesIds=new ArrayList<>();
        for (Penalty penalty : useGasTypes) {
            JSONArray array = JSON.parseArray(penalty.getUseGasTypeId());
            for (Object o : array) {
                usetypesIds.add(o.toString());
            }
        }
        List<String> ids = JSON.parseArray(penaltySaveDTO.getUseGasTypeId(),String.class);
        List<String> saveIds = new ArrayList<>();
        for (String id : ids) {
            if(usetypesIds.contains(id)){
                //获取重复的用气类型
                R<UseGasType> useGasTypeR = useGasTypeBizApi.get(Long.valueOf(id));
                String nanme = "";
                if(null!=useGasTypeR.getData().getUseGasTypeName()){
                    nanme = useGasTypeR.getData().getUseGasTypeName();
                }

                return  R.fail(getLangMessage(MessageConstants.USE_GAS_TYPE_REPEAT)+":"+nanme);
            }
            saveIds.add(id);
        }
        String usegasTypeid = JSON.toJSONString(saveIds);
        penaltySaveDTO.setUseGasTypeId(usegasTypeid);
        return penaltyBizApi.save(penaltySaveDTO);
    }

    @Override
    public R<Penalty> updatePenalty(PenaltyUpdateDTO penaltyUpdateDTO) {
   /*     R<Penalty> res = R.fail(getLangMessage(MessageConstants.USE_GAS_TYPE_REPEAT));
        List<Penalty> useGasTypes = penaltyBizApi.query(new Penalty()).getData();
        List<String> usetypesIds=new ArrayList<>();
        for (Penalty penalty : useGasTypes) {
            JSONArray array = JSON.parseArray(penalty.getUseGasTypeId());
            for (Object o : array) {
                usetypesIds.add(o.toString());
            }
        }
        JSONArray ids = JSON.parseArray(penaltyUpdateDTO.getUseGasTypeId());
        List<Object> saveIds = new ArrayList<>();
        Penalty penaltyR = penaltyBizApi.queryById(penaltyUpdateDTO.getId()).getData();
        for (Object id : ids) {
            if(penaltyR ==null ||  usetypesIds.contains(id)){
                return res;
            }
            saveIds.add(id);
        }
        penaltyUpdateDTO.setUseGasTypeId(saveIds.toString());*/
        R<String> check = penaltyBizApi.check(penaltyUpdateDTO);
        if(StringUtils.isNotEmpty(check.getData())){
            //获取重复的用气类型
            R<UseGasType> useGasTypeR = useGasTypeBizApi.get(Long.valueOf(check.getData()));
            String nanme = "";
            if(null!=useGasTypeR.getData().getUseGasTypeName()){
                nanme = useGasTypeR.getData().getUseGasTypeName();
            }

            return  R.fail(getLangMessage(MessageConstants.USE_GAS_TYPE_REPEAT)+":"+nanme);
        }

        return penaltyBizApi.update(penaltyUpdateDTO);
    }

    @Override
    public R<List<PriceScheme>> getAllPriceScheme() {
        return priceSchemeBizApi.queryAllPriceScheme();
    }

    @Override
    public R<UseGasType> saveUseGasType(UseGasTypeSaveDTO useGasTypeSaveDTO) {
        if (useGasTypeSaveDTO.getAlarmMoney()!=null && useGasTypeSaveDTO.getAlarmMoneyTwo()!=null
                && BigDecimalUtils.greaterThan(useGasTypeSaveDTO.getAlarmMoney(),useGasTypeSaveDTO.getAlarmMoneyTwo())){
            return R.fail("1级报警金额不能大于2级报警金额");
        }
        //add by hc 用气类型名重复性校验
//        UseGasType queryData = new UseGasType();
//        queryData.setUseGasTypeName(useGasTypeSaveDTO.getUseGasTypeName());
//        queryData.setDataStatus(DataStatusEnum.NORMAL.getValue());
//        queryData.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
//        R<List<UseGasType>> listR = useGasTypeBizApi.query(queryData);
//        if(CollectionUtils.isNotEmpty(listR.getData())){
//            return R.fail(getLangMessage(MessageConstants.USE_GAS_TYPE_REPEAT));
//        }
        Boolean check = useGasTypeBizApi.checkUseGasType(null, useGasTypeSaveDTO.getUseGasTypeName()).getData();
        if (check){
            return R.fail(getLangMessage(MessageConstants.USE_GAS_TYPE_REPEAT));
        }
        UseGasType useGasType = useGasTypeBizApi.queryRentUseGasType();
        if(useGasType==null || useGasType.getUseTypeNum()==null){
            useGasTypeSaveDTO.setUseTypeNum(1);
        } else{
            useGasTypeSaveDTO.setUseTypeNum((useGasType.getUseTypeNum()+1));
        }
        return useGasTypeBizApi.save(useGasTypeSaveDTO);
    }

    /**
     * 通过表具Code查询气价方案详情
     *
     * @param gasMeterCode 表具code
     * @return 气价方案详情
     */
    @Override
    public R<UseGasTypeVO> queryByGasMeterCode(String gasMeterCode) {
        R<GasMeter> gasMeterByCode = gasMeterBizApi.findGasMeterByCode(gasMeterCode);
        if (Objects.isNull(gasMeterByCode.getData())) {
            return R.fail(i18nUtil.getMessage(MessageConstants.G_METER_UNCOMPLETEINFO), "表具信息不完整");
        }
        if (Objects.isNull(gasMeterByCode.getData().getUseGasTypeId())) {
            return R.fail("表具还未开户， 未配置气价方案！");
        }
        return useGasTypeBizApi.queryUseGasTypeAndPrice(gasMeterByCode.getData().getUseGasTypeId());
    }

    /**
     * 判断阶梯价格是否正确
     * @param priceSchemeSaveDTO 阶梯价格方案
     * @return
     */
    private R<PriceSchemeSaveDTO> setLadderPriceScheme(PriceSchemeSaveDTO priceSchemeSaveDTO){
        BigDecimal maxPrice = new BigDecimal(65535);
        //测试方便，暂时写在这
        R<PriceSchemeSaveDTO> res = R.fail(getLangMessage(MessageConstants.PRICE_GAS_ERROR));
        //判断阶梯气量是否正确
        if(priceSchemeSaveDTO.getGas1()==null){
            return res;
        }
        if(priceSchemeSaveDTO.getGas7()!=null){
            if(priceSchemeSaveDTO.getGas6()==null||priceSchemeSaveDTO.getGas5()==null||priceSchemeSaveDTO.getGas4()==null
                    ||priceSchemeSaveDTO.getGas3()==null||priceSchemeSaveDTO.getGas2()==null)
            {return res;}
            if(priceSchemeSaveDTO.getGas7().compareTo(priceSchemeSaveDTO.getGas6())<0||priceSchemeSaveDTO.getGas6().compareTo(priceSchemeSaveDTO.getGas5())<0
                    ||priceSchemeSaveDTO.getGas5().compareTo(priceSchemeSaveDTO.getGas4())<0||priceSchemeSaveDTO.getGas4().compareTo(priceSchemeSaveDTO.getGas3())<0
                    ||priceSchemeSaveDTO.getGas3().compareTo(priceSchemeSaveDTO.getGas2())<0||priceSchemeSaveDTO.getGas2().compareTo(priceSchemeSaveDTO.getGas1())<0)
            {return res;}
            if(priceSchemeSaveDTO.getGas7().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas6().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas5().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas4().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas3().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas2().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas1().compareTo(maxPrice)>0)
            {return res;}
            priceSchemeSaveDTO.setGas7(maxPrice);
        }else if(priceSchemeSaveDTO.getGas6()!=null){
            if(priceSchemeSaveDTO.getGas5()==null||priceSchemeSaveDTO.getGas4()==null
                    ||priceSchemeSaveDTO.getGas3()==null||priceSchemeSaveDTO.getGas2()==null)
            {return res;}
            if(priceSchemeSaveDTO.getGas6().compareTo(priceSchemeSaveDTO.getGas5())<0
                    ||priceSchemeSaveDTO.getGas5().compareTo(priceSchemeSaveDTO.getGas4())<0||priceSchemeSaveDTO.getGas4().compareTo(priceSchemeSaveDTO.getGas3())<0
                    ||priceSchemeSaveDTO.getGas3().compareTo(priceSchemeSaveDTO.getGas2())<0||priceSchemeSaveDTO.getGas2().compareTo(priceSchemeSaveDTO.getGas1())<0)
            {return res;}
            if(priceSchemeSaveDTO.getGas6().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas5().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas4().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas3().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas2().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas1().compareTo(maxPrice)>0)
            {return res;}
            priceSchemeSaveDTO.setGas6(maxPrice);
        }else if(priceSchemeSaveDTO.getGas5()!=null){
            if(priceSchemeSaveDTO.getGas4()==null||priceSchemeSaveDTO.getGas3()==null
                    ||priceSchemeSaveDTO.getGas2()==null)
            {return res;}
            if(priceSchemeSaveDTO.getGas5().compareTo(priceSchemeSaveDTO.getGas4())<0||priceSchemeSaveDTO.getGas4().compareTo(priceSchemeSaveDTO.getGas3())<0
                    ||priceSchemeSaveDTO.getGas3().compareTo(priceSchemeSaveDTO.getGas2())<0||priceSchemeSaveDTO.getGas2().compareTo(priceSchemeSaveDTO.getGas1())<0)
            {return res;}
            if(priceSchemeSaveDTO.getGas5().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas4().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas3().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas2().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas1().compareTo(maxPrice)>0)
            {return res;}
            priceSchemeSaveDTO.setGas5(maxPrice);
        }else if(priceSchemeSaveDTO.getGas4()!=null){
            if(priceSchemeSaveDTO.getGas3()==null||priceSchemeSaveDTO.getGas2()==null)
            {return res;}
            if(priceSchemeSaveDTO.getGas4().compareTo(priceSchemeSaveDTO.getGas3())<0||priceSchemeSaveDTO.getGas3().compareTo(priceSchemeSaveDTO.getGas2())<0
                    ||priceSchemeSaveDTO.getGas2().compareTo(priceSchemeSaveDTO.getGas1())<0)
            {return res;}
            if(priceSchemeSaveDTO.getGas4().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas3().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas2().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas1().compareTo(maxPrice)>0)
            {return res;}
            priceSchemeSaveDTO.setGas4(maxPrice);
        }else if(priceSchemeSaveDTO.getGas3()!=null){
            if(priceSchemeSaveDTO.getGas2()==null){return res.setMsg(getLangMessage(MessageConstants.PRICE_GAS_ERROR));}
            if(priceSchemeSaveDTO.getGas3().compareTo(priceSchemeSaveDTO.getGas2())<0
                    ||priceSchemeSaveDTO.getGas2().compareTo(priceSchemeSaveDTO.getGas1())<0)
            {return res;}
            if(priceSchemeSaveDTO.getGas3().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas2().compareTo(maxPrice)>0
                    ||priceSchemeSaveDTO.getGas1().compareTo(maxPrice)>0)
            {return res;}
            priceSchemeSaveDTO.setGas3(maxPrice);
        }else if(priceSchemeSaveDTO.getGas2()!=null){
            if(priceSchemeSaveDTO.getGas2().compareTo(priceSchemeSaveDTO.getGas1())<0)
            {return res;}
            if(priceSchemeSaveDTO.getGas2().compareTo(maxPrice)>0||priceSchemeSaveDTO.getGas1().compareTo(maxPrice)>0)
            {return res;}
            priceSchemeSaveDTO.setGas2(maxPrice);
        }else{
            priceSchemeSaveDTO.setGas1(maxPrice);
        }
        return R.success(priceSchemeSaveDTO);
    }

    /**
     * 判断新增价格方案是否当前生效
     * @param e 价格方案
     * @return
     */
    private R<PriceScheme> isActivatePrice(PriceSchemeSaveDTO e){
        PriceScheme priceSchemeEntity = null;
        //判断是否是采暖方案
        if(e.getIsHeating()!=null&&e.getIsHeating()==1){
            priceSchemeEntity = priceSchemeBizApi.queryRecentHeatingRecord(e.getUseGasTypeId());
        }else{
            priceSchemeEntity = priceSchemeBizApi.queryRecentRecord(e.getUseGasTypeId());
        }
        if(e.getStartTime().isEqual(LocalDate.now())){
            //设置当前价格生效
            e.setDataStatus(1);
            if(priceSchemeEntity!=null){
                priceSchemeEntity.setDataStatus(0);
                priceSchemeEntity.setEndTime(LocalDate.now());
                PriceSchemeUpdateDTO updateDTO = BeanUtil.toBean(priceSchemeEntity,PriceSchemeUpdateDTO.class);
                priceSchemeBizApi.update(updateDTO);
            }
            //新增价格id
            R<PriceScheme> res = priceSchemeBizApi.save(e);
            PriceScheme priceScheme = res.getData();
            //设置价格号
            int priceNum = (int) ((priceScheme.getId()+e.getUseGasTypeId())%127);
            if(priceSchemeEntity!=null&&priceNum==priceSchemeEntity.getPriceNum()){
                priceNum += 1;
            }
            priceScheme.setPriceNum(priceNum);
            PriceSchemeUpdateDTO saveDTO = BeanPlusUtil.toBean(priceScheme,PriceSchemeUpdateDTO.class);
            priceSchemeBizApi.update(saveDTO);
            if(e.getPriceChangeIsClear()==null){e.setPriceChangeIsClear(0);}
            return res;
        }else{
            e.setDataStatus(0);
        }
//        if(priceSchemeEntity!=null&&priceSchemeEntity.getPriceNum()<127){
//            e.setPriceNum((priceSchemeEntity.getPriceNum()+1));
//        }else{
//            e.setPriceNum(1);
//        }
        R<PriceScheme> res = priceSchemeBizApi.save(e);
        PriceScheme priceScheme = res.getData();
        int priceNum = (int) ((priceScheme.getId()+e.getUseGasTypeId())%127);
        if(priceSchemeEntity!=null&&priceNum==priceSchemeEntity.getPriceNum()){
            priceNum += 1;
        }
        priceScheme.setPriceNum(priceNum);
        PriceSchemeUpdateDTO saveDTO = BeanPlusUtil.toBean(priceScheme,PriceSchemeUpdateDTO.class);
        priceSchemeBizApi.update(saveDTO);
        return res;
    }

    public String getCycleDateStr(PriceSchemeSaveDTO e){
        String monthStr = Integer.toString(e.getStartMonth());
        String dayStr = Integer.toString(e.getSettlementDay());
        if(monthStr.length()==1){monthStr = "0"+monthStr;}
        if(dayStr.length()==1){dayStr = "0"+dayStr;}
        return  LocalDate.now().getYear()+"-"+monthStr+"-"+dayStr;
    }
}
