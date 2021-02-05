package com.cdqckj.gmis.pay;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.pay.dto.ApplymentInfoSaveDTO;
import com.cdqckj.gmis.pay.entity.ApplymentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(name = "${gmis.feign.onlinepay-server:gmis-onlinepay-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/applymentInfo", qualifier = "applyMentInfoApi")
public interface ApplyMentInfoApi {
    /**
     * 特约商户进件图片上传
     * @param simpleFile
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<Map<String, String>> uploadFile(@RequestPart(value = "file") MultipartFile simpleFile) throws Exception;
    /**
     * 下载证书
     */

    @RequestMapping(value = "/certDownload", method = RequestMethod.POST)
    void certDownload() throws Exception;

    /**
     * 特约商户进件：提交申请单
     * @param saveDTO
     * @return
     */
    @RequestMapping(value = "/submitApplication", method = RequestMethod.POST)
    R<String> submitApplication(@RequestBody ApplymentInfo saveDTO);

    /**
     * 通过业务申请编号查询申请状态
     * @param saveDTO
     * @return
     */
    @RequestMapping(value = "/statusQuery", method = RequestMethod.POST)
    R<Map<String, String>> statusQuery(@RequestBody ApplymentInfo saveDTO);
}
