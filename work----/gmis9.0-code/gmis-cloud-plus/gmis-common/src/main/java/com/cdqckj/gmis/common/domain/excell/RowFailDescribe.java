package com.cdqckj.gmis.common.domain.excell;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/14 14:45
 * @remark: 每一列的失败的说明
 */
public interface RowFailDescribe {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/14 14:45
    * @remark 请添加函数说明
    */
    void setMsg(List<FailNumInfo> infoList);
}
