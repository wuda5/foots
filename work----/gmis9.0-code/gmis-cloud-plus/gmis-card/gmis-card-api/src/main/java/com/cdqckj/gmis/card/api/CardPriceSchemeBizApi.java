package com.cdqckj.gmis.card.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.card.entity.CardPriceScheme;
import com.cdqckj.gmis.card.dto.CardPriceSchemeSaveDTO;
import com.cdqckj.gmis.card.dto.CardPriceSchemeUpdateDTO;
import com.cdqckj.gmis.card.dto.CardPriceSchemePageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import java.util.List;

/**
* 卡表价格方案记录API
* @author tp
* @Date 2020-08-28
*/
@FeignClient(name = "${gmis.feign.gmis-card-server:gmis-card-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
, path = "/cardPriceScheme", qualifier = "CardPriceSchemeBizApi")
public interface CardPriceSchemeBizApi {

    /**
    * 保存 卡表价格方案记录
    * @param saveDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.POST)
    R<CardPriceScheme> save(@RequestBody CardPriceSchemeSaveDTO saveDTO);

    /**
    * 批量新增 卡表价格方案记录
    * @param list 保存参数
    * @return 实体
    */
    @PostMapping(value = "/saveList")
    R<CardPriceScheme> saveList(@RequestBody List<CardPriceSchemeSaveDTO> list);


    /**
    * 更新 卡表价格方案记录
    * @param updateDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.PUT)
    R<CardPriceScheme> update(@RequestBody CardPriceSchemeUpdateDTO updateDTO);


    /**
    * 批量修改 卡表价格方案记录
    * @param list
    * @return
    */
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<CardPriceSchemeUpdateDTO> list);

    /**
    * 分页查询 卡表价格方案记录
    * @param params
    * @return
    */
    @PostMapping(value = "/page")
    R<Page<CardPriceScheme>> page(@RequestBody PageParams<CardPriceSchemePageDTO> params);

    /**
    * ID查询 卡表价格方案记录
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    R<CardPriceScheme> get(@PathVariable("id") Long id);

    /**
    * 查询 卡表价格方案记录
    * @param queryInfo
    * @return
    */
    @PostMapping(value = "/query")
    R<List<CardPriceScheme>> query(@RequestBody CardPriceScheme queryInfo);

     /**
     * 根据id集合批量查询  卡表价格方案记录
     * @param ids 主键id
     * @return 查询结果
     */
     @PostMapping("/queryList")
     R<List<CardPriceScheme>> queryList(@RequestParam("ids[]") List<Long> ids);


    /**
    * 批量逻辑删除  卡表价格方案记录
    * @param ids
    * @return
    */
    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
    * 条件删除（逻辑删除)  卡表价格方案记录
    * @param entity
    * @return
    */
    @DeleteMapping(value = "/deleteByDto")
    R<Boolean> deleteByDto(@RequestBody CardPriceScheme entity);

    /**
    * 物理删除（慎用） 卡表价格方案记录
    * @param ids
    * @return
    */
    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);


    /**
    * 导出动态生成带下拉框的Excel模板 卡表价格方案记录
    * @param params
    */
    @RequestMapping(value = "/exportCombobox", method = RequestMethod.POST, produces = "application/octet-stream")
    void exportCombobox(@RequestBody PageParams<CardPriceSchemePageDTO> params);


    /**
    * 卡表价格方案记录
    * 使用自动生成的实体+注解方式导入 对RemoteData 类型的字段不支持，
    * 建议自建实体使用
    * @param simpleFile 上传文件
    */
    @PostMapping(value = "/import")
    R<Boolean> importExcel(@RequestParam(value = "file") MultipartFile simpleFile);
}