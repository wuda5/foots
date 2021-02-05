package com.cdqckj.gmis.oauthapi.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.oauthapi.dto.RealNameOauthPageDTO;
import com.cdqckj.gmis.oauthapi.dto.RealNameOauthSaveDTO;
import com.cdqckj.gmis.oauthapi.dto.RealNameOauthUpdateDTO;
import com.cdqckj.gmis.oauthapi.entity.RealNameOauth;
import com.cdqckj.gmis.oauthapi.service.RealNameOauthService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * app应用实名认证表：租户客户关联表
 * </p>
 *
 * @author gmis
 * @date 2020-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/realNameOauth")
@Api(value = "RealNameOauth", tags = "app应用实名认证表：租户客户关联表")
@PreAuth(replace = "realNameOauth:")
public class RealNameOauthController extends SuperController<RealNameOauthService, Long, RealNameOauth, RealNameOauthPageDTO, RealNameOauthSaveDTO, RealNameOauthUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<RealNameOauth> realNameOauthList = list.stream().map((map) -> {
            RealNameOauth realNameOauth = RealNameOauth.builder().build();
            //TODO 请在这里完成转换
            return realNameOauth;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(realNameOauthList));
    }
}
