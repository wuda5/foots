package com.cdqckj.gmis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.dto.GasMeterInfoVO;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.api.CardOperBizApi;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.entity.dto.CardLibraryResult;
import com.cdqckj.gmis.entity.vo.GasCardMeterInfoVO;
import com.cdqckj.gmis.entity.vo.GasMeterICVO;
import com.cdqckj.gmis.service.ICardService;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import com.cdqckj.gmis.userarchive.vo.CustomerPageVo;
import com.cdqckj.gmis.utils.HttpClientUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * ICard管理实现类
 * @author HC
 */
@Service("iCardService")
public class ICardServiceImpl implements ICardService {

    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;

    @Autowired
    private ChargeRecordBizApi chargeRecordBizApi;

    @Autowired
    private CustomerBizApi customerBizApi;

    @Autowired
    private CardOperBizApi cardOperBizApi;

    @Autowired
    private CardInfoBizApi cardInfoBizApi;

    /**卡库服务地址**/
    private static String cardLibrary_URL = "http://localhost:8787/cardServer/card";
    /**获取卡密码**/
    private static String getPwd_URI = "/getPwd";
    /**读整卡数据**/
    private static String readCard_URI = "/read2";

    /**写卡数据**/
    private static String writeCard_URI = "/writeOrders";

    @Override
    public R<GasMeterICVO> getICCardPWD(String encryptCardInfo) {
        GasMeterICVO result = new GasMeterICVO();

        HashMap<String,String> clientData = new HashMap<>();
        clientData.put("data",encryptCardInfo);
        JSONObject jsonObject = sendPostRequest(cardLibrary_URL + getPwd_URI, clientData);
        result.setCardType(Integer.valueOf(jsonObject.get("carType").toString()));
        result.setPassword(jsonObject.get("pwd").toString());
        return R.success(result);
    }

    @Override
    public R<HashMap<String, Object>> getUserInfo(String encryptCardInfo, Integer cardType) {

        HashMap<String,Object> data = new HashMap<>();

        GasMeterInfoVO target = new GasMeterInfoVO();

        //根据卡头数据获取卡信息
        HashMap<String,String> clientData = new HashMap<>();
        clientData.put("data",encryptCardInfo);
        JSONObject jsonObject = sendPostRequest(cardLibrary_URL + readCard_URI, clientData);
        if(null == jsonObject){
            return R.fail("卡库服务调用失败 ");
        }
        //卡号
        String nCardCode = jsonObject.get("nCardCode").toString();
        //表身号
        String niMeterID = jsonObject.get("niMeterID").toString();
        //购气量
        BigDecimal niBuyGas = (BigDecimal) jsonObject.get("niBuyGas");
        //购气次数
        Integer niBuyTimes = (Integer)jsonObject.get("niBuyTimes");
        //气表类型
        Integer niMeterType = (Integer)jsonObject.get("niMeterType");
        //卡类型
        Integer niCardType = (Integer)jsonObject.get("niCardType");

        GasCardMeterInfoVO cardMeterInfo = new GasCardMeterInfoVO();
        cardMeterInfo.setNCardCode(nCardCode);
        cardMeterInfo.setNiBuyGas(niBuyGas);
        cardMeterInfo.setNiBuyTimes(niBuyTimes);
        cardMeterInfo.setNiMeterID(niMeterID);
        cardMeterInfo.setNiMeterType(niMeterType);

        //获取用户自身所属表具信息
        String gasMeterCode = "";
        CardInfo cardInfo = new CardInfo();
        cardInfo.setDataStatus(DataStatusEnum.NORMAL.getValue());
        cardInfo.setCardNo(nCardCode);
        R<List<CardInfo>> listR = cardInfoBizApi.query(cardInfo);
        if(CollectionUtils.isNotEmpty(listR.getData())){
            gasMeterCode = listR.getData().get(0).getGasMeterCode();
        }else{
            return R.fail("未获取到卡信息");
        }

        CustomerPageVo cusQueryData = new CustomerPageVo();
        cusQueryData.setGasCode(gasMeterCode);
        cusQueryData.setDeleteStatus(0);
        cusQueryData.setPageNo(1);
        cusQueryData.setPageSize(30);
        R<Page<CustomerGasDto>> pageR = customerBizApi.findCustomerGasMeterPageTwo(cusQueryData);
        if(pageR.getIsError() || pageR.getData().getTotal()==0){
            return R.fail("表具信息不存在,请核实");
        }
        pageR.getData().getRecords().stream().forEach(item->{
            BeanUtils.copyProperties(item,target);
        });

        data.put("gasMeterInfo",target);
        data.put("cardMeterInfo",cardMeterInfo);

        //读卡回调
        readCardBack(nCardCode,niCardType,Long.parseLong(niMeterID),6);

        return R.success(data);
    }

