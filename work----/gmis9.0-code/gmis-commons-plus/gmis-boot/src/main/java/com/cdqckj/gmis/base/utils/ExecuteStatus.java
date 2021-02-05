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
@ApiModel(value = "ExecuteStatus", description = "执行状态枚举")
public enum ExecuteStatus {
    /**
     * 执行完成
     */
    FINISH(0, "执行完成"),
    /**
     * 未开启
     */
    NOT_STARTED(-1, "未开启"),
    /**
     * 执行中
     */
    EXECUTING(1, "执行中"),
    /**
     * 暂停
     */
    SUSPEND(2, "暂停");

    private int code;
    private String description;
    private static Map<Integer,ExecuteStatus> map = new HashMap<>();

    static{
        map = new HashMap<>();
        for(ExecuteStatus enums:ExecuteStatus.values()){
            map.put(enums.code,enums);
        }
    }

    public static ExecuteStatus getType(Integer dataTypeCode){
        return map.get(dataTypeCode);
    }
}
