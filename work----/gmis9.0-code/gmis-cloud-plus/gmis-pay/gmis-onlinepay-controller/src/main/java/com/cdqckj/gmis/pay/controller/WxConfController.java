package com.cdqckj.gmis.pay.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.pay.dto.ApplymentInfoPageDTO;
import com.cdqckj.gmis.pay.dto.ApplymentInfoSaveDTO;
import com.cdqckj.gmis.pay.dto.ApplymentInfoUpdateDTO;
import com.cdqckj.gmis.pay.entity.ApplymentInfo;
import com.cdqckj.gmis.pay.service.ApplymentInfoService;
import com.cdqckj.gmis.pay.service.WxConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 微信配置部分接口
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wxConf")
@Api(value = "WxConf", tags = "微信配置")
//@PreAuth(replace = "wxPay:")
public class WxConfController extends SuperController<ApplymentInfoService, Long, ApplymentInfo, ApplymentInfoPageDTO, ApplymentInfoSaveDTO, ApplymentInfoUpdateDTO> {

    @Autowired
    private WxConfService wxConfService;
    @Autowired
    private ApplymentInfoService applymentInfoService;

    /**
     * 特约商户进件图片上传
     * @throws Exception
     */
    @ApiOperation(value = "特约商户进件图片上传", notes = "特约商户进件图片上传")
    @PostMapping("/uploadImage")
    @SysLog("特约商户进件图片上传")
    public R<String> uploadImage(@RequestParam(value = "file") MultipartFile simpleFile) throws Exception {
        return wxConfService.uploadImage(simpleFile);
    }

    /**
     * 下载证书
     * @throws Exception
     */
    @ApiOperation(value = "下载证书", notes = "下载证书")
    @PostMapping("/certDownload1")
    @SysLog("下载证书")
    public void certDownload1() throws Exception{
        wxConfService.certDownload();
    }

    /**
     * 特约商户进件：提交申请单
     * @param saveDTO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "特约商户进件：提交申请单", notes = "特约商户进件：提交申请单")
    @PostMapping("/submitApplication1")
    @SysLog("特约商户进件：提交申请单")
    public R<String> submitApplication1(@RequestParam ApplymentInfo saveDTO) throws Exception {
        return applymentInfoService.submitApplication(saveDTO);
    }
}
