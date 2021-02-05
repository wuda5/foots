package com.cdqckj.gmis.pay.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.pay.dto.ApplymentInfoSaveDTO;
import com.cdqckj.gmis.pay.entity.ApplymentInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 业务接口
 * 特约商户进件申请信息
 * </p>
 *
 * @author gmis
 * @date 2020-11-17
 */
public interface ApplymentInfoService extends SuperService<ApplymentInfo> {
    /**
     * 图片上传
     * @return
     */
    R<Map<String, String>> uploadImage(MultipartFile simpleFile) throws Exception ;
    /**
     * 下载证书
     */
    void certDownload() throws Exception;

    /**
     * 特约商户进件：提交申请单
     * @param saveDTO
     * @return
     */
    R<String> submitApplication(@RequestParam ApplymentInfo saveDTO) throws Exception;

    /**
     * 通过业务申请编号查询申请状态
     * @param saveDTO
     * @return
     */
    R<Map<String, String>> statusQuery(@RequestBody ApplymentInfo saveDTO);
}
