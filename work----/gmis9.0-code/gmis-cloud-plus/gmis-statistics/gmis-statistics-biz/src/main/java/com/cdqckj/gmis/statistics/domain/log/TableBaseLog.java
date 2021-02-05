package com.cdqckj.gmis.statistics.domain.log;

/**
 * @author: lijianguo
 * @time: 2020/10/25 9:09
 * @remark: 处理
 */
public interface TableBaseLog<T> {

    /**
     * @auth lijianguo
     * @date 2020/10/25 9:57
     * @remark 获取对应的实体类
     */
    Class getEntityClass();

    /**
     * @auth lijianguo
     * @date 2020/10/28 13:03
     * @remark 这个日志需要处理 类型
     */
    Boolean logNeedProcess(FormatRowData<T> formatRowData, ProcessTypeEnum typeEnum);

}
