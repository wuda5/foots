package com.cdqckj.gmis.devicearchive.controller;
import com.cdqckj.gmis.devicearchive.entity.MeterStock;
import com.cdqckj.gmis.devicearchive.dto.MeterStockSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockPageDTO;
import com.cdqckj.gmis.devicearchive.service.MeterStockService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/meterStock")
@Api(value = "MeterStock", tags = "")
@PreAuth(replace = "meterStock:")
public class MeterStockController extends SuperController<MeterStockService, Long, MeterStock, MeterStockPageDTO, MeterStockSaveDTO, MeterStockUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<MeterStock> meterStockList = list.stream().map((map) -> {
            MeterStock meterStock = MeterStock.builder().build();
            //TODO 请在这里完成转换
            return meterStock;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(meterStockList));
    }


}
