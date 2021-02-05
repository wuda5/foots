package com.cdqckj.gmis.bizcenter.read.meter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.vo.ReadMeterPlanScopeParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReadMeterPlanBizService extends SuperCenterService {
    /**
     * 修改计划状态
     * @param readMeterPlanUpdateDTO
     * @return
     */
    R<ReadMeterPlan> updateSatus(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO);

    void executePlan(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO, List<GasMeterBookRecord> recordList);

    /**
     * 新增抄表任务分配信息
     * @param readMeterPlanScopeSaveDTO
     * @return
     */
    R<ReadMeterPlanScope> saveReadMeterPlanScope(ReadMeterPlanScopeSaveDTO readMeterPlanScopeSaveDTO);

    /**
     * 批量新增抄表任务分配信息
     * @param param
     * @return
     */
    R<Boolean> savePlanScopeList(ReadMeterPlanScopeParam param);

    /**
     * 分页查询没有绑定的抄表册
     * @param params
     * @return
     */
    R<Page<ReadMeterBook>> pageUnboundBook(@RequestBody PageParams<ReadMeterPlan> params);
}