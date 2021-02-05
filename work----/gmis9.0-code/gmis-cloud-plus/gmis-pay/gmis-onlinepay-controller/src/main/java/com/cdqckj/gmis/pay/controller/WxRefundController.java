package com.cdqckj.gmis.pay.controller;

import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.pay.dto.WxRefundSaveDTO;
import com.cdqckj.gmis.pay.dto.WxRefundUpdateDTO;
import com.cdqckj.gmis.pay.dto.WxRefundPageDTO;
import com.cdqckj.gmis.pay.service.WxRefundService;
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
 * 微信退款记录
 * </p>
 *
 * @author gmis
 * @date 2021-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wxRefund")
@Api(value = "WxRefund", tags = "微信退款记录")
@PreAuth(replace = "wxRefund:")
public class WxRefundController extends SuperController<WxRefundService, String, WxRefund, WxRefundPageDTO, WxRefundSaveDTO, WxRefundUpdateDTO> {

    
}
