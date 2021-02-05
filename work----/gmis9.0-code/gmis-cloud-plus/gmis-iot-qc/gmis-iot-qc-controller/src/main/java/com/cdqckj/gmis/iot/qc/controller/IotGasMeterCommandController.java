package com.cdqckj.gmis.iot.qc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotAuth;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotSubscribe;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandDetailService;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandService;
import com.cdqckj.gmis.iot.qc.service.MessageService;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
import com.cdqckj.gmis.security.annotation.PreAuth;
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
 * 前端控制器
 * 物联网气表指令数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iotGasMeterCommand")
@Api(value = "IotGasMeterCommand", tags = "物联网气表指令数据")
//@PreAuth(replace = "iotGasMeterCommand:")
public class IotGasMeterCommandController extends SuperController<IotGasMeterCommandService, Long, IotGasMeterCommand, IotGasMeterCommandPageDTO, IotGasMeterCommandSaveDTO, IotGasMeterCommandUpdateDTO> {

    @Autowired
    private MessageService messageService;

    @Autowired
    private IotGasMeterCommandDetailService iotGasMeterCommandDetailService;

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<IotGasMeterCommand> iotGasMeterCommandList = list.stream().map((map) -> {
            IotGasMeterCommand iotGasMeterCommand = IotGasMeterCommand.builder().build();
            //TODO 请在这里完成转换
            return iotGasMeterCommand;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotGasMeterCommandList));
    }

    @ApiOperation(value = "查询指令列表")
    @PostMapping("/queryCommand")
    public R<Page<CommandEchoVO>> queryCommand(@RequestBody CommandVO params){
        return R.success(baseService.queryCommand(params));
    }

    @ApiOperation(value = "重试指令")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/retry")
    public IotR retry(@RequestHeader("domainId") String domainId, @RequestBody RetryVO retryVO) throws Exception {
        return baseService.retry(domainId,retryVO);
    }

    @ApiOperation(value = "撤销指令")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/cancel")
    public IotR cancel(@RequestHeader("domainId") String domainId, @RequestBody RetryVO retryVO) throws Exception {
        return baseService.cancel(domainId,retryVO);
    }

    @ApiOperation(value = "加入逻辑域重试")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/addDomainRetry")
    public IotR addDomainRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail) throws Exception {
        return messageService.deviceAddEventRetry(domainId,detail);
    }

    @ApiOperation(value = "注册重试")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/registerRetry")
    public IotR registerRetry(@RequestHeader("domainId") String domainId,@RequestBody IotGasMeterCommandDetail detail) throws Exception {
        return messageService.registerDeviceEventRetry(domainId,detail);
    }

    @ApiOperation(value = "更新设备重试")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/updateRetry")
    public IotR updateRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail) throws Exception {
        return messageService.updateDeviceEventRetry(domainId,detail);
    }

    @ApiOperation(value = "移除设备重试")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/removeDeviceRetry")
    public IotR removeDeviceRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail) throws Exception {
        return messageService.registerDeviceEventRetry(domainId,detail);
    }

    @ApiOperation(value = "移除逻辑域重试")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/removeDomainRetry")
    public IotR removeDomainRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail) throws Exception {
        return messageService.removeDomainEventRetry(domainId,detail);
    }

    @ApiOperation(value = "获取指令执行状态")
    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping("/businessStage")
    public IotR businessStage(@RequestHeader("domainId") String domainId, @RequestParam(value = "transactionNo")String transactionNo) throws Exception{
        return baseService.businessStage(domainId,transactionNo);
    }

    @GetMapping(value = "/getByCommandId")
    public  IotGasMeterCommandDetail getByCommandId(@RequestParam(value = "commandId") Long commandId){
        return iotGasMeterCommandDetailService.getByCommandId(commandId);
    }
}
