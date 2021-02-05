package com.cdqckj.gmis.appmanager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.appmanager.AppmanagerApi;
import com.cdqckj.gmis.appmanager.dto.AppmanagerPageDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerUpdateDTO;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 应用管理api
 * @auther hc
 * @date 2020/09/16
 */
@Component
public class AppmanagerApiFallback implements AppmanagerApi {

    @Override
    public R<Appmanager> save(Appmanager appmanager) {
        return R.timeout();
    }

    @Override
    public R<Page<Appmanager>> page(PageParams<AppmanagerPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Appmanager> update(AppmanagerUpdateDTO appmanagerUpdateDTO) {
        return R.fail("修改失败");
    }

    @Override
    public R<Appmanager> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<List<Appmanager>> query(Appmanager appmanager) {
        return R.timeout();
    }
}
