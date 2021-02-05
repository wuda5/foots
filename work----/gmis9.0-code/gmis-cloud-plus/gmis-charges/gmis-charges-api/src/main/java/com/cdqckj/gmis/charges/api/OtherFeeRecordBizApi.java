package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.entity.OtherFeeRecord;
import com.cdqckj.gmis.charges.dto.OtherFeeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.OtherFeeRecordUpdateDTO;
import com.cdqckj.gmis.charges.dto.OtherFeeRecordPageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import java.util.List;

/**
* 附加费记录API
* @author tp
* @Date 2020-08-28
*/
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
, path = "/otherFeeRecord", qualifier = "OtherFeeRecordBizApi")
public interface OtherFeeRecordBizApi {

    /**
    * 保存 附加费记录
    * @param saveDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.POST)
    R<OtherFeeRecord> save(@RequestBody OtherFeeRecordSaveDTO saveDTO);

    /**
    * 批量新增 附加费记录
    * @param list 保存参数
    * @return 实体
    */
    @PostMapping(value = "/saveList")
    R<OtherFeeRecord> saveList(@RequestBody List<OtherFeeRecordSaveDTO> list);


    /**
    * 更新 附加费记录
    * @param updateDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.PUT)
    R<OtherFeeRecord> update(@RequestBody OtherFeeRecordUpdateDTO updateDTO);


    /**
    * 批量修改 附加费记录
    * @param list
    * @return
    */
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<OtherFeeRecordUpdateDTO> list);

    /**
    * 分页查询 附加费记录
    * @param params
    * @return
    */
    @PostMapping(value = "/page")
    R<Page<OtherFeeRecord>> page(@RequestBody PageParams<OtherFeeRecordPageDTO> params);

    /**
    * ID查询 附加费记录
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    R<OtherFeeRecord> get(@PathVariable("id") Long id);

    /**
    * 查询 附加费记录
    * @param queryInfo
    * @return
    */
    @PostMapping(value = "/query")
    R<List<OtherFeeRecord>> query(@RequestBody OtherFeeRecord queryInfo);

     /**
     * 根据id集合批量查询  附加费记录
     * @param ids 主键id
     * @return 查询结果
     */
     @PostMapping("/queryList")
     R<List<OtherFeeRecord>> queryList(@RequestParam("ids[]") List<Long> ids);


    /**
    * 批量逻辑删除  附加费记录
    * @param ids
    * @return
    */
    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
    * 条件删除（逻辑删除)  附加费记录
    * @param entity
    * @return
    */
    @DeleteMapping(value = "/deleteByDto")
    R<Boolean> deleteByDto(@RequestBody OtherFeeRecord entity);

    /**
    * 物理删除（慎用） 附加费记录
    * @param ids
    * @return
    */
    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);


    /**
    * 导出动态生成带下拉框的Excel模板 附加费记录
    * @param params
    */
    @RequestMapping(value = "/exportCombobox", method = RequestMethod.POST, produces = "application/octet-stream")
    void exportCombobox(@RequestBody PageParams<OtherFeeRecordPageDTO> params);


    /**
    * 附加费记录
    * 使用自动生成的实体+注解方式导入 对RemoteData 类型的字段不支持，
    * 建议自建实体使用
    * @param simpleFile 上传文件
    */
    @PostMapping(value = "/import")
    R<Boolean> importExcel(@RequestParam(value = "file") MultipartFile simpleFile);
}