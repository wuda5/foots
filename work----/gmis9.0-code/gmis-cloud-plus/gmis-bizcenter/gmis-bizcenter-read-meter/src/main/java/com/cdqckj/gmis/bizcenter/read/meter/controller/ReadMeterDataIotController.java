package com.cdqckj.gmis.bizcenter.read.meter.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterDataIotBizService;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotErrorApi;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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
@RequestMapping("/readmeter/dataiot")
@Api(value = "data", tags = "物联网抄表")
//@PreAuth(replace = "readMeterData:")
public class ReadMeterDataIotController {

    @Autowired
    public ReadMeterDataIotApi readMeterDataIotApi;
    @Autowired
    public ReadMeterDataIotErrorApi readMeterDataIotErrorApi;
    @Autowired
    public CalculateApi calculateApi;
    @Autowired
    ReadMeterDataIotBizService readMeterDataIotService;

    @ApiOperation(value = "抄表导入")
    @PostMapping("/readMeterData/import")
    public R<Boolean> importExcel(@RequestPart(value = "file") MultipartFile simpleFile) {
        return readMeterDataIotApi.importReadMeterData(simpleFile);
    }

    @ApiOperation(value = "分页查询抄表数据")
    @PostMapping("/readMeterData/page")
    public R<Page<ReadMeterDataIotVo>> page(@RequestBody PageParams<ReadMeterDataIotPageDTO> params){
         String status = params.getModel().getChargeStatus();
        //待收费（必须是已审核通过的数据）
        /*if(ChargeEnum.TO_BE_CHARGED.getCode().equals(status)){
            params.getModel().setProcessStatus(ProcessEnum.APPROVED.getCode());
        }
        if(ProcessEnum.APPROVED.getCode().equals(params.getModel().getProcessStatus())){
            params.getModel().setChargeStatus(ChargeEnum.TO_BE_CHARGED.getCode());
        }*/
        return readMeterDataIotService.page(params);
    }

    @ApiOperation(value = "分页查询物联网异常抄表数据")
    @PostMapping("/readMeterData/pageError")
    public R<Page<ReadMeterDataIotError>> pageError(@RequestBody PageParams<ReadMeterDataIotErrorPageDTO> params){
        return readMeterDataIotErrorApi.page(params);
    }

    @ApiOperation(value = "抄表数据审核")
    @PostMapping("/readMeterData/examine")
    public R<List<ReadMeterDataIot>> examine(@RequestBody List<ReadMeterDataIot> list){
        if(list.size()>0){
            Boolean bool = list.get(0).getProcessStatus().eq(ProcessIotEnum.APPROVED);
            R<List<ReadMeterDataIot>> listR = readMeterDataIotApi.examine(list);
            //调用计费接口
            return listR;
        }else {
            return R.fail("请至少选择一条数据");
        }
    }

    @ApiOperation(value = "抄表数据录入及修改接口")
    @PostMapping("/readMeterData/update")
    public R<ReadMeterDataIot> update(@RequestBody ReadMeterDataIot updateDTO){
        return readMeterDataIotApi.input(updateDTO);
    }

    @ApiOperation(value = "物联网抄表异常数据批量纠正")
    @PostMapping("/readMeterData/updateErrorList")
    public R<Boolean> updateErrorList(@RequestBody List<ReadMeterDataIotErrorUpdateDTO> list){
        list.stream().forEach(updateDTO -> {
            updateDTO.setDataStatus(1);
        });
        Boolean bool = readMeterDataIotErrorApi.updateBatch(list).getData();
        if(bool){
            List<ReadMeterDataIotSaveDTO> readMeterDataList = list.stream().map(dto ->{
                ReadMeterDataIotSaveDTO save = BeanUtil.toBean(dto, ReadMeterDataIotSaveDTO.class);
                save.setProcessStatus(ProcessIotEnum.TO_BE_REVIEWED);
                return save;
            }).collect(Collectors.toList());
            readMeterDataIotApi.saveList(readMeterDataList);
            return R.success();
        }
        return R.fail("纠正失败");
    }

    @ApiOperation(value = "物联网抄表正常数据批量纠正")
    @PostMapping("/readMeterData/updateList")
    public R<Boolean> updateList(@RequestBody List<ReadMeterDataIotUpdateDTO> list){
        return readMeterDataIotApi.updateBatch(list);
    }

    @ApiOperation(value = "导出物联网抄表模板")
    @PostMapping("/readMeterData/exportTemplate")
    public void exportReadMeterList(@RequestBody PageParams<ReadMeterDataIotPageDTO> params, HttpServletResponse httpResponse) throws IOException {
        Response response = readMeterDataIotApi.exportCombobox(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }
}
