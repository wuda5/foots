package com.cdqckj.gmis.controller;

import com.cdqckj.gmis.appmanager.AppmanagerApi;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.constants.AppConfConstants;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.entity.dto.CusOauthRealNameDTO;
import com.cdqckj.gmis.entity.dto.CusOauthRefreshTokenDTO;
import com.cdqckj.gmis.entity.dto.CusOauthTicketDTO;
import com.cdqckj.gmis.entity.dto.CusOauthTokenDTO;
import com.cdqckj.gmis.entity.vo.CusCustomerVO;
import com.cdqckj.gmis.entity.vo.CusOauthTicketVO;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.msgs.api.SmsApi;
import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;
import com.cdqckj.gmis.service.CusOauthService;
import com.cdqckj.gmis.utils.I18nUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 客户端认证控制器
 * @auther hc
 * @date 2020/10/09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cusOauth")
@Api(value = "cusOauth", tags = "客户端认证相关接口")
public class CusOauthController {

    @Autowired
    private AppmanagerApi appmanagerApi;

    @Autowired
    private CustomerBizApi customerBizApi;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private I18nUtil i18nUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CusOauthService cusOauthService;

    //验证码存活时间 5分钟
    private final static Long VERIFY_CODE_LIVE_TIME = 1800L;

    /**
     * 生成临时票据
     * @param ticketDTO
     * @return
     */

    @ApiOperation(value = "生成认证临时票据")
    @PostMapping("/ticket")
    @Deprecated
    public R<CusOauthTicketVO> buildTicket(@RequestBody CusOauthTicketDTO ticketDTO){
        //应用id相关验证
        //认证app_id是否在系统中
        Appmanager queryData = new Appmanager();
        queryData.setAppId(ticketDTO.getAppId());
        queryData.setStatus(true);

        R<List<Appmanager>> listR = appmanagerApi.query(queryData);
        if(listR.getIsError()){
            return R.fail("获取应用服务失败");
        }else if(listR.getIsSuccess() && CollectionUtils.isNotEmpty(listR.getData())) {
            Appmanager appmanager = listR.getData().get(0);

            //2、app应用校验
            if (StringUtils.isEmpty(appmanager.getAppSecret())) {
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_NOT, "当前应用不可用,未授权"));
            }

            // 2、校验app_id时效性
            LocalDateTime nowTime = LocalDateTime.now();
            //应用有效开始时间 默认为分配secret时间为开始时间
            LocalDateTime validTimeStart = appmanager.getValidTimeStart();
            //应用有效结束时间 不设置为永久
            LocalDateTime validTimeEnd = appmanager.getValidTimeEnd();
            if (validTimeStart == null || nowTime.isBefore(validTimeStart)) {
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_EFFECTIVE, "该应用尚未生效"));
            } else if (validTimeEnd != null && nowTime.isAfter(validTimeEnd)) {
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_FAILURE, "该应用已失效"));
            }
            //生成临时票据
            CusOauthTicketVO ticketVO = cusOauthService.buildTicket(ticketDTO,appmanager);
            if(null==ticketVO){
                return R.fail(i18nUtil.getMessage(MessageConstants.TICKET_BUILD_FAIL, "认证票据生成失败"));
            }

            return R.success(ticketVO);
        }

        return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_NOT, "当前应用不可用,未授权"));
    }


    /**
     * 生成token
     * @return
     */
    @PostMapping("/token")
    @ApiOperation(value = "生成token;code:1011,票据已失效;code:1012,密匙不正确")
    public R<AuthApiInfo> buildToken(@RequestBody CusOauthTokenDTO tokenDTO){

        //应用id相关验证
        //认证app_id是否在系统中
        Appmanager queryData = new Appmanager();
        queryData.setAppId(tokenDTO.getAppId());
        queryData.setStatus(true);
        TicketOauthApiDTO oauthApiDTO = new TicketOauthApiDTO();

        R<List<Appmanager>> listR = appmanagerApi.query(queryData);
        if(listR.getIsError()){
            return R.fail("获取应用服务失败");
        }else if(listR.getIsSuccess() && CollectionUtils.isNotEmpty(listR.getData())) {
            Appmanager appmanager = listR.getData().get(0);

            //2、app应用校验
            if (StringUtils.isEmpty(appmanager.getAppSecret())) {
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_NOT, "当前应用不可用,未授权"));
            }else if(!StringUtils.equals(tokenDTO.getAppSecret(),appmanager.getAppSecret())){
                return R.fail(1012,i18nUtil.getMessage(MessageConstants.OAUTH_SECRET_FAIL, "密匙不正确请核实"));
            }

            // 2、校验app_id时效性
            LocalDateTime nowTime = LocalDateTime.now();
            //应用有效开始时间 默认为分配secret时间为开始时间
            LocalDateTime validTimeStart = appmanager.getValidTimeStart();
            //应用有效结束时间 不设置为永久
            LocalDateTime validTimeEnd = appmanager.getValidTimeEnd();
            if (validTimeStart == null || nowTime.isBefore(validTimeStart)) {
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_EFFECTIVE, "该应用尚未生效"));
            } else if (validTimeEnd != null && nowTime.isAfter(validTimeEnd)) {
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_FAILURE, "该应用已失效"));
            }

            //设置token生成参数
            oauthApiDTO.setAppId(appmanager.getAppId());
            oauthApiDTO.setAppSecret(appmanager.getAppSecret());
            oauthApiDTO.setTenantCode(appmanager.getTenantCode());
        }

