package com.cdqckj.gmis.common.utils;

import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.enums.BizFCode;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.SpringUtils;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 业务编码生成工具类
 * 规范： 流程编码 {@link com.cdqckj.gmis.common.enums.BizFCode}
 *       工单编码 {@link com.cdqckj.gmis.common.enums.BizOCode}
 *       业务编码 {@link com.cdqckj.gmis.common.enums.BizBCode}
 */
public class BizCodeUtil {
    /**==============================================标识==============================================**/
    /**
     * 流程 编码标识
     */
    public static String F="F";
    /**
     * 工单 编码标识
     */
    public static String O="O";
    /**
     * 业务数据 编码标识
     */
    public static String B="B";

    /**
     * 收费数据 编码标识
     */
    public static String C="C";

    /**
     * 业务收费单 编码标识
     */
    public static String BCO="BCO";

    /**
     * 退费数据 编码标识
     */
    public static String REFUND="CR";

    /**
     * 账户号 编码标识
     */
    public static String ACCT="ACCT";
    /**
     * 票据编号 编码标识
     */
    public static String RCPT="RCPT";
    /**
     * 发票编号 编码标识
     */
    public static String INVE="INVE";
    /**
     * 表身号 编码标识
     */
    public static String METER_NO="METERNO";

    /**==============================================redis存储key(流程任务)==============================================**/
    /**
     *报装自增编号
     */
    public static String TASK_INSTALL="sys:incr:task:install:id";

    /**
     *表身号
     */
    public static String GAS_NUM="sys:incr:gasmeter:num:id";
    /**
     *安检自增编号
     */
    public static String TASK_SECURITY="sys:incr:task:security:id";

    /**==============================================redis存储key(工单)==============================================**/
    /**
     *报装勘察自增编号
     */
    public static String ORDER_INSTALL_SERVEY="sys:incr:order:install:serveyid";
    /**
     *报装安装自增编号
     */
    public static String ORDER_INSTALL_SG="sys:incr:order:install:installid";
    /**
     *安检检查自增编号
     */
    public static String ORDER_SECURITY_CHECK="sys:incr:order:security:checkid";

    /**==============================================redis存储key(业务数据)==============================================**/
    /**
     *气表自增编号
     */
    public static String ARCHIVE_METER="sys:incr:bizdata:archive:meterid";
    /**
     *客户自增编号
     */
    public static String ARCHIVE_CUSTOMER="sys:incr:bizdata:archive:custid";

    /*
     * 气表出入库自增编号
     * */
    public static String ARCHIVE_METER_IN_OUT_STORE="sys:incr:bizdata:archive:inoroutstoreid";

    /**
     *节点自增编号
     */
    public static String ARCHIVE_NODE="sys:incr:bizdata:security:nodeid";


    /**==============================================redis存储key(收费)==============================================**/
    /**
     *充值
     */
    public static String RECHARGE="sys:incr:charges:recharge:rechargeid";
    /**
     *预存
     */
    public static String PRECHARGE="sys:incr:charges:precharge:prechargeid";
    /**
     *缴费
     */
    public static String CHARGE="sys:incr:charges:charge:chargeid";
    /**
     *缴费
     */
    public static String REFUND_KEY="sys:incr:charges:refund:refundid";
    /**
     *三方支付单号
     */
    public static String CHARGE_THIRD_PAY="sys:incr:charges:thirdpay:thirdpayid";

    /**
     *流水号
     */
    public static String ACCOUNT_SERIAL="sys:incr:charges:serial:serialid";
    /**
     *账户号
     */
    public static String ACCOUNT_NO="sys:incr:charges:account:acctno";
    /**
     *票据编号
     */
    public static String BILL_NO="sys:incr:charges:bill:billno";
    /**
     *发票编号
     */
    public static String INVOICE_NO="sys:incr:charges:account:invoiceno";





    /**==============================================redis存储key(号关业务)==============================================**/
    /**
     * 卡号
     */
    public static String CARD_NUMBER="sys:incr:card:cardnumber:cardnumber";

    /**
     * 卡号
     */
    public static String METER_CHARGE_NO="sys:incr:meter:chargeno:incrid";


    /**==============================================redis存储key(收费单相关编号)==============================================**/
    /**
     * 发卡收费单
     */
    public static String CHARGE_ORDER_ISSUECARD="sys:incr:chargeorder:issuecard:incrid";

    /**
     * 补卡收费单
     */
    public static String CHARGE_ORDER_REPCARD="sys:incr:chargeorder:repcard:incrid";

    /**
     * 补气收费单
     */
    public static String CHARGE_ORDER_REPGAS="sys:incr:chargeorder:repgas:incrid";

    /**
     * 开户收费单
     */
    public static String CHARGE_ORDER_OPENACCOUNT="sys:incr:chargeorder:opencount:incrid";

    /**
     * 过户收费单
     */
    public static String CHARGE_ORDER_TRANACCOUNT="sys:incr:chargeorder:tranaccount:incrid";

    /**
     * 拆表收费单
     */
    public static String CHARGE_ORDER_DISMETER="sys:incr:chargeorder:dismeter:incrid";

    /**
     * 换表收费单
     */
    public static String CHARGE_ORDER_REPMETER="sys:incr:chargeorder:repmeter:incrid";

    /**
     * 拆表记录
     */
    public static String REMOVE_METER_RECORD_NO="sys:incr:meter:remove:incrid";

    /**
     * 换表记录
     */
    public static String CHANGE_METER_RECORD_NO="sys:incr:meter:change:incrid";


