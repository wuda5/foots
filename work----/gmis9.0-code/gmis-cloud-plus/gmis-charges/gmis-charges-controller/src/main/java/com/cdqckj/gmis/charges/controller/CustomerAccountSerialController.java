package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.CustomerAccountSerialPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountSerialSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountSerialUpdateDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.charges.service.CustomerAccountSerialService;
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
 * 账户流水
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerAccountSerial")
@Api(value = "CustomerAccountSerial", tags = "账户流水")
//@PreAuth(replace = "customerAccountSerial:")
public class CustomerAccountSerialController extends SuperController<CustomerAccountSerialService, Long, CustomerAccountSerial, CustomerAccountSerialPageDTO, CustomerAccountSerialSaveDTO, CustomerAccountSerialUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerAccountSerial> customerAccountSerialList = list.stream().map((map) -> {
            CustomerAccountSerial customerAccountSerial = CustomerAccountSerial.builder().build();
            //TODO 请在这里完成转换
            return customerAccountSerial;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerAccountSerialList));
    }
}
