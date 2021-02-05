package com.cdqckj.gmis.common.key;

/**
 * @author: lijianguo
 * @time: 2020/11/27 10:35
 * @remark: 所有的项目都要使用的key
 */
public class RedisCommonKey {

    /** 所有的租户的 list **/
    public static final String TENANT_CACHE_KEY = "a:rc:tenant";

    /** 机器注册的key **/
    public static final String REGISTER_MACHINE_KEY = "a:rc:rm";

    /** 系统code set集合  **/
    public static final String SYS_CODE_CACHE_KEY = "t:rc:code:scc";

    /** 系统code 布式锁的前缀 **/
    public static final String DIS_LOCK_CODE_CACHE_KEY = "t:rc:code:dlcc";

    /** 系统code 当你要获取一个开始的code就添加一个begin id **/
    public static final String CODE_NOT_LOST_CODE_KEY = "t:rc:code:cnlc";

    /** 系统code 下一个自增的编码 **/
    public static final String NEXT_INC_CODE_KEY = "t:rc:code:mi";
}
