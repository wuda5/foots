package com.cdqckj.gmis.msgs.api.fallback;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.msgs.api.MsgsGeneralApi;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息通用api
 *
 * @author gmis
 * @date 2019/09/01
 */
@Component
public class MsgsGeneralApiFallback implements MsgsGeneralApi {
    @Override
    public R<Map<String, Map<String, String>>> enums() {
        return R.timeout();
    }
}
