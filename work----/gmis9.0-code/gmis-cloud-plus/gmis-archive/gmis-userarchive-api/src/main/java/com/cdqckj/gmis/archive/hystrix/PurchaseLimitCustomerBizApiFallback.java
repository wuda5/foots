package com.cdqckj.gmis.archive.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.PurchaseLimitCustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.dto.PurchaseLimitCustomerSaveDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class PurchaseLimitCustomerBizApiFallback implements PurchaseLimitCustomerBizApi {

    @Override
    public R<PurchaseLimitCustomer> saveList(List<PurchaseLimitCustomerSaveDTO> list) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<CustomerDeviceDTO>> findLimitCustomerPage(CustomerDeviceLimitVO params) {
        return R.timeout();
    }

    @Override
    public List<PurchaseLimitCustomer> selectByIds(List<Long> limitIds) {
        return null;
    }

    @Override
    public R<List<PurchaseLimitCustomer>> query(@RequestBody PurchaseLimitCustomer gasMeter) {
        return R.timeout();
    }
}
