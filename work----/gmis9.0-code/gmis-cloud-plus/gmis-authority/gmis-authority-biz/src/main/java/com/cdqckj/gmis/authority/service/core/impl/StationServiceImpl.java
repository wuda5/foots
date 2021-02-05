package com.cdqckj.gmis.authority.service.core.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.dao.core.StationMapper;
import com.cdqckj.gmis.authority.dto.core.StationPageDTO;
import com.cdqckj.gmis.authority.dto.core.UpdateStatusVO;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.authority.service.core.OrgService;
import com.cdqckj.gmis.authority.service.core.StationService;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.injection.annonation.InjectionResult;
import com.cdqckj.gmis.utils.MapHelper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.common.constant.CacheKey.STATION;

/**
 * <p>
 * 业务实现类
 * 岗位
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class StationServiceImpl extends SuperCacheServiceImpl<StationMapper, Station> implements StationService {
    @Override
    protected String getRegion() {
        return STATION;
    }

    @Autowired
    private OrgService orgService;

    @Override
    // 启用属性自动注入
    @InjectionResult
    public IPage<Station> findStationPage(IPage page, StationPageDTO data) {
        Station station = BeanUtil.toBean(data, Station.class);

        //Wraps.lbQ(station); 这种写法值 不能和  ${ew.customSqlSegment} 一起使用
        LbqWrapper<Station> wrapper = Wraps.lbQ();

        // ${ew.customSqlSegment} 语法一定要手动eq like 等
        wrapper.like(Station::getName, station.getName())
                .like(Station::getDescribe, station.getDescribe())
                .eq(Station::getOrg, data.getOrgId())
                .eq(Station::getStatus, station.getStatus())
                .geHeader(Station::getCreateTime, data.getStartCreateTime())
                .leFooter(Station::getCreateTime, data.getEndCreateTime());
        return baseMapper.findStationPage(page, wrapper, new DataScope());
    }

    @Override
    public Map<Serializable, Object> findStationByIds(Set<Serializable> ids) {
        List<Station> list = getStations(ids);

        //key 是 组织id， value 是org 对象
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, Station::getId, (station) -> station);
        return typeMap;
    }

    @Override
    public Map<Serializable, Object> findStationNameByIds(Set<Serializable> ids) {
        List<Station> list = getStations(ids);
        //key 是 组织id， value 是org 对象
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, Station::getId, Station::getName);
        return typeMap;
    }

    @Override
    public IPage<Station> findStationPageEx(PageParams<StationPageDTO> params) {

        StationPageDTO model = params.getModel();
        IPage<Station> page = params.getPage();
        if(model.getStatus()!=null){
            if( model.getStatus()){
                model.setStatusA(1);
            }else{
                model.setStatusA(0);
            }
        }
        IPage<Station> pageEx = baseMapper.findStationPageEx(page, model, new DataScope());
        if(!CollectionUtils.isEmpty(pageEx.getRecords())){
            //获取组织集
            Map<Long, Org> orgMap = orgService.list().stream().filter(a -> a.getStatus() == true)
                    .collect(Collectors.toMap(Org::getId, Function.identity()));
            for(Station item : pageEx.getRecords()){
                ArrayList<String> builder = new ArrayList();
                //当前所属组织
                Org org = orgMap.get(item.getOrg().getKey());
                dealOrg(org,orgMap,builder);
                Collections.reverse(builder);
                String orgStr = StringUtils.join(builder, "/");
                Org orgTag = new Org();
                BeanUtils.copyProperties(org,orgTag);
                orgTag.setLabel(orgStr);
                item.getOrg().setData(orgTag);
            }
        }
        return pageEx;
    }

    /**
     * 组织机构层级处理
     * @auther hc
     * @param org 当前组织
     * @param orgMap 所有组织集
     * @return
     */
    private void dealOrg(Org org,Map<Long, Org> orgMap,ArrayList<String> builder){
        builder.add(org.getLabel());
        //当前岗位所属组织
        if(!org.getParentId().equals(0)){
            Org parentOrg = orgMap.get(org.getParentId());
            if(null!=parentOrg){
                this.dealOrg(parentOrg,orgMap,builder);
            }
        }
    }

    @Override
    public Boolean checkStation(Long orgId, String name) {
        Boolean flag = false;

        LbqWrapper<Station> wrapper = Wraps.lbQ();
        wrapper.eq(Station::getOrg,orgId)
                .eq(Station::getStatus, DataStatusEnum.NORMAL.getValue())
                .eq(Station::getName,name);

        List<Station> stations = baseMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(stations)){
            flag = true;
        }

        return flag;
    }

    @Override
    public Boolean reviseStatus(UpdateStatusVO updateStatusVO) {
        Boolean flag = false;
        LbuWrapper<Station> wrapper = Wraps.lbU();
        wrapper.set(Station::getStatus,updateStatusVO.getStatus())
                .in(Station::getId,updateStatusVO.getIds());
        int update = baseMapper.update(null, wrapper);

        if(update>0){
            flag = true;
        }
        return flag;
    }

    private List<Station> getStations(Set<Serializable> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> idList = ids.stream().mapToLong(Convert::toLong).boxed().collect(Collectors.toList());

        List<Station> list = null;
        if (idList.size() <= 1000) {
            list = idList.stream().map(this::getByIdCache).filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            LbqWrapper<Station> query = Wraps.<Station>lbQ()
                    .in(Station::getId, idList)
                    .eq(Station::getStatus, true);
            list = super.list(query);

            if (!list.isEmpty()) {
                list.forEach(item -> {
                    String itemKey = key(item.getId());
                    cacheChannel.set(getRegion(), itemKey, item);
                });
            }
        }
        return list;
    }

}
