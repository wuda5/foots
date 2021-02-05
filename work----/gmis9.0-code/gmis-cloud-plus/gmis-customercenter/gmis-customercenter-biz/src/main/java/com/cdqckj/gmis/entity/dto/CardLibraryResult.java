package com.cdqckj.gmis.entity.dto;

import lombok.Data;

/**
 * 卡库返回对接实体
 * @auther hc
 */
@Data
public class CardLibraryResult {

    private Integer code;

    private String message;

    private Object data;
}