//       //验证票据的有效性
//        Object ticketData = redisService.get(AppConfConstants.TICKET_PREFIX+tokenDTO.getTicket());
//        if(null == ticketData){
//            return R.fail(1011,i18nUtil.getMessage(MessageConstants.TICKET_PAPER_FAILURE, "临时票据已失效"));
//        }
//        TicketOauthApiDTO oauthApiDTO = JSONObject.parseObject((String) ticketData, TicketOauthApiDTO.class);

//        //验证密钥是否正确
//        if(!StringUtils.equals(tokenDTO.getAppSecret(),oauthApiDTO.getAppSecret())){
//            return R.fail(1012,i18nUtil.getMessage(MessageConstants.OAUTH_SECRET_FAIL, "密匙不正确请核实"));
//        }

        //生成token信息
        AuthApiInfo authApiInfo = cusOauthService.buildToken(tokenDTO,oauthApiDTO);
        if(null== authApiInfo){
            return R.fail(i18nUtil.getMessage(MessageConstants.OAUTH_TOKEN_FAIL, "生成token失败"));
        }

        return R.success(authApiInfo);
    }

    /**
     * 刷新token
     * @auther hc
     * @param refreshTokenDTO
     * @return
     */
    @PostMapping("/refresh_token")
    @ApiOperation(value = "刷新token")
    public R<AuthApiInfo> refreshToken(@RequestBody @Valid CusOauthRefreshTokenDTO refreshTokenDTO){
        //验证refresh_token是否过期 且 对应appID
        Date nowDate = new Date();
        Claims claims = JwtUtil.parseJWT(refreshTokenDTO.getRefreshToken());
        //过期时间
        Date expiration =  claims.getExpiration();
        //app_id
        String appID = claims.get(BaseContextConstants.APP_ID,String.class);
        if(expiration==null || expiration.before(nowDate)){
            return R.fail(1004,i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_NOT, "token失效请重新认证"));
        }
        if(StringUtils.isEmpty(appID) || !StringUtils.equals(appID,refreshTokenDTO.getAppId())){
            return R.fail(1005,i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_NOT, "appId不正确"));
        }

        return cusOauthService.refreshToken(claims);
    }


    /**
     * 客户实名认证
     * @param realNameDTO
     * @return
     */
    @ApiOperation(value = "模拟登录")
    @PostMapping("/realName")
//    @PreAuth
    public R<CusCustomerVO> realNameOauth(@RequestBody CusOauthRealNameDTO realNameDTO){

        //1、验证验证码是否正确
        Object verifyCode = redisService.get(AppConfConstants.VERIFY_CODE_PREFIX+realNameDTO.getPhone());
        if(verifyCode==null ||
                !StringUtils.equals(String.valueOf(verifyCode),realNameDTO.getVerifyCode())){
            return R.fail(1004,i18nUtil.getMessage(MessageConstants.OAUTH_VERIFY_CODE_FAIL,"验证码错误"));
        }
        String tenant = BaseContextHandler.getTenant();
        if(StringUtil.isBlank(tenant)){
            return R.fail(1005,"租户编码不能为空");
        }
        CusCustomerVO result = cusOauthService.realNameOauth(realNameDTO);
        if(result==null){
            return R.fail("存储数据失败");
        }

        return R.success(result);
    }


    /**
     * 发送验证码
     * @auther hc
     * @return
     */
    @ApiOperation(value = "发送短信验证码")
    @GetMapping("/sendVerifyCode")
    public R<String> sendVerifyCode(@RequestParam("phone") String phone){
        String verifyCode = "";
        //验证缓存中是否存在
        Object o = redisService.get(AppConfConstants.VERIFY_CODE_PREFIX + phone);

        if(null!=o){
            verifyCode = (String)o;
        }else{
            verifyCode = buildVerifyCode(phone);
        }

        //获取短信验证码,模板
//        PageParams<SmsTemplatePageDTO> params = new PageParams<>();
//        SmsTemplatePageDTO query = new SmsTemplatePageDTO();
//        //模板类型验证码通知
//        query.setTemplateTypeId(1290854419893583872L);
//        query.setTemplateStatus(0);
//        params.setModel(query);
//        R<Page<SmsTemplate>> pageR = smsApi.templatePage(params);
//        if(pageR.getIsError()){
//            return R.success("调用短信服务失败");
//        }else if(CollectionUtils.isEmpty(pageR.getData().getRecords())){
//            return R.fail("短信验证码模板未配置");
//        }
//        SmsTemplate smsTemplate = pageR.getData().getRecords().get(0);
//        //发送短信
//        SmsSendTaskDTO taskDTO = new SmsSendTaskDTO();
//        taskDTO.setDraft(false);
//        taskDTO.setReceiver(phone);
//        taskDTO.setTemplateId(smsTemplate.getId());
//        JSONObject js = new JSONObject();
//        js.put("1",verifyCode);
//        taskDTO.setTemplateParam(js);
//        R<SmsTask> smsTaskR = smsApi.send(taskDTO);
//        if(smsTaskR.getIsError()){
//            return R.fail(smsTaskR.getMsg());
//        }
//        Boolean flag = true;
//        if(smsTaskR.getIsError()){
//            flag = false;
//        }
        return R.success(verifyCode);
    }

    /**
     * 生成6位验证码
     * @return
     */
    private String buildVerifyCode(String phone){

        String verifyCode = String.valueOf((int)((Math.random()*9+1)*100000));
        //存入缓存
        redisService.set(AppConfConstants.VERIFY_CODE_PREFIX + phone,verifyCode,VERIFY_CODE_LIVE_TIME);

        return verifyCode;
    }
}
