package com.cdqckj.gmis.bizcenter.temp.counter.component;


import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterDataBizService;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementResult;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.enumeration.DataTypeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hp
 */
@Component
public class CostSettlementComponent {

    @Autowired
    private ReadMeterDataBizService readMeterDataBizService;
    @Autowired
    private ReadMeterDataIotApi readMeterDataIotApi;
    @Autowired
    private CalculateApi calculateApi;
    @Autowired
    private GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;
    @Autowired
    private ReadMeterDataApi readMeterDataApi;


    /**
     * 未上报数据结算
     *
     * @param oldVersion 表具版本
     * @return 欠费Id
     */
    public SettlementResult costSettlement(String customerCode, String customerName, BigDecimal meterEndGas, DataTypeEnum dataType,
                                           GasMeter meter, GasMeterVersion oldVersion) {

        SettlementResult result = new SettlementResult();
        if (OrderSourceNameEnum.READMETER_PAY.eq(oldVersion.getOrderSourceName())) {

            ReadMeterData latestData = readMeterDataApi.getLatestData(meter.getGasCode(), customerCode).getData();
            if (Objects.nonNull(latestData) && !DataTypeEnum.ORDINARY_DATA.getCode().equals(latestData.getDataType())) {

                latestData.setUseGasTypeId(meter.getUseGasTypeId())
                        .setUseGasTypeName(meter.getUseGasTypeName())
                        .setReadMeterMonth(LocalDateTime.now().getMonthValue())
                        .setReadMeterYear(LocalDateTime.now().getYear())
                        .setReadTime(LocalDate.now())
                        .setRecordTime(LocalDate.now())
                        .setLastReadTime(LocalDate.now())
                        .setCurrentTotalGas(meterEndGas)
                        .setProcessStatus(ProcessEnum.APPROVED)
                        .setMonthUseGas(meterEndGas.subtract(latestData.getLastTotalGas()))
                        .setDataType(dataType.getCode());
                readMeterDataApi.update(BeanUtil.copyProperties(latestData, ReadMeterDataUpdateDTO.class));

                List<ReadMeterData> resultList = calculateApi.settlement(Collections.singletonList(latestData), 1).getData();
                GasmeterArrearsDetail data = gasmeterArrearsDetailBizApi.getBySettlementNo(resultList.get(0).getGasOweCode()).getData();

                result.setReadMeterDataId(latestData.getId());
                if (Objects.nonNull(data)) {
                    result.setArrearsDetails(Collections.singletonList(data));
                }
            } else {
                //有未上报的数据 自动结算
                ReadMeterData readMeterData = ReadMeterData.builder()
                        .companyCode(meter.getCompanyCode())
                        .companyName(meter.getCompanyName())
                        .orgId(meter.getOrgId())
                        .orgName(meter.getOrgName())
                        .customerCode(customerCode)
                        .customerName(customerName)
                        .gasMeterNumber(meter.getGasMeterNumber())
                        .useGasTypeId(meter.getUseGasTypeId())
                        .useGasTypeName(meter.getUseGasTypeName())
                        .gasMeterCode(meter.getGasCode())
                        .currentTotalGas(meterEndGas)
                        .dataType(dataType.getCode())
                        .build();
                ReadMeterData data = readMeterDataBizService.costSettlement(readMeterData).getData();
                GasmeterArrearsDetail arrearsDetail = gasmeterArrearsDetailBizApi.getByReadMeterId(data.getId()).getData();

                result.setReadMeterDataId(data.getId());
                if (Objects.nonNull(arrearsDetail)) {
                    result.setArrearsDetails(Collections.singletonList(arrearsDetail));
                }
            }
        }
        //中心计费后付费抄表数据结算
        if (OrderSourceNameEnum.REMOTE_READMETER.eq(oldVersion.getOrderSourceName())
                || OrderSourceNameEnum.CENTER_RECHARGE.eq(oldVersion.getOrderSourceName())) {
            //查询未缴费的抄表数据
            Long readMeterId;
            List<ReadMeterDataIot> unCharge = readMeterDataIotApi.queryUnChargedDataIot(meter.getGasCode()).getData();
            if (CollectionUtils.isEmpty(unCharge)) {
                unCharge = new ArrayList<>();
            }
            //查询最新的上报数据  获取最新抄表止数
            ReadMeterDataIot previousData = readMeterDataIotApi.getPreviousData(meter.getGasCode(), meter.getGasMeterNumber(), customerCode, LocalDateTime.now());
            BigDecimal lastTotalGas;
            if (Objects.nonNull(previousData)) {
                //上一条是正常上报的抄表数据
                lastTotalGas = previousData.getCurrentTotalGas();
            } else {
                //还没有上报抄表数据
                lastTotalGas = meter.getGasMeterBase();
            }
            //获取场景数据
            ReadMeterDataIot sceneData = readMeterDataIotApi.getSceneData(meter.getGasMeterNumber(), meter.getGasCode(), customerCode);
            //已经有场景数据了
            if (Objects.nonNull(sceneData)) {
                //上一条是拆、换表 场景生成的抄表数据
                if (OrderSourceNameEnum.CENTER_RECHARGE.eq(oldVersion.getOrderSourceName())) {
                    //中心计费预付费抵扣退费
                    calculateApi.unSettlementIot(Collections.singletonList(sceneData));
                }
                //需要更新的字段
                sceneData.setUseGasTypeId(meter.getUseGasTypeId());
                sceneData.setUseGasTypeName(meter.getUseGasTypeName());
                sceneData.setDataTime(LocalDateTime.now());
                sceneData.setReadMeterMonth(LocalDateTime.now().getMonthValue());
                sceneData.setReadMeterYear(LocalDateTime.now().getYear());
                sceneData.setRecordTime(new Date());
                sceneData.setReadTime(new Date());
                sceneData.setCurrentTotalGas(meterEndGas);
                sceneData.setProcessStatus(ProcessIotEnum.APPROVED);
                sceneData.setIsCreateArrears(0);
                sceneData.setLastTotalGas(lastTotalGas);
                sceneData.setMonthUseGas(meterEndGas.subtract(sceneData.getLastTotalGas()));

                ReadMeterDataIotUpdateDTO update = BeanUtil.copyProperties(sceneData, ReadMeterDataIotUpdateDTO.class);
                readMeterDataIotApi.update(update);
                readMeterId = sceneData.getId();
                unCharge.add(sceneData);

            } else {

                ReadMeterDataIotSaveDTO dataMeter = new ReadMeterDataIotSaveDTO();
                dataMeter.setUseGasTypeId(meter.getUseGasTypeId());
                dataMeter.setUseGasTypeName(meter.getUseGasTypeName());
                dataMeter.setGasMeterCode(meter.getGasCode());
                dataMeter.setGasMeterNumber(meter.getGasMeterNumber());
                dataMeter.setGasMeterAddress(meter.getMoreGasMeterAddress());
                dataMeter.setCustomerCode(customerCode);
                dataMeter.setCustomerName(customerName);

                dataMeter.setDataTime(LocalDateTime.now());
                dataMeter.setReadMeterMonth(LocalDateTime.now().getMonthValue());
                dataMeter.setReadMeterYear(LocalDateTime.now().getYear());
                dataMeter.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
                dataMeter.setProcessStatus(ProcessIotEnum.APPROVED);
                dataMeter.setDataType(dataType.getCode());
                dataMeter.setRecordTime(new Date());
                dataMeter.setReadTime(new Date());
                dataMeter.setCurrentTotalGas(meterEndGas);
                dataMeter.setLastTotalGas(lastTotalGas);
                dataMeter.setIsCreateArrears(0);
                dataMeter.setIsFirst(0);
                dataMeter.setMonthUseGas(meterEndGas.subtract(dataMeter.getLastTotalGas()));
                dataMeter.setMeterType(oldVersion.getOrderSourceName());
                ReadMeterDataIot data = readMeterDataIotApi.save(dataMeter).getData();
                readMeterId = data.getId();
                unCharge.add(data);
            }
            List<ReadMeterDataIot> listR = calculateApi.settlementIot(unCharge, 1).getData();
            List<String> settlementNoList = listR.stream().map(ReadMeterDataIot::getGasOweCode).distinct().collect(Collectors.toList());

            result.setReadMeterDataId(readMeterId);
            if (OrderSourceNameEnum.REMOTE_READMETER.eq(oldVersion.getOrderSourceName())) {
                List<GasmeterArrearsDetail> data = gasmeterArrearsDetailBizApi.queryBySettlementNoList(settlementNoList);
                result.setArrearsDetails(data);
            }
        }
        return result;
    }

