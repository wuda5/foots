package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.dto.StreetPageDTO;
import com.cdqckj.gmis.operateparam.dto.StreetSaveDTO;
import com.cdqckj.gmis.operateparam.dto.StreetUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Street;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StreetBizApiFallback implements StreetBizApi {


    @Override
    public R<Map<String, Object>> getCommunityListByStreetId(Integer pageNo, Integer pageSize, Long streetId) {
        return R.timeout();
    }

    @Override
    public R<Street> save(StreetSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Street> update(StreetUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<Street>> page(PageParams<StreetPageDTO> params) {
        System.out.println("street 接口？超时？ 异常？");
        return R.timeout();
    }

    @Override
    public R<Street> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<Boolean>  check(StreetUpdateDTO streetUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<Street>> query(Street data) {
        return R.timeout();
    }

    @Override
    public R<Street> queryOne(Street query) {
        return R.timeout();
    }


}
