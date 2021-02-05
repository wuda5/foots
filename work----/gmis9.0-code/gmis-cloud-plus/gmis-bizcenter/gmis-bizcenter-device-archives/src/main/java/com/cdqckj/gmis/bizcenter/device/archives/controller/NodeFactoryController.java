package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.NodeFactoryBizApi;
import com.cdqckj.gmis.node.dto.NodeFactoryPageDTO;
import com.cdqckj.gmis.node.dto.NodeFactorySaveDTO;
import com.cdqckj.gmis.node.dto.NodeFactoryUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 节点工厂前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/node/nodeFac")
@Api(value = "nodeFac", tags = "节点厂家")
//@PreAuth(replace = "businessHall:")
public class NodeFactoryController {

    @Autowired
    public NodeFactoryBizApi nodeFactoryBizApi;

    @ApiOperation(value = "新增节点厂家信息")
    @PostMapping("/nodeFactory/add")
    public R<NodeFactory> saveNodeFactory(@RequestBody NodeFactorySaveDTO nodeFactorySaveDTO){
        return nodeFactoryBizApi.save(nodeFactorySaveDTO);
    }

    @ApiOperation(value = "更新节点厂家信息")
    @PutMapping("/nodeFactory/update")
    public R<NodeFactory> updateNodeFactory(@RequestBody NodeFactoryUpdateDTO nodeFactoryUpdateDTO){
        return nodeFactoryBizApi.update(nodeFactoryUpdateDTO);
    }

    @ApiOperation(value = "删除节点厂家信息")
    @DeleteMapping("/nodeFactory/delete")
    public R<Boolean> deleteNodeFactory(@RequestParam("ids[]") List<Long> ids){
        return nodeFactoryBizApi.delete(ids);
    }

    @ApiOperation(value = "分页查询节点厂家信息")
    @PostMapping("/nodeFactory/page")
    public R<Page<NodeFactory>> pageNodeFactory(@RequestBody PageParams<NodeFactoryPageDTO> params){
        return nodeFactoryBizApi.page(params);
    }

    @ApiOperation(value = "按条件查询气表厂家信息")
    @PostMapping("/gasMeterFactory/query")
    public R<List<NodeFactory>> queryGasMeterFactory(@RequestBody NodeFactory queryDTO){
        return nodeFactoryBizApi.query(queryDTO);
    }
}
