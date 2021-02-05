package com.cdqckj.gmis.readmeter.controller;

import com.cdqckj.gmis.readmeter.entity.ReadMeterDataError;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorPageDTO;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataErrorService;
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
 * 抄表导入错误数据临时记录
 * </p>
 *
 * @author gmis
 * @date 2020-09-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterDataError")
@Api(value = "ReadMeterDataError", tags = "抄表导入错误数据临时记录")
@PreAuth(replace = "readMeterDataError:")
public class ReadMeterDataErrorController extends SuperController<ReadMeterDataErrorService, Long, ReadMeterDataError, ReadMeterDataErrorPageDTO, ReadMeterDataErrorSaveDTO, ReadMeterDataErrorUpdateDTO> {

}
