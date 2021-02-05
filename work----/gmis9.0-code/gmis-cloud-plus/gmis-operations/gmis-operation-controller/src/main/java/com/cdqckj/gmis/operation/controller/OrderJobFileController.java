package com.cdqckj.gmis.operation.controller;

import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.dto.OrderJobFileSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderJobFileUpdateDTO;
import com.cdqckj.gmis.operation.dto.OrderJobFilePageDTO;
import com.cdqckj.gmis.operation.service.OrderJobFileService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 工单现场资料
 * </p>
 *
 * @author gmis
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/orderJobFile")
@Api(value = "orderJobFile", tags = "工单现场资料")
public class OrderJobFileController extends SuperController<OrderJobFileService, Long, OrderJobFile, OrderJobFilePageDTO, OrderJobFileSaveDTO, OrderJobFileUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<OrderJobFile> orderJobFileList = list.stream().map((map) -> {
            OrderJobFile orderJobFile = OrderJobFile.builder().build();
            //TODO 请在这里完成转换
            return orderJobFile;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(orderJobFileList));
    }
}
