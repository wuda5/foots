package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribePageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.lot.IotSubscribeBizApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/iotubscribe")
@Api(value = "IotSubscribeConfigControllor", tags = "物联网网关配置")
public class IotSubscribeConfigControllor {
    @Autowired
    IotSubscribeBizApi iotSubscribeBizApi;

    @ApiOperation(value = "新增物联网配置")
    @PostMapping("/iotubscribeconfig/add")
    R<IotSubscribe> saveIotSubscribeConfig(@RequestBody IotSubscribeSaveDTO saveDTO){
        Boolean booleanR = iotSubscribeBizApi.checkAdd(saveDTO);
        if(booleanR==true){
            return R.fail("物联网配置厂家名称或常见编码以及唯一标识重复");
        }
        saveDTO.setTenantCode(BaseContextHandler.getTenant());
        saveDTO.setSubscribe(0);
       return iotSubscribeBizApi.save(saveDTO);
    }

    @ApiOperation(value = "修改物联网配置")
    @PostMapping("/iotubscribeconfig/update")
    R<IotSubscribe> updateIotSubscribeConfig(@RequestBody IotSubscribeUpdateDTO updateDTO){
        Boolean check = iotSubscribeBizApi.check(updateDTO);
        if(check==true){
            return R.fail("物联网配置厂家名称或常见编码以及唯一标识重复");
        }
        return iotSubscribeBizApi.update(updateDTO);
    }

    @ApiOperation(value = "分页物联网配置")
    @PostMapping("/iotubscribeconfig/page")
    R<Page<IotSubscribe>> pageIotSubscribeConfig(@RequestBody PageParams<IotSubscribePageDTO> params){
       return iotSubscribeBizApi.page(params);
    }
}
