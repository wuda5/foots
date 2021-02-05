package com.cdqckj.gmis.charges.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.OtherFeeRecord;
import com.cdqckj.gmis.charges.enums.ChargeItemEnum;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.enums.MoneyMethodEnum;
import com.cdqckj.gmis.charges.service.ChargeLoadService;
import com.cdqckj.gmis.charges.service.OtherFeeLoadService;
import com.cdqckj.gmis.charges.service.OtherFeeRecordService;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.enumeration.TollItemChargeFrequencyEnum;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
@Service
@Log4j2
@DS("#thread.tenant")
public class OtherFeeLoadServiceImpl extends SuperCenterServiceImpl implements OtherFeeLoadService {

    @Autowired
    ChargeLoadService chargeLoadService;

    @Autowired
    TollItemService tollItemService;

    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    OtherFeeRecordService otherFeeRecordService;


    @Autowired
    CustomerGasMeterRelatedService customerGasMeterRelatedService;

    /**
     * 附加费
     * @param meter
     */
    public List<ChargeItemRecord> loadOtherFeeItem(GasMeter meter,Customer customer){
        //not open account
        if(meter.getOpenAccountTime()==null){
            return new ArrayList<>();
        }
        List<OtherFeeRecord> list= loadOhterFeeRecords(meter,customer);
        List<ChargeItemRecord> items=new ArrayList<>();
        for (OtherFeeRecord record : list) {
            items.add(convertOtherFeeToChargeItem(record));
        }
        return items;
    }


