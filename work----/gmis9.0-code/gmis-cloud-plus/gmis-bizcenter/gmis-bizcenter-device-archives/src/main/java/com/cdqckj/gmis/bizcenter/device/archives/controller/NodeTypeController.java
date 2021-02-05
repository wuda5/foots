package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.NodeTypeBizApi;
import com.cdqckj.gmis.node.dto.NodeTypePageDTO;
import com.cdqckj.gmis.node.dto.NodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.NodeTypeUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 节点类型前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/node/nodeTyp")
@Api(value = "nodeTyp", tags = "节点型号")
public class NodeTypeController {

    @Autowired
    public NodeTypeBizApi nodeTypeBizApi;

    @ApiOperation(value = "新增节点型号信息")
    @PostMapping("/nodeType/add")
    public R<NodeType> saveNodeType(@RequestBody NodeTypeSaveDTO nodeTypeSaveDTO){
        return nodeTypeBizApi.save(nodeTypeSaveDTO);
    }

    @ApiOperation(value = "更新节点类型型号信息")
    @PutMapping("/nodeType/update")
    public R<NodeType> updateNodeType(@RequestBody NodeTypeUpdateDTO nodeTypeUpdateDTO){
        return nodeTypeBizApi.update(nodeTypeUpdateDTO);
    }

    @ApiOperation(value = "删除节点型号信息")
    @DeleteMapping("/nodeType/delete")
    public R<Boolean> deleteNodeType(@RequestParam("ids[]") List<Long> ids){
        return nodeTypeBizApi.delete(ids);
    }

    @ApiOperation(value = "分页查询节点型号信息")
    @PostMapping("/nodeType/page")
    public R<Page<NodeType>> pageNodeType(@RequestBody PageParams<NodeTypePageDTO> params){
        return nodeTypeBizApi.page(params);
    }

    @ApiOperation(value = "按条件查询气表厂家信息")
    @PostMapping("/gasMeterFactory/query")
    public R<List<NodeType>> queryGasMeterFactory(@RequestBody NodeType queryDTO){
        return nodeTypeBizApi.query(queryDTO);
    }

}
