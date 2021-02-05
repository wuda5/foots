package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.api.OrgBizApi;
import com.cdqckj.gmis.authority.api.TenantBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.operation.config.service.AddressService;
import com.cdqckj.gmis.operateparam.BatcgDetailedAddressBizApi;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.dto.*;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.pay.PayBizApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/address")
@Api(value = "address", tags = "地址配置")
//@PreAuth(replace = "street:")
public class AddressController {

    @Autowired
    public TenantBizApi tenantBizApi;
    @Autowired
    public OrgBizApi orgBizApi;
    @Autowired
    public PayBizApi payBizApi;
    @Autowired
    public StreetBizApi streetBizApi;
    @Autowired
    public CommunityBizApi communityBizApi;
    @Autowired
    public AddressService addressService;
    @Autowired
    public BatcgDetailedAddressBizApi detailedAddressBizApi;
    @ApiOperation(value = "测试获取公司ID和编码")
    @RequestMapping(value = "companyInfo")
    public R<Map<String,Object>> getCompanyInfoByCode(@RequestParam("code") String code){
            return tenantBizApi.getCompanyInfoByTenantCode(code);
    }

    @ApiOperation(value = "测试获取组织列表")
    @RequestMapping(value = "treeMap")
    public R<Map<String,Object>> getOrgTree() {
        Map<String,Object> data = orgBizApi.getOrgTree().getData();
        return orgBizApi.getOrgTree();
    }

    @ApiOperation(value = "根据街道Id查询小区列表")
    @GetMapping("/communityList")
    public R<Map<String,Object>> getCommunityListByStreetId(@RequestParam("pageNo") Integer pageNo,
                                                            @RequestParam("pageSize")Integer pageSize,
                                                            @RequestParam("streetId")Long streetId){
        return streetBizApi.getCommunityListByStreetId(pageNo,pageSize,streetId);
    }

    @ApiOperation(value = "新增小区信息")
    @PostMapping("/community/add")
    public R<Community> saveCommunity(@RequestBody CommunitySaveDTO communitySaveDTO){
       return addressService.saveCommunity(communitySaveDTO);
    }

    @ApiOperation(value = "更新小区信息")
    @PutMapping("/community/update")
    public R<Community> updateCommunity(@RequestBody CommunityUpdateDTO communityUpdateDTO){
        return addressService.updateCommunity(communityUpdateDTO);
    }

    @ApiOperation(value = "删除小区信息")
    @DeleteMapping("/community/delete")
    public R<Boolean> deleteCommunity(@RequestParam("ids[]") List<Long> id){
        return communityBizApi.delete(id);
    }

    @ApiOperation(value = "分页查询小区信息")
    @PostMapping("/community/page")
    public R<Page<Community>> pageCommunity(@RequestBody PageParams<CommunityPageDTO> params){
        return communityBizApi.page(params);
    }

    @ApiOperation(value = "新增街道信息")
    @PostMapping("/street/add")
    public R<Street> saveStreet(@RequestBody StreetSaveDTO streetSaveDTO){
        return addressService.saveStreet(streetSaveDTO);
    }

    @ApiOperation(value = "更新街道信息")
    @PutMapping("/street/update")
    public R<Street> updateStreet(@RequestBody StreetUpdateDTO streetUpdateDTO){
        return addressService.updateStreet(streetUpdateDTO);
    }

    @ApiOperation(value = "删除街道信息")
    @DeleteMapping("/street/delete")
    public R<Boolean> deleteStreet(@RequestParam("ids[]") List<Long> id){
        return streetBizApi.delete(id);
    }

    @ApiOperation(value = "分页查询街道信息信息")
    @PostMapping("/street/page")
    public R<Page<Street>> pageStreet(@RequestBody PageParams<StreetPageDTO> params){
        R<Page<Street>> r = streetBizApi.page(params);
        return r;
    }
    @ApiOperation(value = "批量建址")
    @PostMapping("/batchdetailedAddress/batchAddresss")
    public R<BatchDetailedAddress> batchAddresss(@RequestBody BatchDetailedAddressSaveDTO detailedAddressSaveDTO){
//        return addressService.batchAddresss(detailedAddressSaveDTO);
        return addressService.batchAddresssEx(detailedAddressSaveDTO);
    }

    /**
     * 单个建址
     * updater hc
     * @date 2020/12/09
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "单个建址")
    @PostMapping("/batchdetailedAddress/saveDetailAddresss")
    R<BatchDetailedAddress> saveDetailAddresss(@RequestBody BatchDetailedAddressSaveDTO saveDTO){
       return addressService.saveDetailAddresss(saveDTO);
    };
    @ApiOperation(value = "分页查询详细地址")
    @PostMapping("/batchdetailedAddress/page")
    R<Page<BatchDetailedAddress>> page(@RequestBody  PageParams<BatchDetailedAddressPageDTO> params){
        return detailedAddressBizApi.page(params);
    }
    @ApiOperation(value = "更新详细地址信息")
    @PutMapping("batchdetailedAddress/update")
    R<BatchDetailedAddress> updateDetailAddresss(@RequestBody BatchDetailedAddressUpdateDTO updateDTO){
       return addressService.updateDetailAddresss(updateDTO);
    }
    @ApiOperation(value = "删除")
    @DeleteMapping("batchdetailedAddress/delete")
    R<Boolean> deleteDetailAddresss(@RequestParam("ids[]") List<Long> id){
        return detailedAddressBizApi.delete(id);
    }
}
