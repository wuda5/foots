package com.cdqckj.gmis.biztemporary;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.dto.GasMeterTempSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.GasMeterTempUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.GasMeterTemp;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasMeterTemp", qualifier = "GasMeterTempBizApi")
public interface GasMeterTempBizApi {
    /**
     * 批量查询
     * @param gasMeterTemp 查询参数
     * @return
     */
    @PostMapping("/query")
    R<List<GasMeterTemp>> query(@RequestBody GasMeterTemp gasMeterTemp);
    /**
     * 单体新增
     * @param gasMeterTempSaveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    R<GasMeterTempSaveDTO> save(@RequestBody @Valid GasMeterTempSaveDTO gasMeterTempSaveDTO);
    /**
     * 更新数据
     * @param updateDTO
     * @return
     */
    @PutMapping
    R<GasMeterTempUpdateDTO> update(@RequestBody GasMeterTempUpdateDTO updateDTO);
    /**
     * 校验
     * @param params
     * @return
     */
    @ApiOperation("校验")
    @PostMapping("/check")
    public Boolean check(@RequestBody GasMeterTemp params);
}
