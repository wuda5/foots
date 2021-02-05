package com.cdqckj.gmis.apiGateway.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.properties.IgnoreTokenProperties;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.jwt.TokenUtil;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.utils.StrPool;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
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

import static com.cdqckj.gmis.context.BaseContextConstants.BEARER_HEADER_KEY;
import static com.cdqckj.gmis.context.BaseContextConstants.JWT_KEY_TENANT;

/**
 * app网关 认证 过滤器
 * @author hc
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
    private RedisService redisService;

    protected boolean isDev(String token) {
        return !StrPool.PROD.equalsIgnoreCase(profiles) && StrPool.TEST.equalsIgnoreCase(token);
    }

    @Override
    public int getOrder() {
        return -1001;
    }

    /**
     * 忽略应用级token
     *
     * @return
     */
    protected boolean isIgnoreToken(String path) {
        return ignoreTokenProperties.isAppIgnoreToken(path);
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

        // 忽略 token 认证相关接口
        if (isIgnoreToken(request.getPath().toString())) {
            //token 认证相关接口 掉用日志记录 TODO
            return chain.filter(exchange);
        }
        String tenant = null;
        int langType = 1;
        try {
            //1, 解码 请求头中的租户信息
            String base64Tenant = getHeader(JWT_KEY_TENANT, request);
            if (StrUtil.isNotEmpty(base64Tenant)) {
                tenant = JwtUtil.base64Decoder(base64Tenant);
                addHeader(mutate, JWT_KEY_TENANT, tenant);
                MDC.put(JWT_KEY_TENANT, BaseContextHandler.getTenant());
            }else{
                return errorResponse(response, "header参数缺失", R.FAIL_CODE, 200);
            }

            // 2,解码 token 后面完善
            String token = getHeader(BEARER_HEADER_KEY, request);
            //去除token前缀
            String newToken = JwtUtil.getToken(token);
            //解析token
            Claims claims = JwtUtil.parseJWT(newToken);
            String appId = Convert.toStr(claims.get(BaseContextConstants.APP_ID));
//          String tenantId = Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_TENANT_ID));

            addHeader(mutate, BaseContextConstants.LANG_TYPE, langType);
            addHeader(mutate, BaseContextConstants.APP_ID,appId);
            addHeader(mutate, BaseContextConstants.JWT_KEY_USER_ID,4);

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
