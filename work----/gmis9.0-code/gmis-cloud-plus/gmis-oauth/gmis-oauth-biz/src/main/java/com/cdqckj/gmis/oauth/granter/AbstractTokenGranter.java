package com.cdqckj.gmis.oauth.granter;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.authority.entity.auth.Application;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.entity.auth.UserToken;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.event.LoginEvent;
import com.cdqckj.gmis.authority.event.model.LoginStatusDTO;
import com.cdqckj.gmis.authority.service.auth.ApplicationService;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.boot.utils.WebUtils;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import com.cdqckj.gmis.injection.core.InjectionCore;
import com.cdqckj.gmis.jwt.TokenUtil;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.jwt.model.JwtUserInfo;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.oauth.utils.TimeUtils;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.utils.*;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.cdqckj.gmis.context.BaseContextConstants.BASIC_HEADER_KEY;
import static com.cdqckj.gmis.utils.BizAssert.gt;
import static com.cdqckj.gmis.utils.BizAssert.notNull;

/**
 * 验证码TokenGranter
 *
 * @author Chill
 */
@Slf4j
public abstract class AbstractTokenGranter implements TokenGranter {
    @Autowired
    protected TokenUtil tokenUtil;
    @Autowired
    protected UserService userService;
    @Autowired
    protected TenantService tenantService;
    @Autowired
    protected CacheChannel cacheChannel;
    @Autowired
    protected ApplicationService applicationService;
    @Autowired
    protected RedisService redisService;
    @Autowired
    private InjectionCore injectionCore;

    /**
     * 处理登录逻辑
     *
     * @param loginParam 登录参数
     * @return 认证信息
     */
    protected R<AuthInfo> login(LoginParamDTO loginParam) {
        if (StrHelper.isAnyBlank(loginParam.getAccount(), loginParam.getPassword())) {
            return R.fail(redisService.getLangMessage(MessageConstants.USER_VERIFY_PASSWORD_INPUT));
        }
        // 1，检测租户是否可用
        Tenant tenant = this.tenantService.getByCode(BaseContextHandler.getTenant());
        if (tenant.getInitStatus() == 1){
            return R.fail("租户数据初始化中,请等待初始化结束");
        }
        if (tenant.getInitStatus() == 3){
            return R.fail("租户数据初始化失败,请联系管理员协助处理");
        }
        notNull(tenant, redisService.getLangMessage(MessageConstants.TENANT_VERIFY_EXIST));
        BizAssert.equals(TenantStatusEnum.NORMAL,tenant.getStatus(),
                redisService.getLangMessage(MessageConstants.TENANT_VERIFY_NOT_ENABLE));
        if (tenant.getExpirationTime() != null) {
            gt(LocalDateTime.now(), tenant.getExpirationTime(), redisService.getLangMessage(MessageConstants.TENANT_VERIFY_EXPIRED));
        }

        BaseContextHandler.setTenant(tenant.getCode());
        // 2.检测client是否可用--类似appid也要xxx--pc端的登录是系统默认 给了合适的 gmis-ui 和 gmis-admin-ui 两个标志（前端通过传入的 头 Authorization 中加密含xx,其实就是对应的appid+app_secret）,检查是查库有正确的xx没有
        R<String[]> checkR = checkClient();
        if (checkR.getIsError()) {
            return R.fail(checkR.getMsg(),checkR.getMsg());
        }

        // 3. 验证登录
        R<User> result = this.getUser(tenant, loginParam.getAccount(), loginParam.getPassword());
        if (result.getIsError()) {
            return R.fail(result.getCode(), result.getMsg());
        }

        // 4.查询用户的权限
        User user = result.getData();

        // 5.生成 token
        AuthInfo authInfo = this.createToken(user);

        UserToken userToken = getUserToken(checkR.getData()[0], authInfo);

        //成功登录事件
        SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.success(user.getId(), userToken)));
        return R.success(authInfo);
    }

    private UserToken getUserToken(String clientId, AuthInfo authInfo) {
        UserToken userToken = new UserToken();
        Map<String, String> fieldMapping = new HashMap<>();
        fieldMapping.put("userId", "createUser");
        BeanPlusUtil.copyProperties(authInfo, userToken, CopyOptions.create().setFieldMapping(fieldMapping));
        userToken.setClientId(clientId);
        userToken.setExpireTime(DateUtils.date2LocalDateTime(authInfo.getExpiration()));
        return userToken;
    }


    /**
     * 检测 client
     *
     * @return
     */
    protected R<String[]> checkClient() {
        String basicHeader = ServletUtil.getHeader(WebUtils.request(), BASIC_HEADER_KEY, StrPool.UTF_8);
        String[] client = JwtUtil.getClient(basicHeader);
        Application application = applicationService.getOne(Wraps.<Application>lbQ().eq(Application::getClientId, client[0])
                .eq(Application::getClientSecret, client[1]));

        if (application == null) {
            return R.fail(redisService.getLangMessage(MessageConstants.LOGIN_VERIFY_CID));
        }
        if (!application.getStatus()) {
            return R.fail(redisService.getLangMessage(MessageConstants.LOGIN_VERIFY_CUS_NOT_ENABLE));
        }
        return R.success(client);
    }


    /**
     * 检测用户密码是否正确
     *
     * @param tenant   租户
     * @param account  账号
     * @param password 密码
     * @return 用户信息
     */
    protected R<User> getUser(Tenant tenant, String account, String password) {
        User user = this.userService.getByAccount(account);
        // 密码错误
        String passwordMd5 = cn.hutool.crypto.SecureUtil.md5(password);
        if (user == null) {
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }

        if (!user.getPassword().equalsIgnoreCase(passwordMd5)) {
            String msg = redisService.getLangMessage(MessageConstants.LOGIN_VERIFY_NAME_PASSWORD);
            // 密码错误事件
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.pwdError(user.getId(), msg)));
            return R.fail(msg);
        }

        // 密码过期
        if (user.getPasswordExpireTime() != null && LocalDateTime.now().isAfter(user.getPasswordExpireTime())) {
            String msg = redisService.getLangMessage(MessageConstants.LOGIN_VERIFY_PASSWORD_EXPIRED);
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg);
        }

        if (!user.getStatus()) {
            String msg = redisService.getLangMessage(MessageConstants.LOGIN_VERIFY_USER_NOT_ENABLE);
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg);
        }

        // 用户锁定
