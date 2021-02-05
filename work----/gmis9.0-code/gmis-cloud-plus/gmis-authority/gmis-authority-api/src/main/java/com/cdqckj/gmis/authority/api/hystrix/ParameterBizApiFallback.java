package com.cdqckj.gmis.authority.api.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.api.ParameterBizApi;
import com.cdqckj.gmis.authority.dto.common.ParameterPageDTO;
import com.cdqckj.gmis.authority.dto.common.ParameterSaveDTO;
import com.cdqckj.gmis.authority.dto.common.ParameterUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.Parameter;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class ParameterBizApiFallback implements ParameterBizApi {
    @Override
    public R<Parameter> save(@Valid ParameterSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Parameter> update(ParameterUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<Parameter>> query(Parameter data) {
        return R.timeout();
    }

    @Override
    public R<IPage<Parameter>> page(PageParams<ParameterPageDTO> params) {
        return R.timeout();
    }
}