    /**
     * 生成任务流程编码（报装，安检，收费，开户，过户等等）
     * @param bcode
     * @param redisKey
     * @return
     */
//    public static String genTaskFlowCode(BizFCode bcode, String redisKey){
//        return new StringBuilder(F).append(bcode.getCode())
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }

    /**
     * 生成客户缴费编号
     * @return
     */
//    public static String genCustomerChargeNo(){
//        return genCustomerChargeNo(8);
//    }

    /**
     * 生成客户缴费编号
     * @return
     */
//    public static String genCustomerChargeNo(Integer length){
//        return genCustomerChargeNo(length,null);
//    }

    /**
     * 生成客户缴费编号
     * @return
     */
//    public static String genCustomerChargeNo(Integer length,String pre){
//        RedisService redisService= (RedisService)SpringUtils.getBean("redisService");
//        Long incrId= redisService.incr(METER_CHARGE_NO,1l);
//        String idStr=String.valueOf(incrId);
//        if(StringUtils.isNotBlank(pre)){
//            length=length-pre.length();
//        }else{
//            pre="";
//        }
//        idStr= Strings.padStart(idStr,length,'0');
//        idStr=pre+idStr;
//        return idStr;
//    }

    /**
     * 生成业务收费单编码
     * @param bcode
     * @param redisKey
     * @return
     */
//    public static String genBizChargeNumber(BizFCode bcode, String redisKey){
//        return new StringBuilder(BCO).append(bcode.getCode())
////                .append(areaCode).append(zoneCode)
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }

    /**
     * 生成卡号
     * @return
     */
    public static String genCardNumber(){
        return genCardNumber(8);
    }

    /**
     * 生成卡号
     * @return
     */
    public static String genCardNumber(int length){
        return genCardNumber(length,null);
    }

    /**
     * 生成卡号
     * @return
     */
    public static String genCardNumber(int length,String pre){
        RedisService redisService= (RedisService)SpringUtils.getBean("redisService");
        Long incrId= redisService.incr(CARD_NUMBER,1l);
        String idStr=String.valueOf(incrId);
        if(StringUtils.isNotBlank(pre)){
            length=length-pre.length();
        }else{
            pre="";
        }
        idStr= Strings.padStart(idStr,length,'0');
        idStr=pre+idStr;
        return idStr;
//
//        return new StringBuilder(getIncrId8(CARD_NUMBER))
//                .toString();
    }
    /**
     * 生成工单编码（勘察，施工，安检）
     * @param bcode
     * @param redisKey
     * @return
     */
//    public static String genWorkOrderCode(BizFCode fcode,BizOCode bcode, String redisKey){
//        return new StringBuilder(O).append(fcode.getCode()).append(bcode.getCode())
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }

    /**
     * 生成业务数据编码 设备相关（气表，节点等设备相关）
     * @param bcode
     * @param redisKey
     * @param factoryCode 厂家编码
     * @return
     */
//    public static String genBizDataDeviceCode(BizBCode bcode,String redisKey,String factoryCode){
//        return new StringBuilder(B).append(bcode.getCode())
////                .append(factoryCode)
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }


    /**
     * 表身号
     * @param redisKey
     * @param factoryCode
     * @return
     */
//    public static String genBodyNumber(String redisKey, String factoryCode){
//
//        return new StringBuilder(METER_NO).append(factoryCode)
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }

    /**
     * 生成业务数据编码-其他（客户等）
     * @param bcode
     * @param redisKey
     * @return
     */
//    public static String genBizDataCode(BizBCode bcode,String redisKey){
//        return new StringBuilder(B).append(bcode.getCode())
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }


    /**
     * 生成账户流水号
     * @param code
     * @param redisKey
     * @return
     */
//    public static String genSerialDataCode(BizSCode code, String redisKey){
//        return new StringBuilder(code.getCode())
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_FULL_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }

    /**
     * 生成收费相关编码
     * @param code
     * @param redisKey
     * @return
     */
//    public static String genChargeDataCode(BizCCode code, String redisKey){
//        return new StringBuilder(C).append(code.getCode())
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }

    /**
     * 生成收费相关编码
     * @return
     */
//    public static String genRefundDataCode(){
//        return new StringBuilder(REFUND)
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(REFUND_KEY))
//                .toString();
//    }
    /**
     * 生成账户号
     * @param code
     * @param redisKey
     * @return
     */
//    public static String genAccountDataCode(BizCCode code, String redisKey){
//        return new StringBuilder(ACCT).append(code.getCode())
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }
    /**
     * 生成票据编号
     * @param redisKey
     * @return
     */
//    public static String genReceiptNoDataCode(String redisKey){
//        return new StringBuilder(RCPT)
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }
    /**
     * 生成发票编号
     * @param redisKey
     * @return
     */
//    public static String genInvoiceNoDataCode(String redisKey){
//        return new StringBuilder(INVE)
//                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU))
//                .append(getIncrId(redisKey))
//                .toString();
//    }

//    private static String getIncrId(String redisKey){
//        RedisService redisService= (RedisService)SpringUtils.getBean("redisService");
//        Long incrId= redisService.incr(redisKey,1l);
//        String idStr=String.valueOf(incrId);
//        idStr= Strings.padStart(idStr,10,'0');
//        return idStr;
//    }
//    private static String getIncrId8(String redisKey){
//        RedisService redisService= (RedisService)SpringUtils.getBean("redisService");
//        Long incrId= redisService.incr(redisKey,1l);
//        String idStr=String.valueOf(incrId);
//        idStr= Strings.padStart(idStr,8,'0');
//        return idStr;
//    }


}
