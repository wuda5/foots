package com.cdqckj.gmis.bizcenter.read.meter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordSaveDTO;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;

import java.util.List;

public interface ReadMeterBookBizService extends SuperCenterService {

    /**
     * 新增抄表册关联用户(条件新增)
     * @param pageDTO
     * @return
     */
    R<Boolean> addByQuery(GasMeterBookRecordPageDTO pageDTO);

    /**
     * 新增抄表册关联用户(选中新增)
     * @param list
     * @return
     */
    R<Boolean> saveBookRecord(List<GasMeterBookRecordSaveDTO> list);

    /**
     * 删除抄表册关联用户
     * @param ids
     * @return
     */
    R<Boolean> deleteBookRecord(List<Long> ids);

    /**
     * 分页查询抄表册关联客户
     * @param params
     * @return
     */
    R<Page<GasMeterBookRecordVo>> pageBookRecord(PageParams<GasMeterBookRecordPageDTO> params);

    /**
     * (单个删除)删除抄表册信息
     * @param ids
     * @return
     */
    R<Boolean> deleteReadMeterBook(List<Long> ids);
}