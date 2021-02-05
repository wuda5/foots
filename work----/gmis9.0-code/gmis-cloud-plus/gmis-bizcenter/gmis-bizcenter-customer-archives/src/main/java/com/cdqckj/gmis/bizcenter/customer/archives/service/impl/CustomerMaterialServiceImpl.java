package com.cdqckj.gmis.bizcenter.customer.archives.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.archive.CustomerMaterialBizApi;
import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.customer.archives.service.CustomerMaterialService;
import com.cdqckj.gmis.bizcenter.customer.archives.vo.ImgVO;
import com.cdqckj.gmis.bizcenter.customer.archives.vo.UserMaterialVO;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialSaveDTO;
import com.cdqckj.gmis.userarchive.entity.CustomerMaterial;
import com.cdqckj.gmis.utils.I18nUtil;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerMaterialServiceImpl extends SuperCenterServiceImpl implements CustomerMaterialService {

    @Autowired
    private I18nUtil i18nUtil;
    @Autowired
    private AttachmentApi attachmentApi;
    @Autowired
    CustomerMaterialBizApi customerMaterialBizApi;

    /**
     * 上传用户资料
     *
     * @param materialVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ImgVO> uploadMaterial(UserMaterialVO materialVO) {
        List<ImgVO> files = materialVO.getImgList();
        List<CustomerMaterialSaveDTO> customerMaterials = new ArrayList<>();
        if (CollectionUtil.isEmpty(files)) {
            return files;
        }
        files.forEach(file -> {
            String base64Str = file.getUrl();
            if (Objects.isNull(file.getId()) && Objects.nonNull(base64Str)) {
                CustomerMaterialSaveDTO customerMaterial = new CustomerMaterialSaveDTO();
                String fileName = StrUtil.uuid();
                String extension;
                if (base64Str.indexOf("data:image/jpeg;") != -1) {
                    base64Str = base64Str.replace("data:image/jpeg;base64,", "");
                    extension = "jpeg";
                    fileName += ".jpeg";
                } else if (base64Str.indexOf("data:image/png;") != -1) {
                    base64Str = base64Str.replace("data:image/png;base64,", "");
                    extension = "png";
                    fileName += ".png";
                } else if (base64Str.indexOf("data:image/gif;") != -1) {
                    base64Str = base64Str.replace("data:image/gif;base64,", "");
                    extension = "gif";
                    fileName += ".gif";
                } else {
                    throw new BizException("请上传图片！");
                }
                byte[] fileBytes = Base64.getDecoder().decode(base64Str);
                InputStream inputStream = new ByteArrayInputStream(fileBytes);
                MultipartFile multipartFile = null;
                try {
                    multipartFile = new MockMultipartFile(fileName, fileName, ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BizException("base64转文件失败！");
                }
                AttachmentDTO uploadResult = attachmentApi.upload(multipartFile, true, null, null, BizTypeEnum.CUSTOMER_MATERIAL.getCode()).getData();

                customerMaterial.setCustomerCode(materialVO.getCustomerCode());
                customerMaterial.setGasMeterCode(materialVO.getGasMeterCode());
                customerMaterial.setGasMeterNumber(materialVO.getMeterNumber());
                customerMaterial.setMaterialType(file.getType());
                customerMaterial.setMaterialFileName(fileName);
                customerMaterial.setMaterialFileExtension(extension);
                customerMaterial.setMaterialImageUrl(uploadResult.getUrl());
                customerMaterial.setDataStatus(DataStatusEnum.NORMAL.getValue());
                customerMaterial.setMaterialAttachmentId(uploadResult.getId());
                customerMaterials.add(customerMaterial);
            }
        });
        customerMaterialBizApi.saveList(customerMaterials);
        return query(materialVO);
    }

    /**
     * 查询用户的资料档案
     *
     * @param query
     * @return
     */
    @Override
    public List<ImgVO> query(UserMaterialVO query) {
        CustomerMaterial customerMaterial = CustomerMaterial.builder()
                .gasMeterCode(query.getGasMeterCode())
                .customerCode(query.getCustomerCode())
                .gasMeterNumber(query.getMeterNumber())
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .build();
        List<CustomerMaterial> data = customerMaterialBizApi.query(customerMaterial).getData();
        return convertToImages(data);
    }

    /**
     * 将数据库对象装换页面对象
     *
     * @param customerMaterialList 档案列表
     */
    private List<ImgVO> convertToImages(List<CustomerMaterial> customerMaterialList) {
        List<ImgVO> images = new ArrayList<>();
        customerMaterialList.forEach(temp -> {
            ImgVO imgVO = new ImgVO();
            imgVO.setId(temp.getId());
            imgVO.setType(temp.getMaterialType());
            imgVO.setUrl(temp.getMaterialImageUrl());
            images.add(imgVO);
        });
        return images;
    }
}