package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;

/**
 * <p>
 * 业务接口
 * 价格方案映射表
 * </p>
 *
 * @author gmis
 * @date 2020-12-03
 */
public interface PriceMappingService extends SuperService<PriceMapping> {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 14:29
    * @remark 获取这个表的价格方案 映射
    */
    PriceMapping getGasMeterPriceMapping(String gasCode);

    PriceScheme restGasMeterPriceMapping(String gasMeterCode, Long priceId, Long yId);

    /**
     * 保存调价后的记录
     * @param priceMapping
     * @return
     */
    int saveChangePrice(PriceMapping priceMapping);

}
