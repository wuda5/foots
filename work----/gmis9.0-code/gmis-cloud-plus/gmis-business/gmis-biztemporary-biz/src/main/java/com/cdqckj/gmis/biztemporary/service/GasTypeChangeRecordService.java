package com.cdqckj.gmis.biztemporary.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.vo.GasTypeChangeRecordVO;
import com.cdqckj.gmis.biztemporary.vo.PriceChangeVO;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 客户用气类型变更记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-16
 */
public interface GasTypeChangeRecordService extends SuperService<GasTypeChangeRecord> {


    /**
     * 批量查询
     * @author hc
     * @param record
     * @return
     */
    List<HashMap<String,Object>> queryEx(GasTypeChangeRecord record);

    /**
     * 根据变更记录筛选方案
     * @param priceChangeVO
     * @return
     */
    PriceScheme getPriceSchemeByRecord(PriceChangeVO priceChangeVO);

    /**
     * 业务关注点用气类型变更记录列表查询
     * @param queryInfo 查询参数
     * @return 变更记录列表
     */
    List<GasTypeChangeRecordVO> queryFocusInfo(GasTypeChangeRecord queryInfo);

}
