package com.cdqckj.gmis.authority.service.common.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.common.DictionaryMapper;
import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.authority.service.common.DictionaryItemService;
import com.cdqckj.gmis.authority.service.common.DictionaryService;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 字典类型
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class DictionaryServiceImpl extends SuperServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

    @Autowired
    private DictionaryItemService dictionaryItemService;

    @Override
    public R<Map<String, List<DictionaryItem>>> queryAllDic() {
        List<DictionaryItem> dictionaryItemList = dictionaryItemService.getBaseMapper().selectList(null);
        if(!dictionaryItemList.isEmpty()){
            Map<String, List<DictionaryItem>> groupByDictionaryType = dictionaryItemList.stream().filter(price -> price.getStatus()==true).
                    collect(Collectors.groupingBy(DictionaryItem::getDictionaryType));
            return R.successDef(groupByDictionaryType);
        }
        return null;
    }
}
