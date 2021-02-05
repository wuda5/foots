package com.cdqckj.gmis.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.entity.dto.GasMeterBindDTO;
import com.cdqckj.gmis.entity.vo.GasMeterICVO;
import com.cdqckj.gmis.entity.vo.GasMeterInfoVO;
import com.cdqckj.gmis.userarchive.dto.CustomerGasInfoDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;

import java.util.HashMap;
import java.util.List;

/**
 * 客户业务-气表相关service
 * @auther hc
 * @date 2020/10/16
 */
public interface CusBizGasService {
    /**
     * 获取客户燃气表及其绑定表具
     * @Auther hc
     * @param meterChargeNos
     * @return
     */
    List<CustomerGasInfoDTO> findGasMeters(List<String> meterChargeNos);

    /**
     * 同步燃气缴费编号
     * @param gasMeterBindDTO
     * @return
     */
    List<CustomerGasInfoDTO> bindGasMeters(GasMeterBindDTO gasMeterBindDTO);

    /**
     * 获取绑定表具列表
     * @auther hc
     * @param targetCus
     * @return
     */
    List<GasMeterInfoVO> findNeedBindGasMeters(Customer targetCus);

    /**
     * 获取表具详情
     * @auther hc
     * @param gascode
     * @return
     */
    R<HashMap<String,Object>> findGasMeterByCode(String gascode);

    /**
     * 表具绑定关联关系
     * @auther hc
     * @param binId
     * @return
     */
    Boolean unBindGasMeters(Long binId);

    /**
     * 获取IC卡密码
     * @auther hc
     * @param encryptCardInfo
     * @return
     */
    R<GasMeterICVO> getICCardPWD(String encryptCardInfo);
}
