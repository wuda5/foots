package com.cdqckj.gmis.statistics.domain.log.process;

import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargeItemEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.statistics.domain.log.*;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import com.cdqckj.gmis.statistics.entity.FeiDay;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.entity.GasFeiNow;
import com.cdqckj.gmis.statistics.enums.StsAllChargeItemEnum;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/25 9:19
 * @remark: 收费的日志同步
 */
@Log4j2
public class ChargeRecordLog implements TableUpdateLog<ChargeRecord>, TableInsertLog<ChargeRecord> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    public ChargeRecordLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<ChargeRecord> formatRowData, ProcessTypeEnum typeEnum) {
        // 添加记录的时候是收费状态就记录
        ChargeRecord after = formatRowData.getAfterRowValue();
        if (typeEnum.equals(ProcessTypeEnum.PROCESS_INSERT)){
            if (after.getChargeStatus().equals("CHARGED")){
                return true;
            }
        }else {
            if (formatRowData.anyColChange("refundStatus", "dataStatus")){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean insertLog(FormatRowData<ChargeRecord> formatRowData) {

        ChargeRecord after = formatRowData.getAfterRowValue();
        List<ChargeItemRecord> itemRecordList = sysServiceBean.getChargeItemRecordBizApi().getChangeItemByChargeNo(after.getChargeNo()).getData();
        if (after.getChargeStatus().equals("CHARGED")){
            List<FeiDay> feiDayList = op(after, 1, itemRecordList);
            saveGasFeeInfo(after, itemRecordList);

            sysServiceBean.getFeiDayService().saveOrUpdateBatch(feiDayList);
            saveFeiDay(feiDayList);
        }
        return true;
    }

    @Override
    public Boolean updateLog(FormatRowData<ChargeRecord> formatRowData) {

        ChargeRecord after = formatRowData.getAfterRowValue();
        List<ChargeItemRecord> itemRecordList = sysServiceBean.getChargeItemRecordBizApi().getChangeItemByChargeNo(after.getChargeNo()).getData();
        // 退费 REFUNDED: 退费完成
        if (formatRowData.colChange("refund_status") && after.getRefundStatus().equals("REFUNDED")){
            List<FeiDay> feiDayList = op(after, 2, itemRecordList);
            saveFeiDay(feiDayList);
        }
        // 收费
        if (formatRowData.colChange("chargeStatus") && after.getChargeStatus().equals("CHARGED")){
            List<FeiDay> feiDayList = op(after, 1, itemRecordList);
            saveGasFeeInfo(formatRowData.getAfterRowValue(), itemRecordList);

            saveFeiDay(feiDayList);
        }
        return true;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/9 16:34
     * @remark 处理日志
     */
    private List<FeiDay> op(ChargeRecord after, Integer type, List<ChargeItemRecord> itemRecordList) {

        List<FeiDay> feiDayList = new ArrayList<>();
        // 充值赠送
        if (!after.getRechargeGiveMoney().equals(BigDecimal.ZERO)) {

            FeiDay logData = BeanPlusUtil.toBean(after, FeiDay.class);
            logData.setId(null);
            logData.setChargeItemMoney(after.getRechargeGiveMoney());
//            FeiDay feiDay = opLog(after, type, StsAllChargeItemEnum.RECHARGE_GIFT.getCode(), logData);
//            feiDayList.add(feiDay);
        }
        // 其他的item的费用
        for (ChargeItemRecord itemRecord : itemRecordList) {
            FeiDay logData = BeanPlusUtil.toBean(itemRecord, FeiDay.class);
            logData.setId(null);
            FeiDay feiDay = opLog(after, type, logData.getChargeItemSourceCode(), logData);
            feiDayList.add(feiDay);
        }
        return feiDayList;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 9:27
     * @remark 1系统配置项目 2充值赠送
     */
    private FeiDay opLog(ChargeRecord after, Integer type, String chargeItemSourceCode, FeiDay logData) {

        logData.setTCode(BaseContextHandler.getTenant());
        logData.setBusinessHallId(sysServiceBean.getBusinessHallId(logData.getOrgId()));
        logData.setType(type);
        logData.setOnline(1);
        logData.setChargeItemSourceCode(chargeItemSourceCode);
        logData.setChargeMethodCode(after.getChargeMethodCode());
        logData.setStsDay(DateStsUtil.dayBeginTime(after.getUpdateTime()));
        return logData;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/19 15:04
     * @remark 保存一个单独的记录来做用气的同步机
     */
    private void saveGasFeeInfo(ChargeRecord after, List<ChargeItemRecord> itemRecordList){

        // 其他的item的费用
        for (ChargeItemRecord itemRecord : itemRecordList) {
            if (ChargeItemEnum.GASFEE.eq(itemRecord.getChargeItemSourceCode())){

                GasFeiNow logData = new GasFeiNow();
                User user = sysServiceBean.getUserBizApi().get(after.getCreateUser()).getData();
                logData.setId(null);
                logData.setTCode(BaseContextHandler.getTenant());
                logData.setCompanyCode(user.getCompanyCode());
                logData.setOrgId(user.getOrg().getKey());
                logData.setBusinessHallId(sysServiceBean.getBusinessHallId(user.getOrg().getKey()));
                logData.setCreateUserId(after.getCreateUser());

                Customer customer = sysServiceBean.getCustomerBizApi().findCustomerByCode(after.getCustomerCode()).getData();
                logData.setOrderSourceName(itemRecord.getChargeItemSourceCode());
                logData.setCustomerTypeCode(customer.getCustomerTypeCode());
                LocalDateTime beginTime = DateStsUtil.dayBeginTime(LocalDateTime.now());
                logData.setStsDay(beginTime);

                String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("total_number", "gas_amount", "fei_amount"));
                GasFeiNow recordData = sysServiceBean.getGasFeiNowService().getNowRecord(sql);
                if (recordData == null) {
                    recordData = logData;
                    recordData.setTotalNumber(0);
                    recordData.setGasAmount(BigDecimal.ZERO);
                    recordData.setFeiAmount(BigDecimal.ZERO);
                }else {
                    recordData.setTotalNumber(recordData.getTotalNumber() + 1);
                    recordData.setGasAmount(recordData.getGasAmount().add(itemRecord.getChargeItemGas()));
                    recordData.setFeiAmount(recordData.getFeiAmount().add(itemRecord.getChargeItemMoney()));
                }
                sysServiceBean.getGasFeiNowService().saveOrUpdate(recordData);
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/19 16:39
     * @remark 保存收费的数据
     */
    private void saveFeiDay(List<FeiDay> feiDayList){
        for (FeiDay logData: feiDayList) {
            String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("chargeItemMoney", "amount"));
            FeiDay recordData = sysServiceBean.getFeiDayService().getNowRecord(sql);
            if (recordData == null) {
                recordData = logData;
                recordData.setId(null);
                sysServiceBean.getFeiDayService().save(recordData);
            } else {
                recordData.setTotalNumber(recordData.getTotalNumber() + 1);
                recordData.setAmount(recordData.getAmount() + 1);
                recordData.setChargeItemMoney(recordData.getChargeItemMoney().add(logData.getChargeItemMoney()));
                sysServiceBean.getFeiDayService().updateById(recordData);
            }
        }
    }
}
