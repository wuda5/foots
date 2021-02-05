package com.cdqckj.gmis.systemparam.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchPlusPageDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchPlusSaveDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchPlusUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitchPlus;
import com.cdqckj.gmis.systemparam.entity.vo.FunctionSwitchPlusVo;
import com.cdqckj.gmis.systemparam.enumeration.FunctionSwitchEnum;
import com.cdqckj.gmis.systemparam.service.FunctionSwitchPlusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 功能项配置plus
 * </p>
 *
 * @author gmis
 * @date 2020-12-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/functionSwitchPlus")
@Api(value = "FunctionSwitchPlus", tags = "功能项配置plus")
@PreAuth(replace = "functionSwitchPlus:")
public class FunctionSwitchPlusController extends SuperController<FunctionSwitchPlusService, Long, FunctionSwitchPlus, FunctionSwitchPlusPageDTO, FunctionSwitchPlusSaveDTO, FunctionSwitchPlusUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<FunctionSwitchPlus> functionSwitchPlusList = list.stream().map((map) -> {
            FunctionSwitchPlus functionSwitchPlus = FunctionSwitchPlus.builder().build();
            //TODO 请在这里完成转换
            return functionSwitchPlus;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(functionSwitchPlusList));
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 17:09
    * @remark 获取一个一个配置项目
    */
    @ApiOperation(value = "获取一个一个配置项目")
    @GetMapping("/getSystemSetting")
    public R<String> getSystemSetting(String item){

        FunctionSwitchPlus functionSwitchPlus = baseService.getSystemSetByItem(item);
        if (functionSwitchPlus == null){
            functionSwitchPlus = new FunctionSwitchPlus();

            FunctionSwitchEnum e = FunctionSwitchEnum.getByItem(item);
            if (e == null){
                throw new BizException("功能项配置不存在");
            }
            functionSwitchPlus.setItem(e.getItem());
            functionSwitchPlus.setDescValue(e.getDesc());
            if (e.getDefaultValue() != null) {
                functionSwitchPlus.setValue(e.getDefaultValue());
            }
            baseService.save(functionSwitchPlus);
        }
        return R.success(functionSwitchPlus.getValue());
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 20:03
    * @remark 获取这个系统的所有的配置
    */
    @ApiOperation(value = "获取这个系统的所有的配置")
    @GetMapping("/getSysAllSetting")
    public R<List<FunctionSwitchPlus>> getSysAllSetting(){

        List<FunctionSwitchPlusVo> all = this.baseService.getSysAllSetting();
        SeparateListData<FunctionSwitchPlusVo> sepData = new SeparateListData<>(all);
        List<FunctionSwitchPlus> sysSetting = new ArrayList<>(FunctionSwitchEnum.values().length);
        for (FunctionSwitchEnum e: FunctionSwitchEnum.values()){

            FunctionSwitchPlusVo vo = sepData.getTheDataByKey(e.getItem());
            FunctionSwitchPlus plus;
            if (vo == null){
                plus = new FunctionSwitchPlus();
                plus.setItem(e.getItem());
                plus.setDescValue(e.getDesc());
                plus.setValue(e.getDefaultValue());
            }else {
                plus = BeanUtil.copyProperties(vo, FunctionSwitchPlus.class);
            }
            sysSetting.add(plus);
        }
        return R.success(sysSetting);
    }

    /**
     * @author hc
     * @date 2020/12/05  
     * @return
     */
    @ApiOperation("获取所有配置:转化为map")
    @GetMapping("/getSysAllSettingMap")
    public R<HashMap<String,Object>> getSysAllSettingMap(){

        HashMap<String,Object> data = new HashMap<>();

        R<List<FunctionSwitchPlus>> allSetting = this.getSysAllSetting();
        allSetting.getData().stream()
                .forEach(item->{
                    //读取配置枚举
                    FunctionSwitchEnum switchEnum = FunctionSwitchEnum.getByItem(item.getItem());
                    if(null!=switchEnum){
                        //判断数据类型
                        Class valueClass = switchEnum.getValueClass();

                        Object value;

                        if(valueClass.equals(Integer.TYPE)){

                            value = Integer.valueOf(item.getValue());

                        }else if(valueClass.equals(BigDecimal.class)){

                            value = new BigDecimal(item.getValue());
                            //默认String
                        }else{
                            value = String.valueOf(item.getValue());
                        }

                        data.put(item.getItem(),value);
                    }
                });

        return R.success(data);
    }

    /**
     * @author hc
     * @date 2020/12/05
     * @param data
     * @return
     */
    @ApiOperation("更新所有配置")
    @PostMapping("/updateSysAllSetting")
    public R<Boolean> updateSysAllSetting(@RequestBody HashMap<String,Object> data){

        final  ArrayList<FunctionSwitchPlusSaveDTO> saveData = new ArrayList<>();

        data.forEach((key, value)->{
            FunctionSwitchPlusSaveDTO item = new FunctionSwitchPlusSaveDTO();
            FunctionSwitchEnum switchEnum = FunctionSwitchEnum.getByItem(key);
            if(null!=switchEnum){
                item.setItem(key);
                item.setDescValue(switchEnum.getDesc());
                //设置默认值
                if(null!=value && StringUtils.isNotEmpty(String.valueOf(value))) {
                    item.setValue(String.valueOf(value));
                }else{
                    item.setValue(switchEnum.getDefaultValue());
                }

                saveData.add(item);
            }
        });

        if(CollectionUtils.isNotEmpty(saveData)){
            //删除所有配置
            baseService.deleteAll();
            //再新增
            super.saveList(saveData);

            return R.success(true);
        }

        return R.success(false);
    }
}
