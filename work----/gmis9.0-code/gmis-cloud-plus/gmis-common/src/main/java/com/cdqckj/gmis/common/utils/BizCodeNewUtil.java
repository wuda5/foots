package com.cdqckj.gmis.common.utils;

import com.cdqckj.gmis.common.domain.code.*;
import com.cdqckj.gmis.common.domain.code.clear.ClearAllException;
import com.cdqckj.gmis.common.enums.*;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.utils.DateUtils;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author: lijianguo
 * @time: 2020/12/08 17:01
 * @remark: 重新生成的整个系统的会保存到数据库的code
 */
public class BizCodeNewUtil {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 12:42
    * @remark 安检的编号
    */
    public static String getSafeCheckCode(){
        String tableName = "gc_security_check_record";
        String colName = "sc_no";
        Integer length = 10;
        String prefix = new StringBuilder("SC").append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 12:52
    * @remark 换表的编码
    */
    public static String getChangeGasMeterCode(){
        String tableName = "gt_change_meter_record";
        String colName = "change_meter_no";
        Integer length = 10;
        String prefix = new StringBuilder("CG").append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 12:55
    * @remark 拆表的编码
    */
    public static String getRemoveMeterCode(){
        String tableName = "gt_remove_meter_record";
        String colName = "remove_meter_no";
        Integer length = 10;
        String prefix = new StringBuilder("RM").append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 12:58
    * @remark 报装的code
    */
    public static String getInstallRecordCode(){
        String tableName = "gc_install_record";
        String colName = "install_number";
        Integer length = 10;
        String prefix = new StringBuilder("IR").append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 13:03
    * @remark 报装的订单的code
    */
    public static String getInstallOrderRecordCode(){
        String tableName = "gc_order_record";
        String colName = "install_number";
        Integer length = 10;
        String prefix = new StringBuilder("IOR").append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/8 16:44
     * @remark 获取客户的收费编号
     */
    public static String genCustomerChargeNo(String chargeNo, String prefix, String customerCode, String gasMeterCode){

        String tableName = "da_customer_gas_meter_related";
        String colName = "customer_charge_no";
        Integer length = 12;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT customer_charge_no FROM da_customer_gas_meter_related WHERE customer_code = '").append(customerCode);
        sql.append("' AND gas_meter_code = '").append(gasMeterCode).append("'");
        String nextCodeSql = "SELECT customer_charge_no FROM da_customer_gas_meter_related WHERE " +
                "customer_charge_flag = 1 ORDER BY create_time DESC LIMIT 500";
        String groupKey = customerCode + gasMeterCode;
        CodeInfo codeInfo = new CodeInfo(CodeTypeEnum.INC_TYPE_ONE, tableName, colName, groupKey, sql.toString(), nextCodeSql);
        if (StringUtils.isNotBlank(chargeNo)){
            Boolean result = BizCodeBaseUtil.checkSaveCode(codeInfo, chargeNo);
            if (result == false){
                throw new ClearAllException("收费编号重复");
            }
            return chargeNo;
        }
        String nextCode = BizCodeBaseUtil.createCode(codeInfo, prefix, length);
        return nextCode;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:08
    * @remark 生成业务收费单编码--场景收费--8位自增的编码
    */
    public static String genCustomerSceneChargeNumber(BizFCode bCode){
        String prefix = new StringBuilder().append(bCode.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String tableName = "gt_customer_scene_charge_order";
        String colName = "scene_charge_no";
        Integer length = 10;
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:14
    * @remark 生成卡号
    */
    public static String genCardNumber(Integer length, String prefix){
        String tableName = "gt_card_info";
        String colName = "card_no";
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:20
    * @remark 生成工单编码（勘察，施工，安检） 客户工单记录 工单编号 这里有问题
    */
    public static String genWorkOrderCode(BizFCode fcode, BizOCode bcode){

        String prefix = new StringBuilder(BizCodeUtil.O).append(fcode.getCode()).append(bcode.getCode()).
                append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(fcode.getTableName(), fcode.getColName(), prefix, fcode.getLength());
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:26
    * @remark 气表表号
    */
    public static String genGasMeterCode(BizBCode bcode){

        String tableName = "da_gas_meter";
        String colName = "gas_code";
        Integer length = 10;
        String prefix = new StringBuilder(BizCodeUtil.B).append(bcode.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 13:19
    * @remark 客户档案的code
    */
    public static String getCustomCode(){
        String tableName = "da_customer";
        String colName = "customer_code";
        Integer length = 10;
        String prefix = new StringBuilder(BizCodeUtil.B).append(BizBCode.C.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 13:24
    * @remark 批次号
    */
    public static String getMeterBatchCode(){
        String tableName = "da_input_output_meter_story";
        String colName = "serial_code";
        Integer length = 10;
        String prefix = new StringBuilder(BizCodeUtil.B).append(BizBCode.P.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:45
    * @remark 收费编号
    */
    public static String genChargeNo(){
        String tableName = "gt_charge_record";
        String colName = "charge_no";
        Integer length = 12;
        String prefix = new StringBuilder(BizCodeUtil.C).append(BizCCode.C.getCode())
                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String codeValue = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return codeValue;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:50
    * @remark 退费编号
    */
    public static String genRefundNo(){
        String tableName = "gt_charge_refund";
        String colName = "refund_no";
        Integer length = 12;
        String prefix = new StringBuilder(BizCodeUtil.REFUND)
                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:53
    * @remark 账户号
    */
    public static String genAccountDataCode(){
        String tableName = "gt_customer_account";
        String colName = "account_code";
        Integer length = 12;

        String prefix = new StringBuilder(BizCodeUtil.ACCT).append(BizCCode.T.getCode())
                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 19:59
    * @remark 生成票据编号
    */
    public static String genReceiptNoDataCode(){
        String tableName = "gt_receipt";
        String colName = "receipt_no";
        Integer length = 12;
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, "PJ", length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 20:05
    * @remark 生成发票编号
    */
    public static String genInvoiceNoDataCode(){
        String tableName = "gt_invoice";
        String colName = "invoice_number";
        Integer length = 12;
        String prefix = new StringBuilder(BizCodeUtil.INVE)
                .append(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix,length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 13:36
    * @remark 用户账户的流水号
    */
    public static String getCustomerAccountCode(BizSCode bizSCode){
        String tableName = "gt_customer_account_serial";
        String colName = "serial_no";
        Integer length = 10;
        String prefix = new StringBuilder(bizSCode.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_FULL_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 13:48
    * @remark 账户流水
    */
    public static String getGasMeterInfoSerialCode(BizSCode bizSCode){
        String tableName = "da_gas_meter_info_serial";
        String colName = "serial_no";
        Integer length = 10;
        String prefix = new StringBuilder(bizSCode.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_FULL_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 13:54
    * @remark 流水号
    */
    public static String getOweCode(BizSCode bizSCode){
        String tableName = "cb_read_meter_data_iot";
        String colName = "gas_owe_code";
        Integer length = 10;
        String prefix = new StringBuilder(bizSCode.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_FULL_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    public static String getOweCodeEX(BizSCode bizSCode){
        String tableName = "cb_read_meter_data";
        String colName = "gas_owe_code";
        Integer length = 10;
        String prefix = new StringBuilder(bizSCode.getCode()).append(DateUtils.format(new Date(),DateUtils.DEFAULT_FULL_FORMAT_NU)).toString();
        String code = BizCodeBaseUtil.createAutoIncCode(tableName, colName, prefix, length);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 20:24
    * @remark
    * 表身号生生成规则，不同的厂家重新编号,每个月从0开始重新计数
    * 1~4位厂家编码的前4位，不足四位在后面补充0
    * 5~6 位年份的两位
    * 7~8 月份的两位
    * 9~14 自增000000~999999
    */
    public static String genGasMeterNumber(Long factoryId, String factoryCode, String inputCode){

        if(factoryId == null){
            throw BizException.wrap("厂家Id不能为空");
        }
        if (factoryCode == null){
            throw BizException.wrap("厂家编码不能为空");
        }
        if (factoryCode.length() >= 4){
            factoryCode = factoryCode.substring(0, 3);
        }else {
            factoryCode = Strings.padEnd(factoryCode,4,'0');
        }
        Integer year = LocalDate.now().getYear();
        String yearString = String.valueOf(year).substring(2, 4);
        Integer month = LocalDate.now().getMonthValue();
        String monthString = String.valueOf(month);
        if (monthString.length() < 2){
            monthString = "0" + monthString;
        }
        String prefix = factoryCode + yearString + monthString;
        String groupKey = factoryId + yearString + monthString;
        String tableName = "da_gas_meter";
        String colName = "gas_meter_number";
        Integer length = 6;
        String searchSql = "SELECT gas_meter_number FROM da_gas_meter WHERE data_status != 5 and data_status != 6 AND gas_meter_factory_id = " + factoryId;
        String nextSql = "SELECT gas_meter_number FROM da_gas_meter WHERE data_status != 5 and data_status != 6 AND auto_generated = 1 AND gas_meter_factory_id = " + factoryId;
        nextSql += " AND gas_meter_number like '" + prefix +"%'";
        CodeInfo codeInfo = new CodeInfo(CodeTypeEnum.INC_TYPE_ONE, tableName, colName, groupKey, searchSql, nextSql);
        if (StringUtils.isNotBlank(inputCode)){
            Boolean result = BizCodeBaseUtil.checkSaveCode(codeInfo, inputCode);
            if (result == false){
                throw new ClearAllException("表身号重复");
            }
            return inputCode;
        }
        String theCode = BizCodeBaseUtil.createCode(codeInfo, prefix, length);
        return theCode;
    }
}
