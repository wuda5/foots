package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.archive.hystrix.NodeBizApiBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.nodearchive.dto.NodePageDTO;
import com.cdqckj.gmis.nodearchive.dto.NodeSaveDTO;
import com.cdqckj.gmis.nodearchive.dto.NodeUpdateDTO;
import com.cdqckj.gmis.nodearchive.entity.Node;
import com.cdqckj.gmis.sim.dto.CardSaveDTO;
import com.cdqckj.gmis.sim.dto.CardUpdateDTO;
import com.cdqckj.gmis.sim.entity.Card;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = NodeBizApiBizApiFallback.class
        , path = "/node", qualifier = "nodeBizApi")
public interface NodeBizApi {
    @RequestMapping(method = RequestMethod.POST)
    R<Node> saveNode(@RequestBody NodeSaveDTO saveDTO);

    @RequestMapping(method = RequestMethod.PUT)
    R<Node> updateNode(@RequestBody NodeUpdateDTO updateDTO);

    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> deleteNode(@RequestParam("ids[]") List<Long> id);


    @PostMapping(value = "/page")
    R<IPage<Node>> page(@RequestBody @Validated PageParams<NodePageDTO> params);

    @DeleteMapping(value = "/logicalDelete")
        //@PreAuth("hasPermit('{}logicalDelete')")
    R<Card> logicalDelete(@RequestParam("ids[]") List<Id> ids) throws Exception;
}
