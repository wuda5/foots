package com.cdqckj.gmis.bizcenter.sts.entity;

import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import com.cdqckj.gmis.utils.TreeNodeInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021/01/28 14:30
 * @remark: 请添加类说明
 */
@Setter
public class OrgTree implements SeparateKey, TreeNodeInterface {

    @ApiModelProperty(value = "父亲节点")
    Long parentId;

    @ApiModelProperty(value = "机构的Id")
    Long orgId;

    @ApiModelProperty(value = "机构的名字")
    String orgName;

    @ApiModelProperty(value = "0不可选 1可选")
    Integer enable = 0;

    @ApiModelProperty(value = "所有的儿子节点")
    private List<TreeNodeInterface> sonOrg = new ArrayList<>();

    public Long getId() {
        return orgId;
    }

    public String getLabel() {
        return orgName;
    }

    public Integer getIsDisabled() {
        return enable;
    }

    public List<TreeNodeInterface> getChildren() {
        return sonOrg;
    }

    @Override
    public String indexKey() {
        return String.valueOf(orgId);
    }

    @Override
    public String getTreeId() {
        return String.valueOf(orgId);
    }

    @Override
    public String getTreeParentId() {
        return String.valueOf(parentId);
    }

    @Override
    public Boolean parentNode() {
        if (parentId == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void setTreeSonNode(TreeNodeInterface node) {
        sonOrg.add(node);
    }

    @Override
    public List<TreeNodeInterface> children() {
        return sonOrg;
    }


}
