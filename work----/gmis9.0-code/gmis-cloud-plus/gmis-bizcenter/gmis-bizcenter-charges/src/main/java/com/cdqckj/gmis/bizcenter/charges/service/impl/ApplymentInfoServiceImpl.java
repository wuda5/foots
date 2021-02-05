package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.charges.service.ApplymentInfoService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeService;
import com.cdqckj.gmis.bizcenter.charges.service.OnlinePayService;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.pay.ApplyMentInfoApi;
import com.cdqckj.gmis.pay.PayBizApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 *
 */
@Service
@Log4j2
public class ApplymentInfoServiceImpl extends SuperCenterServiceImpl implements ApplymentInfoService {

    @Autowired
    public ApplyMentInfoApi applyMentInfoApi;
    @Autowired
    public AttachmentApi attachmentApi;

    @Override
    public R<Map<String, String>> uploadFile(MultipartFile simpleFile) throws Exception {
        //服务器保存原件并返回id和url（后续会将原件打包交给支付通）
        R<AttachmentDTO> result = attachmentApi.upload(simpleFile,true,null, null, BizTypeEnum.KEYSTORE_FILE.getCode());
        if(result.getIsSuccess()){
            AttachmentDTO attachment = result.getData();
            Map<String, String> map = applyMentInfoApi.uploadFile(simpleFile).getData();
            map.put("id",attachment.getId().toString());
            map.put("url",attachment.getUrl());
            return R.success(map);
        }
        return R.fail("");
    }
}
