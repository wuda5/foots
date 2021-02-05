package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.operation.config.service.GaspriceService;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import com.cdqckj.gmis.operateparam.PenaltyBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.dto.*;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.vo.PenUseTypeVo;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/gasprice")
@Api(value = "gasprice", tags = "气价配置")
//@PreAuth(replace = "street:")
public class GaspriceController {

    @Autowired
    public PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    public PenaltyBizApi penaltyBizApi;
    @Autowired
    public UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    public CommonConfigurationApi commonConfigurationApi;
    @Autowired
    public GaspriceService gaspriceService;
    @Autowired
    public ReadMeterDataApi dataApi;
    @Autowired
    private GasMeterBookRecordApi recordApi;

    @ApiOperation(value = "新增用气类型信息")
    @PostMapping("/useGasType/add")
    public R<UseGasType> saveUseGasType(@RequestBody UseGasTypeSaveDTO useGasTypeSaveDTO){
        return gaspriceService.saveUseGasType(useGasTypeSaveDTO);
    }

    @ApiOperation(value = "更新用气类型信息")
    @PutMapping("/useGasType/update")
    public R<UseGasType> updateUseGasType(@RequestBody UseGasTypeUpdateDTO useGasTypeUpdateDTO){
        if (useGasTypeUpdateDTO.getAlarmMoney()!=null && useGasTypeUpdateDTO.getAlarmMoneyTwo()!=null
                && BigDecimalUtils.greaterThan(useGasTypeUpdateDTO.getAlarmMoney(),useGasTypeUpdateDTO.getAlarmMoneyTwo())){
            return R.fail("1级报警金额不能大于2级报警金额");
        }
        Boolean check = useGasTypeBizApi.checkUseGasType(useGasTypeUpdateDTO.getId(), useGasTypeUpdateDTO.getUseGasTypeName()).getData();
        if (check){
            return R.fail("修改用气类型重复");
        }
        GasMeterBookRecordUpdateDTO recordUpdateDTO = new GasMeterBookRecordUpdateDTO();
        recordUpdateDTO.setUseGasTypeId(useGasTypeUpdateDTO.getId())
                .setUseGasTypeName(useGasTypeUpdateDTO.getUseGasTypeName());
        Boolean bool = recordApi.updateByGasType(recordUpdateDTO).getData();
        ReadMeterDataUpdateDTO data = new ReadMeterDataUpdateDTO();
        data.setUseGasTypeId(useGasTypeUpdateDTO.getId())
                .setUseGasTypeName(useGasTypeUpdateDTO.getUseGasTypeName());
        dataApi.updateDataByGasType(data).getData();
        return useGasTypeBizApi.update(useGasTypeUpdateDTO);
    }

    @ApiOperation(value = "删除用气类型信息")
    @DeleteMapping("/useGasType/delete")
    public R<Boolean> deleteUseGasType(@RequestParam("ids[]") List<Long> id){
        return useGasTypeBizApi.delete(id);
    }

    @ApiOperation(value = "分页查询用气类型信息")
    @PostMapping("/useGasType/page")
    public R<Page<UseGasType>> pageUseGasType(@RequestBody PageParams<UseGasTypePageDTO> params){
        return useGasTypeBizApi.page(params);
    }

    @ApiOperation(value = "新增阶梯价格方案信息")
    @PostMapping("/priceScheme/addLadder")
    @GlobalTransactional
    public R<PriceScheme> saveLadderPriceScheme(@RequestBody PriceSchemeSaveDTO priceSchemeSaveDTO){
        return gaspriceService.saveLadderPriceScheme(priceSchemeSaveDTO);
    }

    @ApiOperation(value = "新增固定价格方案信息")
    @PostMapping("/priceScheme/addFixed")
    @GlobalTransactional
    public R<PriceScheme> saveFixedPriceScheme(@RequestBody PriceSchemeSaveDTO priceSchemeSaveDTO){
        return gaspriceService.saveFixedPriceScheme(priceSchemeSaveDTO);
    }

    @ApiOperation(value = "新增采暖价格方案信息")
    @PostMapping("/priceScheme/addHeating")
    @GlobalTransactional
    public R<PriceScheme> saveHeatingPriceScheme(@RequestBody List<PriceSchemeSaveDTO> list){
        return gaspriceService.saveHeatingPriceScheme(list);
    }

    @ApiOperation(value = "更新价格方案信息")
    @PutMapping("/priceScheme/update")
    public R<PriceScheme> updatePriceScheme(@RequestBody PriceSchemeUpdateDTO priceSchemeUpdateDTO){
        return priceSchemeBizApi.update(priceSchemeUpdateDTO);
    }

