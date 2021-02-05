package com.cdqckj.gmis.bizcenter.sys.config.service.Ipml;

import cn.hutool.json.JSONUtil;
import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.sys.config.service.InvoicePlatConfigService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.operateparam.InvoicePlatConfigBizApi;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigKeyStore;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoicePlatConfig;
import com.cdqckj.gmis.systemparam.enumeration.InvoicePlatEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author ASUS
 */
@Service
public class InvoicePlatConfigServiceImpl implements InvoicePlatConfigService {

    @Autowired
    AttachmentApi attachmentApi;

    @Autowired
    InvoicePlatConfigBizApi invoicePlatConfigBizApi;

    /**
     * 启用、禁用更新
     *
     * @param updateDTO
     * @return
     */
    @Override
    public R<InvoicePlatConfig> update(InvoicePlatConfigUpdateDTO updateDTO) {
        if (DataStatusEnum.NORMAL.getValue() == updateDTO.getDataStatus()
                && Boolean.FALSE.equals(checkData())) {
            return R.fail("只能启用一条配置");
        }
        return invoicePlatConfigBizApi.update(updateDTO);
    }

    /**
     * 保存配置（如果有文件同时上传文件到腾讯云）
     *
     * @param saveDTO
     * @param file
     * @param keyStoreAlias
     * @param keyStorePwd
     * @return
     */
    @Override
    public R<InvoicePlatConfig> saveWithFile(InvoicePlatConfigSaveDTO saveDTO, MultipartFile file, String keyStoreAlias, String keyStorePwd) {
        if (Boolean.FALSE.equals(checkData())) {
            return R.fail("已有生效的配置，请先禁用原配置");
        }
        if (InvoicePlatEnum.RUI_HONG.name().equals(saveDTO.getPlatCode())) {
            if (Objects.isNull(file) || StringUtils.isEmpty(keyStoreAlias) || StringUtils.isEmpty(keyStorePwd)) {
                throw new BizException("请上传证书文件，配置证书参数。");
            }
            R<AttachmentDTO> uploadResult = attachmentApi.upload(file, true, null, null, BizTypeEnum.KEYSTORE_FILE.getCode());
            AttachmentDTO uploadData = uploadResult.getData("上传证书失败");
            InvoicePlatConfigKeyStore keyStore = InvoicePlatConfigKeyStore.builder()
                    .appId(saveDTO.getOpenId())
                    .attachmentId(uploadData.getId())
                    .keyStoreName(uploadData.getSubmittedFileName())
                    .keyStorePath(uploadData.getUrl())
                    .keyStoreAlias(keyStoreAlias)
                    .keyStorePwd(keyStorePwd)
                    .build();
            saveDTO.setFileName(uploadData.getSubmittedFileName());
            saveDTO.setFileUrl(uploadData.getUrl());
            saveDTO.setKeyStore(keyStore);
        }

        return invoicePlatConfigBizApi.save(saveDTO);
    }

    /**
     * 更新配置（如果有文件同时上传文件到腾讯云）
     *
     * @param updateDTO
     * @param file
     * @param keyStoreAlias
     * @param keyStorePwd
     * @return
     */
    @Override
    public R<InvoicePlatConfig> updateWithFile(InvoicePlatConfigUpdateDTO updateDTO, MultipartFile file, String keyStoreAlias, String keyStorePwd) {

        if (InvoicePlatEnum.RUI_HONG.name().equals(updateDTO.getPlatCode())) {
            R<InvoicePlatConfig> configResult = invoicePlatConfigBizApi.get(updateDTO.getId());
            if (configResult.getIsError()) {
                throw new BizException("调用发票平台配置服务失败");
            }
            InvoicePlatConfig data = configResult.getData();
            InvoicePlatConfigKeyStore oldKeyStore = JSONUtil.toBean(data.getOtherParam(), InvoicePlatConfigKeyStore.class);

            InvoicePlatConfigKeyStore keyStore = InvoicePlatConfigKeyStore.builder()
                    .appId(StringUtils.isEmpty(updateDTO.getOpenId()) ? oldKeyStore.getAppId() : updateDTO.getOpenId())
                    .keyStoreAlias(StringUtils.isEmpty(keyStoreAlias) ? oldKeyStore.getKeyStoreAlias() : keyStoreAlias)
                    .keyStorePwd(StringUtils.isEmpty(keyStorePwd) ? oldKeyStore.getKeyStorePwd() : keyStorePwd)
                    .build();
            if (Objects.nonNull(file)) {
                R<AttachmentDTO> uploadResult = attachmentApi.upload(file, true, null, null, BizTypeEnum.KEYSTORE_FILE.getCode());
                AttachmentDTO uploadData = uploadResult.getData("上传证书失败");
                keyStore.setAttachmentId(uploadData.getId());
                keyStore.setKeyStoreName(uploadData.getSubmittedFileName());
                keyStore.setKeyStorePath(uploadData.getUrl());
                updateDTO.setFileName(uploadData.getSubmittedFileName());
                updateDTO.setFileUrl(uploadData.getUrl());
            } else {
                keyStore.setAttachmentId(oldKeyStore.getAttachmentId());
                keyStore.setKeyStorePath(oldKeyStore.getKeyStorePath());
                keyStore.setKeyStoreName(oldKeyStore.getKeyStoreName());
            }
            updateDTO.setOtherParam(JSONUtil.toJsonStr(keyStore));
        }
        return invoicePlatConfigBizApi.update(updateDTO);
    }

    /**
     * 检查是否已有生效的电子发票配置
     *
     * @return
     */
    private Boolean checkData() {
        R<InvoicePlatConfig> getResult = invoicePlatConfigBizApi.getOne();
        if (getResult.getIsError()) {
            throw new BizException("调用配置查询接口getOne异常");
        }
        return Objects.isNull(getResult.getData());
    }
}
