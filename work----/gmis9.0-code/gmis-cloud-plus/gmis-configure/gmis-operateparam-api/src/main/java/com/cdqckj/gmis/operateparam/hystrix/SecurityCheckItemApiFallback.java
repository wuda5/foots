package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.SecurityCheckItemApi;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemPageDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemSaveDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.operateparam.vo.ScItemsVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SecurityCheckItemApiFallback implements SecurityCheckItemApi {
    @Override
    public R<SecurityCheckItem> saveTemplate(SecurityCheckItemSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<SecurityCheckItem> update(SecurityCheckItemUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Page<SecurityCheckItem>> page(PageParams<SecurityCheckItemPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<ScItemsVo>> getAllCheckInfos(Map<String, Object> map) {
        return R.timeout();
    }

    @Override
    public R<Boolean> check(SecurityCheckItemSaveDTO securityCheckItemSaveDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> checkupadate(SecurityCheckItemUpdateDTO securityCheckItemUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<SecurityCheckItem>> getAllPzCks() {
        return R.timeout();
    }
}
