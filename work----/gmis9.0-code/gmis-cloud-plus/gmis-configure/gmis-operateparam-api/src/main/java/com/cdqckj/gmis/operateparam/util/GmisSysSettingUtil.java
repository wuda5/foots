package com.cdqckj.gmis.operateparam.util;

import com.cdqckj.gmis.common.key.RedisConfigKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.operateparam.FunctionSwitchPlusBizApi;
import com.cdqckj.gmis.systemparam.enumeration.FunctionSwitchEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/12/04 16:03
 * @remark: 整个系统的设置的工具类
 */
@Component
public class GmisSysSettingUtil {

    @Autowired
    RedisTemplate redisTemplateInit;

    @Autowired
    FunctionSwitchPlusBizApi functionSwitchPlusBizApiInit;

    /** 请求的地址 **/
    private static FunctionSwitchPlusBizApi functionSwitchPlusBizApi;

    /** 缓存 **/
    private static RedisTemplate redisTemplate;

    public GmisSysSettingUtil() {
    }

    @PostConstruct
    public void initStaticData() {
        GmisSysSettingUtil.functionSwitchPlusBizApi = functionSwitchPlusBizApiInit;
        GmisSysSettingUtil.redisTemplate = redisTemplateInit;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 19:03
    * @remark 缴费编码规则设置 自增长、1;人工录入、0
    */
    public static Integer getOpenCustomerPrefix(){
        return getSysValueForInt(FunctionSwitchEnum.OPEN_CUSTOMER_SET_RULE_TYPE.getItem());
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 19:04
    * @remark 缴费编码前缀
    */
    public static String getCustomerPrefix(){
        return getSysValue(FunctionSwitchEnum.OPEN_CUSTOMER_PREFIX_TYPE.getItem());
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 19:05
    * @remark 过户修改缴费编号：1是;0否
    */
    public static Integer getTransferAccountFlag(){
        return getSysValueForInt(FunctionSwitchEnum.TRANSFER_ACCOUNT_FLAG_TYPE.getItem());
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 16:07
    * @remark 取整方式 1：向上取整 ，0：向下取整
    */
    public static Integer getRounding(){
        return getSysValueForInt(FunctionSwitchEnum.ROUND_TYPE.getItem());
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/4 16:07
     * @remark 启用黑名单限购 1是 0否
     */
    public static Integer getOpenBlackList(){
        return getSysValueForInt(FunctionSwitchEnum.OPEN_BLACK_LIST_TYPE.getItem());
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/4 16:07
     * @remark 黑名单限购最大充值气量
     */
    public static BigDecimal getBlackMaxVolume(){
        return getSysValueForBigDecimal(FunctionSwitchEnum.BLACK_MAX_VOLUME_TYPE.getItem());
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/4 16:07
     * @remark 黑名单限购最大充值金额
     */
    public static BigDecimal getBlackMaxMoney(){
        return getSysValueForBigDecimal(FunctionSwitchEnum.BLACK_MAX_MONEY_TYPE.getItem());
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/4 16:07
     * @remark 启动安检限购 1是 0否
     */
    public static Integer getOpenCheckLimit(){
        return getSysValueForInt(FunctionSwitchEnum.OPEN_CHECK_LIMIT_TYPE.getItem());
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/4 16:07
     * @remark 安检限购最大充值气量
     */
    public static BigDecimal getCheckMaxVolume(){
        return getSysValueForBigDecimal(FunctionSwitchEnum.CHECK_MAX_VOLUME_TYPE.getItem());
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/4 16:07
     * @remark 安检限购最大充值金额
     */
    public static BigDecimal getCheckMaxMoney(){
        return getSysValueForBigDecimal(FunctionSwitchEnum.CHECK_MAX_MONEY_TYPE.getItem());
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/4 16:07
     * @remark 结算日期
     */
    public static String getSettlementDate(){
        return getSysValue(FunctionSwitchEnum.SETTLEMENT_DATE_TYPE.getItem());
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 19:52
    * @remark 发票编码规则1自动生成,0人工
    */
    public static Integer getInvoiceCodeRule(){
        return getSysValueForInt(FunctionSwitchEnum.INVOICE_CODE_RULE_TYPE_TYPE.getItem());
    }

    /**
     * @Author: tangping
     * @Date: 2021/01/20 17:22
     * @remark 启用保险功能业务 1是 0否
     */
    public static Integer getInsuranceCodeRule(){
        return getSysValueForInt(FunctionSwitchEnum.OPEN_INSURANCE_TYPE.getItem());
    }

    /**
     * @Author: tangping
     * @Date: 2020/12/10 19:52
     * @remark 获取票据公司名称
     */
    public static String getInvoiceCompanyName(){
        return getSysValue(FunctionSwitchEnum.INVOICE_COMPANY_NAME.getItem());
    }
    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 18:05
    * @remark 获取值
    */
    private static String getSysValue(String item){

        String value = (String) redisTemplate.opsForHash().get(CacheKeyUtil.createTenantKey(RedisConfigKey.SYS_SET_KEY), item);
        if (StringUtils.isBlank(value)){
            String apiValue = GmisSysSettingUtil.functionSwitchPlusBizApi.getSystemSetting(item).getData();
            redisTemplate.opsForHash().putIfAbsent(CacheKeyUtil.createTenantKey(RedisConfigKey.SYS_SET_KEY), item, apiValue);
            value = apiValue;
        }
        return value;
    }

    /**
     * 删除缓存
     */
    public static void cleanCache(){
        redisTemplate.delete(CacheKeyUtil.createTenantKey(RedisConfigKey.SYS_SET_KEY));
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 19:20
    * @remark 获取整数值
    */
    private static Integer getSysValueForInt(String item){
        String value = getSysValue(item);
        return Integer.parseInt(value);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 19:24
    * @remark 获取big的值
    */
    private static BigDecimal getSysValueForBigDecimal(String item){
        String value = getSysValue(item);
        if (StringUtils.isBlank(value)){
            return BigDecimal.ZERO;
        }else {
            return new BigDecimal(value);
        }
    }
}
