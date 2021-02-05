package com.cdqckj.gmis.oauth.api.hystrix;

import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.oauth.api.DictionaryItemApi;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据字典项 查询
 *
 * @author gmis
 * @date 2019/07/26
 */
@Component
public class DictionaryItemApiFallback implements DictionaryItemApi {

    @Override
    public Map<Serializable, Object> findDictionaryItem(Set<Serializable> codes) {
        return new HashMap<>(1);
    }

    @Override
    public R<Map<String, Map<String, String>>> list(String[] types) {
        return R.timeout();
    }

    @Override
    public R<List<DictionaryItem>> listByType(String type) {
        return R.timeout();
    }


}
