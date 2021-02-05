package com.cdqckj.gmis.common.domain.code.next;

/**
 * @author: lijianguo
 * @time: 2020/12/07 08:48
 * @remark: 请添加接口说明
 */
public interface NextCode {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 8:51
    * @remark 这里针对的是随即编码的生成方式
    */
    String create();

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/23 15:11
    * @remark 这个code的前缀
    */
    String codePrefix();
}
