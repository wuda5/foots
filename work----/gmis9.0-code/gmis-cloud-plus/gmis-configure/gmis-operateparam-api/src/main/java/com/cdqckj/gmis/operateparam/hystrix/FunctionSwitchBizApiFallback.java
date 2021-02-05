package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.FunctionSwitchBizApi;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchPageDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchSaveDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public abstract class FunctionSwitchBizApiFallback implements FunctionSwitchBizApi {

    @Override
    public R<FunctionSwitch> save(FunctionSwitchSaveDTO saveDTO) { return R.timeout(); }

    @Override
    public R<FunctionSwitch> update(FunctionSwitchUpdateDTO updateDTO) { return R.timeout(); }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<FunctionSwitch>> page(PageParams<FunctionSwitchPageDTO> params) {
        return R.timeout();
    }

//    @Override
//    public R<List<FunctionSwitch>> query(FunctionSwitch queryDTO) {
//        return R.timeout();
//    }
}
