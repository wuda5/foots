package com.cdqckj.gmis.biztemporary.controller;

import com.cdqckj.gmis.biztemporary.entity.CustomerTemp;
import com.cdqckj.gmis.biztemporary.dto.CustomerTempSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.CustomerTempUpdateDTO;
import com.cdqckj.gmis.biztemporary.dto.CustomerTempPageDTO;
import com.cdqckj.gmis.biztemporary.service.CustomerTempService;
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
 * @author songyz
 * @date 2021-01-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerTemp")
@Api(value = "CustomerTemp", tags = "")
@PreAuth(replace = "customerTemp:")
public class CustomerTempController extends SuperController<CustomerTempService, Long, CustomerTemp, CustomerTempPageDTO, CustomerTempSaveDTO, CustomerTempUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerTemp> customerTempList = list.stream().map((map) -> {
            CustomerTemp customerTemp = CustomerTemp.builder().build();
            //TODO 请在这里完成转换
            return customerTemp;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerTempList));
    }
}
