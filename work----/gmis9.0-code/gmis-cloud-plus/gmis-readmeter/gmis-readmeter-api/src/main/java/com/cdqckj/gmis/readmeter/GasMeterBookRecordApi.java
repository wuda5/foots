package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.hystrix.GasMeterBookRecordApiFallback;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 抄表册
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasMeterBookRecord", qualifier = "gasMeterBookRecordApi")
public interface GasMeterBookRecordApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasMeterBookRecord> save(@RequestBody GasMeterBookRecordSaveDTO saveDTO);

    /**
     * @param list
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    R<GasMeterBookRecord> saveList(@RequestBody List<GasMeterBookRecordSaveDTO> list);

    /**
     * @param list
     * @return
     */
    @RequestMapping(value = "/saveRecordList", method = RequestMethod.POST)
    R<List<GasMeterBookRecord>> saveRecordList(@RequestBody List<GasMeterBookRecordSaveDTO> list);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GasMeterBookRecord> update(@RequestBody GasMeterBookRecordUpdateDTO updateDTO);

    /**
     * @param updateList
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "updateBatch")
    R<Boolean> updateBatch(@RequestBody List<GasMeterBookRecordUpdateDTO> updateList);


    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    @RequestMapping(method = RequestMethod.DELETE ,value = "deleteByDto")
    R<Boolean> deleteByDto(@RequestBody GasMeterBookRecord gasMeterBookRecord);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GasMeterBookRecord>> page(@RequestBody PageParams<GasMeterBookRecordPageDTO> params);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/pageBookRecord")
    R<Page<GasMeterBookRecordVo>> pageBookRecord(@RequestBody PageParams<GasMeterBookRecordPageDTO> params);

    /**
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<GasMeterBookRecord>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryByBookId")
    R<List<GasMeterBookRecord>> queryByBookId(@RequestBody List<Long> ids);

    /**
     * 查询所有未删除的数据
     * @param record
     * @return
     */
    @PostMapping(value = "/query")
    R<List<GasMeterBookRecord>> query(@RequestBody GasMeterBookRecord record);

    /**
     * 条件查询一条数据:一般用于gasCode 查询一个xx
     *
     * @param data 条件查询一条数据
     * @return 查询结果
     */
    @ApiOperation(value = "条件查询一条数据", notes = "条件查询一条数据")
    @PostMapping("/queryOne")
    @SysLog("条件查询一条数据")
    R<GasMeterBookRecord> queryOne(@RequestBody GasMeterBookRecord data) ;

    @PostMapping(value = "/updateGasMeterByCode")
    R<Boolean> updateGasMeterByCode(@RequestBody GasMeterBookRecord record);

    @PostMapping(value = "/updateByCustomer")
    R<Boolean> updateByCustomer(@RequestBody GasMeterBookRecordUpdateDTO updateDTO);
    @PostMapping(value = "/updateByGasType")
    R<Boolean> updateByGasType(@RequestBody GasMeterBookRecordUpdateDTO updateDTO);

    /**
     * 获取抄表册用户最大的排序号
     * @param readMeterBookId
     * @return
     */
    @PostMapping(value = "/getMaxNumber")
    R<Integer> getMaxNumber(@RequestParam("id") Long readMeterBookId);
}
