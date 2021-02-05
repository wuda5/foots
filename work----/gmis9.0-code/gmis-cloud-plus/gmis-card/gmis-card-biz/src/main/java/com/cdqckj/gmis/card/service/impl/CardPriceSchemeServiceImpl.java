package com.cdqckj.gmis.card.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.card.dao.CardPriceSchemeMapper;
import com.cdqckj.gmis.card.entity.CardPriceScheme;
import com.cdqckj.gmis.card.service.CardPriceSchemeService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 业务实现类
 * 卡表价格方案记录
 * </p>
 *
 * @author tp
 * @date 2021-01-12
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CardPriceSchemeServiceImpl extends SuperServiceImpl<CardPriceSchemeMapper, CardPriceScheme> implements CardPriceSchemeService {

    @Autowired
    CalculateService calculateService;

    @Autowired
    PriceSchemeService priceSchemeService;


    public CardPriceScheme getByGasMeterCode(String gasMeterCode){
        return getOne(new LbqWrapper<CardPriceScheme>().eq(CardPriceScheme::getGasMeterCode,gasMeterCode));
    }

    public void backGas(String gasMeterCode){
        CardPriceScheme priceMapping = getByGasMeterCode(gasMeterCode);
        if(priceMapping!=null) {
            if (!priceMapping.getWriteCardPriceId().equals(0L)) {
                CardPriceScheme mapping = new CardPriceScheme();
                mapping.setWriteCardPriceId(0L);
                mapping.setId(priceMapping.getId());
                updateById(mapping);
            } else {
                if (!priceMapping.getPreWriteCardPriceId().equals(0L)) {
                    CardPriceScheme mapping = new CardPriceScheme();
                    mapping.setPreWriteCardPriceId(0L);
                    mapping.setId(priceMapping.getId());
                    updateById(mapping);
                }
            }
        }
    }
    public void readCardCallBack(String gasMeterCode){
        CardPriceScheme priceMapping = getByGasMeterCode(gasMeterCode);
        if(priceMapping!=null) {
            if (!priceMapping.getWriteCardPriceId().equals(0L)) {
                if (!priceMapping.getWriteCardPriceId().equals(priceMapping.getIntoMeterPriceId())) {
                    CardPriceScheme mapping = new CardPriceScheme();
                    mapping.setIntoMeterPriceId(priceMapping.getWriteCardPriceId());
                    mapping.setId(priceMapping.getId());
                    log.info("写卡气价方案上表，系统维护表上当前气价方案");
                    updateById(mapping);
                } else {
                    log.info("方案未发生变更，正常读卡");
                }
            } else {
                if (!priceMapping.getPreWriteCardPriceId().equals(0L)) {
                    CardPriceScheme mapping = new CardPriceScheme();
                    mapping.setPreIntoMeterPriceId(priceMapping.getPreWriteCardPriceId());
                    //查看上次写入预调价是否生效,如果已生效，更新表内当前方案和系统记录一致。
                    PriceScheme scheme = priceSchemeService.getById(priceMapping.getPreWriteCardPriceId());
                    if (scheme.getStartTime().isBefore(LocalDate.now()) || scheme.getStartTime().isEqual(LocalDate.now())) {
                        log.info("更新预调价方案为当前表上气价方案");
                        mapping.setIntoMeterPriceId(mapping.getPreIntoMeterPriceId());
                    }
                    mapping.setId(priceMapping.getId());
                    updateById(mapping);
                } else {
                    log.info("是否进行了退气，没有写入当前方案，也没有写入预调价方案");
                }
            }
        }
    }


    public Map<String,Object> getScheme(GasMeter meter, GasMeterVersion gasMeterVersion, UseGasType useGasType){
        String gasMeterCode=meter.getGasCode();
        Map<String,Object> map=new HashMap<>();
        PriceScheme scheme=calculateService.queryPriceScheme(meter.getGasCode(), LocalDate.now()).getData();
        CardPriceScheme priceMapping = getByGasMeterCode(gasMeterCode);

        //只有0-127,直接返回当前方案
        if (priceMapping == null || priceMapping.getIntoMeterPriceId()==null) {
            map.put("scheme",scheme);
            map.put("isImmediatelyAdj",true);
            //都没写过方案，此时不能写预调价，卡表支持上表一个方案，要么正常方案，要么预调价方案
            CardPriceScheme mapping=new CardPriceScheme();
            mapping.setWriteCardPriceId(scheme.getId());
            mapping.setPreWriteCardPriceId(0L);
            if(priceMapping==null){
                mapping.setGasMeterCode(meter.getGasCode());
                save(mapping);
            }else{
                mapping.setId(priceMapping.getId());
                updateById(mapping);
            }
            return map;
        }else {
            //不管写入是当前方案，还是预调价方案，在读卡回调的时候如果预调价方案生效及时更新到 上表当前方案中，确保表上当前方案和系统当前方案一致。
            //故：写入时，是什么方案就是什么方案，不做其他处理
            Long currId=scheme.getId();
            //上次当前方案ID
            Long intoMeterId = priceMapping.getIntoMeterPriceId();
            if(currId.equals(intoMeterId)){
                //当前方案和表内方案一致，直接找是否有预调价。
                return getPreScheme(map,useGasType.getId(),priceMapping,scheme);
            }else{
                //当前方案不等于表内当前方案，立即调价,由于只能支持一个方案，所以只能发当前方案了
                map.put("scheme",scheme);
                map.put("isImmediatelyAdj",true);
                CardPriceScheme mapping=new CardPriceScheme();
                mapping.setWriteCardPriceId(scheme.getId());
                mapping.setPreWriteCardPriceId(0L);
                mapping.setId(priceMapping.getId());
                updateById(mapping);
                return map;
            }
        }
    }

    private Map<String,Object> getPreScheme(Map<String,Object> map,Long typeID,CardPriceScheme priceMapping,PriceScheme scheme){
        PriceScheme nextScheme=priceSchemeService.queryPrePrice(typeID);
        if(nextScheme!=null) {
            map.put("scheme",nextScheme);
            map.put("isImmediatelyAdj",false);
            map.put("isPreScheme",true);
            CardPriceScheme mapping=new CardPriceScheme();
            mapping.setPreWriteCardPriceId(nextScheme.getId());
            mapping.setWriteCardPriceId(0L); //很关键回调的时候才知道是否是当前方案还是预调价方案
            mapping.setId(priceMapping.getId());
            updateById(mapping);
            return map;
        }else {
            map.put("scheme",scheme);
            map.put("isImmediatelyAdj",false);
            CardPriceScheme mapping=new CardPriceScheme();
            mapping.setWriteCardPriceId(scheme.getId());
            mapping.setPreWriteCardPriceId(0L);
            mapping.setId(priceMapping.getId());
            updateById(mapping);
            return map;
        }
    }
}
