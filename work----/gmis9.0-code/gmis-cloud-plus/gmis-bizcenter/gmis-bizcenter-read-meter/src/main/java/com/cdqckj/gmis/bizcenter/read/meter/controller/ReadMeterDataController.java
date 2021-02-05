package com.cdqckj.gmis.bizcenter.read.meter.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterDataBizService;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.operateparam.ExceptionRuleBizApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterPlanApi;
import com.cdqckj.gmis.readmeter.ReadMeterPlanScopeApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * <p>
 * 抄表导入前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readmeter/data")
@Api(value = "data", tags = "抄表导入")
//@PreAuth(replace = "readMeterData:")
public class ReadMeterDataController {

    @Autowired
    public ReadMeterDataApi readMeterDataApi;
    @Autowired
    public ReadMeterLatestRecordApi recordApi;
    @Autowired
    public CalculateApi calculateApi;
    @Autowired
    public ExceptionRuleBizApi exceptionRuleBizApi;
    @Autowired
    public ReadMeterPlanApi readMeterPlanApi;
    @Autowired
    public ReadMeterPlanScopeApi readMeterPlanScopeApi;
    @Autowired
    public ReadMeterDataBizService readMeterDataBizService;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private GasmeterArrearsDetailBizApi arrearsDetailBizApi;

    @ApiOperation(value = "抄表导入")
    @PostMapping("/readMeterData/import")
    public R<Map<String,Object>> importExcel(@RequestPart(value = "file") MultipartFile simpleFile, @RequestParam(value = "readTime") String readTime) throws IOException {
        return readMeterDataApi.importExcelByTime(simpleFile,readTime);
    }

    @ApiOperation(value = "分页查询抄表数据")
    @PostMapping("/readMeterData/page")
    public R<Page<ReadMeterData>> page(@RequestBody PageParams<ReadMeterDataPageDTO> params){
        return readMeterDataBizService.page(params);
    }

    @ApiOperation(value = "抄表数据审核")
    @PostMapping("/readMeterData/examine")
    public R<List<ReadMeterData>> examine(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<ReadMeterData> list){
        if(list.size()>0){
            List<Long> ids = list.stream().map(ReadMeterData::getId).collect(Collectors.toList());
            List<ReadMeterData> dataList = readMeterDataApi.queryList(ids).getData();
            List<String> codeList = dataList.stream().map(ReadMeterData::getGasMeterCode).collect(Collectors.toList());
            List<ReadMeterLatestRecord> recordList = recordApi.queryListByGasCodes(codeList).getData();
            Boolean bool = list.get(0).getProcessStatus().eq(ProcessEnum.TO_BE_REVIEWED);
            if(dataList.size()>0){
                ChargeEnum charge =  dataList.get(0).getChargeStatus();
                if(null!=charge && (charge.eq(ChargeEnum.NO_CHARGE)||charge.eq(ChargeEnum.CHARGED))){
                    if(bool){
                        Map<String,ReadMeterLatestRecord> map = recordList.stream().collect(Collectors.toMap(ReadMeterLatestRecord::getGasMeterCode, Function.identity()));
                        if(!recordList.isEmpty()){
                            List<ReadMeterData> errList = dataList.stream().filter(item ->{
                                ReadMeterLatestRecord record = map.get(item.getGasMeterCode());
                                if(record.getReadTime().isAfter(item.getReadTime())){
                                    return true;
                                }
                                return false;
                            }).collect(Collectors.toList());
                            if(!errList.isEmpty()){
                                return R.fail("当前气表最新抄表数据已录入，请从当前最新数据依次撤回");
                            }
                        }
                    }
                    dataList = calculateApi.unSettlement(dataList).getData();
                    List<ReadMeterData> failList= dataList.stream().filter(item -> ChargeEnum.CHARGED.eq(item.getChargeStatus())).collect(Collectors.toList());
                    if(failList.size()>0){
                        return R.success(dataList,"存在欠费已交情况，请在柜台操作");
                    }
                    return R.success(dataList);
                }else{
                    bool = list.get(0).getProcessStatus().eq(ProcessEnum.APPROVED);
                    R<List<ReadMeterData>> listR = readMeterDataApi.examine(list);
                    List<ReadMeterDataUpdateDTO> updateDTOList = null;
                    //调用计费接口
                    if(bool){
                        List<ReadMeterData> calList = listR.getData();
                        calList.stream().forEach(data ->{
                            data.setReviewTime(LocalDateTime.now());
                        });
                        List<ReadMeterData> resultList = calculateApi.settlement(calList,0).getData();
                        updateDTOList = resultList.stream().map(item ->{
                            if(null==item.getSettlementTime()){
                                item.setProcessStatus(ProcessEnum.APPROVED);
                            }else{
                                item.setProcessStatus(ProcessEnum.SETTLED);
                            }
                            ReadMeterDataUpdateDTO d = BeanUtil.toBean(item, ReadMeterDataUpdateDTO.class);
                            return d;
                        }).collect(Collectors.toList());
                        readMeterDataApi.updateBatch(updateDTOList);
                    }
                    return listR;
                }
            }
        }
        return R.fail("请至少选择一条数据");
    }

