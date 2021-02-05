package com.cdqckj.gmis.card.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.enums.*;
import com.cdqckj.gmis.card.service.*;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.operateparam.service.PriceMappingService;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 读写卡相关业务逻辑处理
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
@DS("#thread.tenant")
public class CardOperServiceImpl extends SuperCenterServiceImpl implements CardOperService {

    @Autowired
    GasMeterService meterService;

    @Autowired
    CardInfoService cardInfoService;

    @Autowired
    CardRepRecordService cardRepRecordService;


    @Autowired
    CardRecRecordService cardRecRecordService;
    @Autowired
    GasMeterInfoService gasMeterInfoService;
    @Autowired
    GasMeterVersionService gasMeterVersionService;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OperCardLoadService operCardLoadService;

    @Autowired
    PriceMappingService priceMappingService;

    @Autowired
    TollItemService tollItemService;



    /**
     * 发卡加载 -----衍生：发卡只是个记录不作其他使用，发卡后更新表具使用信息表，将卡号回填。补卡后同样回填。
     * 首先查看是否有发卡记录
     * 没有发卡记录，生成一条默认IC状态是待收费--并生成对应收费项。并记录卡类型：表具是否是 IC卡表，如果是IC卡表就发IC卡，如果不是就发ID卡。
     * 已有发卡记录，判断状态
     * 待收费：直接打开收费窗口进行收费（可以顺带充值）
     * 待写卡：加载数据显示写卡按钮--点击写卡按钮进行写卡
     * 已写卡：显示写卡记录信息，无任何操作按钮
     *
     *
     * @param gasMeterCode
     * @return
     */
    @Override
    public R<CardInfo> issueCard(String gasMeterCode,String customerCode) {
        GasMeter meter=meterService.findGasMeterByCode(gasMeterCode);
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        Customer customer=customerService.getOne(new LbqWrapper<Customer>().eq(Customer::getCustomerCode,customerCode));
        if(version.getIssuingCards()==null || version.getIssuingCards().intValue()==0){
            throw BizException.wrap("该气表版号配置不允许发卡");
        }
        CardInfo cardInfo=cardInfoService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,gasMeterCode)
                .eq(CardInfo::getCustomerCode,customerCode)
                .eq(CardInfo::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        if(cardInfo!=null){
            return R.success(cardInfo);
        }else{
            //无发卡记录，需要直接生成发卡记录
            cardInfo=new CardInfo();
            if(version.getOrderSourceName().equals(OrderSourceNameEnum.IC_RECHARGE.getCode())){
                //IC 卡
                cardInfo.setCardType(CardTypeEnum.IC.getCode());
                //有可能充值，如果不充值，直接调用后台接口修改为待写卡状态。
            }else{
                //ID 卡
                cardInfo.setCardType(CardTypeEnum.ID.getCode());
            }
            cardInfo.setCardStatus(null);
            cardInfo.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            cardInfo.setGasMeterCode(gasMeterCode);
            cardInfo.setCustomerCode(customer.getCustomerCode());
            cardInfo.setCustomerName(customer.getCustomerName());
            cardInfoService.save(cardInfo);

            return R.success(cardInfo);
        }
    }

