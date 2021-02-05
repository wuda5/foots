package com.cdqckj.gmis.common.domain.excell;

import com.cdqckj.gmis.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: lijianguo
 * @time: 2020/12/16 14:22
 * @remark: 时间的工具类
 */
public class ExcelDateUtil {
    
    /**
    * @Author: lijiangguo
    * @Date: 2020/12/16 14:22
    * @remark 转换为时间的格式
    */
    public static Object covertToDate(String strValue, Class cla){
        if (StringUtils.isBlank(strValue)){
            return "";
        }
        if (cla.equals(LocalDate.class)){
            if(strValue.contains("/") || strValue.contains("-")){
                strValue = strValue.replaceAll("/","-");
                return DateUtils.StringToDate(strValue);
            }
            Double cellDoubleValue = Double.parseDouble(strValue);
            Date cellDate = DateUtil.getJavaDate(cellDoubleValue);
            Instant instant = cellDate.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            return localDate;
        }
        return strValue;
    }
}
