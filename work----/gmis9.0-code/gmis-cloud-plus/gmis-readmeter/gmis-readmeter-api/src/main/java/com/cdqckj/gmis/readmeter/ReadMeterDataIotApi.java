package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import com.cdqckj.gmis.readmeter.vo.SettlementArrearsVO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 抄表导入
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/readMeterDataIot", qualifier = "readMeterDataIotApi")
public interface ReadMeterDataIotApi {

    /**
     * @param
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    R<ReadMeterDataIot> saveList(@RequestBody List<ReadMeterDataIotSaveDTO> readMeterDataList);

    /**
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ReadMeterDataIot>> page(@RequestBody PageParams<ReadMeterDataIotPageDTO> params);


    @PostMapping(value = "/pageList")
    R<Page<ReadMeterDataIotVo>> pageList(@RequestBody PageParams<ReadMeterDataIotPageDTO> params);

    /**
     * 根据id集合获取list
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<ReadMeterDataIot>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     *
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ReadMeterDataIot> save(@RequestBody ReadMeterDataIotSaveDTO saveDTO);

    /**
     * 批量查询
     * @param data
     * @return
     */
    @PostMapping("/query")
    R<List<ReadMeterDataIot>> query(@RequestBody ReadMeterDataIot data);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @PostMapping("/getById")
    R<ReadMeterDataIot> getById(@RequestParam("id") Long id);

    @PostMapping("/input")
    R<ReadMeterDataIot> input(@RequestBody ReadMeterDataIot updateDTO);

    @PostMapping("/inputList")
    R<Boolean> inputList(@RequestBody List<ReadMeterDataIot> updateDTO);

    /**
     * 根据抄表册id获取抄表数据
     * @param ids
     * @return
     */
    @PostMapping("/getDataByBookId")
    R<List<ReadMeterDataIot>> getDataByBookId(@RequestBody List<Long> ids);

    /**
     * 根据抄表册id获取抄表数据
     * @param list
     * @return
     */
    @PostMapping("/getDataByCustomerCode")
    R<List<ReadMeterDataIot>> getDataByCustomerCode(@RequestBody List<String> list);

    /**
     * 根据表身号查询
     * @param gasMeterNumber
     * @return
     */
    @GetMapping("/getDataByMeterNo")
    R<ReadMeterDataIot> getDataByMeterNo(@RequestParam(value = "gasMeterNumber") String gasMeterNumber);

    /**
     * 导出抄表名单
     * @param params
     * @return
     */
    @PostMapping(value = "/exportCombobox")
    Response exportCombobox(@RequestBody @Validated PageParams<ReadMeterDataIotPageDTO> params);

    /**
     * 导出抄表名单
     * @param list
     * @return
     */
    @PostMapping(value = "/exportReadMeterList")
    Response exportReadMeterList(@RequestBody List<ReadMeterPlanScope> list);

    /**
     *
     * @param file
     * @return
     */
    /*@PostMapping(value = "/importReadMeterData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<Map<Integer,ReadMeterDataIot>> importReadMeterData(@RequestPart(value = "file") MultipartFile file);*/

    /**
     *
     * @param list
     * @param process
     * @return
     */
    @PostMapping(value = "/getMeterDataByCode")
    R<List<ReadMeterDataIot>> getMeterDataByCode(@RequestBody List<ReadMeterDataIot> list, @RequestParam("processEnum") ProcessEnum process);

    /**
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/export")
    Response export(@RequestBody @Validated PageParams<ReadMeterDataIotPageDTO> params);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ReadMeterDataIot> update(@RequestBody ReadMeterDataIotUpdateDTO updateDTO);

    /**
     * @param list
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT ,value = "/updateBatch")
    R<Boolean> updateBatch(@RequestBody List<ReadMeterDataIotUpdateDTO> list);

    /**
     * 获取历史最近三条数据
     * @param list
     * @return
     */
    @PostMapping(value = "/getHistory")
    R<Map<String, BigDecimal>> getHistory(@RequestBody List<ReadMeterDataIotUpdateDTO> list);

    /**
     * 审核
     * @param list
     * @return
     */
    @PostMapping(value = "/examine")
    R<List<ReadMeterDataIot>> examine(@RequestBody List<ReadMeterDataIot> list);

    /**
     * 计算排序
     * @param list
     * @return
     */
    @PostMapping(value = "/settlement")
    R<List<ReadMeterDataIot>> settlement(@RequestBody List<Long> list);

    /**
     * 录入单条抄表数据
     * @param readMeterDataUpdateDTO
     * @return
     */
    @PostMapping(value = "/inputData")
    R<ReadMeterDataIot> inputData(@RequestBody ReadMeterDataIotUpdateDTO readMeterDataUpdateDTO);

    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    @GetMapping("/check")
    Boolean check(@RequestParam(value = "sDate") LocalDate sDate, @RequestParam(value = "meterId")
            String meterId,@RequestParam(value = "customerCode")String customerCode,@RequestParam(value = "gasMeterCode")String gasMeterCode);

    @PostMapping(value = "/importReadMeterData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<Boolean> importReadMeterData(@RequestPart(value = "file") MultipartFile simpleFile);

    /**
     * 通过表具编号查询未缴费的抄表数据
     * @param gasMeterCode 表具编号
     * @return 未缴费抄表数据
     */
    @GetMapping("/queryUnChargedDataIot")
    public R<List<ReadMeterDataIot>> queryUnChargedDataIot(@RequestParam("gasMeterCode")String gasMeterCode);

    @GetMapping(value = "/getSettlementData")
    R<List<ReadMeterDataIot>> getSettlementData();

    @GetMapping(value = "/getPreviousData")
    ReadMeterDataIot getPreviousData(@RequestParam("gasMeterCode") String gasMeterCode, @RequestParam("gasMeterNumber") String gasMeterNumber,@RequestParam("customerCode") String customerCode
            ,@RequestParam("dataTime")LocalDateTime dataTime);

    @PostMapping(value = "getSettlementArrears")
    List<ReadMeterDataIot> getSettlementArrears(@RequestBody SettlementArrearsVO arrearsVO);

    @PostMapping("/updateDataRefundIot")
    R<Boolean> updateDataRefundIot(@RequestBody List<Long> idsList);

    /**
     * 获取最新一条抄表数据-不区分dataType
     * @param gasMeterNumber 表身号
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @param dataTime 冻结时间
     * @return 最新数据
     */
    @GetMapping(value = "/getLatestData")
    ReadMeterDataIot getLatestData(@RequestParam("gasMeterNumber") String gasMeterNumber, @RequestParam("gasMeterCode") String gasMeterCode,
                                   @RequestParam("customerCode") String customerCode ,@RequestParam("dataTime")LocalDateTime dataTime);

    /**
     * 获取拆、换表场景生成的抄表数据
     * @param gasMeterNumber 表身号
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 抄表数据
     */
    @GetMapping(value = "/getSceneData")
    ReadMeterDataIot getSceneData(@RequestParam("gasMeterNumber") String gasMeterNumber, @RequestParam("gasMeterCode") String gasMeterCode,
                                   @RequestParam("customerCode") String customerCode);

    /**
     * 根据欠费编号更新抄表数据
     * @param idsList
     * @return
     */
    @PostMapping(value = "/updateByGasOweCode")
    R<Integer> updateByGasOweCode(@RequestBody List<Long> idsList);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/29 14:17
    * @remark 物联网表的用气统计
    */
    @PostMapping(value = "sts/stsInternetGasMeterGas")
    R<StsDateVo> stsInternetGasMeterGas(@RequestBody StsSearchParam stsSearchParam);
}
