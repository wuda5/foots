package com.cdqckj.gmis.systemparam.controller;

import com.cdqckj.gmis.systemparam.entity.InvoiceParam;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamUpdateDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamPageDTO;
import com.cdqckj.gmis.systemparam.service.InvoiceParamService;
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
 * @date 2020-07-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/invoiceParam")
@Api(value = "InvoiceParam", tags = "开票参数配置")
@PreAuth(replace = "invoiceParam:")
public class InvoiceParamController extends SuperController<InvoiceParamService, Long, InvoiceParam, InvoiceParamPageDTO, InvoiceParamSaveDTO, InvoiceParamUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<InvoiceParam> invoiceParamList = list.stream().map((map) -> {
            InvoiceParam invoiceParam = InvoiceParam.builder().build();
            //TODO 请在这里完成转换
            return invoiceParam;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(invoiceParamList));
    }
}
