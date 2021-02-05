package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordResDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.vo.ChargeItemVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBasePlusVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 收费项记录API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/chargeItemRecord", qualifier = "ChargeItemRecordBizApi")
public interface ChargeItemRecordBizApi {
    /**
     * 保存收费项记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R< ChargeItemRecord> save(@RequestBody  ChargeItemRecordSaveDTO saveDTO);


    /**
     * 批量保存收费项记录
     * @param saveDTOs
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/saveList")
    R< ChargeItemRecord> saveList(@RequestBody List< ChargeItemRecordSaveDTO> saveDTOs);



    /**
     * 分页查询收费项记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    R<Page<ChargeItemRecordResDTO>> pageList(@RequestBody PageParams< ChargeItemRecordPageDTO> params);


    /**
     * 分页查询收费项记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page< ChargeItemRecord>> page(@RequestBody PageParams< ChargeItemRecordPageDTO> params);


    /**
     * 查询缴费记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<ChargeItemRecord>> query(@RequestBody ChargeItemRecord queryInfo);

    /**
     * 批量修改收费项缴费记录
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch",method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<ChargeItemRecordUpdateDTO> list);

    /**
     * @auth lijianguo
     * @date 2020/10/27 14:33
     * @remark 通过收费的号查询这个收费的item list
     */
    @PostMapping(value = "/getChangeItemByChargeNo")
    R<List<ChargeItemRecord>> getChangeItemByChargeNo(@RequestParam(name="chargeNo") String chargeNo);

    /**
     * 获取表具燃气费缴纳次数和最近一次缴纳时间
     *
     * @param gasMeterCode 表具编号
     * @return 查询结果
     */
    @GetMapping("/getLastUpdateTimeAndCount")
    R<ChargeItemVO> getLastUpdateTimeAndCount(@RequestParam("gasMeterCode") String gasMeterCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/20 10:13
    * @remark 售气收费看板-用气类型统计
    */
    @PostMapping("sts/gasFeeAndType")
    R<List<StsInfoBasePlusVo<String, Long>>> stsGasFeeAndType(@RequestBody StsSearchParam stsSearchParam);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/20 10:13
     * @remark 卡表的售气量
     */
    @PostMapping("sts/stsCardGasMeter")
    R<StsDateVo> stsCardGasMeter(@RequestBody StsSearchParam stsSearchParam);
}
