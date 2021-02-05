package com.cdqckj.gmis.${cfg.serviceName}.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import ${package.Entity}.${entity};
import ${cfg.SaveDTO}.${entity}SaveDTO;
import ${cfg.SaveDTO}.${entity}UpdateDTO;
import ${cfg.SaveDTO}.${entity}PageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import java.util.List;

/**
* ${table.comment!?replace("\n","\n * ")}API
* @author tp
* @Date 2020-08-28
*/
@FeignClient(name = "${r"${"}gmis.feign.gmis-${cfg.serviceName}-server:gmis-${cfg.serviceName}-server${r"}"}", fallbackFactory = HystrixTimeoutFallbackFactory.class
, path = "/${entity?uncap_first}", qualifier = "${entity}BizApi")
public interface ${entity}BizApi {

    /**
    * 保存 ${table.comment!?replace("\n","\n * ")}
    * @param saveDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.POST)
    R<${entity}> save(@RequestBody ${entity}SaveDTO saveDTO);

    /**
    * 批量新增 ${table.comment!?replace("\n","\n * ")}
    * @param list 保存参数
    * @return 实体
    */
    @PostMapping(value = "/saveList")
    R<${entity}> saveList(@RequestBody List<${entity}SaveDTO> list);


    /**
    * 更新 ${table.comment!?replace("\n","\n * ")}
    * @param updateDTO
    * @return
    */
    @RequestMapping(method = RequestMethod.PUT)
    R<${entity}> update(@RequestBody ${entity}UpdateDTO updateDTO);


    /**
    * 批量修改 ${table.comment!?replace("\n","\n * ")}
    * @param list
    * @return
    */
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<${entity}UpdateDTO> list);

    /**
    * 分页查询 ${table.comment!?replace("\n","\n * ")}
    * @param params
    * @return
    */
    @PostMapping(value = "/page")
    R<Page<${entity}>> page(@RequestBody PageParams<${entity}PageDTO> params);

    /**
    * ID查询 ${table.comment!?replace("\n","\n * ")}
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    R<${entity}> get(@PathVariable("id") Long id);

    /**
    * 查询 ${table.comment!?replace("\n","\n * ")}
    * @param queryInfo
    * @return
    */
    @PostMapping(value = "/query")
    R<List<${entity}>> query(@RequestBody ${entity} queryInfo);

     /**
     * 根据id集合批量查询  ${table.comment!?replace("\n","\n * ")}
     * @param ids 主键id
     * @return 查询结果
     */
     @PostMapping("/queryList")
     R<List<${entity}>> queryList(@RequestParam("ids[]") List<Long> ids);


    /**
    * 批量逻辑删除  ${table.comment!?replace("\n","\n * ")}
    * @param ids
    * @return
    */
    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
    * 条件删除（逻辑删除)  ${table.comment!?replace("\n","\n * ")}
    * @param entity
    * @return
    */
    @DeleteMapping(value = "/deleteByDto")
    R<Boolean> deleteByDto(@RequestBody ${entity} entity);

    /**
    * 物理删除（慎用） ${table.comment!?replace("\n","\n * ")}
    * @param ids
    * @return
    */
    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);


    /**
    * 导出动态生成带下拉框的Excel模板 ${table.comment!?replace("\n","\n * ")}
    * @param params
    */
    @RequestMapping(value = "/exportCombobox", method = RequestMethod.POST, produces = "application/octet-stream")
    void exportCombobox(@RequestBody PageParams<${entity}PageDTO> params);


    /**
    * ${table.comment!?replace("\n","\n * ")}
    * 使用自动生成的实体+注解方式导入 对RemoteData 类型的字段不支持，
    * 建议自建实体使用
    * @param simpleFile 上传文件
    */
    @PostMapping(value = "/import")
    R<Boolean> importExcel(@RequestParam(value = "file") MultipartFile simpleFile);
}