package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.hystrix.ReadMeterBookApiFallback;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.util.List;

/**
 * 抄表册
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/readMeterBook", qualifier = "readMeterBookBizApi")
public interface ReadMeterBookApi {

    /**
     * @param book
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "saveBook")
    R<Boolean> saveBook(@RequestBody ReadMeterBook book);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ReadMeterBook> update(@RequestBody ReadMeterBookUpdateDTO updateDTO);
    /**
     * @param book
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "updateBookDetail")
    R<Boolean> updateBookDetail(@RequestBody ReadMeterBook book);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ReadMeterBook>> page(@RequestBody PageParams<ReadMeterBookPageDTO> params);

    /**
     * @param id
     * @return
     */
    @PostMapping(value = "/getById")
    R<ReadMeterBook> getById(@RequestParam("id") Long id);

    /**
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<ReadMeterBook>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     * 导出抄表册
     * @param params
     * @return
     */
    @PostMapping(value = "/export")
    Response export(@RequestBody @Validated PageParams<ReadMeterBookPageDTO> params);

    /**
     * 导出抄表册(模板)
     * @param params
     * @return
     */
    @PostMapping(value = "/exportCombobox")
    Response exportCombobox(@RequestBody @Validated PageParams<ReadMeterBookPageDTO> params);

    /**
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryByWrap")
    R<Page<ReadMeterBook>> queryByWrap(@RequestBody PageParams<ReadMeterPlan> params);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
     R<List<ReadMeterBook>> query(@RequestBody ReadMeterBook data);
}
