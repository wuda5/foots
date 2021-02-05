package com.cdqckj.gmis.card.controller;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.card.dto.CardInfoPageDTO;
import com.cdqckj.gmis.card.dto.CardInfoSaveDTO;
import com.cdqckj.gmis.card.dto.CardInfoUpdateDTO;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.entity.ConcernsCardInfo;
import com.cdqckj.gmis.card.service.CardInfoService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表卡信息
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cardInfo")
@Api(value = "CardInfo", tags = "气表卡信息")
//@PreAuth(replace = "cardInfo:")
public class CardInfoController extends SuperController<CardInfoService, Long, CardInfo, CardInfoPageDTO, CardInfoSaveDTO, CardInfoUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CardInfo> cardInfoList = list.stream().map((map) -> {
            CardInfo cardInfo = CardInfo.builder().build();
            //TODO 请在这里完成转换
            return cardInfo;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(cardInfoList));
    }

    /**
     * 查询 气表卡信息
     * @param gasMeterCode
     * @return
     */
    @ApiOperation("查询 气表卡信息")
    @GetMapping(value = "/getByGasMeterCode")
    R<CardInfo> getByGasMeterCode(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                  @RequestParam(value = "customerCode") String customerCode){
        return R.success(baseService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,gasMeterCode)
                .eq(CardInfo::getCustomerCode,customerCode)
                .eq(CardInfo::getDataStatus, DataStatusEnum.NORMAL.getValue()))
        );
    }

    /**
     * 充值校验卡是否余额，未读卡回写
     * @param gasMeterCode

     */
    @ApiOperation("充值校验卡是否余额，未读卡回写")
    @PostMapping(value = "/rechargeCheckCardBalance")
    R<Boolean> rechargeCheckCardBalance(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                        @RequestParam(value = "customerCode") String customerCode
                                        ){
        CardInfo cardInfo=baseService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,gasMeterCode)
                .eq(CardInfo::getCustomerCode,customerCode)
                .eq(CardInfo::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        if(cardInfo!=null){
            BigDecimal cardB=cardInfo.getCardBalance();
            if(cardB!=null && cardB.compareTo(BigDecimal.ZERO)>0){
                return R.success(true);
            }else{
                return  R.success(false);
            }
        }
        return R.success(false);
    }

    @ApiOperation(value = "获取兴趣关注点卡信息")
    @GetMapping("/getConcernsCardInfo")
    public R<ConcernsCardInfo> getConcernsCardInfo(@RequestParam("gasCode") String gasCode,
                                                   @RequestParam(value = "customerCode") String customerCode){
        ConcernsCardInfo concernsCardInfo = baseService.getConcernsCardInfo(gasCode,customerCode);
        return  R.success(concernsCardInfo);
    }

    /**
     * 查询 气表卡信息
     * @param chargeNO
     * @return
     */
    @GetMapping(value = "/getByChargeNO")
    R<CardInfo> getByChargeNO(@RequestParam(value = "chargeNO") String chargeNO
    ){
        CardInfo cardInfo=baseService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getChargeNo,chargeNO)
                .eq(CardInfo::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        return R.success(cardInfo);
    }


    /**
     * 卡信息作废
     * @return gasMeterCode
     */
    @ApiOperation(value = "卡信息作废")
    @GetMapping("/invalidCardByMeterAndCustomerCode")
    public R<Boolean> invalidCard(@RequestParam("gasMeterCode") String gasMeterCode,
                                   @RequestParam(value = "customerCode") String customerCode){
        CardInfo cardInfo=baseService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,gasMeterCode)
                .eq(CardInfo::getCustomerCode,customerCode)
                .eq(CardInfo::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        CardInfo update=new CardInfo();
        update.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        update.setId(cardInfo.getId());
        update.setUniqueParam(cardInfo.getId().toString());
        baseService.updateById(update);
        return R.success(true);
    }


    /**
     * 卡信息作废
     * @return gasMeterCode
     */
    @ApiOperation(value = "卡信息作废")
    @GetMapping("/invalidCardById")
    public R<Boolean> invalidCardById(@RequestParam("id") Long id){
        CardInfo update=new CardInfo();
        update.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        update.setId(id);
        update.setUniqueParam(id.toString());
        baseService.updateById(update);
        return R.success(true);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 16:49
     * @remark 统计发卡数量
     */
    @PostMapping("/sts/stsSendCardNum")
    R<Long> stsSendCardNum(@RequestBody StsSearchParam stsSearchParam){
        Long num = this.baseService.stsSendCardNum(stsSearchParam);
        if (num == null){
            num = 0L;
        }
        return R.success(num);
    }
}
