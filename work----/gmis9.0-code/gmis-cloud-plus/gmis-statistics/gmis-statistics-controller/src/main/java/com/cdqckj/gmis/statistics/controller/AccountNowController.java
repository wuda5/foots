package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.BusinessTemporaryBizApi;
import com.cdqckj.gmis.biztemporary.ChangeMeterRecordBizApi;
import com.cdqckj.gmis.biztemporary.OpenAccountRecordBizApi;
import com.cdqckj.gmis.biztemporary.RemoveMeterRecordBizApi;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.common.enums.gasmeter.MeterCustomerRelatedOperTypeEnum;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.service.AccountNowService;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/accountNow")
@Api(value = "AccountNow", tags = "开户的统计")
@PreAuth(replace = "accountNow:")
public class AccountNowController extends BaseController {

    @Autowired
    AccountNowService accountNowService;

    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    GasMeterBizApi gasMeterBizApi;

    @Autowired
    OpenAccountRecordBizApi openAccountRecordBizApi;

    @Autowired
    BusinessTemporaryBizApi businessTemporaryBizApi;

    @Autowired
    RemoveMeterRecordBizApi removeMeterRecordBizApi;

    @Autowired
    ChangeMeterRecordBizApi changeMeterRecordBizApi;

    @Autowired
    CardInfoBizApi cardInfoBizApi;

    /**
     * @auth lijianguo
     * @date 2020/11/12 13:14
     * @remark 1.开户 2.过户 3.销户 4.拆表 5.发卡 6.换表
     */
    @ApiOperation(value = "柜台临时综合_柜台统计")
    @PostMapping("/accountNowTypeFrom")
    public R<List<StsInfoBaseVo<Integer, Long>>> accountNowTypeFrom(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<Integer, Long>> accountNowTypeVoList = new ArrayList<>();
        if (stsRawDataType()){
//            List<StsInfoBaseVo<String, Long>> baseVoList = customerGasMeterRelatedBizApi.stsAccountNowTypeFrom(stsSearchParam).getData();
//            for (StsInfoBaseVo<String, Long> baseVo: baseVoList){
//                StsInfoBaseVo<Integer, Long> result = new StsInfoBaseVo<>();
//                if (MeterCustomerRelatedOperTypeEnum.OPEN_ACCOUNT.eq(baseVo.getType())){
//                    result.setType(1);
//                    result.setAmount(baseVo.getAmount());
//                }else if(MeterCustomerRelatedOperTypeEnum.TRANS_ACCOUNT.eq(baseVo.getType())){
//                    result.setType(2);
//                    result.setAmount(baseVo.getAmount());
//                }else if(MeterCustomerRelatedOperTypeEnum.REMOVE_METER.eq(baseVo.getType())){
//                    result.setType(4);
//                    result.setAmount(baseVo.getAmount());
//                }else if(MeterCustomerRelatedOperTypeEnum.CHANGE_METER.eq(baseVo.getType())){
//                    result.setType(6);
//                    result.setAmount(baseVo.getAmount());
//                }
//                accountNowTypeVoList.add(result);
//            }
            // 开户
            Long open = openAccountRecordBizApi.stsOpenAccountRecord(stsSearchParam).getData();
            StsInfoBaseVo<Integer, Long> openResult = new StsInfoBaseVo<>();
            openResult.setType(1);
            openResult.setAmount(open);
            accountNowTypeVoList.add(openResult);
            // 过户
            Long transfer = businessTemporaryBizApi.stsTransferNum(stsSearchParam).getData();
            StsInfoBaseVo<Integer, Long> transferResult = new StsInfoBaseVo<>();
            transferResult.setType(2);
            transferResult.setAmount(transfer);
            accountNowTypeVoList.add(transferResult);
            // 拆表
            Long remove = removeMeterRecordBizApi.stsRemoveMeterNum(stsSearchParam).getData();
            StsInfoBaseVo<Integer, Long> removeResult = new StsInfoBaseVo<>();
            removeResult.setType(4);
            removeResult.setAmount(remove);
            accountNowTypeVoList.add(removeResult);
            // 换表
            Long change = changeMeterRecordBizApi.stsChangeMeterNum(stsSearchParam).getData();
            StsInfoBaseVo<Integer, Long> changeResult = new StsInfoBaseVo<>();
            changeResult.setType(6);
            changeResult.setAmount(change);
            accountNowTypeVoList.add(changeResult);
            // 发卡统计
            Long sendCard = cardInfoBizApi.stsSendCardNum(stsSearchParam).getData();
            StsInfoBaseVo<Integer, Long> sendResult = new StsInfoBaseVo<>();
            sendResult.setType(5);
            sendResult.setAmount(sendCard);
            accountNowTypeVoList.add(sendResult);
        }else {
            accountNowTypeVoList = this.accountNowService.accountNowTypeFrom(stsSearchParam);
        }
        return R.success(accountNowTypeVoList);
    }

}
