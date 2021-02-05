package com.cdqckj.gmis.bizcenter.temp.counter.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.*;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterArchiveService;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterBookBizService;
import com.cdqckj.gmis.bizcenter.temp.counter.component.ClearMeterComponent;
import com.cdqckj.gmis.bizcenter.temp.counter.component.CostSettlementComponent;
import com.cdqckj.gmis.bizcenter.temp.counter.component.IotOpenAccountComponent;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementResult;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementReturnParam;
import com.cdqckj.gmis.bizcenter.temp.counter.service.ChangeMeterService;
import com.cdqckj.gmis.biztemporary.ChangeMeterRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.enums.CardOperateEnum;
import com.cdqckj.gmis.biztemporary.enums.ChangeModeEnum;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
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
import com.cdqckj.gmis.common.enums.ConversionType;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.PayStatusEnum;
import com.cdqckj.gmis.common.enums.gasmeter.MeterCustomerRelatedOperTypeEnum;
import com.cdqckj.gmis.common.utils.BizCacheCodeRemove;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.iot.qc.vo.OpenAccountVO;
import com.cdqckj.gmis.iot.qc.vo.PriceVO;
import com.cdqckj.gmis.iot.qc.vo.RemoveDeviceVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateBalanceVO;
import com.cdqckj.gmis.lot.DeviceBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordSaveDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.enumeration.DataTypeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ReadMeterStatusEnum;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 换表场景 相关接口
 *
 * @author gmis
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ChangeMeterServiceImpl implements ChangeMeterService {

    @Autowired
    private ChangeMeterRecordBizApi changeMeterRecordBizApi;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private GasMeterArchiveService gasMeterArchiveService;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;

    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    private ChargeBizApi chargeBizApi;
    @Autowired
    private CustomerSceneChargeOrderBizApi customerSceneChargeOrderBizApi;
    @Autowired
    private I18nUtil i18nUtil;
    @Autowired
    private CustomerAccountBizApi customerAccountBizApi;
    @Autowired
    private CalculateApi calculateApi;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private DeviceBizApi deviceBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private ReadMeterBookBizService readMeterBookService;
    @Autowired
    private ReadMeterDataApi readMeterDataApi;
    @Autowired
    private GasMeterBookRecordApi gasMeterBookRecordApi;
    @Autowired
    private ReadMeterLatestRecordApi readMeterLatestRecordApi;
    @Autowired
    private TollItemBizApi tollItemBizApi;
    @Autowired
    private IotOpenAccountComponent iotOpenAccountComponent;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ClearMeterComponent clearMeterComponent;
    @Autowired
    private CostSettlementComponent costSettlementComponent;
    @Autowired
    private ReadMeterDataIotApi readMeterDataIotApi;
    @Autowired
    private GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    /**
     * 分页查询换表申请列表
     *
     * @param pageDTO
     * @return
     */
    @Override
    public R<Page<ChangeMeterRecord>> pageChangeRecord(PageParams<ChangeMeterRecordPageDTO> pageDTO) {
        return changeMeterRecordBizApi.page(pageDTO);
    }

    /**
     * 通过ID查询详情
     *
     * @param id
     * @return
     */
    @Override
    public R<ChangeMeterRecord> getChangeRecord(Long id) {
        return changeMeterRecordBizApi.get(id);
    }

    /**
     * 新增换表申请数据包含新表信息
     *
     * @param saveDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CodeNotLost
    public R<BusinessCheckResultDTO<ChangeMeterRecord>> add(ChangeMeterRecordSaveDTO saveDTO) {
        BusinessCheckResultDTO<ChangeMeterRecord> result = new BusinessCheckResultDTO();
        //获取新旧表数据
        GasMeter oldMeter = gasMeterBizApi.findGasMeterByCode(saveDTO.getOldMeterCode()).getData();
        //获取表具版本信息
        GasMeterVersion oldVersion = gasMeterVersionBizApi.get(oldMeter.getGasMeterVersionId()).getData();
        String oldSourceName = oldVersion.getOrderSourceName();
        if (ChangeModeEnum.CHANGE_ZERO.eq(saveDTO.getChangeMode())) {
            //物联网表清零
            if (!isIotMeter(oldVersion.getOrderSourceName())) {
                return R.fail("目前只支持物联网表旧表清零！");
            }
            //判断是否收费
            Boolean chargeFlag = tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.CHANGE_METER.getCode()).getData();
            if (Boolean.TRUE.equals(chargeFlag)) {
                saveDTO.setDeleteStatus(DeleteStatusEnum.DELETE.getValue());
                R<ChangeMeterRecord> saveResult = saveChangeRecord(saveDTO, oldMeter, oldMeter, oldVersion, oldVersion);
                ChangeMeterRecord record = saveResult.getData();
                customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                        .customerCode(record.getCustomerCode())
                        .gasMeterCode(record.getOldMeterCode())
                        .sceneCode(TolltemSceneEnum.CHANGE_METER.getCode())
                        .bizNoOrId(record.getId().toString()).build());
                result.setStep(PayStatusEnum.WAIT_PAY.getCode());
                result.setNeedToPay(true);
                result.setBusData(saveResult.getData());
            } else {
                //调用换表记录新增服务
                saveDTO.setStatus(PayStatusEnum.SUCCESS.getCode());
                saveDTO.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
                R<ChangeMeterRecord> saveResult = saveChangeRecord(saveDTO, oldMeter, oldMeter, oldVersion, oldVersion);
                clearMeterComponent.clearMeter(saveResult.getData());

                result.setStep(PayStatusEnum.SUCCESS.getCode());
                result.setNeedToPay(false);
                result.setBusData(saveResult.getData());
            }

        } else {
            GasMeterVersion newVersion = gasMeterVersionBizApi.get(saveDTO.getNewMeterVersionId()).getData();
            //校验：物联网中心计费不能换成表端计费
            String judge = judgeMeterType(oldVersion, newVersion);
            if (!StringUtils.isEmpty(judge)) {
                return R.fail(judge);
            }

            GasMeterInfo oldMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(saveDTO.getOldMeterCode(), saveDTO.getCustomerCode()).getData();
            BigDecimal bigDecimal = judgeLastReadMeter(saveDTO, oldMeter, oldVersion, oldMeterInfo);
            if (Objects.nonNull(bigDecimal)) {
                return R.fail(String.format("旧表止数不能小于表具最新读数：%s！", bigDecimal.toString()));
            }

            if (!judgeSupplement(saveDTO, oldVersion)) {
                return R.fail("补充气量或补充金额换算气量后不能大于旧表止数！");
            }

            GasMeter newMeter = gasMeterBizApi.findEffectiveMeterByNumber(saveDTO.getNewMeterNumber()).getData();
            if (Objects.isNull(newMeter)) {
                //如果没有查询到数据,则新表还未录入系统,走表具入库操作
                newMeter = addGasMeter(saveDTO);
            } else {
                //校验新表是否开户
                CustomerGasMeterRelatedUpdateDTO checkParam = CustomerGasMeterRelatedUpdateDTO.builder()
                        .gasMeterCode(newMeter.getGasCode())
                        .build();
                Boolean checkResult = customerGasMeterRelatedBizApi.check(checkParam).getData();
                if (Boolean.TRUE.equals(checkResult)) {
                    return R.fail("所选表具已开户，请选择其他表具。");
                }
            }
            //判断是否收费
            boolean reaMeterFlag = false;
            if (OrderSourceNameEnum.READMETER_PAY.eq(oldSourceName) || OrderSourceNameEnum.REMOTE_READMETER.eq(oldSourceName)) {
                //结算未上报的数据
                SettlementResult settlementResult = costSettlementComponent.costSettlement(saveDTO.getCustomerCode(), saveDTO.getCustomerName(),
                        saveDTO.getOldMeterEndGas(), DataTypeEnum.CHANGE_DATA, oldMeter, oldVersion);
                saveDTO.setReadMeterId(settlementResult.getReadMeterDataId());
                if (!CollectionUtils.isEmpty(settlementResult.getArrearsDetails())) {
                    reaMeterFlag = true;
                    List<String> collect = settlementResult.getArrearsDetails().stream().map(temp -> temp.getId().toString()).collect(Collectors.toList());
                    saveDTO.setArrearsDetailIdList(collect.stream().collect(Collectors.joining(",", "", "")));
                    result.setArrearsDetailIds(collect);
                }
            }

            if (OrderSourceNameEnum.CENTER_RECHARGE.eq(oldSourceName)) {
                //查询户表余额， 判断是否收费
                GasMeterInfo checkInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(saveDTO.getOldMeterCode(), saveDTO.getCustomerCode()).getData();
                if (BigDecimal.ZERO.compareTo(checkInfo.getGasMeterBalance()) > 0) {
                    reaMeterFlag = true;
                    saveDTO.setSupplementAmount(BigDecimal.ZERO);
                } else {
                    saveDTO.setSupplementAmount(checkInfo.getGasMeterBalance());
                }
            }

            Boolean chargeFlag = tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.CHANGE_METER.getCode()).getData();
            if (Boolean.TRUE.equals(chargeFlag) || reaMeterFlag) {
                //调用换表记录新增服务
                saveDTO.setDeleteStatus(DeleteStatusEnum.DELETE.getValue());
                R<ChangeMeterRecord> saveResult = saveChangeRecord(saveDTO, newMeter, oldMeter, oldVersion, newVersion);
                if (Boolean.TRUE.equals(saveResult.getIsError())) {
                    throw new BizException("新增记录失败");
                }
                if (Boolean.TRUE.equals(chargeFlag)) {
                    //新增场景缴费记录
                    ChangeMeterRecord record = saveResult.getData();
                    customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                            .customerCode(record.getCustomerCode())
                            .gasMeterCode(record.getOldMeterCode())
                            .sceneCode(TolltemSceneEnum.CHANGE_METER.getCode())
                            .bizNoOrId(record.getId().toString()).build());

                }
                result.setStep(PayStatusEnum.WAIT_PAY.getCode());
                result.setNeedToPay(true);
                result.setBusData(saveResult.getData());
            } else {
                //调用换表记录新增服务
                saveDTO.setStatus(PayStatusEnum.SUCCESS.getCode());
                saveDTO.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
                R<ChangeMeterRecord> saveResult = saveChangeRecord(saveDTO, newMeter, oldMeter, oldVersion, newVersion);
                if (Boolean.TRUE.equals(saveResult.getIsError())) {
                    throw new BizException("新增记录失败");
                }
                updateCorrelationData(saveResult.getData());
                result.setStep(PayStatusEnum.SUCCESS.getCode());
                result.setNeedToPay(false);
                result.setBusData(saveResult.getData());
            }
        }

        return R.success(result);
    }

    /**
     * 校验补充金额或补充气量
     *
     * @param saveDTO
     * @param oldVersion
     * @return
     */
    private boolean judgeSupplement(ChangeMeterRecordSaveDTO saveDTO, GasMeterVersion oldVersion) {
        String orderSourceName = oldVersion.getOrderSourceName();
        //普表补气量或补充金额换算后 不能大于旧表止数
        if (OrderSourceNameEnum.READMETER_PAY.eq(orderSourceName)) {
            BigDecimal endGas = saveDTO.getOldMeterEndGas();

            BigDecimal supplementGas = saveDTO.getSupplementGas();
            if (Objects.nonNull(supplementGas) && supplementGas.compareTo(BigDecimal.ZERO) > 0) {
                if (supplementGas.compareTo(endGas) > 0) {
                    return false;
                }
            }
            //调整后旧表是普表 一定会有补充其量  不用校验补充金额了
//            BigDecimal supplementAmount = saveDTO.getSupplementAmount();
//            if (Objects.nonNull(supplementAmount) && supplementAmount.compareTo(BigDecimal.ZERO) > 0) {
//                ConversionVO conversionVO = ConversionVO.builder()
//                        .gasMeterCode(saveDTO.getOldMeterCode())
//                        .conversionValue(supplementAmount)
//                        .conversionType(ConversionType.MONEY)
//                        .useGasTypeId(oldGasMeter.getUseGasTypeId())
//                        .build();
//                BigDecimal conversionGas = calculateApi.conversion(conversionVO).getData();
//                if (conversionGas.compareTo(endGas) > 0) {
//                    return false;
//                }
//            }
        }
        return true;
    }

    /**
     * 根据表具类型判断能否换表
     *
     * @param oldVersion 旧表类型
     * @param newVersion 新表类型
     * @return true 可以换表； false 不能换表
     */
    private String judgeMeterType(GasMeterVersion oldVersion, GasMeterVersion newVersion) {
        String oldType = oldVersion.getOrderSourceName();
        String newType = newVersion.getOrderSourceName();
        //旧表是中心计费表
        if (OrderSourceNameEnum.CENTER_RECHARGE.eq(oldType) || OrderSourceNameEnum.REMOTE_READMETER.eq(oldType)) {
            //新表是表端计费
            if (OrderSourceNameEnum.IC_RECHARGE.eq(newType) || OrderSourceNameEnum.REMOTE_RECHARGE.eq(newType)) {
                //不能换表
                return "物联网中心计费不能换成表端计费！";
            }
        }
//        if (OrderSourceNameEnum.IC_RECHARGE.eq(oldType) && OrderSourceNameEnum.READMETER_PAY.eq(newType)) {
//            return "卡表不能换成普表！";
//        }
//        if (isIotMeter(oldType) && (OrderSourceNameEnum.READMETER_PAY.eq(newType) || OrderSourceNameEnum.IC_RECHARGE.eq(newType))) {
//            return "物联网表不能换成普表或卡表！";
//        }
        return null;
    }

    /**
     * 校验旧表止数是否小于最新一次抄表读数
     *
     * @param saveDTO
     * @param oldVersion
     * @return
     */
    private BigDecimal judgeLastReadMeter(ChangeMeterRecordSaveDTO saveDTO, GasMeter oldGasMeter, GasMeterVersion oldVersion, GasMeterInfo meterInfo) {
        BigDecimal oldReadData = BigDecimal.ZERO;
        log.info("校验旧表读数是否有未上报的数据,表具类型:" + oldVersion.getOrderSourceName());
        if (OrderSourceNameEnum.READMETER_PAY.eq(oldVersion.getOrderSourceName())) {
            //校验旧表是否还有未上报数据
            ReadMeterLatestRecord readMeterLatestRecord = ReadMeterLatestRecord.builder()
                    .gasMeterCode(saveDTO.getOldMeterCode())
                    .build();
            ReadMeterLatestRecord latestReadMeter = readMeterLatestRecordApi.queryOne(readMeterLatestRecord).getData();
            if (Objects.nonNull(latestReadMeter) && Objects.nonNull(latestReadMeter.getCurrentTotalGas())) {
                oldReadData = latestReadMeter.getCurrentTotalGas();
            } else {
                oldReadData = oldGasMeter.getGasMeterBase() == null ? BigDecimal.ZERO : oldGasMeter.getGasMeterBase();
            }
        }
        if (OrderSourceNameEnum.REMOTE_READMETER.eq(oldVersion.getOrderSourceName()) || OrderSourceNameEnum.CENTER_RECHARGE.eq(oldVersion.getOrderSourceName())) {
            oldReadData = meterInfo.getMeterTotalGas() == null ? BigDecimal.ZERO : meterInfo.getMeterTotalGas();
        }
        if (saveDTO.getOldMeterEndGas().compareTo(oldReadData) < 0) {
            return oldReadData;
        }
        return null;
    }

    /**
     * 组装参数调用新增接口
     */
    private R<ChangeMeterRecord> saveChangeRecord(ChangeMeterRecordSaveDTO saveDTO, GasMeter newMeter, GasMeter oldMeter,
                                                  GasMeterVersion oldVersion, GasMeterVersion newVersion) {
        saveDTO.setNewMeterCode(newMeter.getGasCode());
        saveDTO.setNewMeterFactoryId(newMeter.getGasMeterFactoryId());
        saveDTO.setNewMeterModelId(newMeter.getGasMeterModelId());
        saveDTO.setNewMeterVersionId(newMeter.getGasMeterVersionId());
        saveDTO.setChangeDate(LocalDate.now());
        saveDTO.setOldMeterType(oldVersion.getOrderSourceName());
        saveDTO.setOldMeterNumber(oldMeter.getGasMeterNumber());
        saveDTO.setOldAmountMark(oldVersion.getAmountMark());
        saveDTO.setNewMeterType(newVersion.getOrderSourceName());
        saveDTO.setNewAmountMark(newVersion.getAmountMark());
        if (OrderSourceNameEnum.IC_RECHARGE.eq(newVersion.getOrderSourceName())) {
            saveDTO.setCardOperate(CardOperateEnum.WAIT_WRITE_CARD.getCode());
        }
//        saveDTO.setChangeMeterNo(BizCodeUtil.genTaskFlowCode(BizFCode.HB, BizCodeUtil.CHANGE_METER_RECORD_NO));
        saveDTO.setChangeMeterNo(BizCodeNewUtil.getChangeGasMeterCode());
        return changeMeterRecordBizApi.addWithMeterInfo(saveDTO);
    }

    /**
     * 新增前数据校验
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 检查结果
     */
    @Override
    public R<BusinessCheckResultDTO<ChangeMeterRecord>> check(String gasMeterCode, String customerCode) {
        R<GasMeter> oldMeterResult = gasMeterBizApi.findGasMeterByCode(gasMeterCode);
        if (Boolean.TRUE.equals(oldMeterResult.getIsError())) {
            throw new BizException(i18nUtil.getMessage(MessageConstants.G_ERROR_SERVICE));
        }
        if (Objects.isNull(oldMeterResult.getData())) {
            return R.fail(10033, i18nUtil.getMessage(MessageConstants.G_METER_NOT));
        }
        //获取表具版号
        GasMeterVersion version = gasMeterVersionBizApi.get(oldMeterResult.getData().getGasMeterVersionId()).getData();
        //如果是普表 需要校验是否有未交费的抄表数据
        if (OrderSourceNameEnum.READMETER_PAY.eq(version.getOrderSourceName())) {
            List<ReadMeterData> readMeterData = readMeterDataApi.queryUnChargedData(gasMeterCode).getData();
            if (!CollectionUtils.isEmpty(readMeterData)) {
                return R.fail(10032, "该表具存在还未缴费的抄表数据。");
            }
        }

        R<BusinessCheckResultDTO<ChangeMeterRecord>> check = changeMeterRecordBizApi.check(gasMeterCode, customerCode);
        if (Boolean.TRUE.equals(check.getIsSuccess()) && Boolean.FALSE.equals(check.getData().getFlag())) {
            ChargeLoadReqDTO chargeLoadReqDTO = ChargeLoadReqDTO.builder()
                    .customerCode(customerCode)
                    .gasMeterCode(gasMeterCode)
                    .build();
            R<ChargeLoadDTO> result = chargeBizApi.chargeLoad(chargeLoadReqDTO);
            if (Boolean.TRUE.equals(result.getIsError())) {
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
     * @return R<Boolean>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> updateAfterPaid(Long id) {
        ChangeMeterRecord record = changeMeterRecordBizApi.get(id).getData();
        if (Objects.isNull(record) || PayStatusEnum.SUCCESS.getCode().equals(record.getStatus())) {
            return R.success();
        }
        ChangeMeterRecordUpdateDTO updateStatus = ChangeMeterRecordUpdateDTO.builder()
                .id(id)
                .deleteStatus(DeleteStatusEnum.NORMAL.getValue())
                .status(PayStatusEnum.SUCCESS.getCode())
                .build();
        R<ChangeMeterRecord> update = changeMeterRecordBizApi.update(updateStatus);
        if (Boolean.TRUE.equals(update.getIsError())) {
            log.error("更新换表记录失败：" + id);
            throw new BizException("更新换表记录失败：" + id);
        }

        if (ChangeModeEnum.CHANGE_ZERO.eq(record.getChangeMode())) {
            clearMeterComponent.clearMeter(record);
        } else {
            updateCorrelationData(record);
        }
        return R.success();
    }

    /**
     * 支付完成后更新数据状态
     *
     * @param chargeNo 记录id
     * @return
     */
    @Override
    public R<Boolean> updateAfterPaid(String chargeNo) {
        ChangeMeterRecord changeMeter = changeMeterRecordBizApi.getOneByChargeNo(chargeNo).getData();
        if (Objects.isNull(changeMeter)) {
            throw new BizException("数据不存在");
        }
        ChangeMeterRecordUpdateDTO record = ChangeMeterRecordUpdateDTO.builder()
                .status(PayStatusEnum.SUCCESS.getCode())
                .deleteStatus(DeleteStatusEnum.NORMAL.getValue())
                .id(changeMeter.getId())
                .build();
        changeMeterRecordBizApi.update(record);
        if (ChangeModeEnum.CHANGE_ZERO.eq(changeMeter.getChangeMode())) {
            clearMeterComponent.clearMeter(changeMeter);
        } else {
            updateCorrelationData(changeMeter);
        }
        return R.success();
    }

    /**
     * 换表完成后更新关联数据
     *
     * @param data 换表记录
     */
    private void updateCorrelationData(ChangeMeterRecord data) {
        //更新新表字段
        GasMeter oldGasMeter = gasMeterBizApi.findGasMeterByCode(data.getOldMeterCode()).getData();

        GasMeter newGasMeter = updateMeterInstallData(data, oldGasMeter);

        //更新旧表状态、旧表和用户的关联关系
        GasMeter update = GasMeter.builder()
                .gasCode(data.getOldMeterCode())
                .dataStatus(GasMeterStatusEnum.Dismantle.getCode())
                .build();
        update.setRemoveGasmeter(oldGasMeter.getId().toString());
        gasMeterBizApi.updateByCode(update);
        //清除表身号缓存
        BizCacheCodeRemove.removeGasMeterNumber(oldGasMeter.getGasMeterFactoryId(), oldGasMeter.getGasMeterNumber());
        //旧表和用户的关联关系
        CustomerGasMeterRelatedUpdateDTO updateRelated = CustomerGasMeterRelatedUpdateDTO.builder()
                .gasMeterCode(data.getOldMeterCode())
                .customerCode(data.getCustomerCode())
                .operType(MeterCustomerRelatedOperTypeEnum.CHANGE_METER.getCode())
                .build();
        CustomerGasMeterRelated oldRelation = customerGasMeterRelatedBizApi.deleteByCustomerAndMeter(updateRelated).getData();

        //新增新表与客户关联关系
        CustomerGasMeterRelatedSaveDTO gasMeterRelated = CustomerGasMeterRelatedSaveDTO.builder()
                .customerCode(data.getCustomerCode())
                .gasMeterCode(data.getNewMeterCode())
                .customerChargeFlag(oldRelation.getCustomerChargeFlag())
                .operType(MeterCustomerRelatedOperTypeEnum.CHANGE_METER.getCode())
                .customerChargeNo(oldRelation.getCustomerChargeNo())
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .build();
        customerGasMeterRelatedBizApi.save(gasMeterRelated);

        BigDecimal rechargeAmount = chargeNumber(data, newGasMeter.getUseGasTypeId());

        //更新表具使用数据  删除旧的 新增新的
        createNewMeterUseInfo(data, rechargeAmount);

        //新表是物联网表下发开户指令
        if (isIotMeter(data.getNewMeterType())) {
            //开户指令下发
            iotOpenAccount(data.getCustomerCode(), newGasMeter, rechargeAmount);
            //物联网表中心计费下发余额更新指令
            if ((OrderSourceNameEnum.CENTER_RECHARGE.eq(data.getNewMeterType()) || OrderSourceNameEnum.REMOTE_READMETER.eq(data.getNewMeterType()))
                    && BigDecimal.ZERO.compareTo(rechargeAmount) != 0) {
                iotUpdateBalance(newGasMeter, rechargeAmount);
            }
        }

        if (isIotMeter(data.getOldMeterType())) {
            log.info("拆物联网表,下发移除指令。表具编号：" + data.getOldMeterCode());
            RemoveDeviceVO removeDeviceVO = new RemoveDeviceVO();
            removeDeviceVO.setGasMeterNumber(oldGasMeter.getGasMeterNumber());
            removeDeviceVO.setDeviceType(0);
            IotR iotR = deviceService.removeDevice(removeDeviceVO);
            log.info("拆物联网表,下发移除指令结果：" + iotR.getIsSuccess());
        }
        //更新账户
        updateCustomerAccount(data, newGasMeter.getUseGasTypeId());
        //更新抄表册
        updateReadMeterBook(data, newGasMeter);
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
     * 更新抄表册数据
     *
     * @param changeMeterRecord 换表数据
     */
    private void updateReadMeterBook(ChangeMeterRecord changeMeterRecord, GasMeter newGasMeter) {
        //如果旧表是普表:查询抄表册是否有数据。有则删除数据
        GasMeterBookRecord oldBook = null;
        if (OrderSourceNameEnum.READMETER_PAY.eq(changeMeterRecord.getOldMeterType())) {
            Long readMeterId = changeMeterRecord.getReadMeterId();
            ReadMeterDataUpdateDTO readMeterData = ReadMeterDataUpdateDTO.builder()
                    .id(readMeterId)
                    .dataStatus(ReadMeterStatusEnum.READ.getCode())
                    .build();
            readMeterDataApi.update(readMeterData);

            GasMeterBookRecord record = GasMeterBookRecord.builder()
                    .gasMeterCode(changeMeterRecord.getOldMeterCode())
                    .customerCode(changeMeterRecord.getCustomerCode())
                    .deleteStatus(DeleteStatusEnum.NORMAL.getValue())
                    .build();
            List<GasMeterBookRecord> query = gasMeterBookRecordApi.query(record).getData();
            if (!CollectionUtils.isEmpty(query)) {
                //抄表册中删除旧表
                oldBook = query.get(0);
                List<Long> ids = query.stream().map(SuperEntity::getId).collect(Collectors.toList());
                readMeterBookService.deleteBookRecord(ids);
                log.info("移除抄表册中旧表数据");
            }
        }
        if (OrderSourceNameEnum.READMETER_PAY.eq(changeMeterRecord.getNewMeterType())) {
            if (Objects.isNull(oldBook)) {
                initMeterLatest(changeMeterRecord, newGasMeter);
            } else {
                //新表也是普表则在抄表册中新增新表  数据和原来的一样 只有表具信息调整
                GasMeterBookRecordSaveDTO saveDTO = BeanUtil.copyProperties(oldBook, GasMeterBookRecordSaveDTO.class);
                saveDTO.setGasMeterCode(changeMeterRecord.getNewMeterCode());
                saveDTO.setGasMeterType(changeMeterRecord.getNewMeterType());
                saveDTO.setGasMeterNumber(changeMeterRecord.getNewMeterNumber());
                readMeterBookService.saveBookRecord(Collections.singletonList(saveDTO));
                log.info("新增抄表册中新表表数据");
            }
        }
    }

    /**
     * 普表初始化抄表数据
     *
     * @param changeMeterRecord
     * @param newGasMeter
     */
    private void initMeterLatest(ChangeMeterRecord changeMeterRecord, GasMeter newGasMeter) {
        String gasCode = changeMeterRecord.getNewMeterCode();
        List<ReadMeterLatestRecord> latestRecords = readMeterLatestRecordApi.queryListByGasCodes(Collections.singletonList(gasCode)).getData();
        if (CollectionUtils.isEmpty(latestRecords)) {
            ReadMeterLatestRecordSaveDTO save = new ReadMeterLatestRecordSaveDTO();
            save.setYear(2000).setMonth(1);// 还得 设置此 表具初始的年月（2000.01） 和 --》底数（从表具档案获得）
            save.setReadTime(DateUtils.getDate8(2000, 1));
            BigDecimal gasMeterBase = changeMeterRecord.getNewMeterStartGas();
            save.setCurrentTotalGas(gasMeterBase != null ? gasMeterBase : BigDecimal.ZERO);
            save.setCustomerCode(changeMeterRecord.getCustomerCode()).setCustomerName(changeMeterRecord.getCustomerName())
                    .setGasMeterCode(gasCode).setGasMeterNumber(newGasMeter.getGasMeterNumber());
            readMeterLatestRecordApi.save(save);
        }
    }

    /**
     * 物联网表下发开户指令
     *
     * @param customerCode   客户编号
     * @param gasMeter       表具信息
     * @param rechargeAmount 充值金额
     */
    private void iotOpenAccount(String customerCode, GasMeter gasMeter, BigDecimal rechargeAmount) {
        log.info("换物联网表下发开户指令：" + gasMeter.getGasCode());
        List<String> meterList = new ArrayList<>();
        meterList.add(gasMeter.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        deviceBizApi.meterCache(meterCacheVO);

        //查询气价配置信息
        List<PriceScheme> priceSchemeList = priceSchemeBizApi.queryRecord(gasMeter.getUseGasTypeId());
        PriceScheme priceScheme = priceSchemeList.get(0);
        UseGasType useGasType = useGasTypeBizApi.get(gasMeter.getUseGasTypeId()).getData();

        OpenAccountVO openAccountVO = new OpenAccountVO();
        openAccountVO.setGasMeterNumber(gasMeter.getGasMeterNumber());
        openAccountVO.setUseGasTypeId(gasMeter.getUseGasTypeId());
        openAccountVO.setUseGasTypeName(gasMeter.getUseGasTypeName());
        openAccountVO.setUseGasTypeNum(useGasType.getUseTypeNum());
        openAccountVO.setCycle(priceScheme.getCycle());
        openAccountVO.setEndTime(priceScheme.getEndTime());
        openAccountVO.setIsClear(priceScheme.getPriceChangeIsClear());
        openAccountVO.setPriceChangeIsClear(priceScheme.getPriceChangeIsClear());
        openAccountVO.setPriceId(priceScheme.getId());
        openAccountVO.setPriceNum(priceScheme.getPriceNum());
        openAccountVO.setSettlementDay(priceScheme.getSettlementDay());
        openAccountVO.setStartMonth(priceScheme.getStartMonth());
        openAccountVO.setStartTime(priceScheme.getStartTime());
        openAccountVO.setRechargeAmount(rechargeAmount.doubleValue());
        openAccountVO.setRechargeTimes(1);
        if (useGasType.getAlarmGas() != null) {
            openAccountVO.setAlarmAmount(useGasType.getAlarmGas().intValue());
        }
        List<PriceVO> priceList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            PriceVO price = new PriceVO();
            switch (i) {
                case 1:
                    if (PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())) {
                        price.setTieredPrice(priceScheme.getFixedPrice().doubleValue());
                        openAccountVO.setCycle(12);
                        openAccountVO.setStartMonth(1);
                        openAccountVO.setSettlementDay(1);
                        price.setTieredValue(65535);
                    } else {
                        price.setTieredPrice(priceScheme.getPrice1().doubleValue());
                        price.setTieredValue(priceScheme.getGas1().intValue());
                    }
                    price.setTieredNum(1);
                    priceList.add(price);
                    break;
                case 2:
                    if (priceScheme.getGas2() == null) {
                        break;
                    }
                    price.setTieredNum(2);
                    price.setTieredPrice(priceScheme.getPrice2().doubleValue());
                    price.setTieredValue(priceScheme.getGas2().intValue());
                    priceList.add(price);
                    break;
                case 3:
                    if (priceScheme.getGas3() == null) {
                        break;
                    }
                    price.setTieredNum(3);
                    price.setTieredPrice(priceScheme.getPrice3().doubleValue());
                    price.setTieredValue(priceScheme.getGas3().intValue());
                    priceList.add(price);
                    break;
                case 4:
                    if (priceScheme.getGas4() == null) {
                        break;
                    }
                    price.setTieredNum(4);
                    price.setTieredPrice(priceScheme.getPrice4().doubleValue());
                    price.setTieredValue(priceScheme.getGas4().intValue());
                    priceList.add(price);
                    break;
                case 5:
                    if (priceScheme.getGas5() == null) {
                        break;
                    }
                    price.setTieredNum(5);
                    price.setTieredPrice(priceScheme.getPrice5().doubleValue());
                    price.setTieredValue(65535);
                    priceList.add(price);
                    break;
                default:
                    break;
            }
        }
        openAccountVO.setTiered(priceList);
        IotR iotR = null;
        try {
            iotR = businessService.openAccount(openAccountVO);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        iotOpenAccountComponent.updateIotDevice(gasMeter, customerCode);
        log.info("换物联网表下发开户指令结果：" + iotR.getIsSuccess());
    }

    /**
     * 中心计费预付费表下发余额更新指令
     *
     * @param gasMeter       表具信息
     * @param rechargeAmount 充值金额
     */
    private void iotUpdateBalance(GasMeter gasMeter, BigDecimal rechargeAmount) {
        log.info("换表中心计费预付费表更新补充金额");
        UpdateBalanceVO param = new UpdateBalanceVO();
        param.setGasMeterNumber(gasMeter.getGasMeterNumber());
        param.setBalance(rechargeAmount);
        businessService.updatebalance(param);
        log.info("换表中心计费预付费表更新补充金额--end");
    }

    /**
     * 新建表具使用信息表
     *
     * @param data 换表记录
     */
    private void createNewMeterUseInfo(ChangeMeterRecord data, BigDecimal supplement) {
        GasMeterInfo oldMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(data.getOldMeterCode(), data.getCustomerCode()).getData();
        String oldMeterType = data.getOldMeterType();

        GasMeterInfoUpdateDTO updateDTO = GasMeterInfoUpdateDTO.builder()
                .id(oldMeterInfo.getId())
                .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                .build();

        if (!StringUtils.isEmpty(data.getArrearsDetailIdList())) {
            if (OrderSourceNameEnum.READMETER_PAY.getCode().equals(oldMeterType)) {
                List<Long> arrearsDetailIdList = Arrays.stream(data.getArrearsDetailIdList().split(","))
                        .map(Long::valueOf).collect(Collectors.toList());
                List<GasmeterArrearsDetail> arrearsDetail = gasmeterArrearsDetailBizApi.queryList(arrearsDetailIdList).getData();
                arrearsDetail.forEach(temp -> updateDTO.setCycleUseGas(bigDecimalAdd(oldMeterInfo.getCycleUseGas(), temp.getGas()))
                        .setTotalUseGas(bigDecimalAdd(oldMeterInfo.getTotalUseGas(), temp.getGas()))
                        .setTotalUseGasMoney(bigDecimalAdd(oldMeterInfo.getTotalUseGasMoney(), temp.getGasMoney())));
            }
            if (OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(oldMeterType)) {
                ReadMeterDataIot iotReadMeter = readMeterDataIotApi.getById(data.getReadMeterId()).getData();
                updateDTO.setCycleUseGas(bigDecimalAdd(oldMeterInfo.getCycleUseGas(), iotReadMeter.getMonthUseGas()))
                        .setTotalUseGas(bigDecimalAdd(oldMeterInfo.getTotalUseGas(), iotReadMeter.getMonthUseGas()))
                        .setTotalUseGasMoney(bigDecimalAdd(oldMeterInfo.getTotalUseGasMoney(), iotReadMeter.getUseMoney()));
            }
        } else {
            updateDTO.setCycleUseGas(oldMeterInfo.getCycleUseGas());
        }
        gasMeterInfoBizApi.update(updateDTO);

        GasMeterInfoSaveDTO newMeterInfo = GasMeterInfoSaveDTO.builder()
                .gasmeterCode(data.getNewMeterCode())
                .customerCode(data.getCustomerCode())
                .customerName(data.getCustomerName())
                .cycleUseGas(data.getCycleUseGas())
                .cycleChargeGas(oldMeterInfo.getCycleChargeGas())
                .totalUseGasMoney(BigDecimal.ZERO)
                .totalUseGas(BigDecimal.ZERO)
                .priceSchemeId(oldMeterInfo.getPriceSchemeId())
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .initialMeasurementBase(data.getNewMeterStartGas())
                .build();
        if (OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(oldMeterType)
                || (OrderSourceNameEnum.IC_RECHARGE.getCode().equals(oldMeterType) && data.getChangeType().startsWith("MONEY"))) {
            newMeterInfo.setCycleUseGas(data.getCycleUseGas());
        } else {
            newMeterInfo.setCycleUseGas(updateDTO.getCycleUseGas());
        }

        if (isIotMeter(data.getNewMeterType())) {
            //物联网表充值次数，开户时强制为1，
            newMeterInfo.setTotalRechargeMeterCount(1);
        }
        //中心计费预付费要把补充金额放到户表账户里
        if (OrderSourceNameEnum.CENTER_RECHARGE.eq(data.getNewMeterType())) {
            newMeterInfo.setGasMeterBalance(supplement);
        }
        if (BigDecimal.ZERO.compareTo(supplement) != 0) {
            //有补充金额或者补充气量
            newMeterInfo.setValue1(supplement);
            newMeterInfo.setTotalRechargeMeterCount(1);
        }

        //新建使用数据表
        gasMeterInfoBizApi.save(newMeterInfo);
    }


    /**
     * 根据换表记录更新新表的部分字段
     *
     * @param data 换表记录
     * @return 新表记录
     */
    private GasMeter updateMeterInstallData(ChangeMeterRecord data, GasMeter oldMeter) {
        R<GasMeter> newMeterResult = gasMeterBizApi.findGasMeterByCode(data.getNewMeterCode());
        if (newMeterResult.getIsError()) {
            throw new BizException(i18nUtil.getMessage(MessageConstants.G_ERROR_SERVICE));
        }
        GasMeter newMeter = newMeterResult.getData();

        newMeter.setProvinceCode(oldMeter.getProvinceCode());
        newMeter.setCityCode(oldMeter.getCityCode());
        newMeter.setAreaCode(oldMeter.getAreaCode());
        newMeter.setStreetId(oldMeter.getStreetId());
        newMeter.setCommunityId(oldMeter.getCommunityId());
        newMeter.setCommunityName(oldMeter.getCommunityName());
        newMeter.setDirection(oldMeter.getDirection());
        newMeter.setGasMeterAddress(oldMeter.getGasMeterAddress());
        newMeter.setMoreGasMeterAddress(oldMeter.getMoreGasMeterAddress());
        newMeter.setUseGasTypeId(oldMeter.getUseGasTypeId());
        newMeter.setUseGasTypeName(oldMeter.getUseGasTypeName());
        newMeter.setPopulation(oldMeter.getPopulation());
        newMeter.setNodeNumber(oldMeter.getNodeNumber());
        newMeter.setLongitude(oldMeter.getLongitude());
        newMeter.setLatitude(oldMeter.getLatitude());
        newMeter.setOpenAccountUserName(oldMeter.getOpenAccountUserName());
        newMeter.setOpenAccountUserId(oldMeter.getOpenAccountUserId());
        newMeter.setOpenAccountTime(LocalDateTime.now());
        //通气状态改为已通气  先使用旧表的通气数据  这里可能需要修改！
        newMeter.setDataStatus(oldMeter.getDataStatus());
        newMeter.setVentilateStatus(oldMeter.getVentilateStatus());
        newMeter.setVentilateUserId(oldMeter.getVentilateUserId());
        newMeter.setVentilateUserName(oldMeter.getVentilateUserName());
        newMeter.setVentilateTime(oldMeter.getVentilateTime());
        newMeter.setSendCardStauts(SendCardState.WAITE_SEND.getCode());
        newMeter.setGasMeterBase(data.getNewMeterStartGas());
        gasMeterBizApi.updateByCode(newMeter);
        return newMeter;
    }

    /**
     * 判断换表时填写的的气表底数和原档案是否一致
     *
     * @param meterBase        原档案气表底数
     * @param newMeterStartGas 换表时填写气表底数
     * @return true(一致) or false(不一致)
     */
    private boolean validStartGas(BigDecimal meterBase, BigDecimal newMeterStartGas) {
        if (meterBase == null) {
            meterBase = BigDecimal.ZERO;
        }
        if (newMeterStartGas == null) {
            newMeterStartGas = BigDecimal.ZERO;
        }
        return meterBase.compareTo(newMeterStartGas) == 0;
    }

    /**
     * 支付回滚
     *
     * @param id 记录id
     * @return 回滚结果
     */
    @Override
    public R<Boolean> rollbackPaid(Long id) {
        R<ChangeMeterRecord> recordR = changeMeterRecordBizApi.get(id);
        if (Boolean.TRUE.equals(recordR.getIsError())) {
            throw new BizException("调用查询接口失败");
        }
        ChangeMeterRecordUpdateDTO updateDTO = ChangeMeterRecordUpdateDTO.builder()
                .id(id)
                .status(PayStatusEnum.WAIT_PAY.getCode())
                .build();
        R<ChangeMeterRecord> update = changeMeterRecordBizApi.update(updateDTO);
        //TODO 业务数据回滚 需要调整表结构 记录表具原状态
        return R.success();
    }

    @Override
    public SettlementResult costCenterRechargeMeter(ChangeMeterRecord record) {

        String customerCode = record.getCustomerCode();
        String customerName = record.getCustomerName();
        String oldMeterCode = record.getOldMeterCode();

        BigDecimal oldMeterEndGas = record.getOldMeterEndGas();
        SettlementResult settlementResult = new SettlementResult();
        //获取新旧表数据
        GasMeter oldMeter = gasMeterBizApi.findGasMeterByCode(oldMeterCode).getData();
        //获取表具版本信息
        GasMeterVersion oldVersion = gasMeterVersionBizApi.get(oldMeter.getGasMeterVersionId()).getData();
        GasMeterInfo oldMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(oldMeterCode, customerCode).getData();
        BigDecimal totalGas = oldMeterInfo.getMeterTotalGas() == null ? BigDecimal.ZERO : oldMeterInfo.getMeterTotalGas();
        if (oldMeterEndGas.compareTo(totalGas) < 0) {
            throw new BizException("旧表止数不能小于表具最新上报数据！" + oldMeterInfo.getMeterTotalGas());
        }
        //如果是物联网表中心计费预付费；
        if (OrderSourceNameEnum.CENTER_RECHARGE.eq(oldVersion.getOrderSourceName())) {
            //物联网表中心计费预付费结算完是没有欠费记录的  是户表记录中户表账户余额
            costSettlementComponent.costSettlementCenterRecharge(customerCode, customerName, oldMeterEndGas,
                    DataTypeEnum.CHANGE_DATA, oldMeter);
            GasMeterInfo meterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(oldMeterCode, customerCode).getData();
            settlementResult.setGasMeterBalance(meterInfo.getGasMeterBalance());
            settlementResult.setCycleUseGas(meterInfo.getCycleUseGas());
        }
        //如果是物联网表中心计费后付费；
        if (OrderSourceNameEnum.REMOTE_READMETER.eq(oldVersion.getOrderSourceName())) {
            BigDecimal bigDecimal = costSettlementComponent.countRemoteReadMeterData(customerCode, oldMeter, oldMeterEndGas);
            GasMeterInfo meterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(oldMeterCode, customerCode).getData();
            settlementResult.setGasMeterBalance(meterInfo.getGasMeterBalance());
            BigDecimal cycleUseGas = meterInfo.getCycleUseGas() == null ? BigDecimal.ZERO : meterInfo.getCycleUseGas();
            settlementResult.setCycleUseGas(cycleUseGas.add(bigDecimal));
        }
        return settlementResult;
    }

    @Override
    public void settlementReturn(SettlementReturnParam param) {
        //获取场景数据
        ReadMeterDataIot sceneData = readMeterDataIotApi.getSceneData(param.getGasMeterNumber(), param.getGasMeterCode(), param.getCustomerCode());
        //物联网中心计费预付费才回退
        if (Objects.nonNull(sceneData) && OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(sceneData.getMeterType())) {
            //抵扣退费
            calculateApi.unSettlementIot(Collections.singletonList(sceneData));
        }
    }

    /**
     * 账户余额变更
     * 根据换表场景不同，不同的后续处理逻辑
     *
     * @param record 换表记录
     */
    private void updateCustomerAccount(ChangeMeterRecord record, Long useGasTypeId) {
        String newMeterType = record.getNewMeterType();
        BigDecimal supplementMoney = BigDecimal.ZERO;
        //如果有结算生成的欠费记录，那么补充金额/气量 无效
        if (!StringUtils.isEmpty(record.getArrearsDetailIdList())) {
            return;
        }
        //卡表换普表时，可将补气气量折算成的金额预存到客户账户中 折算方法：使用用气类型的价格配置，计算补充气量价值,即为折算金额；
        //卡表(金额)换普表或物联网预付费表换普表时，可将补充金额预存到客户账户中；
        if (OrderSourceNameEnum.READMETER_PAY.eq(newMeterType) || OrderSourceNameEnum.REMOTE_READMETER.eq(newMeterType)) {
            //如果补充金额不为0 直接取补充金额
            if (BigDecimal.ZERO.compareTo(record.getSupplementAmount()) != 0) {
                supplementMoney = record.getSupplementAmount();
            } else {
                //否则 补充气量不为空则转换补充气量
                if (BigDecimal.ZERO.compareTo(record.getSupplementGas()) != 0) {
                    ConversionVO conversionVO = ConversionVO.builder()
                            .gasMeterCode(record.getNewMeterCode())
                            .conversionValue(record.getSupplementGas())
                            .conversionType(ConversionType.GAS)
                            .useGasTypeId(useGasTypeId)
                            .build();
                    supplementMoney = calculateApi.conversion(conversionVO).getData();
                }
            }
        }

        if (BigDecimal.ZERO.compareTo(supplementMoney) != 0) {
            log.info("调用 账户余额变更 接口");
            UpdateAccountParamDTO updateAccountParam = UpdateAccountParamDTO.builder()
                    .customerCode(record.getCustomerCode())
                    .gasMeterCode(record.getNewMeterCode())
                    .accountMoney(supplementMoney)
                    .billNo(record.getChangeMeterNo())
                    .billType(AccountSerialSceneEnum.CHANGE_METER_SUPPLEMENT.getCode())
                    .build();
            customerAccountBizApi.updateAccountAfterChangeMeter(updateAccountParam);
        }
    }

    /**
     * 充值数据  气量表为充值气量， 金额表为充值金额
     *
     * @param record 换表记录
     * @return
     */
    private BigDecimal chargeNumber(ChangeMeterRecord record, Long useGasTypeId) {
        //如果结算时有生成欠费记录 那么补充金额或者补充气量是不生效的。
        if (!StringUtils.isEmpty(record.getArrearsDetailIdList())) {
            return BigDecimal.ZERO;
        }
        BigDecimal supplement = BigDecimal.ZERO;
        String changeType = record.getChangeType();
        if (changeType.endsWith("MONEY")) {
            supplement = record.getSupplementAmount();
        }
        if (changeType.endsWith("GAS")) {
            supplement = record.getSupplementGas();
        }
        //如果新表是物联网表
        if (isIotMeter(record.getNewMeterType())) {
            //补充金额不为空 直接取补充金额
            if (record.getSupplementAmount().compareTo(BigDecimal.ZERO) > 0) {
                supplement = record.getSupplementAmount();
            } else {
                if (record.getSupplementGas().compareTo(BigDecimal.ZERO) > 0) {
                    //那么需要将气量转换为金额 下发到表上
                    ConversionVO conversionVO = ConversionVO.builder()
                            .gasMeterCode(record.getOldMeterCode())
                            .conversionValue(record.getSupplementGas())
                            .conversionType(ConversionType.GAS)
                            .useGasTypeId(useGasTypeId)
                            .build();
                    supplement = calculateApi.conversion(conversionVO).getData();
                }
            }
        }
        return supplement;
    }

    /**
     * 新增表具档案
     *
     * @param saveDTO 换表上传参数
     */
    private GasMeter addGasMeter(ChangeMeterRecordSaveDTO saveDTO) {
        GasMeter meter = GasMeter.builder()
                .gasMeterFactoryId(saveDTO.getNewMeterFactoryId())
                .gasMeterVersionId(saveDTO.getNewMeterVersionId())
                .gasMeterNumber(saveDTO.getNewMeterNumber())
                .gasMeterModelId(saveDTO.getNewMeterModelId())
                .gasMeterBase(saveDTO.getNewMeterStartGas())
                .build();
        R<List<GasMeter>> listR = gasMeterArchiveService.addGasMeterList(Collections.singletonList(meter));
        return listR.getData().get(0);
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
