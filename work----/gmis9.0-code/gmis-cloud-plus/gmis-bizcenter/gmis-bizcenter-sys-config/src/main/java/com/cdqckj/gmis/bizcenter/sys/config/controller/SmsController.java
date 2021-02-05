package com.cdqckj.gmis.bizcenter.sys.config.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.sys.config.service.SmsService;
import com.cdqckj.gmis.msgs.api.SmsApi;
import com.cdqckj.gmis.sms.dto.*;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 短信配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/msgs/sms")
@Api(value = "sms", tags = "短信")
//@PreAuth(replace = "sms:")
public class SmsController {

    @Autowired
    public SmsApi smsApi;
    @Autowired
    public SmsService smsService;

    /**
     * 添加短信签名
     * @param
     * @return
     */
    @ApiOperation(value = "添加短信签名", notes = "添加短信签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
            @ApiImplicitParam(name = "signName", value = "签名内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "signType", value = "签名类型 0：公司", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "usedMethod", value = "签名用途： 0：自用。 1：他用。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "documentType", value = "证明类型：0：三证合一。 1：企业营业执照。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "international", value = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信", dataType = "long", paramType = "query"),
    })
    @PostMapping("/sign/save")
    public R<Boolean> saveSign(@RequestPart(value = "file") MultipartFile file,
                               @RequestParam(value = "signName", required = true) String signName,
                               @RequestParam(value = "signType", required = true) Integer signType,
                               @RequestParam(value = "usedMethod", required = true) Integer usedMethod,
                               @RequestParam(value = "documentType", required = true) Integer documentType,
                               @RequestParam(value = "international", required = true) Integer international) throws IOException {
        return smsApi.saveSign(file, signName, signType, usedMethod, documentType, international);
    }

    /**
     * 修改短信签名
     * @param file
     * @param id
     * @param signName
     * @param signType
     * @param usedMethod
     * @param documentType
     * @param international
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "修改短信签名", notes = "修改短信签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
            @ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "signName", value = "签名内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "signType", value = "签名类型 0：公司", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "usedMethod", value = "签名用途： 0：自用。 1：他用。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "documentType", value = "证明类型：0：三证合一。 1：企业营业执照。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "international", value = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信", dataType = "long", paramType = "query"),
    })
    @PostMapping("/sign/update")
    public R<Boolean> updateSign(@RequestPart(value = "file") MultipartFile file,
                                 @RequestParam(value = "id", required = true) long id,
                                 @RequestParam(value = "signName", required = true) String signName,
                                 @RequestParam(value = "signType", required = true) Integer signType,
                                 @RequestParam(value = "usedMethod", required = true) Integer usedMethod,
                                 @RequestParam(value = "documentType", required = true) Integer documentType,
                                 @RequestParam(value = "international", required = true) Integer international) throws IOException {

        return smsApi.updateSign(file,id, signName, signType, usedMethod, documentType, international);
    }

    /**
     * 删除短信签名
     * @param ids
     * @return
     */
    @PostMapping("/sign/delete")
    @ApiOperation("删除短信签名")
    public R<Boolean> deleteSign(@RequestBody List<Long> ids) {
        return smsApi.deleteSign(ids);
    }

    /**
     * 查询短信签名
     * @return
     */
    @PostMapping("/sign/page")
    @ApiOperation("查询短信签名")
    public R<Page<Sign>> signPage(@RequestBody @Validated PageParams<SignPageDTO> params){
        return smsApi.signPage(params);
    }

    /**
     * 批量初始化短信模板
     * @return
     */
    @PostMapping("/sign/initSmsTemplate")
    @ApiOperation("批量初始化短信模板")
    public R<Boolean> initSmsTemplate(){
        return smsApi.initSmsTemplate();
    }

    /**
     * 新增短信模板
     * @param data
     * @return
     */
    @PostMapping("/template/save")
    @ApiOperation("新增短信模板")
    public R<SmsTemplate> saveTemplate(@RequestBody SmsTemplateSaveDTO data) {
        R<Sign> result = smsApi.getDefaultSign();
        Sign sign = result.getData();
        if(null!=sign){
            data.setSignName(sign.getSignName());
            return smsApi.saveTemplate(data);
        }
        return R.fail(result.getMsg());
    }

    /**
     * 修改短信模板
     * @param model
     * @return
     */
    @PutMapping("/template/update")
    @ApiOperation("修改短信模板")
    public R<SmsTemplate> updateTemplate(@RequestBody SmsTemplateUpdateDTO model) {
        return smsApi.updateTemplate(model);
    }

    /**
     * 短信模板开启/关闭
     * @param model
     * @return
     */
    @Deprecated
    @PostMapping("/template/updateStatus")
    @ApiOperation("短信模板开启/关闭")
    public R<Boolean> updateStatus(@RequestBody SmsTemplateUpdateDTO model) {
        return smsApi.updateStatus(model);
    }

    /**
     * 设置默认短信模板
     * @param model
     * @return
     */
    @PostMapping("/template/setDefaultSms")
    @ApiOperation("设置默认短信模板")
    public R<Boolean> setDefaultSms(@RequestBody SmsTemplate model) {
        return smsService.setDefaultSms(model);
    }

    /**
     * 删除短信模板
     * @param ids
     * @return
     */
    @PostMapping("/template/delete")
    @ApiOperation("删除短信模板")
    public R<Boolean> deleteTemplate(@RequestBody List<Long> ids) {
        return smsApi.deleteTemplate(ids);
    }

    /**
     * 查询短信模板
     * @return
     */
    @PostMapping("/template/page")
    @ApiOperation("查询短信模板")
    public R<Page<SmsTemplate>> templatePage(@RequestBody @Validated PageParams<SmsTemplatePageDTO> params){
        return smsApi.templatePage(params);
    }

    /**
     * 短信模板测试发送
     * @return
     */
    @PostMapping("/smsTask/testSend")
    @ApiOperation("短信测试发送")
    public R<SmsTask> testSend(@RequestBody SmsSendTaskDTO smsTaskDTO){
        return smsApi.send(smsTaskDTO);
    }

    @ApiOperation(value = "添加签名（base64）", notes = "添加签名（base64）")
    @PostMapping("/sign/saveSignFileStr")
    public R<Boolean> saveSignFileStr(@RequestBody SignSaveDTO signSaveDTO) {
        return smsApi.saveSignFileStr(signSaveDTO);
    }


    @ApiOperation(value = "修改签名（base64）", notes = "修改签名（base64）")
    @PutMapping("/sign/updateSignFileStr")
    public R<Boolean> updateSignFileStr(@RequestBody SignUpdateDTO signUpdateDTO) {
        return smsApi.updateSignFileStr(signUpdateDTO);
    }

}
