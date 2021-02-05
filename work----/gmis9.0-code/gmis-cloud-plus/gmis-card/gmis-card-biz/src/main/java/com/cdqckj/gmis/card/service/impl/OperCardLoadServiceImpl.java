package com.cdqckj.gmis.card.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.biztemporary.enums.RepGasMethodEnum;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.card.dto.CardSupplementGasDTO;
import com.cdqckj.gmis.card.dto.RecCardLoadDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardPriceScheme;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.enums.CardTypeEnum;
import com.cdqckj.gmis.card.enums.RepCardMethodEnum;
import com.cdqckj.gmis.card.enums.RepCardTypeEnum;
import com.cdqckj.gmis.card.service.*;
import com.cdqckj.gmis.card.util.CardUtils;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterFactoryService;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.operateparam.service.PriceMappingService;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.I18nUtil;
import io.seata.common.util.CollectionUtils;
import io.swagger.models.Scheme;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 写卡数据加载
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
@DS("#thread.tenant")

public class OperCardLoadServiceImpl extends SuperCenterServiceImpl implements OperCardLoadService {

    @Value("#{${gmis.card.card-core-conf}}")
    private Map<String,String> cardCoreConf;
    private static final int DEFAULT_CARD_TYPE=51;
    @Autowired
    I18nUtil i18nUtil;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    CardInfoService cardInfoService;
    @Autowired
    CardRepRecordService cardRepRecordService;
    @Autowired
    GasMeterInfoService gasMeterInfoService;
    @Autowired
    GasMeterVersionService gasMeterVersionService;
    @Autowired
    GasMeterFactoryService gasMeterFactoryService;

    @Autowired
    UseGasTypeService useGasTypeService;

    @Autowired
    CalculateService calculateService;


    @Autowired
    PriceSchemeService priceSchemeService;

    @Autowired
    PriceMappingService priceMappingService;

    @Autowired
    CardPriceSchemeService cardPriceSchemeService;


    @Autowired
    OperCardBackService operCardBackService;


    /**
     * 开户卡
     * @param
     * @return
     */
    @Override
    @CodeNotLost
    public R<JSONObject> issueCardLoad(Long id,JSONObject readData) {
        log.info("发卡读卡数据：{}",readData.toJSONString());
        CardUtils.valid(readData);
        //需要判断是否是新卡
        if(!CardUtils.isNewCard(readData)){
            throw BizException.wrap("当前卡不是新卡");
        }
        CardInfo cardInfo=cardInfoService.getById(id);

        JSONObject data=loadInfo(cardInfo.getGasMeterCode(), CardUtils.OPEN_ACCOUNT);
        GasMeterVersion version=(GasMeterVersion) data.get("meterVersion");
        Integer cardLength=version.getCardNumberLength();
        if(cardLength==null){
            cardLength=8;
        }
        String pre=version.getCardNumberPrefix();
        cardInfo.setCardNo(BizCodeNewUtil.genCardNumber( cardLength, pre));
        if(StringUtils.isBlank(cardInfo.getCardNo())){
            throw BizException.wrap("卡号未成功获取");
        }

        if(CardTypeEnum.IC.eq(cardInfo.getCardType())){
            setMeterInfo(cardInfo.getGasMeterCode(),cardInfo.getCustomerCode(),data);
        }
        if(cardInfo==null){
            throw BizException.wrap("非正常写开户卡操作，不存在开户记录");
        }
        data.put("CardNo_s",cardInfo.getCardNo());
        log.info("开户卡写卡数据加载：{}",data.toJSONString());
        if(data.containsKey("meterVersion")){
            data.remove("meterVersion");
        }
        return R.success(data);
    }
    /**
     * 购气
     * @return
     */
    @Override
    public R<JSONObject> buyGasLoad(String gasMeterCode,JSONObject readData) {
        log.info("购气读卡数据：{}",readData.toJSONString());
        CardUtils.valid(readData);
        validBuyGasAndRepGas(gasMeterCode,readData);
        CardInfo cardInfo=operCardBackService.readCardCallBack(readData).getData();
        JSONObject data=loadInfo(gasMeterCode,CardUtils.BUY_GAS);
        if(CardTypeEnum.IC.eq(cardInfo.getCardType())){
            setMeterInfo(gasMeterCode,cardInfo.getCustomerCode(),data);
        }
        if(StringUtils.isBlank(cardInfo.getCardNo())){
            throw BizException.wrap("卡号未成功获取");
        }
        data.put("CardNo_s",cardInfo.getCardNo());
        log.info("购气写卡数据加载：{}",data.toJSONString());
        if(data.containsKey("meterVersion")){
            data.remove("meterVersion");
        }
        return R.success(data);
    }
    /**
     * 补卡--判断是否是新卡
     * @param id
     * @return
     */
    @Override
    public R<JSONObject> repCardLoad(Long id,JSONObject readData) {
        log.info("补卡读卡数据：{}",readData.toJSONString());
        CardUtils.valid(readData);
        JSONObject resultData=new JSONObject();
        if(!CardUtils.isNewCard(readData)){
            throw BizException.wrap("当前卡不是新卡");
        }
        //加载补卡数据，如果有充值
        CardRepRecord cardRepRecord=cardRepRecordService.getById(id);
        if(cardRepRecord==null){
            throw BizException.wrap("非正常补卡操作，不存在补卡记录");
        }
        String gasMeterCode=cardRepRecord.getGasMeterCode();
        JSONObject  data;
        if(RepCardTypeEnum.REP_NEW_USER_CARD.eq(cardRepRecord.getRepCardType()) ){
            data= loadInfo(gasMeterCode, CardUtils.OPEN_ACCOUNT);
            if(CardTypeEnum.IC.eq(cardRepRecord.getCardType())){
                setMeterInfo(gasMeterCode,cardRepRecord.getCustomerCode(),data);
                if(RepCardMethodEnum.NOT_REP_RECHARGE.eq(cardRepRecord.getRepCardMethod())){
                    //不补上次气量，直接清0
                    data.put("OneGas_i",decimalToString(BigDecimal.ZERO,getDigit(data)));
                }
            }
        }else{
            data= loadInfo(gasMeterCode, CardUtils.REP_CARD);
            Object version=data.get("meterVersion");
            JSONObject obj=JSONObject.parseObject(data.toJSONString());
            if(CardTypeEnum.IC.eq(cardRepRecord.getCardType())){
                obj.put("meterVersion",version);
                setMeterInfo(gasMeterCode,cardRepRecord.getCustomerCode(),obj);
                //老用户卡，如果不补气量，不用写入金额，如果要补上次，需要设置setMeterInfo
                if(RepCardMethodEnum.NOT_REP_RECHARGE.eq(cardRepRecord.getRepCardMethod())){
                    obj.put("OneGas_i",decimalToString(BigDecimal.ZERO,1));
                }
            }
            obj.put("OperType_i", CardUtils.BUY_GAS);
            //转换字符窜会被丢弃，但是写卡必须输入
            obj.put("InValues_s",null);
            if(obj.containsKey("meterVersion")){
                obj.remove("meterVersion");
            }
            if(StringUtils.isBlank(cardRepRecord.getCardNo())){
                throw BizException.wrap("卡号未成功获取");
            }
            obj.put("CardNo_s",cardRepRecord.getCardNo());
            resultData.put("repGasData", obj);
        }
        if(StringUtils.isBlank(cardRepRecord.getCardNo())){
            throw BizException.wrap("卡号未成功获取");
        }
        data.put("CardNo_s",cardRepRecord.getCardNo());
        resultData.put("repCardData",data);
        if(data.containsKey("meterVersion")){
            data.remove("meterVersion");
        }
        log.info("补卡写卡数据加载：{}",data.toJSONString());
        return R.success(resultData);
    }


