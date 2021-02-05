package com.cdqckj.gmis.bizcenter.temp.counter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SupplementGasVO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;

/**
 * @author gmis
 */
public interface SupplementGasService {

    /**
     * 分页查询补气记录列表
     *
     * @param pageDTO 分页查询对象
     * @return 补气记录分页列表
     */
    R<Page<SupplementGasRecord>> pageSupplementGasRecord(PageParams<SupplementGasRecordPageDTO> pageDTO);

    /**
     * 通过ID查询补气记录详情
     *
     * @param id 记录ID
     * @return 补气记录
     */
    R<SupplementGasRecord> getSupplementGasRecord(Long id);

    /**
     * 通过表具code查询未完成的补气记录
     *
     * @param customerCode 客户编号
     * @param meterCode 表具编号
     * @return 补气记录
     */
    R<SupplementGasVO> queryUnfinishedRecord(String customerCode, String meterCode);

    /**
     * 新增补气记录数据
     *
     * @param saveDTO 新增参数
     * @return 新增对象
     */
    R<SupplementGasVO> add(SupplementGasRecordSaveDTO saveDTO);

}
