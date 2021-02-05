package com.cdqckj.gmis.device;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.hystrix.UseNodeTypeBizApiFallback;
import com.cdqckj.gmis.node.dto.UseNodeTypePageDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypeUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeType;
import com.cdqckj.gmis.node.entity.UseNodeType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节点型号管理信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.device-server:gmis-device-server}", fallback = UseNodeTypeBizApiFallback.class
        , path = "/useNodeType", qualifier = "useNodeTypeBizApi")
public interface UseNodeTypeBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<UseNodeType> save(@RequestBody UseNodeTypeSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<UseNodeType> update(@RequestBody UseNodeTypeUpdateDTO updateDTO);

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
    R<Page<UseNodeType>> page(@RequestBody PageParams<UseNodeTypePageDTO> params);

    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/query")
    R<List<UseNodeType>> query(@RequestBody UseNodeType queryDTO);
}
