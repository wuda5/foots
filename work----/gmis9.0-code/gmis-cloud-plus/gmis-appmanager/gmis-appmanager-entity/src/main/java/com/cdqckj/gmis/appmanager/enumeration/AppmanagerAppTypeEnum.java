package com.cdqckj.gmis.appmanager.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.cdqckj.gmis.base.BaseEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 应用管理表
 * </p>
 *
 * @author gmis
 * @date 2020-09-16
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AppmanagerAppTypeEnum", description = "应用类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AppmanagerAppTypeEnum implements BaseEnum {

    /**
     * APPLET="applet""小程序"
     */
    APPLET("applet","工程APP"),
    /**
     * APP="app""app"
     */
    APP("app","用户APP"),
    /**
     * KAKU="kaku""卡库"
     */
    KAKU("kaku","卡库"),
    /**
     * OTHER="other""其他"
     */
    OTHER("other","其他"),
    ;

    @ApiModelProperty(value = "值")
    private String val;
    @ApiModelProperty(value = "描述")
    private String desc;


    public static AppmanagerAppTypeEnum match(String val, AppmanagerAppTypeEnum def) {
        for (AppmanagerAppTypeEnum enm : AppmanagerAppTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AppmanagerAppTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(AppmanagerAppTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "APPLET,APP,KAKU,OTHER", example = "APPLET")
    public String getCode() {
        return this.name();
    }

    /**
     * 转成map
     * @return
     */
    public static HashMap<String,String> toMap(){
        HashMap<String,String> data = new HashMap<>();
        for (AppmanagerAppTypeEnum enm : AppmanagerAppTypeEnum.values()) {
            data.put(enm.val,enm.desc);
        }
        return data;
    }

}
