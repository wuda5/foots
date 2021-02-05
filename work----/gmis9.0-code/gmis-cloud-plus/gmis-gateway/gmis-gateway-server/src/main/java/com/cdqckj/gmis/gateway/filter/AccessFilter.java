package com.cdqckj.gmis.gateway.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.cdqckj.gmis.authority.api.TenantApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.BizConstant;
import com.cdqckj.gmis.common.constant.CacheKey;
import com.cdqckj.gmis.common.properties.IgnoreTokenProperties;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.jwt.TokenUtil;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.cdqckj.gmis.context.BaseContextConstants.*;
import static com.cdqckj.gmis.exception.code.ExceptionCode.JWT_OFFLINE;

/**
 * 过滤器
 *
 * @author gmis
 * @date 2019/07/31
 */
@Component
@Slf4j
@EnableConfigurationProperties({IgnoreTokenProperties.class})
public class AccessFilter implements GlobalFilter, Ordered {
    @Value("${spring.profiles.active:dev}")
    protected String profiles;
    @Value("${gmis.database.multiTenantType:SCHEMA}")
    protected String multiTenantType;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private IgnoreTokenProperties ignoreTokenProperties;
    @Autowired
    private CacheChannel channel;
    @Autowired
    private TenantApi tenantApi;
    @Autowired
    private RedisService redisService;

    protected boolean isDev(String token) {
        return !StrPool.PROD.equalsIgnoreCase(profiles) && StrPool.TEST.equalsIgnoreCase(token);
    }

    @Override
    public int getOrder() {
        return -1000;
    }

    /**
     * 忽略应用级token
     *
     * @return
     */
    protected boolean isIgnoreToken(String path) {
        return ignoreTokenProperties.isIgnoreToken(path);
    }