    /**
     * 中心计费预付费抄表数据结算
     *
     * @param customerCode 用户编号
     * @param customerName 用户名称
     * @param meterEndGas  表具止数
     * @param dataType     抄表类型
     * @param meter        表具信息
     */
    public void costSettlementCenterRecharge(String customerCode, String customerName, BigDecimal meterEndGas, DataTypeEnum dataType,
                                             GasMeter meter) {
        //查询未缴费的抄表数据
        List<ReadMeterDataIot> unCharge = readMeterDataIotApi.queryUnChargedDataIot(meter.getGasCode()).getData();
        if (CollectionUtils.isEmpty(unCharge)) {
            unCharge = new ArrayList<>();
        }
        //查询最新的上报数据  获取最新抄表止数
        ReadMeterDataIot previousData = readMeterDataIotApi.getPreviousData(meter.getGasCode(), meter.getGasMeterNumber(), customerCode, LocalDateTime.now());
        BigDecimal lastTotalGas;
        if (Objects.nonNull(previousData)) {
            //上一条是正常上报的抄表数据
            lastTotalGas = previousData.getCurrentTotalGas();
        } else {
            //还没有上报抄表数据
            lastTotalGas = meter.getGasMeterBase();
        }
        //获取场景数据
        ReadMeterDataIot sceneData = readMeterDataIotApi.getSceneData(meter.getGasMeterNumber(), meter.getGasCode(), customerCode);
        if (Objects.nonNull(sceneData)) {

            //抵扣退费
            calculateApi.unSettlementIot(Collections.singletonList(sceneData));

            //需要更新的字段
            sceneData.setUseGasTypeId(meter.getUseGasTypeId());
            sceneData.setUseGasTypeName(meter.getUseGasTypeName());
            sceneData.setDataTime(LocalDateTime.now());
            sceneData.setReadMeterMonth(LocalDateTime.now().getMonthValue());
            sceneData.setReadMeterYear(LocalDateTime.now().getYear());
            sceneData.setRecordTime(new Date());
            sceneData.setReadTime(new Date());
            sceneData.setCurrentTotalGas(meterEndGas);
            sceneData.setLastTotalGas(lastTotalGas);
            sceneData.setMonthUseGas(meterEndGas.subtract(sceneData.getLastTotalGas()));
            sceneData.setProcessStatus(ProcessIotEnum.APPROVED);
            sceneData.setIsCreateArrears(0);

            ReadMeterDataIotUpdateDTO update = BeanUtil.copyProperties(sceneData, ReadMeterDataIotUpdateDTO.class);
            readMeterDataIotApi.update(update);

            unCharge.add(sceneData);

        } else {

            ReadMeterDataIotSaveDTO dataMeter = new ReadMeterDataIotSaveDTO();
            dataMeter.setUseGasTypeId(meter.getUseGasTypeId());
            dataMeter.setUseGasTypeName(meter.getUseGasTypeName());
            dataMeter.setGasMeterCode(meter.getGasCode());
            dataMeter.setGasMeterNumber(meter.getGasMeterNumber());
            dataMeter.setGasMeterAddress(meter.getMoreGasMeterAddress());
            dataMeter.setCustomerCode(customerCode);
            dataMeter.setCustomerName(customerName);

            dataMeter.setDataTime(LocalDateTime.now());
            dataMeter.setReadMeterMonth(LocalDateTime.now().getMonthValue());
            dataMeter.setReadMeterYear(LocalDateTime.now().getYear());
            dataMeter.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            dataMeter.setProcessStatus(ProcessIotEnum.APPROVED);
            dataMeter.setDataType(dataType.getCode());
            dataMeter.setRecordTime(new Date());
            dataMeter.setReadTime(new Date());
            dataMeter.setCurrentTotalGas(meterEndGas);
            dataMeter.setLastTotalGas(lastTotalGas);
            dataMeter.setMeterType(OrderSourceNameEnum.CENTER_RECHARGE.getCode());
            dataMeter.setIsCreateArrears(0);
            dataMeter.setIsFirst(0);
            dataMeter.setMonthUseGas(meterEndGas.subtract(dataMeter.getLastTotalGas()));
            ReadMeterDataIot data = readMeterDataIotApi.save(dataMeter).getData();
            unCharge.add(data);
        }

        calculateApi.settlementIot(unCharge, 1);

    }


