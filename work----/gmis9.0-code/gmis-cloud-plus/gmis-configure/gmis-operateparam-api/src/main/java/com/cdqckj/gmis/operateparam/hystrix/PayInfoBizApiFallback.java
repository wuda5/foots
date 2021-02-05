package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.PayInfoBizApi;
import com.cdqckj.gmis.systemparam.dto.PayInfoPageDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoSaveDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class PayInfoBizApiFallback implements PayInfoBizApi {

    @Override
    public R<PayInfo> save(PayInfoSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<PayInfo> update(PayInfoUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<PayInfo>> page(PageParams<PayInfoPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<PayInfo>> query(PayInfo data) {
        return R.timeout();
    }
}
