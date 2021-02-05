package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.PurchaseLimitBizApi;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitPageDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.vo.PurchaseLimitVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class PurchaseLimitBizApiFallback implements PurchaseLimitBizApi {

    @Override
    public R<PurchaseLimit> save(PurchaseLimitSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<PurchaseLimit> update(PurchaseLimitUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<PurchaseLimit>> page(PageParams<PurchaseLimitPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<PurchaseLimit> get(Long id) {
        return R.timeout();
    }

    @Override
    public List<PurchaseLimit> getAllRecord() {
        return null;
    }

    @Override
    public R<List<PurchaseLimit>> query(@RequestBody PurchaseLimit data) {
        return R.timeout();
    }

    /**
     * 查询用户的限购信息
     *
     * @param pageParams 分页查询参数
     * @return 限购信息列表
     */
    @Override
    public R<Page<PurchaseLimit>> pageCustomerLimitInfo(PageParams<PurchaseLimitVO> pageParams) {
        return R.timeout();
    }
}
