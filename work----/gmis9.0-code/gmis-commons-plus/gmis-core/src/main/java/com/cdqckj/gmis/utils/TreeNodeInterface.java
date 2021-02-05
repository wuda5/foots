package com.cdqckj.gmis.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/12 11:02
 * @remark: 请输入接口说明
 */
public interface TreeNodeInterface extends Serializable {

    /**
     * @auth lijianguo
     * @date 2020/10/12 11:05
     * @remark 获取Id
     */
    String getTreeId();

    /**
     * @auth lijianguo
     * @date 2020/10/12 11:06
     * @remark 父亲节点
     */
    String getTreeParentId();

    /**
     * @auth lijianguo
     * @date 2020/10/12 11:30
     * @remark 是不是父亲节点
     */
    Boolean parentNode();

    /**
     * @auth lijianguo
     * @date 2020/10/12 11:07
     * @remark 设置为子节点
     */
    void setTreeSonNode(TreeNodeInterface node);

    /**
     * @auth lijianguo
     * @date 2020/10/12 13:11
     * @remark获取子节点
     */
    List<TreeNodeInterface> children();
}
