package com.cdqckj.gmis.readmeter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.RecordYear;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterMonthGas;
import com.cdqckj.gmis.readmeter.enumeration.*;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotService;
import com.cdqckj.gmis.readmeter.service.ReadMeterMonthGasService;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import com.cdqckj.gmis.readmeter.vo.SettlementArrearsVO;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 物联网抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterDataIot")
@Api(value = "ReadMeterDataIot", tags = "物联网抄表数据")
@PreAuth(replace = "readMeterDataIot:")
public class ReadMeterDataIotController extends SuperController<ReadMeterDataIotService, Long,
        ReadMeterDataIot, ReadMeterDataIotPageDTO, ReadMeterDataIotSaveDTO, ReadMeterDataIotUpdateDTO> {

    @Autowired
    public ReadMeterMonthGasService monthGasService;

    @ApiOperation(value = "录入抄表数据信息")
    @PostMapping("/inputData")
//    @Transactional(rollbackFor = Exception.class)
    public R<ReadMeterDataIot> inputData(@RequestBody ReadMeterDataIotUpdateDTO dto) {
        ReadMeterDataIot oldData = Optional.ofNullable(baseService.getById(dto.getId()))
                .orElseThrow(() -> new BizException("没有此抄表数据信息：" + dto.getId()));

//        boolean update = baseService.update(Wraps.<ReadMeterData>lbU()
//                .set(ReadMeterData::getCurrentTotalGas, dto.getCurrentTotalGas())
//                .set(ReadMeterData::getMonthUseGas, dto.getMonthUseGas())
//                .set(ReadMeterData::getDataStatus, 0) // 代表抄表完成
//                .eq(ReadMeterData::getId, dto.getId()));

        oldData.setCurrentTotalGas(dto.getCurrentTotalGas())
                .setMonthUseGas(dto.getMonthUseGas())
                .setDataStatus(0);// 代表抄表完成
        boolean b = baseService.updateById(oldData);

        //TODO 同步需要修改其他 相关联的 数据信息......

        return success(oldData);
    }

    @ApiOperation(value = "判断是否上报过数据")
    @GetMapping("/check")
    public Boolean check(@RequestParam(value = "sDate") LocalDate sDate, @RequestParam(value = "meterId") String meterId,@RequestParam(value = "customerCode")String customerCode,@RequestParam(value = "gasMeterCode")String gasMeterCode){
        return baseService.check(sDate,meterId,customerCode,gasMeterCode);
    }

    /**
     * Excel导入后的操作
     *
     * @param simpleFile
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/importReadMeterData")
    public R<Boolean> importReadMeterData(@RequestParam(value = "file") MultipartFile simpleFile,
                                                              HttpServletRequest request,HttpServletResponse response) throws Exception {
        return importExcel(simpleFile,request,response);
        /*ImportParams params = new ImportParams();
        //接收原始数据
        //List<Map<String, Object>> list = ExcelImportUtil.importExcel(simpleFile.getInputStream(), Map.class, params);;//getDataList(simpleFile, request);
        List<Map<String, Object>> list = ImportUtil.readExcel(simpleFile.getInputStream());
        Map<Integer, ReadMeterDataIot> map = getEntityMap(list);
        return R.success(map);*/
    }

    /**
     * 根据气表编码，抄表年月，查询抄表数据(默认查询未通过的)
     *
     * @param
     * @return
     */
    @ApiOperation(value = "根据气表编码，抄表年月，查询抄表数据", notes = "根据气表编码，抄表年月，查询抄表数据")
    @PostMapping("/getMeterDataByCode")
    @SysLog("根据气表编码，抄表年月，查询抄表数据")
    public R<List<ReadMeterDataIot>> getMeterDataByCode(@RequestBody List<ReadMeterDataIot> readMeterDataList, @RequestParam(value = "processEnum", required = false) ProcessEnum process) {//, List<String> meterCodelist, List<Integer> yearlist, List<Integer> monthlist
        List<String> meterCodelist = readMeterDataList.stream().map(ReadMeterDataIot::getGasMeterCode).collect(Collectors.toList());
        List<Integer> yearlist = readMeterDataList.stream().map(ReadMeterDataIot::getReadMeterYear).distinct().collect(Collectors.toList());
        List<Integer> monthlist = readMeterDataList.stream().map(ReadMeterDataIot::getReadMeterMonth).distinct().collect(Collectors.toList());
        return baseService.getMeterDataByCode(meterCodelist, yearlist, monthlist, process);
    }


    @PostMapping("/getHistory")
    @SysLog("获取最近三次历史数据")
    public R<Map<String, BigDecimal>> getHistory(@RequestBody List<ReadMeterDataIotUpdateDTO> list) {
        Map<String, BigDecimal> map = new HashMap<>();
        Map<String, List<ReadMeterDataIot>> groupByCode = new HashMap<>();
        //按年月分组
        Map<Date, List<ReadMeterDataIotUpdateDTO>> groupByTime = list.stream().collect(Collectors.groupingBy(ReadMeterDataIotUpdateDTO::getReadTime));
        for (Map.Entry<Date, List<ReadMeterDataIotUpdateDTO>> entry : groupByTime.entrySet()) {
            List<String> meterCodeList = entry.getValue().stream().map(ReadMeterDataIotUpdateDTO::getGasMeterCode).collect(Collectors.toList());
            List<ReadMeterDataIot> dataList = baseService.getHistory(meterCodeList, entry.getKey()).getData();
            Map<String, List<ReadMeterDataIot>> m = dataList.stream().collect(Collectors.groupingBy(ReadMeterDataIot::getGasMeterCode));
            groupByCode.putAll(m);
        }
        for (Map.Entry<String, List<ReadMeterDataIot>> entry : groupByCode.entrySet()) {
            List<ReadMeterDataIot> li = entry.getValue();
            if (null != li && li.size() > 0) {
                BigDecimal monthUseGas = li.stream().map(ReadMeterDataIot::getMonthUseGas).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(li.size()), 2, BigDecimal.ROUND_HALF_UP);
                map.put(entry.getKey(), monthUseGas);
            } else {
                map.put(entry.getKey(), new BigDecimal(0));
            }
        }
        return R.success(map);
    }

    @ApiOperation(value = "抄表数据审核")
    @PostMapping("/examine")
    public R<List<ReadMeterDataIot>> examine(@RequestBody List<ReadMeterDataIot> dataList) {
        ProcessIotEnum status = dataList.get(0).getProcessStatus();
        String rejectMsg = dataList.get(0).getReviewObjection();
        List<Long> ids = dataList.stream().map(ReadMeterDataIot::getId).collect(Collectors.toList());
        List<ReadMeterDataIot> list = getReadMeterData(ids);
        //list = baseService.listByIds(ids);
        if (list.size() > 0) {
            List<ProcessIotEnum> statusList = list.stream().map(ReadMeterDataIot::getProcessStatus).distinct().collect(Collectors.toList());
            if (statusList.size() != 1) {
                R.fail("审核数据状态不一致，请重新选择");
            } else {
                //当前状态
                ProcessIotEnum process = statusList.get(0);
                switch (status) {
                    case TO_BE_REVIEWED:
                        //相当于撤回
                        if (!process.eq(ProcessIotEnum.SUBMIT_FOR_REVIEW)) {
                            return R.fail("当前状态无法撤回");
                        }
                        break;
                    case SUBMIT_FOR_REVIEW:
                        //提审
                        if (process.eq(ProcessIotEnum.TO_BE_REVIEWED) /*|| process.eq(ProcessEnum.REVIEW_REJECTED)*/) {
                            return R.fail("当前状态无法再次提审");
                        }
                        break;
                    case APPROVED:
                        //审核通过
                        if (!process.eq(ProcessIotEnum.SUBMIT_FOR_REVIEW)) {
                            return R.fail("非提审状态无法审核");
                        }
                        break;
                    case REVIEW_REJECTED:
                        if (!process.eq(ProcessIotEnum.SUBMIT_FOR_REVIEW)) {
                            return R.fail("非提审状态无法驳回");
                        }
                        break;
                    default:
                        return R.fail("无效操作");
                }
                list.stream().forEach(dto -> {
                    dto.setProcessStatus(status);
                    dto.setReviewObjection(rejectMsg);
                });
                baseService.updateBatchById(list);
            }
        }
        return success(list);
    }

    @ApiOperation(value = "抄表数据审核")
    @PostMapping("/settlement")
    public R<List<ReadMeterDataIot>> settlement(@RequestBody List<Long> dataList) {
        List<ReadMeterDataIot> list = getReadMeterData(dataList);
        return R.success(list);
    }

    /**
     * 结算排序
     *
     * @param ids
     * @return
     */
    public List<ReadMeterDataIot> getReadMeterData(@RequestBody List<Long> ids) {
        LambdaQueryWrapper<ReadMeterDataIot> lambda = new LambdaQueryWrapper<>();
        lambda.orderByAsc(ReadMeterDataIot::getGasMeterCode);
        lambda.orderByAsc(ReadMeterDataIot::getCreateTime);
        lambda.in(ReadMeterDataIot::getId, ids);
        List<ReadMeterDataIot> list = baseService.list(lambda);
        return list;
    }

    @ApiOperation(value = "根据客户编码获取抄表数据")
    @PostMapping("/getDataByCustomerCode")
    R<List<ReadMeterDataIot>> getDataByCustomerCode(@RequestBody List<String> list) {
        LambdaQueryWrapper<ReadMeterDataIot> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterDataIot::getCustomerCode, list);
        lambda.ne(ReadMeterDataIot::getDataStatus, 0);
        return R.success(baseService.list(lambda));
    }

    ;

    @ApiOperation(value = "抄表数据录入及修改接口")
    @PostMapping("/input")
    R<ReadMeterDataIot> input(@RequestBody ReadMeterDataIot updateDTO) {
        ReadMeterDataIot oldData = Optional.ofNullable(baseService.getById(updateDTO.getId())).orElseThrow(() -> new BizException("无此抄表数据"));
        updateDTO.setDataStatus(0);
        // 1.跟新data 抄表数据
        baseService.updateById(updateDTO);
        return R.success(updateDTO);
    }

    @ApiOperation(value = "抄表数据录入及修改接口")
    @PostMapping("/inputList")
    R<Boolean> inputList(@RequestBody List<ReadMeterDataIot> list){
        List<Long> ids = list.stream().map(ReadMeterDataIot::getId).collect(Collectors.toList());
        List<ReadMeterDataIot> dataList = baseService.listByIds(ids);
        //修改改变计划表
        baseService.updateBatchById(list);
        return R.success();
    }

    /**
     * 反射设置  meterMonthGas 根据月份查询字段并赋值
     *
     * @param lastMonth
     * @param meterMonthGas
     */
    public void setLastTotalGas(Integer lastMonth, ReadMeterMonthGas meterMonthGas, BigDecimal currentTotalGas) {
        String monthStr = RecordYear.getType(lastMonth);
        try {
            if (null != meterMonthGas) {
                Field field = ReadMeterMonthGas.class.getDeclaredField(monthStr);
                field.setAccessible(true);
                field.set(meterMonthGas, currentTotalGas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据表身号查询
     * @param gasMeterNumber
     * @return
     */
    @GetMapping("/getDataByMeterNo")
    public R<ReadMeterDataIot> getDataByMeterNo(@RequestParam(value = "gasMeterNumber") String gasMeterNumber){
        return  R.success(baseService.getDataByMeterNo(gasMeterNumber));
    }

    /**
     * Updated upstream
     * 通过表具编号查询未缴费的抄表数据
     * @param gasMeterCode 表具编号
     * @return 未缴费抄表数据
     */
    @ApiOperation("通过表具编号查询未缴费的抄表数据")
    @GetMapping("/queryUnChargedDataIot")
    public R<List<ReadMeterDataIot>> queryUnChargedDataIot(@RequestParam("gasMeterCode")String gasMeterCode) {
        LbqWrapper<ReadMeterDataIot> lqw = Wraps.lbQ();
        lqw.eq(ReadMeterDataIot::getGasMeterCode, gasMeterCode)
                .eq(ReadMeterDataIot::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .eq(ReadMeterDataIot::getDataType, DataTypeEnum.ORDINARY_DATA.getCode())
                .and(wrapper -> wrapper.eq(ReadMeterDataIot::getChargeStatus, ChargeIotEnum.NO_CHARGE.getCode())
                        .or().isNull(ReadMeterDataIot::getChargeStatus))
                .orderByAsc(ReadMeterDataIot::getDataTime);
        List<ReadMeterDataIot> list = baseService.list(lqw);
        return R.success(list);
    }

    /**
     * 查询可结算数据
     * @return
     */
    @GetMapping(value = "/getSettlementData")
    public R<List<ReadMeterDataIot>> getSettlementData(){
        return  R.success(baseService.settlement());
    }

    /**
     * 获取前一条数据
     * @param gasMeterNumber
     * @param dataTime
     * @return
     */
    @GetMapping(value = "/getPreviousData")
    public ReadMeterDataIot getPreviousData(@RequestParam("gasMeterCode") String gasMeterCode, @RequestParam("gasMeterNumber") String gasMeterNumber,@RequestParam("customerCode") String customerCode
            ,@RequestParam("dataTime")LocalDateTime dataTime){
        return baseService.getPreviousData(gasMeterCode, gasMeterNumber,customerCode,dataTime);
    }

    /**
     *  分页查询列表
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    public R<Page<ReadMeterDataIotVo>> pageList(@RequestBody PageParams<ReadMeterDataIotPageDTO> params) {
        return R.success(baseService.pageList(params));
    }
     /**
     * 物联网中心计费获取欠费
     * @param arrearsVO
     * @return
     */
    @PostMapping(value = "getSettlementArrears")
    List<ReadMeterDataIot> getSettlementArrears(@RequestBody SettlementArrearsVO arrearsVO){
        return baseService.getSettlementArrears(arrearsVO);
    }

    @ApiOperation(value = "退费时修改数据")
    @PostMapping("/updateDataRefundIot")
    R<Boolean> updateDataRefundIot(@RequestBody List<Long> idsList) {
        return R.success(baseService.updateDataRefundIot(idsList));
    };

    /**
     * 获取最新一条抄表数据-不区分dataType
     * @param gasMeterNumber 表身号
     * @param gasMeterCode 表身编号
     * @param customerCode 客户编号
     * @param dataTime 冻结时间
     * @return 最新数据
     */
    @GetMapping(value = "/getLatestData")
    ReadMeterDataIot getLatestData(@RequestParam("gasMeterNumber") String gasMeterNumber,@RequestParam("gasMeterCode") String gasMeterCode,
                                   @RequestParam("customerCode") String customerCode ,@RequestParam("dataTime")LocalDateTime dataTime){
        return  baseService.getLatestData(gasMeterNumber,gasMeterCode, customerCode, dataTime);
    }

    /**
     * 获取拆、换表场景生成的抄表数据
     * @param gasMeterNumber 表身号
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 抄表数据
     */
    @GetMapping(value = "/getSceneData")
    ReadMeterDataIot getSceneData(@RequestParam("gasMeterNumber") String gasMeterNumber,@RequestParam("gasMeterCode") String gasMeterCode,
                                   @RequestParam("customerCode") String customerCode){
        LbqWrapper<ReadMeterDataIot> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(ReadMeterDataIot::getGasMeterCode, gasMeterCode)
                .eq(ReadMeterDataIot::getCustomerCode, customerCode)
                .eq(ReadMeterDataIot::getGasMeterNumber, gasMeterNumber)
                .in(ReadMeterDataIot::getDataType, DataTypeEnum.REMOVE_DATA.getCode(), DataTypeEnum.CHANGE_DATA.getCode())
                .last("limit 1");
        return  baseService.getOne(lbqWrapper);
    }

    /**
     * 根据欠费编号更新抄表数据
     * @param idsList
     * @return
     */
    @PostMapping(value = "/updateByGasOweCode")
    R<Integer> updateByGasOweCode(@RequestBody List<Long> idsList){
        return R.success(baseService.updateByGasOweCode(idsList));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/29 14:17
     * @remark 物联网表的用气统计
     */
    @PostMapping(value = "sts/stsInternetGasMeterGas")
    R<StsDateVo> stsInternetGasMeterGas(@RequestBody StsSearchParam stsSearchParam){

        StsDateVo vo = new StsDateVo();
        String type = stsSearchParam.getSearchKeyValue("gasMeterType");

        stsSearchParam.setStartDay(LocalDateUtil.beforeDay(LocalDate.now()));
        stsSearchParam.setEndDay(LocalDate.now());
        BigDecimal day = this.baseService.stsInternetGasMeterGas(stsSearchParam, type);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(LocalDate.now()));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(LocalDate.now()));
        BigDecimal month = this.baseService.stsInternetGasMeterGas(stsSearchParam, type);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(LocalDate.now()));
        BigDecimal year = this.baseService.stsInternetGasMeterGas(stsSearchParam, type);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal all = this.baseService.stsInternetGasMeterGas(stsSearchParam, type);

        vo.setTodayNum(day);
        vo.setMonthNum(month);
        vo.setYearNum(year);
        vo.setAllNum(all);
        return R.success(vo);
    }

}
