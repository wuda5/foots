package com.cdqckj.gmis.service;

import com.cdqckj.gmis.entity.vo.CusFeesPayTypeVO;

import java.util.List;

public interface BaseBizService {

    /**
     * 获取租户的支付方式
     * @return
     */
    List<CusFeesPayTypeVO> payType();
}
