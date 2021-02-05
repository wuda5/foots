package com.cdqckj.gmis.archive.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBlacklistBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.userarchive.dto.*;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.entity.CustomerBlacklist;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
public class CustomerBlacklistBizApiFallback implements CustomerBlacklistBizApi {

    @Override
    public R<CustomerBlacklist> save(List<CustomerBlacklistSaveDTO> saveDTO) {
        return R.timeout();
    }
    @Override
    public R<CustomerBlacklist> update(List<CustomerBlacklistUpdateDTO> updateDTO) {
        return R.timeout();
    }
    @Override
    public R<Boolean> delete(List<Long> ids) {
        return R.timeout();
    }
    @Override
    public R<Page<CustomerBlacklist>> page(PageParams<CustomerBlacklistPageDTO> params) {
        return R.timeout();
    }
    @Override
    public R<CustomerBlacklist> findBlacklistStatus(String customerCode) {
        return R.timeout();
    }

}
