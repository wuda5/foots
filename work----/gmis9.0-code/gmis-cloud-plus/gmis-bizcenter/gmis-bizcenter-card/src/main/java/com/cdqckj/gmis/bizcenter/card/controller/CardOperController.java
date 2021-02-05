package com.cdqckj.gmis.bizcenter.card.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.card.dto.CardRepLoadDTO;
import com.cdqckj.gmis.bizcenter.card.service.CardBizService;
import com.cdqckj.gmis.card.api.CardOperBizApi;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController("cardOperController")
@RequestMapping("/card")
@Api(value = "card", tags = "卡相关操作接口")
/*
@PreAuth(replace = "card:")*/
public class CardOperController {

    @Autowired
    CardOperBizApi cardOperBizApi;

    @Autowired
    CardBizService cardBizService;

    /**
     * 发卡加载 -----衍生：发卡只是个记录不作其他使用，发卡后更新表具使用信息表，将卡号回填。补卡后同样回填。
     * 首先查看是否有发卡记录
     * 没有发卡记录，生成一条默认IC状态是待收费。并记录卡类型：表具是否是 IC卡表，如果是IC卡表就发IC卡，如果不是就发ID卡。
     * 已有发卡记录，判断状态
     * 待收费：直接打开收费窗口进行收费（可以顺带充值）
     * 待写卡：加载数据显示写卡按钮--点击写卡按钮进行写卡
     * 已写卡：显示写卡记录信息，无任何操作按钮
     *
     * 收费-转收费流程，收完费并完善发卡记录状态 为 待写卡，前端加载写开户卡数据写卡（独立接口）
     *
     * 写卡
     * 读卡判断是否是新卡，然后加载写开户卡数据(独立接口-接口中会读充值数据写卡)写卡，完成后维护写卡状态
     * 发IC还是ID通过写卡参数中控制，待确认参数名？
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "发卡")
    @GetMapping("/issueCard")
    @GlobalTransactional
    public R<CardInfo> issueCard(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                 @NotBlank @RequestParam(value = "customerCode") String customerCode
){
        return cardBizService.issueCard(gasMeterCode,customerCode);
    }

    @ApiOperation(value = "发卡")
    @GetMapping("/watherIssueCard")
    @GlobalTransactional
    public R<Boolean> watherIssueCard(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                 @NotBlank @RequestParam(value = "customerCode") String customerCode
    ){
        return cardBizService.watherIssueCard(gasMeterCode,customerCode);
    }


    @ApiOperation(value = "发卡保存-未充值发卡调用")
    @PostMapping("/issueCardSave")
    @GlobalTransactional
    public R<CardInfo> issueCardSave( @RequestParam(value = "id") Long id){
        return cardOperBizApi.issueCardSave(id);
    }


    /**
     * 补卡加载
     * @return gasMeterCode
     */
    @ApiOperation(value = "补卡加载")
    @PostMapping("/repCard")
    public R<CardRepLoadDTO> repCard(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                     @NotBlank @RequestParam(value = "customerCode") String customerCode){
        return cardBizService.repCard(gasMeterCode,customerCode);
    }


    /**
     * 补卡保存
     * 补充基本信息，确认是否补上次充值，如果补上次充值，根据输入金额气量进行保存
     * @return gasMeterCode
     */
    @ApiOperation(value = "补卡保存")
    @PostMapping("/repCardSave")
    @GlobalTransactional
    public R<CardRepLoadDTO> repCardSave(@RequestBody @Validated CardRepRecordSaveDTO saveDTO,
                                         @RequestParam(value = "id",required = false) Long id){
            return cardBizService.repCardSave(saveDTO,id);
    }
}
