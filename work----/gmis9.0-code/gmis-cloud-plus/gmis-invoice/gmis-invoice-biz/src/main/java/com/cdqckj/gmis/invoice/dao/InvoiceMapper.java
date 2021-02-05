package com.cdqckj.gmis.invoice.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.invoice.entity.Invoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-07
 */
@Repository
public interface InvoiceMapper extends SuperMapper<Invoice> {

    /**
     * 分页查询发票信息
     *
     * @param page
     * @param queryWrapper
     * @param dataScope
     * @return
     */
    IPage<Invoice> findPage(IPage page, @Param(Constants.WRAPPER) Wrapper<Invoice> queryWrapper, DataScope dataScope);

    /**
     * 作废发票
     *
     * @param invoiceId
     */
    int invalidInvoiceById(Long invoiceId);

    /**
     * 开票失败回调接口通过操作流水号更新开票结果
     *
     * @param result
     * @param serialNo
     * @return
     */
    int updateFailResultBySerialNo(@Param(value = "result") String result, @Param(value = "serialNo") String serialNo);

    /**
     * 通过缴费编号查询未冲红、作废的且开票中或开票成功的发票
     *
     * @param payNo 缴费编号
     * @return 发票数据
     */
    Invoice getEffectiveInvoice(@Param(value = "payNo") String payNo);

    /**
     * 已扎帐收费明细记录查询
     * @param page
     * @param summaryId
     * @return
     */
    Page<Invoice> queryInvoiceList(IPage<Invoice> page, @Param("summaryId") Long summaryId);

    /**
     * 关联缴费数据查询发票接口
     * @param startTime
     * @param endTime
     * @return
     */
    List<Invoice> queryInvoicesByTimeFrame(@Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 根据扎帐ID查询发票信息
     * @param summaryId
     * @return
     */
    List<Invoice> queryInvoicesBySummaryId(@Param("summaryId") Long summaryId);
    /**
     * 根据时间范围分页查询缴费记录的发票信息
     * @param page
     * @param startTime
     * @param endTime
     * @return
     */
    Page<Invoice> pageInvoicesByTimeFrame(IPage<Invoice> page, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 16:48
    * @remark 统计这个类型下面的发票
    */
    List<InvoiceDayStsVo> invoiceStsByType(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("type") String type, @Param("invalidStatus") Integer invalidStatus, @Param("dataScope") String dataScope);
}
