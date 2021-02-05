package com.cdqckj.gmis.card.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.card.dto.CardRepRecordPageDTO;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardRepRecordUpdateDTO;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.vo.CardRepRecordVO;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 补换卡记录API
* @author tp
* @Date 2020-08-28
*/
@FeignClient(name = "${gmis.feign.gmis-card-server:gmis-card-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
, path = "/cardRepRecord", qualifier = "CardRepRecordBizApi")
public interface CardRepRecordBizApi {

    /**
    * 保存 补换卡记录
    * @param saveDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.POST)
    R<CardRepRecord> save(@RequestBody CardRepRecordSaveDTO saveDTO);

    /**
    * 批量新增 补换卡记录
    * @param list 保存参数
    * @return 实体
    */
    @PostMapping(value = "/saveList")
    R<CardRepRecord> saveList(@RequestBody List<CardRepRecordSaveDTO> list);


    /**
    * 更新 补换卡记录
    * @param updateDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.PUT)
    R<CardRepRecord> update(@RequestBody CardRepRecordUpdateDTO updateDTO);


    /**
    * 批量修改 补换卡记录
    * @param list
    * @return
    */
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<CardRepRecordUpdateDTO> list);

    /**
    * 分页查询 补换卡记录
    * @param params
    * @return
    */
    @PostMapping(value = "/page")
    R<Page<CardRepRecord>> page(@RequestBody PageParams<CardRepRecordPageDTO> params);

    /**
    * ID查询 补换卡记录
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    R<CardRepRecord> get(@PathVariable("id") Long id);

    /**
    * 查询 补换卡记录
    * @param queryInfo
    * @return
    */
    @PostMapping(value = "/query")
    R<List<CardRepRecord>> query(@RequestBody CardRepRecord queryInfo);

     /**
     * 根据id集合批量查询  补换卡记录
     * @param ids 主键id
     * @return 查询结果
     */
     @PostMapping("/queryList")
     R<List<CardRepRecord>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     * 查询业务关注点补卡记录列表
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    R<List<CardRepRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                            @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode);

    /**
     * 统计补卡次数
     * @param gasMeterCode 表具编号
     * @return 补卡次数
     */
    @GetMapping("/countCardRep")
    R<Integer> countCardRep(@RequestParam(value = "gasMeterCode") String gasMeterCode);
}