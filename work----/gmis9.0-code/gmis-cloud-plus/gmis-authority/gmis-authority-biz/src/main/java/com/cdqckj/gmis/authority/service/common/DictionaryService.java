package com.cdqckj.gmis.authority.service.common;

import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务接口
 * 字典类型
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
public interface DictionaryService extends SuperService<Dictionary> {
    /**
     * 查询字典所有key和value
     * @return
     */
    R<Map<String, List<DictionaryItem>>> queryAllDic();
}
