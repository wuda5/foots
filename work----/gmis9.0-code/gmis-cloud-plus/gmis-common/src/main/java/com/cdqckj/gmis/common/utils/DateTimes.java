package com.cdqckj.gmis.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimes
{
    static private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date;
    Calendar calendar;

    public DateTimes()
    {
        date = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(date);
    }

    public DateTimes(String time)
    {
        try {
            date = new Date();
            date = dateFormat.parse(time);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public DateTimes(long allMillisec)
    {
        date = new Date(allMillisec);
        calendar.setTime(date);
    }

    public DateTimes(int year, int month, int day)
    {
        date = new Date(year, month, day);
        calendar = Calendar.getInstance();
        calendar.set(year, month, day);
    }

    public DateTimes(int year, int month, int day, int hour, int minute, int second)
    {
        date = new Date(year, month, day, hour, minute, second);
        calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
    }

    public long getAllMillisec()
    {
        return date.getTime();
    }

    public static DateTimes Now()
    {
        return new DateTimes();
    }

    public int getYear()
    {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth()
    {
        return calendar.get(Calendar.MONTH);
    }

    public int getDay()
    {
        return calendar.get(Calendar.DATE);
    }

    public int getHour()
    {
        return calendar.get(Calendar.HOUR);
    }

    public int getMinute()
    {
        return calendar.get(Calendar.MINUTE);
    }

    public int getSecond()
    {
        return calendar.get(Calendar.SECOND);
    }

    public String getWeek()
    {
        String[] week={"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return week[Calendar.DAY_OF_WEEK];
    }

    public Calendar getCalendar()
    {
        return calendar;
    }
    public void setDate(Date dt)
    {
        this.date = dt;
        this.calendar.setTime(dt);
    }

    public DateTimes AddSeconds(int seconds)
    {
        DateTimes time = new DateTimes(this.toString("yyyy-MM-dd HH:mm:ss"));
        time.getCalendar().add(Calendar.SECOND, seconds);
        time.setDate(time.getCalendar().getTime());
        return time;
    }

    public DateTimes AddMinutes(int minutes)
    {
        DateTimes time = new DateTimes(this.toString("yyyy-MM-dd HH:mm:ss"));
        time.getCalendar().add(Calendar.MINUTE, minutes);
        time.setDate(time.getCalendar().getTime());
        return time;
    }

    public DateTimes AddDays(int days)
    {
        DateTimes time = new DateTimes(this.toString("yyyy-MM-dd HH:mm:ss"));
        time.getCalendar().add(Calendar.DATE, days);
        time.setDate(time.getCalendar().getTime());
        return time;
    }

    public DateTimes AddMonths(int months)
    {
        DateTimes time = new DateTimes(this.toString("yyyy-MM-dd HH:mm:ss"));
        time.getCalendar().add(Calendar.MONTH, months);
        time.setDate(time.getCalendar().getTime());
        return time;
    }

    public DateTimes AddYears(int years)
    {
        DateTimes time = new DateTimes(this.toString("yyyy-MM-dd HH:mm:ss"));
        time.getCalendar().add(Calendar.YEAR, years);
        time.setDate(time.getCalendar().getTime());
        return time;
    }

    public String toString(String format)
    {
        DateFormat dtDateFormat = new SimpleDateFormat(format);
        return dtDateFormat.format(date);
    }
}
