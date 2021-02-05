package com.cdqckj.gmis.pay.test;

import com.cdqckj.gmis.pay.elias.util.HttpUrlUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApplymentBo {
    public void exe(String contactName, String contactIdNum, String contactMobile, String contactMail, String microName, String microAddressCode,
                    String microAddress, String idCardCopy, String idCardNational, String cardPeriodBegin, String cardPeriodEnd, String servicePhone,
                    String accountBank, String bankAddressCode, String bankName, String accountNumber) throws Exception {
        // 超级管理员信息
        Map<String, Object> contact_info = new HashMap<String, Object>();
        String contact_name = new RsaCryptoTest().encryptTest(contactName); // 超级管理员姓名
        String contact_id_number = new RsaCryptoTest().encryptTest(contactIdNum); // 超级管理员身份证件号码
        String mobile_phone = new RsaCryptoTest().encryptTest(contactMobile);// 联系手机
        String contact_email = new RsaCryptoTest().encryptTest(contactMail);// 联系邮箱
        contact_info.put("contact_name", contact_name);
        contact_info.put("contact_id_number", contact_id_number);
        contact_info.put("mobile_phone", mobile_phone);
        contact_info.put("contact_email", contact_email);
        // 主体资料
        String subject_type = "SUBJECT_TYPE_ENTERPRISE"; // 主体类型
        String micro_biz_type = "MICRO_TYPE_STORE"; // 小微经营类型
        String micro_name = microName; // 门店名称
        String micro_address_code = microAddressCode; // 门店省市编码
        String micro_address = microAddress; // 门店街道名称
        String store_entrance_pic = "cBvjxDBQSO-p80nXq743_FkwctfQvnHw7Q9K8_1zxmfZDXEn_76mSwRHjjzpGMho77przbzu0rEKezcefKqD9eHMcskPTbqqgiiQEKbn4Qw"; // 门店门口照片
        String micro_indoor_copy = "cBvjxDBQSO-p80nXq743_FkwctfQvnHw7Q9K8_1zxmfZDXEn_76mSwRHjjzpGMho77przbzu0rEKezcefKqD9eHMcskPTbqqgiiQEKbn4Qw"; // 店内环境照片
        // 证件类型，IDENTIFICATION_TYPE_IDCARD
        String id_doc_type = "IDENTIFICATION_TYPE_IDCARD";
        String id_card_name = new RsaCryptoTest().encryptTest(contactName); // 身份证姓名
        String id_card_number = new RsaCryptoTest().encryptTest(contactIdNum); // 身份证号码
        Map<String, Object> subject_info = new HashMap<String, Object>(); // 主体资料
        Map<String, String> business_license_info = new HashMap<>();//营业执照
        business_license_info.put("license_copy", "0-GR6C8ZAAWHw3e93tUlwLD_Dv03SWpWaYDBHmXf8h9xw0BlmcoRnrUesP49B6JOwrSqYhYGnKGmnxpCP7C28HDXS61ZsIY8kZxYW57julc");//营业执照照片
        business_license_info.put("license_number", "123456789012345678");//注册号/统一社会信用代码
        business_license_info.put("merchant_name", "科技有限公司");//商户名称
        business_license_info.put("legal_person", "测试人");//个体户经营者/法人姓名
        Map<String, Object> organization_info = new HashMap<>();//组织机构代码证
        organization_info.put("organization_copy", "0-GR6C8ZAAWHw3e93tUlwLD_Dv03SWpWaYDBHmXf8h9xw0BlmcoRnrUesP49B6JOwrSqYhYGnKGmnxpCP7C28HDXS61ZsIY8kZxYW57julc");
        organization_info.put("organization_code", "91510107MA61WA6P3J");
        organization_info.put("org_period_begin", "2019-08-01");
        organization_info.put("org_period_end", "长期");
        Map<String, Object> identity_info = new HashMap<String, Object>(); // 经营者身份证信息

        //Map<String, Object> micro_biz_info = new HashMap<String, Object>(); // 小微商户辅助材料
        //Map<String, Object> micro_store_info = new HashMap<String, Object>(); // 门店场所信息

        Map<String, Object> id_card_info = new HashMap<String, Object>(); // 身份证信息
        id_card_info.put("id_card_copy", idCardCopy);
        id_card_info.put("id_card_national", idCardNational);
        id_card_info.put("id_card_name", id_card_name);
        id_card_info.put("id_card_number", id_card_number);
        id_card_info.put("card_period_begin", cardPeriodBegin);
        id_card_info.put("card_period_end", cardPeriodEnd);
        identity_info.put("id_doc_type", id_doc_type);
        identity_info.put("id_card_info", id_card_info);
        identity_info.put("owner", true);
        //micro_store_info.put("micro_name", micro_name);
        //micro_store_info.put("micro_address_code", micro_address_code);
        //micro_store_info.put("micro_address", micro_address);
        //micro_store_info.put("store_entrance_pic", store_entrance_pic);
        //micro_store_info.put("micro_indoor_copy", micro_indoor_copy);
        //micro_biz_info.put("micro_biz_type", micro_biz_type);
        //micro_biz_info.put("micro_store_info", micro_store_info);





        subject_info.put("subject_type", subject_type);
        subject_info.put("business_license_info", business_license_info);
        //subject_info.put("organization_info", organization_info);

        //subject_info.put("micro_biz_info", micro_biz_info);
        subject_info.put("identity_info", identity_info);
        Map<String, Object> business_info = new HashMap<String, Object>();
        Map<String, Object> sales_info = new HashMap<String, Object>();//经营场景
        String scenesType[] = {"SALES_SCENES_STORE"};
        sales_info.put("sales_scenes_type", scenesType);
        Map<String, Object> biz_store_info = new HashMap<>();//线下门店场景
        biz_store_info.put("biz_store_name", "门店名");//门店名称
        biz_store_info.put("biz_address_code", "440305");//门店省市编码
        biz_store_info.put("biz_store_address", "南山区xx大厦x层xxxx室");//门店地址
        String entrancePic[] = {"0-GR6C8ZAAWHw3e93tUlwLD_Dv03SWpWaYDBHmXf8h9xw0BlmcoRnrUesP49B6JOwrSqYhYGnKGmnxpCP7C28HDXS61ZsIY8kZxYW57julc"};
        biz_store_info.put("store_entrance_pic", entrancePic);//门店门头照片
        String indooric[] = {"0-GR6C8ZAAWHw3e93tUlwLD_Dv03SWpWaYDBHmXf8h9xw0BlmcoRnrUesP49B6JOwrSqYhYGnKGmnxpCP7C28HDXS61ZsIY8kZxYW57julc"};
        biz_store_info.put("indoor_pic", indooric);//店内环境照片
        biz_store_info.put("biz_sub_appid", "wx5408d1ddde603ba9");//线下场所对应的商家APPID
        sales_info.put("biz_store_info", biz_store_info);
        business_info.put("merchant_shortname", microName);
        business_info.put("service_phone", servicePhone);
        business_info.put("sales_info", sales_info);

        String settlement_id = "730";//
        String qualification_type = "公共事业（水电煤气）"; // 所属行业;请填写所属行业名称，建议参见《费率结算规则对照表》 示例值：餐饮
        Map<String, Object> settlement_info = new HashMap<String, Object>();
        settlement_info.put("settlement_id", settlement_id);
        settlement_info.put("qualification_type", qualification_type);

        // 收款银行卡
        // 账户类型 若主体为小微，可填写：经营者个人银行卡 枚举值：
        // BANK_ACCOUNT_TYPE_PERSONAL：经营者个人银行卡
        // 示例值：BANK_ACCOUNT_TYPE_CORPORATE
        String bank_account_type = "BANK_ACCOUNT_TYPE_CORPORATE";

        String account_name = "科技有限公司"; // 开户名称（该字段需进行加密处理）

        String account_number = new RsaCryptoTest().encryptTest(accountNumber); // 银行账号（该字段需进行加密处理）
        Map<String, Object> bank_account_info = new HashMap<String, Object>();
        bank_account_info.put("bank_account_type", bank_account_type);
        bank_account_info.put("account_name", new RsaCryptoTest().encryptTest(account_name));
        bank_account_info.put("account_bank", accountBank);
        bank_account_info.put("bank_address_code", bankAddressCode);
        bank_account_info.put("bank_name", bankName);
        bank_account_info.put("account_number", account_number);

        String business_code = UUID.randomUUID().toString().replaceAll("-",""); // 申请单号
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("business_code", business_code);
        map.put("contact_info", contact_info);
        map.put("subject_info", subject_info);
        map.put("business_info", business_info);
        map.put("settlement_info", settlement_info);
        map.put("bank_account_info", bank_account_info);
        try {
            String body = JSONObject.fromObject(map).toString();
            //String str = HttpUrlUtil.sendPost(body);
            String str = HttpUrlUtil.sendPost(map);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCertDownload() {
        String contactName = "测试人"; // 超级管理员姓名
        String contactIdNum = "51092219910420319X"; // 身份证号码
        String contactMobile = "13515489653"; // 手机号码
        String contactMail = "548415548@qq.com"; // 联系邮箱
        String microName = "联运公司停车场"; // 门店名称（也用于简称）

        // 门店省市编码
        // 参考：https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/ecommerce/applyments/chapter4_1.shtml
        String microAddressCode = "520200";
        String microAddress = "成龙路52号"; // 门店地址

        String idCardCopy = "cBvjxDBQSO-p80nXq743_FkwctfQvnHw7Q9K8_1zxmfZDXEn_76mSwRHjjzpGMho77przbzu0rEKezcefKqD9eHMcskPTbqqgiiQEKbn4Qw"; // 身份证正面照
        String idCardNational = "cBvjxDBQSO-p80nXq743_FkwctfQvnHw7Q9K8_1zxmfZDXEn_76mSwRHjjzpGMho77przbzu0rEKezcefKqD9eHMcskPTbqqgiiQEKbn4Qw"; // 身份证反面照

        String cardPeriodBegin = "2007-08-10"; // 身份证有效期开始时间2011-06-24
        String cardPeriodEnd = "2027-08-10";// 身份证有效期结束时间2011-06-24
        String servicePhone = "13515489653";// 客服电话； 无特殊使用管理员手机号码
        String accountBank = "其他银行"; // 开户银行
        String bankAddressCode = "510100"; // 开户银行省市编码至少精确到市，详细参见《省市区编号对照表》 示例值：110000；

        // 1、“开户银行”为17家直连银行无需填写
        // 2、“开户银行”为其他银行，则开户银行全称（含支行）和开户银行联行号二选一
        // 3、需填写银行全称，如"深圳农村商业银行XXX支行"，详细参见《开户银行全称（含支行）对照表》
        // 示例值：施秉县农村信用合作联社城关信用社
        String bankName = "贵阳银行股份有限公司成都分行"; // 开户银行全称（含支行]
        String accountNumber = "21010120030013668"; // 银行账号

        //ApplymentBo t = new ApplymentBo();
        try {
            exe(contactName, contactIdNum, contactMobile, contactMail, microName, microAddressCode, microAddress, idCardCopy, idCardNational,
                    cardPeriodBegin, cardPeriodEnd, servicePhone, accountBank, bankAddressCode, bankName, accountNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
