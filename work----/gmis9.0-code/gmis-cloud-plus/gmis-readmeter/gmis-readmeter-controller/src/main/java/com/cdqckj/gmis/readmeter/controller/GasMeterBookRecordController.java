package com.cdqckj.gmis.readmeter.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.service.GasMeterBookRecordService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;

import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 前端控制器
 * 抄表册关联客户
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeterBookRecord")
@Api(value = "gasMeterBookRecord", tags = "抄表册关联客户")
@PreAuth(replace = "gasMeterBookRecord:")
public class GasMeterBookRecordController extends SuperController<GasMeterBookRecordService, Long, GasMeterBookRecord, GasMeterBookRecordPageDTO, GasMeterBookRecordSaveDTO, GasMeterBookRecordUpdateDTO> {

    @Autowired
    private ReadMeterDataService dataService;
    /**
     * 根据抄表册id查询所有关联信息
     * @param ids
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookIds", value = "主键", dataType = "array", paramType = "query"),
    })
    @ApiOperation(value = "根据抄表册id查询所有关联信息", notes = "根据抄表册id查询所有关联信息")
    @PostMapping("/queryByBookId")
    @SysLog("根据id集合批量查询")
    public R<List<GasMeterBookRecord>> queryByBookId(@RequestBody List<Long> ids){
        return baseService.queryByBookId(ids);
    };

    /**
     * 根据其表编号修改表身号
     * @param gasMeterBookRecord
     * @return
     */
    @ApiOperation(value = "根据其表编号修改表身号", notes = "根据其表编号修改表身号")
    @PostMapping("/updateGasMeterByCode")
    @SysLog("根据其表编号修改表身号")
    public R<Boolean> updateGasMeterByCode(@RequestBody GasMeterBookRecord gasMeterBookRecord){
        Boolean bool = baseService.update(Wraps.<GasMeterBookRecord>lbU()
                .set(GasMeterBookRecord::getGasMeterNumber,gasMeterBookRecord.getGasMeterNumber())
                .set(GasMeterBookRecord::getMoreGasMeterAddress,gasMeterBookRecord.getMoreGasMeterAddress())
                .eq(GasMeterBookRecord::getGasMeterCode,gasMeterBookRecord.getGasMeterCode()));
        if(bool){
            dataService.update(Wraps.<ReadMeterData>lbU()
                    .set(ReadMeterData::getGasMeterNumber,gasMeterBookRecord.getGasMeterNumber())
                    .set(ReadMeterData::getMoreGasMeterAddress,gasMeterBookRecord.getMoreGasMeterAddress())
                    .eq(ReadMeterData::getGasMeterCode,gasMeterBookRecord.getGasMeterCode()));//eq(ReadMeterData::getDataStatus, -1)
        }
        return R.success();
    };

    @ApiOperation(value = "分页查询抄表册关联用户", notes = "分页查询抄表册关联用户")
    @PostMapping("/pageBookRecord")
    @SysLog("分页查询抄表册关联用户")
    R<IPage<GasMeterBookRecordVo>> pageBookRecord(@RequestBody PageParams<GasMeterBookRecordPageDTO> params) throws Exception {
        return R.success(baseService.pageBookRecord(params));
    };

    @ApiOperation(value = "修改客户")
    @PostMapping("/updateByCustomer")
    R<Boolean> updateByCustomer(@RequestBody GasMeterBookRecordUpdateDTO updateDTO) {
        return R.success(baseService.update(Wraps.<GasMeterBookRecord>lbU()
                .set(GasMeterBookRecord::getCustomerName,updateDTO.getCustomerName())
                .eq(GasMeterBookRecord::getCustomerCode,updateDTO.getCustomerCode())));
    };

    @ApiOperation(value = "修改用气类型")
    @PostMapping("/updateByGasType")
    R<Boolean> updateByGasType(@RequestBody GasMeterBookRecordUpdateDTO updateDTO) {
        if(StringUtils.isNullOrEmpty(updateDTO.getGasMeterCode())){
            return R.success(baseService.update(Wraps.<GasMeterBookRecord>lbU()
                    .set(GasMeterBookRecord::getUseGasTypeName,updateDTO.getUseGasTypeName())
                    .eq(GasMeterBookRecord::getUseGasTypeId,updateDTO.getUseGasTypeId())));
        }else{
            return R.success(baseService.update(Wraps.<GasMeterBookRecord>lbU()
                    .set(GasMeterBookRecord::getUseGasTypeName,updateDTO.getUseGasTypeName())
                    .set(GasMeterBookRecord::getUseGasTypeId,updateDTO.getUseGasTypeId())
                    .eq(GasMeterBookRecord::getGasMeterCode,updateDTO.getGasMeterCode())));
        }
    };

    @PostMapping(value = "/saveRecordList")
    public R<List<GasMeterBookRecord>> saveRecordList(@RequestBody List<GasMeterBookRecordSaveDTO> list) {
        List<String> codelist = list.stream().map(GasMeterBookRecordSaveDTO::getGasMeterCode).collect(Collectors.toList());
        List<GasMeterBookRecord> existList = baseService.list(Wraps.<GasMeterBookRecord>lbU()
                .eq(GasMeterBookRecord::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue()).in(GasMeterBookRecord::getGasMeterCode,codelist));
        codelist = existList.stream().map(GasMeterBookRecord::getGasMeterCode).collect(Collectors.toList());
        List<String> finalCodelist = codelist;
        List<GasMeterBookRecord> entityList = new ArrayList<>();
        list.forEach(dto ->{
            if(!finalCodelist.contains(dto.getGasMeterCode())){
                GasMeterBookRecord record = BeanUtil.toBean(dto, GasMeterBookRecord.class);
                entityList.add(record);
            }
        });
        if(!list.isEmpty()){
            baseService.saveBatch(entityList);
        }
        return R.success(entityList);
    }

    @PostMapping(value = "/getMaxNumber")
    R<Integer> getMaxNumber(@RequestParam("id") Long readMeterBookId){
        GasMeterBookRecord record = baseService.getOne(Wraps.<GasMeterBookRecord>lbU()
                .eq(GasMeterBookRecord::getDeleteStatus,DeleteStatusEnum.NORMAL.getValue())
                .eq(GasMeterBookRecord::getReadMeterBook,readMeterBookId).orderByDesc(GasMeterBookRecord::getNumber).last("limit 1"));
        return R.success(record==null? 0: record.getNumber());
    }
}
