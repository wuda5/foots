package com.cdqckj.gmis.tenant.controller;

import com.cdqckj.gmis.tenant.entity.LivingExpenses;
import com.cdqckj.gmis.tenant.dto.LivingExpensesSaveDTO;
import com.cdqckj.gmis.tenant.dto.LivingExpensesUpdateDTO;
import com.cdqckj.gmis.tenant.dto.LivingExpensesPageDTO;
import com.cdqckj.gmis.tenant.service.LivingExpensesService;
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
 * 支付宝微信-生活缴费关联租户
 * </p>
 *
 * @author gmis
 * @date 2021-01-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/livingExpenses")
@Api(value = "LivingExpenses", tags = "支付宝微信-生活缴费关联租户")
@PreAuth(replace = "livingExpenses:")
public class LivingExpensesController extends SuperController<LivingExpensesService, Long, LivingExpenses, LivingExpensesPageDTO, LivingExpensesSaveDTO, LivingExpensesUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<LivingExpenses> livingExpensesList = list.stream().map((map) -> {
            LivingExpenses livingExpenses = LivingExpenses.builder().build();
            //TODO 请在这里完成转换
            return livingExpenses;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(livingExpensesList));
    }
}
