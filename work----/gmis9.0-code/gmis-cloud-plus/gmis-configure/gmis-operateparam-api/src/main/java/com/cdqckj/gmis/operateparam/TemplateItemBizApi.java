package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.hystrix.TemplateItemBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.TemplateItemPageDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收费配置详情
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = TemplateItemBizApiFallback.class
        , path = "/templateItem", qualifier = "templateItemBizApi")
public interface TemplateItemBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<TemplateItem> save(@RequestBody TemplateItemSaveDTO saveDTO);

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(value = "/share" ,method = RequestMethod.POST)
    R<TemplateItem> share(@RequestBody TemplateItem saveDTO);

    /**
     * 审核
     * @param list
     * @return
     */
    @RequestMapping(value = "/adminExamine" ,method = RequestMethod.POST)
    R<Boolean> adminExamine(@RequestBody List<TemplateItem> list);

    /**
     * 模板下发
     * @param list
     * @return
     */
    @PostMapping(value = "/publish")
    R<Boolean> publish(@RequestBody List<TemplateItem> list);

    /**
     *
     * @param list
     * @param code
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/updateByCode")
    R<Boolean> updateByCode(@RequestBody List<TemplateItem> list,@RequestParam("tenantCode") String code);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<TemplateItem> update(@RequestBody TemplateItemUpdateDTO updateDTO);

    /**
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch" ,method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<TemplateItemUpdateDTO> list);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<TemplateItem>> page(@RequestBody PageParams<TemplateItemPageDTO> params);

    /**
     * 平台模板
     * @param params
     * @return
     */
    @PostMapping(value = "/pageAdminTemplate")
    R<Page<TemplateItem>> pageAdminTemplate(@RequestBody PageParams<TemplateItemPageDTO> params);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/query")
    R<List<TemplateItem>> query(@RequestBody TemplateItem params);

    /**
     * 根据id获取集合
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<TemplateItem>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value ="/getById", method = RequestMethod.POST)
    R<TemplateItem> getById(@ApiParam(name = "id", value = "id")
                          @RequestParam(value = "id") Long id);

    /**
     * 测试
     * @param path
     * @return
     */
    @PostMapping(value = "/test")
    R<String> test(@RequestParam("path") String path);
}
