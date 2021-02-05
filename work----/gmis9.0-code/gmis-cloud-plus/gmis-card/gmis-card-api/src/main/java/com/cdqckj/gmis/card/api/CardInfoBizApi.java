package com.cdqckj.gmis.card.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.card.dto.CardInfoPageDTO;
import com.cdqckj.gmis.card.dto.CardInfoSaveDTO;
import com.cdqckj.gmis.card.dto.CardInfoUpdateDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.ConcernsCardInfo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 气表卡信息API
* @author tp
* @Date 2020-08-28
*/
@FeignClient(name = "${gmis.feign.gmis-card-server:gmis-card-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
, path = "/cardInfo", qualifier = "CardInfoBizApi")
public interface CardInfoBizApi {

    /**
    * 保存 气表卡信息
    * @param saveDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.POST)
    R<CardInfo> save(@RequestBody CardInfoSaveDTO saveDTO);

    /**
    * 批量新增 气表卡信息
    * @param list 保存参数
    * @return 实体
    */
    @PostMapping(value = "/saveList")
    R<CardInfo> saveList(@RequestBody List<CardInfoSaveDTO> list);

    /**
    * 更新 气表卡信息
    * @param updateDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.PUT)
    R<CardInfo> update(@RequestBody CardInfoUpdateDTO updateDTO);


    /**
    * 批量修改 气表卡信息
    * @param list
    * @return
    */
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<CardInfoUpdateDTO> list);

    /**
    * 分页查询 气表卡信息
    * @param params
    * @return
    */
    @PostMapping(value = "/page")
    R<Page<CardInfo>> page(@RequestBody PageParams<CardInfoPageDTO> params);

    /**
    * ID查询 气表卡信息
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    R<CardInfo> get(@PathVariable("id") Long id);

    /**
    * 查询 气表卡信息
    * @param queryInfo
    * @return
    */
    @PostMapping(value = "/query")
    R<List<CardInfo>> query(@RequestBody CardInfo queryInfo);


    /**
     * 充值校验卡是否余额，未读卡回写
     * @param gasMeterCode

     */
    @PostMapping(value = "/rechargeCheckCardBalance")
    R<Boolean> rechargeCheckCardBalance(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                        @RequestParam(value = "customerCode") String customerCode);


    /**
     * 查询 气表卡信息
     * @param gasMeterCode
     * @return
     */
    @GetMapping(value = "/getByGasMeterCode")
    R<CardInfo> getByGasMeterCode(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                  @RequestParam(value = "customerCode") String customerCode
                                  );
    /**
     * 查询 气表卡信息
     * @param chargeNO
     * @return
     */
    @GetMapping(value = "/getByChargeNO")
    R<CardInfo> getByChargeNO(@RequestParam(value = "chargeNO") String chargeNO
    );

    /**
     *获取兴趣关注点卡信息
     * @param gasCode
     * @return
     */
    @ApiOperation(value = "获取兴趣关注点卡信息")
    @GetMapping("/getConcernsCardInfo")
    R<ConcernsCardInfo> getConcernsCardInfo(@RequestParam("gasCode") String gasCode,
                                            @RequestParam(value = "customerCode") String customerCode);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 16:49
     * @remark 统计发卡数量
     */
    @PostMapping("/sts/stsSendCardNum")
    R<Long> stsSendCardNum(@RequestBody StsSearchParam stsSearchParam);

    /**
     * 卡信息作废
     * @return gasMeterCode
     */
    @ApiOperation(value = "卡信息作废")
    @GetMapping("/invalidCardByMeterAndCustomerCode")
    R<Boolean> invalidCardByMeterAndCustomerCode(@RequestParam("gasMeterCode") String gasMeterCode,
                                  @RequestParam(value = "customerCode") String customerCode);

    /**
     * 卡信息作废
     * @return gasMeterCode
     */
    @ApiOperation(value = "卡信息作废")
    @GetMapping("/invalidCardById")
    R<Boolean> invalidCardById(@RequestParam("id") Long id);
}