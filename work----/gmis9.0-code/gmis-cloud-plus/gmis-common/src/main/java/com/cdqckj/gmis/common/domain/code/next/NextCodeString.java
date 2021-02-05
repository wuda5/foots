package com.cdqckj.gmis.common.domain.code.next;

import cn.hutool.core.util.StrUtil;

/**
 * @author: lijianguo
 * @time: 2020/12/07 13:05
 * @remark: 随机串的code
 */
public class NextCodeString implements NextCode{

    /** 长度 **/
    private Integer codeLength;

    /** 查询的前缀 **/
    private String prefix;

    public NextCodeString(Integer codeLength, String prefix) {
        this.codeLength = codeLength;
        this.prefix = prefix;
    }

    @Override
    public String create() {
        String str = StrUtil.uuid();
        str = str.replace("-","");
        if (str.length() > codeLength){
            return str.substring(0, codeLength);
        }else {
            return str;
        }
    }

    @Override
    public String codePrefix() {
        return prefix;
    }
}
