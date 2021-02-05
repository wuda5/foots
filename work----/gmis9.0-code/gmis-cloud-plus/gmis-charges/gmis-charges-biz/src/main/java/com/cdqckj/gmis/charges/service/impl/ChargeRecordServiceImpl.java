package com.cdqckj.gmis.charges.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.ChargeRecordMapper;
import com.cdqckj.gmis.charges.dto.ChargeListReqDTO;
import com.cdqckj.gmis.charges.dto.ChargeMeterInfoDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.enums.SummaryBillStatusEnum;
import com.cdqckj.gmis.charges.service.ChargeRecordService;
import com.cdqckj.gmis.charges.vo.StsCounterStaffVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderDetailFeeVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderFeeVo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.invoice.enumeration.InvoiceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 缴费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ChargeRecordServiceImpl extends SuperServiceImpl<ChargeRecordMapper, ChargeRecord> implements ChargeRecordService {
    /**
     * 票据发票状态更新
     * @param chargeNo
     * @param invoiceType
     * @param status
     * @return
     */
    @Override
    public Boolean updateReceiptOrInvoiceStatus(String chargeNo,String invoiceType,String status){
        ChargeRecord chargeRecord=new ChargeRecord();
        if(chargeNo==null) {
            return false;
        }
        if(InvoiceTypeEnum.RECEIPT.getCode().equals(invoiceType)){
            chargeRecord.setReceiptStatus(status);
        }else{
            chargeRecord.setInvoiceStatus(status);
        }
        return this.update(chargeRecord,new LbqWrapper<ChargeRecord>().eq(ChargeRecord::getChargeNo,chargeNo));
    }
    /**
     * 根据收费单号查询收费信息
     * @param chargeNo
     * @return
     */
    public ChargeRecord getChargeRecordByNo(String chargeNo){
        ChargeRecord record=getOne(new LbqWrapper<ChargeRecord>().eq(ChargeRecord::getChargeNo,chargeNo));
        return record;
    }

    /**
     * 还原退费状态为空
     * @param id
     * @return
     */
    public Boolean rebackRefundStatus(Long id){
         baseMapper.rebackRefundStatus(id);
        return true;
    }

    /**
     * 查询最近一次收费记录。
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    public  ChargeRecord nearestCharge(String gasMeterCode, String customerCode, LocalDateTime dateTime){
        //后续可以修改为 只是不等于REFUNDED 现在兼容历史数据等于null
        LbqWrapper wrapper=Wraps.<ChargeRecord>lbQ()
                .eq(ChargeRecord::getGasMeterCode,gasMeterCode)
                .eq(ChargeRecord::getCustomerCode,customerCode)
                .gt(ChargeRecord::getCreateTime,dateTime)
                .and(lbq->lbq.ne(ChargeRecord::getRefundStatus,RefundStatusEnum.REFUNDED.getCode())
                        .or().isNull(ChargeRecord::getRefundStatus)
                )
                .eq(ChargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .orderByDesc(ChargeRecord::getCreateTime)
                .last(" limit 1 ");
        log.info("查询最近一条是否完成退费：{}",wrapper.getSqlSelect());
        ChargeRecord record=getOne(wrapper);
        return record;
    }

    @Override
    public Boolean isSummaryBill(long chargeUserId, LocalDateTime startTime, LocalDateTime endTime) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<ChargeRecord> wrapper = new QueryWrapper<>();
        List<String> refundStatusList = new ArrayList<>();
        refundStatusList.add(RefundStatusEnum.NOMAL.getCode());
        refundStatusList.add(RefundStatusEnum.REFUNDED.getCode());
        wrapper.lambda().eq(ChargeRecord::getCreateUserId, chargeUserId)
                .eq(ChargeRecord::getSummaryBillStatus, SummaryBillStatusEnum.UNBILL.getCode())
                .and(sql->sql.ne(ChargeRecord::getChargeStatus, ChargeStatusEnum.CHARGED.getCode())
                        .notIn(ChargeRecord::getRefundStatus, refundStatusList));
        if (ObjectUtil.isNull(startTime) && ObjectUtil.isNotNull(endTime)) {
            wrapper.lambda().and(dateSql -> dateSql.le(ChargeRecord::getCreateTime, endTime.format(dateTimeFormatter)));
        }
        else if(ObjectUtil.isNotNull(startTime) && ObjectUtil.isNotNull(endTime)) {
            wrapper.lambda().and(dateSql -> dateSql.ge(ChargeRecord::getCreateTime, startTime.format(dateTimeFormatter))
                    .le(ChargeRecord::getCreateTime, endTime.format(dateTimeFormatter)));

        }
        else if(ObjectUtil.isNotNull(startTime) && ObjectUtil.isNull(endTime)) {
            wrapper.lambda().and(dateSql -> dateSql.ge(ChargeRecord::getCreateTime, startTime.format(dateTimeFormatter)));
        }
        return super.count(wrapper) > 0;

    }

    /**
     * 缴费信息列表查询
     * @param req
     * @return
     */
    public List<ChargeMeterInfoDTO> chargeCustomerAndMeterList(ChargeListReqDTO req, int currIndex, int pageSize){
        return null;
    }

    @Override
    public List<StsCounterStaffVo> counterStaff(Page<StsCounterStaffVo> resultPage, StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.counterStaff(resultPage, stsSearchParam, dataScope);
    }

    @Override
    public List<StsGasLadderFeeVo> stsGasLadderFee(StsSearchParam stsSearchParam, List<Long> idList) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsGasLadderFee(stsSearchParam, idList, dataScope);
    }

    @Override
    public List<StsGasLadderDetailFeeVo> stsGasLadderDetailFee(StsSearchParam stsSearchParam, List<Long> idList) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsGasLadderDetailFee(stsSearchParam, idList, dataScope);
    }

    @Override
    public List<StsInfoBaseVo<String, BigDecimal>> stsFeeByChargeMethod(StsSearchParam stsSearchParam) {
        String refundStatus = stsSearchParam.getSearchKeyValue("refundStatus");
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        if (refundStatus != null){
            return this.baseMapper.stsRefundByChargeMethod(stsSearchParam, dataScope);
        }else {
            return this.baseMapper.stsChangeFeeByChargeMethod(stsSearchParam, dataScope);
        }
    }

    @Override
    public List<StsInfoBaseVo<String, BigDecimal>> stsChargeFeeItemType(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsChargeFeeItemType(stsSearchParam, dataScope);
    }

    @Override
    public BigDecimal stsBeforeGasMeter(StsSearchParam stsSearchParam, String gasMeterType) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsBeforeGasMeter(stsSearchParam, gasMeterType, dataScope);
    }
}
