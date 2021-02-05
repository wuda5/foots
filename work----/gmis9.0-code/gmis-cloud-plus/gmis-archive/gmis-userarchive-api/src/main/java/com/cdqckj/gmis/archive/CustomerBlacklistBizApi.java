package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.hystrix.CustomerBlacklistBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.userarchive.dto.CustomerBlacklistPageDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerBlacklistSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerBlacklistUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.entity.CustomerBlacklist;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = CustomerBlacklistBizApiFallback.class
        , path = "/customerBlacklist", qualifier = "customerblacklistbizapi")
public interface CustomerBlacklistBizApi {
    /**
     * @param
     * @return
     */
    @PostMapping(value = "/saveList")
    R<CustomerBlacklist> save(@RequestBody List<CustomerBlacklistSaveDTO> saveDTOs);

    /**
     * @param updateDTO
     * @return
     */
    @PutMapping(value = "/updateBatch")
    R<CustomerBlacklist> update(@RequestBody List<CustomerBlacklistUpdateDTO> updateDTO);

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
    R<Page<CustomerBlacklist>> page(@RequestBody PageParams<CustomerBlacklistPageDTO> params);

    @PostMapping("/BlacklistStatus")
    R<CustomerBlacklist> findBlacklistStatus(@RequestBody String customerCode);

  /*  @ApiOperation(value = "设置黑名单")
    @PostMapping("/SetBlacklist")
   R<Boolean>  SetBlacklist(@Param("customerCode") String customerCode);

    @ApiOperation(value = "移除黑名单")
    @PostMapping("/RemoveBlacklist")
    public R<Boolean> RemoveBlacklist(@RequestParam("customerCode") String customerCode);*/
}
