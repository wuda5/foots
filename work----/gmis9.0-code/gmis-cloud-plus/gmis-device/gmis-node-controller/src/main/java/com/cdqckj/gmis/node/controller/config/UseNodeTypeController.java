package com.cdqckj.gmis.node.controller.config;

import com.cdqckj.gmis.node.entity.UseNodeType;
import com.cdqckj.gmis.node.dto.UseNodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypeUpdateDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypePageDTO;
import com.cdqckj.gmis.node.service.UseNodeTypeService;
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
 * 节点类型管理
 * </p>
 *
 * @author gmis
 * @date 2020-07-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/useNodeType")
@Api(value = "UseNodeType", tags = "节点类型管理")
@PreAuth(replace = "useNodeType:")
public class UseNodeTypeController extends SuperController<UseNodeTypeService, Long, UseNodeType, UseNodeTypePageDTO, UseNodeTypeSaveDTO, UseNodeTypeUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<UseNodeType> useNodeTypeList = list.stream().map((map) -> {
            UseNodeType useNodeType = UseNodeType.builder().build();
            //TODO 请在这里完成转换
            return useNodeType;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(useNodeTypeList));
    }
}
