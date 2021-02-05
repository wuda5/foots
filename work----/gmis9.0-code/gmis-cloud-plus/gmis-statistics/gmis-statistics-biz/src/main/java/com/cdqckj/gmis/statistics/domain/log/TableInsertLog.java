package com.cdqckj.gmis.statistics.domain.log;

/**
 * @author: lijianguo
 * @time: 2020/10/28 11:23
 * @remark: 据库的操作日志
 */
public interface TableInsertLog<T> extends TableBaseLog<T> {

    /**
     * @auth lijianguo
     * @date 2020/10/25 9:13
     * @remark 新建的日志处理
     */
    Boolean insertLog(FormatRowData<T> formatRowData);
}
