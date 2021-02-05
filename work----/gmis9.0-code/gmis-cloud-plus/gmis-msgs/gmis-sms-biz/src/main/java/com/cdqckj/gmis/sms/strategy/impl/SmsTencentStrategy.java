package com.cdqckj.gmis.sms.strategy.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.cdqckj.gmis.sms.dto.SignSaveDTO;
import com.cdqckj.gmis.sms.dto.SignUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.cdqckj.gmis.sms.enumeration.ExamineStatus;
import com.cdqckj.gmis.sms.properties.SmsServerProperties;
import com.cdqckj.gmis.sms.strategy.domain.SmsDO;
import com.cdqckj.gmis.sms.strategy.domain.SmsResult;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.cdqckj.gmis.sms.enumeration.ProviderType;
import com.tencentcloudapi.sms.v20190711.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 腾讯发送短信实现类
 *
 * @author gmis
 * @date 2018/12/20
 */
@Component("TENCENT")
@Slf4j
public class SmsTencentStrategy extends AbstractSmsStrategy {

    private final static Map<String, String> ERROR_CODE_MAP = new HashMap<>();

    @Autowired
    protected SmsServerProperties smsServerProperties;

    static {
        ERROR_CODE_MAP.put("1001", "sig 校验失败");
        ERROR_CODE_MAP.put("1002", "短信/语音内容中含有敏感词");
        ERROR_CODE_MAP.put("1003", "请求包体没有 sig 字段或 sig 为空");
        ERROR_CODE_MAP.put("1004", "请求包解析失败，通常情况下是由于没有遵守 API 接口说明规范导致的");
        ERROR_CODE_MAP.put("1006", "请求没有权限");
        ERROR_CODE_MAP.put("1007", "其他错误");
        ERROR_CODE_MAP.put("1008", "请求下发短信/语音超时");
        ERROR_CODE_MAP.put("1009", "请求 IP 不在白名单中");
        ERROR_CODE_MAP.put("1011", "不存在该 REST API 接口");
        ERROR_CODE_MAP.put("1012", "签名格式错误或者签名未审批");
        ERROR_CODE_MAP.put("1013", "下发短信/语音命中了频率限制策略");
        ERROR_CODE_MAP.put("1014", "模版未审批或请求的内容与审核通过的模版内容不匹配");
        ERROR_CODE_MAP.put("1015", "手机号在黑名单库中，通常是用户退订或者命中运营商黑名单导致的");
        ERROR_CODE_MAP.put("1016", "手机号格式错误");
        ERROR_CODE_MAP.put("1017", "请求的短信内容太长");
        ERROR_CODE_MAP.put("1018", "语音验证码格式错误");
        ERROR_CODE_MAP.put("1019", "sdkappid 不存在");
        ERROR_CODE_MAP.put("1020", "sdkappid 已禁用");
        ERROR_CODE_MAP.put("1021", "请求发起时间不正常，通常是由于您的服务器时间与腾讯云服务器时间差异超过10分钟导致的");
        ERROR_CODE_MAP.put("1022", "业务短信日下发条数超过设定的上限");
        ERROR_CODE_MAP.put("1023", "单个手机号30秒内下发短信条数超过设定的上限");
        ERROR_CODE_MAP.put("1024", "单个手机号1小时内下发短信条数超过设定的上限");
        ERROR_CODE_MAP.put("1025", "单个手机号日下发短信条数超过设定的上限");
        ERROR_CODE_MAP.put("1025", "单个手机号下发相同内容超过设定的上限");
        ERROR_CODE_MAP.put("1029", "营销短信发送时间限制");
        ERROR_CODE_MAP.put("1030", "不支持该请求");
        ERROR_CODE_MAP.put("1031", "套餐包余量不足");
        ERROR_CODE_MAP.put("1032", "个人用户没有发营销短信的权限");
        ERROR_CODE_MAP.put("1033", "欠费被停止服务");
        ERROR_CODE_MAP.put("1034", "群发请求里既有国内手机号也有国际手机号");
        ERROR_CODE_MAP.put("1036", "单个模板变量字符数超过12个");
        ERROR_CODE_MAP.put("1045", "不支持该地区短信下发");
        ERROR_CODE_MAP.put("1046", "调用群发 API 接口单次提交的手机号个数超过200个");
        ERROR_CODE_MAP.put("1047", "国际短信日下发条数被限制");
        ERROR_CODE_MAP.put("60008", "处理请求超时");
    }

