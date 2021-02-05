package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.MeterStockBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterArchiveService;
import com.cdqckj.gmis.devicearchive.dto.GasMeterConfDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockPageDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.MeterStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 杨家兵
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/archive/devicearchive")
@Api(value = "devicearchive", tags = "设备档案")
public class DeviceArchiveController {

    @Autowired
    private MeterStockBizApi meterStockBizApi;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private GasMeterArchiveService gasMeterArchiveService;

    /**
     * @param saveDTO
     * @return
     */
    @PostMapping(value = "/meterstock/add")
    @ApiOperation(value = "新增表具库存")
    R<MeterStock> saveMeterStock(@RequestBody MeterStockSaveDTO saveDTO){
        return meterStockBizApi.save(saveDTO);
    }

    /**
     * @param updateDTO
     * @return
     */
    @PutMapping(value = "/meterstock/update")
    @ApiOperation(value = "更新表具库存")
    R<MeterStock> updateMeterStock(@RequestBody MeterStockUpdateDTO updateDTO){
       return meterStockBizApi.update(updateDTO);

    }

    /**
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/meterstock/delete")
    @ApiOperation(value = "删除表具库存")
    R<Boolean> deleteMeterStock(@RequestParam("ids[]") List<Long> ids){
         return meterStockBizApi.delete(ids);
    }

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/meterstock/page")
    @ApiOperation(value = "分页查询表具库存")
    R<Page<MeterStock>> pageMeterStock(@RequestBody PageParams<MeterStockPageDTO> params){
        return meterStockBizApi.page(params);
    }



    /**
     * 根据气表档案号，获取厂家，版号，型号相关配置数据。
     * @param gasMeterCode
     * @return
     */
    @GetMapping(value = "/findGasMeterConfInfoByCode/{gasMeterCode}")
    @ApiOperation(value = "根据气表档案号，获取厂家，版号，型号相关配置数据")
    R<GasMeterConfDTO> findGasMeterConfInfoByCode(@PathVariable(value = "gasMeterCode") String  gasMeterCode){
        return R.success(gasMeterArchiveService.gasMeterConfDTOByCode(gasMeterCode));
    }

}
