package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.PayInfoBizApi;
import com.cdqckj.gmis.systemparam.dto.PayInfoPageDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoSaveDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 支付配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/payInfo")
@Api(value = "payInfo", tags = "支付配置")
//@PreAuth(replace = "payInfo:")
public class PayInfoController {

    @Autowired
    public PayInfoBizApi payInfoBizApi;

    @ApiOperation(value = "新增支付信息")
    @PostMapping("/payInfo/add")
    public R<PayInfo> savePayInfo(@RequestBody PayInfoSaveDTO payInfoSaveDTO){
        return payInfoBizApi.save(payInfoSaveDTO);
    }

    @ApiOperation(value = "更新支付信息")
    @PutMapping("/payInfo/update")
    public R<PayInfo> updatePayInfo(@RequestBody PayInfoUpdateDTO payInfoUpdateDTO){
        return payInfoBizApi.update(payInfoUpdateDTO);
    }

    @ApiOperation(value = "删除支付信息")
    @DeleteMapping("/payInfo/delete")
    public R<Boolean> deletePayInfo(@RequestParam("ids[]") List<Long> ids){
        return payInfoBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询支付信息")
    @PostMapping("/payInfo/page")
    public R<Page<PayInfo>> pagePayInfo(@RequestBody PageParams<PayInfoPageDTO> params){
        return payInfoBizApi.page(params);
    }

}
