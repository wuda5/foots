package com.cdqckj.gmis.oauthapi.hystrix;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.oauthapi.OauthapiApi;
import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.dto.TokenOauthApiDTO;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;
import org.springframework.stereotype.Component;

/**
 * 熔断类
 *
 * @author hc
 * @date 2020/10/09
 */
@Component
public class OauthapiApiFallback implements OauthapiApi {
    @Override
    public R<String> buildTicket(TicketOauthApiDTO apiDTO) {
        return R.timeout();
    }

    @Override
    public R<AuthApiInfo> buildToken(TokenOauthApiDTO tokenDTO) {
        return R.timeout();
    }
}
