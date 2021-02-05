package com.cdqckj.gmis.iot.qc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterUploadDataPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterUploadDataSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterUploadDataUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterUploadData;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotAuth;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotSubscribe;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterDayDataService;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterUploadDataService;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 物联网气表上报数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/meter")
@Api(value = "IotGasMeterUploadData", tags = "物联网气表上报数据")
//@PreAuth(replace = "iotGasMeterUploadData:")
public class IotGasMeterUploadDataController extends SuperController<IotGasMeterUploadDataService, Long, IotGasMeterUploadData, IotGasMeterUploadDataPageDTO, IotGasMeterUploadDataSaveDTO, IotGasMeterUploadDataUpdateDTO> {

    @Autowired
    private IotGasMeterDayDataService iotGasMeterDayDataService;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<IotGasMeterUploadData> iotGasMeterUploadDataList = list.stream().map((map) -> {
            IotGasMeterUploadData iotGasMeterUploadData = IotGasMeterUploadData.builder().build();
            //TODO 请在这里完成转换
            return iotGasMeterUploadData;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotGasMeterUploadDataList));
    }

    @GetMapping(value = "/queryActivateMeter")
    public IotGasMeterUploadData queryActivateMeter(@RequestParam(value = "gasMeterNumber")String gasMeterNumber){
       return baseService.queryActivateMeter(gasMeterNumber);
    }

    @ApiOperation(value = "查询每日上报数据列表")
    @PostMapping("/queryData")
    public R<Page<DayDataVO>> queryData(@RequestBody DayDataParamsVO params){
        return R.success(iotGasMeterDayDataService.queryDayData(params));
    }
}
