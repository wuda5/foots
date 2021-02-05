package com.cdqckj.gmis.card.util;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.function.Function;

@Log4j2
public class CardUtils {



    /**
     *公司内部8系表需要的密钥
     */
    public final static String gkey="5140166053543679562";
    /**
     *公司内部8系表需要的密钥
     */
    public final static String tkey="2547246818369445541";

//开户=0，购气=1，补卡=2，补气=3，退气=4，换表=5，读卡=6，回收=7
    /**
     * 开户
     */
    public final  static int OPEN_ACCOUNT=0;
    /**
     * 购气
     */
    public final  static int BUY_GAS=1;
    /**
     * 补卡
     */
    public final  static int REP_CARD=2;
    /**
     * 补气
     */
    public final  static int REP_GAS=3;
    /**
     * 退气
     */
    public final  static int BACK_GAS=4;
    /**
     * 换表
     */
    public final  static int REP_METER=5;
    /**
     * 读卡
     */
    public final  static int READ_CARD=6;
    /**
     * 回收
     */
    public final  static int REC_CARD=7;

    /**
     * 操作类型版本参数值，默认0气量
     */
    public final  static int OPERTYPE_DEFAULT=0;

    /**
     * 操作类型版本参数值，2 金额
     */
    public final  static int OPERTYPE_MONEY=2;

    /**
     * 操作类型版本参数值，1气量
     */
    public final  static int OPERTYPE_GAS=1;

//    Identification_s	string	“” 默认
//    CardType_i	string	0 默认
//    CardNo_s	string	卡号"00000060"
//    UseID_i	Int	用户id
//    RechargeTimes_i	Int	充值次数
//    MeterID	Long	表id，0,当上表后，该值为实际的表4294967295
//    ReturnFlag	Int	返回标志 0 成功
//    ReturnCode_i	Int	返回标志 0 成功
//    ReturnMsg_s	string	返回信息，例如："成功!"
//    OperType_i	Int	操作类型，开户=0，购气=1，补卡=2，补气=3，退气=4，换表=5，读卡=6，回收=7
//    OperTypeVer_i	Int	0，1
//    OneGas_i	string	本次充值气量13.13，不能充值
//    UserNO_s	String	默认“”
//    OutValues_s	String	“” 输出参数
//    CommpanyNo_s	String	公司编号
//    MeterType_s	String	表类型

    public static void valid(JSONObject jsonObject){
        //5 秦川新卡 ，-7其他公司新卡，暂时是这样解释，其他 通过 retrunCode判断是否成功
        if(jsonObject.getIntValue("ReturnCode_i")!=0
                && jsonObject.getIntValue("ReturnCode_i")!=-7
                && jsonObject.getIntValue("ReturnCode_i")!=5){
            log.error("卡操作失败: "+jsonObject.toJSONString());
            throw BizException.wrap("卡操作失败: "+jsonObject.getString("ReturnMsg_s"));
        }

//        if(!jsonObject.containsKey("ReturnFlag") && !jsonObject.containsKey("ReturnCode_i")){
//            throw BizException.wrap("读写卡无响应数据传入接口，请求失败");
//        }
//        if(jsonObject.getIntValue("ReturnFlag")!=0 ){
//            throw BizException.wrap("卡操作失败："+jsonObject.getString("ReturnMsg_s"));
//        }else{
//            if(jsonObject.getIntValue("ReturnCode_i")==-1
//                    || jsonObject.getIntValue("ReturnCode_i")==-2
//                    || jsonObject.getIntValue("ReturnCode_i")==-3
//                    || jsonObject.getIntValue("ReturnCode_i")==100
//                    || jsonObject.getIntValue("ReturnCode_i")==27
//                    ||  jsonObject.getIntValue("ReturnCode_i")==16
//            ){
//                //100 卡类型102 4442不对 ，-1 串口失败 -2读卡器状态获取失败 -3读卡器中无卡
//                //27 不支持的表型号  16 写卡数据校验错误
//                throw BizException.wrap("卡操作失败："+jsonObject.getString("ReturnMsg_s"));
//            }
//        }

    }

