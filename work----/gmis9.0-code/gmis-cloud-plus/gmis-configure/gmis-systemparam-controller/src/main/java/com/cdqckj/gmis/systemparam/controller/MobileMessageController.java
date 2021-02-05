package com.cdqckj.gmis.systemparam.controller;

import com.cdqckj.gmis.systemparam.entity.MobileMessage;
import com.cdqckj.gmis.systemparam.dto.MobileMessageSaveDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessageUpdateDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessagePageDTO;
import com.cdqckj.gmis.systemparam.service.MobileMessageService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 短信模板配置
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/mobileMessage")
@Api(value = "MobileMessage", tags = "短信模板配置")
@PreAuth(replace = "mobileMessage:")
public class MobileMessageController extends SuperController<MobileMessageService, Long, MobileMessage, MobileMessagePageDTO, MobileMessageSaveDTO, MobileMessageUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<MobileMessage> mobileMessageList = list.stream().map((map) -> {
            MobileMessage mobileMessage = MobileMessage.builder().build();
            //TODO 请在这里完成转换
            return mobileMessage;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(mobileMessageList));
    }
}
