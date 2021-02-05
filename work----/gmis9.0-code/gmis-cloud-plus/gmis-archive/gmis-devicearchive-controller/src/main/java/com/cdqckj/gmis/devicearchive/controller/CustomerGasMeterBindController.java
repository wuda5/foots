package com.cdqckj.gmis.devicearchive.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.devicearchive.dto.*;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterBind;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterBindService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.utils.I18nUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 客户气表绑定关系表 - 代缴业务
 * </p>
 *
 * @author wanghuaqiao
 * @date 2020-10-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerGasMeterBind")
@Api(value = "CustomerGasMeterBind", tags = "客户气表绑定关系表 - 代缴业务")
@PreAuth(replace = "customerGasMeterBind:")
public class CustomerGasMeterBindController extends SuperController<CustomerGasMeterBindService, Long, CustomerGasMeterBind, CustomerGasMeterBindPageDTO, CustomerGasMeterBindSaveDTO, CustomerGasMeterBindUpdateDTO> {

    @Autowired
    CustomerGasMeterBindService customerGasMeterBindService;
    @Autowired
    private I18nUtil i18nUtil;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerGasMeterBind> customerGasMeterBindList = list.stream().map((map) -> {
            CustomerGasMeterBind customerGasMeterBind = CustomerGasMeterBind.builder().build();
            //TODO 请在这里完成转换
            return customerGasMeterBind;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerGasMeterBindList));
    }
    @Override
    public R<CustomerGasMeterBind> saveList(@RequestBody List<CustomerGasMeterBindSaveDTO> list) {
        List<CustomerGasMeterBindSaveDTO> customerGasMeterBindSaveDTOS =new ArrayList<>();
        for (CustomerGasMeterBindSaveDTO customerGasMeterBindSaveDTO : list) {
            Boolean aBoolean = customerGasMeterBindService.checkAdd(customerGasMeterBindSaveDTO);
            if(aBoolean){
                return R.fail(i18nUtil.getMessage("gasbindStatus.not.repeat"));
            }
            customerGasMeterBindSaveDTOS.add(customerGasMeterBindSaveDTO);
        }
        return super.saveList(customerGasMeterBindSaveDTOS);
    }
    @Override
    public R<CustomerGasMeterBind> update(@RequestBody CustomerGasMeterBindUpdateDTO updateDTO) {
        Boolean check = customerGasMeterBindService.check(updateDTO);
        if(check){
            return R.fail(i18nUtil.getMessage("gasbindStatus.not.repeat"));
        }
        return super.update(updateDTO);
    }

    @PostMapping("/getGasMeterInfo")
    @ApiOperation(value = "查询气表信息")
    public  R<List<GasMeterBindResult>> getGasMeterInfo(@RequestBody GasMeterBindPrame gasMeterBindPrame){
       return R.success(customerGasMeterBindService.getGasMeterInfo(gasMeterBindPrame));
    }
}
