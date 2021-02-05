package com.cdqckj.gmis.devicearchive.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoPageDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表使用情况
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeterInfo")
@Api(value = "GasMeterInfo", tags = "气表使用情况")
//@PreAuth(replace = "gasMeterInfo:")
public class GasMeterInfoController extends SuperController<GasMeterInfoService, Long, GasMeterInfo, GasMeterInfoPageDTO, GasMeterInfoSaveDTO, GasMeterInfoUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasMeterInfo> gasMeterInfoList = list.stream().map((map) -> {
            GasMeterInfo gasMeterInfo = GasMeterInfo.builder().build();
            //TODO 请在这里完成转换
            return gasMeterInfo;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasMeterInfoList));
    }

    /**
     * 根据气表code查询
     * @param gasMeterCode
     * @return
     */
    @GetMapping(value = "/queryByGasMeterCode")
    public R<GasMeterInfo> getByGasMeterCode(@RequestParam(value = "gasMeterCode")String gasMeterCode){
        return R.success(baseService.getByGasMeterCode(gasMeterCode));
    }

    /**
     * 根据气表code and customer code 查询
     * @param gasMeterCode
     * @return
     */
    @GetMapping(value = "/getByMeterAndCustomerCode")
    public R<GasMeterInfo> getByMeterAndCustomerCode(@RequestParam(value = "gasMeterCode")String gasMeterCode,
    @RequestParam(value = "customerCode")String customerCode
    ){
        return R.success(baseService.getByMeterAndCustomerCode(gasMeterCode,customerCode));
    }

    /**
     * 根据价格id更新
     * @param meterInfoVO
     * @return
     */
    @PostMapping(value = "/updateByPriceId")
    R<Integer> updateByPriceId(@RequestBody MeterInfoVO meterInfoVO){
        return baseService.updateByPriceId(meterInfoVO);
    }

    /**
     * 根据价格id更新
     * @param meterInfoVO
     * @return
     */
    @PostMapping(value = "/updateCycleByPriceId")
    R<Integer> updateCycleByPriceId(@RequestBody MeterInfoVO meterInfoVO){
        return baseService.updateCycleByPriceId(meterInfoVO);
    }

    /**
     * 根据气表编号更新气表使用情况
     * @param gasCode
     * @param gasMeterInfo
     * @return
     */
    @ApiOperation("根据气表编号更新气表使用情况")
    @PutMapping("/updateByGasMeterCode")
    public R<Boolean> updateByGasMeterCode(@RequestParam("gasCode") String gasCode, @RequestBody GasMeterInfo gasMeterInfo){
        LbqWrapper<GasMeterInfo> meterInfoLbqWrapper = Wraps.lbQ();
        meterInfoLbqWrapper.eq(GasMeterInfo::getGasmeterCode, gasCode);
        return success(baseService.update(gasMeterInfo, meterInfoLbqWrapper));
    }
}
