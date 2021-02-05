package com.cdqckj.gmis.authority.controller.auth;

import com.cdqckj.gmis.authority.dto.auth.UserTokenUpdateDTO;
import com.cdqckj.gmis.authority.service.auth.UserTokenService;
import com.cdqckj.gmis.authority.dto.auth.UserTokenPageDTO;
import com.cdqckj.gmis.authority.dto.auth.UserTokenSaveDTO;
import com.cdqckj.gmis.authority.entity.auth.UserToken;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * token
 * </p>
 *
 * @author gmis
 * @date 2020-04-02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/userToken")
@Api(value = "UserToken", tags = "token")
@PreAuth(replace = "userToken:")
public class UserTokenController extends SuperController<UserTokenService, Long, UserToken, UserTokenPageDTO, UserTokenSaveDTO, UserTokenUpdateDTO> {


}