    @Override
    protected SmsResult send(SmsDO smsDO) {
        try {
            //初始化单发
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            SmsSingleSender singleSender = new SmsSingleSender(Convert.toInt(tencent.getAppId(), 0), tencent.getAppSecret());
            String paramStr = smsDO.getTemplateParams();

            JSONObject param = JSONObject.parseObject(paramStr, Feature.OrderedField);

            Set<Map.Entry<String, Object>> sets = param.entrySet();

            ArrayList<String> paramList = new ArrayList<>();
            for (Map.Entry<String, Object> val : sets) {
                paramList.add(val.getValue().toString());
            }
            SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam("86", smsDO.getPhone(),
                    Convert.toInt(smsDO.getTemplateCode()), paramList, smsDO.getSignName(), "", "");
            log.info("tencent result={}", singleSenderResult.toString());
            return SmsResult.build(ProviderType.TENCENT, String.valueOf(singleSenderResult.result),
                    singleSenderResult.sid, singleSenderResult.ext,
                    ERROR_CODE_MAP.getOrDefault(String.valueOf(singleSenderResult.result), singleSenderResult.errMsg), singleSenderResult.fee);
        } catch (Exception e) {
            log.error(e.getMessage());
            return SmsResult.fail(e.getMessage());
        }
    }