//        Integer maxPasswordErrorNum = Convert.toInt(tenant.getPasswordErrorNum(), 0);
        Integer maxPasswordErrorNum = 0;
        Integer passwordErrorNum = Convert.toInt(user.getPasswordErrorNum(), 0);
        if (maxPasswordErrorNum > 0 && passwordErrorNum > maxPasswordErrorNum) {
            log.info("当前错误次数{}, 最大次数:{}", passwordErrorNum, maxPasswordErrorNum);

            LocalDateTime passwordErrorLockTime = TimeUtils.getPasswordErrorLockTime("0");
            log.info("passwordErrorLockTime={}", passwordErrorLockTime);
            if (passwordErrorLockTime.isAfter(user.getPasswordErrorLastTime())) {
                // 登录失败事件
                String msg = StrUtil.format(redisService.getLangMessage(MessageConstants.LOGIN_VERIFY_USER_LOCK),
                        maxPasswordErrorNum);
                SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
                return R.fail(msg);
            }
        }

        return R.success(user);
    }

    /**
     * 创建用户TOKEN
     *
     * @param user 用户
     * @return token
     */
    protected AuthInfo createToken(User user) {
        log.info("创建token信息时user 为：{}",user.toString());
        Tenant tenant=  tenantService.getOne(new QueryWrap<Tenant>().eq("code",BaseContextHandler.getTenant()));
//        User.builder().orgId(new RemoteData(user.getOrg())).build();
        BaseContextHandler.setUserId(user.getId());
        Long orgId= UserOrgIdUtil.getUserOrgId();
        JwtUserInfo userInfo = new JwtUserInfo(user.getId(), user.getAccount(),user.getName(), tenant.getCode(),tenant.getName(),orgId,"",null,null);
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo, null);
        log.info("创建token信息：{}",authInfo.toString());
        authInfo.setAvatar(user.getAvatar());
        authInfo.setWorkDescribe(user.getWorkDescribe());
        return authInfo;
    }


}
