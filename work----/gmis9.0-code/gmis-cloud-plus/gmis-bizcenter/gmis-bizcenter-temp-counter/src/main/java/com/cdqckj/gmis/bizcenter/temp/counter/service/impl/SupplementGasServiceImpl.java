package com.cdqckj.gmis.bizcenter.temp.counter.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SupplementGasVO;
import com.cdqckj.gmis.bizcenter.temp.counter.service.SupplementGasService;
import com.cdqckj.gmis.biztemporary.SupplementGasRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.biztemporary.enums.RepGasMethodEnum;
import com.cdqckj.gmis.biztemporary.enums.RepGasStatusEnum;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterExVo;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.vo.RechargeVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateBalanceVO;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


/**
 * 补气场景相关接口
 *
 * @author hp
 */
@Slf4j
@Service
public class SupplementGasServiceImpl implements SupplementGasService {

    @Autowired
    private SupplementGasRecordBizApi supplementGasRecordBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private CardInfoBizApi cardInfoBizApi;
    @Autowired
    private RechargeRecordBizApi rechargeRecordBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;

    /**
     * 分页查询补气记录列表
     *
     * @param pageDTO 分页查询对象
     * @return 补气记录分页列表
     */
    @Override
    public R<Page<SupplementGasRecord>> pageSupplementGasRecord(PageParams<SupplementGasRecordPageDTO> pageDTO) {
        return supplementGasRecordBizApi.page(pageDTO);
    }

    /**
     * 通过ID查询补气记录详情
     *
     * @param id 记录ID
     * @return 补气记录
     */
    @Override
    public R<SupplementGasRecord> getSupplementGasRecord(Long id) {
        return supplementGasRecordBizApi.get(id);
    }

    /**
     * 通过表具code查询未完成的补气记录
     *
     * @param meterCode 表具编号
     * @return 补气记录
     */
    @Override
    public R<SupplementGasVO> queryUnfinishedRecord(String customerCode, String meterCode) {
        GasMeter gasMeter = gasMeterBizApi.findGasMeterByCode(meterCode).getData();
        //获取表具版本信息
        GasMeterVersion version = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();

        //6a的表现在是卡库原因无法获取表ID，所以在发卡时状态直接改变，如果发卡没有充值，这个校验不会生效，没法控制
        if (OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())) {
            if (SendCardState.WAITE_SEND.eq(gasMeter.getSendCardStauts())) {
                throw BizException.wrap("IC卡表未完成发卡，不允许补气，如已发卡，请确认是否读卡");
            }
        }
        if (checkVersion(version)) {
            return R.fail("该表不支持补气！");
        }
        //校验是否有充值未上表的数据
        List<RechargeRecord> data = rechargeRecordBizApi.queryUnfinishedRecord(meterCode).getData();
        if (!CollectionUtils.isEmpty(data)) {
            return R.fail("该表还有完成上表的充值记录，请先完成充值上表！");
        }

