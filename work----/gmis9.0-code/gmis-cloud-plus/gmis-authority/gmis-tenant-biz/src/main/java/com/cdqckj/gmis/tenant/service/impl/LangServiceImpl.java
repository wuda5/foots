package com.cdqckj.gmis.tenant.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.tenant.dao.LangMapper;
import com.cdqckj.gmis.tenant.entity.Lang;
import com.cdqckj.gmis.tenant.service.LangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-08
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class LangServiceImpl extends SuperServiceImpl<LangMapper, Lang> implements LangService {

    @Autowired
    private LangMapper langMapper;

    @Override
    public List<Lang> initLang() {
        List<Lang> list = langMapper.selectList(null);
        return list;
    }
}
