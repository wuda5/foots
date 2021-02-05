package com.cdqckj.gmis.userarchive.controller;

import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.userarchive.entity.CustomerBlacklist;
import com.cdqckj.gmis.userarchive.dto.CustomerBlacklistSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerBlacklistUpdateDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerBlacklistPageDTO;
import com.cdqckj.gmis.userarchive.service.CustomerBlacklistService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerBlacklist")
@Api(value = "CustomerBlacklist", tags = "黑名单接口")
@PreAuth(replace = "customerBlacklist:")
public class CustomerBlacklistController extends SuperController<CustomerBlacklistService, Long, CustomerBlacklist, CustomerBlacklistPageDTO, CustomerBlacklistSaveDTO, CustomerBlacklistUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerBlacklist> customerBlacklistList = list.stream().map((map) -> {
            CustomerBlacklist customerBlacklist = CustomerBlacklist.builder().build();
            //TODO 请在这里完成转换
            return customerBlacklist;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerBlacklistList));
    }
    @ApiOperation(value = "查询黑名单状态")
    @PostMapping("/BlacklistStatus")
    public R<CustomerBlacklist>  findBlacklistStatus(@RequestBody String customerCode) {
       return R.success(this.baseService.findBlacklistStatusByCustomerCode(customerCode));
    }

/*    @ApiOperation(value = "设置黑名单状态")
    @PostMapping("/SetBlacklist")
    public R<Boolean>  SetBlacklist(@RequestParam("customerCode") String customerCode) {
        return this.baseService.SetBlacklist(customerCode);
    }
    @ApiOperation(value = "移除黑名单")
    @PostMapping("/RemoveBlacklist")
    public R<Boolean>  RemoveBlacklist(@RequestParam("customerCode") String customerCode) {
        return this.baseService.RemoveBlacklist(customerCode);
    }*/


}
