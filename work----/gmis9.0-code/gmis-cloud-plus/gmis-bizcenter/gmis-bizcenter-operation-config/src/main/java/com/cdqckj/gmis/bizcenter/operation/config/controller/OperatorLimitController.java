package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.operation.config.service.OperatorLimitService;
import com.cdqckj.gmis.operateparam.CompanyUserQuotaRecordBizApi;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordPageDTO;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;
import com.cdqckj.gmis.operateparam.vo.BusinessHallVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户配额前端控制器
 * </p>
 * @author gmis
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/operatorlimit")
@Api(value = "operatorlimit", tags = "操作员配额")
//@PreAuth(replace = "street:")
public class OperatorLimitController {

    @Autowired
    public CompanyUserQuotaRecordBizApi companyUserQuotaRecordBizApi;
    @Autowired
    public UserBizApi userBizApi;
    @Autowired
    public OperatorLimitService operatorLimitService;

    @ApiOperation(value = "新增用户配额信息")
    @PostMapping("/companyUserQuotaRecord/add")
    public R<CompanyUserQuotaRecord> saveCompanyUserQuotaRecord(@RequestBody CompanyUserQuotaRecordSaveDTO saveDTO){
        return operatorLimitService.saveCompanyUserQuotaRecord(saveDTO);
    }

    @ApiOperation(value = "分页查询用户配额信息")
    @PostMapping("/companyUserQuotaRecord/page")
    public R<Page<CompanyUserQuotaRecord>> pageCompanyUserQuotaRecord(@RequestBody PageParams<CompanyUserQuotaRecordPageDTO> params){
        return companyUserQuotaRecordBizApi.page(params);
    }

    @ApiOperation(value = "分页查询营业厅营业员配额信息")
    @PostMapping("/businessHall/page")
    public R<Map<String, List<BusinessHallVO>>> pageBusinessHall(){
        return companyUserQuotaRecordBizApi.getUserBusinessHall();
    }

}
