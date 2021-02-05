package com.cdqckj.gmis.sms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.sms.dto.SignPageDTO;
import com.cdqckj.gmis.sms.dto.SignSaveDTO;
import com.cdqckj.gmis.sms.dto.SignUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.enumeration.ExamineStatus;
import com.cdqckj.gmis.sms.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 短信签名
 * </p>
 *
 * @author gmis
 * @date 2020-08-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sign")
@Api(value = "Sign", tags = "短信签名")
@PreAuth(replace = "sign:")
public class SignController extends SuperController<SignService, Long, Sign, SignPageDTO, SignSaveDTO, SignUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Sign> signList = list.stream().map((map) -> {
            Sign sign = Sign.builder().build();
            //TODO 请在这里完成转换
            return sign;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(signList));
    }

    /**
     * 添加签名
     * @param
     * @return
     */
    @ApiOperation(value = "添加签名", notes = "添加签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
            @ApiImplicitParam(name = "signName", value = "签名内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "signType", value = "签名类型 0：公司", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "usedMethod", value = "签名用途： 0：自用。 1：他用。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "documentType", value = "证明类型：0：三证合一。 1：企业营业执照。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "international", value = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信", dataType = "long", paramType = "query"),
    })
    @PostMapping("/saveSign")
    @SysLog("添加签名")
    public R<Boolean> saveSign(@RequestParam(value = "file") MultipartFile file,
                               @RequestParam(value = "signName", required = true) String signName,
                               @RequestParam(value = "signType", required = true) Integer signType,
                               @RequestParam(value = "usedMethod", required = true) Integer usedMethod,
                               @RequestParam(value = "documentType", required = true) Integer documentType,
                               @RequestParam(value = "international", required = true) Integer international) throws IOException {
        SignSaveDTO signSaveDTO = new SignSaveDTO(signName,signType,documentType,international,usedMethod);
        String picStr = fileToBase64Str(file);
        Boolean bool = baseService.saveSign(signSaveDTO,picStr);
        return bool? success():R.fail("添加失败");
    }

    /**
     * 修改签名
     * @param file
     * @param id
     * @param signName
     * @param signType
     * @param usedMethod
     * @param documentType
     * @param international
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "修改签名", notes = "修改签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
            @ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "signName", value = "签名内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "signType", value = "签名类型 0：公司", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "usedMethod", value = "签名用途： 0：自用。 1：他用。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "documentType", value = "证明类型：0：三证合一。 1：企业营业执照。", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "international", value = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信", dataType = "long", paramType = "query"),
    })
    @PostMapping("/updateSign")
    @SysLog("修改签名")
    public R<Boolean> updateSign(@RequestParam(value = "file") MultipartFile file,
                               @RequestParam(value = "id", required = true) long id,
                               @RequestParam(value = "signName", required = true) String signName,
                               @RequestParam(value = "signType", required = true) Integer signType,
                               @RequestParam(value = "usedMethod", required = true) Integer usedMethod,
                               @RequestParam(value = "documentType", required = true) Integer documentType,
                               @RequestParam(value = "international", required = true) Integer international) throws IOException {
        SignUpdateDTO signUpdateDTO = new SignUpdateDTO(id,signName,signType,documentType,international,usedMethod);
        String picStr = fileToBase64Str(file);
        String mess = baseService.updateSign(signUpdateDTO,picStr);
        return mess==null? R.successDef():R.fail(mess);
    }

    /**
     * 查询签名
     * @return
     */
    @Override
    @PostMapping("/signPage")
    public R<IPage<Sign>> page(@RequestBody @Validated PageParams<SignPageDTO> params){
        R<IPage<Sign>> page = super.page(params);
        List<Sign> list = page.getData().getRecords();
        if(list.size()>0){
            for(Sign sign:list){
                Integer signStatus = sign.getSignStatus();
                if(signStatus== ExamineStatus.UNDER_REVIEW.getCode()){
                    baseService.getStstus(sign);
                    signStatus = sign.getSignStatus();
                    if(signStatus!=ExamineStatus.UNDER_REVIEW.getCode()){
                        baseService.saveOrUpdate(sign);
                    }
                }
            }
            return page;
        }
        return R.success(null);
    }

    /**
     * 获取一个审核通过且未删除的签名
     * @return
     */
    @PostMapping("/getDefaultSign")
    public R<Sign> getDefaultSign(){
        return baseService.getDefaultSign();
    }

    /**
     * 删除签名
     * @return
     *//*
    @Override
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        return null;
    }*/

    private String fileToBase64Str(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] imageBytes = file.getBytes();
        String picStr = Base64Utils.encodeToString(imageBytes);
        picStr = StrUtil.removePrefix(picStr,"data:image/jpeg;base64,");
        return picStr;
    }

    /**
     * 添加签名（base64编码传递文件）
     * @param signSaveDTO
     * @return
     */
    @ApiOperation(value = "添加签名（base64）", notes = "添加签名（base64）")
    @PostMapping("/saveSignFileStr")
    @SysLog("添加签名")
    public R<Boolean> saveSignFileStr(@RequestBody SignSaveDTO signSaveDTO) {
        Boolean bool = baseService.saveSign(signSaveDTO,signSaveDTO.getPicStr());
        return bool? success():R.fail("添加失败");
    }

    /**
     * 修改签名（base64编码传递文件）
     * @param signUpdateDTO
     * @return
     */
    @ApiOperation(value = "修改签名（base64）", notes = "修改签名（base64）")
    @PutMapping("/updateSignFileStr")
    @SysLog("修改签名")
    public R<Boolean> updateSignFileStr(@RequestBody SignUpdateDTO signUpdateDTO) {
        String mess = baseService.updateSign(signUpdateDTO,signUpdateDTO.getPicStr());
        return mess==null? R.successDef():R.fail(mess);
    }

}
