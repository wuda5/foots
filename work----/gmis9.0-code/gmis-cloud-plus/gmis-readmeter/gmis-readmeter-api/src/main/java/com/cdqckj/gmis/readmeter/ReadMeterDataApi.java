package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.dtoex.OneReadDataInputDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.vo.GasMeterReadStsVo;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 抄表导入
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/readMeterData", qualifier = "readMeterDataApi")
public interface ReadMeterDataApi {

    /**
     * @param
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    R<ReadMeterData> saveList(@RequestBody List<ReadMeterDataSaveDTO> readMeterDataList);

    /**
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ReadMeterData>> page(@RequestBody PageParams<ReadMeterDataPageDTO> params);
    /**
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/pageExistData")
    R<Page<ReadMeterData>> pageExistData(@RequestBody PageParams<ReadMeterDataPageDTO> params);

    /**
     * 根据id集合获取list
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<ReadMeterData>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     *
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ReadMeterData> save(@RequestBody ReadMeterDataSaveDTO saveDTO);

    /**
     * 批量查询
     * @param data
     * @return
     */
    @PostMapping("/query")
    R<List<ReadMeterData>> query(@RequestBody ReadMeterData data);
    /**
     * 条件查询一条数据
     *
     * @param data 条件查询一条数据
     * @return 查询结果
     */
    @ApiOperation(value = "条件查询一条数据", notes = "条件查询一条数据")
    @PostMapping("/queryOne")
    @SysLog("条件查询一条数据")
    R<ReadMeterData> queryOne(@RequestBody ReadMeterData data) ;
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @PostMapping("/getById")
    R<ReadMeterData> getById(@RequestParam("id") Long id);

    @PostMapping("/input")
    R<ReadMeterData> input(@RequestBody ReadMeterData updateDTO);

    @PostMapping("/inputList")
    R<Boolean> inputList(@RequestBody List<ReadMeterData> updateDTO);

    /**
     * 根据抄表册id获取抄表数据
     * @param ids
     * @return
     */
    @PostMapping("/getDataByBookId")
    R<List<ReadMeterData>> getDataByBookId(@RequestBody List<Long> ids);

    /**
     * 根据抄表册id获取抄表数据
     * @param list
     * @return
     */
    @PostMapping("/getDataByCustomerCode")
    R<List<ReadMeterData>> getDataByCustomerCode(@RequestBody List<String> list);
    /**
     * 根据抄表册id获取抄表数据
     * @param list
     * @return
     */
    @PostMapping("/deleteByGasCode")
    R<Boolean> deleteByGasCode(@RequestBody List<String> list);

    /**
     * 导出抄表名单
     * @param params
     * @return
     */
    @PostMapping(value = "/exportCombobox")
    Response exportCombobox(@RequestBody @Validated PageParams<ReadMeterDataPageDTO> params);

    /**
     * 导出抄表名单
     * @param list
     * @return
     */
    @PostMapping(value = "/exportReadMeterList")
    Response exportReadMeterList(@RequestBody List<ReadMeterPlanScope> list);

    /**
     * 根据抄表月份和抄表册id导出
     * @param readMeterData
     * @return
     */
    @PostMapping(value = "/exportReadMeterData")
    Response exportReadMeterData(@RequestBody ReadMeterData readMeterData);

