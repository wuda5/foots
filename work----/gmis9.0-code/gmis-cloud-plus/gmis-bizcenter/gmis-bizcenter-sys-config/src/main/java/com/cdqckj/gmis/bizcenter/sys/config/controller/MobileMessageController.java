package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.MobileMessageBizApi;
import com.cdqckj.gmis.systemparam.dto.MobileMessagePageDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessageSaveDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessageUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.MobileMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 短信配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/sms")
@Api(value = "sms", tags = "短信配置")
//@PreAuth(replace = "mobileMessage:")
public class MobileMessageController {

    @Autowired
    public MobileMessageBizApi mobileMessageBizApi;

    @ApiOperation(value = "新增短信信息")
    @PostMapping("/mobileMessage/add")
    public R<MobileMessage> saveMobileMessage(@RequestBody MobileMessageSaveDTO mobileMessageSaveDTO){
        return mobileMessageBizApi.save(mobileMessageSaveDTO);
    }

    @ApiOperation(value = "更新短信信息")
    @PutMapping("/mobileMessage/update")
    public R<MobileMessage> updateMobileMessage(@RequestBody MobileMessageUpdateDTO mobileMessageUpdateDTO){
        return mobileMessageBizApi.update(mobileMessageUpdateDTO);
    }

    @ApiOperation(value = "删除短信信息")
    @DeleteMapping("/mobileMessage/delete")
    public R<Boolean> deleteMobileMessage(@RequestParam("ids[]") List<Long> ids){
        return mobileMessageBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询短信信息")
    @PostMapping("/mobileMessage/page")
    public R<Page<MobileMessage>> pageMobileMessage(@RequestBody PageParams<MobileMessagePageDTO> params){
        return mobileMessageBizApi.page(params);
    }

}
