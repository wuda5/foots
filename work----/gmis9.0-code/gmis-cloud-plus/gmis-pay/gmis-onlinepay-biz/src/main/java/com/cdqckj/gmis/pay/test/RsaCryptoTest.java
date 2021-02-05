package com.cdqckj.gmis.pay.test;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.cdqckj.gmis.pay.test.aes.WXBizMsgCrypt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wechat.pay.contrib.apache.httpclient.util.RsaCryptoUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RsaCryptoTest {

  private static String mchId = "1603115705"; // 商户号
  private static String mchSerialNo = "2339B893C2371AC03D7EB6A56840AFEB595F1C8E"; // 商户证书序列号
  private static String apiV3Key = "e1dea31d334f48948313d90ac71efabb"; // api密钥
  // 你的商户私钥
  private static String privateKey = "-----BEGIN PRIVATE KEY-----\n"
          +"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDinVSNnWJ/aLD7\n" +
          "QragBg++wUZ3apmQnkZ8NHof3RkoxHMHvhYqxq03Q8i5gXB1JELijr/trXNB88be\n" +
          "JqKtlk5WLHhbmDN/hzz3+b1Yv9vcFEZM8UQJXXQ1FXbupo8SwbEoIZTWQ8nRIffI\n" +
          "8WskuHj4S+Tb594lWtz/w9Lxj5cBkaYvXx4d6IbCO+CP68qYEPXZ793F7DVNJPF1\n" +
          "AROD0PMjEDnoAB6OWpnp3nUJJJmyVUYGaRBco5aArTkRRXC1Ihqjvsuqp7qtsOYZ\n" +
          "ParjmAAgdE2Q9avoEtlfY82rFLscLSF3z9qquy9MzU1AotjgZz5G38MEKguyV+RN\n" +
          "Vlts+io3AgMBAAECggEAcRPMozWLdsQIu/fYJRWhObA7t07L/evchBGzdr7e1Qbh\n" +
          "11U0vneQ62i5ekdqlClZ7q8IelL4lkpMS4G/3xqHUFy0WhAiclpLQ/msT+K8lJ7R\n" +
          "TYd+SaYGXI6vX/pgnh1khv/qwtfklqR6fhxpOFOMmWlVc53JrZ4fdMiEM+FQmojg\n" +
          "c6ZI0mvOVcZ3v7g6T5x0cgWyOhK8kRWM0K72tv+s9XQTSCSYSwXZ81oV6uALxTG+\n" +
          "/pqHIaNlUwsFXb3zhEAAR+dfj7X+HH0lGCkqqewq9hhDRVOQrbvhe6hari4xJNJn\n" +
          "1HTN3VooulXbdv0ofX50/ipJMPfkPfdty545//exQQKBgQD6ZxQOFvpel+a5xYvz\n" +
          "5uX9wDPxzFw9I+xBL2fyB3CJvMsM6Xeo0rdlQY/B7FyDjNj6mkvUw7LwfDmN/wVQ\n" +
          "eoJQxsWiqYe72vU5I07xZnZ/KYORN59FJhKMTAh2hug/CtMZ7wKQL79WEaTX3Swc\n" +
          "/lV8hOi4IrrX2d3uN9cHc1iARwKBgQDnriAY7hwaWAsdkT5tZzNMSC+XHkT4NZrX\n" +
          "FSSWmNZORgZ7Mmt16H4LjLRlApanXeJXqbn08CyS/cOHblj6hsgFi/ARYsc9agsu\n" +
          "ykOvpeDZ3ECGwob3yi9m7Zwz6PAyVU34AxMR0XRjOVJXYkzjWR0+tSXgUPqNRt+K\n" +
          "8CgHue1ukQKBgQDITPw16RuN32So5eT1zZXcTYs/uIFwRvQNkKZNbLYQ9/xU78zQ\n" +
          "Nv6M+MzOCwxWDqziidpu77yLslM3yu+tWNI1W0tsur/g/V17v0q+v3+6aLLdzuiQ\n" +
          "n8vmfkumxHOzOi1zaUVboVYkuyhppHpNFwW6/XZLFTzwvU5R6EkRpKr+sQKBgQDK\n" +
          "rZzs0lmJfl5zBl5brT3GTRw9EsD5d0O3R4rTqG9K2J3Q5wH1i2fBSN2DxGjxkANz\n" +
          "90p0CiykhxWoBBQZTxHrMEnbm9N7Wargyxe9sNrtHG7itYkHezxOyi2th+mhzti/\n" +
          "0Ei9fMRaDYqIYbmr5ojcE/NHsvAN6eOkE4ahg7k/0QKBgCF3EI+hJcSp3Z4xdL5Q\n" +
          "10WpX9b+aHKhV+OzGH3VKWNN1rk3B96b6/qH8v3KqXfPDTF8iYv4BNjL9sdrwdKT\n" +
          "5NToYSkm4UzzcGJT7pSW0ezwnbA0HAeYfyyerNhwZnBTe8vfwsJjaEf1B9qOKMz7\n" +
          "CDDML0/gUrayglRhRKj3EJ6H"
          + "-----END PRIVATE KEY-----\n";

  private CloseableHttpClient httpClient;
  private AutoUpdateCertificatesVerifier verifier;

  {
    PrivateKey merchantPrivateKey = null;
    try {
      merchantPrivateKey = PemUtil.loadPrivateKey(
              new ByteArrayInputStream(privateKey.getBytes("utf-8")));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    //使用自动更新的签名验证器，不需要传入证书
    try {
      verifier = new AutoUpdateCertificatesVerifier(
              new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
              apiV3Key.getBytes("utf-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }


    httpClient = WechatPayHttpClientBuilder.create()
            .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
            .withValidator(new WechatPay2Validator(verifier))
            .build();
  }

  @Before
  public void setup() throws IOException {
    PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
        new ByteArrayInputStream(privateKey.getBytes("utf-8")));

    //使用自动更新的签名验证器，不需要传入证书
    verifier = new AutoUpdateCertificatesVerifier(
        new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
        apiV3Key.getBytes("utf-8"));


    httpClient = WechatPayHttpClientBuilder.create()
        .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
        .withValidator(new WechatPay2Validator(verifier))
        .build();
  }

  @After
  public void after() throws IOException {
    httpClient.close();
  }

  /**
   * 加密
   * @throws Exception
   */
  //
  // @Test
  public String encryptTest(String text) throws Exception {
    String ciphertext = RsaCryptoUtil.encryptOAEP(text, verifier.getValidCertificate());
    System.out.println("ciphertext: " + ciphertext);
    return ciphertext;
  }

  @Test
  public void Test() throws Exception {
    String ciphertext = RsaCryptoUtil.encryptOAEP("51092219910420319X", verifier.getValidCertificate());
    System.out.println("ciphertext: " + ciphertext);
  }

  @Test
  public  void Test111() throws Exception{
    String sToken = "8Qa0dx";
    String sCorpID = "qinchuanOrderc71246e906dcdc2c";
    String sEncodingAESKey = "ZDQ2MjRjMzZiNjc5NWQxZDk5ZGNmMDU0N2FmNTQ0M2Q";

    WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		/*
		------------使用示例一：验证回调URL---------------
		*企业开启回调模式时，企业号会向验证url发送一个get请求
		假设点击验证时，企业收到类似请求：
		* GET /cgi-bin/wxpush?msg_signature=5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3&timestamp=1409659589&nonce=263014780&echostr=P9nAzCzyDtyTWESHep1vC5X9xho%2FqYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp%2B4RPcs8TgAE7OaBO%2BFZXvnaqQ%3D%3D
		* HTTP/1.1 Host: qy.weixin.qq.com

		接收到该请求时，企业应		1.解析出Get请求的参数，包括消息体签名(msg_signature)，时间戳(timestamp)，随机数字串(nonce)以及公众平台推送过来的随机加密字符串(echostr),
		这一步注意作URL解码。
		2.验证消息体签名的正确性
		3. 解密出echostr原文，将原文当作Get请求的response，返回给公众平台
		第2，3步可以用公众平台提供的库函数VerifyURL来实现。

		*/
    // 解析出url上的参数值如下：
    // String sVerifyMsgSig = HttpUtils.ParseUrl("msg_signature");
    String sVerifyMsgSig = "43c436d1035db8dfaca928c75a707e4ffcdfb3cd";
    // String sVerifyTimeStamp = HttpUtils.ParseUrl("timestamp");
    String sVerifyTimeStamp = "1609308780077";
    // String sVerifyNonce = HttpUtils.ParseUrl("nonce");
    String sVerifyNonce = "01751ea4-5f19-491a-a4cf-11626a20127d";
    // String sVerifyEchoStr = HttpUtils.ParseUrl("echostr");
    String sVerifyEchoStr = "gSg01aNFhufIx1K47Vi5D9VDDEH4FpwBORR05LzutJtfuxAzMmD71c//w0MTNFzf1TeGxAOma1lTMD+PUIUo1ZVOgy6TR43/qwc0yuiQ4uT66t5iI5kAv2qQyp4TwW6Jz0BhvZC/w8dAozc7GlkVxCmUB32e+ios1ZMLrikyg7dby6ggE3ec5a6R++L9Com4nYVGXJFKzrQ2NRDku82RjZyCpGEPbTd/7KngLV944DUViTIpnmuOEkDtxgmWA8ZapB8IpstqCHDqisGi1TkQSYCDsVsErcPxBFYL47YK76u9KwVYm7LA539AKVm3/Dod3QrqsrEcZCcv4OuN1OyKB3XlgioTScz6runVYrSgi2Aeyw6SjiOQiiLtWIF552aWSHaMP8IfvbGkFucwAnrXkZdOfi95Pv9jorjtStxwvdDhoGfOMc1sGFzlgvooLOdp53ZBYWh2aYPdilOvQ4/RfmR7Vu3rNTEWnSxfl0r3YJw5cLApp2Lu75OOe70nRAFsvt7wIr9NpXVrPjUKtnnjwU5UVxOFW096pX4lMK5pKDOGNKB71usnAxVSuG3NS0+qhMdm4bpj1T7xM9lmDJN9NZ3PejFsvTVxylpvnMjgx6Xqe4xrFFNBI2b4qmY9R/HsLwaLsdoVJnxuHwIGMdHcH8vtCoRQnvA63t24+si6fYdWgOfKLfEhfkmkKZiwW5WXfCpuE/KWKMvGtCD30VJV0FiIs9lB68IFXr0ZvnaEyV54G74VNS0hCwd0Xf+07LjA0144sC+QlFvQDp95lOgalud9i3vp4WclZJjrC3MnECjG+6BPc2FqUWObCbq/go3Yogm/yhF/FYfXdWg7ITN1cuV4k5z79RHxIJr+2/LZ/heZsIpNaS6rUl/AHoTuPJq/";
    String sEchoStr; //需要返回的明文
    try {
      sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,
              sVerifyNonce, sVerifyEchoStr);
      System.out.println("verifyurl echostr: " + sEchoStr);
      // 验证URL成功，将sEchoStr返回
      // HttpUtils.SetResponse(sEchoStr);
    } catch (Exception e) {
      //验证URL失败，错误原因请查看异常
      e.printStackTrace();
    }

		/*
		------------使用示例二：对用户回复的消息解密---------------
		用户回复消息或者点击事件响应时，企业会收到回调消息，此消息是经过公众平台加密之后的密文以post形式发送给企业，密文格式请参考官方文档
		假设企业收到公众平台的回调消息如下：
		POST /cgi-bin/wxpush? msg_signature=477715d11cdb4164915debcba66cb864d751f3e6&timestamp=1409659813&nonce=1372623149 HTTP/1.1
		Host: qy.weixin.qq.com
		Content-Length: 613
		<xml>		<ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt>
		<AgentID><![CDATA[218]]></AgentID>
		</xml>

		企业收到post请求之后应该		1.解析出url上的参数，包括消息体签名(msg_signature)，时间戳(timestamp)以及随机数字串(nonce)
		2.验证消息体签名的正确性。
		3.将post请求的数据进行xml解析，并将<Encrypt>标签的内容进行解密，解密出来的明文即是用户回复消息的明文，明文格式请参考官方文档
		第2，3步可以用公众平台提供的库函数DecryptMsg来实现。
		*/
    // String sReqMsgSig = HttpUtils.ParseUrl("msg_signature");
    String sReqMsgSig = "477715d11cdb4164915debcba66cb864d751f3e6";
    // String sReqTimeStamp = HttpUtils.ParseUrl("timestamp");
    String sReqTimeStamp = "1409659813";
    // String sReqNonce = HttpUtils.ParseUrl("nonce");
    String sReqNonce = "1372623149";
    // post请求的密文数据
    // sReqData = HttpUtils.PostData();
    String sReqData = "<xml><ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt><AgentID><![CDATA[218]]></AgentID></xml>";

    try {
      String sMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
      System.out.println("after decrypt msg: " + sMsg);
      // TODO: 解析出明文xml标签的内容进行处理
      // For example:
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      StringReader sr = new StringReader(sMsg);
      InputSource is = new InputSource(sr);
      Document document = db.parse(is);

      Element root = document.getDocumentElement();
      NodeList nodelist1 = root.getElementsByTagName("Content");
      String Content = nodelist1.item(0).getTextContent();
      System.out.println("Content：" + Content);

    } catch (Exception e) {
      // TODO
      // 解密失败，失败原因请查看异常
      e.printStackTrace();
    }

	/*
		------------使用示例三：企业回复用户消息的加密---------------
		企业被动回复用户的消息也需要进行加密，并且拼接成密文格式的xml串。
		假设企业需要回复用户的明文如下：
		<xml>
		<ToUserName><![CDATA[mycreate]]></ToUserName>
		<FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName>
		<CreateTime>1348831860</CreateTime>
		<MsgType><![CDATA[text]]></MsgType>
		<Content><![CDATA[this is a test]]></Content>
		<MsgId>1234567890123456</MsgId>
		<AgentID>128</AgentID>
		</xml>

		为了将此段明文回复给用户，企业应：			1.自己生成时间时间戳(timestamp),随机数字串(nonce)以便生成消息体签名，也可以直接用从公众平台的post url上解析出的对应值。
		2.将明文加密得到密文。	3.用密文，步骤1生成的timestamp,nonce和企业在公众平台设定的token生成消息体签名。			4.将密文，消息体签名，时间戳，随机数字串拼接成xml格式的字符串，发送给企业。
		以上2，3，4步可以用公众平台提供的库函数EncryptMsg来实现。
		*/
    String sRespData = "<xml><ToUserName><![CDATA[mycreate]]></ToUserName><FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId><AgentID>128</AgentID></xml>";
    try{
      String sEncryptMsg = wxcpt.EncryptMsg(sRespData, sReqTimeStamp, sReqNonce);
      System.out.println("after encrypt sEncrytMsg: " + sEncryptMsg);
      // 加密成功
      // TODO:
      // HttpUtils.SetResponse(sEncryptMsg);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      // 加密失败
    }
  }



  //@Test
  public void postEncryptDataTest() throws Exception {
    HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/smartguide/guides");

    String text = "helloworld";
    String ciphertext = RsaCryptoUtil.encryptOAEP(text, verifier.getValidCertificate());

    String data = "{\n"
        + "  \"store_id\" : 1234,\n"
        + "  \"corpid\" : \"1234567890\",\n"
        + "  \"name\" : \"" + ciphertext + "\",\n"
        + "  \"mobile\" : \"" + ciphertext + "\",\n"
        + "  \"qr_code\" : \"https://open.work.weixin.qq.com/wwopen/userQRCode?vcode=xxx\",\n"
        + "  \"sub_mchid\" : \"1234567890\",\n"
        + "  \"avatar\" : \"logo\",\n"
        + "  \"userid\" : \"robert\"\n"
        + "}";
    StringEntity reqEntity = new StringEntity(
        data, ContentType.create("application/json", "utf-8"));
    httpPost.setEntity(reqEntity);
    httpPost.addHeader("Accept", "application/json");
    httpPost.addHeader("Wechatpay-Serial", "5157F09EFDC096DE15EBE81A47057A7232F1B8E1");

    CloseableHttpResponse response = httpClient.execute(httpPost);
    assertTrue(response.getStatusLine().getStatusCode() != 401);
    assertTrue(response.getStatusLine().getStatusCode() != 400);
    try {
      HttpEntity entity2 = response.getEntity();
      // do something useful with the response body
      // and ensure it is fully consumed
      EntityUtils.consume(entity2);
    } finally {
      response.close();
    }
  }
}
