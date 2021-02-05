package com.cdqckj.gmis.oauthapi;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.jwt.TokenUtil;
import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.dto.TokenOauthApiDTO;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;
import com.cdqckj.gmis.utils.I18nUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * api认证Controller
 * @auther hc
 * @date 2020/09/30
 */
@Slf4j
@RestController
@RequestMapping("/oauth_api")
@AllArgsConstructor
@Api(value = "api授权认证", tags = "api授权认证接口")
public class OauthApiController {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RedisService redisService;

    @Autowired
    private OauthApiService oauthApiService;

    @Autowired
    private I18nUtil i18nUtil;


    /**
     * 生成临时票据
     * @auther hc
     * @date 2020/09/30
     * @param apiDTO
     * @return
     */
    @PostMapping("/ticket")
    public R<String> buildTicket(@RequestBody TicketOauthApiDTO apiDTO){
        //生成票据
        String ticket_code = oauthApiService.buildTicket(apiDTO);
        return R.success(ticket_code);
    }


    /**
     * 生成token
     * @auther hc
     * @date 2020/10/8
     * @param tokenDTO
     * @return
     */
    @PostMapping("/token")
    public R<AuthApiInfo> buildToken(@RequestBody TokenOauthApiDTO tokenDTO){

        return R.success(oauthApiService.buildToken(tokenDTO));
    }


//    /**
//     * 刷新token
//     * @return
//     */
//    @PostMapping("/refresh_token")
//    public R<AuthApiInfo> refreshToken(@RequestBody ){
//
//        return null;
//    }
}
