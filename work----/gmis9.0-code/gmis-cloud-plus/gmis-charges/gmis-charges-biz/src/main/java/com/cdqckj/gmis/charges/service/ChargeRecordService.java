package com.cdqckj.gmis.charges.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.dto.ChargeListReqDTO;
import com.cdqckj.gmis.charges.dto.ChargeMeterInfoDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.vo.StsCounterStaffVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderDetailFeeVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderFeeVo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 缴费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
public interface ChargeRecordService extends SuperService<ChargeRecord> {
    /**
     * 收据发票状态修改
     * @param chargeNo
     * @param invoiceType
     * @param status
     * @return
     */
    Boolean updateReceiptOrInvoiceStatus(String chargeNo,String invoiceType,String status);

    /**
     * 根据收费单号查询收费信息
     * @param chargeNo
     * @return
     */
    ChargeRecord getChargeRecordByNo(String chargeNo);


    /**
     * 还原退费状态为空
     * @param id
     * @return
     */
    Boolean rebackRefundStatus(Long id);

    /**
     * 查询最近一次收费记录。
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    ChargeRecord nearestCharge(String gasMeterCode, String customerCode, LocalDateTime dateTime);

    /**
     * 是否可扎帐
     * @param chargeUserId
     * @return
     */
    public Boolean isSummaryBill(long chargeUserId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 缴费信息列表查询
     * @param req
     * @return
     */
    public List<ChargeMeterInfoDTO> chargeCustomerAndMeterList(ChargeListReqDTO req,int currIndex,int pageSize);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 10:32
    * @remark 柜台人员的统计
    */
    List<StsCounterStaffVo> counterStaff(Page<StsCounterStaffVo> resultPage, StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 10:15
    * @remark 统计这些费用
    */
    List<StsGasLadderFeeVo> stsGasLadderFee(StsSearchParam stsSearchParam, List<Long> idList);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 16:13
    * @remark 统计这些阶梯费用
    */
    List<StsGasLadderDetailFeeVo> stsGasLadderDetailFee(StsSearchParam stsSearchParam, List<Long> idList);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 15:28
    * @remark 收费方式
    */
    List<StsInfoBaseVo<String, BigDecimal>> stsFeeByChargeMethod(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/20 11:39
    * @remark 收费的类型
    */
    List<StsInfoBaseVo<String, BigDecimal>> stsChargeFeeItemType(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/22 15:07
    * @remark 预付费表
     * @return
    */
    BigDecimal stsBeforeGasMeter(StsSearchParam stsSearchParam, String gasMeterType);
}
