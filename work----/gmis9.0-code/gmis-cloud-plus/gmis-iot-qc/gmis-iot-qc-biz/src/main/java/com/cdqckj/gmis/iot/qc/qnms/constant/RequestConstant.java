package com.cdqckj.gmis.iot.qc.qnms.constant;

import com.google.common.net.MediaType;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 请求接口常量
 * @author: 秦川物联网科技
 * @date: 2020/10/13 09:28
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class RequestConstant {
    /**
     * 接口数据请求方式
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * 接口数据请求方式值(采用json方式)
     */
    public static final String CONTENT_TYPE_VAL = "application/json;charset=UTF-8";
    /**
     * 请求类型
     */
    public static final String TYPE = "Type";
    /**
     * 请求类型值
     */
    public static final String TYPE_VAL = "GMIS";
    /**
     * 访问token常量
     */
    public static final String ACCESS_TOKEN = "Authorization";
    /**
     * 签名常量
     */
    public static final String SIGN = "Sign";

    public class Method{
        /**
         * get请求
         */
        public static final String GET = "get";
        /**
         * post请求
         */
        public static final String POST = "post";
    }

    /**
     * 应用默认json格式请求头
     */
    public static final MediaType DEFAULT_MEDIATYPE = MediaType.parse("application/json; charset=utf-8");
}
