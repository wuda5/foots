package com.cdqckj.gmis.readmeter.controller;

import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Map;

public class ReadDataTimeVaildRule implements ValidRule {

    @ApiModelProperty(value = "抄表时间")
    private String readTime;
    @Autowired
    private ReadMeterDataService dataService;

    @ApiModelProperty(value = "已存在数据")
    private Map<String, ReadMeterData> existDataMap;

    @ApiModelProperty(value = "失败的说明")
    private String validDesc = "aaaa";

    @ApiModelProperty(value = "空的说明")
    private String emptyDesc;

    public ReadDataTimeVaildRule(String readTime, Map<String, ReadMeterData> existDataMap) {
        this.readTime = readTime;
        this.existDataMap = existDataMap;
    }

    @Override
    public Integer validColStart() {
        return 0;
    }

    @Override
    public Integer validColSum() {
        return 8;
    }

    @Override
    public String validEmptyFailDesc() {
        return emptyDesc;
    }

    @Override
    public void validProcess(ValidResult validResult) {
        String code = (String) validResult.getColDataList().get(2);
        if(null!=code && !"".equals(code)){
            ReadMeterData data = existDataMap.get(code);
            if(null!=data){
                Integer latestYear = data.getReadMeterYear();
                Integer latestMonth = data.getReadMeterMonth();
                ProcessEnum process = data.getProcessStatus();
                boolean bool = false;
                if(!process.eq(ProcessEnum.SETTLED)){
                    bool = true;
                }else{
                    LocalDate readTime1 =  DateUtils.StringToDate(readTime+"-01");
                    if(data.getReadTime().isAfter(readTime1) || data.getReadTime().isEqual(readTime1)){
                        bool = true;
                    }
                }
                if(bool){
                    String datetime = latestYear + "年" + latestMonth + "月";
                    String str = "该气表最新数据为" + datetime + "，状态为"+process.getDesc()+",故无法当前数据无法导入";
                    validResult.setStatus(false);
                    validResult.setInvalidDesc(str);
                }
            }else{
                String importTime = (String) validResult.getColDataList().get(7).replaceAll("/","-");
                LocalDate importTime1 =  DateUtils.StringToDate(importTime);
                LocalDate readTime1 =  DateUtils.StringToDate(readTime+"-01");
                if(importTime1.isBefore(readTime1)){
                    String str = "抄表日期不得早于抄表月份,故无法当前数据无法导入";
                    validResult.setStatus(false);
                    validResult.setInvalidDesc(str);
                }
            }
            /*if (DateUtils.getDate8(year,month).isBefore(DateUtils.getDate8(latestYear,latestMonth)) ||
                    DateUtils.getDate8(year,month).isEqual(DateUtils.getDate8(latestYear,latestMonth))){
                String datetime = latestYear + "年" + latestMonth + "月";
                validResult.setStatus(false);
                String str = "该气表已完成" + datetime + "抄表，故无法当前数据无法导入";
                validResult.setStatus(false);
                validResult.setInvalidDesc(str);
            }*/
        }
    }

    @Override
    public int validType() {
        return 0;
    }

    @Override
    public String validFailDesc() {
        return validDesc;
    }

}
