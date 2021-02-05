package com.cdqckj.gmis.pay.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.pay.dto.ApplymentInfoPageDTO;
import com.cdqckj.gmis.pay.dto.ApplymentInfoSaveDTO;
import com.cdqckj.gmis.pay.dto.ApplymentInfoUpdateDTO;
import com.cdqckj.gmis.pay.entity.ApplymentInfo;
import com.cdqckj.gmis.pay.service.ApplymentInfoService;
import com.cdqckj.gmis.pay.test.ApplymentBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * <p>
 * 前端控制器
 * 特约商户进件申请信息
 * </p>
 *
 * @author gmis
 * @date 2020-11-17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/applymentInfo")
@Api(value = "ApplymentInfo", tags = "特约商户进件申请信息")
//@PreAuth(replace = "applymentInfo:")
public class ApplymentInfoController extends SuperController<ApplymentInfoService, Long, ApplymentInfo, ApplymentInfoPageDTO, ApplymentInfoSaveDTO, ApplymentInfoUpdateDTO> {

    @Autowired
    private ApplymentInfoService applymentInfoService;

    /**
     * 特约商户进件图片上传
     * @throws Exception
     */
    @ApiOperation(value = "特约商户进件图片上传", notes = "特约商户进件图片上传")
    @PostMapping("/uploadFile")
    @SysLog("特约商户进件图片上传")
    public R<Map<String, String>> uploadFile(@RequestParam(value = "file") MultipartFile simpleFile) throws Exception {
        return applymentInfoService.uploadImage(simpleFile);
    }

    /**
     * 下载证书
     * @throws Exception
     */
    @ApiOperation(value = "下载证书", notes = "下载证书")
    @PostMapping("/certDownload")
    @SysLog("下载证书")
    public void certDownload() throws Exception{
        applymentInfoService.certDownload();
    }

    /**
     * 特约商户进件：提交申请单
     * @param saveDTO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "特约商户进件：提交申请单", notes = "特约商户进件：提交申请单")
    @PostMapping("/submitApplication")
    @SysLog("特约商户进件：提交申请单")
    public R<String> submitApplication(@RequestBody ApplymentInfo saveDTO) throws Exception {
        return applymentInfoService.submitApplication(saveDTO);
    }

    /**
     * 通过业务申请编号查询申请状态
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "通过业务申请编号查询申请状态", notes = "通过业务申请编号查询申请状态")
    @PostMapping("/statusQuery")
    @SysLog("通过业务申请编号查询申请状态")
    R<Map<String, String>> statusQuery(@RequestBody ApplymentInfo saveDTO){
        return applymentInfoService.statusQuery(saveDTO);
    };

}
