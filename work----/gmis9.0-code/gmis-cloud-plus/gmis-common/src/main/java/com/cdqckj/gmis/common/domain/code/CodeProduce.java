package com.cdqckj.gmis.common.domain.code;

import com.cdqckj.gmis.common.domain.code.next.NextCode;

/**
 * @author: lijianguo
 * @time: 2020/12/07 14:18
 * @remark: code的生成
 */
public interface CodeProduce {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 14:18
    * @remark 生成code
    */
    String getCode(CodeInfo codeInfo, NextCode nextCode);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/10 14:05
    * @remark 保存code 成功返回true 失败返回 false
    */
    Boolean saveCode(CodeInfo codeInfo, String newCode);
}
