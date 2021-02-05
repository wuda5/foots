package com.cdqckj.gmis.readmeter.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.dto.auth.UserPageDTO;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotErrorSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotErrorUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotErrorPageDTO;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotErrorService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;


/**
 * <p>
 * 前端控制器
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-11-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterDataIotError")
@Api(value = "ReadMeterDataIotError", tags = "物联网异常抄表数据")
//@PreAuth(replace = "readMeterDataIotError:")
public class ReadMeterDataIotErrorController extends SuperController<ReadMeterDataIotErrorService, Long, ReadMeterDataIotError, ReadMeterDataIotErrorPageDTO, ReadMeterDataIotErrorSaveDTO, ReadMeterDataIotErrorUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<ReadMeterDataIotError> readMeterDataIotErrorList = list.stream().map((map) -> {
            ReadMeterDataIotError readMeterDataIotError = ReadMeterDataIotError.builder().build();
            //TODO 请在这里完成转换
            return readMeterDataIotError;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(readMeterDataIotErrorList));
    }

    @GetMapping(value = "/queryByGasMeterNumber")
    R<ReadMeterDataIotError> queryByGasMeterNumber(@RequestParam(value = "gasMeterNumber") String gasMeterNumber){
       return R.success(baseService.queryByGasMeterNumber(gasMeterNumber));
    }

    /**
     * 根据气表编号查询
     * @param meterNoList
     * @return
     */
    @PostMapping(value = "/queryByMeterNo")
    public List<ReadMeterDataIotError> queryByMeterNo(@RequestBody List<String> meterNoList){
        return baseService.queryByMeterNo(meterNoList);
    }

    /**
     * 批量更新
     * @param list
     * @return
     */
    @PostMapping(value = "/updateBathMeter")
    public Boolean updateBathMeter(@RequestBody List<ReadMeterDataIotError> list){
        return baseService.updateBathMeter(list);
    }

    @Override
    public void query(PageParams<ReadMeterDataIotErrorPageDTO> params, IPage<ReadMeterDataIotError> page, Long defSize) {
        ReadMeterDataIotErrorPageDTO errorPageDTO = params.getModel();

        QueryWrap<ReadMeterDataIotError> wrap = Wraps.q();
        handlerWrapper(wrap, params);

        LbqWrapper<ReadMeterDataIotError> wrapper = wrap.lambda();
        wrapper.ne(ReadMeterDataIotError::getDataStatus, 1)
                .like(ReadMeterDataIotError::getCustomerName,errorPageDTO.getCustomerName())
                .like(ReadMeterDataIotError::getCustomerCode, errorPageDTO.getCustomerCode())
                .like(ReadMeterDataIotError::getUseGasTypeName,errorPageDTO.getUseGasTypeName())
                .like(ReadMeterDataIotError::getGasMeterNumber, errorPageDTO.getGasMeterNumber())
                .like(ReadMeterDataIotError::getGasMeterAddress, errorPageDTO.getGasMeterAddress());
        baseService.findPage(page, wrapper);
    }

}
