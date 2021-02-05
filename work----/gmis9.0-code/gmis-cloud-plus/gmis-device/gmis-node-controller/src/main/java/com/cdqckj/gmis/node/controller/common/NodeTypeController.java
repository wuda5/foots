package com.cdqckj.gmis.node.controller.common;

import com.cdqckj.gmis.node.entity.NodeType;
import com.cdqckj.gmis.node.dto.NodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.NodeTypeUpdateDTO;
import com.cdqckj.gmis.node.dto.NodeTypePageDTO;
import com.cdqckj.gmis.node.service.NodeTypeService;
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
 * @date 2020-07-21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/nodeType")
@Api(value = "NodeType", tags = "节点型号")
@PreAuth(replace = "nodeType:")
public class NodeTypeController extends SuperController<NodeTypeService, Long, NodeType, NodeTypePageDTO, NodeTypeSaveDTO, NodeTypeUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<NodeType> nodeTypeList = list.stream().map((map) -> {
            NodeType nodeType = NodeType.builder().build();
            //TODO 请在这里完成转换
            return nodeType;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(nodeTypeList));
    }
}
