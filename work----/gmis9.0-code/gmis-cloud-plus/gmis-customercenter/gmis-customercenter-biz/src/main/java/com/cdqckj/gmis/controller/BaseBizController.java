package com.cdqckj.gmis.controller;

import com.cdqckj.gmis.authority.api.TenantApi;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.constants.CusOauthConstants;
import com.cdqckj.gmis.entity.vo.CusFeesPayTypeVO;
import com.cdqckj.gmis.enums.PlatformTypeEnums;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.service.BaseBizService;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.utils.I18nUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/baseBiz")
@Api(value = "baseBiz", tags = "客户端业务基础数据")
//@PreAuth()
public class BaseBizController {

   @Autowired
   private I18nUtil i18nUtil;

   @Autowired
   private BaseBizService baseBizService;

   @Autowired
   private TenantApi tenantApi;

   /**
    * 获取租户列表
    * @auther hc
    * @date 2020/10/12
    * @param tenantName 燃气公司名字
    * @param platType 应用平台标识
    *      IOS("ios","ios"),
    *     ANDROID("android","安卓"),
    *     MP_WEIXIN("mp-weixin","微信小程序"),
    *     MP_ALIPAY("mp-alipay","支付宝小程序");
    * @return
    */
   @ApiOperation(value = "获取燃气公司列表")
   @GetMapping("/tenants")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "tenantName",value = "燃气公司搜索条件"),
           @ApiImplicitParam(name = "platType",value = "应用平台标识:ios;android;mp-weixin：微信小程序;mp-alipay：支付宝小程序",required = true)
   })
   public R<List<HashMap<String,Object>>> getTenants(String tenantName, @RequestParam("platType") @NotEmpty String platType){
      Tenant queryData = new Tenant();
      queryData.setStatus(TenantStatusEnum.NORMAL);
      queryData.setName(tenantName);

      R<List<Tenant>> listR = tenantApi.query(queryData);
      if(listR.getIsError()){
         return R.fail(i18nUtil.getMessage(MessageConstants.SELECT_FAIL,
                 "燃气公司列表获取失败"));
      }
      List<Tenant> listData = listR.getData();
      ArrayList<HashMap<String,Object>> rsData = new ArrayList<>();
      //加密租户code
      listData.stream()
              .forEach(item->
              {
                 HashMap<String,Object> ha = new HashMap<>();
                 ha.put("code",JwtUtil.base64Encoder(item.getCode()));
                 ha.put("name",item.getName());
                 /** 设置小程序应用三方标识 现阶段只有微信用到  暂无数据源只有先写死**/
                 //获取租户对应的微信小程序应用三方标识
                 if(PlatformTypeEnums.MP_WEIXIN.getKey().equals(platType)){

                    ha.put("three_appid", CusOauthConstants.THREE_APPID);
                    ha.put("three_secret", CusOauthConstants.THREE_SECRET);
                    //TODO
                 }
                 rsData.add(ha);
              });

      return R.success(rsData);
   }

   /**
    * 获取支付方式
    * @auther hc
    * @return
    */
   @ApiOperation("获取支付方式")
   @GetMapping("/payType")
   public R<List<CusFeesPayTypeVO>> payType(){
      List<CusFeesPayTypeVO> data = baseBizService.payType();
      if(null==data){
         return R.fail("获取支付方式失败");
      }

      return R.success(data);
   }
}
