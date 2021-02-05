package com.cdqckj.gmis.authority.vo.auth;


import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import com.cdqckj.gmis.utils.TreeNodeInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/12 9:57
 * @remark: 请输入类说明
 */
public class MenuVo extends Menu implements SeparateKey, TreeNodeInterface {

    @ApiModelProperty(value = "租户有这个菜单")
    private Boolean rentHasThisMenu = false;

    @ApiModelProperty(value = "所有的儿子节点")
    private List<TreeNodeInterface> sonNodeList = new ArrayList<>();

    public void setRentHasThisMenu(Boolean rentHasThisMenu) {
        this.rentHasThisMenu = rentHasThisMenu;
    }

    @Override
    public String indexKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getTreeId() {
        return String.valueOf(getId());
    }

    @Override
    public String getTreeParentId() {
        return String.valueOf(getParentId());
    }

    @Override
    public Boolean parentNode() {
        if (getParentId() == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void setTreeSonNode(TreeNodeInterface node) {
        initChildren();
        children.add((Menu) node);
    }

    @Override
    public List<TreeNodeInterface> children() {
        return sonNodeList;
    }
}
