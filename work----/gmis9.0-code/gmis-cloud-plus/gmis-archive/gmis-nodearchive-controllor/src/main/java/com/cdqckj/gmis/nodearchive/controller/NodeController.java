package com.cdqckj.gmis.nodearchive.controller;

import com.cdqckj.gmis.nodearchive.entity.Node;
import com.cdqckj.gmis.nodearchive.dto.NodeSaveDTO;
import com.cdqckj.gmis.nodearchive.dto.NodeUpdateDTO;
import com.cdqckj.gmis.nodearchive.dto.NodePageDTO;
import com.cdqckj.gmis.nodearchive.service.NodeService;
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
 * @date 2020-08-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/node")
@Api(value = "Node", tags = "节点档案接口")
/*@PreAuth(replace = "node:")*/
public class NodeController extends SuperController<NodeService, Long, Node, NodePageDTO, NodeSaveDTO, NodeUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Node> nodeList = list.stream().map((map) -> {
            Node node = Node.builder().build();
            //TODO 请在这里完成转换
            return node;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(nodeList));
    }
}