    /**
     * 补气
     * @param repGasData
     * @return
     */
    @Override
    public R<JSONObject> repGasLoad(CardSupplementGasDTO repGasData) {
        JSONObject readData=repGasData.getReadCardData();
        log.info("补气读卡数据：{}",readData.toJSONString());
        CardUtils.valid(readData);
        String cardNo=CardUtils.getCardNumber(readData);
        if(StringUtils.isBlank(cardNo) ||"0".equals(cardNo)){
//            throw BizException.wrap("无卡号（新卡）");
            return R.success(null);
        }
        CardInfo cardInfo=getCardInfoByCardNo(cardNo);
        validBuyGasAndRepGas(repGasData.getGasMeterCode(),readData);
        JSONObject data=loadInfo(repGasData.getGasMeterCode(),CardUtils.REP_GAS);
        GasMeterInfo meterInfo=setMeterInfo(repGasData.getGasMeterCode(),cardInfo.getCustomerCode(),data);
        if(RepGasMethodEnum.REP_GAS_ONDEMAND.eq(repGasData.getRepGasMethod())){
            String val1=data.getString("OneGas_i");
            String val2=data.getString("TwoGas_i");
//            String val3=data.getString("ThreeGas_i");
            data.put("OneGas_i",decimalToString(repGasData.getRepVal(),getDigit(data)));
            data.put("TwoGas_i",val1);
            data.put("ThreeGas_i",val2);
            if(meterInfo.getTotalRechargeMeterCount()==null || meterInfo.getTotalRechargeMeterCount().intValue()==0){
                data.put("RechargeTimes_i",1);
            }else{
                data.put("RechargeTimes_i",meterInfo.getTotalRechargeMeterCount().intValue()+1);
            }
        }
        if(StringUtils.isBlank(cardInfo.getCardNo())){
            throw BizException.wrap("卡号未成功获取");
        }
        data.put("CardNo_s",cardInfo.getCardNo());
        log.info("补气写卡数据加载：{}",data.toJSONString());
        if(data.containsKey("meterVersion")){
            data.remove("meterVersion");
        }
        return R.success(data);
    }
    /**
     * 退气
     * @param gasMeterCode
     * @return
     */
    @Override
    public R<JSONObject> backGasLoad(String gasMeterCode,JSONObject readData) {
        log.info("退气读卡数据：{}",readData.toJSONString());
        CardUtils.valid(readData);
        String cardNo=CardUtils.getCardNumber(readData);
        //退费的时候发卡记录已作废
        List<CardInfo> cards=cardInfoService.list(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getCardNo ,cardNo)
//                .eq(CardInfo::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        if(CollectionUtils.isEmpty(cards)){
            throw BizException.wrap("未知用户卡");
        }
        List<CardInfo> ncards=new ArrayList<>();
        CardInfo cardInfo=null;
        for (CardInfo card : cards) {
            if(StringUtils.isNotBlank(card.getCardNo()) && card.getCardNo().equals(cardNo)){
                cardInfo=card;
            }
            if(card.getDataStatus()==DataStatusEnum.NORMAL.getValue()){
                ncards.add(card);
            }
        }
        if(ncards.size()>1){
            throw BizException.wrap("卡号重复需要柜台处理后才能操作");
        }
        BigDecimal balance=CardUtils.getBalance(readData);
        if(balance.compareTo(BigDecimal.ZERO)==0){
            throw BizException.wrap("卡上无气量，不可退气");
        }
        if(cardInfo!=null) {
            cardNo=cardInfo.getCardNo();
            if (!cardInfo.getGasMeterCode().equals(gasMeterCode)) {
                throw BizException.wrap("待写卡卡号和实际操作气表卡号不对应");
            }
            if(balance.compareTo(cardInfo.getCardBalance())!=0){
                throw BizException.wrap("卡上气量和系统所记录卡上气量不一致，不能退气");
            }
            Integer count=CardUtils.getReChargeCount(readData);
            if(count.intValue()!=cardInfo.getCardChargeCount().intValue()){
                throw BizException.wrap("卡上充值次数和系统所记录卡上次数不一致，不能退气");
            }

        }

