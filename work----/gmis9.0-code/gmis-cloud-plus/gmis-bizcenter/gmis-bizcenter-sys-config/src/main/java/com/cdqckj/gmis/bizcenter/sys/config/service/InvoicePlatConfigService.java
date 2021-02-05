package com.cdqckj.gmis.bizcenter.sys.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoicePlatConfig;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ASUS
 */
public interface InvoicePlatConfigService {

    /**
     * 启用、禁用更新
     *
     * @param saveDTO
     * @return
     */
    R<InvoicePlatConfig> update(InvoicePlatConfigUpdateDTO saveDTO);

    /**
     * 保存配置（如果有文件同时上传文件到腾讯云）
     *
     * @param saveDTO
     * @param file
     * @param keyStoreAlias
     * @param keyStorePwd
     * @return
     */
    R<InvoicePlatConfig> saveWithFile(InvoicePlatConfigSaveDTO saveDTO, MultipartFile file, String keyStoreAlias, String keyStorePwd);

    /**
     * 更新配置（如果有文件同时上传文件到腾讯云）
     *
     * @param updateDTO
     * @param file
     * @param keyStoreAlias
     * @param keyStorePwd
     * @return
     */
    R<InvoicePlatConfig> updateWithFile(InvoicePlatConfigUpdateDTO updateDTO, MultipartFile file, String keyStoreAlias, String keyStorePwd);
}
