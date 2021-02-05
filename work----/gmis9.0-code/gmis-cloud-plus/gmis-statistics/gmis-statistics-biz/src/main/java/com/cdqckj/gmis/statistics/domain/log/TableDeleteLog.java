package com.cdqckj.gmis.statistics.domain.log;

/**
 * @author: lijianguo
 * @time: 2020/10/28 11:23
 * @remark: 据库的操作日志
 */
public interface TableDeleteLog<T> extends TableBaseLog<T> {

    /**
     * @auth lijianguo
     * @date 2020/10/25 9:13
     * @remark 删除的日志
     */
    Boolean updateLog(FormatRowData<T> formatRowData);
}
