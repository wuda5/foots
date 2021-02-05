package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dto.ChargeLoadDTO;
import com.cdqckj.gmis.charges.dto.ChargeLoadReqDTO;
import com.cdqckj.gmis.charges.dto.ChargeSceneLoadReqDTO;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.operateparam.enumeration.ActivityScene;

import java.util.List;
import java.util.Map;

/**
 * 收费项加载
 *
 * @author tp
 * @date 2020-09-04
 */
public interface ChargeLoadService {
    /**
     * 加载所有收费项
     * @param gasMeterCode
     * @param customerNo
     * @return
     */
    R<ChargeLoadDTO> chargeLoad(String gasMeterCode, String customerNo);

    /**
     * 加载固定场景收费项
     * @return
     */
    R<ChargeLoadDTO> sceneChargeLoad(ChargeSceneLoadReqDTO param);

    /**
     * 第三方缴费查询燃气收费数据加载
     * @param param
     * @return
     */
    R<ChargeLoadDTO> thirdGasFeeChargeLoad(ChargeLoadReqDTO param);

    /**
     * 加载减免项或者赠送活动
     * @param meter
     * @param scene
     * @return
     */
    Map<Long, CustomerEnjoyActivityRecord> loadGiveReduction(GasMeter meter, ActivityScene scene, List<Long> tollItemIds);

//    /**
//     * 加载附加费收费项
//     * @param meter
//     * @return
//     */
//    Map<Long,TollItemChargeRecord> loadTollItem(GasMeter meter);
//    /**
//     * 加载场景费收费项
//     * @param gasMeterCode
//     * @return
//     */
//    Map<Long,ChargeItemRecord> loadSceneFee(String gasMeterCode);
    /**
     * 加载燃气费收费项
     * @param gasMeterCode
     * @return
     */
    List<ChargeItemRecord> loadGasFee(String gasMeterCode);
    List<ChargeItemRecord> loadGasFeeByArrearsId(List<Long> arrearsId);
    /**
     * 加载调价补差收费项
     * @param gasMeterCode
     * @return
     */
    List<ChargeItemRecord> loadAdjustPrice(String gasMeterCode);
}
