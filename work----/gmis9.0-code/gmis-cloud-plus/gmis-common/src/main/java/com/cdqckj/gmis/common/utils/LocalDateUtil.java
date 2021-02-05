package com.cdqckj.gmis.common.utils;

import cn.hutool.core.date.DateUtil;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/3 9:46
 * @remark: 这个统计的时间的函数
 */
public class LocalDateUtil {

    /**
     * @auth lijianguo
     * @date 2020/11/3 9:46
     * @remark 得到今天的时间字符串
     */
    public static String getTodayDateString(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        String dayStr = DateUtil.format(date,"yyyy-MM-dd");
        return dayStr;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:56
     * @remark 这天的开始时间
     */
    public static LocalDate nextDay(LocalDate localDate){
        return localDate.plusDays(1);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/30 9:43
    * @remark 上一天
    */
    public static LocalDate beforeDay(LocalDate localDate){
        return localDate.plusDays(-1);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 18:18
    * @remark 这个月的开始时间
    */
    public static LocalDate monthBegin(LocalDate localDate){
        LocalDate firstDay = LocalDate.of(localDate.getYear(), localDate.getMonth(),1);
        return firstDay;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 18:22
    * @remark 下一个月的开始时间
    */
    public static LocalDate nextMonthBegin(LocalDate localDate){
        LocalDate thisMonth = monthBegin(localDate);
        LocalDate nextMonth = thisMonth.plusMonths(1L);
        return nextMonth;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 19:00
    * @remark 请添加函数说明
    */
    /**
     * @Author: lijiangguo
     * @Date: 2020/12/29 18:22
     * @remark 下一个月的开始时间
     */
    public static LocalDate monthBeginChangeNum(LocalDate localDate, Long num){
        LocalDate thisMonth = monthBegin(localDate);
        LocalDate nextMonth = thisMonth.plusMonths(num);
        return nextMonth;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 18:26
    * @remark 年的开始时间
    */
    public static LocalDate yearBegin(LocalDate localDate){
        LocalDate yearBegin = localDate.with(TemporalAdjusters.firstDayOfYear());
        return yearBegin;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 18:30
    * @remark 下一年的开始时间
    */
    public static LocalDate nextYearBegin(LocalDate localDate){

        LocalDate begin = yearBegin(localDate);
        LocalDate nextYear = begin.plusYears(1L);
        return nextYear;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 18:46
    * @remark 年份改变-只改变年份
    */
    public static LocalDate yearChangeNum(LocalDate localDate, Long num){

        LocalDate begin = yearBegin(localDate);
        LocalDate nextYear = localDate.plusYears(num);
        return nextYear;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 10:36
    * @remark 分割时间
    */
    public static List<LocalDate> splitDateTimeByMonth(LocalDate beginDate, LocalDate endDate){

        List<LocalDate> dateTimes = new ArrayList<>();
        dateTimes.add(beginDate);
        while (beginDate.isBefore(endDate)){
            beginDate = LocalDateUtil.nextMonthBegin(beginDate);
            dateTimes.add(beginDate);
        }
        return dateTimes;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/28 11:58
     * @remark 获取月份的编号
     */
    public static String getMonthNum(LocalDate localDate){

        Integer m = localDate.getMonth().getValue();
        String ms = String.valueOf(m);
        if (ms.length() < 2){
            return "0" + ms;
        }else {
            return ms;
        }
    }

}
