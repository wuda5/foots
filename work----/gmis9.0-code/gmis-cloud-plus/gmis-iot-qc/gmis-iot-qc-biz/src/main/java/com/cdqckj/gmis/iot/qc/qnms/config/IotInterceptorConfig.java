package com.cdqckj.gmis.iot.qc.qnms.config;

import com.cdqckj.gmis.iot.qc.qnms.interceptor.IotAuthInterceptor;
import com.cdqckj.gmis.iot.qc.qnms.interceptor.IotSubscribeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 远传系统拦截器配置
 * @author: 秦川物联网科技
 * @date: 2020/10/12  09:58
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Configuration
public class IotInterceptorConfig implements WebMvcConfigurer {

   @Override
   public void addInterceptors(InterceptorRegistry registry){
       registry.addInterceptor(new IotAuthInterceptor());
       registry.addInterceptor(new IotSubscribeInterceptor());
   }
}
