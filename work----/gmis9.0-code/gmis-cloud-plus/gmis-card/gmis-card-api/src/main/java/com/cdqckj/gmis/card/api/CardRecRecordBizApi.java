package com.cdqckj.gmis.card.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.card.entity.CardRecRecord;
import com.cdqckj.gmis.card.dto.CardRecRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardRecRecordUpdateDTO;
import com.cdqckj.gmis.card.dto.CardRecRecordPageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import java.util.List;

/**
* 卡收回记录API
* @author tp
* @Date 2020-08-28
*/
@FeignClient(name = "${gmis.feign.gmis-card-server:gmis-card-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
, path = "/cardRecRecord", qualifier = "CardRecRecordBizApi")
public interface CardRecRecordBizApi {

    /**
    * 保存 卡收回记录
    * @param saveDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.POST)
    R<CardRecRecord> save(@RequestBody CardRecRecordSaveDTO saveDTO);



    /**
    * 分页查询 卡收回记录
    * @param params
    * @return
    */
    @PostMapping(value = "/page")
    R<Page<CardRecRecord>> page(@RequestBody PageParams<CardRecRecordPageDTO> params);

    /**
    * ID查询 卡收回记录
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    R<CardRecRecord> get(@PathVariable("id") Long id);

    /**
    * 查询 卡收回记录
    * @param queryInfo
    * @return
    */
    @PostMapping(value = "/query")
    R<List<CardRecRecord>> query(@RequestBody CardRecRecord queryInfo);
}