        GasMeterInfo gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(meterCode, customerCode).getData();
        SupplementGasVO vo = new SupplementGasVO();
        vo.setAmountMark(version.getAmountMark());
        vo.setOrderSourceName(version.getOrderSourceName());
        vo.setGasMeterInfo(gasMeterInfo);
        //可以补气
        SupplementGasRecord record = supplementGasRecordBizApi.queryUnfinishedRecord(meterCode).getData();
        if (Objects.nonNull(record)) {
            vo.setSupplementGasRecord(record);
            return R.success(vo);
        } else {
            //判断卡上是否还有余额,如果有余额不能补气
            if (OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())) {
                CardInfo cardInfo = cardInfoBizApi.getByGasMeterCode(meterCode, customerCode).getData();
                if (Objects.nonNull(cardInfo)) {
                    BigDecimal cardB = cardInfo.getCardBalance();
                    if (cardB != null && cardB.compareTo(BigDecimal.ZERO) > 0) {
                        return R.fail("卡上余额未上表不能充值，请确认是否读卡回写");
                    }
                }
            }
        }
        return R.success(vo);
    }

    /**
     * 新增补气记录数据
     *
     * @param saveDTO 新增参数
     * @return 新增对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<SupplementGasVO> add(SupplementGasRecordSaveDTO saveDTO) {

        if (Objects.isNull(saveDTO.getRepMoney()) && Objects.isNull(saveDTO.getRepGas())) {
            return R.fail("请填写补气金额或补气气量！");
        }

        GasMeterExVo gasMeter = gasMeterBizApi.findGasMeterInfoByCodeEx(saveDTO.getGasMeterCode()).getData();
        //获取表具版本信息
        GasMeterVersion version = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
        if (checkVersion(version)) {
            return R.fail("该表不支持补气！");
        }
        //获取使用信息
        GasMeterInfo gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeter.getGasCode(), gasMeter.getCustomerCode()).getData();

        SupplementGasVO vo = new SupplementGasVO();
        vo.setAmountMark(version.getAmountMark());
        vo.setOrderSourceName(version.getOrderSourceName());

        BigDecimal supplement;
        //按需补充
        if (RepGasMethodEnum.REP_GAS_ONDEMAND.eq(saveDTO.getRepGasMethod())) {
            //校验填写数据
            if (AmountMarkEnum.GAS.eq(version.getAmountMark())) {
                supplement = saveDTO.getRepGas();
            } else {
                supplement = saveDTO.getRepMoney();
            }
        } else {
            //补上次充值
            if (Objects.isNull(gasMeterInfo.getValue1()) || BigDecimal.ZERO.compareTo(gasMeterInfo.getValue1()) == 0) {
                return R.fail("没有充值记录！");
            }
            supplement = gasMeterInfo.getValue1();
            if (AmountMarkEnum.GAS.eq(version.getAmountMark())) {
                saveDTO.setRepGas(supplement);
            } else {
                saveDTO.setRepMoney(supplement);
            }
        }
        saveDTO.setCustomerCode(gasMeter.getCustomerCode());
        saveDTO.setCustomerName(gasMeter.getCustomerName());
        saveDTO.setRepGasStatus(RepGasStatusEnum.WAIT_OPERATE.getCode());
        saveDTO.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        SupplementGasRecord save = supplementGasRecordBizApi.save(saveDTO).getData();
        GasMeterInfo newInfo = updateMeterInfo(saveDTO.getRepGasMethod(), gasMeterInfo, version, supplement);
        //物联网下发指令
        Boolean sentFlag = iotSentOrder(gasMeter, version, newInfo, supplement);
        if (Boolean.TRUE.equals(sentFlag)) {
            //如果指令已下发 更新补气状态
            SupplementGasRecordUpdateDTO updateDTO = SupplementGasRecordUpdateDTO.builder()
                    .id(save.getId())
                    .repGasStatus(RepGasStatusEnum.REP_GAS_SUCCESS.getCode())
                    .dataStatus(DataStatusEnum.NORMAL.getValue())
                    .build();
            supplementGasRecordBizApi.update(updateDTO);
        }
        vo.setSupplementGasRecord(save);
        return R.success(vo);
    }

    /**
     * 校验表具版本是否支持补气
     *
     * @param version 表具版本
     * @return true：可以补气，false:不能补气
     */
    private boolean checkVersion(GasMeterVersion version) {
        return !OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())
                && !OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())
                && !OrderSourceNameEnum.REMOTE_RECHARGE.eq(version.getOrderSourceName());
    }

    private GasMeterInfo updateMeterInfo(String supplementType, GasMeterInfo meterInfo, GasMeterVersion version, BigDecimal supplement) {
        //补上次充值  什么都不改
        if (RepGasMethodEnum.REP_PRE_CHARGE.eq(supplementType)) {
            return meterInfo;
        }
        String orderSourceName = version.getOrderSourceName();
        //中心预付费表 充值要记录表端余额用于结账和下发显示
        if (OrderSourceNameEnum.CENTER_RECHARGE.eq(orderSourceName)) {
            GasMeterInfoUpdateDTO update = new GasMeterInfoUpdateDTO();
            update.setId(meterInfo.getId());
            BigDecimal balance = meterInfo.getGasMeterBalance() == null ? BigDecimal.ZERO : meterInfo.getGasMeterBalance();
            update.setGasMeterBalance(balance.add(supplement));
            return gasMeterInfoBizApi.update(update).getData();
        }
        return meterInfo;
    }

    /**
     * 表端计费物联网下发指令 金额
     */
    private Boolean iotSentOrder(GasMeter gasMeter, GasMeterVersion version, GasMeterInfo gasMeterInfo, BigDecimal supplement) {
        //物联网表端计费 下发充值指令
        if (OrderSourceNameEnum.REMOTE_RECHARGE.eq(version.getOrderSourceName())) {
            log.info("物联网表端计费 下发充值指令");
            RechargeVO rechargeVO = new RechargeVO();
            rechargeVO.setGasMeterNumber(gasMeter.getGasMeterNumber());
            rechargeVO.setRechargeAmount(supplement);
            UseGasType useGasType = useGasTypeBizApi.get(gasMeter.getUseGasTypeId()).getData();
            rechargeVO.setAlarmAmount(useGasType.getAlarmGas().toBigInteger().intValue());
            IotR recharge = businessService.recharge(rechargeVO);
            return recharge.getIsSuccess();
        } else if (OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())) {
            //物联网中心计费预付费:修改账户余额 下发更新指令
            log.info("中心计费预付费表更新补充金额");
            UpdateBalanceVO param = new UpdateBalanceVO();
            param.setGasMeterNumber(gasMeter.getGasMeterNumber());
            param.setBalance(gasMeterInfo.getGasMeterBalance());
            IotR update = businessService.updatebalance(param);
            log.info("中心计费预付费表更新补充金额--end");
            return update.getIsSuccess();
        }
        return false;
    }
}
