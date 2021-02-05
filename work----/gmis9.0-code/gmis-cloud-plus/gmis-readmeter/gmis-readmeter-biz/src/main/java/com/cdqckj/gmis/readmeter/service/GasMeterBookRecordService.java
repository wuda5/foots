package com.cdqckj.gmis.readmeter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 抄表册关联客户
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
public interface GasMeterBookRecordService extends SuperService<GasMeterBookRecord> {

    R<List<GasMeterBookRecord>> queryByBookId(List<Long> ids);

    GasMeterBookRecord queryByGasCode(String gasCode);

    IPage<GasMeterBookRecordVo> pageBookRecord(PageParams<GasMeterBookRecordPageDTO> params) throws Exception;
}
