package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.PenaltyPageDTO;
import com.cdqckj.gmis.operateparam.dto.PenaltySaveDTO;
import com.cdqckj.gmis.operateparam.dto.PenaltyUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import com.cdqckj.gmis.operateparam.hystrix.PenaltyBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = PenaltyBizApiFallback.class
        , path = "/penalty", qualifier = "penaltyBizApi")
public interface PenaltyBizApi {

    /**
     * 保存滞纳金信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<Penalty> save(@RequestBody PenaltySaveDTO saveDTO);

    /**
     * 更新滞纳金信息
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<Penalty> update(@RequestBody PenaltyUpdateDTO updateDTO);

    /**
     * 删除滞纳金信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 根据id查询价格详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<Penalty> queryById(@PathVariable("id") Long id);

    /**
     * 查询最新一条记录
     * @return
     */
    @GetMapping(value = "/queryRecentRecord")
    Penalty queryRecentRecord();

    /**
     * 分页查询滞纳金信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<Penalty>> page(@RequestBody PageParams<PenaltyPageDTO> params);


    @PostMapping("/query")
    R<List<Penalty>> query(@RequestBody Penalty data);

    @PostMapping(value = "/check")
    R<String> check(@RequestBody PenaltyUpdateDTO penaltyUpdateDTO);
    @PostMapping("/getById")
    R<Penalty> getById(@RequestParam("id") Long id);
    /**
     * 根据用气类型id获取滞纳金
     * @param useGasId
     * @return
     */
    @GetMapping(value = "/queryByUseGasId")
    R<Penalty> queryByUseGasId(@RequestParam("useGasId") Long useGasId);
}
