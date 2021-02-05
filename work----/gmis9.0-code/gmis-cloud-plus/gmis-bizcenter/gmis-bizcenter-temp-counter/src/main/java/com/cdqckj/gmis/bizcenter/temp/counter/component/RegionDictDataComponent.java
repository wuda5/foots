package com.cdqckj.gmis.bizcenter.temp.counter.component;

import cn.hutool.core.util.ObjectUtil;
import com.cdqckj.gmis.authority.api.AreaBizApi;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RegionDictDataComponent {

    @Resource
    private AreaBizApi areaBizApi;
    @Autowired
    private StreetBizApi streetBizApi;
    @Autowired
    private CommunityBizApi communityBizApi;
    @Resource
    private CommonConfigurationApi commonConfigurationApi;
    @Resource
    private I18nUtil i18nUtil;

    /**
     * 省、市、区、街道、小区字典
     */
    public static final String PROVINCE = "PROVINCE";
    public static final String CITY = "CITY";
    public static final String COUNTY = "COUNTY";
    public static final String PROVINCE_CITY = "PROVINCE_CITY";
    public static final String CITY_COUNTY = "CITY_COUNTY";
    public static final String COUNTY_STREET = "COUNTY_STREET";
    public static final String STREET_COMMUNITY = "COMMUNITY";
    public static final String CUSTOMER_TYPE = "CUSTOMER_TYPE";
    public static final String SEX = "SEX";
    public static final String VENTILATION_DIRECTION = "VENTILATION_DIRECTION";

    /**
     * 主字典
     */
    private Map<String, Object> dict = new HashMap();

    public Object getDict(String condition) {
        return dict.get(condition);
    }

    public void setDict() {

            // 省、市、区、街道、小区的映射
            Map<String, TreeSet<String>> streetCommunityMap = new HashMap<>();
            Map<String, TreeSet<String>> countyStreetMap = new HashMap<>();
            Map<String, TreeSet<String>> cityCoutyMap = new HashMap<>();
            Map<String, TreeSet<String>> provinceCityMap = new HashMap<>();
            //查询省信息
            List<Area> provinces = getAreas(new RemoteData<>(PROVINCE));
            List<String> provincelist = provinces.stream().map(Area::getLabel).collect(Collectors.toList());
            TreeSet<String> provinceSet = provincelist.stream().collect(Collectors.toCollection(TreeSet::new));
            //查询省、市
            List<Area> citys = getAreas(new RemoteData<>(CITY));
            for(Area province:provinces) {
                String provinceLabel = province.getLabel();
                long provinceId = province.getId();
                List<String> citylist = citys.stream().filter(item->item.getParentId() == provinceId).map(Area::getLabel).collect(Collectors.toList());
                this.createDict(provinceCityMap, provinceLabel, citylist);
            }
            //查询市、区
            List<Area> countys = getAreas(new RemoteData<>(COUNTY));
            //市、区数据字典
            for(Area city:citys) {
                String cityLabel = city.getFullName();
                long cityId = city.getId();
                List<String> countylist = countys.stream().filter(item->item.getParentId() == cityId).map(Area::getLabel).collect(Collectors.toList());
                this.createDict(cityCoutyMap, cityLabel, countylist);
            }
            //查询区、街道
            Street streetParam = new Street();
            List<Street> streets = getStreets(streetParam);
            for(Area county : countys) {
                String coutyLabel = county.getFullName();
                String countyCode = county.getCode();
                List<String> streetlist = streets.stream().filter(item->countyCode.equals(item.getAreaCode())).map(Street::getStreetName).collect(Collectors.toList());
                this.createDict(countyStreetMap, coutyLabel, streetlist);
            }
            //街道、小区
            Community community = new Community();
            List<Community> communitys = getCommunitys(community);
            for(Street street : streets){
                String streetLabel = street.getStreetName();
                String areaCode = street.getAreaCode();
                String label = null;
                for(Area county : countys) {
                    if(areaCode.equals(county.getCode())){
                        label = county.getFullName();
                    }
                }
                label = label.concat(streetLabel);
                long streetId = street.getId();
                List<String> communitylist = communitys.stream().filter(item->item.getStreetId() == streetId).map(Community::getCommunityName).collect(Collectors.toList());
                this.createDict(streetCommunityMap, label, communitylist);
            }
            //查询客户类型
            List<DictionaryItem> customerTypeList = getDictionaryItemList("USER_TYPE");
            TreeSet<String> customerTypeSet = customerTypeList.stream().map(DictionaryItem::getName).collect(Collectors.toCollection(TreeSet::new));
            //查询客户性别
            List<DictionaryItem> sexList = getDictionaryItemList("SEX");
            TreeSet<String> sexSet = sexList.stream().map(DictionaryItem::getName).collect(Collectors.toCollection(TreeSet::new));
            //通气方向
            List<DictionaryItem> ventilationDirectionList = getDictionaryItemList("VENTILATION_DIRECTION");
            TreeSet<String> ventilationDirectionSet = ventilationDirectionList.stream().map(DictionaryItem::getName).collect(Collectors.toCollection(TreeSet::new));
            dict.put(PROVINCE, provinceSet);
            dict.put(PROVINCE_CITY, provinceCityMap);
            dict.put(CITY_COUNTY, cityCoutyMap);
            dict.put(COUNTY_STREET, countyStreetMap);
            dict.put(STREET_COMMUNITY, streetCommunityMap);
            dict.put(CUSTOMER_TYPE, customerTypeSet);
            dict.put(SEX, sexSet);
            dict.put(VENTILATION_DIRECTION, ventilationDirectionSet);

    }

    /**
     * 创建从字典
     *
     * @param map
     * @param name
     */
    private void createDict(Map<String, TreeSet<String>> map, String name, List<String> list) {
        if (map.containsKey(name)) {
            for(String str : list){
                map.get(name).add(str);
            }
        } else {
            TreeSet<String> names = new TreeSet<>();
            for(String str : list){
                names.add(str);
            }
            map.put(name, names);
        }
    }

    /**
     * 根据父类ID查询地区信息
     * @return
     */
    private List<Area> getAreaListByParentId(Long parentId) {
        new Area();
        Area areaParam = Area
                .builder()
                .parentId(parentId)
                .build();
        R<List<Area>> areaListR = areaBizApi.query(areaParam);
        if (areaListR.getIsError() || ObjectUtil.isNull(areaListR.getData())) {
            log.error("根据父类ID查询地区信息异常");
            throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_REGION_BY_PARENTID_THROW_EXCEPTION));
        }
        return areaListR.getData();
    }

    /**
     * 查询地区信息
     * @return
     */
    private List<Area> getAreas(RemoteData level) {
        new Area();
        Area areaParam = Area
                .builder()
                .level(level)
                .build();
        R<List<Area>> areaListR = areaBizApi.query(areaParam);
        if (areaListR.getIsError() || ObjectUtil.isNull(areaListR.getData())) {
            log.error("查询地区信息异常");
            throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_REGION_THROW_EXCEPTION));
        }
        return areaListR.getData();
    }

    /**
     * 查询街道
     * @return
     */
    private List<Street> getStreets(Street street){
        R<List<Street>> streetListR = streetBizApi.query(street);
        if (streetListR.getIsError() || ObjectUtil.isNull(streetListR.getData())) {
            log.error("查询街道异常");
            throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_STREET_THROW_EXCEPTION));
        }
        return streetListR.getData();
    }

    /**
     * 查询小区
     * @return
     */
    private List<Community> getCommunitys(Community community) {
        R<List<Community>> communityListR = communityBizApi.query(community);
        if (communityListR.getIsError() || ObjectUtil.isNull(communityListR.getData())) {
            log.error("查询小区异常");
            throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_COMMUNITY_THROW_EXCEPTION));
        }
        return communityListR.getData();
    }
    /**
     * 查询数据字典项
     */
    private List<DictionaryItem> getDictionaryItemList(String dictionarytype){
        R<List<DictionaryItem>>  dictionaryItemListR= commonConfigurationApi.findCommonConfigbytype(dictionarytype);
        if(dictionaryItemListR.getIsError() || ObjectUtil.isNull(dictionaryItemListR.getData())){
            throw new BizException("查询数据字典项异常");
        }
        List<DictionaryItem> dictionaryItemList = dictionaryItemListR.getData();
        return dictionaryItemList;
    }
}
