package com.cdqckj.gmis;

import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateTest  {
    public static void main(String[] args){
        LocalDate nowDate = LocalDate.parse("2019-03-02");
        LocalDate endDate = LocalDate.parse("2019-02-02");
        long days= (nowDate.toEpochDay()-endDate.toEpochDay())+1;
        System.out.println(days);
    }
}