    @Override
    public R<Object> getWriteData(String encryptCardInfo, Integer cardType, String payCode) {

        //获取订单数据
        R<ChargeRecord> record = chargeRecordBizApi.getChargeRecordByNo(payCode);
        ChargeRecord recordData = record.getData();
        if(recordData==null){
            return R.fail("未获取到相应的订单数据");
        }
        //代收费或收费中
        if(ChargeStatusEnum.UNCHARGE.getCode().equals(recordData.getChargeStatus())
                || ChargeStatusEnum.CHARGING.getCode().equals(recordData.getChargeStatus())){
            return R.fail("收费未成功,请稍后再试");
        }else if(ChargeStatusEnum.CHARGE_FAILURE.getCode().equals(recordData.getChargeStatus())){
            return R.fail("缴费失败");
        }

        //再读一次卡
        HashMap<String,String> clientData = new HashMap<>();
        clientData.put("data",encryptCardInfo);
        JSONObject jsonObject = sendPostRequest(cardLibrary_URL + readCard_URI, clientData);
        //卡号
        String nCardCode = jsonObject.get("nCardCode").toString();
        //表身号
        String niMeterID = jsonObject.get("niMeterID").toString();
        //购气量(卡上气量)
        BigDecimal niBuyGas = (BigDecimal) jsonObject.get("niBuyGas");
        //购气次数
        Integer niBuyTimes = (Integer)jsonObject.get("niBuyTimes");
        //气表类型
        Integer niMeterType = (Integer)jsonObject.get("niMeterType");
        //卡类型
        Integer niCardType = (Integer)jsonObject.get("niCardType");

        if(niBuyGas.compareTo(BigDecimal.ZERO)>0){
            return R.fail("卡上尚有未上表的数据,请先上表");
        }

        //写卡数据加载
        String gasMeterCode = recordData.getGasMeterCode();
        JSONObject readData = readCardDataLoad(nCardCode,gasMeterCode,niMeterID,niCardType,niBuyGas,niBuyTimes, 1,niMeterType);

        R<JSONObject> buyGasLoadR = cardOperBizApi.buyGasLoad(gasMeterCode, readData);
        JSONObject buyGasLoad = buyGasLoadR.getData();
        if(buyGasLoad==null){
            return R.fail("写卡数据加载失败");
        }
        //卡库调用
        HashMap<String,Object> data = new HashMap<>();
        //全卡数据
        data.put("databuff",encryptCardInfo);
        //气表类型
        data.put("meterType",niMeterType);
        //卡号
        data.put("nsCardno",nCardCode);
        //充值次数
        data.put("niCpuChgN",buyGasLoad.get("RechargeTimes_i"));
        //第一次充值
        data.put("oneValue",buyGasLoad.get("OneGas_i"));
        //第二次充值
        data.put("twoValue",buyGasLoad.get("TwoGas_i"));
        //第三次充值
        data.put("threeValue",buyGasLoad.get("ThreeGas_i"));

        LocalDate localDate = LocalDate.now();

        //年
        data.put("niYear",localDate.getYear());
        //月
        data.put("niMonth",localDate.getMonth());
        //日
        data.put("niDay",localDate.getDayOfMonth());

        //一阶梯气量
        data.put("niGasGate0",buyGasLoad.get("niGasGate0"));
        //一阶梯单价
        data.put("niBasePrice0",buyGasLoad.get("niBasePrice0"));
        //二阶梯气量
        data.put("niGasGate1",buyGasLoad.get("niGasGate1"));
        //二阶梯单价
        data.put("niBasePrice1",buyGasLoad.get("niBasePrice1"));
        //三阶梯气量
        data.put("niGasGate2",buyGasLoad.get("niGasGate2"));
        //三阶梯单价
        data.put("niBasePrice2",buyGasLoad.get("niBasePrice2"));
        //周期 1-6是月份,7是一年
        data.put("niPriceStep",buyGasLoad.get("niPriceStep"));
        //价格类型
        data.put("niPriceKind",buyGasLoad.get("niPriceKind"));
        //价格方案
        data.put("niPriceNum",buyGasLoad.get("niPriceNum"));
        //清零日
        data.put("niPriceSyncDay",buyGasLoad.get("niExecDay"));
        //阶梯数
        data.put("niStepNum",buyGasLoad.get("niStepNum"));
        //清零年
        data.put("niAdjPriceStartYear",buyGasLoad.get("niAdjPriceStartYear"));
        //清零月
        data.put("niAdjPriceMonth",buyGasLoad.get("niAdjPriceMonth"));
        //是否清零 默认 0
        data.put("niAdjZero",buyGasLoad.get("niAdjZero"));
        //报警气量
        data.put("alarmGas",buyGasLoad.get("AlarmValue_i"));
        //卡类型
        data.put("cardType",niCardType);

        HashMap<String,String> clientData1 = new HashMap<>();
        clientData.put("writeOrders",JSONObject.toJSONString(data));
        JSONObject jsonObject1 = sendPostRequest(cardLibrary_URL + writeCard_URI, clientData1);


        //写卡完成回调
        cardOperBizApi.buyGasCallBack(recordData.getGasMeterCode(),buyGasLoad);

        return R.success(jsonObject1);
    }

