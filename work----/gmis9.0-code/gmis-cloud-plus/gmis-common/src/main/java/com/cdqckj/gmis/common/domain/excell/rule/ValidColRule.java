package com.cdqckj.gmis.common.domain.excell.rule;

import com.cdqckj.gmis.common.domain.excell.ValidResult;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/12 09:29
 * @remark: 对一个列的验证规则
 */
public interface ValidColRule {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 10:38
    * @remark 验证的列数据
    */
    Integer validCol();
    
    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 10:40
    * @remark 从第几行开始验证
    */
    Integer validStartRow();

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 10:42
    * @remark 从第几列结束验证
    */
    Integer validEndRow();

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 13:32
    * @remark 失败说明
    */
    String failDescribe();

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 11:36
    * @remark 开始验证--这个一整列的数据
    */
    List<ValidResult> validColData(List<String> colData);
}
