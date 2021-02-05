package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operateparam.dto.UseGasTypePageDTO;
import com.cdqckj.gmis.operateparam.dto.UseGasTypeSaveDTO;
import com.cdqckj.gmis.operateparam.dto.UseGasTypeUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/useGasType")
@Api(value = "UseGasType", tags = "")
//@PreAuth(replace = "useGasType:")
public class UseGasTypeController extends SuperController<UseGasTypeService, Long, UseGasType, UseGasTypePageDTO, UseGasTypeSaveDTO, UseGasTypeUpdateDTO> {

    @Resource
    private UseGasTypeService useGasTypeService;

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<UseGasType> useGasTypeList = list.stream().map((map) -> {
            UseGasType useGasType = UseGasType.builder().build();
            //TODO 请在这里完成转换
            return useGasType;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(useGasTypeList));
    }

    @ApiOperation(value = "检测用气类型名称是否有重复,true代表重复", notes = "检测用气类型名称是否有重复,true代表重复")
    @GetMapping("/checkUseGasType/{useGasTypeName}")
    public R<Boolean> checkUseGasType(@RequestParam(value = "id",required = false) Long id, @PathVariable(value = "useGasTypeName") String useGasTypeName) {
        int count = baseService.count(Wraps.<UseGasType>lbQ().eq(UseGasType::getUseGasTypeName, useGasTypeName)
                .ne(UseGasType::getId, id));
        return success(count > 0);
    }

    /**
     * 根据用气类型名称获取用气类型信息
     *
     * @return
     */
    @ApiOperation(value = "根据用气类型名称获取用气类型信息")
    @GetMapping(value = "/getUseGasType/{useGasTypeName}")
    public R<UseGasType> getUseGasType(@PathVariable(value = "useGasTypeName") String useGasTypeName) {
        return useGasTypeService.getUseGasType(useGasTypeName);
    }

    @ApiOperation(value = "查询最新一条用气类型数据")
    @GetMapping(value = "/queryRentUseGasType")
    public UseGasType queryRentUseGasType() {
        return useGasTypeService.queryRentUseGasType();
    }

    @ApiOperation(value = "通过ID查询用气类型及价格方案")
    @GetMapping(value = "/queryUseGasTypeAndPrice")
    public R<UseGasTypeVO> queryUseGasTypeAndPrice(@RequestParam("id") Long id) {
        return R.success(useGasTypeService.queryUseGasTypeAndPrice(id));
    }



}
