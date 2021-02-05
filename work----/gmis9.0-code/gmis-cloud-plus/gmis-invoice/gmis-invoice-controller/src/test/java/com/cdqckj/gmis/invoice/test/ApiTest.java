//package com.cdqckj.gmis.invoice.test;
//
//import cn.hutool.json.JSONObject;
//import com.cdqckj.gmis.invoice.controller.Constants;
//import com.cdqckj.gmis.invoice.controller.HttpUtil;
//import org.junit.Assert;
//import org.junit.Test;
//
//public class ApiTest {
//
//    @Test
//    public void testGetToken() {
//	JSONObject proxyParm = new JSONObject();
//	proxyParm.put("openid", "CXBgahq0T0NH5TXpNML7DIsd9FQu2kGuvaiAtccD9pPw7YquVnU1476878645585");
//	proxyParm.put("app_secret", "2eGZdnv6L88MrdVhI2rTtczB9gPMVnOQHmd4vmM7nXuS8OAhRpn1476878645586");
//	JSONObject result = HttpUtil.doPostJSON2(Constants.API_TOKEN_URL, proxyParm);
//	System.out.println(result);
//	Assert.assertNotNull(result);
//	Assert.assertEquals("SUCCESS", result.getStr("result"));
//    }
//
//    @Test
//    public void testGetPdfUrl() {
//	JSONObject proxyParm = new JSONObject();
//	proxyParm.put("access_token", "access_token");
//	proxyParm.put("serviceKey", "ep_pdf_getPdfUrl");
//	JSONObject data = new JSONObject();
//	data.put("fileKey", "pdf_BMKbQ2n1478500628215");
//	proxyParm.put("data", data);
//	System.out.println(proxyParm);
//	JSONObject result = HttpUtil.doPostJSON2(Constants.API_BUSS_URL, proxyParm);
//	System.out.println(result);
//	Assert.assertNotNull(result);
//	Assert.assertEquals("SUCCESS", result.getStr("result"));
//    }
//}
