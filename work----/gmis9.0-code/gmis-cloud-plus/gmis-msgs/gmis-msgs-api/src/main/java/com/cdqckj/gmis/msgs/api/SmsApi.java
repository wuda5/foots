package com.cdqckj.gmis.msgs.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.msgs.api.fallback.SmsApiFallback;
import com.cdqckj.gmis.sms.dto.*;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件接口
 *
 * @author gmis
 * @date 2019/06/21
 */
@FeignClient(name = "${gmis.feign.msgs-server:gmis-msgs-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class)
public interface SmsApi {
    /**
     * 短信发送
     *
     * @param smsTaskDTO
     * @return
     */
    @RequestMapping(value = "/smsTask/send", method = RequestMethod.POST)
    R<SmsTask> send(@RequestBody SmsSendTaskDTO smsTaskDTO);

    /**
     * 发送验证码
     *
     * @param data
     * @return
     */
    @PostMapping(value = "/verification/send")
    R<Boolean> sendCode(@Validated @RequestBody VerificationCodeDTO data);

    /**
     * 验证
     *
     * @param data
     * @return
     */
    @PostMapping("/verification")
    R<Boolean> verification(@Validated(SuperEntity.Update.class) @RequestBody VerificationCodeDTO data);

    /**
     * 新增短信签名
     */
    @PostMapping(value = "/sign/saveSign", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<Boolean> saveSign(@RequestPart(value = "file") MultipartFile file,
                               @RequestParam(value = "signName", required = true) String signName,
                               @RequestParam(value = "signType", required = true) Integer signType,
                               @RequestParam(value = "usedMethod", required = true) Integer usedMethod,
                               @RequestParam(value = "documentType", required = true) Integer documentType,
                               @RequestParam(value = "international", required = true) Integer international);


    /**
     * 修改短信签名
     */
    @PostMapping(value = "/sign/updateSign", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<Boolean> updateSign(@RequestPart(value = "file") MultipartFile file,
                                 @RequestParam(value = "id", required = true) long id,
                                 @RequestParam(value = "signName", required = true) String signName,
                                 @RequestParam(value = "signType", required = true) Integer signType,
                                 @RequestParam(value = "usedMethod", required = true) Integer usedMethod,
                                 @RequestParam(value = "documentType", required = true) Integer documentType,
                                 @RequestParam(value = "international", required = true) Integer international);


    /**
     * 查询短信签名
     * @return
     */
    @PostMapping("/sign/signPage")
    R<Page<Sign>> signPage(@RequestBody @Validated PageParams<SignPageDTO> params);

    /**
     * 批量初始化短信模板
     * @return
     */
    @PostMapping("/smsTemplate/initSmsTemplate")
    R<Boolean> initSmsTemplate();

    /**
     * 删除短信签名
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/sign/logicalDelete")
    R<Boolean> deleteSign(@RequestParam("ids[]") List<Long> ids);

    /**
     * 获取一个审核通过且未删除的签名
     * @return
     */
    @PostMapping("/sign/getDefaultSign")
    R<Sign> getDefaultSign();

    @PostMapping("/smsTemplate/getAdminSmsTemplate")
    R<List<SmsTemplate>> getAdminSmsTemplate();

    /**
     * 新增短信模板
     * @param data
     * @return
     */
    @PostMapping("/smsTemplate/saveTemplate")
    R<SmsTemplate> saveTemplate(SmsTemplateSaveDTO data);

    /**
     * 修改短信模板
     * @param model
     * @return
     */
    @PutMapping("/smsTemplate/updateTemplate")
    R<SmsTemplate> updateTemplate(SmsTemplateUpdateDTO model);

    /**
     * 短信模板开启/关闭
     * @param data
     * @return
     */
    @PostMapping("/smsTemplate/updateStatus")
    R<Boolean> updateStatus(SmsTemplateUpdateDTO data);

    /**
     * 删除模板
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/smsTemplate/logicalDelete")
    R<Boolean> deleteTemplate(@RequestParam("ids[]") List<Long> ids);

    /**
     * 查询短信模板
     * @return
     */
    @PostMapping("/smsTemplate/templatePage")
    public R<Page<SmsTemplate>> templatePage(@RequestBody @Validated PageParams<SmsTemplatePageDTO> params);

    /**
     * 根据id查询短信模板
     * @return
     */
    @RequestMapping(value ="/smsTemplate/getById", method = RequestMethod.POST)
    R<SmsTemplate> getById(@RequestParam(value = "id") Long id);

    /**
     * 条件查询所有模板
     * @param params
     * @return
     */
    @PostMapping(value = "/smsTemplate/query")
    R<List<SmsTemplate>> query(@RequestBody SmsTemplate params);

    /**
     * 批量逻辑删除
     * @param list
     * @return
     */
    @RequestMapping(value = "/smsTemplate/updateBatch" ,method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<SmsTemplateUpdateDTO> list);

    /**
     * 保存
     * @param signSaveDTO
     * @return
     */
    @PostMapping("/sign/saveSignFileStr")
    R<Boolean> saveSignFileStr(@RequestBody SignSaveDTO signSaveDTO);

    /**
     * 修改
     * @param signUpdateDTO
     * @return
     */
    @PutMapping("/sign/updateSignFileStr")
    R<Boolean> updateSignFileStr(@RequestBody SignUpdateDTO signUpdateDTO);
}
