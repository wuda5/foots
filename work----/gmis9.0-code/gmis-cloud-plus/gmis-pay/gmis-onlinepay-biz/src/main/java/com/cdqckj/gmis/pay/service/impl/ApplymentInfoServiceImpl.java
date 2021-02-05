package com.cdqckj.gmis.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.pay.dao.ApplymentInfoMapper;
import com.cdqckj.gmis.pay.elias.CertificateDownloader;
import com.cdqckj.gmis.pay.elias.util.CertUtil;
import com.cdqckj.gmis.pay.elias.util.HttpUrlUtil;
import com.cdqckj.gmis.pay.elias.util.JsonUtils;
import com.cdqckj.gmis.pay.entity.ApplymentInfo;
import com.cdqckj.gmis.pay.enumeration.SalesScenesEnum;
import com.cdqckj.gmis.pay.service.ApplymentInfoService;
import com.cdqckj.gmis.pay.util.WxPayAppConfig;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.WechatPayUploadHttpPost;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wechat.pay.contrib.apache.httpclient.util.RsaCryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import picocli.CommandLine;

import java.io.*;
import java.net.URI;
import java.security.PrivateKey;
import java.util.*;

import static com.cdqckj.gmis.utils.HttpClientUtils.doGet;
import static org.junit.Assert.assertEquals;

