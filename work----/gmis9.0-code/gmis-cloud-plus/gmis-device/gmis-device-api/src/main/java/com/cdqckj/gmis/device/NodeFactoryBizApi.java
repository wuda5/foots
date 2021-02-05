package com.cdqckj.gmis.device;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.hystrix.NodeFactoryBizApiFallback;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.node.dto.NodeFactoryPageDTO;
import com.cdqckj.gmis.node.dto.NodeFactorySaveDTO;
import com.cdqckj.gmis.node.dto.NodeFactoryUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节点工厂信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.device-server:gmis-device-server}", fallback = NodeFactoryBizApiFallback.class
        , path = "/nodeFactory", qualifier = "nodeFactoryBizApi")
public interface NodeFactoryBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<NodeFactory> save(@RequestBody NodeFactorySaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<NodeFactory> update(@RequestBody NodeFactoryUpdateDTO updateDTO);

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
    R<Page<NodeFactory>> page(@RequestBody PageParams<NodeFactoryPageDTO> params);

    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/query")
    R<List<NodeFactory>> query(@RequestBody NodeFactory queryDTO);
}
