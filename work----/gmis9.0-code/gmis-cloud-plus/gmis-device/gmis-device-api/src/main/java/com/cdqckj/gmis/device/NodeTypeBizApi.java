package com.cdqckj.gmis.device;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.hystrix.NodeTypeBizApiFallback;
import com.cdqckj.gmis.node.dto.NodeTypePageDTO;
import com.cdqckj.gmis.node.dto.NodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.NodeTypeUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeFactory;
import com.cdqckj.gmis.node.entity.NodeType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节点类型信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.device-server:gmis-device-server}", fallback = NodeTypeBizApiFallback.class
        , path = "/nodeType", qualifier = "nodeTypeBizApi")
public interface NodeTypeBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<NodeType> save(@RequestBody NodeTypeSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<NodeType> update(@RequestBody NodeTypeUpdateDTO updateDTO);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<NodeType>> page(@RequestBody PageParams<NodeTypePageDTO> params);

    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/query")
    R<List<NodeType>> query(@RequestBody NodeType queryDTO);
}
