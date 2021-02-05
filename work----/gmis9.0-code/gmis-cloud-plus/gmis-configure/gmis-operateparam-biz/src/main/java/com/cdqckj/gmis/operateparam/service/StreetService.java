package com.cdqckj.gmis.operateparam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.dto.StreetPageDTO;
import com.cdqckj.gmis.operateparam.dto.StreetUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Street;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
public interface StreetService extends SuperService<Street> {

    Boolean deleteStreetAndCommunity(List<Long> ids);

    Boolean check(StreetUpdateDTO streetUpdateDTO);
}