    /**
     * 补卡
     * 是否有未完成的补卡，如果有，直接加载并判断状态进行修改和收费处理。
     * @param gasMeterCode
     * @return
     */
    @Override
    public R<CardRepRecord> repCard(String gasMeterCode,String customerCode) {
        CardInfo cardInfo=cardInfoService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,gasMeterCode)
                .eq(CardInfo::getCustomerCode,customerCode)
                .eq(CardInfo::getCardStatus, CardStatusEnum.ISSUE_CARD.getCode())
                .eq(CardInfo::getDataStatus, DataStatusEnum.NORMAL.getValue()));
        if(cardInfo==null){
            throw BizException.wrap("未完成发卡，不能直接补卡");
        }
        CardRepRecord cardRepRecord=cardRepRecordService.getOne(new LbqWrapper<CardRepRecord>()
                .eq(CardRepRecord::getGasMeterCode,gasMeterCode)
                .eq(CardRepRecord::getCustomerCode,customerCode)
                .eq(CardRepRecord::getRepCardStatus,RepCardStatusEnum.WAITE_WRITE_CARD.getCode())
                .eq(CardRepRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
        );
        if(cardRepRecord!=null){
            return R.success(cardRepRecord);
        }
        return R.success(null);
    }

    /**
     * 补卡保存
     * 补充基本信息，确认是否补上次充值，如果补上次充值，根据输入金额气量进行保存
     * @return gasMeterCode
     */
    public R<CardRepRecord> repCardSave(CardRepRecordSaveDTO saveDTOTemp, Long id){
        CardRepRecord saveDTO=new CardRepRecord();
        BeanPlusUtil.copyProperties(saveDTOTemp,saveDTO);
        GasMeter meter=gasMeterService.findGasMeterByCode(saveDTO.getGasMeterCode());
        CardRepRecord rep=cardRepRecordService.getOne(new LbqWrapper<CardRepRecord>()
                .eq(CardRepRecord::getGasMeterCode,meter.getGasCode())
                .eq(CardRepRecord::getCustomerCode,saveDTO.getCustomerCode())
                .eq(CardRepRecord::getRepCardStatus,RepCardStatusEnum.WAITE_WRITE_CARD.getCode())
                .eq(CardRepRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
        );
        if(rep!=null){
            throw BizException.wrap("存在未完成的补卡记录");
        }
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        if(version.getOrderSourceName().equals(OrderSourceNameEnum.IC_RECHARGE.getCode())){
            //IC 卡
            saveDTO.setCardType(CardTypeEnum.IC.getCode());
            if("01".equals(version.getCompanyCode())&& "0".equals(version.getIcCardCoreMark())){
                //暂时不知道怎么校验
            }else {
                if (RepCardTypeEnum.REP_NEW_USER_CARD.eq(saveDTOTemp.getRepCardType())) {
                    if (meter.getGasMeterRealId() != null && meter.getGasMeterRealId().longValue() != 0L) {
                        throw BizException.wrap("检测用户已是老用户，补新用户卡会导致充值无法上表。");
                    }
                } else {
                    //这里不能校验，可能使用工具卡处理过，目前还是做校验，以后遇到这种情况，如果允许，那么考虑补救措施，需要清除上表次数
                    // 上表次数根据是否补上次充值来判断是清0，还是清1（如果本就为0，只能清0）和近三次充值（补上次充值将上次充值填充）。
                    if (meter.getGasMeterRealId() == null || meter.getGasMeterRealId().longValue() == 0L) {
                        throw BizException.wrap("检测用户不是老用户，补老用户卡会导致充值无法上表。");
                    }
                }
            }
        }else{
            //ID 卡
            saveDTO.setCardType(CardTypeEnum.ID.getCode());
        }

        saveDTO.setId(id);
        CardInfo cardInfo=cardInfoService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,saveDTO.getGasMeterCode())
                .eq(CardInfo::getCustomerCode,saveDTO.getCustomerCode())
                .eq(CardInfo::getCardStatus, CardStatusEnum.ISSUE_CARD.getCode())
                .eq(CardInfo::getDataStatus, DataStatusEnum.NORMAL.getValue()));
        if(cardInfo==null){
            throw BizException.wrap("未完成发卡，不能直接补卡");
        }
        saveDTO.setCardNo(cardInfo.getCardNo());
        saveDTO.setCustomerCode(cardInfo.getCustomerCode());
        saveDTO.setCustomerName(cardInfo.getCustomerName());
        if(tollItemService.existTollItem(TolltemSceneEnum.CARD_REPLACEMENT.getCode())){
            saveDTO.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            saveDTO.setRepCardStatus(RepCardStatusEnum.WAITE_CHARGE.getCode());
        }else{
            saveDTO.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            saveDTO.setRepCardStatus(RepCardStatusEnum.WAITE_WRITE_CARD.getCode());
        }
        if(RepCardMethodEnum.REP_PRE_RECHARGE.eq(saveDTO.getRepCardMethod())){
            GasMeterInfo gasMeterInfo= gasMeterInfoService.getByMeterAndCustomerCode(saveDTO.getGasMeterCode(),saveDTO.getCustomerCode());
            if(AmountMarkEnum.GAS.eq(version.getAmountMark())){
                saveDTO.setRepCardGas(gasMeterInfo.getValue1());
            }else{
                saveDTO.setRepCardMoney(gasMeterInfo.getValue1());
            }
        }
        if(id==null){
            cardRepRecordService.save(saveDTO);
        }else{
            cardRepRecordService.updateById(saveDTO);
        }
        return R.success(saveDTO);

    }
}
