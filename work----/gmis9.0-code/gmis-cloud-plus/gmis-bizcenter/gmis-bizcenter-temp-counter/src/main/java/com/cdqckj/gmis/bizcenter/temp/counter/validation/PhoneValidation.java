package com.cdqckj.gmis.bizcenter.temp.counter.validation;

import org.apache.commons.lang3.StringUtils;

/**
 * @author songyz
 */
public class PhoneValidation {

    public final static int PHONE_LENGTH = 11;

    /**
     *大陆手机号 判断是否符合手机号规则
     * @param mobileNumber
     * @return
     */
    public static boolean isMobileNumber(String mobileNumber) {
        if (StringUtils.isNotBlank(mobileNumber)){
            mobileNumber = mobileNumber.trim();
            return validate(mobileNumber);
        }
        return false;
    }

    /**
     * 校验手机号
     * @param mobileNumber
     * @return
     */
    private static boolean validate(String mobileNumber){
        boolean result = true;
        // 手机号不能为空
        result = result && StringUtils.isNotBlank(mobileNumber);
        // 手机号长度是11
        result = result && PHONE_LENGTH == mobileNumber.length();
        //手机号第一位为1
        char firstCh = mobileNumber.charAt(0);
        result = result && firstCh >= '1';
        //手机号第2位为3456789
        char secondCh = mobileNumber.charAt(1);
        result = result && secondCh >= '3' && secondCh <= '9';
        // 手机号的后9位必须是阿拉伯数字
        for (int i = 2; result && i < PHONE_LENGTH - 1; i++){
            char ch = mobileNumber.charAt(i);
            result = result && ch >= '0' && ch <= '9';
        }
        return result;
    }
}
