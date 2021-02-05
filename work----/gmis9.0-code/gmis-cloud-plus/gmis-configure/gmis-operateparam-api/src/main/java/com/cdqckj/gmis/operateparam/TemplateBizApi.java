package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.hystrix.TemplateBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.TemplatePageDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.Template;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收费配置列表
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/template", qualifier = "templateBizApi")
public interface TemplateBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
    R<Template> saveTemplate(@RequestBody TemplateSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<Template> update(@RequestBody TemplateUpdateDTO updateDTO);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<Template>> page(@RequestBody PageParams<TemplatePageDTO> params);

    @PostMapping(value = "/query")
    R<List<Template>> query(@RequestBody Template params);

    @PostMapping(value = "/getById")
    R<Template> getById(@RequestParam("id") Long id);
}
