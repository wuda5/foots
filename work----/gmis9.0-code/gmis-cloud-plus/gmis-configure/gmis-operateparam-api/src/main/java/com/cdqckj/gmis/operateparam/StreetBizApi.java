package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.StreetPageDTO;
import com.cdqckj.gmis.operateparam.dto.StreetSaveDTO;
import com.cdqckj.gmis.operateparam.dto.StreetUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.hystrix.StreetBizApiFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = StreetBizApiFallback.class
        , path = "/street", qualifier = "streetBizApi")
public interface StreetBizApi {

    /**
     * @param pageNo
     * @param pageSize
     * @param streetId
     * @return
     */
    @RequestMapping(value = "/communityList", method = RequestMethod.GET)
    R<Map<String,Object>> getCommunityListByStreetId(@RequestParam("pageNo") Integer pageNo,
                                                   @RequestParam("pageSize")Integer pageSize,
                                                   @RequestParam("streetId")Long streetId);
    /**
     * 保存街道
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<Street> save(@RequestBody StreetSaveDTO saveDTO);

    /**
     * 更新街道
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<Street> update(@RequestBody StreetUpdateDTO updateDTO);

    /**
     * 删除街道
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询街道
      * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<Street>> page(@RequestBody PageParams<StreetPageDTO> params);

    @GetMapping("/{id}")
     R<Street> get(@PathVariable("id") Long id);

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody StreetUpdateDTO streetUpdateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<Street>> query(@RequestBody Street data);


    @PostMapping("/queryOne")
    R<Street> queryOne(@RequestBody Street query);
}
