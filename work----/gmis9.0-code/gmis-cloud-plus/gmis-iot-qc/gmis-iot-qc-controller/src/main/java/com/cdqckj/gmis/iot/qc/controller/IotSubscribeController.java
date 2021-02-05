package com.cdqckj.gmis.iot.qc.controller;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribePageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.helper.TokenHelper;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotAuth;
import com.cdqckj.gmis.iot.qc.service.IotSubscribeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




/**
 * <p>
 * 秦川物联网
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iot")
@Api(value = "IotSubscribe", tags = "")
//@PreAuth(replace = "iotSubscribe:")
public class IotSubscribeController extends SuperController<IotSubscribeService, Long, IotSubscribe, IotSubscribePageDTO, IotSubscribeSaveDTO, IotSubscribeUpdateDTO> {

    @Autowired
    private TokenHelper tokenHelper;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<IotSubscribe> iotSubscribeList = list.stream().map((map) -> {
            IotSubscribe iotSubscribe = IotSubscribe.builder().build();
            //TODO 请在这里完成转换
            return iotSubscribe;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotSubscribeList));
    }

    @GetMapping(value = "/login")
    @ApiOperation(value = "登录", notes = "登录")
    public IotR login(String licence) throws Exception{
        return tokenHelper.login(licence);
    }

    @QnmsIotAuth
    @ApiOperation(value = "订阅", notes = "订阅")
    @PostMapping(value = "/subscribe")
    public IotR subscribe(@RequestHeader("domainId") String domainId) throws Exception{
        return baseService.subscribe(domainId);
    }

    @QnmsIotAuth
    @ApiOperation(value = "退订", notes = "退订")
    @PostMapping("unsubscribe")
    public IotR unsubscribe(@RequestHeader("domainId") String domainId) throws Exception{
        return baseService.unsubscribe(domainId);
    }
    @PostMapping(value = "/check")
    Boolean check(@RequestBody  IotSubscribeUpdateDTO iotSubscribeUpdateDTO){
        return baseService.check(iotSubscribeUpdateDTO);
    };
    @PostMapping(value = "/checkAdd")
    Boolean checkAdd(@RequestBody  IotSubscribeSaveDTO iotSubscribeSaveDTO){
        return baseService.checkAdd(iotSubscribeSaveDTO);
    };
    @GetMapping(value = "/queryByFactoryCode")
    IotSubscribe queryByFactoryCode(@RequestParam(value = "factoryCode") String factoryCode){
        return baseService.queryByFactoryCode(factoryCode);
    };
}
