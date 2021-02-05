package com.cdqckj.gmis.lot;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.dto.IotAlarmSaveDTO;
import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 物联网异常信息
 *
 * @author hp
 */
@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/iotAlarm", qualifier = "iotAlarmBizApi")
public interface IotAlarmBizApi {

    /**
     * 条件查询列表
     *
     * @param param 查询参数
     * @return 表具异常信息列表
     */
    @PostMapping("/query")
    R<List<IotAlarm>> query(@RequestBody IotAlarm param);

    /**
     * 查询表具开户后的异常信息
     *
     * @param gasMeterNumber  表身号
     * @param openAccountTime 开户时间
     * @return 异常信息列表
     */
    @GetMapping("/queryAfterCreateTime")
    List<IotAlarm> queryAfterCreateTime(@RequestParam("gasMeterNumber") String gasMeterNumber,
                                        @RequestParam("openAccountTime") LocalDateTime openAccountTime);


    /**
     * 新增报警
     *
     * @param saveDTO
     * @return
     */
    @PostMapping
    R<IotAlarm> save(@RequestBody IotAlarmSaveDTO saveDTO);
}
