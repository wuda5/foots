package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.statistics.service.MeterNowService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.statistics.service.MeterUploadStsNowService;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.statistics.vo.StsMeterInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;


/**
 * <p>
 * 前端控制器
 * 表具的厂家，类型的两个维度
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/meterNow")
@Api(value = "MeterNow", tags = "表具")
@PreAuth(replace = "meterNow:")
public class MeterNowController extends BaseController{

    @Autowired
    MeterNowService meterNowService;

    @Autowired
    MeterUploadStsNowService meterUploadStsNowService;

    @Autowired
    UserBizApi userBizApi;

    @Autowired
    GasMeterBizApi gasMeterBizApi;

    /**
     * @auth lijianguo
     * @date 2020/11/13 10:58
     * @remark 统计表具的信息
     */
    @ApiOperation(value = "表具档案管理—统计数据")
    @PostMapping("/stsInfo")
    public R<StsMeterInfoVo> stsInfo(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String,Long>> typeVos;
        List<StsInfoBaseVo<String,Long>> factoryVos;
        List<StsInfoBaseVo<Integer,Long>> gasMaterStatus = new ArrayList<>();
        if (stsRawDataType()){
            typeVos = gasMeterBizApi.stsGasMeterType(stsSearchParam).getData();
            factoryVos = gasMeterBizApi.stsFactory(stsSearchParam).getData();
            gasMaterStatus = gasMeterBizApi.stsStatus(stsSearchParam).getData();
        }else {
            typeVos = meterNowService.stsTypeAmount(stsSearchParam);
            factoryVos = meterNowService.stsFactoryAmount(stsSearchParam);
        }
        // 统计表具每天的上报数据 1在线 2上报
        stsSearchParam.setStsDay(LocalDate.now());
        Long online = meterUploadStsNowService.stsMeterNumInfo(1, stsSearchParam);
        Long warning = meterUploadStsNowService.stsMeterNumInfo(2, stsSearchParam);
        StsMeterInfoVo vo = new StsMeterInfoVo();
        // 物联网表具的状态
        vo.setExceptionNum(warning);
        vo.setOnlineNum(online);
        vo.setFactoryNum(factoryVos.size());
        Long outNum = vo.getInternetMeter() - online < 0 ? 0: vo.getInternetMeter() - online;
        vo.setOutlineNum(outNum);
        // 表具的数量
        for (StsInfoBaseVo<String, Long> type: typeVos){
            if (OrderSourceNameEnum.READMETER_PAY.getCode().equals(type.getType())){
                vo.setGeneralMeter(vo.getGeneralMeter() + type.getAmount().intValue());
            }else if(OrderSourceNameEnum.IC_RECHARGE.getCode().equals(type.getType())){
                vo.setCardMeter(vo.getCardMeter() + type.getAmount().intValue());
            }else {
                vo.setInternetMeter(vo.getInternetMeter() + type.getAmount().intValue());
            }
        }
        // 每个状态的数量
        for (StsInfoBaseVo<Integer, Long> status: gasMaterStatus){
            if (status.getType().equals(GasMeterStatusEnum.Pre_doc.getCode())){
                vo.setPreStatus(status.getAmount().intValue());
            }else if(status.getType().equals(GasMeterStatusEnum.Unopen.getCode())){
                vo.setInstallStatus(status.getAmount().intValue());
            }else if(status.getType().equals(GasMeterStatusEnum.UnVentilation.getCode())){
                vo.setOpenStatus(status.getAmount().intValue());
            }else if(status.getType().equals(GasMeterStatusEnum.Ventilated.getCode())){
                vo.setGasStatus(status.getAmount().intValue());
            }else if(status.getType().equals(GasMeterStatusEnum.Dismantle.getCode())){
                vo.setRemoveStatus(status.getAmount().intValue());
            }else {

            }
        }
        return R.success(vo);
    }

}
