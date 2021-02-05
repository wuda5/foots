package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorSaveDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataError;
import com.cdqckj.gmis.readmeter.hystrix.ReadMeterDataErrorApiFallback;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 抄表导入
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/readMeterDataError", qualifier = "readMeterDataErrorApi")
public interface ReadMeterDataErrorApi {

    /**
     * @param
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    R<ReadMeterDataError> saveList(@RequestBody List<ReadMeterDataErrorSaveDTO> readMeterDataErrorList);

    /**
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ReadMeterDataError>> page(@RequestBody PageParams<ReadMeterDataErrorPageDTO> params);

    /**
     * 根据id集合获取list
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<ReadMeterDataError>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     * 批量查询
     * @param data
     * @return
     */
    @PostMapping("/query")
    R<List<ReadMeterDataError>> query(@RequestBody ReadMeterDataError data);

    /**
     * 导出抄表名单
     * @param params
     * @return
     */
    @PostMapping(value = "/exportCombobox")
    Response exportCombobox(@RequestBody @Validated PageParams<ReadMeterDataErrorPageDTO> params);


}
