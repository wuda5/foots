package com.cdqckj.gmis.devicearchive.controller;

import com.cdqckj.gmis.devicearchive.entity.InputOutputMeterStory;
import com.cdqckj.gmis.devicearchive.dto.InputOutputMeterStorySaveDTO;
import com.cdqckj.gmis.devicearchive.dto.InputOutputMeterStoryUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.InputOutputMeterStoryPageDTO;
import com.cdqckj.gmis.devicearchive.service.InputOutputMeterStoryService;
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
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/inputOutputMeterStory")
@Api(value = "InputOutputMeterStory", tags = "")
@PreAuth(replace = "inputOutputMeterStory:")
public class InputOutputMeterStoryController extends SuperController<InputOutputMeterStoryService, Long, InputOutputMeterStory, InputOutputMeterStoryPageDTO, InputOutputMeterStorySaveDTO, InputOutputMeterStoryUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<InputOutputMeterStory> inputOutputMeterStoryList = list.stream().map((map) -> {
            InputOutputMeterStory inputOutputMeterStory = InputOutputMeterStory.builder().build();
            //TODO 请在这里完成转换
            return inputOutputMeterStory;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(inputOutputMeterStoryList));
    }
}
