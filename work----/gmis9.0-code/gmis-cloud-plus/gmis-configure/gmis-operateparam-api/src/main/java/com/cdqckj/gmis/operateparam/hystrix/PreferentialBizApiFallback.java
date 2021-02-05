package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.PreferentialBizApi;
import com.cdqckj.gmis.operateparam.dto.PreferentialPageDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Preferential;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class PreferentialBizApiFallback implements PreferentialBizApi {

    @Override
    public R<Preferential> save( PreferentialSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R< Preferential> update( PreferentialUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page< Preferential>> page(PageParams<PreferentialPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List< Preferential>> query(Preferential data ){return R.timeout(); }
}
