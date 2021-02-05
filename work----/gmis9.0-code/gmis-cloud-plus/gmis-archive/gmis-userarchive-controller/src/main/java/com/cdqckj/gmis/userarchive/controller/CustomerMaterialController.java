package com.cdqckj.gmis.userarchive.controller;

import com.cdqckj.gmis.userarchive.entity.CustomerMaterial;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialUpdateDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialPageDTO;
import com.cdqckj.gmis.userarchive.service.CustomerMaterialService;
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
 * @date 2020-07-31
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerMaterial")
@Api(value = "CustomerMaterial", tags = "客户资料接口")
@PreAuth(replace = "customerMaterial:")
public class CustomerMaterialController extends SuperController<CustomerMaterialService, Long, CustomerMaterial, CustomerMaterialPageDTO, CustomerMaterialSaveDTO, CustomerMaterialUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerMaterial> customerMaterialList = list.stream().map((map) -> {
            CustomerMaterial customerMaterial = CustomerMaterial.builder().build();
            //TODO 请在这里完成转换
            return customerMaterial;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerMaterialList));
    }
}
