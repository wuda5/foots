package com.cdqckj.gmis.installed;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.installed.dto.InstallDesignSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallDesignUpdateDTO;
import com.cdqckj.gmis.installed.entity.InstallAccept;
import com.cdqckj.gmis.installed.entity.InstallDesign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${gmis.feign.installed-server:gmis-installed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/installDesign", qualifier = "installDesignApi")
public interface InstallDesignApi {
    /**
     * 保存
     * @param saveDTO
     * @return
     */
    @PostMapping
    R<InstallDesign> save(@RequestBody InstallDesignSaveDTO saveDTO);

    @PutMapping
    R<InstallDesign> update(@RequestBody InstallDesignUpdateDTO updateDTO);


    @PostMapping("/queryOne")
    R<InstallDesign> queryOne(@RequestBody InstallDesign query);
}
