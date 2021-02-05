package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.UseNodeTypeBizApi;
import com.cdqckj.gmis.node.dto.UseNodeTypePageDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypeUpdateDTO;
import com.cdqckj.gmis.node.entity.UseNodeType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 节点型号管理前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/node/useNode")
@Api(value = "useNode", tags = "节点型号管理")
public class UseNodeTypeController {

    @Autowired
    public UseNodeTypeBizApi useNodeTypeBizApi;

    @ApiOperation(value = "新增节点型号信息")
    @PostMapping("/useNodeType/add")
    public R<UseNodeType> saveUseNodeType(@RequestBody UseNodeTypeSaveDTO useNodeTypeSaveDTO){
        return useNodeTypeBizApi.save(useNodeTypeSaveDTO);
    }

    @ApiOperation(value = "更改节点型号状态")
    @PutMapping("/useNodeType/update")
    public R<UseNodeType> updateUseNodeType(@RequestBody UseNodeTypeUpdateDTO useNodeTypeUpdateDTO){
        return useNodeTypeBizApi.update(useNodeTypeUpdateDTO);
    }

   /* @ApiOperation(value = "删除节点型号信息")
    @DeleteMapping("/useNodeType/delete")
    public R<Boolean> deleteUseNodeType(@RequestParam("ids[]") List<Long> ids){
        return useNodeTypeBizApi.delete(ids);
    }
*/
    @ApiOperation(value = "分页查询节点型号信息")
    @PostMapping("/useNodeType/page")
    public R<Page<UseNodeType>> pageUseNodeType(@RequestBody PageParams<UseNodeTypePageDTO> params){
        return useNodeTypeBizApi.page(params);
    }

    @ApiOperation(value = "按条件查询气表厂家信息")
    @PostMapping("/gasMeterFactory/query")
    public R<List<UseNodeType>> queryGasMeterFactory(@RequestBody UseNodeType queryDTO){
        return useNodeTypeBizApi.query(queryDTO);
    }

}
