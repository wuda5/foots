package com.cdqckj.gmis.bizjobcenter.controller;

import com.cdqckj.gmis.bizjobcenter.GiveReductionConfJobService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/piveReductionConfJobController")
@Api(value = "GiveReductionConfJobController", tags = "赠送减免活动定时任务")
public class GiveReductionConfJobController {
    @Autowired
    GiveReductionConfJobService giveReductionConfJobService;
    @PostMapping(value="/updateStatus")
    void updateGiveReductionStatus(){
        giveReductionConfJobService.updateGiveReductionStatus();
    }
}
