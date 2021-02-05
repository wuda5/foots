package com.cdqckj.gmis.authority.api.hystrix;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemSaveDTO;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CommonConfigurationFallback  implements CommonConfigurationApi {
    @Override
    public R<Boolean> delete(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<DictionaryItem> save(DictionaryItemSaveDTO dictionaryItemSaveDTO) {
        return R.timeout();
    }

    @Override
    public R<DictionaryItem> update(DictionaryItemUpdateDTO dictionaryItemUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<DictionaryItem>> findCommonConfigbytype(String dictionarytype) {
        return R.timeout();
    }

    @Override
    public R<List<DictionaryItem>> query(DictionaryItem dictionaryItem) {
        return R.timeout();
    }


}
