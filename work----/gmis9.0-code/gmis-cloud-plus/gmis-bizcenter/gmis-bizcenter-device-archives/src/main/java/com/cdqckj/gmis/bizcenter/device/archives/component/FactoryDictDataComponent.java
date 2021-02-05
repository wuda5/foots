package com.cdqckj.gmis.bizcenter.device.archives.component;

import com.cdqckj.gmis.authority.api.AreaBizApi;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.utils.ExcelSelectortDTO;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FactoryDictDataComponent {

    @Resource
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;

    @Autowired
    private CommonConfigurationApi commonConfigurationApi;
    @Resource
    private I18nUtil i18nUtil;

    /**
     * 主字典
     */
    private Map<String, Object> dict = new HashMap();

    public Object getDict(String condition) {
        return dict.get(condition);
    }

    public void setDict() {

        // 厂家、版号映射
        Map<String, TreeSet<String>> factoryVersionMap = new HashMap<>();
        //版号、型号映射
        Map<String, TreeSet<String>> versionModelMap = new HashMap<>();
        //查询厂家信息
        List<GasMeterFactory> gasMeterFactorys = gasMeterFactoryBizApi.query(new GasMeterFactory()).getData();
        List<String> gasMeterFactorylist = gasMeterFactorys.stream().map(GasMeterFactory::getGasMeterFactoryName).collect(Collectors.toList());
        TreeSet<String> gasMeterFactorySet = gasMeterFactorylist.stream().collect(Collectors.toCollection(TreeSet::new));
        //查询厂家、版号
        List<GasMeterVersion> gasMeterVersions = gasMeterVersionBizApi.query(new GasMeterVersion()).getData();
        Map<Long, String> factoryVersionName = new HashMap<>();
        for (GasMeterFactory gasMeterFactory : gasMeterFactorys) {
            String gasMeterFactoryName = gasMeterFactory.getGasMeterFactoryName();
            long factoryId = gasMeterFactory.getId();
            factoryVersionName.put(factoryId, gasMeterFactoryName);
            List<String> gasMeterVersionList = gasMeterVersions.stream().filter(item -> item.getCompanyId() == factoryId).map(GasMeterVersion::getGasMeterVersionName).collect(Collectors.toList());
            this.createDict(factoryVersionMap, gasMeterFactoryName, gasMeterVersionList);
        }
        //版号 型号
        List<GasMeterModel> gasMeterModels = gasMeterModelBizApi.query(new GasMeterModel()).getData();
        for (GasMeterVersion version : gasMeterVersions) {
            String factoryName = factoryVersionName.get(version.getCompanyId());
            String gasMeterVersionName = version.getGasMeterVersionName();
            long versionId = version.getId();
            List<String> gasMeteModelList = gasMeterModels.stream().filter(item -> item.getGasMeterVersionId() == versionId).map(GasMeterModel::getModelName).collect(Collectors.toList());
            this.createDict(versionModelMap, factoryName + gasMeterVersionName, gasMeteModelList);
        }

        List<DictionaryItem> directions = commonConfigurationApi.findCommonConfigbytype("VENTILATION_DIRECTION").getData();
        List<String> directionlist = directions.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> directionSet = directionlist.stream().collect(Collectors.toCollection(TreeSet::new));



/*
        List<DictionaryItem> charge_type = commonConfigurationApi.findCommonConfigbytype("CHARGE_TYPE").getData();
        List<String> charge_typelist = charge_type.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> charge_typeSet = charge_typelist.stream().collect(Collectors.toCollection(TreeSet::new));

        List<DictionaryItem> settlement_mode = commonConfigurationApi.findCommonConfigbytype("SETTLEMENT_MODE").getData();
        List<String> settlement_modelist = settlement_mode.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> settlement_modeSet = settlement_modelist.stream().collect(Collectors.toCollection(TreeSet::new));

        List<DictionaryItem> settlement_type = commonConfigurationApi.findCommonConfigbytype("SETTLEMENT_TYPE").getData();
        List<String> settlement_typelist = settlement_type.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> settlement_typeSet = settlement_typelist.stream().collect(Collectors.toCollection(TreeSet::new));*/


        dict.put("factory", gasMeterFactorySet);
        dict.put("version", factoryVersionMap);
        dict.put("model", versionModelMap);

        dict.put("directionSet", directionSet);

        /*dict.put("charge_typeSet", charge_typeSet);
        dict.put("settlement_modeSet", settlement_modeSet);
        dict.put("settlement_typeSet", settlement_typeSet);*/

    }

    public void setDictTwo() {

        // 厂家、版号映射
        Map<String, TreeSet<String>> factoryVersionMap = new HashMap<>();
        //版号、型号映射
        Map<String, TreeSet<String>> versionModelMap = new HashMap<>();
        //查询厂家信息
        List<GasMeterFactory> gasMeterFactorys = gasMeterFactoryBizApi.query(new GasMeterFactory().setGasMeterFactoryStatus(DataStatusEnum.NORMAL.getValue())).getData();
        List<DictionaryItem> items=commonConfigurationApi.findCommonConfigbytype("GAS_METER_FACTORY").getData();
        Map<String,DictionaryItem> map=new HashMap<>();
        for (DictionaryItem item : items) {
            map.put(item.getCode(),item);
        }
        gasMeterFactorys=gasMeterFactorys.stream().filter(obj->map.get(obj.getGasMeterFactoryCode())!=null).collect(Collectors.toList());

        Collections.sort(gasMeterFactorys, new Comparator<GasMeterFactory>() {
            @Override
            public int compare(GasMeterFactory o1, GasMeterFactory o2) {
                System.out.println(o1.getGasMeterFactoryName()+":"+o2.getGasMeterFactoryName());
                System.out.println(map.get(o1.getGasMeterFactoryCode()).getSortValue()+":"+map.get(o2.getGasMeterFactoryCode()).getSortValue());
                return map.get(o1.getGasMeterFactoryCode()).getSortValue()-map.get(o2.getGasMeterFactoryCode()).getSortValue();
            }
        });
        List<String> gasMeterFactorylist = gasMeterFactorys.stream().map(GasMeterFactory::getGasMeterFactoryName).collect(Collectors.toList());
        TreeSet<String> gasMeterFactorySet = new TreeSet<>();
        int i=10000;
        for (GasMeterFactory gasMeterFactory : gasMeterFactorys) {
            gasMeterFactorySet.add(i+gasMeterFactory.getGasMeterFactoryName());
            i++;
        }
//        TreeSet<String> gasMeterFactorySet = gasMeterFactorylist.stream().collect(Collectors.toCollection(TreeSet::new));

        //查询厂家、版号
        List<GasMeterVersion> gasMeterVersions = gasMeterVersionBizApi.query(new GasMeterVersion()).getData();
        Map<Long, String> factoryVersionName = new HashMap<>();
        for (GasMeterFactory gasMeterFactory : gasMeterFactorys) {
            String gasMeterFactoryName = gasMeterFactory.getGasMeterFactoryName();
            long factoryId = gasMeterFactory.getId();
            factoryVersionName.put(factoryId, gasMeterFactoryName);
            List<String> gasMeterVersionList = gasMeterVersions.stream().filter(item -> item.getCompanyId() == factoryId).map(GasMeterVersion::getGasMeterVersionName).collect(Collectors.toList());
            this.createDict(factoryVersionMap, gasMeterFactoryName, gasMeterVersionList);
        }
        //版号 型号
        List<GasMeterModel> gasMeterModels = gasMeterModelBizApi.query(new GasMeterModel()).getData();
        for (GasMeterVersion version : gasMeterVersions) {
            String factoryName = factoryVersionName.get(version.getCompanyId());
            String gasMeterVersionName = version.getGasMeterVersionName();
            if("成都秦川物联网科技股份有限公司".equals(factoryName)&&"6E2/6F2".equals(gasMeterVersionName)){
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            }
            long versionId = version.getId();
            List<String> gasMeteModelList = gasMeterModels.stream().filter(item -> item.getGasMeterVersionId() == versionId).map(GasMeterModel::getModelName).collect(Collectors.toList());
            this.createDict(versionModelMap, factoryName + gasMeterVersionName, gasMeteModelList);
        }

        List<DictionaryItem> directions = commonConfigurationApi.findCommonConfigbytype("VENTILATION_DIRECTION").getData();
        List<String> directionlist = directions.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> directionSet = directionlist.stream().collect(Collectors.toCollection(TreeSet::new));



/*
        List<DictionaryItem> charge_type = commonConfigurationApi.findCommonConfigbytype("CHARGE_TYPE").getData();
        List<String> charge_typelist = charge_type.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> charge_typeSet = charge_typelist.stream().collect(Collectors.toCollection(TreeSet::new));

        List<DictionaryItem> settlement_mode = commonConfigurationApi.findCommonConfigbytype("SETTLEMENT_MODE").getData();
        List<String> settlement_modelist = settlement_mode.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> settlement_modeSet = settlement_modelist.stream().collect(Collectors.toCollection(TreeSet::new));

        List<DictionaryItem> settlement_type = commonConfigurationApi.findCommonConfigbytype("SETTLEMENT_TYPE").getData();
        List<String> settlement_typelist = settlement_type.stream().map(DictionaryItem::getName).collect(Collectors.toList());
        TreeSet<String> settlement_typeSet = settlement_typelist.stream().collect(Collectors.toCollection(TreeSet::new));*/


        dict.put("factory", gasMeterFactorySet);
        dict.put("factory_sub", true);
        dict.put("version", factoryVersionMap);
        dict.put("model", versionModelMap);

        dict.put("directionSet", directionSet);

        /*dict.put("charge_typeSet", charge_typeSet);
        dict.put("settlement_modeSet", settlement_modeSet);
        dict.put("settlement_typeSet", settlement_typeSet);*/

    }

    /**
     * 创建从字典
     *
     * @param map
     * @param name
     */
    private void createDict(Map<String, TreeSet<String>> map, String name, List<String> list) {
        if (map.containsKey(name)) {
            for (String str : list) {
                map.get(name).add(str);
            }
        } else {
            TreeSet<String> names = new TreeSet<>();
            for (String str : list) {
                names.add(str);
            }
            map.put(name, names);
        }
    }
}
