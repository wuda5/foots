package com.cdqckj.gmis.readmeter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.ExecuteStatus;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.readmeter.dao.*;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.*;
import com.cdqckj.gmis.readmeter.service.ReadMeterBookService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 抄表册
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterBookServiceImpl extends SuperServiceImpl<ReadMeterBookMapper, ReadMeterBook> implements ReadMeterBookService {

    @Autowired
    public GasMeterBookRecordMapper bookRecordMapper;
    @Autowired
    public ReadMeterPlanScopeMapper planScopeMapper;
    @Autowired
    public ReadMeterPlanMapper planMapper;
    @Autowired
    public ReadMeterDataMapper dataMapper;

    /**
     * 暂时没有用到
     * @param ids
     * @return
     */
    public R<Boolean> deleteBookRecord(List<Long> ids) {
        Integer size = ids.size();
        if(size>0){
            List<GasMeterBookRecord> bookRecordList = bookRecordMapper.selectBatchIds(ids);
            Long bookId = bookRecordList.get(0).getReadMeterBook();
            ReadMeterBook book = getById(bookId);
            LambdaQueryWrapper<ReadMeterPlanScope> scopeLambda = new LambdaQueryWrapper<>();
            scopeLambda.eq(ReadMeterPlanScope::getBookId, bookId);
            List<ReadMeterPlanScope> scopeList = planScopeMapper.selectList(scopeLambda);
            List<Long> planIds = scopeList.stream().map(ReadMeterPlanScope::getReadmeterPlanId).collect(Collectors.toList());
            List<ReadMeterPlan> planList = planMapper.selectBatchIds(planIds);
            Map<Long,ReadMeterPlan> planMap = planList.stream().collect(Collectors.toMap(ReadMeterPlan::getId, Function.identity()));
            LambdaQueryWrapper<ReadMeterData> dataLambda = new LambdaQueryWrapper<>();
            dataLambda.eq(ReadMeterData::getBookId, bookId);
            dataLambda.in(ReadMeterData::getPlanId, planIds);
            dataLambda.ne(ReadMeterData::getDataStatus, 0);
            List<ReadMeterData> dataList = dataMapper.selectList(dataLambda);
            //抄表册关联表删除
            bookRecordMapper.deleteBatchIds(ids);
            //抄表册
            Integer nowCount = book.getTotalReadMeterCount()-size>0? book.getTotalReadMeterCount()-size:0;
            book.setTotalReadMeterCount(nowCount);
            updateById(book);
            //计划及其他
            scopeList.stream().forEach(scope ->{
                if(scope.getDataStatus()== ExecuteStatus.NOT_STARTED.getCode()){
                    Integer totalCount = scope.getTotalReadMeterCount()-size>0 ?scope.getTotalReadMeterCount()-size:0;
                    scope.setTotalReadMeterCount(totalCount);
                    if(planMap.containsKey(scope.getReadmeterPlanId())){
                        ReadMeterPlan plan = planMap.get(scope.getReadmeterPlanId());
                        Integer count = plan.getTotalReadMeterCount()-1>0? plan.getTotalReadMeterCount()-1:0;
                        plan.setTotalReadMeterCount(count);
                    }
                }else if(scope.getDataStatus()==ExecuteStatus.SUSPEND.getCode() || scope.getDataStatus()== ExecuteStatus.EXECUTING.getCode()){
                    Integer totalCount = scope.getTotalReadMeterCount()-1>0? book.getTotalReadMeterCount()-1:0;
                    scope.setTotalReadMeterCount(totalCount);
                    if(totalCount==0 && scope.getDataStatus().equals(ExecuteStatus.EXECUTING.getCode())){
                        scope.setDataStatus(ExecuteStatus.SUSPEND.getCode());
                    }
                    if(planMap.containsKey(scope.getReadmeterPlanId())){
                        ReadMeterPlan plan = planMap.get(scope.getReadmeterPlanId());
                        Integer count = plan.getTotalReadMeterCount()-1>0? plan.getTotalReadMeterCount()-1:0;
                        plan.setTotalReadMeterCount(count);
                        if(count==0 && plan.getDataStatus().equals(ExecuteStatus.EXECUTING.getCode())){
                            plan.setDataStatus(ExecuteStatus.SUSPEND.getCode());
                        }
                    }
                }
                planScopeMapper.updateAllById(scope);
            });
            dataMapper.deleteBatchIds(dataList);
            for(Map.Entry<Long,ReadMeterPlan> entry:planMap.entrySet()){
                planMapper.updateAllById(entry.getValue());
            }
        }
        return R.fail("请至少选择一条数据");
    }

    @Override
    public R<Boolean> saveBook(ReadMeterBook book) {
        if (checkName(book)) {
            return R.fail("抄表册名称已存在");
        }
        book.setOrgId(UserOrgIdUtil.getUserOrgId());
        return R.success(save(book));
    }

    public boolean checkName(ReadMeterBook book) {
        String bookName = book.getBookName();
        Long bookId = book.getId();
        boolean bool = true;
        if(null!=bookId){
            ReadMeterBook book1 = baseMapper.selectById(bookId);
            if(book1.getBookName().equals(bookName)){
                bool =  false;
            }
        }else{
            Integer count = baseMapper.selectCount(Wraps.<ReadMeterBook>lbU().eq(ReadMeterBook::getBookName, bookName));
            if (count == 0) {
                bool =  false;
            }
        }
        return bool;
    }

    @Override
    public R<Boolean> updateBookDetail(ReadMeterBook book) {
        if (checkName(book)) {
            return R.fail("抄表册名称已存在");
        }
        updateById(book);
        Long bookId = book.getId();
        LambdaQueryWrapper<ReadMeterData> dataLambda = new LambdaQueryWrapper<>();
        dataLambda.eq(ReadMeterData::getBookId, bookId);
        //未抄表的数据
        dataLambda.eq(ReadMeterData::getDataStatus, -1);
        ReadMeterData data = new ReadMeterData();
        data.setReadMeterUserId(book.getReadMeterUser());
        data.setReadMeterUserName(book.getReadMeterUserName());
        dataMapper.update(data, dataLambda);
        return R.success();
    }

    @Override
    public R<IPage<ReadMeterBook>> page(PageParams<ReadMeterBookPageDTO> params) {
        ReadMeterBookPageDTO book = params.getModel();
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        if(orgIds.size()>0){
            return R.success(baseMapper.selectPage(params.getPage(),Wraps.<ReadMeterBook>lbQ().like(ReadMeterBook::getBookName,book.getBookName())
                    .like(ReadMeterBook::getReadMeterUserName,book.getReadMeterUserName()).in(ReadMeterBook::getOrgId,orgIds)));
        }
        return R.fail("当前用户没有关联组织");
    }
}