/**
 * <p>
 * 业务实现类
 * 特约商户进件申请信息
 * </p>
 *
 * @author gmis
 * @date 2020-11-17
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ApplymentInfoServiceImpl extends SuperServiceImpl<ApplymentInfoMapper, ApplymentInfo> implements ApplymentInfoService {
    @Autowired
    private WxPayAppConfig config;

    private List<String> SETTLEMENT_ID = Arrays.asList("719","721","716","717","730","739","727","738","726");

    //用于证书解密的密钥(管理员身份登录微信商户平台自己设置)
    private String apiV3key;
    // 商户号
    private static String mchId;
    // 商户证书序列号
    private static String mchSerialNo;
    // 微信支付平台证书(非必须)
    private static String wechatpayCertificateFilePath = "";
    //下载成功后保存证书的路径
    private static String outputFilePath = "weixinpay/certificate";
    private PrivateKey merchantPrivateKey;
    private AutoUpdateCertificatesVerifier verifier;
    private CloseableHttpClient httpClient;

    @Autowired
    public void init() throws IOException {
        mchId = null==mchId? config.getMchID():mchId;
        apiV3key = null==apiV3key? config.getApiV3key():apiV3key;
        mchSerialNo = null==mchSerialNo? config.getMchSerialNo():mchSerialNo;
        //privateKey = null==privateKey? CertUtil.getResourceByPath(CertUtil.mchPrivateKeyFilePath):privateKey;
        merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(CertUtil.privateKey.getBytes("utf-8")));
        //使用自动更新的签名验证器，不需要传入证书
        verifier = new AutoUpdateCertificatesVerifier(new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
                apiV3key.getBytes("utf-8"));
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();
    }

    @Override
    public R<Map<String, String>> uploadImage(MultipartFile simpleFile) throws Exception {
        //init();
        String oldFileName = simpleFile.getOriginalFilename();
        URI uri = new URI("https://api.mch.weixin.qq.com/v3/merchant/media/upload");
        File file = new File(UUID.randomUUID().toString()+oldFileName.substring(oldFileName.lastIndexOf(".")));
        //InputStream inputStream = simpleFile.getInputStream();
        FileUtils.copyInputStreamToFile(simpleFile.getInputStream(), file);
        simpleFile.transferTo(file);
        try (FileInputStream s1 = new FileInputStream(file)) {
            String sha256 = DigestUtils.sha256Hex(s1);
            try (InputStream s2 = new FileInputStream(file)) {
                WechatPayUploadHttpPost request = new WechatPayUploadHttpPost.Builder(uri)
                        .withImage(file.getName(), sha256, s2)
                        .build();

                CloseableHttpResponse response1 = httpClient.execute(request);
                assertEquals(200, response1.getStatusLine().getStatusCode());
                try {
                    HttpEntity entity1 = response1.getEntity();
                    String s = EntityUtils.toString(entity1);
                    System.out.println("图片："+s);
                    return R.success(JsonUtils.stringToMap(s));
                } finally {
                    response1.close();
                    s2.close();
                    s1.close();
                    httpClient.close();
                    FileUtils.forceDelete(file);
                }
            }
        }
    }

    /**
     * 下载证书到服务器
     */
    @Override
    public void certDownload() throws Exception {
        //init();
        String[] args = {"-k", apiV3key, "-m", mchId, "-f", CertUtil.mchPrivateKeyFilePath,
                "-s", mchSerialNo, "-o", outputFilePath};//, "-c", wechatpayCertificateFilePath
        CommandLine.run(new CertificateDownloader(), args);
    }

    @Override
    public R<String> submitApplication(ApplymentInfo saveDTO) throws Exception  {
        //init();
        //请求参数
        Map<String, Object> map = new HashMap<>();
        // 1-业务申请编号
        String business_code = UUID.randomUUID().toString().replaceAll("-","");
        saveDTO.setBusinessCode(business_code);
        // 2-超级管理员信息(必填)
        Map<String, Object> contact_info = new HashMap<>();
        contact_info.put("contact_name", encrypt(saveDTO.getContactName()));// 超级管理员姓名
        contact_info.put("contact_id_number", encrypt(saveDTO.getContactIdNumber()));// 超级管理员身份证件号码
        contact_info.put("openid", saveDTO.getOpenid());// 超级管理员微信openid
        contact_info.put("mobile_phone", encrypt(saveDTO.getMobilePhone()));// 超级管理员联系手机
        contact_info.put("contact_email", encrypt(saveDTO.getContactEmail()));// 超级管理员联系邮箱
        // 3-主体资料(必填)
        Map<String, Object> subject_info = new HashMap<>();
        //3.1-营业执照
        Map<String, String> business_license_info = new HashMap<>();//营业执照
        business_license_info.put("license_copy", saveDTO.getLicenseCopy());//营业执照照片
        business_license_info.put("license_number", saveDTO.getLicenseNumber());//注册号/统一社会信用代码
        business_license_info.put("merchant_name", saveDTO.getMerchantName());//商户名称
        business_license_info.put("legal_person", saveDTO.getLegalPerson());//个体户经营者/法人姓名
        //3.2-经营者/法人身份证件
        Map<String, Object> identity_info = new HashMap<>(); // 经营者身份证信息
        identity_info.put("id_doc_type", saveDTO.getIdDocType());
        Map<String, Object> id_card_info = new HashMap<>(); // 身份证信息
        id_card_info.put("id_card_copy", saveDTO.getIdCardCopy());
        id_card_info.put("id_card_national", saveDTO.getIdCardNational());
        id_card_info.put("id_card_name", encrypt(saveDTO.getIdCardName()));
        id_card_info.put("id_card_number", encrypt(saveDTO.getIdCardNumber()));
        id_card_info.put("card_period_begin", saveDTO.getCardPeriodBegin());
        id_card_info.put("card_period_end", saveDTO.getCardPeriodEnd());
        identity_info.put("id_card_info", id_card_info);
        boolean bool= saveDTO.getOwner();
        identity_info.put("owner", bool);
        if(!bool){
            Map<String, Object> ubo_info = new HashMap<>(); // 最终受益人信息(UBO]
            ubo_info.put("id_type", saveDTO.getUboIdType());
            ubo_info.put("id_card_copy", saveDTO.getUboIdCardCopy());
            ubo_info.put("id_card_national", saveDTO.getUboIdCardNational());
            ubo_info.put("name", encrypt(saveDTO.getUboName()));
            ubo_info.put("id_number", encrypt(saveDTO.getUboIdNumber()));
            ubo_info.put("id_period_begin", saveDTO.getUboIdPeriodBegin());
            ubo_info.put("id_period_end", saveDTO.getUboIdPeriodEnd());
            subject_info.put("ubo_info", ubo_info);
        }
        subject_info.put("subject_type", saveDTO.getSubjectType());// 主体类型
        subject_info.put("business_license_info", business_license_info);
        subject_info.put("identity_info", identity_info);
        //4-经营资料
        Map<String, Object> business_info = new HashMap<>();
        //经营场景
        Map<String, Object> sales_info = new HashMap<>();
        String[] saleTypes =  saveDTO.getSalesScenesType().split(",");
        sales_info.put("sales_scenes_type", saleTypes);
        for(String str:saleTypes){
            SalesScenesEnum tyepe =  SalesScenesEnum.get(str);

            switch (tyepe){
                case SALES_SCENES_STORE:
                    //线下门店场景
                    Map<String, Object> biz_store_info = new HashMap<>();
                    biz_store_info.put("biz_store_name", saveDTO.getBizStoreName());//门店名称
                    biz_store_info.put("biz_address_code", saveDTO.getBizAddressCode());//门店省市编码
                    biz_store_info.put("biz_store_address", saveDTO.getBizStoreAddress());//门店地址
                    biz_store_info.put("store_entrance_pic", saveDTO.getStoreEntrancePic().split(","));//门店门头照片
                    biz_store_info.put("indoor_pic", saveDTO.getIndoorPic().split(","));//店内环境照片
                    biz_store_info.put("biz_sub_appid", config.getAppSubAppid());//线下场所对应的商家APPID
                    sales_info.put("biz_store_info", biz_store_info);
                    break;
                case SALES_SCENES_MINI_PROGRAM:
                    // 小程序场景
                    Map<String, Object> mini_program_info = new HashMap<>();
                    mini_program_info.put("mini_program_appid", saveDTO.getMiniProgramAppid());//服务商小程序APPID
                    mini_program_info.put("mini_program_sub_appid", saveDTO.getMiniProgramSubAppid());//商家小程序APPID
                    sales_info.put("mini_program_info", mini_program_info);
                    break;
                case SALES_SCENES_APP:
                    // APP场景
                    Map<String, Object> app_info = new HashMap<>();
                    app_info.put("app_appid", saveDTO.getAppAppid());//服务商应用APPID
                    app_info.put("app_sub_appid", saveDTO.getAppSubAppid());//商家应用APPID
                    sales_info.put("app_info", app_info);
                    break;
                case SALES_SCENES_WEB:
                    // 互联网网站场景
                    Map<String, Object> web_info = new HashMap<>();
                    web_info.put("domain", saveDTO.getDomain());//互联网网站域名
                    sales_info.put("web_info", web_info);
                    break;
                default:
                    break;
            }
        }
        business_info.put("merchant_shortname", saveDTO.getMerchantShortname());
        business_info.put("service_phone", saveDTO.getServicePhone());
        business_info.put("sales_info", sales_info);
        // 结算规则
        Map<String, Object> settlement_info = new HashMap<>();
        settlement_info.put("qualifications", Arrays.asList(saveDTO.getQualifications()));// 特殊资质图片
        settlement_info.put("settlement_id", saveDTO.getSettlementId());
        settlement_info.put("qualification_type", saveDTO.getQualificationType());
        if(SETTLEMENT_ID.contains(saveDTO.getSettlementId())){
            // 结算银行账户
            Map<String, Object> bank_account_info = new HashMap<>();
            bank_account_info.put("bank_account_type", saveDTO.getBankAccountType());
            bank_account_info.put("account_name", encrypt(saveDTO.getAccountName()));
            bank_account_info.put("account_bank", saveDTO.getAccountBank());
            bank_account_info.put("bank_address_code", saveDTO.getBankAddressCode());
            bank_account_info.put("bank_name", saveDTO.getBankName());
            bank_account_info.put("account_number", encrypt(saveDTO.getAccountNumber()));
            map.put("bank_account_info", bank_account_info);
        }
        map.put("business_code", business_code);
        map.put("contact_info", contact_info);
        map.put("subject_info", subject_info);
        map.put("business_info", business_info);
        map.put("settlement_info", settlement_info);
        try {
            String str = HttpUrlUtil.sendPost(map);
            if(str.contains("applyment_id")){
                save(saveDTO);
            }
            System.out.println(str);
            return R.success(str);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Map<String, String>> statusQuery(ApplymentInfo saveDTO) {
        String str = HttpUrlUtil.statusQuery(saveDTO.getBusinessCode());
        System.out.println(str);
        return R.success(JsonUtils.stringToMap(str));
    }

    /**
     * 加密
     * @param text
     * @return
     * @throws Exception
     */
    public String encrypt(String text) throws Exception {
        return RsaCryptoUtil.encryptOAEP(text, verifier.getValidCertificate());
    }

}
