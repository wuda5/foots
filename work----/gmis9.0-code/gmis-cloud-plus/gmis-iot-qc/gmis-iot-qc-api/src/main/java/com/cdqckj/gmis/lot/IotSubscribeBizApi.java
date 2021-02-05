package com.cdqckj.gmis.lot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribePageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;



@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/iot", qualifier = "iotSubscribeBizApi")
public interface IotSubscribeBizApi {
    /**
     *
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<IotSubscribe> save(@RequestBody IotSubscribeSaveDTO saveDTO);

    /**
     *
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<IotSubscribe> update(@RequestBody IotSubscribeUpdateDTO updateDTO);


    /**
     * 分页查询用气类型信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<IotSubscribe>> page(@RequestBody PageParams<IotSubscribePageDTO> params);

    @PostMapping(value = "/check")
    Boolean check(@RequestBody  IotSubscribeUpdateDTO iotSubscribeUpdateDTO);

    @PostMapping(value = "/checkAdd")
    Boolean checkAdd(@RequestBody IotSubscribeSaveDTO iotSubscribeSaveDTO);

    @GetMapping(value = "/queryByFactoryCode")
    IotSubscribe queryByFactoryCode(@RequestParam(value = "factoryCode") String factoryCode);
}
