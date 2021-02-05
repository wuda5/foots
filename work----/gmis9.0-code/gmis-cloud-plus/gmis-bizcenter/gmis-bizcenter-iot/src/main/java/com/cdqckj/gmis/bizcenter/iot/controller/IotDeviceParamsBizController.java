package com.cdqckj.gmis.bizcenter.iot.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.bizcenter.iot.strategy.MeterStrategy;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import com.cdqckj.gmis.lot.BusinessBizApi;
import com.cdqckj.gmis.lot.IotDeviceParamsBizApi;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
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
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("/iot/iotDeviceParams")
@Api(value = "iotDeviceParams", tags = "物联网设备参数设置记录表bizCenter")
public class IotDeviceParamsBizController {

    @Autowired
    private IotDeviceParamsBizApi iotDeviceParamsBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private BusinessBizApi businessBizApi;

    @Autowired
    private MeterFactoryCacheConfig meterFactoryCacheConfig;
    @Autowired
    private DeviceService deviceService;
    /**
     * 通过表身号集合查询
     *
     * @param gasMeterNumbers
     * @return
     */
    @ApiOperation(value = "通过表身号集合查询 获取物联网设备参数设置记录 集合")
    @PostMapping("/queryByNumbers")
    public R<List<IotDeviceParams>> queryByNumbers(@RequestBody List<String> gasMeterNumbers){
        R<List<IotDeviceParams>> listR = iotDeviceParamsBizApi.queryByNumbers(gasMeterNumbers);
        return  listR ;
    }
//    /**
//     * 设备参数设置记录
//     *
//     * @param gasMeterNumber
//     * @return
//     */
//    @ApiOperation(value = "单个gasMeterNumber 获取物联网设备参数设置记录")
//    @GetMapping("/getIotDeviceParams")
//    public R<IotDeviceParams> getIotDeviceParams(@RequestParam("gasMeterNumber") String gasMeterNumber) {
//        return iotDeviceParamsBizApi.queryOne(new IotDeviceParams().setGasMeterNumber(gasMeterNumber));
//    }

    /**
     * 编辑设备参数设置记录
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "编辑物联网设备参数设置记录")
    @PostMapping("/inputParams")
    public R<IotDeviceParams> inputIotDeviceParams(@RequestBody IotDeviceParamsUpdateDTO params) {
        if (null==params.getId()){
          return   iotDeviceParamsBizApi.save(BeanPlusUtil.toBean(params, IotDeviceParamsSaveDTO.class));
        }
        return iotDeviceParamsBizApi.update(params);
    }

    /**
     * 编辑设备参数设置记录
     *
     * @param list
     * @return
     */
    @ApiOperation(value = "编辑物联网设备参数设置记录--批量编辑")
    @PostMapping("/inputParamsBatch")
    public R<Boolean> inputIotDeviceParamsBatch(@RequestBody List<IotDeviceParamsUpdateDTO> list) {

        BizAssert.isTrue(CollectionUtil.isNotEmpty(list),"传入参数不能null");
        List<IotDeviceParamsUpdateDTO> updateList = list.stream().filter(x -> x.getId() != null).collect(Collectors.toList());
        iotDeviceParamsBizApi.updateBatchById(updateList);

        List<IotDeviceParamsUpdateDTO> saveList = list.stream().filter(x -> x.getId() == null).collect(Collectors.toList());
        iotDeviceParamsBizApi.saveList(BeanPlusUtil.toBeanList(saveList,IotDeviceParamsSaveDTO.class));

//        businessBizApi.setDeviceParams("", BeanPlusUtil.toBeanList(saveList,IotDeviceParams.class));
        // 同一个租户下，这些iot表对应的 逻辑域 同？，不同则必须单个循环调用，--

//        new HashMap<String, List<GasMeter>>();
//        new Gum
        // 避免空集合
        ArrayList<IotDeviceParams> rex = new ArrayList<>();
        rex.addAll(BeanPlusUtil.toBeanList(saveList, IotDeviceParams.class));
        rex.addAll(BeanPlusUtil.toBeanList(updateList, IotDeviceParams.class));
        handleSetParams(rex);
        return R.success();
    }

    private void handleSetParams(List<IotDeviceParams> rex) {
        for(int i = 0; i< rex.size(); i++){
            String domainId = meterFactoryCacheConfig.getReceiveDomainId(rex.get(i).getGasMeterNumber());
            if(StringUtil.isNullOrBlank(domainId)){continue;}
            rex.get(i).setDomainId(domainId);
        }
        Map<String, List<IotDeviceParams>> groupByDomainId = rex.stream().
                collect(Collectors.groupingBy(IotDeviceParams::getDomainId));

        groupByDomainId.forEach((k,v)->{
            //判断厂家下发指令
            MeterStrategy strategy = deviceService.getMeterStrategy(k);
            strategy.setDeviceParamsQ(v);
        });
    }

    /**
     * 编辑设备参数设置记录
     *
     * @param
     * @return
     */
    @ApiOperation(value = "编辑物联网设备参数设置记录--version 批量编辑，不用传gasMeterNumber和 id")
    @PostMapping("/inputParamsByVersion")
    public R<Boolean> inputIotDeviceParamsByVersion(@RequestParam(value = "versionId") Long versionId, @RequestBody IotDeviceParamsUpdateDTO params) {
        List<GasMeter> meters = gasMeterBizApi.query(new GasMeter().setGasMeterVersionId(versionId)).getData();
        BizAssert.isTrue(CollectionUtil.isNotEmpty(meters),"此版号下还没有对应物联网表具");
        List<String> gasMeterNumbers = meters.stream().map(x -> x.getGasMeterNumber()).collect(Collectors.toList());
        // 查询已经存在表身号所对应的 参数设置记录，
        List<IotDeviceParams> needUpdate=  iotDeviceParamsBizApi.queryByNumbers(gasMeterNumbers).getData();//

        // 1.判断 l 中没有缺少的部分，需要直接新增
        List<String> needSaveNumber = gasMeterNumbers.stream().filter(x ->CollectionUtil.isEmpty(needUpdate) || !needUpdate.stream().map(y->y.getGasMeterNumber()).collect(Collectors.toList()).contains(x)).collect(Collectors.toList());
        List<IotDeviceParamsSaveDTO> toSave = needSaveNumber.stream().map(x -> BeanPlusUtil.toBean(params, IotDeviceParamsSaveDTO.class).setGasMeterNumber(x)).collect(Collectors.toList());
        iotDeviceParamsBizApi.saveList(toSave);

        //2.直接更新的部分--构造(让通过id去更新,表身号不能变化)
        List<IotDeviceParamsUpdateDTO> toUpdate = needUpdate.stream().map(x -> BeanPlusUtil.toBean(params, IotDeviceParamsUpdateDTO.class)
                .setId(x.getId()).setGasMeterNumber(x.getGasMeterNumber())).collect(Collectors.toList());
        iotDeviceParamsBizApi.updateBatchById(toUpdate);

        // 避免空集合
        ArrayList<IotDeviceParams> rex = new ArrayList<>();
        rex.addAll(BeanPlusUtil.toBeanList(toSave, IotDeviceParams.class));
        rex.addAll(BeanPlusUtil.toBeanList(toUpdate, IotDeviceParams.class));
//        List<IotDeviceParams> rex = BeanPlusUtil.toBeanList(toSave, IotDeviceParams.class);
//        rex.addAll(BeanPlusUtil.toBeanList(toUpdate, IotDeviceParams.class));

        handleSetParams(rex);

        return R.success();
    }
}
