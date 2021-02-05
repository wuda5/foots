package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.CommunityPageDTO;
import com.cdqckj.gmis.operateparam.dto.CommunitySaveDTO;
import com.cdqckj.gmis.operateparam.dto.CommunityUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.hystrix.CommunityBizApiFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = CommunityBizApiFallback.class
        , path = "/community", qualifier = "communityBizApi")
public interface CommunityBizApi {

    /**
     * 保存小区信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<Community> save(@RequestBody CommunitySaveDTO saveDTO);

    /**
     * 更新小区信息
      * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<Community> update(@RequestBody CommunityUpdateDTO updateDTO);

    /**
     * 删除小区信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询小区信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<Community>> page(@RequestBody PageParams<CommunityPageDTO> params);

    @GetMapping("/{id}")
    R<Community> get(@PathVariable("id") Long id);

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody CommunityUpdateDTO communityUpdateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<Community>> query(@RequestBody Community data);
}
