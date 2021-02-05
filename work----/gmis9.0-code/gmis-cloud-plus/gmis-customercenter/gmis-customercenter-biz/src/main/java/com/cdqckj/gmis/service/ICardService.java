package com.cdqckj.gmis.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.entity.vo.GasMeterICVO;

import java.util.HashMap;

public interface ICardService {
    /**
     * 获取ic卡密码
     * @param encryptCardInfo IC卡头部数据,16进制,64字节
     * @return
     */
    R<GasMeterICVO> getICCardPWD(String encryptCardInfo);

    /**
     * 获取IC卡用户信息
     * @param encryptCardInfo
     * @param cardType
     * @return
     */
    R<HashMap<String,Object>> getUserInfo(String encryptCardInfo, Integer cardType);

    /**
     * 获取写卡数据
     * @param encryptCardInfo
     * @param cardType 卡类型
     * @param payCode 缴费编号
     * @return
     */
    R<Object> getWriteData(String encryptCardInfo, Integer cardType, String payCode);
}
