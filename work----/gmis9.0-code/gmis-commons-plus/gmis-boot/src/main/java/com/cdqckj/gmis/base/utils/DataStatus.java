package com.cdqckj.gmis.base.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "DataStatus", description = "数据状态枚举")
public enum DataStatus {
    /**
     * 正常
     */
    FINISH(0, "正常"),
    /**
     * 偏小
     */
    NOT_STARTED(-1, "偏小"),
    /**
     * 偏大
     */
    EXECUTING(1, "偏大");

    private int code;
    private String description;
    private static Map<Integer, DataStatus> map = new HashMap<>();

    static{
        map = new HashMap<>();
        for(DataStatus enums: DataStatus.values()){
            map.put(enums.code,enums);
        }
    }

    public static DataStatus getType(Integer dataTypeCode){
        return map.get(dataTypeCode);
    }
}
