package com.cdqckj.gmis.userarchive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.dto.PurchaseLimitCustomerSaveDTO;
import com.cdqckj.gmis.userarchive.dto.PurchaseLimitCustomerUpdateDTO;
import com.cdqckj.gmis.userarchive.dto.PurchaseLimitCustomerPageDTO;
import com.cdqckj.gmis.userarchive.service.PurchaseLimitCustomerService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2020-07-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/purchaseLimitCustomer")
@Api(value = "PurchaseLimitCustomer", tags = "客户限额接")
//@PreAuth(replace = "purchaseLimitCustomer:")
public class PurchaseLimitCustomerController extends SuperController<PurchaseLimitCustomerService, Long, PurchaseLimitCustomer, PurchaseLimitCustomerPageDTO, PurchaseLimitCustomerSaveDTO, PurchaseLimitCustomerUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<PurchaseLimitCustomer> purchaseLimitCustomerList = list.stream().map((map) -> {
            PurchaseLimitCustomer purchaseLimitCustomer = PurchaseLimitCustomer.builder().build();
            //TODO 请在这里完成转换
            return purchaseLimitCustomer;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(purchaseLimitCustomerList));
    }

    @ApiOperation(value = "查询用户设备限额列表")
    @PostMapping("/limitList")
    public R<Page<CustomerDeviceDTO>> findLimitCustomerPage(@RequestBody  CustomerDeviceLimitVO params){
        return R.success(baseService.findLimitCustomerPage(params));
    }
    @ApiOperation(value = "根据限购id查询")
    @PostMapping("/selectByIds")
    List<PurchaseLimitCustomer> selectByIds(@RequestBody List<Long> limitIds){
        return baseService.selectByIds(limitIds);
    }
}
