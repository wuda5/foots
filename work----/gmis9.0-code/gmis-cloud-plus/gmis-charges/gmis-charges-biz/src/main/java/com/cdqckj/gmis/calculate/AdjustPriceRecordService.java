package com.cdqckj.gmis.calculate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;

/**
 * <p>
 * 业务接口
 * 调价补差记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
public interface AdjustPriceRecordService extends SuperService<AdjustPriceRecord> {
    /**
     * 生成中心计费（预付费、后付费）抄表数据
     * @param adjustPrice
     * @return
     */
    public void generateIotReadMeterData(AdjustPrice adjustPrice);
    /**
     * 生成普表抄表数据
     * @param adjustPrice
     * @return
     */
    public void generateReadMeterData(AdjustPrice adjustPrice);
    /**
     * 生成充值数据（IC卡气量表、表端计费气量表）
     * @param adjustPrice
     * @return
     */
    public void generateRechargeData(AdjustPrice adjustPrice);
    /**
     * 中心计费（预付费、后付费）抄表总条数
     * @param adjustPrice
     * @return
     */
    public int countIotReadMeterData(AdjustPrice adjustPrice);
    /**
     * 普表抄表总条数
     * @param adjustPrice
     * @return
     */
    public int countReadMeterData(AdjustPrice adjustPrice);
    /**
     * 充值总条数
     * @param adjustPrice
     * @return
     */
    public int countRechargeData(AdjustPrice adjustPrice);
    /**
     * 查询调价补差列表
     * @param params
     * @return
     */
    Page<AdjustPriceRecord> pageAdjustPrice(Integer pageNo, Integer pageSize, AdjustPrice params);
}
