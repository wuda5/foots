package com.cdqckj.gmis.authority.controller.core;

import com.cdqckj.gmis.authority.dto.core.OrgSaveDTO;
import com.cdqckj.gmis.authority.dto.core.OrgUpdateDTO;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.authority.service.core.OrgService;
import com.cdqckj.gmis.authority.service.core.StationService;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperCacheController;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.base.MessageConstants.ORG_CREATE_NAME;
import static com.cdqckj.gmis.base.MessageConstants.ORG_CREATE_ONLY;
import static com.cdqckj.gmis.utils.StrPool.DEF_PARENT_ID;
import static com.cdqckj.gmis.utils.StrPool.DEF_ROOT_PATH;


/**
 * <p>
 * 前端控制器
 * 组织
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Slf4j
@RestController
@RequestMapping("/org")
@Api(value = "Org", tags = "组织")
@PreAuth(replace = "org:")
public class OrgController extends SuperCacheController<OrgService, Long, Org, Org, OrgSaveDTO, OrgUpdateDTO> {

    @Autowired
    private UserService userService;
    @Autowired
    private StationService stationService;

    @Override
    public R<Org> handlerSave(OrgSaveDTO model) {

        Org org = BeanPlusUtil.toBean(model, Org.class);
        fillOrg(org);
        /*String tenant = getTenant();
        List<Org> orgList = null;
        Map<String,Object> orgMap = new HashMap(1);
        String key = "orgHash"+tenant;
        if(!getRedisService().hasKey(key)){
           List<Org> list =  baseService.list();
           if(list!=null&&list.size()>0){
               orgMap.put("orgList",list);
               getRedisService().hmset(key,orgMap);
           }
        }*/
        List<Org> orgList = baseService.list();//(List<Org>) getRedisService().hmget(key).get("orgList");
        //判断新增组织是否存在
        if(orgList!=null){
           List<Org> rootList = orgList.stream().filter(e->e.getParentId()==0).
                   collect(Collectors.toList());
           if(rootList.size()>0&&org.getParentId()==0){
               return R.fail(getLangMessage(ORG_CREATE_ONLY));
           }
           List<Org> nameList = orgList.stream().filter(e->e.getLabel().trim().
                    equals(org.getLabel().trim())).collect(Collectors.toList());
           if(nameList.size()>0){
               return R.fail(getLangMessage(ORG_CREATE_NAME));
           }
        }
        this.baseService.save(org);
        //放入最新组织数据到redis
        /*orgList.add(org);
        orgMap.put("orgList",orgList);
        getRedisService().hmset(key,orgMap);*/
        return success(org);
    }

    @Override
    public R<Org> handlerUpdate(OrgUpdateDTO model) {
        //add by hc 如果禁用组织，需要校验其下没有用户
        if(!model.getStatus()){
            Set<Long> keys = new HashSet<>();
            keys.add(model.getId());
            List<User> users = userService.findUserByOrgIds(keys);
            if(CollectionUtils.isNotEmpty(users)){
                //当前组织下存在用户,不能禁用
                return R.fail(redisService.getLangMessage(MessageConstants.CURRENT_ORGANIZATION_HAVE_USER));
            }
            //校验当前下是否有岗位
            Boolean aBoolean = stationService.checkStation(model.getId(), null);
            if(!aBoolean){
                return R.fail("当前组织下存在岗位,不能禁用");
            }
        }
        Org org = BeanPlusUtil.toBean(model, Org.class);
        fillOrg(org);
        this.baseService.updateAllById(org);
        return success(org);
    }

    private Org fillOrg(Org org) {
        if (org.getParentId() == null || org.getParentId() <= 0) {
            org.setParentId(DEF_PARENT_ID);
            org.setTreePath(DEF_ROOT_PATH);
        } else {
            Org parent = this.baseService.getByIdCache(org.getParentId());
            BizAssert.notNull(parent,getLangMessage(MessageConstants.ORG_VERIFY_PARENT));
            org.setTreePath(StringUtils.join(parent.getTreePath(), parent.getId(), DEF_ROOT_PATH));
        }
        return org;
    }

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        return this.success(baseService.remove(ids));
    }


    /**
     * 查询系统所有的组织树
     *
     * @param status 状态
     * @return
     * @author gmis
     * @date 2019-07-29 11:59
     */
    @ApiOperation(value = "查询系统所有的组织树", notes = "查询系统所有的组织树")
    @GetMapping("/tree")
    @SysLog("查询系统所有的组织树")
    public R<List<Org>> tree(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "status", required = false) Boolean status) {
        List<Org> list = this.baseService.findOrgPage(name,status);
        List<Org> resultList = TreeUtil.buildTree(list);
        Long orgId = UserOrgIdUtil.getUserOrgId();
        List<Org> userList = new ArrayList<>();
        TreeUtil.setUserTreeEntity(resultList, orgId, userList);
        return this.success(userList);
    }

    @ApiOperation(value = "查询系统所有的组织树", notes = "查询系统所有的组织树")
    @GetMapping("/treeMap")
    @SysLog("查询系统所有的组织树")
    public R<Map<String,Object>> treeMap(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "status", required = false) Boolean status) {
        Map<String,Object> map = new HashMap<>(2);
        List<Org> list = this.baseService.findOrgPage(name,status);
        map.put("tree",TreeUtil.buildTree(list));
        return R.success(map);
    }

    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<Org> userList = list.stream().map((map) -> {
            Org item = new Org();
            /*item.setDescribe(map.getOrDefault("描述", EMPTY));
            item.setLabel(map.getOrDefault("名称", EMPTY));
            item.setAbbreviation(map.getOrDefault("简称", EMPTY));
            item.setStatus(Convert.toBool(map.getOrDefault("状态", EMPTY)));*/
            return item;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(userList));
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/22 8:51
    * @remark 查询系统所有的机构
    */
    @PostMapping("/allOnlineOrg")
    public R<List<Org>> allOnlineOrg() {
        List<Org> allOrgList = this.baseService.allOnlineOrg();
        return R.success(allOrgList);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/28 13:53
     * @remark 这个用户的所有的机构
     */
    @GetMapping("/userAllOrg")
    R<List<Org>> userAllOrg(@RequestParam Long userId){
        List<Org> allOrgList = this.baseService.userAllOrg(userId);
        return R.success(allOrgList);
    }

}
