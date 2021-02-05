package com.cdqckj.gmis.authority.api.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.api.AreaBizApi;
import com.cdqckj.gmis.authority.dto.common.AreaPageDTO;
import com.cdqckj.gmis.authority.dto.common.AreaSaveDTO;
import com.cdqckj.gmis.authority.dto.common.AreaUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class AreaBizApiFallback implements AreaBizApi {
    @Override
    public R<Area> save(@Valid AreaSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Area> update(AreaUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<Area>> query(Area data) {
        return R.timeout();
    }

    @Override
    public R<IPage<Area>> page(PageParams<AreaPageDTO> params) {
        return R.timeout();
    }
}
