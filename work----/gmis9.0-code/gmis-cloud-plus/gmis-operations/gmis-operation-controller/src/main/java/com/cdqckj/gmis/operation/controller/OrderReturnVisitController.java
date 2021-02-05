package com.cdqckj.gmis.operation.controller;

import com.cdqckj.gmis.operation.entity.OrderReturnVisit;
import com.cdqckj.gmis.operation.dto.OrderReturnVisitSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderReturnVisitUpdateDTO;
import com.cdqckj.gmis.operation.dto.OrderReturnVisitPageDTO;
import com.cdqckj.gmis.operation.service.OrderReturnVisitService;
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
 * 工单回访记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/orderReturnVisit")
@Api(value = "OrderReturnVisit", tags = "工单回访记录")
@PreAuth(replace = "orderReturnVisit:")
public class OrderReturnVisitController extends SuperController<OrderReturnVisitService, Long, OrderReturnVisit, OrderReturnVisitPageDTO, OrderReturnVisitSaveDTO, OrderReturnVisitUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<OrderReturnVisit> orderReturnVisitList = list.stream().map((map) -> {
            OrderReturnVisit orderReturnVisit = OrderReturnVisit.builder().build();
            //TODO 请在这里完成转换
            return orderReturnVisit;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(orderReturnVisitList));
    }
}