    /**
     * 加载其他收费项（附加费）：
     * 收费项分频次：针对一次性收费校验是否已经收费，判断开户时间在一次性收费之后
     * 针对周期性收费校验是否有缴费记录，结合缴费记录计算近期是否有待缴费项（校验开户时间）
     * 按需收费的供操作员在界面选择（校验开户时间）
     * @param meter
     */
    private List<OtherFeeRecord> loadOhterFeeRecords(GasMeter meter,Customer customer) {

        //初步计算100条附加费，最多也就按年10年也就1000条，按月 10年也就10000条，预计不会有这么多，所以直接内存加载
        List<OtherFeeRecord> orecords=otherFeeRecordService.list(
                new LbqWrapper<OtherFeeRecord>()
                        .eq(OtherFeeRecord:: getGasmeterCode,meter.getGasCode())
                        .eq(OtherFeeRecord:: getCustomerCode,customer.getCustomerCode())
                        .eq(OtherFeeRecord:: getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        if(orecords==null )orecords=new ArrayList<>();
        List<OtherFeeRecord> unchargeRecords=new ArrayList<>();
        Map<Long,OtherFeeRecord> cycleNeasts=new HashMap<>();
        OtherFeeRecord temp;
        Set<Long> onceids=new HashSet<>();
        for (OtherFeeRecord record : orecords) {
            if(ChargeStatusEnum.UNCHARGE.eq(record.getChargeStatus())){
                unchargeRecords.add(record);
            }
            if(TollItemChargeFrequencyEnum.BY_YEAR.eq(record.getChargeFrequency()) ||
                    TollItemChargeFrequencyEnum.BY_MONTH.eq(record.getChargeFrequency())
            ){
                //周期收费项最近一次计费
                temp=cycleNeasts.get(record.getTollItemId());
                if(temp!=null){
                    if(temp.getTotalEndTime().isBefore(record.getTotalEndTime())){
                        cycleNeasts.put(record.getTollItemId(),record);
                    }
                }else{
                    cycleNeasts.put(record.getTollItemId(),record);
                }
            }else if(TollItemChargeFrequencyEnum.ONE_TIME.eq(record.getChargeFrequency())){
                onceids.add(record.getTollItemId());
            }
        }


        //查询一次性收费记录，用于过滤一次性收费项
        List<OtherFeeRecord> oneTimeCharges =null ;
        Map<Long, OtherFeeRecord> oneTimeMap=new HashMap<>();
        //整体加载所有收费项：非删除，非禁用，场景类型为附加费
        List<TollItem> qitems=tollItemService.list(
                new LbqWrapper<TollItem>()
                        .eq(TollItem::getDataStatus,DataStatusEnum.NORMAL.getValue())
                        .eq(TollItem::getSceneCode, TolltemSceneEnum.OTHER.getCode())
                        .eq(TollItem::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue())
        );
        if (CollectionUtils.isEmpty(qitems)) return unchargeRecords;
        List<TollItem> items=new ArrayList<>();
        for (TollItem item : qitems) {
            //用气类型是全部或者包含该气表的
            if (!validUseGasType(item.getUseGasTypes(), meter)) continue;
            items.add(item);
        }
        if (CollectionUtils.isEmpty(items)) return unchargeRecords;

        List<OtherFeeRecord> saveList=new ArrayList<>();
        List<OtherFeeRecord> nosaveList=new ArrayList<>();
        Map<Long,OtherFeeRecord> totalItemMap = new HashMap<>();
        LocalDate now = LocalDate.now();
        LocalDate periodEnd;
        LocalDate openAccountDate = meter.getOpenAccountTime().toLocalDate();
        for (TollItem item : items) {
            //用气类型是全部或者包含该气表的
            if (!validUseGasType(item.getUseGasTypes(), meter)) continue;
            if(item.getStartTime()==null){
                 continue;
            }
            if(item.getChargePeriod()==null || item.getChargePeriod().intValue()==0){
                //无期限
                periodEnd=null;
            }else{
                periodEnd=item.getStartTime().plusYears(item.getChargePeriod());
            }
            //看开户时间是否在周期内,不在就不需要有该收费项
            if(periodEnd!=null && openAccountDate.isBefore(item.getStartTime())
                    && openAccountDate.isAfter(periodEnd)){
                continue;
            }
            //一次性收费，校验用气类型，校验是否已收费;  如果一次性缴费已经存在缴费记录，就不进行再次收费。
            if(TollItemChargeFrequencyEnum.ONE_TIME.getCode().equals(item.getChargeFrequency())){
                if(!onceids.contains(item.getId()) && (item.getStartTime().isBefore(now) ||
                        item.getStartTime().isEqual(now))){
                    saveList.add(convertllItemToOtherFee(item,meter,customer));
                    continue;
                }
            }else if (TollItemChargeFrequencyEnum.ON_DEMAND.getCode().equals(item.getChargeFrequency())
                    && (item.getStartTime().isBefore(now) || item.getStartTime().isEqual(now))) {
                //按需收费项
                nosaveList.add(convertllItemToOtherFee(item,meter,customer));
                continue;
            }else if (TollItemChargeFrequencyEnum.BY_MONTH.getCode().equals(item.getChargeFrequency())
                    || TollItemChargeFrequencyEnum.BY_YEAR.getCode().equals(item.getChargeFrequency())
            ){

                OtherFeeRecord record= cycleNeasts.get(item.getId());
                OtherFeeRecord cycleRecord= caculCycleChargeItem(record, item, meter,customer);
                if(cycleRecord!=null){
                    saveList.add(cycleRecord);
                }

            }
        }
        for (OtherFeeRecord record : saveList) {
            record.setDataStatus(DataStatusEnum.NORMAL.getValue());
//            otherFeeRecordService.save(record);
//            System.out.println("唯一id:============================="+record.getId());
//            unchargeRecords.add(record);
        }
        otherFeeRecordService.saveBatchSomeColumn(saveList);
        unchargeRecords.addAll(saveList);
        unchargeRecords.addAll(nosaveList);
        return unchargeRecords;
    }


    /**
     * 周期收费项判断是否进行收费算法
     * 已经缴费过，直接通过上次截止日期（）
     * 未缴费，需要判断开户时间
     * 这里暂时不考虑周期收费项修改开始日期及按年按月互改的情况，另外周期收费项修改为了一次性和按需不会进入该方法
     * @param record 周期收费项缴费记录
     */
    private OtherFeeRecord caculCycleChargeItem(OtherFeeRecord record, TollItem item, GasMeter meter,Customer customer) {

        OtherFeeRecord itemTemp = convertllItemToOtherFee(item,meter,customer);
        //周期开始时间默认 收费项开始时间
        LocalDate startDate=item.getStartTime();
        if (record!=null) {
            //已经缴费过，直接通过上次截止日期+1是新周期计费开始日期
//                TollItemCycleLastChargeRecord record=cycleMap.get(item.getId());
            LocalDate lastDate=record.getTotalEndTime().plusDays(1);
            //这里应该要做周期原始开始时间判断，如果修改过，就执行下面逻辑，没修改过就直接设置，需要在记录表记录原始开始时间
            if(startDate.isEqual(record.getTotalStartTime())){
                startDate=lastDate;
            }else {
                //如果修改那么这里周期缴费记录开始时间就要做处理
                if (startDate.isBefore(lastDate)) {
                    //开始日期在上次收费周期之前，例如 2019-03-01 之前都缴纳了费用，开始日期提前到了 2020-01-01
                    //为了不重复收费，从下个周期开始计算，先调整到当前已缴费所在的年份，至少从这一年开始
                    startDate = startDate.withYear(lastDate.getYear());
                    if (TollItemChargeFrequencyEnum.BY_YEAR.getCode().equals(item.getChargeFrequency())) {
                        if (startDate.isBefore(lastDate)) {
                            startDate = startDate.plusYears(1);
                        }
                    }
                    if (TollItemChargeFrequencyEnum.BY_MONTH.getCode().equals(item.getChargeFrequency())) {
                        startDate = startDate.withMonth(lastDate.getMonthValue());
                        if (startDate.isBefore(lastDate)) {
                            startDate = startDate.plusMonths(1);
                        }
                    }
                }
                //新的开始日期在上次计费之后或者等于开始日期，从开始日期算
            }
        } else {
            /**
             * 没有缴纳费用？判断开户日期和收费项开始日期,开户之前的不能计算费用，从开户这个时间点往后算周期
             * 如果是开户之后的 收费项就直接按照收费项周期开始时间计算。
             * 相等的情况： 2018-06-05 开户 ，收费项是 2018-06-05 开始，默认是直接开始算。
             * 后开户的情况： 2019-10-09 开户 ，收费项是 2018-06-03
             *          年周期开始为（次年生效）：2020-06-03 ,如果当年生效就去掉+1
             *          月周期开始为（次月生效）：2019-11-03 ,如果当月生效就去掉+1
             */
            LocalDate openAccountDate = meter.getOpenAccountTime().toLocalDate();
            //startDate=2018-06-03
            if (openAccountDate.isAfter(item.getStartTime()) ) {
                //startDate=2019-06-03 ，年周期 2019.06.03-2020.06.03 是一个周期，所以如果大于 2019.06.03都需要加一年，下个周期开始算
                startDate=startDate.withYear(openAccountDate.getYear());
                if (TollItemChargeFrequencyEnum.BY_YEAR.getCode().equals(item.getChargeFrequency())) {
                    if (openAccountDate.isAfter(startDate)) {
                        startDate = startDate.plusYears(1);
                    }
                }
                //startDate=2019-06-03 ，实际开户时2019-10-09，此时月周期 2019.06.03-2019.07.03 只设置了年份还不到周期开始，需要设置月份
                if (TollItemChargeFrequencyEnum.BY_MONTH.getCode().equals(item.getChargeFrequency())) {
                    //startDate=2019-10-03,此时月周期 2019.10.03-2019.11.03,由于10-09开户，所以下个月才生效+1月，如果10月2号呢？应该不加，所以要判断天
                    startDate=startDate.withMonth(openAccountDate.getMonthValue());
                    //+1个月 11月03号就是该用户该周期收费项的开始收费时间
                    if(openAccountDate.isAfter(startDate)){
                        startDate=startDate.plusMonths(1);
                    }
                }
            }
        }
        itemTemp.setTotalStartTime(startDate);
        Period periodTime = Period.between(startDate, LocalDate.now());
        if (TollItemChargeFrequencyEnum.BY_YEAR.getCode().equals(item.getChargeFrequency())
                && periodTime.getYears() > 0
        ) {
            itemTemp.setTotalCycleCount(periodTime.getYears());
            itemTemp.setTotalEndTime(itemTemp.getTotalStartTime()
                    .plusYears(periodTime.getYears())
                    .withMonth(startDate.getMonthValue())
                    .withDayOfMonth(startDate.getDayOfMonth()).minusDays(1)
            );
        } else if (TollItemChargeFrequencyEnum.BY_MONTH.getCode().equals(item.getChargeFrequency())
                && periodTime.getYears() * 12 + periodTime.getMonths() > 0
        ) {
            itemTemp.setTotalCycleCount(periodTime.getYears() * 12 + periodTime.getMonths());
            itemTemp.setTotalEndTime(startDate
                    .plusYears(periodTime.getYears())
                    .withMonth(startDate.getMonthValue())
                    .plusMonths(periodTime.getMonths())
                    .withDayOfMonth(startDate.getDayOfMonth()).minusDays(1)
            );
        }
        //存在周期费用才返回
        if (itemTemp.getTotalCycleCount() != null) {
            if (MoneyMethodEnum.fixed.getCode().equals(itemTemp.getMoneyMethod())) {
                itemTemp.setTotalCycleMoney(item.getMoney().multiply(
                        new BigDecimal(itemTemp.getTotalCycleCount().toString())));
                //固定金额的直接填充合计
                itemTemp.setTotalChargeMoney(itemTemp.getTotalCycleMoney());
            }
            //满足收费条件
            return itemTemp;
        }
        return null;
    }

    /**
     * 验证用气类型,如果用气类型不为空判断用气类型是否存在。
     * 如果用气类型为空，说明是全局配置项。也返回true
     *
     * @param useGasTypes
     * @param meter
     * @return
     */
    private boolean validUseGasType(String useGasTypes, GasMeter meter) {
        if (StringUtils.isNotBlank(useGasTypes) && meter != null && meter.getUseGasTypeId() != null) {
            JSONArray array = JSON.parseArray(useGasTypes);
            //异常数据返回false
            if(array==null) return false;
            if(array.size()==0) return true;
            JSONObject temp;
            for (int i = 0; i < array.size(); i++) {
                temp = array.getJSONObject(i);
                if (temp.getLong("use_gas_type_id").longValue() == meter.getUseGasTypeId().longValue()) {
                    return true;
                }
            }
        }
        if (StringUtils.isBlank(useGasTypes)) {
            return true;
        }
        return false;
    }

    /**
     * 转换附加费为统一收费项内容
     * @param otherFee
     * @return
     */
    private ChargeItemRecord convertOtherFeeToChargeItem(OtherFeeRecord otherFee){
        ChargeItemRecord chargeItemRecord= new ChargeItemRecord()
                .setMoneyMethod(otherFee.getMoneyMethod())
                .setChargeItemSourceCode(ChargeItemEnum.OTHER.getCode())
                .setChargeItemSourceName(ChargeItemEnum.OTHER.getDesc())
                .setChargeItemName(otherFee.getItemName())
                .setChargeItemSourceId(otherFee.getId()!=null?otherFee.getId().toString():null)
                .setChargeItemMoney(otherFee.getTotalChargeMoney())
                .setTollItemScene(TolltemSceneEnum.OTHER.getCode())
                .setTollItemId(otherFee.getTollItemId())
                .setChargeFrequency(otherFee.getChargeFrequency())
                .setPrice(otherFee.getTotalChargeMoney())
                .setIsReductionItem(false);
        if(!TollItemChargeFrequencyEnum.ON_DEMAND.eq(otherFee.getChargeFrequency())  &&
                !TollItemChargeFrequencyEnum.ONE_TIME.eq(otherFee.getChargeFrequency())
        ){
            chargeItemRecord.setTotalCount(otherFee.getTotalCycleCount());
            if(otherFee.getTotalChargeMoney()!=null){
                chargeItemRecord.setPrice(otherFee.getTotalChargeMoney().divide(new BigDecimal(otherFee.getTotalCycleCount()+""),2, RoundingMode.UP));
            }
        }else{
            chargeItemRecord.setTotalCount(1);
        }
        setCommonParams(chargeItemRecord);
        return chargeItemRecord;
    }


    /**
     * 转换附加费 为 附加费收费明细记录
     * @param item
     * @param meter
     * @return
     */
    private OtherFeeRecord convertllItemToOtherFee(TollItem item, GasMeter meter, Customer customer){

        OtherFeeRecord itemTemp = new OtherFeeRecord()
                .setTollItemId(item.getId())
                .setCompanyCode(item.getCompanyCode())
                .setCompanyName(item.getCompanName())
                .setOrgId(item.getOrgId())
                .setOrgName(item.getOrgName())
                .setGasmeterCode(meter.getGasCode())
                .setChargeNo(null)
                .setItemName(item.getItemName())
                .setCustomerName(customer.getCustomerName())
                .setChargeFrequency(item.getChargeFrequency())
                .setChargePeriod(item.getChargePeriod())
                .setCycleValue(item.getCycleValue())
                .setMoneyMethod(item.getMoneyMethod())
                .setCustomerCode(customer.getCustomerCode())
                .setTotalEndTime(null)
                .setTotalStartTime(null)
                .setTotalCycleCount(null)
                .setTotalCycleMoney(null)
                .setTotalChargeMoney(item.getMoney())
                .setChargeStatus(ChargeStatusEnum.UNCHARGE.getCode())
                ;
//        itemTemp.setId(item.getId());
        setCommonParams(itemTemp);
        return itemTemp;
    }
}