        JSONObject data=loadInfo(gasMeterCode,CardUtils.BACK_GAS);
        if(StringUtils.isBlank(cardNo)){
            throw BizException.wrap("卡号未成功获取");
        }
        data.put("CardNo_s",cardNo);
        log.info("退气写卡数据加载：{}",data.toJSONString());
        if(data.containsKey("meterVersion")){
            data.remove("meterVersion");
        }
        return R.success(data);
    }

    /**
     * 换表
     * @param gasMeterCode
     * @return
     */
    @Override
    public R<JSONObject> changeMeterLoad(String gasMeterCode) {
        return R.success(loadInfo(gasMeterCode,CardUtils.REP_METER));
    }
    /**
     * 读卡
     * @return
     */
    @Override
    public R<JSONObject> readCardLoad() {
        JSONObject data=buildSendData(CardUtils.READ_CARD,getCardType(null),getSupportFactory(),null);
        log.info("读卡数据加载：{}",data.toJSONString());
        if(data.containsKey("meterVersion")){
            data.remove("meterVersion");
        }
        return R.success(data);
    }

    /**
     * 回收
     * @return
     */
    @Override
    public R<RecCardLoadDTO> recCardLoad(JSONObject readData) {
        log.info("卡回收读卡数据：{}",readData.toJSONString());
        RecCardLoadDTO cardLoadDTO=new RecCardLoadDTO();
        try{
            CardUtils.valid(readData);
            if(CardUtils.isNewCard(readData)){
                cardLoadDTO.setIsRecCard(false);
                cardLoadDTO.setMsg("新用户卡，不能回收");
                return R.success(cardLoadDTO);
            }
        }catch (BizException e){
            cardLoadDTO.setIsRecCard(false);
            cardLoadDTO.setMsg(e.getMessage());
            return R.success(cardLoadDTO);
        }
        cardLoadDTO.setIsRecCard(true);
        cardLoadDTO.setCardBalance(CardUtils.getBalance(readData));
        cardLoadDTO.setCardNo(CardUtils.getCardNumber(readData));
        cardLoadDTO.setCardChargeCount(CardUtils.getReChargeCount(readData));
        cardLoadDTO.setTotalAmount(CardUtils.getTotalAmount(readData));

        //需要记录回收卡余额,因为操作后，响应数据完全没有。暂时不做，还不清楚回收细节。
        JSONObject data=buildSendData(CardUtils.REC_CARD,getCardType(null),getSupportFactory(),null);
//        result.put("recJsonData",data);
        if(data.containsKey("meterVersion")){
            data.remove("meterVersion");
        }
        cardLoadDTO.setRecCardJsonData(data);
        log.info("卡回收写卡数据加载：{}",data.toJSONString());
        return R.success(cardLoadDTO);
    }

    private int getDigit(JSONObject data){
        int digits=2;
        GasMeterVersion version=(GasMeterVersion) data.get("meterVersion");
        if(version==null){
            return digits;
        }
        if(version.getAmountDigits()!=null){
            digits=version.getAmountDigits().intValue();
        }
        return digits;
    }

    private boolean validBuyGasAndRepGas(String gasMeterCode,JSONObject readData){
        if(CardUtils.isNewCard(readData)){
            throw BizException.wrap("当前卡为新卡不能充值或补气，错误操作");
        }
        String cardNo=CardUtils.getCardNumber(readData);
        List<CardInfo> cards=cardInfoService.list(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getCardNo ,cardNo)
                .eq(CardInfo::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        if(CollectionUtils.isEmpty(cards)){
            throw BizException.wrap("未知用户卡");
        }
        if(cards.size()>1){
            throw BizException.wrap("卡号重复需要柜台处理后才能充值补气");
        }
        CardInfo cardInfo=cards.get(0);
        if(!cardInfo.getGasMeterCode().equals(gasMeterCode)){
            throw BizException.wrap("待写卡卡号和实际操作气表卡号不对应");
        }
        if(CardUtils.hasBalance(readData)){
            throw BizException.wrap("卡上存在余额，不能购气和补气");
        }
        return true;
    }
    private JSONObject loadInfo(String gasMeterCode,int operType){

        GasMeter meter=gasMeterService.findGasMeterByCode(gasMeterCode);
        GasMeterVersion gasMeterVersion= gasMeterVersionService.getById(meter.getGasMeterVersionId());
        if(operType==CardUtils.BUY_GAS){
            //6A的表太特殊了没有表ID也能充值...
            if("01".equals(gasMeterVersion.getCompanyCode())&& "0".equals(gasMeterVersion.getIcCardCoreMark())){
                //暂时不知道怎么校验
            }else{
                if(meter.getGasMeterRealId()==null || "0".equals(meter.getGasMeterRealId())){
                    throw BizException.wrap("开户卡未进行读卡回写表ID，不能购气写卡");
                }
            }
        }
//        GasMeterVersion gasMeterVersion= gasMeterVersionService.getById(meter.getGasMeterVersionId());
        UseGasType useGasType=useGasTypeService.getById(meter.getUseGasTypeId());
        if(meter.getUseGasTypeId()==null || meter.getUseGasTypeId().longValue()==0) {
            log.error("气表用气类型未配置，不能获取价格方案");
            throw BizException.wrap("气表用气类型未配置，不能获取价格方案");
        }
        if(useGasType==null){
            log.error("气表用气类型未配置，不能获取价格方案");
            throw BizException.wrap("气表用气类型未配置，不能获取价格方案");
        }
        JSONObject data=buildSendData(operType,getCardType(gasMeterVersion.getIcCardCoreMark()),getSupportFactory(),createPrice(meter,gasMeterVersion,useGasType));
        data.put("MeterID",meter.getGasMeterRealId()==null?0:meter.getGasMeterRealId());//  Long       表id，0,当上表后，该值为实际的表id （开户，购气，补卡，补气，换表）
        if(OrderSourceNameEnum.IC_RECHARGE.eq(gasMeterVersion.getOrderSourceName())){
            data.put("MeterType_s",nullDefault(gasMeterVersion.getIcCardCoreMark(),"999"));    //  string     气表类型 （开户，购气，补卡，补气，换表）6a:0;8a/8b/8h:1; 8c:2; 6e/6f:3;8b2:4;9a/9G:4;12F:10;12F2:11;12S:12;
            if(AmountMarkEnum.MONEY.eq(gasMeterVersion.getAmountMark())){
                data.put("OperTypeVer_i",CardUtils.OPERTYPE_MONEY);               //  Int        0 默认 0,1气量表 2金额表
            }else{
                data.put("OperTypeVer_i",CardUtils.OPERTYPE_GAS);               //  Int        0 默认 0,1气量表 2金额表
            }
        }else{
            data.put("MeterType_s","-1");    //ID卡
        }
        GasMeterFactory factory= gasMeterFactoryService.getById(meter.getGasMeterFactoryId());
        data.put("CommpanyCode_s",nullDefault(factory.getGasMeterFactoryMarkCode(),"2")); //  String     公司码，秦川是“2” 必须传
        data.put("CommpanyNo_s",nullDefault(factory.getGasMeterFactoryCode(),"01"));      //  String     公司编号，秦川是“01” 必须传
        if(meter!=null){
            CardInfo cardInfo=cardInfoService.getOne(new LbqWrapper<CardInfo>()
                    .eq(CardInfo::getGasMeterCode,meter.getGasCode())
                    .eq(CardInfo::getDataStatus,DataStatusEnum.NORMAL.getValue())
            );
            if(cardInfo!=null){
                data.put("CardNo_s",cardInfo.getCardNo());
            }
        }
        String max="2000";
        //表端最大储蓄量，金额或气量
        if(useGasType.getMaxDeposit()!=null){
            max=useGasType.getMaxDeposit().toBigInteger().toString();
        }
        int alarm=5;
        if(useGasType.getAlarmGas()!=null){
            alarm=useGasType.getAlarmGas().intValue();
        }
        data.put("AlarmValue_i",alarm);
        data.put("MaxSumRechargeGas_i",max);
        data.put("meterVersion",gasMeterVersion);
        return data;
    }

    private GasMeterInfo setMeterInfo(String gasMeterCode,String customerCode, JSONObject data){
        GasMeterInfo meterInfo= gasMeterInfoService.getByMeterAndCustomerCode(gasMeterCode,customerCode);
        data.put("OneGas_i",decimalToString(meterInfo.getValue1(),getDigit(data)));   //  string     本次充值气量13.13  （开户，购气，补卡，补气，换表）
        data.put("TwoGas_i",decimalToString(meterInfo.getValue2(),getDigit(data)));   //  string     上次充值气量 （开户，购气，补卡，补气，换表）
        data.put("ThreeGas_i",decimalToString(meterInfo.getValue3(),getDigit(data))); //  string     上上次充值气量 （开户，购气，补卡，补气，换表）
        int rechargeCount=1;
        //为什么要这么做呢？如果充值了，默认是1，不用变更，如果没充值是0，那么上表的时候不能为0，所以要修改设置。
        if(meterInfo.getTotalRechargeMeterCount()==null || meterInfo.getTotalRechargeMeterCount().intValue()==0){
            GasMeterInfo update=new GasMeterInfo();
            update.setTotalRechargeMeterCount(1);
            update.setId(meterInfo.getId());
            gasMeterInfoService.updateById(update);
        }else{
            rechargeCount=meterInfo.getTotalRechargeMeterCount();
        }
        data.put("RechargeTimes_i",rechargeCount);        //  Int     充值次数 （开户，购气，补卡，补气，换表）
        return meterInfo;
    }


    private JSONObject buildSendData(int operType,int cardType,JSONArray factories,JSONObject price){
       return buildSendData(0,9600,cardType,operType,factories,price);
    }

    private JSONObject buildSendData(int comNo,int baud,int cardType,int operType,JSONArray factories,JSONObject price){
//        { "Baud_i": 9600,"Cardinfo": 51, "CommpanyCode_s": "01", "CommpanyNo_s": "01", "ComNo_i": 0,
//        "inGuardKey1_s": "5140166053543679562", "inTransportKey1_s": "2547246818369445541",
//        "InValues_s": null, "MaxSumRechargeGas_i": "", "MeterID": 0, "MeterType_s": "",
//        "OneGas_i": "", "OperType_i": 6, "OperTypeVer_i": 0, "SupportedKernel": ["01", "08", "15"],
//        "UseID_i": 0 }
//
//        {"AlarmValue_i":"5","AreaCode_s":"1","Baud_i":9600,"BCardTimes_i":0,
//        "BGasTimes_i":0,"Cardinfo":51,"CardNo_s":"00000061","CommpanyCode_s":"2",
//        "CommpanyNo_s":"01","ComNo_i":0,"FlowValue_i":"0","inGuardKey1_s":"5140166053543679562",
//        "inTransportKey1_s":"2547246818369445541","InValues_s":null,"MaxSumRechargeGas_i":"2000",
//        "MeterID":0,"MeterType_s":"11","OneGas_i":"12.13","OperType_i":6,"OperTypeVer_i":2,"overdraftGas_i":0,
//        "Price_i":"","pwd_s":"","RechargeTimes_i":1,"SumRechargeGas_i":"12.13","SupportedKernel":["01","08","15"],
//        "ThreeGas_i":"0","TwoGas_i":"0","UseID_i":0,"UserNO_s":"","UserType_i":1}

        JSONObject data=new JSONObject();
        //用气类型上面的告警设置
        data.put("AlarmValue_i",5);                //  Int     报警气量 5  ，（开户，购气，补卡，补气，换表）
        //默认  未设置
        data.put("AreaCode_s","");                  //  String     区域码，默认空
        data.put("ComNo_i",comNo);                    //  Int     串口号 0  默认
        data.put("Baud_i",baud);                    //  int     波特率 9600 默认
        data.put("BCardTimes_i",0);                //  Int     0  默认 补卡次数？
        data.put("BGasTimes_i",0);                 //  int     0  默认 补气次数？

        //参数设置，秦川内部使用默认就可以，其他厂家如果有独立的配置需要扩展。系统配置文件配置关系
        data.put("Cardinfo",cardType);              //  int     21是4442卡，51是102卡。默认51

        //表具对应卡信息设置
        data.put("CardNo_s","");                    //  String     卡号，都需要传（开户，购气，补卡，补气，换表）

        //厂家信息设置
        data.put("CommpanyCode_s","2");              //  String     公司码，秦川是“2” 必须传
        data.put("CommpanyNo_s","01");                //  String     公司编号，秦川是“01” 必须传

        //默认 未设置
        data.put("FlowValue_i","0");                 //  String     “0” 默认
        data.put("inGuardKey1_s",CardUtils.gkey);             //  string     秘钥 5140166053543679562 必须传
        data.put("inTransportKey1_s",CardUtils.tkey);         //  string     秘钥 2547246818369445541 必须传
        data.put("InValues_s",null);                //  string     默认Null

        // 用气类型上面表端储蓄量设置
        data.put("MaxSumRechargeGas_i","2000");         //  string     最大充值气量“2000”

        //表具档案设置
        data.put("MeterID",0);                     //  Long       表id，0,当上表后，该值为实际的表id （开户，购气，补卡，补气，换表）

        //版号设置--IC卡内核标识
        data.put("MeterType_s","");                 //  string     气表类型 （开户，购气，补卡，补气，换表）6a:0;8a/8b/8h:1; 8c:2; 6e/6f:3;8b2:4;9a/9G:4;12F:10;12F2:11;12S:12;

        //参数传入
        data.put("OperType_i",operType);            //  Int        操作类型，开户=0，购气=1，补卡=2，补气=3，退气=4，换表=5，读卡=6，回收=7

        //根据版号配置设置
        data.put("OperTypeVer_i",0);               //  Int        0 默认 0,1气量表 2金额表

        //开户卡充值记录设置 ，其他时候气表使用信息设置
        data.put("OneGas_i","0");                    //  string     本次充值气量13.13  （开户，购气，补卡，补气，换表）
        data.put("TwoGas_i","0");                    //  string     上次充值气量 （开户，购气，补卡，补气，换表）
        data.put("ThreeGas_i","0");                  //  string     上上次充值气量 （开户，购气，补卡，补气，换表）
        data.put("RechargeTimes_i","0");             //  Int     充值次数 （开户，购气，补卡，补气，换表）

        //默认 未设置
        data.put("overdraftGas_i",0);              //  int     0 是否允许透支  默认0

        //金额表-通过接口获取价格方案设置
        data.put("Price_i",price==null?"":price);                     //  String     阶梯价格，对应的json字符串（如下）金额表必传（开户，购气，补卡，补气，换表），气量表传“”

        //默认 未设置
        data.put("pwd_s","");                       //  String     密码 “” 默认
        data.put("SumRechargeGas_i","");            //  String     总充值气量/金额 默认可以为空

        //外部传入
        data.put("SupportedKernel",factories);      //  String     ["01"]支持公司的代码，01是秦川 必须传

        //客户信息设置
        data.put("UseID_i",0);                     //  Int     用户id
        data.put("UserNO_s","");                    //  String     默认“”
        data.put("UserType_i",1);                  //  String     默认 1
        return data;
    }

    private JSONObject createPrice(GasMeter meter,GasMeterVersion gasMeterVersion,UseGasType useGasType){
        if(!OrderSourceNameEnum.IC_RECHARGE.eq(gasMeterVersion.getOrderSourceName()) || !AmountMarkEnum.MONEY.eq(gasMeterVersion.getAmountMark())){
            return null;
        }
        JSONObject price = new JSONObject();
        Boolean singleMoneyMeter=gasMeterVersion.getSingleLevelAmount()==null?false:gasMeterVersion.getSingleLevelAmount().intValue()==1?true:false;
        Map<String,Object> map= cardPriceSchemeService.getScheme( meter, gasMeterVersion, useGasType);
        PriceScheme scheme=(PriceScheme) map.get("scheme");
        //是否是预调价
        Boolean isPreScheme=false;
        //是否立即调价(需要和上次方案做比较，该方案如果是已发的，不立即调价，如果是未发的需要立即调价，这个时候需要看是否清零)
        Boolean isImmediatelyAdj=(Boolean) map.get("isImmediatelyAdj");
        if(scheme.getStartTime().isAfter(LocalDate.MAX)) {
            isPreScheme=true;
        }
        int stepNum;
        if(PriceType.FIXED_PRICE.eq(useGasType.getPriceType())){
            stepNum=1;
        }else{
            if(singleMoneyMeter){
                stepNum=1;
            }else{
                stepNum=getMaxStepNum(useGasType,scheme,gasMeterVersion);
            }
        }
        //该参数是否立即调价的意思？不明确
        price.put("niAdjFlag",true);                   //  Bool     true  可用
        //调价年月日
        price.put("niYear",scheme.getStartTime()==null?0:scheme.getStartTime().getYear());//  int     调价年份
        price.put("niMonth",scheme.getStartTime()==null?"0":scheme.getStartTime().getMonthValue()+"");//  string     调价月
        price.put("niDay",scheme.getStartTime()==null?"0":scheme.getStartTime().getDayOfMonth()+"");//  string     调价日
        if(isPreScheme) {
            price.put("niAdjType",0);                       //  Int     调价类型 默认0
            price.put("niAdjZero",false);//肯定不能立即清零
            //预调价清零应该是调价时候，根据是否清零标识，清零
            if(scheme.getPriceChangeIsClear()==1){
                price.put("niAdjPriceMonth", scheme.getStartTime()==null?1:scheme.getStartTime().getMonthValue());//  int     清零月
                price.put("niExecDay", scheme.getStartTime()==null?1:scheme.getStartTime().getDayOfMonth());//  Int     清零日
                price.put("niAdjPriceStartYear", scheme.getStartTime()==null?0:scheme.getStartTime().getYear()); //  int     清零年份 默认0
            }else{
                //不清零，预调价格周期清零---,这个需要表端自己清零
                price.put("niAdjPriceMonth", scheme.getCycleEnableDate()==null?1:scheme.getCycleEnableDate().getMonthValue());//  int     清零月
                price.put("niExecDay", scheme.getCycleEnableDate()==null?1:scheme.getCycleEnableDate().getDayOfMonth());//  Int     清零日
                price.put("niAdjPriceStartYear", scheme.getCycleEnableDate()==null?0:scheme.getCycleEnableDate().getYear()); //  int     清零年份 默认0
            }
        }else{
            price.put("niAdjType",0);                       //  Int     调价类型 默认0
            //都是周期才清零
            price.put("niAdjPriceMonth", scheme.getCycleEnableDate()==null?1:scheme.getCycleEnableDate().getMonth().getValue());//  int     清零月
            price.put("niExecDay", scheme.getCycleEnableDate()==null?1:scheme.getCycleEnableDate().getDayOfMonth());//  Int     清零日
            price.put("niAdjPriceStartYear", scheme.getCycleEnableDate()==null?0:scheme.getCycleEnableDate().getYear()); //  int     清零年份 默认0
            if(isImmediatelyAdj){
                //立即调价需要判断是否立即清零
                if(scheme.getPriceChangeIsClear()==1){
                    price.put("niAdjZero",true);
                }else{
                   price.put("niAdjZero",false);
                }
            }else{
                if(scheme.getPriceChangeIsClear()==1){

                    //不是立即调价,不能清零，并且
                    price.put("niAdjZero",true);//肯定不能立即清零
                }else {

                    //不是立即调价,不能清零，并且
                    price.put("niAdjZero",false);//肯定不能立即清零
                }
            }
        }

        //价格类型--用气类型对应的一个编号的话，按道理可以不用变。只用变方案号，这里类型固定写死1
        price.put("niPriceKind",useGasType.getUseTypeNum()==null?1:useGasType.getUseTypeNum()); //  Int     价格类型 对应用气类型
        price.put("niPriceNum",scheme.getPriceNum());//  int     价格方案
        price.put("niPriceStep",convertSchemaCycle(scheme.getCycle()));//  int     周期，1-6是月份，7是一年
        price.put("niStepNum",stepNum);//  int     阶梯数

        price.put("niGasGate1", "0");//  string     二阶梯气量 只能是整数例如"1"
        price.put("niBasePrice1","0");//  String     二阶梯单价
        price.put("niGasGate2", "0");//  string     二阶梯气量 只能是整数例如"1"
        price.put("niBasePrice2","0");//  String     二阶梯单价
//        setPriceVal(price,stepNum,scheme,gasMeterVersion);

        //固定价
        if(PriceType.FIXED_PRICE.eq(useGasType.getPriceType()) ){
            price.put("niBasePrice0",scheme.getFixedPrice());//  String     一阶梯单价
            price.put("niGasGate0","65535");//  string     一阶梯气量 只能是整数例如"1"
        }else{
            //单阶金额表，始终使用第一阶梯
            if(singleMoneyMeter){
                price.put("niBasePrice0",scheme.getPrice1());//  String     一阶梯单价
                price.put("niGasGate0","65535");//  string     一阶梯气量 只能是整数例如"1"
            }else{
                //根据阶梯数设置
                setPriceVal(price,stepNum,scheme,gasMeterVersion);
            }
        }
        return price;
    }

//    private Map<String,Object> getScheme(GasMeter meter,GasMeterVersion gasMeterVersion,UseGasType useGasType){
//        String gasMeterCode=meter.getGasCode();
//        Map<String,Object> map=new HashMap<>();
//        PriceScheme scheme=calculateService.queryPriceScheme(meter.getGasCode(), LocalDate.now()).getData();
//        PriceMapping priceMapping = priceMappingService.getGasMeterPriceMapping(gasMeterCode);
//        //只有0-127,直接返回当前方案
//        if (priceMapping == null || priceMapping.getPriceId()==null || priceMapping.getPriceId().longValue()==0L) {
//            map.put("scheme",scheme);
//            map.put("isImmediatelyAdj",true);
//            if(priceMapping==null){
//                PriceMapping mapping=new PriceMapping();
//                mapping.setPriceId(0L);
//                mapping.setPriceNum(scheme.getPriceNum());
//                mapping.setUseGasTypeNum(useGasType.getUseTypeNum());
//                mapping.setPrePriceNum(1);
//                mapping.setPreUseGasTypeNum(1);
//                mapping.setPrePriceId(scheme.getId());
//                mapping.setGasMeterCode(meter.getGasCode());
//                priceMappingService.save(mapping);
//            }
//            return map;
//        }else {
//            //上表回调的时候，就是下次读卡并且回写表ID的时候说明该次方案已经写入。
//            //pricemapping中只记录已成功上表的方案
//            Long pre = priceMapping.getPriceId();
//            //这里要判断如果上次本来就是预调价，看看是否有新方案取代上次的预调价
//            PriceScheme pres=priceSchemeService.getById(pre);
//            if(pres.getStartTime().isBefore(LocalDate.now())){
//                //上表方案今天之前，已生效，那么当前方案正常一定时等于的，如果不等说明中途有调价，会立即调价
//            }else if(pres.getStartTime().isEqual(LocalDate.now())){
//                //上表方案今天生效，说明方案已经上表，这时如果不等继续调价，如果相等，查找是否有预调价
//            }else{
//                //上表方案本来是预调价，并且还没生效？怎么处理呢？
//                // 中途又建立了一个有效方案，并且已生效？怎么办，肯定不等，立即调价，并清空预调价，设置为周期日？ 中途建立的一个待生效，生效时间还比上个预期调价早？
//                // 如果中途没建立,现在调价方案默认为只有一个预调价，所以不存在该问题。
//            }
//            if(scheme.getId().equals(pre)) {
//                //本次等于上次，查询是否有预期调价方案
//                PriceScheme nextScheme=priceSchemeService.queryPrePrice(useGasType.getId());
//                if(nextScheme!=null) {
//                    map.put("scheme",nextScheme);
//                    map.put("isImmediatelyAdj",false);
//                    PriceMapping mapping=new PriceMapping();
//                    mapping.setPrePriceId(nextScheme.getId());
//                    mapping.setId(priceMapping.getId());
//                    priceMappingService.updateById(mapping);
//                    return map;
//                }else {
//                    map.put("scheme",scheme);
//                    map.put("isImmediatelyAdj",false);
//                    return map;
//                }
//            }else{
//                //当前方案不等于上次方案，正常情况上次不管是预调价还是当时的当前方案，只要不相等，都应该立即调价
//                //上次是预调价呢？上次是预期调价，这次和上次的当前相同，怎么处理呢？不记录预调价，但是不记录预调价也有问题，当预调价已生效，本次来就没法判断是否方案已生效？
//
//                map.put("isImmediatelyAdj",true);
//                map.put("scheme",scheme);
//                PriceMapping mapping=new PriceMapping();
//                mapping.setPrePriceId(scheme.getId());
//                mapping.setId(priceMapping.getId());
//                priceMappingService.updateById(mapping);
//                return map;
//            }
//        }
//    }
    private void setPriceVal(JSONObject price,int stepNum,PriceScheme scheme,GasMeterVersion gasMeterVersion){
        price.put("niBasePrice0",priceSchemaConvert(scheme.getPrice1()));//  String     一阶梯单价
        price.put("niGasGate0",gasSchemaConvert(scheme.getGas1()));//  string     一阶梯气量 只能是整数例如"1"
        if(stepNum>1){
            price.put("niGasGate1", gasSchemaConvert(scheme.getGas2()));//  string     二阶梯气量 只能是整数例如"1"
            price.put("niBasePrice1",priceSchemaConvert(scheme.getPrice2()));//  String     二阶梯单价
        }
        if(stepNum>2){
            price.put("niGasGate2", gasSchemaConvert(scheme.getGas3()));//  string     三阶梯气量 只能是整数例如"1"
            price.put("niBasePrice2",priceSchemaConvert(scheme.getPrice3()));//  string     三阶梯单价
        }
    }

    private int getMaxStepNum(UseGasType useGasType,PriceScheme scheme,GasMeterVersion gasMeterVersion){
        BigDecimal max=new BigDecimal("65535.00");
        if(PriceType.FIXED_PRICE.eq(useGasType.getPriceType())){
            return 1;
        }else{
            int stepNum=0;
            //是否是单阶计价
            if(gasMeterVersion.getSingleLevelAmount()!=null && gasMeterVersion.getSingleLevelAmount().intValue()==1){
                stepNum=1;
                scheme.setGas1(max);
            }else{
                //校验三阶以内是否都小于65535
                if(scheme.getGas1().compareTo(max)>=0){
                    stepNum=1;
                    scheme.setGas1(max);
                }else if(scheme.getGas2()!=null && scheme.getGas2().compareTo(max)>=0){
                    stepNum=2;
                    scheme.setGas2(max);
                }else if(scheme.getGas3()!=null && scheme.getGas3().compareTo(max)>=0){
                    stepNum=3;
                    scheme.setGas3(max);
                }else{
                    stepNum=3;
                    scheme.setGas3(max);
                }
            }
            return stepNum;
        }
    }

    /**
     * 21是4442卡，51是102卡。默认51
     * 公司12系表之前都是102卡，12系（12F2,12F,12S）都是4442卡
     * @param cardCore
     * @return
     */
    private int getCardType(String cardCore){
        if(cardCoreConf==null){
            cardCoreConf=new HashMap<>();
        }
        if(StringUtils.isBlank(cardCore)){
            return DEFAULT_CARD_TYPE;
        }
        for (Map.Entry<String, String> mapEntry : cardCoreConf.entrySet()) {
            String[] cores=mapEntry.getValue().split(",");
            for (String core : cores) {
                if(cardCore.equals(core)){
                    return Integer.parseInt(mapEntry.getKey());
                }
            }
        }
        return DEFAULT_CARD_TYPE;

        //6a:0;     8a/8b/8h:1;  8c:2; 6e/6f:3;  8b2:4;  9a/9G:4;
        //12F:10;   12F2:11;     12S:12;
//        if(cardCore.equals("11") || cardCore.equals("12") || cardCore.equals("10")){
//            return 21;
//        }
//        return 51;
    }

    private String priceSchemaConvert(BigDecimal val){
        if(val==null){
            return "";
        }else{
            return val.setScale(2, RoundingMode.UP).toPlainString();
        }
    }
    private Boolean adjZeroConvert(Integer val){
        if(val==null || val.intValue()==0){
            return false;
        }
        return true;
    }

    private int convertSchemaCycle(Integer val){
        if(val==null){
            return 1;
        }
        if(val.intValue()==12){
            return 7;
        }
        return val.intValue();
    }

    private String gasSchemaConvert(BigDecimal val){
        if(val==null){
            return "";
        }else{
            return Integer.parseInt(val.setScale(0).toPlainString())+"";
        }
    }


    private String nullDefault(String value,String defaultValue){
        if(value==null) {
            if(defaultValue==null) {
                defaultValue="";
            }
            return defaultValue;
        }
        return value;
    }
    private String decimalToString(BigDecimal value,int digit){
        if(value==null) {
            return "0";
        }
        if(value.compareTo(BigDecimal.ZERO)==0){
            return "0";
        }
        return value.setScale(digit).toPlainString();
    }

    private JSONArray getSupportFactory(){
        JSONArray arr=new JSONArray();
        arr.add("01");
//        arr.add("08");
//        arr.add("15");
        return arr;
    }

    private CardInfo getCardInfoByCardNo(String cardNo){

        List<CardInfo> list=cardInfoService.list(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getCardNo,cardNo)
                .eq(CardInfo::getDataStatus, DataStatusEnum.NORMAL.getValue())
        );

        if(org.apache.commons.collections4.CollectionUtils.isEmpty(list)){
            throw BizException.wrap("卡号无效");
        }
        if(list.size()>1){
            throw BizException.wrap("卡号重复，需要特殊处理");
        }
        return list.get(0);
    }
}
