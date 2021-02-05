package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.operateparam.dao.PriceMappingMapper;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.service.PriceMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 价格方案映射表
 * </p>
 *
 * @author gmis
 * @date 2020-12-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PriceMappingServiceImpl extends SuperServiceImpl<PriceMappingMapper, PriceMapping> implements PriceMappingService {

    @Override
    public PriceMapping getGasMeterPriceMapping(String gasCode) {
        return this.baseMapper.getGasMeterPriceMapping(gasCode);
    }

    public PriceScheme restGasMeterPriceMapping(String gasMeterCode, Long priceId, Long yId){
        long c=priceId.longValue();
        PriceMapping priceMapping = getGasMeterPriceMapping(gasMeterCode);
        //只有0-127
        if (priceMapping == null) {
            priceMapping = new PriceMapping();
            priceMapping.setGasMeterCode(gasMeterCode);
            priceMapping.setPriceNum(1);
            priceMapping.setPrePriceNum(0);
            priceMapping.setPriceId(c);
            saveOrUpdate(priceMapping);
            return PriceScheme.builder().id(c).priceNum(1).build();
        }else {
            Long p = priceMapping.getPriceId();
            Long pre = priceMapping.getPrePriceId();
            int preNum=priceMapping.getPrePriceNum().intValue();
            int cNum=0;
            if (priceMapping.getPriceNum() == 0) {
                //当前等于上次操作的方案ID 上次的还没发，直接发 当前
                if(c==p.longValue()){
                    cNum=preNum+1>127?1:preNum+1;
                    priceMapping.setPriceId(c);
                    priceMapping.setPriceNum(cNum);
                    saveOrUpdate(priceMapping);
                    return PriceScheme.builder()
                            .id(c)
                            .priceNum(cNum).build();
                }else{
                    //当前不等于上次未发的方案ID，分情况:
                    //1.当前C不等于上次A也不等于前一次B，说明是个新的当前方案，待发C，且应该立即调价
                    if (pre==null || c!=pre.longValue()) {
                        cNum=preNum+1>127?1:preNum+1;
                        priceMapping.setPriceId(c);
                        priceMapping.setPriceNum(cNum);
                        saveOrUpdate(priceMapping);
                        return PriceScheme.builder()
                                .id(c)
                                .priceNum(cNum).build();
                    }else{
                        //2.当前C不等于上次A但是等于前一次B，只能说A是预期调价且没发，这个时候应该重新获取预调价看看预调价是否还是A
                        //如果严谨一点这里应该要重新查预调价，由于考虑价格方案变更没那么频繁，所以直接发A
                        long yid=p.longValue();
                        if(yId!=null && yId.longValue()!=yid){
                            yid=yId.longValue();
                            priceMapping.setPriceId(yid);
                            saveOrUpdate(priceMapping);
                        }
                        cNum=preNum+1>127?1:preNum+1;
                        return PriceScheme.builder()
                                .id(yid)
                                .priceNum(cNum).build();
                    }
                }
            }else{
                //没有退费,当前等于上次，分情况1：是上次发的时候是预调价，现在已经生效 2.上次到现在都没调价。 两者都应该查预调价
                if(c==p.longValue()){
                    //应该查预调价，有预调价更新且发预调价
                    if(yId!=null){
                        cNum=priceMapping.getPriceNum().intValue()+1;
                        cNum=cNum>127?1:cNum;
                        priceMapping.setPriceNum(cNum);
                        priceMapping.setPrePriceNum(priceMapping.getPriceNum());
                        priceMapping.setPriceId(yId);
                        saveOrUpdate(priceMapping);
                        return PriceScheme.builder()
                                .id(yId)
                                .priceNum(cNum).build();
                    }else{
                        //直接发本次不走任何更新
                        return PriceScheme.builder()
                                .id(c)
                                .priceNum(priceMapping.getPriceNum()).build();
                    }
                }else{
                    //不等于上次，说明上次方案过期，要更新
                    cNum=priceMapping.getPriceNum().intValue()+1;
                    cNum=cNum>127?1:cNum;
                    priceMapping.setPriceNum(cNum);
                    priceMapping.setPrePriceNum(priceMapping.getPriceNum());
                    priceMapping.setPriceId(c);
                    saveOrUpdate(priceMapping);
                    return PriceScheme.builder()
                            .id(c)
                            .priceNum(cNum).build();

                }
            }
        }
    }

    @Override
    public int saveChangePrice(PriceMapping priceMapping) {
        return baseMapper.insert(priceMapping);
    }
}
