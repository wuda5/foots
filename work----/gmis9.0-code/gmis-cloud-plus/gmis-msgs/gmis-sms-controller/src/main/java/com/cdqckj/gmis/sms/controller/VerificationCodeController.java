package com.cdqckj.gmis.sms.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.common.constant.CacheKey;
import com.cdqckj.gmis.sms.service.SmsTaskService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.cache.repository.CacheRepository;
import com.cdqckj.gmis.sms.dto.VerificationCodeDTO;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.enumeration.SourceType;
import com.cdqckj.gmis.sms.enumeration.TemplateCodeType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用验证码
 *
 * @author gmis
 * @date 2019/08/06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/verification")
@Api(value = "VerificationCode", tags = "验证码")
public class VerificationCodeController {

    @Autowired
    private CacheRepository cacheRepository;
    @Autowired
    private SmsTaskService smsTaskService;

    /**
     * 通用的发送验证码功能
     * <p>
     * 一个系统可能有很多地方需要发送验证码（注册、找回密码等），每增加一个场景，VerificationCodeType 就需要增加一个枚举值
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @PostMapping(value = "/send")
    public R<Boolean> send(@Validated @RequestBody VerificationCodeDTO data) {
        String code = RandomUtil.randomNumbers(6);

        SmsTask smsTask = SmsTask.builder().build();
        smsTask.setSourceType(SourceType.SERVICE);
        JSONObject param = new JSONObject();
        param.put("1", code);
        smsTask.setTemplateParams(param.toString());
        smsTask.setReceiver(data.getMobile());
        smsTaskService.saveTask(smsTask, TemplateCodeType.gmis_COMMON);

        String key = CacheKey.buildTenantKey(CacheKey.REGISTER_USER, data.getType().name(), data.getMobile());
        cacheRepository.setExpire(key, code, CacheRepository.DEF_TIMEOUT_5M);
        return R.success();
    }

    /**
     * 对某种类型的验证码进行校验
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "验证验证码", notes = "验证验证码")
    @PostMapping
    public R<Boolean> verification(@Validated(SuperEntity.Update.class) @RequestBody VerificationCodeDTO data) {
        String key = CacheKey.buildTenantKey(CacheKey.REGISTER_USER, data.getType().name(), data.getMobile());
        String code = cacheRepository.get(key);
        return R.success(data.getCode().equals(code));
    }
}
