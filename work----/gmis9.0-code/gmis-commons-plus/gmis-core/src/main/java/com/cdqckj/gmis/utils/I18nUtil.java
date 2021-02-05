package com.cdqckj.gmis.utils;

import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;


/**
 * @Description: 多语言国际化消息工具类
 * @Author: junqiang.lu
 * @Date: 2019/1/24
 */
@Component
public class I18nUtil {
    @Autowired
    MessageSource messageSource;

    /**
     * 获取一条语言配置信息
     * @return
     * @throws IOException
     */
    public String getMessage(String message, String defaultMessage) {
        int language=getLangType();
        if(language==1){
            return messageSource.getMessage(message,null, Locale.CHINESE);
        }else if(language==2){
            return messageSource.getMessage(message,null,Locale.US);
        }
        return defaultMessage;
    }

    /**
     * 获取一条语言配置信息
     * @return
     * @throws IOException
     */
    public String getMessage(String message) {
        int language=getLangType();
        if(language==1){
            return messageSource.getMessage(message,null, Locale.CHINESE);
        }else if(language==2){
            return messageSource.getMessage(message,null,Locale.US);
        }
        return "未安装语言包，无法识别消息";
    }

    public static int getLangType(){
        Integer langType = BaseContextHandler.getLang();
        if(langType==null) langType=1;
        return langType.intValue();
    }
    public static String getMsg(String msg,String msgEn){
        if(getLangType()==1) return msg;
        return msgEn;
    }
}

