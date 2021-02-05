package com.cdqckj.gmis.authority.service.common.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdqckj.gmis.authority.dao.common.DictionaryItemMapper;
import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.authority.service.common.DictionaryItemService;
import com.cdqckj.gmis.authority.service.common.DictionaryService;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.utils.MapHelper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

import static com.cdqckj.gmis.common.constant.CacheKey.DICTIONARY_ITEM;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 业务实现类
 * 字典项
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class DictionaryItemServiceImpl extends SuperCacheServiceImpl<DictionaryItemMapper, DictionaryItem> implements DictionaryItemService {

    @Autowired
    DictionaryService dictionaryService;
    @Override
    protected String getRegion() {
        return DICTIONARY_ITEM;
    }

    @Override
    public Map<String, Map<String, String>> map(String[] types) {
        if (ArrayUtil.isEmpty(types)) {
            return Collections.emptyMap();
        }
        LbqWrapper<DictionaryItem> query = Wraps.<DictionaryItem>lbQ()
                .in(DictionaryItem::getDictionaryType, types)
                .eq(DictionaryItem::getStatus, true)
                .orderByAsc(DictionaryItem::getSortValue);
        List<DictionaryItem> list = super.list(query);

        //key 是类型
        Map<String, List<DictionaryItem>> typeMap = list.stream().collect(groupingBy(DictionaryItem::getDictionaryType, LinkedHashMap::new, toList()));

        //需要返回的map
        Map<String, Map<String, String>> typeCodeNameMap = new LinkedHashMap<>(typeMap.size());

        typeMap.forEach((key, items) -> {
            ImmutableMap<String, String> itemCodeMap = MapHelper.uniqueIndex(items, DictionaryItem::getCode, DictionaryItem::getName);
            typeCodeNameMap.put(key, itemCodeMap);
        });
        return typeCodeNameMap;
    }

    @Override
    public List<DictionaryItem> mapNew(String type) {
        if (StringUtil.isEmpty(type)) {
            return Collections.emptyList();
        }
//        Dictionary dic = dictionaryService.getOne(Wraps.<Dictionary>lbQ().eq(Dictionary::getType, type));
        LbqWrapper<DictionaryItem> query = Wraps.<DictionaryItem>lbQ()
                .in(DictionaryItem::getDictionaryType, type)
                .eq(DictionaryItem::getStatus, true)
                .orderByAsc(DictionaryItem::getSortValue);
        // 查的子项
        List<DictionaryItem> list = super.list(query);
        return list;
    }

    @Override
    public Map<Serializable, Object> findDictionaryItem(Set<Serializable> codes) {
        if (codes.isEmpty()) {
            return Collections.emptyMap();
        }
        // 1. 根据 字典编码查询可用的字典列表
        LbqWrapper<DictionaryItem> query = Wraps.<DictionaryItem>lbQ()
                .in(DictionaryItem::getCode, codes)
                .eq(DictionaryItem::getStatus, true)
                .orderByAsc(DictionaryItem::getSortValue);
        List<DictionaryItem> list = super.list(query);

        // 2. 将 list 转换成 Map，Map的key是字典编码，value是字典名称
        ImmutableMap<String, String> typeMap = MapHelper.uniqueIndex(list, DictionaryItem::getCode, DictionaryItem::getName);

        // 3. 将 Map<String, String> 转换成 Map<Serializable, Object>
        Map<Serializable, Object> typeCodeNameMap = new LinkedHashMap<>(typeMap.size());
        typeMap.forEach((key, value) -> typeCodeNameMap.put(key, value));
        return typeCodeNameMap;
    }

    @Override
    public List<DictionaryItem> findcommontype(String dictionarytype) {
        QueryWrapper<DictionaryItem> queryWrapperSdf = new QueryWrapper<>();
        queryWrapperSdf.eq("dictionary_type",dictionarytype);
        return  baseMapper.selectList(queryWrapperSdf);
    }
}
