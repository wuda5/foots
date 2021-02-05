package com.cdqckj.gmis.oauthapi;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.dto.TokenOauthApiDTO;
import com.cdqckj.gmis.oauthapi.hystrix.OauthapiApiFallback;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 岗位API
 *
 * @author gmis
 * @date 2019/08/02
 */
@FeignClient(name = "${gmis.feign.oauthapi-server:gmis-oauthapi-server}", path = "/oauth_api",
        qualifier = "oauthapiApi", fallback = OauthapiApiFallback.class)
public interface OauthapiApi {

    /**
     * 生成临时票据
     * @auther hc
     * @date 2020/09/30
     * @param apiDTO
     * @return
     */
    @PostMapping("/ticket")
    R<String> buildTicket(@RequestBody TicketOauthApiDTO apiDTO);


    /**
     * 生成token
     * @auther hc
     * @date 2020/10/8
     * @param tokenDTO
     * @return
     */
    @PostMapping("/token")
    R<AuthApiInfo> buildToken(@RequestBody TokenOauthApiDTO tokenDTO);
}
