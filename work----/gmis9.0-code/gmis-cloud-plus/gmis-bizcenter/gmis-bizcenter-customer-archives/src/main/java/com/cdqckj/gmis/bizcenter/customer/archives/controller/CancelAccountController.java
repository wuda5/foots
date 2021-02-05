package com.cdqckj.gmis.bizcenter.customer.archives.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.customer.archives.service.CancelAccountService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelSaveDTO;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 销户
 *
 * @author hp
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cancelAccount")
@Api(value = "CancelAccountController", tags = "销户api")
@PreAuth(replace = "cancelAccount:")
public class CancelAccountController {

    @Autowired
    CancelAccountService cancelAccountService;

    @ApiOperation("新增销户记录")
    @PostMapping(value = "add")
    public R<MeterAccountCancel> add(@RequestBody MeterAccountCancelSaveDTO saveDTO) {
        return cancelAccountService.add(saveDTO);
    }

    @ApiOperation("批量销户")
    @PostMapping(value = "batchAdd")
    @GlobalTransactional
    public R<Boolean> batchAdd(@RequestBody List<MeterAccountCancelSaveDTO> saveDTO) {
        return cancelAccountService.batchAdd(saveDTO);
    }

}
