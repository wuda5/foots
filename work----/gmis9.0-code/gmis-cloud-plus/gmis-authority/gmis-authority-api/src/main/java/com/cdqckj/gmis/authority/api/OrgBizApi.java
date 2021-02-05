package com.cdqckj.gmis.authority.api;

import com.cdqckj.gmis.authority.api.hystrix.OrgBizApiFallback;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.authority-server:gmis-authority-server}", fallback = OrgBizApiFallback.class
        , path = "/org", qualifier = "orgBizApi")
public interface OrgBizApi {
    /**
     * @return
     */
    @RequestMapping(value = "/treeMap", method = RequestMethod.GET)
    R<Map<String,Object>> getOrgTree();

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/22 8:51
     * @remark 查询系统所有的机构
     */
    @PostMapping("/allOnlineOrg")
    R<List<Org>> allOnlineOrg();

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/28 13:53
    * @remark 这个用户的所有的机构
    */
    @GetMapping("/userAllOrg")
    R<List<Org>> userAllOrg(@RequestParam("userId") Long userId);
}
