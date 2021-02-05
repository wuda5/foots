package com.cdqckj.gmis.installed;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.installed.dto.*;
import com.cdqckj.gmis.installed.entity.InstallAccept;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.entity.InstallProcessRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.installed-server:gmis-installed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/installAccept", qualifier = "installAcceptApi"
)
public interface InstallAcceptApi {

    /**
     * 保存
     *
     * @param params
     * @return
     */
    @PostMapping
    R<InstallAccept> save(@RequestBody @Validated InstallAcceptSaveDTO params);

    @PostMapping("/query")
    R<List<InstallAccept>> query(@RequestBody InstallAccept query);

    @PostMapping("/queryOne")
    R<InstallAccept> queryOne(@RequestBody InstallAccept query);

    @PostMapping("/saveList")
    R<List<InstallAccept>> saveList(@RequestBody List<InstallAcceptSaveDTO> saveDTO);

    /**
     * 修改
     *
     * @param params
     * @return
     */
    @PutMapping
    R<InstallAccept> update(@RequestBody @Validated InstallAcceptUpdateDTO params);

}
