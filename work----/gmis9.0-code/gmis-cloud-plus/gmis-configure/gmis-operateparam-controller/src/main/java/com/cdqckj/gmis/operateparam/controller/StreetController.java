package com.cdqckj.gmis.operateparam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.api.OrgBizApi;
import com.cdqckj.gmis.authority.api.TenantBizApi;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.dto.StreetSaveDTO;
import com.cdqckj.gmis.operateparam.dto.StreetUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.StreetPageDTO;
import com.cdqckj.gmis.operateparam.service.CommunityService;
import com.cdqckj.gmis.operateparam.service.StreetService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2020-07-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/street")
@Api(value = "Street", tags = "")
@PreAuth(replace = "street:")
public class StreetController extends SuperController<StreetService, Long, Street, StreetPageDTO, StreetSaveDTO, StreetUpdateDTO> {

    @Autowired
    public TenantBizApi tenantBizApi;
    @Autowired
    public OrgBizApi orgBizApi;
    @Autowired
    public CommunityService communityService;
    @Autowired
    public StreetService streetService;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Street> streetList = list.stream().map((map) -> {
            Street street = Street.builder().build();
            //TODO 请在这里完成转换
            return street;
        }).collect(Collectors.toList());
        return R.success(baseService.saveBatch(streetList));
    }
    @Override
    public R<Boolean> handlerLogicalDelete(List<Long> ids){
        return R.successDef(streetService.deleteStreetAndCommunity(ids));
    }

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
    public R<IPage<Community>> getCommunityListByStreetId(@RequestParam("pageNo") Integer pageNo,
                                                          @RequestParam("pageSize")Integer pageSize,
                                                          @RequestParam("streetId")Long streetId){
        return R.success(communityService.getCommunityListByStreetId(pageNo,pageSize,streetId));
    }
//    @Override
//    public void query(PageParams<StreetPageDTO> params, IPage<Street> page, Long defSize) {
//        baseService.findPage(page, params.getModel());
//    }

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody StreetUpdateDTO streetUpdateDTO){
        return R.success(baseService.check(streetUpdateDTO));
    };
}
