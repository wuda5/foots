package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.sys.config.service.InvoicePlatConfigService;
import com.cdqckj.gmis.operateparam.InvoicePlatConfigBizApi;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigPageDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoicePlatConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 电子发票平台配置前端控制器
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/invoicePlatConfig")
@Api(value = "invoicePlatConfig", tags = "电子发票服务平台配置")
//@PreAuth(replace = "invoicePlatConfig:")
public class InvoicePlatConfigController {

    @Autowired
    public InvoicePlatConfigBizApi invoicePlatConfigBizApi;
    @Autowired
    InvoicePlatConfigService invoicePlatConfigService;

    @ApiOperation(value = "新增电子发票服务平台配置")
    @PostMapping("/add")
    public R<InvoicePlatConfig> saveInvoiceParam(@RequestBody InvoicePlatConfigSaveDTO invoicePlatConfigSaveDTO) {
        return invoicePlatConfigBizApi.save(invoicePlatConfigSaveDTO);
    }

    @ApiOperation(value = "更新电子发票服务平台配置")
    @PutMapping("/update")
    public R<InvoicePlatConfig> updateInvoiceParam(@RequestBody InvoicePlatConfigUpdateDTO invoicePlatConfigUpdateDTO) {
        return invoicePlatConfigService.update(invoicePlatConfigUpdateDTO);
    }

    @ApiOperation(value = "删除电子发票服务平台配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键id", dataType = "array", paramType = "query"),
    })
    @DeleteMapping("/delete")
    public R<Boolean> deleteInvoicePlatConfig(@RequestParam("ids") List<Long> ids) {
        return invoicePlatConfigBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询电子发票服务平台配置")
    @PostMapping("/page")
    public R<Page<InvoicePlatConfig>> pageInvoicePlatConfig(@RequestBody PageParams<InvoicePlatConfigPageDTO> params) {
        return invoicePlatConfigBizApi.page(params);
    }

    @ApiOperation(value = "保存配置信息(同时上传文件)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platCode", required = true, value = "平台编码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "platName", required = true, value = "平台名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "openId", required = true, value = "应用标识；发票平台授权的应用唯一标识", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appSecret", required = true, value = "应用密码：发票平台授权的密码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contacts", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "请求地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "签名证书密钥库文件", dataType = "MultipartFile", allowMultiple = true),
            @ApiImplicitParam(name = "keyStoreAlias", value = "证书别名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "keyStorePwd", value = "证书密码", dataType = "string", paramType = "query"),
    })
    @PostMapping("/saveWithFile")
    public R<InvoicePlatConfig> saveWithFile(
            @RequestParam(value = "platCode") String platCode,
            @RequestParam(value = "platName") String platName,
            @RequestParam(value = "openId") String openId,
            @RequestParam(value = "appSecret") String appSecret,
            @RequestParam(value = "contacts", required = false) String contacts,
            @RequestParam(value = "telephone", required = false) String telephone,
            @RequestParam(value = "url", required = false) String url,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "keyStoreAlias", required = false) String keyStoreAlias,
            @RequestParam(value = "keyStorePwd", required = false) String keyStorePwd) {

        InvoicePlatConfigSaveDTO saveDTO = InvoicePlatConfigSaveDTO.builder()
                .platCode(platCode)
                .platName(platName)
                .appSecret(appSecret)
                .openId(openId)
                .contacts(contacts)
                .telephone(telephone)
                .url(url)
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .build();
        return invoicePlatConfigService.saveWithFile(saveDTO, file, keyStoreAlias, keyStorePwd);
    }

    @ApiOperation(value = "更新配置信息(同时上传文件)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "platCode", required = true, value = "平台编码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "platName", required = true, value = "平台名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "openId", required = true, value = "应用标识；发票平台授权的应用唯一标识", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appSecret", required = true, value = "应用密码：发票平台授权的密码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contacts", value = "联系人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "请求地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "签名证书密钥库文件", dataType = "MultipartFile", allowMultiple = true),
            @ApiImplicitParam(name = "keyStoreAlias", value = "证书别名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "keyStorePwd", value = "证书密码", dataType = "string", paramType = "query"),
    })
    @PostMapping("/updateWithFile")
    public R<InvoicePlatConfig> updateWithFile(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "platCode") String platCode,
            @RequestParam(value = "platName") String platName,
            @RequestParam(value = "openId", required = false) String openId,
            @RequestParam(value = "appSecret", required = false) String appSecret,
            @RequestParam(value = "contacts", required = false) String contacts,
            @RequestParam(value = "telephone", required = false) String telephone,
            @RequestParam(value = "url", required = false) String url,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "keyStoreAlias", required = false) String keyStoreAlias,
            @RequestParam(value = "keyStorePwd", required = false) String keyStorePwd) {

        InvoicePlatConfigUpdateDTO updateDTO = InvoicePlatConfigUpdateDTO.builder()
                .id(id)
                .platCode(platCode)
                .platName(platName)
                .appSecret(appSecret)
                .openId(openId)
                .contacts(contacts)
                .telephone(telephone)
                .url(url)
                .build();
        return invoicePlatConfigService.updateWithFile(updateDTO, file, keyStoreAlias, keyStorePwd);
    }

}
