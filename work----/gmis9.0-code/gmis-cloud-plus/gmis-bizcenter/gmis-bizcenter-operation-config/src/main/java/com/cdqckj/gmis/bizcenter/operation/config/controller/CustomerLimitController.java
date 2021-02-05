package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.PurchaseLimitCustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.vo.BatchUserDeviceVO;
import com.cdqckj.gmis.operateparam.PurchaseLimitBizApi;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitPageDTO;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.dto.PurchaseLimitCustomerSaveDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/customerlimit")
@Api(value = "customerlimit", tags = "限购客户型配置")
public class CustomerLimitController {
    @Autowired
    private PurchaseLimitBizApi purchaseLimitBizApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private PurchaseLimitCustomerBizApi purchaseLimitCustomerBizApi;

    @ApiOperation(value = "分页查询限购列表信息")
    @PostMapping(value = "/purchaseLimit/page")
    public R<Page<PurchaseLimit>> pagePurchaseLimit(@RequestBody PageParams<PurchaseLimitPageDTO> params){
        return purchaseLimitBizApi.page(params);
    }
    @PostMapping(value = "/saveList")
    @ApiOperation(value = "新增限购客户信息")
    R<Boolean> saveList(@RequestBody List<PurchaseLimitCustomerSaveDTO> list){
        if(list==null||list.size()==0){return null;}
        purchaseLimitCustomerBizApi.saveList(list);
        // 不在使用客户表具关联表去xxxx 耦合，查询未限购的客户表具时采用子查询代替--
//        List<BatchUserDeviceVO> listUserGas = new ArrayList<>();
//        for(PurchaseLimitCustomerSaveDTO pu:list){
//            BatchUserDeviceVO batchUserDeviceVO = new BatchUserDeviceVO();
//            batchUserDeviceVO.setDataStatus(6);//?? 6
//            batchUserDeviceVO.setGasCode(pu.getGasMeterCode());
//            listUserGas.add(batchUserDeviceVO);
//        }
//        customerGasMeterRelatedBizApi.updateBatchUserGas(listUserGas);
        return R.successDef(true);
    }
    @ApiOperation(value = "删除限购客户信息")
    @DeleteMapping("/community/delete")
    public R<Boolean> deleteCommunity(@RequestParam("ids[]") List<Long> id){
        purchaseLimitCustomerBizApi.delete(id);
        // 新增客户表具到限购方案下时，已去除关联操作客户表具表，故现在去除下面的逻辑
//        List<PurchaseLimitCustomer> list = purchaseLimitCustomerBizApi.selectByIds(id);
//        if(list==null||list.size()==0){return null;}
//        List<BatchUserDeviceVO> listUserGas = new ArrayList<>();
//        for(PurchaseLimitCustomer pu:list){
//            BatchUserDeviceVO batchUserDeviceVO = new BatchUserDeviceVO();
//            batchUserDeviceVO.setDataStatus(1);
//            batchUserDeviceVO.setGasCode(pu.getGasMeterCode());
//            listUserGas.add(batchUserDeviceVO);
//        }
//        customerGasMeterRelatedBizApi.updateBatchUserGas(listUserGas);
        return R.successDef(true);
    }
    @ApiOperation(value = "根据类型查询客户设备列表")
    @PostMapping("/customerList")
    R<Page<CustomerDeviceDTO>> findLimitCustomerPage(@RequestBody CustomerDeviceVO params){
        return customerGasMeterRelatedBizApi.findLimitCustomerPage(params);
    }
    @ApiOperation(value = "查询用户设备限购列表")
    @PostMapping("/limitList")
    R<Page<CustomerDeviceDTO>> findLimitCustomerDevicePage(@RequestBody CustomerDeviceLimitVO params){
        return purchaseLimitCustomerBizApi.findLimitCustomerPage(params);
    }
}
