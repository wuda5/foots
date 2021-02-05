package com.cdqckj.gmis.bizcenter.temp.counter.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterBookBizService;
import com.cdqckj.gmis.bizcenter.temp.counter.component.CostSettlementComponent;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementResult;
import com.cdqckj.gmis.bizcenter.temp.counter.service.RemoveMeterService;
import com.cdqckj.gmis.biztemporary.RemoveMeterRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.charges.api.ChargeBizApi;
import com.cdqckj.gmis.charges.api.CustomerAccountBizApi;
import com.cdqckj.gmis.charges.api.CustomerSceneChargeOrderBizApi;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.dto.ChargeLoadDTO;
import com.cdqckj.gmis.charges.dto.ChargeLoadReqDTO;
import com.cdqckj.gmis.charges.dto.GenSceneOrderDTO;
import com.cdqckj.gmis.charges.dto.UpdateAccountParamDTO;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.charges.enums.AccountSerialSceneEnum;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.PayStatusEnum;
import com.cdqckj.gmis.common.enums.gasmeter.MeterCustomerRelatedOperTypeEnum;
import com.cdqckj.gmis.common.utils.BizCacheCodeRemove;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.vo.RemoveDeviceVO;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.enumeration.DataTypeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ReadMeterStatusEnum;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 拆表场景相关接口
 *
 * @author gmis
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class RemoveMeterServiceImpl implements RemoveMeterService {

    @Autowired
    private RemoveMeterRecordBizApi removeMeterRecordBizApi;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    private ChargeBizApi chargeBizApi;

    @Autowired
    private CustomerSceneChargeOrderBizApi customerSceneChargeOrderBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private ReadMeterDataApi readMeterDataApi;
    @Autowired
    private ReadMeterLatestRecordApi readMeterLatestRecordApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private GasMeterBookRecordApi gasMeterBookRecordApi;
    @Autowired
    private ReadMeterBookBizService readMeterBookService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private TollItemBizApi tollItemBizApi;
    @Autowired
    private CostSettlementComponent costSettlementComponent;
    @Autowired
    private CustomerAccountBizApi customerAccountBizApi;
    @Autowired
    private ReadMeterDataIotApi readMeterDataIotApi;
    @Autowired
    private GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    /**
     * 分页查询拆表列表
     *
     * @param pageDTO
     * @return
     */
    @Override
    public R<Page<RemoveMeterRecord>> pageRemoveRecord(PageParams<RemoveMeterRecordPageDTO> pageDTO) {
        return removeMeterRecordBizApi.page(pageDTO);
    }

    /**
     * 通过ID查询详情
     *
     * @param id
     * @return
     */
    @Override
    public R<RemoveMeterRecord> getRemoveRecord(Long id) {
        return removeMeterRecordBizApi.get(id);
    }

    /**
     * 新增拆表记录
     *
     * @param saveDTO
     * @return
     */
    @Override
    @Transactional
    @CodeNotLost
    public R<BusinessCheckResultDTO<RemoveMeterRecord>> add(RemoveMeterRecordSaveDTO saveDTO) {
        BusinessCheckResultDTO<RemoveMeterRecord> result = new BusinessCheckResultDTO();
        R<GasMeter> gasMeterResult = gasMeterBizApi.findGasMeterByCode(saveDTO.getMeterCode());
        if (gasMeterResult.getIsError() || Objects.isNull(gasMeterResult.getData())) {
            throw new BizException("表具档案查询失败，或表具不存在。");
        }
        GasMeter meter = gasMeterResult.getData();
        saveDTO.setMeterFactoryId(meter.getGasMeterFactoryId());
        saveDTO.setMeterModelId(meter.getGasMeterModelId());
        saveDTO.setMeterVersionId(meter.getGasMeterVersionId());
//        saveDTO.setRemoveMeterNo(BizCodeUtil.genTaskFlowCode(BizFCode.CB, BizCodeUtil.REMOVE_METER_RECORD_NO));
        saveDTO.setRemoveMeterNo(BizCodeNewUtil.getRemoveMeterCode());
        saveDTO.setRemoveDate(LocalDate.now());
        //获取表具版本信息
        GasMeterVersion version = gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();

        boolean reaMeterFlag = false;
        if (isReadMeter(version.getOrderSourceName())) {

            GasMeterInfo meterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(saveDTO.getMeterCode(), saveDTO.getCustomerCode()).getData();
            BigDecimal bigDecimal = judgeLastReadMeter(saveDTO, version, meterInfo);
            if (Objects.nonNull(bigDecimal)) {
                return R.fail(String.format("气表止数不能小于表具最新读数：%s！", bigDecimal.toString()));
            }

            //结算未上报的数据
            SettlementResult settlementResult = costSettlementComponent.costSettlement(saveDTO.getCustomerCode(), saveDTO.getCustomerName(),
                    saveDTO.getMeterEndGas(), DataTypeEnum.REMOVE_DATA, meter, version);
            saveDTO.setReadMeterId(settlementResult.getReadMeterDataId());
            String orderSourceName = version.getOrderSourceName();
            if (OrderSourceNameEnum.READMETER_PAY.eq(orderSourceName) || OrderSourceNameEnum.REMOTE_READMETER.eq(orderSourceName)) {
                if (!CollectionUtils.isEmpty(settlementResult.getArrearsDetails())) {
                    reaMeterFlag = true;
                    List<String> collect = settlementResult.getArrearsDetails().stream().map(temp -> temp.getId().toString()).collect(Collectors.toList());
                    saveDTO.setArrearsDetailIdList(collect.stream().collect(Collectors.joining(",", "", "")));
                    result.setArrearsDetailIds(collect);
                }
            }
            if (OrderSourceNameEnum.CENTER_RECHARGE.eq(orderSourceName)) {
                //查询户表余额， 判断是否收费
                GasMeterInfo checkInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(saveDTO.getMeterCode(), saveDTO.getCustomerCode()).getData();
                BigDecimal balance = checkInfo.getGasMeterBalance() == null ? BigDecimal.ZERO : checkInfo.getGasMeterBalance();
                if (BigDecimal.ZERO.compareTo(balance) > 0) {
                    reaMeterFlag = true;
                    saveDTO.setArrearsDetailId(checkInfo.getId());
                }
            }
        }
        //判断是否收费
        Boolean chargeFlag = tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.DIS_METER.getCode()).getData();

        if (Boolean.FALSE.equals(chargeFlag) && Boolean.FALSE.equals(reaMeterFlag)) {
            saveDTO.setStatus(PayStatusEnum.SUCCESS.getCode());
            saveDTO.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
            R<RemoveMeterRecord> saveResult = removeMeterRecordBizApi.save(saveDTO);
            if (saveResult.getIsError()) {
                throw new BizException("新增拆表记录失败");
            }
            //不收费直接更新数据
            updateCorrelationData(saveResult.getData(), version.getOrderSourceName());

            result.setStep(PayStatusEnum.SUCCESS.getCode());
            result.setNeedToPay(false);
            result.setBusData(saveResult.getData());
        } else {
            saveDTO.setStatus(PayStatusEnum.WAIT_PAY.getCode());
            saveDTO.setDeleteStatus(DeleteStatusEnum.DELETE.getValue());
            R<RemoveMeterRecord> saveResult = removeMeterRecordBizApi.save(saveDTO);
            if (saveResult.getIsError()) {
                throw new BizException("新增拆表记录失败");
            }
            if (Boolean.TRUE.equals(chargeFlag)) {
                RemoveMeterRecord record = saveResult.getData();
                customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                        .customerCode(record.getCustomerCode())
                        .gasMeterCode(record.getMeterCode())
                        .sceneCode(TolltemSceneEnum.DIS_METER.getCode())
                        .bizNoOrId(record.getId().toString()).build());
            }
            result.setStep(PayStatusEnum.WAIT_PAY.getCode());
            result.setFlag(true);
            result.setNeedToPay(true);
            result.setBusData(saveResult.getData());
        }
        return R.success(result);
    }

    /**
     * 校验旧表止数是否小于最新一次抄表读数
     *
     * @param saveDTO
     * @param version
     * @param meterInfo
     * @return
     */
    private BigDecimal judgeLastReadMeter(RemoveMeterRecordSaveDTO saveDTO, GasMeterVersion version, GasMeterInfo meterInfo) {
        BigDecimal oldReadData = BigDecimal.ZERO;
        log.info("开校验校验旧表止数是否小于最新一次抄表读数,表具类型:" + version.getOrderSourceName());
        if (OrderSourceNameEnum.READMETER_PAY.eq(version.getOrderSourceName())) {
            //校验旧表是否还有未上报数据
            ReadMeterLatestRecord readMeterLatestRecord = ReadMeterLatestRecord.builder()
                    .gasMeterCode(saveDTO.getMeterCode())
                    .build();
            ReadMeterLatestRecord latestReadMeter = readMeterLatestRecordApi.queryOne(readMeterLatestRecord).getData();
            if (Objects.nonNull(latestReadMeter) && Objects.nonNull(latestReadMeter.getCurrentTotalGas())) {
                oldReadData = latestReadMeter.getCurrentTotalGas();
            }

        }
        //物联网表 将止数和累计用气量比较  如果小于累计用气量 不能拆表
        if (OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())
                || OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())) {
            oldReadData = meterInfo.getMeterTotalGas() == null ? BigDecimal.ZERO : meterInfo.getMeterTotalGas();
        }
        if (saveDTO.getMeterEndGas().compareTo(oldReadData) < 0) {
            return oldReadData;
        }
        return null;
    }

    /**
     * 判断是否是有抄表数据的表
     *
     * @param orderSourceName
     * @return
     */
    private boolean isReadMeter(String orderSourceName) {
        return OrderSourceNameEnum.REMOTE_READMETER.eq(orderSourceName) || OrderSourceNameEnum.CENTER_RECHARGE.eq(orderSourceName)
                || OrderSourceNameEnum.READMETER_PAY.eq(orderSourceName);
    }

    /**
     * 拆表前检查：是否已有记录、是否欠费
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 检查结果
     */
    @Override
    public R<BusinessCheckResultDTO<RemoveMeterRecord>> check(String gasMeterCode, String customerCode) {
        R<GasMeter> gasMeterResult = gasMeterBizApi.findGasMeterByCode(gasMeterCode);
        if (gasMeterResult.getIsError()) {
            throw new BizException("表具档案查询失败，或表具不存在。");
        }
        if (Objects.isNull(gasMeterResult.getData())) {
            return R.fail(10033, "表具不存在");
        }
        if (GasMeterStatusEnum.Dismantle.getCode().equals(gasMeterResult.getData().getDataStatus())) {
            return R.fail(10034, "表具已拆除，请勿重复拆除。");
        }
        //获取表具版号
        GasMeterVersion version = gasMeterVersionBizApi.get(gasMeterResult.getData().getGasMeterVersionId()).getData();
        //如果是普表 需要校验是否有未交费的抄表数据
        if (OrderSourceNameEnum.READMETER_PAY.eq(version.getOrderSourceName())) {
            List<ReadMeterData> readMeterData = readMeterDataApi.queryUnChargedData(gasMeterCode).getData();
            if (!CollectionUtils.isEmpty(readMeterData)) {
                return R.fail(10032, "该表具存在还未缴费的抄表数据。");
            }
        }

        R<BusinessCheckResultDTO<RemoveMeterRecord>> check = removeMeterRecordBizApi.check(gasMeterCode, customerCode);
        if (check.getIsSuccess() && !check.getData().getFlag()) {
            ChargeLoadReqDTO chargeLoadReqDTO = ChargeLoadReqDTO.builder()
                    .customerCode(customerCode)
                    .gasMeterCode(gasMeterCode)
                    .build();
            R<ChargeLoadDTO> result = chargeBizApi.chargeLoad(chargeLoadReqDTO);
            if (result.getIsError()) {
                throw new BizException("待缴费数据查询失败");
            }
            ChargeLoadDTO data = result.getData();
            if (Objects.nonNull(data) && data.getTotalMoney().compareTo(BigDecimal.ZERO) > 0) {
                return R.fail(10032, "该表具存在欠费未结清。");
            }
        }
        return check;
    }

    /**
     * 支付完成后更新数据状态
     *
     * @param id 记录id
     * @return 更新结果
     */
    @Override
    @Transactional
    public R<Boolean> updateAfterPaid(Long id) {
        RemoveMeterRecord record = removeMeterRecordBizApi.get(id).getData();
        if (Objects.isNull(record) || PayStatusEnum.SUCCESS.getCode().equals(record.getStatus())) {
            return R.success();
        }

        RemoveMeterRecordUpdateDTO updateStatus = RemoveMeterRecordUpdateDTO.builder()
                .id(id)
                .deleteStatus(DeleteStatusEnum.NORMAL.getValue())
                .status(PayStatusEnum.SUCCESS.getCode())
                .build();
        R<RemoveMeterRecord> update = removeMeterRecordBizApi.update(updateStatus);
        if (update.getIsError()) {
            throw new BizException("更新记录状态失败");
        }
        GasMeterVersion version = gasMeterVersionBizApi.get(record.getMeterVersionId()).getData();
        updateCorrelationData(record, version.getOrderSourceName());
        return R.success();
    }

    /**
     * 支付完成后更新数据状态
     *
     * @param chargeNo 缴费编号
     * @return 更新结果
     */
    @Override
    public R<Boolean> updateAfterPaid(String chargeNo) {
        RemoveMeterRecord removeMeterRecord = removeMeterRecordBizApi.getOneByChargeNo(chargeNo).getData();
        if (Objects.isNull(removeMeterRecord) || PayStatusEnum.SUCCESS.getCode().equals(removeMeterRecord.getStatus())) {
            throw new BizException("数据不存在");
        }
        RemoveMeterRecordUpdateDTO updateStatus = RemoveMeterRecordUpdateDTO.builder()
                .id(removeMeterRecord.getId())
                .deleteStatus(DeleteStatusEnum.NORMAL.getValue())
                .status(PayStatusEnum.SUCCESS.getCode())
                .build();
        R<RemoveMeterRecord> update = removeMeterRecordBizApi.update(updateStatus);
        if (update.getIsError()) {
            throw new BizException("更新记录状态失败");
        }
        GasMeterVersion version = gasMeterVersionBizApi.get(removeMeterRecord.getMeterVersionId()).getData();
        updateCorrelationData(removeMeterRecord, version.getOrderSourceName());
        return R.success();
    }

    /**
     * 更新拆表相关数据
     *
     * @param record 拆表记录
     */
    private void updateCorrelationData(RemoveMeterRecord record, String orderSourceName) {
        //删除表和用户的关联关系
        CustomerGasMeterRelatedUpdateDTO updateRelated = CustomerGasMeterRelatedUpdateDTO.builder()
                .gasMeterCode(record.getMeterCode())
                .customerCode(record.getCustomerCode())
                .operType(MeterCustomerRelatedOperTypeEnum.REMOVE_METER.getCode())
                .build();
        customerGasMeterRelatedBizApi.deleteByCustomerAndMeter(updateRelated);

        GasMeterInfo oldMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(record.getMeterCode(), record.getCustomerCode()).getData();

        GasMeterInfoUpdateDTO updateDTO = GasMeterInfoUpdateDTO.builder()
                .id(oldMeterInfo.getId())
                .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                .build();
        if (!StringUtils.isEmpty(record.getArrearsDetailIdList())) {
            if (OrderSourceNameEnum.READMETER_PAY.getCode().equals(orderSourceName)) {
                List<Long> arrearsDetailIdList = Arrays.stream(record.getArrearsDetailIdList().split(","))
                        .map(Long::valueOf).collect(Collectors.toList());
                List<GasmeterArrearsDetail> arrearsDetail = gasmeterArrearsDetailBizApi.queryList(arrearsDetailIdList).getData();
                arrearsDetail.forEach(temp -> updateDTO.setCycleUseGas(bigDecimalAdd(oldMeterInfo.getCycleUseGas(), temp.getGas()))
                        .setTotalUseGas(bigDecimalAdd(oldMeterInfo.getTotalUseGas(), temp.getGas()))
                        .setTotalUseGasMoney(bigDecimalAdd(oldMeterInfo.getTotalUseGasMoney(), temp.getGasMoney())));
            }
            if (OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(orderSourceName)) {
                ReadMeterDataIot iotReadMeter = readMeterDataIotApi.getById(record.getReadMeterId()).getData();
                updateDTO.setCycleUseGas(bigDecimalAdd(oldMeterInfo.getCycleUseGas(), iotReadMeter.getMonthUseGas()))
                        .setTotalUseGas(bigDecimalAdd(oldMeterInfo.getTotalUseGas(), iotReadMeter.getMonthUseGas()))
                        .setTotalUseGasMoney(bigDecimalAdd(oldMeterInfo.getTotalUseGasMoney(), iotReadMeter.getUseMoney()));
            }
        }
        gasMeterInfoBizApi.update(updateDTO);

        String meterCode = record.getMeterCode();
        GasMeter data = gasMeterBizApi.findGasMeterByCode(meterCode).getData();
        GasMeter gasMeter = GasMeter.builder()
                .gasCode(meterCode)
                .dataStatus(GasMeterStatusEnum.Dismantle.getCode())
                .build();
        gasMeter.setRemoveGasmeter(data.getId().toString());
        gasMeterBizApi.updateByCode(gasMeter);
        //清除表身号缓存
        BizCacheCodeRemove.removeGasMeterNumber(record.getMeterFactoryId(), data.getGasMeterNumber());

        //如果是普表：删除抄表册关联关系
        if (OrderSourceNameEnum.READMETER_PAY.eq(orderSourceName)) {
            Long readMeterId = record.getReadMeterId();
            ReadMeterDataUpdateDTO readMeterData = ReadMeterDataUpdateDTO.builder()
                    .id(readMeterId)
                    .dataStatus(ReadMeterStatusEnum.READ.getCode())
                    .build();
            readMeterDataApi.update(readMeterData);

            log.info("移除抄表册中旧表数据");
            GasMeterBookRecord bookRecord = GasMeterBookRecord.builder()
                    .gasMeterCode(meterCode)
                    .customerCode(record.getCustomerCode())
                    .deleteStatus(DeleteStatusEnum.NORMAL.getValue())
                    .build();
            List<GasMeterBookRecord> query = gasMeterBookRecordApi.query(bookRecord).getData();
            if (!CollectionUtils.isEmpty(query)) {
                //抄表册中删除旧表
                List<Long> ids = query.stream().map(SuperEntity::getId).collect(Collectors.toList());
                readMeterBookService.deleteBookRecord(ids);
            }
        }

        //物联网表下发移除指令
        if (isIotMeter(orderSourceName)) {
            log.info("拆物联网表,下发移除指令。表具编号：" + meterCode);
            RemoveDeviceVO removeDeviceVO = new RemoveDeviceVO();
            removeDeviceVO.setGasMeterNumber(data.getGasMeterNumber());
            removeDeviceVO.setDeviceType(0);
            IotR iotR = deviceService.removeDevice(removeDeviceVO);
            log.info("拆物联网表,下发移除指令结果：" + iotR.getIsSuccess());
        }
        //如果是表端计费 需要把填写的表端余额补充到账户中 普表多抄数据也通过这个退回账户
        if (OrderSourceNameEnum.IC_RECHARGE.eq(orderSourceName) || OrderSourceNameEnum.REMOTE_RECHARGE.eq(orderSourceName)
                || OrderSourceNameEnum.READMETER_PAY.eq(orderSourceName)) {
            //针对普表如果拆表还在收费 那么理论上退回账户的金额是无效的
            if (!StringUtils.isEmpty(record.getArrearsDetailIdList())) {
                return;
            }
            BigDecimal inputBalance = record.getMeterBalance() == null ? BigDecimal.ZERO : record.getMeterBalance();
            if (BigDecimal.ZERO.compareTo(inputBalance) != 0) {
                UpdateAccountParamDTO updateAccountParam = UpdateAccountParamDTO.builder()
                        .customerCode(record.getCustomerCode())
                        .gasMeterCode(record.getMeterCode())
                        .accountMoney(inputBalance)
                        .billNo(record.getRemoveMeterNo())
                        .billType(AccountSerialSceneEnum.REMOVE_METER_SUPPLEMENT.getCode())
                        .build();
                customerAccountBizApi.updateAccountAfterChangeMeter(updateAccountParam);
            }
        }
        //如果是户表余额 需要把户表余额退回账户中
        if (OrderSourceNameEnum.CENTER_RECHARGE.eq(orderSourceName) || OrderSourceNameEnum.REMOTE_READMETER.eq(orderSourceName)) {
            if (Objects.nonNull(oldMeterInfo.getGasMeterBalance()) && BigDecimal.ZERO.compareTo(oldMeterInfo.getGasMeterBalance()) != 0) {
                UpdateAccountParamDTO updateAccountParam = UpdateAccountParamDTO.builder()
                        .customerCode(record.getCustomerCode())
                        .gasMeterCode(record.getMeterCode())
                        .accountMoney(oldMeterInfo.getGasMeterBalance())
                        .billNo(record.getRemoveMeterNo())
                        .billType(AccountSerialSceneEnum.REMOVE_METER_SUPPLEMENT.getCode())
                        .build();
                customerAccountBizApi.updateAccountAfterChangeMeter(updateAccountParam);
            }
        }
    }

    /**
     * 是否是物理联网表
     *
     * @param meterType 表具类型
     * @return 是:true;否：false
     */
    private boolean isIotMeter(String meterType) {
        return OrderSourceNameEnum.CENTER_RECHARGE.eq(meterType) || OrderSourceNameEnum.REMOTE_RECHARGE.eq(meterType)
                || OrderSourceNameEnum.REMOTE_READMETER.eq(meterType);
    }

    /**
     * 支付回滚
     *
     * @param id 记录id
     * @return 回滚结果
     */
    @Override
    public R<Boolean> rollbackPaid(Long id) {
        R<RemoveMeterRecord> removeMeterRecordR = removeMeterRecordBizApi.get(id);
        if (removeMeterRecordR.getIsError()) {
            throw new BizException("数据不存在");
        }
        RemoveMeterRecord record = removeMeterRecordR.getData();

        RemoveMeterRecordUpdateDTO updateStatus = RemoveMeterRecordUpdateDTO.builder()
                .id(id)
                .status(PayStatusEnum.WAIT_PAY.getCode())
                .build();
        R<RemoveMeterRecord> update = removeMeterRecordBizApi.update(updateStatus);
        //TODO 业务数据回滚 需要调整表结构 记录表具原状态


        return R.success();
    }

    private BigDecimal bigDecimalAdd(BigDecimal a, BigDecimal b) {
        if (Objects.isNull(a)) {
            a = BigDecimal.ZERO;
        }
        if (Objects.isNull(b)) {
            b = BigDecimal.ZERO;
        }
        return a.add(b);
    }

}
