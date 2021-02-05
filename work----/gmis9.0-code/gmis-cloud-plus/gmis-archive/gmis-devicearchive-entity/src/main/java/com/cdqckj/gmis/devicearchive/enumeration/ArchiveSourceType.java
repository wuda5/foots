package com.cdqckj.gmis.devicearchive.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.cdqckj.gmis.base.BaseEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 气表档案
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ArchiveSourceType", description = "建档来源-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ArchiveSourceType implements BaseEnum {

    /**
     * BATCH_CREATE_ARCHIVE="批量建档"
     */
    BATCH_CREATE_ARCHIVE("批量建档"),
    /**
     * IMPORT_ARCHIVE="导入建档"
     */
    IMPORT_ARCHIVE("导入建档"),
    /**
     * ARTIFICIAL_CREATE_ARCHIVE="人工建档"
     */
    ARTIFICIAL_CREATE_ARCHIVE("人工建档"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static ArchiveSourceType match(String val, ArchiveSourceType def) {
        for (ArchiveSourceType enm : ArchiveSourceType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ArchiveSourceType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ArchiveSourceType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "BATCH_CREATE_ARCHIVE,IMPORT_ARCHIVE,ARTIFICIAL_CREATE_ARCHIVE", example = "BATCH_CREATE_ARCHIVE")
    public String getCode() {
        return this.name();
    }

}
