package com.cdqckj.gmis.installed;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;

import com.cdqckj.gmis.installed.dto.GasInstallFileSaveDTO;
import com.cdqckj.gmis.installed.dto.GasInstallFileUpdateDTO;
import com.cdqckj.gmis.installed.entity.GasInstallFile;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.installed-server:gmis-installed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasInstallFile", qualifier = "gasInstallFileBizApi")
public interface GasInstallFileBizApi {

    @ApiOperation(value = "条件删除（真实删除）")
    @DeleteMapping(value = "/deleteReal")
    R<Boolean> delete(@RequestBody GasInstallFile file);
    /**
     * 批量保存报装材料信息
     * @param saveDTO
     * @return
     */
    @PostMapping("/saveList")
    R<List<GasInstallFile>> saveList(@RequestBody List<GasInstallFileSaveDTO> saveDTO);

    /**
     * 批量修改报装材料信息
     * @param updateDTO
     * @return
     */
    @PutMapping("/updateBatch")
    R<Boolean> updateBatch(@RequestBody List<GasInstallFileUpdateDTO> updateDTO);


    /**
     * 保存
     * @param saveDTO
     * @return
     */
    @PostMapping
    R<GasInstallFile> save(@RequestBody GasInstallFileSaveDTO saveDTO);

    /**
     * 修改
     * @param updateDTO
     * @return
     */
    @PutMapping
    R<GasInstallFile> update(@RequestBody GasInstallFileUpdateDTO updateDTO);


    /**
     * 条件查询
     * @return
     */
    @PostMapping("/query")
    R<List<GasInstallFile>> query(@RequestBody GasInstallFile query);
}
