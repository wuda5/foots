package com.cdqckj.gmis.oauth.api;

import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.oauth.api.hystrix.UserApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户
 *
 * @author gmis
 * @date 2019/07/02
 */
@FeignClient(name = "${gmis.feign.oauth-server:gmis-oauth-server}", fallback = UserApiFallback.class
        , path = "/user", qualifier = "userApi")
public interface UserApi {
    /**
     * 刷新token
     * @param id 用户id
     * @return
     */
    @GetMapping(value = "/ds/{id}")
    Map<String, Object> getDataScopeById(@PathVariable("id") Long id);

    /**
     * 根据id查询用户
     *
     * @param ids
     * @return
     */
    @GetMapping("/findUserByIds")
    Map<Serializable, Object> findUserByIds(@RequestParam(value = "ids") Set<Serializable> ids);

    /**
     * 根据id查询用户名称
     *
     * @param ids
     * @return
     */
    @GetMapping("/findUserNameByIds")
    Map<Serializable, Object> findUserNameByIds(@RequestParam(value = "ids") Set<Serializable> ids);

    @PostMapping("/getById")
    R<User> getById(@RequestParam("id") Long id);

    @PostMapping("/query")
     R<List<User>> query(@RequestBody User data);
}
