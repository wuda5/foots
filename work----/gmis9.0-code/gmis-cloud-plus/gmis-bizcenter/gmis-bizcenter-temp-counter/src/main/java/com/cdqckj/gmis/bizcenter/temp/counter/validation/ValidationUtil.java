package com.cdqckj.gmis.bizcenter.temp.counter.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    /**
     * 是否是数字、字母
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean result = true;
        for (int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if(!Character.isLetter(ch) && !Character.isDigit(ch)) {
                result = false;
            }
        }
        return result;
    }

    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        boolean result = true;
        for (int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
        }
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
}
