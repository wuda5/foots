package com.cdqckj.gmis.node.controller.common;

import com.cdqckj.gmis.node.entity.NodeFactory;
import com.cdqckj.gmis.node.dto.NodeFactorySaveDTO;
import com.cdqckj.gmis.node.dto.NodeFactoryUpdateDTO;
import com.cdqckj.gmis.node.dto.NodeFactoryPageDTO;
import com.cdqckj.gmis.node.service.NodeFactoryService;
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
@RequestMapping("/nodeFactory")
@Api(value = "NodeFactory", tags = "节点厂家")
@PreAuth(replace = "nodeFactory:")
public class NodeFactoryController extends SuperController<NodeFactoryService, Long, NodeFactory, NodeFactoryPageDTO, NodeFactorySaveDTO, NodeFactoryUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<NodeFactory> nodeFactoryList = list.stream().map((map) -> {
            NodeFactory nodeFactory = NodeFactory.builder().build();
            //TODO 请在这里完成转换
            return nodeFactory;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(nodeFactoryList));
    }
}
