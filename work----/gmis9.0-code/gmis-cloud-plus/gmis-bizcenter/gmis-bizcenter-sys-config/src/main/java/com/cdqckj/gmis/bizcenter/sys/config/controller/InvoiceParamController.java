package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.InvoiceParamBizApi;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamPageDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoiceParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 开票参数前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/invoiceParam")
@Api(value = "invoiceParam", tags = "开票参数配置")
//@PreAuth(replace = "invoiceParam:")
public class InvoiceParamController {

    @Autowired
    public InvoiceParamBizApi invoiceParamBizApi;

    @ApiOperation(value = "新增开票参数")
    @PostMapping("/invoiceParam/add")
    public R<InvoiceParam> saveInvoiceParam(@RequestBody InvoiceParamSaveDTO invoiceParamSaveDTO){
        return invoiceParamBizApi.save(invoiceParamSaveDTO);
    }

    @ApiOperation(value = "更新开票参数")
    @PutMapping("/invoiceParam/update")
    public R<InvoiceParam> updateInvoiceParam(@RequestBody InvoiceParamUpdateDTO invoiceParamUpdateDTO){
        return invoiceParamBizApi.update(invoiceParamUpdateDTO);
    }

    @ApiOperation(value = "删除开票参数")
    @DeleteMapping("/invoiceParam/delete")
    public R<Boolean> deleteInvoiceParam(@RequestParam("ids[]") List<Long> ids){
        return invoiceParamBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询开票参数")
    @PostMapping("/invoiceParam/page")
    public R<Page<InvoiceParam>> pageInvoiceParam(@RequestBody PageParams<InvoiceParamPageDTO> params){
        return invoiceParamBizApi.page(params);
    }

}
