package com.cdqckj.gmis.devicearchive.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindPrame;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindResult;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterBind;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 客户气表绑定关系表 - 代缴业务
 * </p>
 *
 * @author wanghuaqiao
 * @date 2020-10-16
 */
public interface CustomerGasMeterBindService extends SuperService<CustomerGasMeterBind> {

    Boolean check(CustomerGasMeterBindUpdateDTO customerUpdateDTO);

    Boolean checkAdd(CustomerGasMeterBindSaveDTO customerSaveDTO);

    List<GasMeterBindResult> getGasMeterInfo(GasMeterBindPrame gasMeterBindPrame);


}