    protected String getHeader(String headerName, ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String token = StrUtil.EMPTY;
        if (headers == null || headers.isEmpty()) {
            return token;
        }

        token = headers.getFirst(headerName);

        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        return request.getQueryParams().getFirst(headerName);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder mutate = request.mutate();

        BaseContextHandler.setGrayVersion(getHeader(BaseContextConstants.GRAY_VERSION, request));
        AuthInfo authInfo = null;
        String tenant = null;
        int langType = 1;
        try {
            //1, 解码 请求头中的租户信息
            if (!"NONE".equals(multiTenantType)) {
                String base64Tenant = getHeader(JWT_KEY_TENANT, request);
                if (StrUtil.isNotEmpty(base64Tenant)) {
                    tenant = JwtUtil.base64Decoder(base64Tenant);
                    //通过访问URL识别租户
                    if(tenant.indexOf(DOMAIN_COM) != -1 ||
                            tenant.indexOf(DOMAIN_CN) != -1 ||
                            tenant.indexOf(DOMAIN_NET) != -1 ||
                            tenant.indexOf(DOMAIN_ORG) != -1){
                        tenant = tenantApi.findCodeByUrl(tenant);
                    }
                    Object langObj= redisService.get(CacheKey.LANG+tenant);
                    if(langObj!=null){
                        langType=(int)langObj;
                    }
//                    if(!L18nMenuContainer.LANG_MAP.containsKey(CacheKey.LANG+tenant)
//                            && langType != -1){
//                        L18nMenuContainer.LANG_MAP.put(CacheKey.LANG+tenant,langType);
//                    }
                    BaseContextHandler.setTenant(tenant);
                    BaseContextHandler.setLang(langType);
                    addHeader(mutate, JWT_KEY_TENANT, BaseContextHandler.getTenant());
                    MDC.put(JWT_KEY_TENANT, BaseContextHandler.getTenant());
                }
            }

            // 2,解码 Authorization 后面完善
            String base64Authorization = getHeader(BASIC_HEADER_KEY, request);
            if (StrUtil.isNotEmpty(base64Authorization)) {
                String[] client = JwtUtil.getClient(base64Authorization);
                BaseContextHandler.setClientId(client[0]);
                addHeader(mutate, JWT_KEY_CLIENT_ID, BaseContextHandler.getClientId());
            }

            // 忽略 token 认证的接口
            if (isIgnoreToken(request.getPath().toString())) {
                log.debug("access filter not execute");
                return chain.filter(exchange);
            }
            //获取token， 解析，然后想信息放入 heade
            //3, 获取token
            String token = getHeader(BEARER_HEADER_KEY, request);
            // 测试环境 token=test 时，写死一个用户信息，便于测试
            if (isDev(token)) {
                authInfo = new AuthInfo().setAccount("gmis").setUserId(1L)
                        .setTokenType(BEARER_HEADER_KEY).setName("平台管理员").setTenantId("1")
                        .setTenantName("秦川物联网").setOrgId(1L).setOrgName("技术中心");
            }
            // 4,解析 并 验证 token
            if (authInfo == null) {
                authInfo = tokenUtil.getAuthInfo(token);// 完成解析认证
            }
            if (!isDev(token)) {
                // 5，验证 是否在其他设备登录或被挤下线
                String newToken = JwtUtil.getToken(token);
                String tokenKey = CacheKey.buildKey(newToken);
                CacheObject tokenCache = channel.get(CacheKey.TOKEN_USER_ID, tokenKey);
                if (tokenCache.getValue() == null || tokenCache.getValue().equals("forbidden")) {
                    // 为空就认为是没登录或者被T会有bug，该 bug 取决于登录成功后，异步调用UserTokenService.save 方法的延迟
                    //return errorResponse(response, "forbidden", R.FAIL_CODE, 200);
                } else if (StrUtil.equals(BizConstant.LOGIN_STATUS, (String) tokenCache.getValue())) {
                    return errorResponse(response, JWT_OFFLINE.getMsg(), JWT_OFFLINE.getCode(), 200);
                }
            }
        } catch (BizException e) {
            return errorResponse(response, e.getMessage(), e.getCode(), 200);
        } catch (Exception e) {
            if(langType == 2){
                return errorResponse(response, "Error verifying token", R.FAIL_CODE, 200);
            }
            e.printStackTrace();
            log.error("验证token msg {}" , e.getMessage());
            return errorResponse(response, "验证token出错", R.FAIL_CODE, 200);
        }
        addHeader(mutate, BaseContextConstants.LANG_TYPE, langType);
        //6, 转换，将 token 解析出来的用户身份 和 解码后的tenant、Authorization 重新封装到请求头
        if (authInfo != null) {
            addHeader(mutate, BaseContextConstants.JWT_KEY_ACCOUNT, authInfo.getAccount());
            addHeader(mutate, BaseContextConstants.JWT_KEY_USER_ID, authInfo.getUserId());
            addHeader(mutate, BaseContextConstants.JWT_KEY_NAME, authInfo.getName());
//            log.info("登录token信息:{}",authInfo.toString());
            addHeader(mutate,BaseContextConstants.JWT_KEY_TENANT_ID,authInfo.getTenantId());
            addHeader(mutate,BaseContextConstants.JWT_KEY_TENANT_NAME,authInfo.getTenantName());
            addHeader(mutate,BaseContextConstants.JWT_KEY_ORG_ID,authInfo.getOrgId());
            addHeader(mutate,BaseContextConstants.JWT_KEY_ORG_NAME,authInfo.getOrgName());

            MDC.put(BaseContextConstants.JWT_KEY_USER_ID, String.valueOf(authInfo.getUserId()));
        }

        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (ObjectUtil.isEmpty(value)) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = URLUtil.encode(valueStr);
        mutate.header(name, valueEncode);
    }

    protected Mono<Void> errorResponse(ServerHttpResponse response, String errMsg, int errCode, int httpStatusCode) {
        R tokenError = R.fail(errCode, errMsg);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(tokenError.toString().getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

}
