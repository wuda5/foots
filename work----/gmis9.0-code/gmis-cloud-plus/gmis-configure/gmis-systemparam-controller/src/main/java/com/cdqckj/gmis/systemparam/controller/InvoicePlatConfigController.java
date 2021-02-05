package com.cdqckj.gmis.systemparam.controller;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigPageDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoicePlatConfig;
import com.cdqckj.gmis.systemparam.service.InvoicePlatConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 电子发票服务平台配置表
 * </p>
 *
 * @author gmis
 * @date 2020-10-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/invoicePlatConfig")
@Api(value = "InvoicePlatConfig", tags = "电子发票服务平台配置表")
@PreAuth(replace = "invoicePlatConfig:")
public class InvoicePlatConfigController extends SuperController<InvoicePlatConfigService, Long, InvoicePlatConfig, InvoicePlatConfigPageDTO, InvoicePlatConfigSaveDTO, InvoicePlatConfigUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<InvoicePlatConfig> invoicePlatConfigList = list.stream().map((map) -> {
            InvoicePlatConfig invoicePlatConfig = InvoicePlatConfig.builder().build();
            //TODO 请在这里完成转换
            return invoicePlatConfig;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(invoicePlatConfigList));
    }

    @ApiOperation(value = "查询当前公司生效的发票服务平台配置")
    @PostMapping("/getOne")
    public R<InvoicePlatConfig> getOne() {
        LbqWrapper<InvoicePlatConfig> wrapper = Wraps.lbQ();
        wrapper.eq(InvoicePlatConfig::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .eq(InvoicePlatConfig::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        InvoicePlatConfig one = baseService.getOne(wrapper);
        return R.success(one);
    }
}
