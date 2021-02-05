package com.cdqckj.gmis.authority.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.api.hystrix.LivingExpensesBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.tenant.dto.LivingExpensesPageDTO;
import com.cdqckj.gmis.tenant.dto.LivingExpensesSaveDTO;
import com.cdqckj.gmis.tenant.dto.LivingExpensesUpdateDTO;
import com.cdqckj.gmis.tenant.entity.LivingExpenses;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "${gmis.feign.authority-server:gmis-authority-server}", fallback = LivingExpensesBizApiFallback.class
        , path = "/livingExpenses", qualifier = "livingExpensesBizApi")
public interface LivingExpensesBizApi {
    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<LivingExpenses> save(@RequestBody @Valid LivingExpensesSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<LivingExpenses> update(@RequestBody @Validated(SuperEntity.Update.class) LivingExpensesUpdateDTO updateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<LivingExpenses>> query(@RequestBody LivingExpenses data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<IPage<LivingExpenses>> page(@RequestBody @Validated PageParams<LivingExpensesPageDTO> params);
}