    /**
     * 添加签名到腾讯短信服务
     * @param signSaveDTO
     * @param str
     */
    @Override
    public void saveSign(SignSaveDTO signSaveDTO, String str) {
        try{
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String signParams = "{\"SignName\":\"ReplaceName\",\"SignType\":ReplaceType,\"DocumentType\":ReplaceDocument," +
                    "\"International\":ReplaceInter,\"UsedMethod\":ReplaceMethod,\"ProofImage\":\"ReplaceImage\"}";
            SmsClient client = getSmsClient(tencent);
            signParams = signParams.replace("ReplaceName",signSaveDTO.getSignName())
                    .replace("ReplaceType",signSaveDTO.getSignType().toString())
                    .replace("ReplaceDocument",signSaveDTO.getDocumentType().toString())
                    .replace("ReplaceInter",signSaveDTO.getInternational().toString())
                    .replace("ReplaceMethod",signSaveDTO.getUsedMethod().toString())
                    .replace("ReplaceImage",str);
            AddSmsSignRequest req = AddSmsSignRequest.fromJsonString(signParams, AddSmsSignRequest.class);
            AddSmsSignResponse resp = client.AddSmsSign(req);
            AddSignStatus signStatus = resp.getAddSignStatus();
            signSaveDTO.setSignId(signStatus.getSignId());
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 修改短信签名
     * @param signUpdateDTO
     * @param str
     * @return
     */
    @Override
    public String updateSign(SignUpdateDTO signUpdateDTO,String str) {
        try{
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String signParams = "{\"SignId\":ReplaceId,\"SignName\":\"ReplaceName\",\"SignType\":ReplaceType,\"DocumentType\":ReplaceDocument," +
                    "\"International\":ReplaceInter,\"UsedMethod\":ReplaceMethod,\"ProofImage\":\"ReplaceImage\"}";
            SmsClient client = getSmsClient(tencent);
            signParams = signParams.replace("ReplaceId",signUpdateDTO.getSignId().toString())
                    .replace("ReplaceName",signUpdateDTO.getSignName())
                    .replace("ReplaceType",signUpdateDTO.getSignType().toString())
                    .replace("ReplaceDocument",signUpdateDTO.getDocumentType().toString())
                    .replace("ReplaceInter",signUpdateDTO.getInternational().toString())
                    .replace("ReplaceMethod",signUpdateDTO.getUsedMethod().toString())
                    .replace("ReplaceImage",str);
            ModifySmsSignRequest req = ModifySmsSignRequest.fromJsonString(signParams, ModifySmsSignRequest.class);
            ModifySmsSignResponse resp = client.ModifySmsSign(req);
            ModifySignStatus modifySignStatus = resp.getModifySignStatus();
            return null;
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * 获取短信签名审核状态
     * @param sign
     */
    @Override
    public void getStstus(Sign sign) {
        try{
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String signParams = "{\"SignIdSet\":[replaceSignId],\"International\":replaceIntern}";
            SmsClient client = getSmsClient(tencent);
            signParams = signParams.replace("replaceSignId",sign.getSignId().toString())
                    .replace("replaceIntern",sign.getInternational().toString());
            DescribeSmsSignListRequest req = DescribeSmsSignListRequest.fromJsonString(signParams, DescribeSmsSignListRequest.class);
            DescribeSmsSignListResponse resp = client.DescribeSmsSignList(req);
            DescribeSignListStatus describeSignListStatus = resp.getDescribeSignListStatusSet()[0];
            sign.setSignStatus(describeSignListStatus.getStatusCode().intValue());
            sign.setReviewReply(describeSignListStatus.getReviewReply());
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 新增短信模板
     * @param smsTemplate
     */
    @Override
    public void saveTemplate(SmsTemplate smsTemplate) {
        try{
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String saveParams = "{\"TemplateName\":\"replaceName\",\"TemplateContent\":\"replaceContent\"," +
                    "\"SmsType\":replaceType,\"International\":replaceInterna,\"Remark\":\"replaceRemark\"}";
            SmsClient client = getSmsClient(tencent);
            saveParams = saveParams.replace("replaceName",smsTemplate.getName())
                    .replace("replaceContent",smsTemplate.getContent())
                    .replace("replaceRemark",smsTemplate.getTemplateDescribe())
                    .replace("replaceType",smsTemplate.getSmsType().toString())
                    .replace("replaceInterna",smsTemplate.getInternatType().toString());
            AddSmsTemplateRequest req = AddSmsTemplateRequest.fromJsonString(saveParams, AddSmsTemplateRequest.class);
            AddSmsTemplateResponse resp = client.AddSmsTemplate(req);
            AddTemplateStatus addTemplateStatus = resp.getAddTemplateStatus();
            String templateId = addTemplateStatus.getTemplateId();
            smsTemplate.setId(Long.valueOf(templateId));
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 修改短信模板
     * @param smsTemplate
     * @return
     */
    @Override
    public String updateTemplate(SmsTemplate smsTemplate) {
        try{
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String updateParams = "{\"TemplateId\":replaceId,\"TemplateName\":\"replaceName\",\"TemplateContent\":\"replaceContent\"," +
                    "\"SmsType\":replaceType,\"International\":replaceInterna,\"Remark\":\"replaceRemark\"}";
            SmsClient client = getSmsClient(tencent);
            updateParams = updateParams.replace("replaceId",smsTemplate.getId().toString())
                    .replace("replaceName",smsTemplate.getName())
                    .replace("replaceContent",smsTemplate.getContent())
                    .replace("replaceRemark",smsTemplate.getTemplateDescribe())
                    .replace("replaceType",smsTemplate.getSmsType().toString())
                    .replace("replaceInterna",smsTemplate.getInternatType().toString());
            ModifySmsTemplateRequest req = ModifySmsTemplateRequest.fromJsonString(updateParams, ModifySmsTemplateRequest.class);
            ModifySmsTemplateResponse resp = client.ModifySmsTemplate(req);
            return null;
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * 获取短信模板审核状态
     * @param list
     */
    @Override
    public void getTemplateStstus(List<SmsTemplate> list) {
        try{
            Map<Long,SmsTemplate> map = list.stream().collect(Collectors.toMap(SmsTemplate::getId, smsTemplate -> smsTemplate));
            List<Long> idsList = list.stream().map(SmsTemplate::getId).collect(Collectors.toList());
            String ids = idsList.toString();//StringUtils.join(idsList,",");
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String queryParams = "{\"TemplateIdSet\":replaceId,\"International\":replaceInterna}";
            SmsClient client = getSmsClient(tencent);
            queryParams = queryParams.replace("replaceId",ids)
                    .replace("replaceInterna",list.get(0).getInternatType().toString());
            DescribeSmsTemplateListRequest req = DescribeSmsTemplateListRequest.fromJsonString(queryParams, DescribeSmsTemplateListRequest.class);
            DescribeSmsTemplateListResponse resp = client.DescribeSmsTemplateList(req);
            DescribeTemplateListStatus[] describeTemplateListStatuses = resp.getDescribeTemplateStatusSet();
            for(DescribeTemplateListStatus status:describeTemplateListStatuses){
                Integer sta = status.getStatusCode().intValue();
                if(sta!= ExamineStatus.UNDER_REVIEW.getCode()){
                    map.get(status.getTemplateId()).setExamineStatus(sta);
                    map.get(status.getTemplateId()).setReviewReply(status.getReviewReply());
                }
            }
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 短信模板腾讯云物理删除
     * @param id
     */
    public void deleteTemplate(Long id){
        try{
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String signParams = "{\"TemplateId\":replaceId}";
            SmsClient client = getSmsClient(tencent);
            signParams = signParams.replace("replaceId",id.toString());
            DeleteSmsTemplateRequest req = DeleteSmsTemplateRequest.fromJsonString(signParams, DeleteSmsTemplateRequest.class);
            DeleteSmsTemplateResponse resp = client.DeleteSmsTemplate(req);
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 短信签名腾讯云物理删除
     * @param signId
     */
    public void deleteSign(Long signId){
        try{
            SmsServerProperties.TencentSms tencent = smsServerProperties.getTencent();
            String signParams = "{\"SignId\":replaceSignId}";
            SmsClient client = getSmsClient(tencent);
            signParams = signParams.replace("replaceSignId",signId.toString());
            DeleteSmsSignRequest req = DeleteSmsSignRequest.fromJsonString(signParams, DeleteSmsSignRequest.class);
            DeleteSmsSignResponse resp = client.DeleteSmsSign(req);
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取短信服务客户端
     * @param tencent
     * @return
     */
    private SmsClient getSmsClient(SmsServerProperties.TencentSms tencent) {
        Credential cred = new Credential(tencent.getSecretId(), tencent.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, tencent.getReginName(), clientProfile);
    }
}
