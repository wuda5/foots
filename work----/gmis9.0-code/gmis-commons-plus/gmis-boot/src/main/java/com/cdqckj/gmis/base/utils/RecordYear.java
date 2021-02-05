package com.cdqckj.gmis.base.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RecordYear", description = "日期枚举")
public enum RecordYear {
    /**
     * 1月
     */
    JAN(1, "janTotalGas","janAverage"),
    /**
     * 2月
     */
    FEB(2, "febTotalGas","febAverage"),
    /**
     * 3月
     */
    MAR(3, "marTotalGas","marAverage"),
    /**
     * 4月
     */
    APR(4, "aprTotalGas","aprAverage"),
    /**
     * 5月
     */
    MAY(5, "mayTotalGas","mayAverage"),
    /**
     * 6月
     */
    JUN(6, "junTotalGas","junAverage"),
    /**
     * 7月
     */
    JUL(7, "julTotalGas","julAverage"),
    /**
     * 8月
     */
    AUG(8, "augTotalGas","augAverage"),
    /**
     * 9月
     */
    SEP(9, "septTotalGas","septAverage"),
    /**
     * 10月
     */
    OCT(10, "octTotalGas","octAverage"),
    /**
     * 11月
     */
    NOV(11, "novTotalGas","novAverage"),
    /**
     * 12月
     */
    DEC(12, "decTotalGas","decAverage");

    private int code;
    private String totalGas;
    private String average;

    private static Map<Integer,String> ascTotalGasMap = new TreeMap<>();
    private static Map<Integer,String> averageMap = new TreeMap<>();
    static{
        for(RecordYear enums:RecordYear.values()){
            ascTotalGasMap.put(enums.code,enums.totalGas);
            averageMap.put(enums.code,enums.average);
        }
        //descMap = ((TreeMap) ascMap).descendingMap();//降序
    }

    public static String getType(Integer dataTypeCode){
        return ascTotalGasMap.get(dataTypeCode);
    }

    public static String getAverage(Integer dataTypeCode){
        return averageMap.get(dataTypeCode);
    }
}