    /**
     * 中心计费后付费待结算抄表数据累加
     *
     * @param customerCode 用户编号
     * @param meter        表具信息
     * @param meterEndGas  表具止数
     */
    public BigDecimal countRemoteReadMeterData(String customerCode, GasMeter meter, BigDecimal meterEndGas) {
        BigDecimal result = BigDecimal.ZERO;
        //查询未缴费的抄表数据
        List<ReadMeterDataIot> unCharge = readMeterDataIotApi.queryUnChargedDataIot(meter.getGasCode()).getData();
        if (!CollectionUtils.isEmpty(unCharge)) {
            result = unCharge.stream().map(ReadMeterDataIot::getMonthUseGas).reduce(result, BigDecimal::add);
        }
        //查询最新的上报数据  获取最新抄表止数
        ReadMeterDataIot previousData = readMeterDataIotApi.getPreviousData(meter.getGasCode(), meter.getGasMeterNumber(), customerCode, LocalDateTime.now());
        BigDecimal lastTotalGas;
        if (Objects.nonNull(previousData)) {
            //上一条是正常上报的抄表数据
            lastTotalGas = previousData.getCurrentTotalGas();
        } else {
            //还没有上报抄表数据
            lastTotalGas = meter.getGasMeterBase();
        }
        result = result.add(meterEndGas.subtract(lastTotalGas));
        return result;
    }


}
