package com.cdqckj.gmis.statistics.domain.log;

import org.nustaq.serialization.annotations.Serialize;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2020/12/01 09:19
 * @remark: 处理接口的数据
 */
public interface BinlogData {


    /**
    * @Author: lijiangguo
    * @Date: 2020/12/1 9:20
    * @remark 处理数据
    */
    FormatRowData realProcessData(Serializable serializable);
}
