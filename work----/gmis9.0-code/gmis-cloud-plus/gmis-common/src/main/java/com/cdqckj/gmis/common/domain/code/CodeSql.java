package com.cdqckj.gmis.common.domain.code;

/**
 * @author: lijianguo
 * @time: 2020/12/31 09:42
 * @remark: 查询code的sql
 */
public interface CodeSql {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/31 9:42
    * @remark 查询所有的code的sql
    */
    String allCodeSql();


    /**
    * @Author: lijiangguo
    * @Date: 2020/12/31 9:43
    * @remark 查询下一个code的sql
    */
    String nextCodeSql();
}
