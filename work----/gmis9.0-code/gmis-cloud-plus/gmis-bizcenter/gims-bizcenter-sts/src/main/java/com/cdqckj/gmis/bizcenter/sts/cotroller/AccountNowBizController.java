package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.temp.counter.service.RemoveMeterService;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.domain.search.InitParamUtil;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.AccountNowApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.enums.StsAccountNowTypeEnum;
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
@RequestMapping("biz/sts/accountNow")
@Api(value = "AccountNow", tags = "开户")
@PreAuth(replace = "accountNow:")
public class AccountNowBizController {

    @Autowired
    AccountNowApi accountNowApi;

    @Autowired
    RemoveMeterService removeMeterService;

    /**
     * @auth lijianguo
     * @date 2020/11/12 13:14
     * @remark 这个时间段开户的数据的统计
     */
    @ApiOperation(value = "柜台临时综合_柜台统计")
    @PostMapping("/accountNowTypeFrom")
    public R<List<StsInfoBaseVo>> accountNowTypeFrom(@RequestBody StsSearchParam stsSearchParam){
        InitParamUtil.setNowDayAndMonth(stsSearchParam);
        List<StsInfoBaseVo> accountNowTypeVoList = this.accountNowApi.accountNowTypeFrom(stsSearchParam).getData();
        SeparateListData<StsInfoBaseVo> separateListData = new SeparateListData(accountNowTypeVoList);
        List<StsInfoBaseVo> result = new ArrayList<>(StsAccountNowTypeEnum.values().length);
        for (com.cdqckj.gmis.bizcenter.sts.enums.StsAccountNowTypeEnum e : com.cdqckj.gmis.bizcenter.sts.enums.StsAccountNowTypeEnum.values()){

            StsInfoBaseVo vo = separateListData.getTheDataByKey(String.valueOf(e.getCode()));
            if (vo == null){
                vo = new StsInfoBaseVo();
                vo.setAmount(0);
            }
            vo.setType(e.getCode());
            vo.setTypeName(e.getDesc());
            result.add(vo);
        }
        return R.success(result);
    }

}
