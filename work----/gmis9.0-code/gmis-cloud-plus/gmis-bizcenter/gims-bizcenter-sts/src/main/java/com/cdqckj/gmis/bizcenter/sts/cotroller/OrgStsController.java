package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.authority.api.OrgBizApi;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.sts.entity.OrgTree;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.utils.TreeNodeInterface;
import com.cdqckj.gmis.utils.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021/01/22 08:59
 * @remark: 请添加类说明
 */
@Slf4j
@Validated
@RestController
@RequestMapping("biz/sts/org")
@Api(value = "stsOrg", tags = "机构")
public class OrgStsController {

    @Autowired
    OrgBizApi orgBizApi;

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/22 8:51
     * @remark 查询系统所有的机构
     */
    @ApiOperation(value = "查询系统所有的机构")
    @PostMapping("/allOnlineOrg")
    R<List> allOnlineOrg(){
        Long userId = BaseContextHandler.getUserId();
        List<Org> userOrgList = orgBizApi.userAllOrg(userId).getData();
        List<Org> allOrg = orgBizApi.allOnlineOrg().getData();

        List<OrgTree> allOrgTree = new ArrayList<>(allOrg.size());
        for (Org org: allOrg){
            OrgTree orgTree = new OrgTree();
            orgTree.setParentId(org.getParentId());
            orgTree.setOrgId(org.getId());
            orgTree.setOrgName(org.getLabel());
            orgTree.setEnable(1);
            allOrgTree.add(orgTree);
        }
        SeparateListData<OrgTree> sepOrgTree = new SeparateListData<>(allOrgTree);
        for (Org org: userOrgList){
            OrgTree orgTree = sepOrgTree.getTheDataByKey(String.valueOf(org.getId()));
            if (orgTree != null){
                orgTree.setEnable(0);
            }
        }
        allOrgTree = sepOrgTree.getAllData();
        List<TreeNodeInterface> result = TreeUtil.buildTreeForInterface(allOrgTree);

        Long orgId = UserOrgIdUtil.getUserOrgId();
        List<TreeNodeInterface> userList = new ArrayList<>();
        TreeUtil.getTreeNodeInterface(result, String.valueOf(orgId), userList);
        return R.success(userList);
    }


}
