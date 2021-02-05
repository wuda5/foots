package com.cdqckj.gmis.common.domain.search;

import com.cdqckj.gmis.common.utils.LocalDateUtil;
import io.swagger.models.auth.In;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

/**
 * @author: lijianguo
 * @time: 2021/01/16 09:27
 * @remark: 初始化搜索工具类的数据
 */
@Log4j2
public class InitParamUtil {
    
    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 9:28
    * @remark 初始初始化年月的数据
    */
    public static void setNowDayAndMonth(StsSearchParam param){
        Object timeType = param.getSearchKeyValue("timeType");
        if (timeType != null){
            Integer intType = Integer.parseInt((String)timeType);
            // 1当日 2当月
            if (intType == 1){
                param.setStartDay(LocalDate.now());
                param.setEndDay(LocalDateUtil.nextDay(LocalDate.now()));
            }else {
                param.setStartDay(LocalDateUtil.monthBegin(LocalDate.now()));
                param.setEndDay(LocalDateUtil.nextMonthBegin(LocalDate.now()));
            }
        }
        log.info("date is {} {}", param.getStartDay(), param.getEndDay());
    }
}
