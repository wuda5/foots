package com.cdqckj.gmis.iot.qc.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.iot.qc.dto.IotAlarmPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotAlarmSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotAlarmUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import com.cdqckj.gmis.iot.qc.service.IotAlarmService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 报警信息
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iotAlarm")
@Api(value = "IotAlarm", tags = "报警信息")
@PreAuth(replace = "iotAlarm:")
public class IotAlarmController extends SuperController<IotAlarmService, Long, IotAlarm, IotAlarmPageDTO, IotAlarmSaveDTO, IotAlarmUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<IotAlarm> iotAlarmList = list.stream().map((map) -> {
            IotAlarm iotAlarm = IotAlarm.builder().build();
            //TODO 请在这里完成转换
            return iotAlarm;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotAlarmList));
    }

    /**
     * 查询表具开户后的异常信息
     *
     * @param gasMeterNumber  表身号
     * @param openAccountTime 开户时间
     * @return 异常信息列表
     */
    @GetMapping("/queryAfterCreateTime")
    public List<IotAlarm> queryAfterCreateTime(@RequestParam("gasMeterNumber") String gasMeterNumber,
                                               @RequestParam("openAccountTime") LocalDateTime openAccountTime) {
        LbqWrapper<IotAlarm> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(IotAlarm::getDeviceId, gasMeterNumber)
                .gt(SuperEntity::getCreateTime, openAccountTime)
                .orderByDesc(Entity::getUpdateTime);
        return baseService.list(lbqWrapper);
    }
}
