package com.cdqckj.gmis.operateparam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitPageDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.service.PurchaseLimitService;
import com.cdqckj.gmis.operateparam.vo.PurchaseLimitVO;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/purchaseLimit")
@Api(value = "PurchaseLimit", tags = "")
@PreAuth(replace = "purchaseLimit:")
public class PurchaseLimitController extends SuperController<PurchaseLimitService, Long, PurchaseLimit, PurchaseLimitPageDTO, PurchaseLimitSaveDTO, PurchaseLimitUpdateDTO> {

    @Autowired
    PurchaseLimitService purchaseLimitService;

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<PurchaseLimit> purchaseLimitList = list.stream().map((map) -> {
            PurchaseLimit purchaseLimit = PurchaseLimit.builder().build();
            //TODO 请在这里完成转换
            return purchaseLimit;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(purchaseLimitList));
    }

    /**
     * 查询所有限购配置
     *
     * @return
     */
    @GetMapping(value = "/getAllRecord")
    public List<PurchaseLimit> getAllRecord() {
        return purchaseLimitService.getAllRecord();
    }

    @ApiOperation(value = "通过用户编号和表具编号分页查询用户的限购方案信息")
    @PostMapping(value = "/pageCustomerLimitInfo")
    public R<Page<PurchaseLimit>> pageCustomerLimitInfo(@RequestBody PageParams<PurchaseLimitVO> pageParams) {
        Page<PurchaseLimit> records = purchaseLimitService.queryCustomerLimitInfo(pageParams);
        return R.success(records);
    }
}
