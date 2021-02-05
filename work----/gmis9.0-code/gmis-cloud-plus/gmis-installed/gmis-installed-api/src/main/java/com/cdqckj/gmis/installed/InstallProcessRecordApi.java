package com.cdqckj.gmis.installed;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordUpdateDTO;
import com.cdqckj.gmis.installed.entity.InstallProcessRecord;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * 报装流程操作记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@FeignClient(name = "${gmis.feign.installed-server:gmis-installed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/installProcessRecord", qualifier = "installProcessRecord")
public interface InstallProcessRecordApi{

    @PostMapping(value = "/getById")
    R<InstallRecord> getById(@RequestParam("id") Long id);

    @PostMapping(value = "/queryList")
    R<List<InstallProcessRecord>> queryList(@RequestParam("ids[]") List<Long> ids);


    @PostMapping("/query")
    R<List<InstallProcessRecord>> query(@RequestBody InstallProcessRecord query);


    @PostMapping
    R<InstallProcessRecord> save(@RequestBody InstallProcessRecordSaveDTO dto);

    @PutMapping
    R<InstallProcessRecord> update(@RequestBody InstallProcessRecordUpdateDTO updateDTO);
}
