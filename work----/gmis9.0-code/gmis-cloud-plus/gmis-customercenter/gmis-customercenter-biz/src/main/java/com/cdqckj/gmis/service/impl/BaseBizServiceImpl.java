package com.cdqckj.gmis.service.impl;

import com.cdqckj.gmis.entity.vo.CusFeesPayTypeVO;
import com.cdqckj.gmis.service.BaseBizService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service("baseBizService")
public class BaseBizServiceImpl implements BaseBizService {

    @Override
    public List<CusFeesPayTypeVO> payType() {
        List<CusFeesPayTypeVO> result = new ArrayList<>();
        // TODO 调用服务接口获取支付方式

        return result;
    }
}
