package com.cdqckj.gmis.charges.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 调价补差记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
@Repository
public interface AdjustPriceRecordMapper extends SuperMapper<AdjustPriceRecord> {
    /**
     * 获取中心计费（预付费、后付费）抄表数据
     * @param adjustPrice
     * @param handler
     */
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(AdjustPriceRecord.class)
    public void getIotReadMeterData(AdjustPrice adjustPrice, ResultHandler<AdjustPriceRecord> handler);
    /**
     * 获取普表抄表数据
     * @param adjustPrice
     * @param handler
     */
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(AdjustPriceRecord.class)
    public void getReadMeterData(AdjustPrice adjustPrice, ResultHandler<AdjustPriceRecord> handler);

    /**
     * 获取充值数据
     * @param adjustPrice
     * @param handler
     */
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(AdjustPriceRecord.class)
    public void getRechargeData(AdjustPrice adjustPrice, ResultHandler<AdjustPriceRecord> handler);
    /**
     * 获取中心计费（预付费、后付费）抄表总条数
     * @param adjustPrice
     * @return
     */
    public int countIotReadMeterData(AdjustPrice adjustPrice);
    /**
     * 获取普表抄表总条数
     * @param adjustPrice
     * @return
     */
    public int countReadMeterData(AdjustPrice adjustPrice);
    /**
     * 获取充值总条数
     * @param adjustPrice
     * @return
     */
    public int countRechargeData(AdjustPrice adjustPrice);

    /**
     * 查询调价补差列表
     * @param page
     * @param wrapper
     * @param dataScope
     * @return
     */
    Page<AdjustPriceRecord> pageAdjustPrice(IPage<AdjustPriceRecord> page, @Param(Constants.WRAPPER) Wrapper<AdjustPriceRecord> wrapper, DataScope dataScope);
}
