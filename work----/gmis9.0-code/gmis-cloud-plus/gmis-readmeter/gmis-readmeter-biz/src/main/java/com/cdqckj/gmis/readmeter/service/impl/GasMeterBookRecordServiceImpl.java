package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.readmeter.dao.GasMeterBookRecordMapper;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.service.GasMeterBookRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 抄表册关联客户
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterBookRecordServiceImpl extends SuperServiceImpl<GasMeterBookRecordMapper, GasMeterBookRecord> implements GasMeterBookRecordService {
    @Autowired
    public GasMeterBookRecordMapper gasMeterBookRecordMapper;

    @Override
    public R<List<GasMeterBookRecord>> queryByBookId(List<Long> ids) {
        LambdaQueryWrapper<GasMeterBookRecord> lambda = new LambdaQueryWrapper<GasMeterBookRecord>();
        lambda.eq(GasMeterBookRecord::getDeleteStatus,0);
        lambda.in(GasMeterBookRecord::getReadMeterBook, ids);
        List<GasMeterBookRecord> gasMeterBookRecordList = gasMeterBookRecordMapper.selectAll(lambda);
        return R.success(gasMeterBookRecordList);
    }

    @Override
    public GasMeterBookRecord queryByGasCode(String gasCode) {
        LbqWrapper<GasMeterBookRecord> wp = Wraps.lbQ();
        wp.eq(GasMeterBookRecord::getDeleteStatus,0);
        wp.eq(GasMeterBookRecord::getGasMeterCode, gasCode);
        GasMeterBookRecord re = baseMapper.selectOne(wp);
        return re;
    }

    @Override
    public IPage<GasMeterBookRecordVo> pageBookRecord(PageParams<GasMeterBookRecordPageDTO> params) throws Exception {
        Page<GasMeterBookRecordVo> page = new Page<>(params.getCurrent(),params.getSize());
        return baseMapper.pageBookRecord(page,params.getModel());
    }
}
