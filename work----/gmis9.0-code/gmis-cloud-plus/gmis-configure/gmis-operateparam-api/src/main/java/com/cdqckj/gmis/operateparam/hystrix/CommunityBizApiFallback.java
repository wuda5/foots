package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.dto.CommunityPageDTO;
import com.cdqckj.gmis.operateparam.dto.CommunitySaveDTO;
import com.cdqckj.gmis.operateparam.dto.CommunityUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Community;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class CommunityBizApiFallback implements CommunityBizApi {

    @Override
    public R<Community> save(CommunitySaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Community> update(CommunityUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<Community>> page(PageParams<CommunityPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Community> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<Boolean> check(CommunityUpdateDTO communityUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<Community>> query(Community data) {
        return R.timeout();
    }


}
