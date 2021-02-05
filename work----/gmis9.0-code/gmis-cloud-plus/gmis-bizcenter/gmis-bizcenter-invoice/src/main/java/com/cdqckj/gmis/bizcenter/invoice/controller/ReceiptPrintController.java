package com.cdqckj.gmis.bizcenter.invoice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.invoice.dto.ReceiptPrintDTO;
import com.cdqckj.gmis.bizcenter.invoice.service.ReceiptPrintService;
import com.cdqckj.gmis.invoice.ReceiptPrintRecordBizApi;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordPageDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptPrintRecord;
import com.cdqckj.gmis.security.annotation.LoginUser;
import com.cdqckj.gmis.security.model.SysUser;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.enumeration.TemplateTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * <p>
 * 票据打印记录
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/receiptPrint")
@Api(value = "receiptPrint", tags = "票据打印记录")
//@PreAuth(replace = "invoicePlatConfig:")
public class ReceiptPrintController {

    @Autowired
    private ReceiptPrintRecordBizApi receiptPrintRecordBizApi;

    @Autowired
    private ReceiptPrintService receiptPrintService;

    @ApiOperation(value = "新增票据打印记录")
    @PostMapping("/add")
    public R<ReceiptPrintRecord> add(@RequestBody ReceiptPrintDTO saveDTO, @ApiIgnore @LoginUser(isFull = true) SysUser user) {
        return receiptPrintService.add(saveDTO, user);
    }

    @ApiOperation(value = "删除票据打印记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键id", dataType = "array", paramType = "query"),
    })
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestParam("ids") List<Long> ids) {
        return receiptPrintRecordBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询票据打印记录")
    @PostMapping("/page")
    public R<Page<ReceiptPrintRecord>> page(@RequestBody PageParams<ReceiptPrintRecordPageDTO> params) {
        return receiptPrintRecordBizApi.page(params);
    }

    @ApiOperation(value = "获取打印模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "模板编码(GAS_BILL：燃气收费票据;" +
                    "COMMODITY_CHARGE_BILL：商品收费票据;" +
                    "SCENARIO_CHARGE_TICKET：场景收费票据;" +
                    "ACCESSORY_FEE_BILL：附件费票据;" +
                    "PREMIUM_NOTE：保险费票据)", dataType = "string", paramType = "query"),
    })
    @GetMapping("/getPrintTemplate")
    R<TemplateItem> getPrintTemplate(@RequestParam(value = "templateCode") TemplateTypeEnum.Type.Ticket templateCode) {
        return receiptPrintRecordBizApi.getPrintTemplate(templateCode);
    }

}
