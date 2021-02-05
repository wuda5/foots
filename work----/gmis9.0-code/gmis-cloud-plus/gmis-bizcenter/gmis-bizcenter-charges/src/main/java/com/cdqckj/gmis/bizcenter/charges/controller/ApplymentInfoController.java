package com.cdqckj.gmis.bizcenter.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.charges.service.ApplymentInfoService;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.pay.ApplyMentInfoApi;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.pay.dto.ApplymentInfoSaveDTO;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.ApplymentInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/wx/specialMerchant")
@Api(value = "specialMerchant", tags = "特约商户")
public class ApplymentInfoController {

    @Autowired
    public ApplyMentInfoApi applyMentInfoApi;
    @Autowired
    public ApplymentInfoService applymentInfoService;

    /**
     * 特约商户进件图片上传
     * @param simpleFile
     * @return
     */
    @ApiOperation(value = "特约商户进件图片上传")
    @PostMapping("/uploadImage")
    public R<Map<String, String>> uploadImage(@RequestParam(value = "file") MultipartFile simpleFile) throws Exception{
        return applymentInfoService.uploadFile(simpleFile);
    }

    /**
     * 下载证书
     * @throws Exception
     */
    @ApiOperation(value = "下载证书", notes = "下载证书")
    @PostMapping("/certDownload")
    public void certDownload() throws Exception{
        applyMentInfoApi.certDownload();
    }

    /**
     * 特约商户进件：提交申请单
     * @param saveDTO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "特约商户进件：提交申请单", notes = "特约商户进件：提交申请单")
    @PostMapping("/submitApplication")
    public R<String> submitApplication(@RequestBody ApplymentInfo saveDTO) throws Exception {
        return applyMentInfoApi.submitApplication(saveDTO);
    }

    /**
     *  通过业务申请编号查询申请状态
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "查询申请状态", notes = "查询申请状态")
    @PostMapping("/statusQuery")
    public R<Map<String, String>> statusQuery(@RequestBody ApplymentInfo saveDTO) {
        return applyMentInfoApi.statusQuery(saveDTO);
    }
}