    /**
     * 读卡数据加载
     * @param nCardCode 卡号
     * @param gasMeterCode 表编码
     * @param niMeterID 表号
     * @param niCardType
     * @param niBuyGas 剩余气量
     * @param niBuyTimes 购气次数
     * @param operType_i 操作类型
     * @return
     */
    private JSONObject readCardDataLoad(String nCardCode, String gasMeterCode, String niMeterID, Integer niCardType,
                                        BigDecimal niBuyGas, Integer niBuyTimes,Integer operType_i,Integer niMeterType) {

        R<JSONObject> cardLoad = cardOperBizApi.readCardLoad();

        JSONObject data = cardLoad.getData();
        data.put("MeterID",niMeterID);
        data.put("OperType_i",operType_i);
        data.put("CardNo_s",nCardCode);
        data.put("MeterType_s",niMeterType);
        data.put("Cardinfo",niCardType);
        return data;

    }

    private JSONObject sendPostRequest(String url,HashMap<String,String> clientData){
        JSONObject data = null;
        try {
            String postResponse = HttpClientUtils.sendPostRequest(url, clientData, null, null);
            CardLibraryResult cardLibraryResult = JSONObject.parseObject(postResponse, CardLibraryResult.class);
             data = (JSONObject) cardLibraryResult.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 读卡回调
     * @auther hc
     * @date 2021/1/22
     * @param cardType 卡类型
     * @param cardNo 卡号
     * @param operType 操作类型
     */
    private void readCardBack(String cardNo,Integer cardType,Long meterID,Integer operType){
//        R<JSONObject> cardLoad = cardOperBizApi.readCardLoad();

//        Identification_s	string	“” 默认
//        CardType_i	string	0 默认
//        CardNo_s	string	卡号"00000060"
//        UseID_i	Int	用户id
//        RechargeTimes_i	Int	充值次数
//        MeterID	Long	表id，0,当上表后，该值为实际的表4294967295
//        ReturnFlag	Int	返回标志 0 成功
//        _i	Int	返回标志 0 成功
//        ReturnMsg_s	string	返回信息，例如："成功!"
//        OperType_i	Int	操作类型，开户=0，购气=1，补卡=2，补气=3，退气=4，换表=5，读卡=6，回收=7
//        OperTypeVer_i	Int	0，1
//        OneGas_i	string	本次充值气量13.13
//        UserNO_s	String	默认“”
//        OutValues_s	String	“” 输出参数
//        CommpanyNo_s	String	公司编号
//        MeterType_s	String	表类型
        JSONObject data = createCardResult();

        data.put("Identification_s","");//
        data.put("CardType_i",cardType);//
        data.put("CardNo_s",cardNo);//
        data.put("RechargeTimes_i",0);//
        data.put("MeterID",meterID); //
        data.put("ReturnFlag_i",0);//
        data.put("ReturnMsg_s","成功");//
        data.put("OperType_i",operType);//
        data.put("OneGas_i","");//
        data.put("CommpanyNo_s","");//
        data.put("MeterType_s","");//
        cardOperBizApi.readCardCallBack(data);
    }
    private JSONObject createCardResult(){
//        R<JSONObject> cardLoad = cardOperBizApi.readCardLoad();

//        Identification_s	string	“” 默认
//        CardType_i	string	0 默认
//        CardNo_s	string	卡号"00000060"
//        UseID_i	Int	用户id
//        RechargeTimes_i	Int	充值次数
//        MeterID	Long	表id，0,当上表后，该值为实际的表4294967295
//        ReturnFlag	Int	返回标志 0 成功
//        _i	Int	返回标志 0 成功
//        ReturnMsg_s	string	返回信息，例如："成功!"
//        OperType_i	Int	操作类型，开户=0，购气=1，补卡=2，补气=3，退气=4，换表=5，读卡=6，回收=7
//        OperTypeVer_i	Int	0，1
//        OneGas_i	string	本次充值气量13.13
//        UserNO_s	String	默认“”
//        OutValues_s	String	“” 输出参数
//        CommpanyNo_s	String	公司编号
//        MeterType_s	String	表类型

        JSONObject data = new JSONObject();
         data.put("Identification_s","");
         data.put("CardType_i","");
         data.put("CardNo_s","");
         data.put("UseID_i",0);
         data.put("RechargeTimes_i",0);
         data.put("MeterID",0);
         data.put("ReturnFlag_i",0);
         data.put("ReturnMsg_s","");
         data.put("OperType_i",0);
         data.put("OperTypeVer_i",0);
         data.put("OneGas_i","");
         data.put("UserNO_s","");
         data.put("OutValues_s","");
         data.put("CommpanyNo_s","");
         data.put("MeterType_s","");
         return data;
    }

}