    @ApiOperation(value = "抄表数据撤回到未抄表")
    @PostMapping("/readMeterData/withdraw")
    public R<Boolean> withdraw(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<Long> ids){
        return readMeterDataApi.withdraw(ids);
    }

    @ApiOperation(value = "抄表数据手动结算")
    @PostMapping("/readMeterData/settlement")
    public R<List<ReadMeterData>> settlement(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<Long> list){
        R<List<ReadMeterData>> listR = null;
        listR = readMeterDataApi.settlement(list);
        //调用计费接口
        List<ReadMeterData> calList = listR.getData();
        calculateApi.settlement(calList,0);
        List<ReadMeterDataUpdateDTO> updateDTOList = calList.stream().map(item ->{
            ReadMeterDataUpdateDTO d = BeanUtil.toBean(item, ReadMeterDataUpdateDTO.class);
            d.setProcessStatus(ProcessEnum.SETTLED);
            d.setChargeStatus(ChargeEnum.NO_CHARGE);
            return d;
        }).collect(Collectors.toList());
        readMeterDataApi.updateBatch(updateDTOList);
        return listR;
    }

    @ApiOperation(value = "抄表数据录入及修改接口")
    @PostMapping("/readMeterData/update")
    public R<ReadMeterData> update(@RequestBody ReadMeterData updateDTO){
        return readMeterDataApi.input(updateDTO);
    }

    /**
     * 拆表，换表时旧表费用结算
     * @param data
     * @return
     */
    @ApiOperation(value = "拆表，换表时旧表费用结算")
    @PostMapping("/readMeterData/costSettlement")
    public R<ReadMeterData> costSettlement(@RequestBody ReadMeterData data){
        return readMeterDataBizService.costSettlement(data);
    }

    /**
     * pc端数据录入
     * @param list
     * @return
     */
    @ApiOperation(value = "抄表数据批量修改")
    @PostMapping("/readMeterData/updateList")
    public R<Boolean> updateList(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<ReadMeterData> list){
        list.stream().forEach(updateDTO -> {
            updateDTO.setDataStatus(0);
        });
        return readMeterDataApi.inputList(list);
    }

    /**
     * 根据抄表月份和抄表册id导出
     * @param readMeterData
     * @param httpResponse
     * @throws IOException
     */
    @ApiOperation(value = "根据抄表月份和抄表册id导出")
    @PostMapping("/readMeterData/exportReadMeterData")
    public void exportReadMeterData(@RequestBody ReadMeterData readMeterData, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        String time = readMeterData.getReadMeterYear()+"年"+readMeterData.getReadMeterMonth()+"月";
        Response response = readMeterDataApi.exportReadMeterData(readMeterData);
        ExportUtil.exportExcel(response,httpResponse,time+"抄表名单");
    }

    /**
     * 判断表是否可以过户
     * false-有未结算数据，不能开户，true-可以开户
     * @param gasMeter
     * @return
     */
    @ApiOperation(value = "判断表是否可以过户")
    @PostMapping("/readMeterData/isFinished")
    public R<Boolean> isFinished(@RequestBody CustomerGasDto gasMeter){
        return readMeterDataBizService.isFinished(gasMeter);
    }

    /**
     * （缴费通知单）获取欠费信息
     * @param list
     * @return
     */
    @ApiOperation(value = "（缴费通知单）获取欠费信息")
    @PostMapping("/readMeterData/getArrearsInformation")
    public R<List<GasmeterArrearsDetail>> getArrearsInformation(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<ReadMeterData> list){
        List<Long> ids = list.stream().map(ReadMeterData::getId).collect(Collectors.toList());
        list = readMeterDataApi.queryList(ids).getData();
        List<GasmeterArrearsDetail> resultList = new ArrayList<>();
        if(list.size()>0){
            Map<Long,ReadMeterData> map = list.stream().collect(Collectors.toMap(ReadMeterData::getId, Function.identity()));
            resultList = arrearsDetailBizApi.getByReadMeterIds(ids).getData();
            if(resultList.size()>0){
                resultList.forEach(e ->{
                    ReadMeterData data = map.get(e.getReadmeterDataId());
                    e.setStartDate(data.getLastReadTime());
                    e.setEndDate(data.getRecordTime());
                });
            }
        }
        return R.success(resultList);
    }
}
