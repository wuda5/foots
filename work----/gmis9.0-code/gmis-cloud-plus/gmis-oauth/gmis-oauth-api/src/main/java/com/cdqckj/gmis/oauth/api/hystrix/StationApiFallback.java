package com.cdqckj.gmis.oauth.api.hystrix;

import com.cdqckj.gmis.oauth.api.StationApi;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 熔断类
 *
 * @author gmis
 * @date 2020年02月09日11:24:23
 */
@Component
public class StationApiFallback implements StationApi {
    @Override
    public Map<Serializable, Object> findStationByIds(Set<Serializable> ids) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Serializable, Object> findStationNameByIds(Set<Serializable> ids) {
        return Collections.emptyMap();
    }
}
