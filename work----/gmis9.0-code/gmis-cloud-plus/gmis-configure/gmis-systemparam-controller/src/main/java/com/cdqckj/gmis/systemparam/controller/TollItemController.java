package com.cdqckj.gmis.systemparam.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.dto.TollItemPageDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 收费项配置表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tollItem")
@Api(value = "TollItem", tags = "收费项配置表")
@PreAuth(replace = "tollItem:")
public class TollItemController extends SuperController<TollItemService, Long, TollItem, TollItemPageDTO, TollItemSaveDTO, TollItemUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<TollItem> tollItemList = list.stream().map((map) -> {
            TollItem tollItem = TollItem.builder().build();
            //TODO 请在这里完成转换
            return tollItem;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(tollItemList));
    }
    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody TollItemSaveDTO tollItemSaveDTO){
        return R.success(baseService.check(tollItemSaveDTO));
    }

    @ApiOperation("场景是否收费")
    @GetMapping("/whetherSceneCharge")
    R<Boolean> whetherSceneCharge(@RequestParam("sceneCode") @NotNull String sceneCode){

        return R.success(baseService.existTollItem(sceneCode));
    }
}
