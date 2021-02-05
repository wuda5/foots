package com.cdqckj.gmis.common.domain.code;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2020/12/08 08:44
 * @remark: 暂时保存的code信息
 */
@Data
public class TempSaveCodeInfo implements Serializable {

    /** 编码类型 */
    private Integer codeType;

    /**  数据库的名字 */
    private String tableName;

    /**  列的名字 */
    private String colName;

    /**  true新增code false非新增code */
    private Boolean newCode;

    /** 固定的前缀  */
    private String codeGroupType;

    /** 编码 */
    private String code;

    public TempSaveCodeInfo() {
    }
}
