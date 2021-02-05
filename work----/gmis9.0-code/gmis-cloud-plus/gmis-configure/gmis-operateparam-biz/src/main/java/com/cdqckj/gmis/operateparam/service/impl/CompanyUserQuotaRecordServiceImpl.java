package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operateparam.dao.CompanyUserQuotaRecordMapper;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;
import com.cdqckj.gmis.operateparam.service.CompanyUserQuotaRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.operateparam.vo.BusinessHallVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CompanyUserQuotaRecordServiceImpl extends SuperServiceImpl<CompanyUserQuotaRecordMapper, CompanyUserQuotaRecord> implements CompanyUserQuotaRecordService {
    @Override
    public R<Map<String, List<BusinessHallVO>>> getUserBusinessHall() {
        List<BusinessHallVO> list = getBaseMapper().selectBusinessHallUser();
        if(list!=null&&list.size()>0){
            Map<String, List<BusinessHallVO>> groupByDictionaryType = list.stream().
                    collect(Collectors.groupingBy(BusinessHallVO::getBusinessHallName));
            return R.successDef(groupByDictionaryType);
        }
        return R.fail("未找到营业厅用户");
    }

    @Override
    public CompanyUserQuotaRecord queryRecentRecord() {
        return baseMapper.queryRecentRecord();
    }
}
