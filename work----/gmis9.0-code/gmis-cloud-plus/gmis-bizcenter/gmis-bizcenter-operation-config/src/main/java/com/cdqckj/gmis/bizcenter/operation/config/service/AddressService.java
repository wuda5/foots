package com.cdqckj.gmis.bizcenter.operation.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.operateparam.dto.*;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;
import com.cdqckj.gmis.operateparam.entity.Street;
import org.springframework.web.bind.annotation.RequestBody;

public interface AddressService extends SuperCenterService {
     /**
      * 新增小区
      * @param communitySaveDTO 小区信息
      * @return
      */
     R<Community> saveCommunity(@RequestBody CommunitySaveDTO communitySaveDTO);

     /**
      * 新增街道
      * @param streetSaveDTO 街道信息
      * @return
      */
     R<Street> saveStreet(@RequestBody StreetSaveDTO streetSaveDTO);

     @Deprecated
    R<BatchDetailedAddress> batchAddresss(BatchDetailedAddressSaveDTO detailedAddressSaveDTO);

    /**
     * 批量建地址:新增 调压箱号批量建址逻辑
     * @Author hc
     * @date 2020/11/30
     * @param detailedAddressSaveDTO
     * @return
     */
    R<BatchDetailedAddress> batchAddresssEx(BatchDetailedAddressSaveDTO detailedAddressSaveDTO);

    R<BatchDetailedAddress>  saveDetailAddresss(BatchDetailedAddressSaveDTO saveDTO);

    R<Community> updateCommunity(CommunityUpdateDTO communityUpdateDTO);

    R<Street> updateStreet(StreetUpdateDTO streetUpdateDTO);

    R<BatchDetailedAddress> updateDetailAddresss(BatchDetailedAddressUpdateDTO updateDTO);
}