    public static void validCompanyCode(String companyCode,String cardCore,JSONObject jsonObject){


    }


    public static Boolean isNewCard(JSONObject jsonObject){
        valid(jsonObject);
//        String cardNo=null;
//        if(jsonObject.containsKey("CardNo_s")){
//            cardNo= jsonObject.getString("CardNo_s");
//        }
//        String msg=jsonObject.getString("ReturnMsg_s");
        if(jsonObject.getIntValue("ReturnCode_i")==-7
                || jsonObject.getIntValue("ReturnCode_i")==5){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断卡上是否余额
     * @param jsonObject
     * @return
     */
    public static Boolean hasBalance(JSONObject jsonObject){
        BigDecimal balance;
        if(jsonObject.containsKey("OneGas_i")){
            balance=jsonObject.getBigDecimal("OneGas_i");
            if(balance==null){
                return false;
            }
            if(balance.compareTo(BigDecimal.ZERO)>0){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取卡上金额或气量
     * @param jsonObject
     * @return
     */
    public static BigDecimal getBalance(JSONObject jsonObject){
        BigDecimal balance=new BigDecimal("0.00");
        if(jsonObject.containsKey("OneGas_i")){
            balance=jsonObject.getBigDecimal("OneGas_i");
            if(balance==null){
                balance=new BigDecimal("0.00");
            }
        }
        return balance;
    }

    /**
     * 获取卡上累计量----注意暂时无
     * @param jsonObject
     * @return
     */
    public static BigDecimal getTotalAmount(JSONObject jsonObject){
        BigDecimal total=null;
        if(jsonObject.containsKey("SumRechargeGas_i")){
            total=jsonObject.getBigDecimal("SumRechargeGas_i");
            if(total!=null && total.longValue()==0l){
                return null;
            }
        }
        return total;
    }
    /**
     * 获取卡上充值次数
     * @param jsonObject
     * @return
     */
    public static Integer getReChargeCount(JSONObject jsonObject){
        Integer count=0;
        if(jsonObject.containsKey("RechargeTimes_i")){
            count=jsonObject.getInteger("RechargeTimes_i");
            if(count==null ){
                return 0;
            }
        }
        return count;
    }

    public static Long getMeterRealId(JSONObject jsonObject){
        Long meterId=null;
        if(jsonObject.containsKey("MeterID")){
            meterId=jsonObject.getLong("MeterID");
            if(meterId!=null && meterId.longValue()==0l){
                return null;
            }
        }
        return meterId;
    }

    public static int getOperType(JSONObject jsonObject){
        return jsonObject.getIntValue("OperType_i");
    }

    public static String getCardNumber(JSONObject jsonObject){
        return jsonObject.getString("CardNo_s");
    }
    /**
     * 获取到系统生成的卡号，应该要在内部做转换，该动作必须在写卡上做区分，否则读卡始终无法区分。
     * @param jsonObject
     * @return
     */
    public static String getGasMeterCode(JSONObject jsonObject, Function<String, String> transCardNoToGasMeterCode){
        //历史卡号导入过来是否做映射？做映射历史卡号，那么读取卡号就需要先去找个新卡号，
        // 但是如果是这样，新发卡号如果和历史卡号重复，这个时候没法区分

        //所以不应该做映射，直接存储，一个燃气公司内部卡号不应该重复？但是如果发新卡使用9.0的规则可能和原来重复，
        // 解决该问题就只能对接原公司声称卡号规则，及基数沿用。否则所有卡都可能作废重新处理。

        //各个厂家卡号规则不一致，需要独立处理
        String cardNo= getCardNumber(jsonObject);
        //卡号查找表号，对于卡号有特殊处理需要在这里处理。
        if(transCardNoToGasMeterCode!=null){
           return transCardNoToGasMeterCode.apply(cardNo);
        }
        return null;
    }
}
