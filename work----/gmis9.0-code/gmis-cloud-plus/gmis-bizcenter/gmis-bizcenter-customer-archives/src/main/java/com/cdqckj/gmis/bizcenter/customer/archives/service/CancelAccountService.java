package com.cdqckj.gmis.bizcenter.customer.archives.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelSaveDTO;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;

import java.util.List;

/**
 * @author hp
 */
public interface CancelAccountService {

    /**
     * 新增销户记录
     *
     * @param saveDTO 销户新增参数
     * @return 新增记录
     */
    R<MeterAccountCancel> add(MeterAccountCancelSaveDTO saveDTO);

    /**
     * 批量新增
     *
     * @param saveDTOList 新增的销户
     * @return 新增记录
     */
    R<Boolean> batchAdd(List<MeterAccountCancelSaveDTO> saveDTOList);

}
