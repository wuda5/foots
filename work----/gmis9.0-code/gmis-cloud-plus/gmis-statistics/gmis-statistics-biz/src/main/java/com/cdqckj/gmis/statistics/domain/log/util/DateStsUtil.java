package com.cdqckj.gmis.statistics.domain.log.util;

import cn.hutool.core.date.DateUtil;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: lijianguo
 * @time: 2020/11/3 9:46
 * @remark: 这个统计的时间的函数
 */
public class DateStsUtil {

    /**
     * @auth lijianguo
     * @date 2020/11/3 9:46
     * @remark 得到今天的时间字符串
     */
    public static String getTodayDateString(LocalDateTime dateTime){
        String dayStr = DateUtil.format(dateTime,"yyyy-MM-dd");
        return dayStr;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:56
     * @remark 这天的开始时间
     */
    public static LocalDateTime dayBeginTime(LocalDateTime dateTime){
        if (dateTime == null){
            dateTime = LocalDateTime.now();
        }
        LocalDateTime beginTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
        return beginTime;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 18:54
    * @remark 这天的结束时间
    */
    public static LocalDateTime dayEndTime(LocalDateTime dateTime){

        LocalDateTime endTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
        return endTime;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/7 11:00
     * @remark 开始时间
     */
    public static LocalDateTime dayBeginTime(){
        return dayBeginTime(LocalDateTime.now());
    }

    /**
     * @auth lijianguo
     * @date 2020/11/18 14:49
     * @remark 这个月的开始时间
     */
    public static LocalDateTime monthBeginTime(LocalDateTime localDateTime){
        LocalDate today = localDateTime.toLocalDate();
        LocalDate monthFirst = LocalDate.of(today.getYear(), today.getMonth(),1);
        return monthFirst.atStartOfDay();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/18 14:59
     * @remark 下一个月的日期
     */
    public static LocalDateTime nextMonthBeginTime(LocalDateTime localDateTime){

        Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Integer month = localDateTime.getMonth().getValue();
        if(month >= 12){
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, 0);
        }else {
            calendar.add(Calendar.MONTH, 1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        LocalDateTime localDateTimeRe = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTimeRe;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/18 14:53
     * @remark 这个月的开始时间
     */
    public static LocalDateTime dateTimeBegin(LocalDateTime localDateTime, Integer yearAdd, Integer monthAdd, Integer dayNum){

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearAdd);
        calendar.add(Calendar.MONTH, monthAdd);
        calendar.set(Calendar.DAY_OF_MONTH, dayNum);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        LocalDateTime localDateTimeRe = date.toInstant().atZone(zoneId).toLocalDateTime();
        return localDateTimeRe;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/18 16:19
     * @remark 月末
     */
    public static LocalDateTime dateTimeEnd(LocalDateTime localDateTime, Integer yearAdd, Integer monthAdd, Integer dayNum){

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearAdd);
        calendar.add(Calendar.MONTH, monthAdd);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (dayNum > lastDay){
            dayNum = lastDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, dayNum);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        LocalDateTime localDateTimeRe = date.toInstant().atZone(zoneId).toLocalDateTime();
        return localDateTimeRe;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 19:10
    * @remark 今年的开始时间
    */
    public static LocalDateTime yearStartTime(LocalDateTime localDateTime, Integer yearAdd){

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearAdd);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        LocalDateTime localDateTimeRe = date.toInstant().atZone(zoneId).toLocalDateTime();
        LocalDateTime endTime = LocalDateTime.of(localDateTimeRe.toLocalDate(), LocalTime.MIN);
        return endTime;

    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/28 13:17
    * @remark 年份改变
    */
    public static LocalDateTime yearChange(LocalDateTime localDateTime, Integer change){

        Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, change);
        date = calendar.getTime();
        LocalDateTime localDateTimeRe = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTimeRe;
    }



}
