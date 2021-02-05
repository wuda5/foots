package com.cdqckj.gmis.authority.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.dto.auth.UserPageDTO;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户
 *
 * @author gmis
 * @date 2019/07/02
 */
@FeignClient(name = "${gmis.feign.authority-server:gmis-authority-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/user", qualifier = "userBizApi")
public interface UserBizApi {
    @PostMapping("/query")
    R<List<User>> query(@RequestBody User query);

    /**
     * 查询所有的用户id
     *
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    R<List<Long>> findAllUserId();

    @PostMapping("/page")
    R<Page<User>> page(@RequestBody @Validated PageParams<UserPageDTO> params);

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping
    R<User> update(@RequestBody User user);

    @GetMapping("/{id}")
    R<User> get(@PathVariable(value = "id") Long id);

    @PostMapping("/userbyOrgid")
    R<User> update(@RequestBody Long orgId);
}
