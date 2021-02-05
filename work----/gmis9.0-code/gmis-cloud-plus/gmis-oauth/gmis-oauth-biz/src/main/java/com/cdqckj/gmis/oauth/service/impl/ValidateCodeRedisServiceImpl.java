package com.cdqckj.gmis.oauth.service.impl;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.machine.EnvProperty;
import com.cdqckj.gmis.common.key.RedisOauthKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.oauth.service.ValidateCodeService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.cdqckj.gmis.exception.code.ExceptionCode.CAPTCHA_ERROR;

/**
 * @author: lijianguo
 * @time: 2020/11/27 10:04
 * @remark: 请添加类说明
 */
@Log4j2
@Service
@Primary
public class ValidateCodeRedisServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    EnvProperty envProperty;

    @Override
    public void create(String key, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(key)) {
            throw BizException.validFail("验证码key不能为空","Verification code key cannot be empty");
        }
        setHeader(response, "arithmetic");

        Captcha captcha = createCaptcha("arithmetic");
        redisTemplate.opsForValue().set(createCodeKey(key), StringUtils.lowerCase(captcha.text()), 10, TimeUnit.MINUTES);
        captcha.out(response.getOutputStream());
    }

    @Override
    public R<Boolean> check(String key, String value) {
        if (StringUtils.isBlank(value)) {
            return R.fail(CAPTCHA_ERROR.build("请输入验证码","Please enter the verification code"));
        }
        Object cacheValue = redisTemplate.opsForValue().get(createCodeKey(key));
        // 给测试环境添加一个通用的验证码
        if (!envProperty.proEnv() && "9527".equalsIgnoreCase(value)){
            return R.success(true);
        }
        if (cacheValue == null) {
            return R.fail(CAPTCHA_ERROR.build("验证码已过期","Verification code expired"));
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(cacheValue))) {
            return R.fail(CAPTCHA_ERROR.build("验证码不正确","Verification code expired"));
        }
        redisTemplate.delete(createCodeKey(key));
        return R.success(true);
    }

    private Captcha createCaptcha(String type) {
        Captcha captcha;
        if (StringUtils.equalsIgnoreCase(type, "gif")) {
            captcha = new GifCaptcha(115, 42, 4);
        } else if (StringUtils.equalsIgnoreCase(type, "png")) {
            captcha = new SpecCaptcha(115, 42, 4);
        } else if (StringUtils.equalsIgnoreCase(type, "chinese")) {
            captcha = new ChineseCaptcha(115, 42);
        } else  /*if (StringUtils.equalsIgnoreCase(type, "arithmetic")) */ {
            captcha = new ArithmeticCaptcha(115, 42);
        }
        captcha.setCharType(2);
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, "gif")) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/11/27 10:12
    * @remark 获取key
    */
    private String createCodeKey(String key){
        String codeKey = CacheKeyUtil.createNoTenantKey(RedisOauthKey.AUTH_VALID_CODE_KEY, key);
        return codeKey;
    }
}
