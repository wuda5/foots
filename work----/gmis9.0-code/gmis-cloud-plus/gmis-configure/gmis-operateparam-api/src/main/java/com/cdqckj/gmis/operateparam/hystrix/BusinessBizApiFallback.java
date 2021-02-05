package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.systemparam.dto.BusinessHallPageDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallSaveDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class BusinessBizApiFallback implements BusinessHallBizApi {

    @Override
    public R<BusinessHall> save(BusinessHallSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<BusinessHall> update(BusinessHallUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatch(List<BusinessHallUpdateDTO> list) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<BusinessHall> queryById(Long id) {
        return R.timeout();
    }

    @Override
    public BusinessHall queryByOrgId(Long orgId) {
        return null;
    }

    @Override
    public R<Page<BusinessHall>> page(PageParams<BusinessHallPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<BusinessHall>> query( BusinessHall queryInfo) {
        return R.timeout();
    }
}
