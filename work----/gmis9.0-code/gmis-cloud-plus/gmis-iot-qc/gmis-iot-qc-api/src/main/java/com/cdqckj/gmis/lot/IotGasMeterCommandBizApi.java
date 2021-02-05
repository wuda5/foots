package com.cdqckj.gmis.lot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandDetailSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandSaveDTO;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/iotGasMeterCommand", qualifier = "iotGasMeterCommandBizApi")
public interface IotGasMeterCommandBizApi {
    /**
     * 查询指令列表
     * @param params
     * @return
     */
    @PostMapping("/queryCommand")
    R<Page<CommandEchoVO>> queryCommand(@RequestBody CommandVO params);

    /**
     * 重试业务
     * @param domainId
     * @param retryVO
     * @return
     */
    @PostMapping("/retry")
    IotR retry(@RequestHeader("domainId") String domainId, @RequestBody RetryVO retryVO);

    /**
     * 撤销业务
     * @param domainId
     * @param retryVO
     * @return
     */
    @PostMapping("/cancel")
    IotR cancel(@RequestHeader("domainId") String domainId, @RequestBody RetryVO retryVO);

    @PostMapping("/addDomainRetry")
    IotR addDomainRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail);

    @PostMapping("/registerRetry")
    IotR registerRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail);

    @PostMapping("/updateRetry")
    IotR updateRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail);

    @PostMapping("/removeDeviceRetry")
    IotR removeDeviceRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail);

    @PostMapping("/removeDomainRetry")
    IotR removeDomainRetry(@RequestHeader("domainId") String domainId, @RequestBody IotGasMeterCommandDetail detail);

    /**
     * 获取指令执行状态
     * @param domainId
     * @param transactionNo
     * @return
     */
    @PostMapping("/businessStage")
    IotR businessStage(@RequestHeader("domainId") String domainId, @RequestParam(value = "transactionNo")String transactionNo);

    @GetMapping(value = "/getByCommandId")
    IotGasMeterCommandDetail getByCommandId(@RequestParam(value = "commandId") Long commandId);

    @PostMapping
    R<IotGasMeterCommand> save(@RequestBody IotGasMeterCommandSaveDTO saveDTO);
}
