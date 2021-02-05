package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.statistics.entity.MeterUploadStsNow;
import com.cdqckj.gmis.statistics.dto.MeterUploadStsNowSaveDTO;
import com.cdqckj.gmis.statistics.dto.MeterUploadStsNowUpdateDTO;
import com.cdqckj.gmis.statistics.dto.MeterUploadStsNowPageDTO;
import com.cdqckj.gmis.statistics.service.MeterUploadStsNowService;
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
 * 在线,报警两个指标
 * </p>
 *
 * @author gmis
 * @date 2020-11-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/meterUploadStsNow")
@Api(value = "MeterUploadStsNow", tags = "在线,报警两个指标")
@PreAuth(replace = "meterUploadStsNow:")
public class MeterUploadStsNowController extends SuperController<MeterUploadStsNowService, Long, MeterUploadStsNow, MeterUploadStsNowPageDTO, MeterUploadStsNowSaveDTO, MeterUploadStsNowUpdateDTO> {


}
