package com.cdqckj.gmis.statistics.domain.log;

/**
 * @author: lijianguo
 * @time: 2020/11/4 14:02
 * @remark: 生成tablelog 处理的简单工厂
 */
public interface TableLogEntityProduce {

    /**
     * @auth lijianguo
     * @date 2020/11/5 9:11
     * @remark 处理实体的泛型--类型的转换
     */
    void entityGeneric(Object o);

    /**
     * @auth lijianguo
     * @date 2020/11/5 10:40
     * @remark 切换你的数据库
     */
    void switchDatabase(String schemaName);

    /**
     * @auth lijianguo
     * @date 2020/11/4 14:03
     * @remark 生成每个根据不同的表的名字生成不同的处理对象
     */
    TableBaseLog entityFactory(String tableName, SysServiceBean sysServiceBean);

}
