package com.cdqckj.gmis.area2;

import cn.hutool.log.StaticLog;
import com.cdqckj.gmis.authority.entity.common.Area;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LocationCityParserDecorator {

    public List<Area> parseProvinces(List<Area> list) {

        StaticLog.info("查询出经纬度了. . . ");
        return list;
    }

}