    /**
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/importReadMeterData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<Map<Integer,ReadMeterData>> importReadMeterData(@RequestPart(value = "file") MultipartFile file);

    /**
     * 导入excel
     * @param simpleFile
     * @return
     */
    @PostMapping(value = "/importExcelByTime", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<Map<String,Object>> importExcelByTime(@RequestPart(value = "file") MultipartFile simpleFile, @RequestParam("readTime")  String readTime);

    /**
     *
     * @param list
     * @param process
     * @return
     */
    @PostMapping(value = "/getMeterDataByCode")
    R<List<ReadMeterData>> getMeterDataByCode(@RequestBody List<ReadMeterData> list, @RequestParam("processEnum") ProcessEnum process);

    /**
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/export")
    Response export(@RequestBody @Validated PageParams<ReadMeterDataPageDTO> params);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ReadMeterData> update(@RequestBody ReadMeterDataUpdateDTO updateDTO);

    /**
     * @param list
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT ,value = "/updateBatch")
    R<Boolean> updateBatch(@RequestBody List<ReadMeterDataUpdateDTO> list);

    /**
     * 获取历史最近三条数据
     * @param list
     * @return
     */
    @PostMapping(value = "/getHistory")
    R<Map<String, BigDecimal>> getHistory(@RequestBody List<ReadMeterDataUpdateDTO> list);

    /**
     * 审核
     * @param list
     * @return
     */
    @PostMapping(value = "/examine")
    R<List<ReadMeterData>> examine(@RequestBody List<ReadMeterData> list);

    /**
     * 计算排序
     * @param list
     * @return
     */
    @PostMapping(value = "/settlement")
    R<List<ReadMeterData>> settlement(@RequestBody List<Long> list);

    @PostMapping(value = "/withdraw")
    R<Boolean> withdraw(@RequestBody List<Long> ids);

    /**
     * 录入单条抄表数据
     * @param readMeterDataUpdateDTO
     * @return
     */
    @PostMapping(value = "/inputData")
    R<ReadMeterData> inputData(@RequestBody ReadMeterDataUpdateDTO readMeterDataUpdateDTO);

    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    @PostMapping(value = "/pageReadMeterData")
    R<Page<ReadMeterData>> pageReadMeterData(@RequestBody PageParams<ReadMeterDataPageDTO> params);

    /**
     * 根据抄表册id导出抄表名单
     * @param bookId
     * @return
     */
    @PostMapping(value = "/exportReadMeterByBook")
    Response exportReadMeterByBook(@RequestParam("bookId") Long bookId);

    /**
     * 根据表具编号查询还未缴费的抄表数据
     * @param gasMeterCode 表具编号
     * @return 未缴费的抄表数据
     */
    @GetMapping("/queryUnChargedData")
    R<List<ReadMeterData>> queryUnChargedData(@RequestParam("gasMeterCode")String gasMeterCode);

    /**
     * 查询抄表次数
     * @param gasMeterCode 表具编号
     * @return 表具抄表次数
     */
    @GetMapping("/countReadMeterData")
    R<Integer> countReadMeterData(@RequestParam("gasMeterCode")String gasMeterCode);

    @ApiOperation(value = "检测确保 某表具 对应的月份初始化数据", notes = "检测确保 某表具 对应的月份初始化数据")
    @PostMapping("/checkInitOneMeterData")
    R<ReadMeterData> checkInitOneMeterData(@RequestBody OneReadDataInputDTO dto);

    @PostMapping(value = "/updateDataByCustomer")
    R<Boolean> updateDataByCustomer(@RequestBody ReadMeterDataUpdateDTO updateDTO);
    @PostMapping(value = "/updateDataByGasType")
    R<Boolean> updateDataByGasType(@RequestBody ReadMeterDataUpdateDTO updateDTO);

    /**
     * @param list
     * @return
     */
    @RequestMapping(method = RequestMethod.POST ,value = "/updateDataRefund")
    R<Boolean> updateDataRefund(@RequestBody List<Long> list);

    @RequestMapping(method = RequestMethod.POST, value = "/costSettlement")
    R<ReadMeterData> costSettlement(@RequestBody ReadMeterData data);

    /**
     * 判断表是否可以过户
     * false-有未结算数据，不能开户，true-可以开户
     * @param gasMeter
     * @return
     */
    @PostMapping(value = "/isFinished")
    R<Boolean> isFinished(@RequestBody CustomerGasDto gasMeter);

    /**
     * 统计抄表完成率
     * @param stsSearchParam
     * @return
     */
    @PostMapping(value = "/stsReadMeter")
    R<Page<MeterPlanNowStsVo>> stsReadMeter(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 13:55
    * @remark 抄表业务管理-普表抄表-抄表统计
    */
    @ApiOperation(value = "抄表业务管理-普表抄表-抄表统计")
    @PostMapping("/generalGasMeterReadSts")
    R<GasMeterReadStsVo> generalGasMeterReadSts(@RequestBody StsSearchParam stsSearchParam);


    /**
     * 获取最新一条抄表数据-不区分dataType
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 最新数据
     */
    @GetMapping(value = "/getLatestData")
    R<ReadMeterData> getLatestData(@RequestParam("gasMeterCode") String gasMeterCode,
                                   @RequestParam("customerCode") String customerCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 14:57
    * @remark 售气数据-普表
    */
    @PostMapping(value = "sts/stsGeneralGasMeter")
    R<StsDateVo> stsGeneralGasMeter(@RequestBody StsSearchParam stsSearchParam);
}
