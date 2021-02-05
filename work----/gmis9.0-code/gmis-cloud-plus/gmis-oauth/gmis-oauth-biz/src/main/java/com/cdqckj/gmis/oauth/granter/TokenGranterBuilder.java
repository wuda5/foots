package com.cdqckj.gmis.oauth.granter;

import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.lang.L18nEnum;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TokenGranterBuilder
 *
 * @author gmis
 * @date 2020年03月31日10:27:24
 */
@Component
public class TokenGranterBuilder {
    @Autowired
    RedisService redisService;
    /**
     * TokenGranter缓存池
     */
    private Map<String, TokenGranter> granterPool = new ConcurrentHashMap<>();

    public TokenGranterBuilder(Map<String, TokenGranter> granterPool) {
        granterPool.forEach(this.granterPool::put);
    }

    /**
     * 获取TokenGranter
     *
     * @param grantType 授权类型
     * @return ITokenGranter
     */
    public TokenGranter getGranter(String grantType) {
        TokenGranter tokenGranter = granterPool.get(grantType);
        if (tokenGranter == null) {
            throw new BizException(redisService.getLangMessage(MessageConstants.GRENT_VERIFY_TYPE));
        } else {
            return tokenGranter;
        }
    }

}
