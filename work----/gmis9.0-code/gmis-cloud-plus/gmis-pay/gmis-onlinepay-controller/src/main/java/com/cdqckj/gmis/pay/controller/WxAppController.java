package com.cdqckj.gmis.pay.controller;

import com.cdqckj.gmis.pay.entity.WxApp;
import com.cdqckj.gmis.pay.dto.WxAppSaveDTO;
import com.cdqckj.gmis.pay.dto.WxAppUpdateDTO;
import com.cdqckj.gmis.pay.dto.WxAppPageDTO;
import com.cdqckj.gmis.pay.service.WxAppService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wxApp")
@Api(value = "WxApp", tags = "")
@PreAuth(replace = "wxApp:")
public class WxAppController extends SuperController<WxAppService, String, WxApp, WxAppPageDTO, WxAppSaveDTO, WxAppUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<WxApp> wxAppList = list.stream().map((map) -> {
            WxApp wxApp = WxApp.builder().build();
            //TODO 请在这里完成转换
            return wxApp;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(wxAppList));
    }
}
