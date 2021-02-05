package com.cdqckj.gmis.authority.api.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.api.LivingExpensesBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.tenant.dto.LivingExpensesPageDTO;
import com.cdqckj.gmis.tenant.dto.LivingExpensesSaveDTO;
import com.cdqckj.gmis.tenant.dto.LivingExpensesUpdateDTO;
import com.cdqckj.gmis.tenant.entity.LivingExpenses;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class LivingExpensesBizApiFallback implements LivingExpensesBizApi {
    @Override
    public R<LivingExpenses> save(@Valid LivingExpensesSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<LivingExpenses> update(LivingExpensesUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<LivingExpenses>> query(LivingExpenses data) {
        return R.timeout();
    }

    @Override
    public R<IPage<LivingExpenses>> page(PageParams<LivingExpensesPageDTO> params) {
        return R.timeout();
    }
}
