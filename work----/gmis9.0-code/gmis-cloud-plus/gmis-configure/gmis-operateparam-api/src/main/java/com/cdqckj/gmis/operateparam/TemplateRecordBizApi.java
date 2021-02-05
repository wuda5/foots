package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.hystrix.TemplateRecordBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordPageDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TemplateRecord;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收费配置详情
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = TemplateRecordBizApiFallback.class
        , path = "/templateRecord", qualifier = "templateRecordBizApi")
public interface TemplateRecordBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<TemplateRecord> save(@RequestBody TemplateRecordSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<TemplateRecord> update(@RequestBody TemplateRecordUpdateDTO updateDTO);

    /**
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
    R<Page<TemplateRecord>> page(@RequestBody PageParams<TemplateRecordPageDTO> params);


    @PostMapping(value = "/query")
    R<List<TemplateRecord>> query(@RequestBody TemplateRecord params);
}
