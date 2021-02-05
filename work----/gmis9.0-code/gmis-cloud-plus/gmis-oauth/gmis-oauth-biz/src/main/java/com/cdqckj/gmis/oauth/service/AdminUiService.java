package com.cdqckj.gmis.oauth.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.appmanager.AppmanagerApi;
import com.cdqckj.gmis.appmanager.dto.AppmanagerPageDTO;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.authority.dto.auth.AuthorityBuildDTO;
import com.cdqckj.gmis.authority.vo.auth.AuthorityBuildVO;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.boot.utils.WebUtils;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.BizConstant;
import com.cdqckj.gmis.constants.AppConfConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import com.cdqckj.gmis.jwt.TokenUtil;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.jwt.model.JwtUserInfo;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.tenant.entity.GlobalUser;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.tenant.service.GlobalUserService;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.I18nUtil;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

import static com.cdqckj.gmis.context.BaseContextConstants.BASIC_HEADER_KEY;
import static com.cdqckj.gmis.utils.BizAssert.gt;
import static com.cdqckj.gmis.utils.BizAssert.notNull;

/**
 * @author gmis
 * @createTime 2017-12-15 13:42
 */
@Service
@Slf4j
public class AdminUiService {

    @Autowired
    protected TokenUtil tokenUtil;
    @Autowired
    private GlobalUserService globalUserService;
    @Autowired
    AppmanagerApi appmanagerApi;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TenantService tenantService;

    @Autowired
    private I18nUtil i18nUtil;

    /**临时键超时时间 单位 s **/
    private final Long OVER_TIME_CODE = 1800L;

    /**
     * 超管账号登录
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    public R<AuthInfo> adminLogin(String account, String password) {
        String basicHeader = ServletUtil.getHeader(WebUtils.request(), BASIC_HEADER_KEY, StrPool.UTF_8);
        String[] client = JwtUtil.getClient(basicHeader);

        GlobalUser user = this.globalUserService.getOne(Wrappers.<GlobalUser>lambdaQuery()
                .eq(GlobalUser::getAccount, account).eq(GlobalUser::getTenantCode, BizConstant.SUPER_TENANT));
        // 密码错误
        if (user == null) {
            throw new BizException(ExceptionCode.JWT_USER_INVALID.getCode(), ExceptionCode.JWT_USER_INVALID.getMsg(), ExceptionCode.JWT_USER_INVALID.getMsg());
        }

        String passwordMd5 = SecureUtil.md5(password);
        if (!user.getPassword().equalsIgnoreCase(passwordMd5)) {
            return R.fail("用户名或密码错误!","Wrong user name or password!");
        }


        JwtUserInfo userInfo = new JwtUserInfo(user.getId(), user.getAccount(), user.getName(),null,null,null,null,null,null);

        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo, null);
        log.info("token={}", authInfo.getToken());
        return R.success(authInfo);
    }


    /**
     * 生成认证临时票据
     * @auther hc
     * @date 2020/09/22
     * @param buildDTO
     * @return
     */
    public R<AuthorityBuildVO> buidAuthorization(AuthorityBuildDTO buildDTO) {
        AuthorityBuildVO vo = new AuthorityBuildVO();

        //认证app_id是否在系统中
        Appmanager queryData = new Appmanager();
        queryData.setAppId(buildDTO.getAppId());
        queryData.setStatus(true);

        R<List<Appmanager>> listR = appmanagerApi.query(queryData);
        if(listR.getIsSuccess() && CollectionUtils.isNotEmpty(listR.getData())){
            Appmanager appmanager = listR.getData().get(0);

            //租户code
            String tenantCode;
            //验证是否是单租户应用绑定
            if(!appmanager.getTenantCode().equals(AppConfConstants.TENANT_DEFAULT_CODE)){
                tenantCode = appmanager.getTenantCode();
            }else{
                tenantCode = JwtUtil.base64Decoder(buildDTO.getTenant());
            }

            // 1，检测租户是否可用
            Tenant tenant = this.tenantService.getByCode(tenantCode);
            notNull(tenant, i18nUtil.getMessage(MessageConstants.SYS_VALID_TENANT_USE,"租户不可用"));
            BizAssert.equals(TenantStatusEnum.NORMAL,tenant.getStatus(),
                    i18nUtil.getMessage(MessageConstants.SYS_VALID_TENANT_USE,"租户不可用"));
            if (tenant.getExpirationTime() != null) {
                gt(LocalDateTime.now(), tenant.getExpirationTime(), i18nUtil.getMessage(MessageConstants.SYS_VALID_TENANT_USE,"租户不可用"));
            }

            //2、app应用校验
            if(StringUtils.isEmpty(appmanager.getAppSecret())){
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_NOT,"当前应用不可用,未授权"));
            }

            appmanager.setTenantCode(tenant.getCode());
            appmanager.setTenantName(tenant.getName());
            appmanager.setTenantId(tenant.getId());

            // 2、校验app_id时效性
            LocalDateTime nowTime = LocalDateTime.now();
            //应用有效开始时间 默认为分配secret时间为开始时间
            LocalDateTime validTimeStart = appmanager.getValidTimeStart();
            //应用有效结束时间 不设置为永久
            LocalDateTime validTimeEnd = appmanager.getValidTimeEnd();
            if(validTimeStart ==null || nowTime.isBefore(validTimeStart)){
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_EFFECTIVE,"该应用尚未生效"));
            }else if(validTimeEnd!=null && nowTime.isAfter(validTimeEnd)){
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_FAILURE,"该应用已失效"));
            }

            //生成认证code
            String code =  buidCode(buildDTO,tenantCode);
            BeanUtils.copyProperties(buildDTO,vo);
            vo.setCode(code);
            //转成JSON对象
            String redis_value = JSONObject.toJSONString(appmanager);
            //存入redis
            if(!redisService.set(code,redis_value,OVER_TIME_CODE)){
                return R.fail("临时授权key,存入缓存失败");
            }
        }else if(listR.getIsError()){
            return R.fail(listR.getDebugMsg());
        }else{
            return R.fail("当前应用在系统中不识别");
        }

        return R.success(vo);
    }

    /**
     * 生成临时认证code
     * @auther hc
     * @param buildDTO
     * @param tenantCode
     * @return
     */
    private String buidCode(AuthorityBuildDTO buildDTO,String tenantCode){
        StringBuilder builder = new StringBuilder();
        builder.append(tenantCode+":");
        builder.append(buildDTO.getAppId()+":");
        builder.append(LocalDateTime.now().toInstant(ZoneOffset.of("+8")));

        return Base64.getEncoder().encodeToString(builder.toString().getBytes());
    }
}
