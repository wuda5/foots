package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.dto.UseGasTypePageDTO;
import com.cdqckj.gmis.operateparam.dto.UseGasTypeSaveDTO;
import com.cdqckj.gmis.operateparam.dto.UseGasTypeUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class UseGasTypeBizApiFallback implements UseGasTypeBizApi {

    @Override
    public R<UseGasType> save(UseGasTypeSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<UseGasType> update(UseGasTypeUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<UseGasType> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<Page<UseGasType>> page(PageParams<UseGasTypePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<UseGasType>> queryList(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<List<UseGasType>> query(UseGasType data) {
        return R.timeout();
    }

    @Override
    public R<UseGasType> getUseGasType(String useGasTypeName) {
        return R.timeout();
    }

    @Override
    public UseGasType queryRentUseGasType() {
        return null;
    }

    @Override
    public R<UseGasTypeVO> queryUseGasTypeAndPrice(Long id) {
        return R.timeout();
    }

    @Override
    public R<Boolean> checkUseGasType(Long id, String useGasTypeName) {
        return  R.timeout();
    }
}
