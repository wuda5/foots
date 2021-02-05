package com.cdqckj.gmis.systemparam.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.dto.PayInfoPageDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoSaveDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.cdqckj.gmis.systemparam.service.PayInfoService;
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
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/payInfo")
@Api(value = "PayInfo", tags = "支付配置")
@PreAuth(replace = "payInfo:")
public class PayInfoController extends SuperController<PayInfoService, Long, PayInfo, PayInfoPageDTO, PayInfoSaveDTO, PayInfoUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<PayInfo> payInfoList = list.stream().map((map) -> {
            PayInfo payInfo = PayInfo.builder().build();
            //TODO 请在这里完成转换
            return payInfo;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(payInfoList));
    }
}