    @ApiOperation(value = "删除价格方案信息")
    @DeleteMapping("/priceScheme/delete")
    public R<Boolean> deletePriceScheme(@RequestParam("ids[]") List<Long> id){
        return priceSchemeBizApi.delete(id);
    }

    @ApiOperation(value = "分页查询价格方案息信息")
    @PostMapping("/priceScheme/page")
    public R<Page<PriceScheme>> pagePriceScheme(@RequestBody PageParams<PriceSchemePageDTO> params){
        return priceSchemeBizApi.page(params);
    }

    @ApiOperation(value = "新增滞纳金信息")
    @PostMapping("/penalty/add")
    public R<Penalty> savePenalty(@RequestBody PenaltySaveDTO penaltySaveDTO){
        return gaspriceService.savePenalty(penaltySaveDTO);
    }

    @ApiOperation(value = "更新滞纳金信息")
    @PutMapping("/penalty/update")
    public R<Penalty> updatePenalty(@RequestBody PenaltyUpdateDTO penaltyUpdateDTO){
        return gaspriceService.updatePenalty(penaltyUpdateDTO);
    }

    @ApiOperation(value = "删除滞纳金信息")
    @DeleteMapping("/penalty/delete")
    public R<Boolean> deletePenalty(@RequestParam("ids[]") List<Long> id){
        return penaltyBizApi.delete(id);
    }

    @ApiOperation(value = "分页查询滞纳金息信息")
    @PostMapping("/penalty/page")
    public R<Page<PenUseTypeVo>> pagePenalty(@RequestBody PageParams<PenaltyPageDTO> params){
        List<UseGasType> useGasTypeList = useGasTypeBizApi.query(new UseGasType()).getData();
        HashMap<String,String> map=new HashMap<>();
        for (UseGasType useGasType : useGasTypeList) {
            map.put(useGasType.getId().toString(),useGasType.getUseGasTypeName());
        }
        List<Penalty> penaltyList = penaltyBizApi.page(params).getData().getRecords();
        List<PenUseTypeVo> penalties=new ArrayList<>();
        for (int i=0;i<penaltyList.size(); i++ ) {
            Penalty penalty=penaltyList.get(i);
            PenUseTypeVo penUseTypeVo=new PenUseTypeVo();
            List<String> ids = JSON.parseArray(penalty.getUseGasTypeId(), String.class);
            StringBuilder usename=new StringBuilder();
            for (String id : ids) {
                usename.append(map.get(id)).append(",");
            }
            String usetypeName= usename.toString().substring(0,usename.length()-1);
            penUseTypeVo = BeanPlusUtil.toBean(penalty, penUseTypeVo.getClass());
            penUseTypeVo.setUseGasTypeName(usetypeName);
            penUseTypeVo.setRateTwo(penalty.getRate().toString());
            penalties.add(penUseTypeVo);
        }
        Page<PenUseTypeVo> penaltyPage=new Page<>();
        return R.success(penaltyPage.setRecords(penalties));
    }

    @ApiOperation(value = "根据id查询价格详情")
    @GetMapping("/{id}")
    R<Penalty> queryById(@PathVariable Long id){
        return penaltyBizApi.queryById(id);
    }
    @ApiOperation(value = "获取枚举参数")
    @PostMapping("/penalty/params")
    public R<Map<String,Object>> getTollItemParams(){
        Map<String,Object> map = new HashMap<>();
        map.put("USER_TYPE",commonConfigurationApi.findCommonConfigbytype("USER_TYPE").getData());
        map.put("PRICE_TYPE",commonConfigurationApi.findCommonConfigbytype("PRICE_TYPE").getData());
        map.put("GAS_TYPE",commonConfigurationApi.findCommonConfigbytype("GAS_TYPE").getData());
        return R.success(map);
    }

    @ApiOperation(value = "通过用气类型id查询气价方案")
    @GetMapping(value = "/queryUseGasTypeAndPrice")
    R<UseGasTypeVO> queryUseGasTypeAndPrice(@RequestParam("id") Long id) {
        return useGasTypeBizApi.queryUseGasTypeAndPrice(id);
    }

    @ApiOperation(value = "通过表具Code查询气价方案")
    @GetMapping(value = "/queryByGasMeterCode")
    R<UseGasTypeVO> queryByGasMeterCode(@RequestParam("gasMeterCode") String gasMeterCode) {
        return gaspriceService.queryByGasMeterCode(gasMeterCode);
    }

}
