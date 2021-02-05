package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfPageDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionQueryDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import com.cdqckj.gmis.operateparam.service.GiveReductionConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 赠送减免活动配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/giveReductionConf")
@Api(value = "GiveReductionConf", tags = "赠送减免活动配置")
//@PreAuth(replace = "giveReductionConf:")
public class GiveReductionConfController extends SuperController<GiveReductionConfService, Long, GiveReductionConf, GiveReductionConfPageDTO, GiveReductionConfSaveDTO, GiveReductionConfUpdateDTO> {


    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GiveReductionConf> giveReductionConfList = list.stream().map((map) -> {
            GiveReductionConf giveReductionConf = GiveReductionConf.builder().build();
            //TODO 请在这里完成转换
            return giveReductionConf;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(giveReductionConfList));
    }

    @ApiOperation(value = "条件查询有效可用的赠送减免活动")
    @PostMapping("/queryEffectiveGiveReduction")
    R<List<GiveReductionConf>> queryEffectiveGiveReduction(@RequestBody GiveReductionQueryDTO queryDTO) {
        return baseService.queryEffectiveGiveReduction(queryDTO);
    }
}
