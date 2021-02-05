package com.cdqckj.gmis.bizcenter.device.archives.service.impl;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import com.cdqckj.gmis.archive.*;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.base.utils.ExcelCascadeSelectorDTO;
import com.cdqckj.gmis.bizcenter.device.archives.component.FactoryDictDataComponent;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterArchiveService;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.BizBCode;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.*;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.enumeration.*;
import com.cdqckj.gmis.devicearchive.vo.GasmeterPoi;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.I18nUtil;
import com.google.common.collect.Lists;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.base.MessageConstants.IMPORT_DATA;
import static com.cdqckj.gmis.base.MessageConstants.REPEAT_GAS_METER_NUMBER;

@Slf4j
@Service
public class GasMeterArchiveServiceImpl extends SuperCenterServiceImpl implements GasMeterArchiveService {
    @Autowired
    private FactoryDictDataComponent factoryDictDataComponent;
    @Autowired
    private InputOutputMeterStoryBizApi inputOutputMeterStoryBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Autowired
    private CommonConfigurationApi commonConfigurationApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private CustomerBizApi customerBizApi;
    @Autowired
    private I18nUtil i18nUtil;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    public GasMeterBookRecordApi recordApi;
    @Autowired
    public ReadMeterDataApi readMeterDataApi;

    private static final String DEFAULT_FILE_NAME = "临时文件";
    @Override
    @CodeNotLost
    public R<List<GasMeter>> addGasMeterList(List<GasMeter> gasMeterList) {
     List<GasMeter> gasMetersave=new ArrayList<>();
     for (int i =0;i<gasMeterList.size();i++) {
         GasMeter gasMeter = gasMeterList.get(i);
         GasMeterFactory gasMeterFactory = gasMeterFactoryBizApi.get(gasMeter.getGasMeterFactoryId()).getData();
         gasMeter.setGasCode(BizCodeNewUtil.genGasMeterCode(BizBCode.M));
         GasMeterVersion gasMeterVersion =  gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
         if(gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.IC_RECHARGE.getCode())
                 ||gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.READMETER_PAY.getCode())){
             if(GasMeterStatusEnum.UNVALID.getCode().equals(gasMeter.getDataStatus())) {
                 gasMeter.setGasMeterNumber(gasMeter.getGasMeterNumber());
             }else{
                 GasMeterFactory factory = gasMeterFactoryBizApi.get(gasMeter.getGasMeterFactoryId()).getData();
                 gasMeter.setGasMeterNumber(BizCodeNewUtil.genGasMeterNumber(gasMeter.getGasMeterFactoryId(), factory.getGasMeterFactoryCode(), gasMeter.getGasMeterNumber()));
                 gasMeter.setAutoGenerated(1);
             }

         }
         gasMetersave.add(gasMeter);
      }
        //物联网表新增
         deviceService.addDevice(gasMetersave);
         //普表、卡表
         List<GasMeter> gasMeters = gasMeterBizApi.addGasMeterList(gasMetersave).getData();
         Map<Long, InputOutputMeterStoryVo> resultAll=new HashMap<>();
         List<InputOutputMeterStoryVo> inputOutputMeterStoryVos=new ArrayList<>();
        if(gasMeters !=null && gasMeters.size()>0){
             for (GasMeter gasMeter : gasMeters) {
                 BigDecimal d=new BigDecimal("0.0000");
                 InputOutputMeterStoryVo temp = resultAll.get(gasMeter.getGasMeterFactoryId());
                 if(temp==null){
                     log.info("--------------------------同一厂家下没找到库存记录------------------");
                     temp=new InputOutputMeterStoryVo();
                     temp.setGasMeterFactoryId(String.valueOf(gasMeter.getGasMeterFactoryId()));
                     GasMeterFactory gasMeterFactory = gasMeterFactoryBizApi.get(gasMeter.getGasMeterFactoryId()).getData();
                     if(gasMeterFactory !=null){
                         temp.setGasMeterFactoryName(gasMeterFactory.getGasMeterFactoryName());
                     }
                     temp.setGasMeterVersionId(String.valueOf(gasMeter.getGasMeterVersionId()));
                     GasMeterVersion gasMeterVersion =  gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
                     temp.setGasMeterVersionName(gasMeterVersion.getGasMeterVersionName());

                     temp.setStoreCount(1L);
                     temp.setStatus(InputOutputMeterStoryEnum.inputStore.getCode());
//                     temp.setSerialCode(BizCodeUtil.genBizDataCode(BizBCode.P,BizCodeUtil.ARCHIVE_METER_IN_OUT_STORE));
                     temp.setSerialCode(BizCodeNewUtil.getMeterBatchCode());
                     resultAll.put(gasMeter.getGasMeterFactoryId(),temp);
                 }else {
                     log.info("--------------------------同一厂家下库存记录的批次号增加1------------------");
                     temp.setStoreCount(temp.getStoreCount()+1);
                 }
                 inputOutputMeterStoryVos.add(temp);
             }
        /*     inputOutputMeterStoryBizApi.saveList(inputOutputMeterStoryVos);*/

             return R.success(gasMeters);
         }else {
             return R.fail( gasMeterBizApi.addGasMeterList(gasMeterList).getMsg());
         }
    }

    @Override
    public void export(PageParams<GasMeterPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
        factoryDictDataComponent.setDictTwo();
        //厂
        TreeSet<String> gasMeterFactorySet= (TreeSet<String>)factoryDictDataComponent.getDict("factory");
        Boolean factorySub=(Boolean)factoryDictDataComponent.getDict("factory_sub");
        ExcelCascadeSelectorDTO factoryCascadeSelector =new ExcelCascadeSelectorDTO("厂家",'A',0, gasMeterFactorySet, "factory");
        if(factorySub!=null){
            factoryCascadeSelector.setPreSub(factorySub);
        }
        //版号
        Map<String, Object> factoryVersionMap= (Map<String, Object>)factoryDictDataComponent.getDict("version");
        ExcelCascadeSelectorDTO versionCascadeSelector =new ExcelCascadeSelectorDTO("版号",'B',1, factoryVersionMap, "version");
        //
        Map<String, Object> versionModelMap= (Map<String, Object>)factoryDictDataComponent.getDict("model");
        ExcelCascadeSelectorDTO modelCascadeSelector =new ExcelCascadeSelectorDTO("型号",'C',2, versionModelMap, "model");


        TreeSet<String> directionSet= (TreeSet<String>)factoryDictDataComponent.getDict("directionSet");
        ExcelCascadeSelectorDTO directionCascadeSelector =new ExcelCascadeSelectorDTO("通气方向",'D',0, directionSet, "direction");

/*        TreeSet<String> settlement_typeSet= (TreeSet<String>)factoryDictDataComponent.getDict("settlement_typeSet");
        ExcelCascadeSelectorDTO settlementCascadeSelector =new ExcelCascadeSelectorDTO("结算类型",'G',0, settlement_typeSet, "settlement_type");

        TreeSet<String> settlement_modeSet= (TreeSet<String>)factoryDictDataComponent.getDict("settlement_modeSet");
        ExcelCascadeSelectorDTO settlement_modeCascadeSelector =new ExcelCascadeSelectorDTO("结算方式",'H',0, settlement_modeSet, "settlement_mode");

        TreeSet<String> charge_typeSet= (TreeSet<String>)factoryDictDataComponent.getDict("charge_typeSet");
        ExcelCascadeSelectorDTO charge_typeSetCascadeSelector =new ExcelCascadeSelectorDTO("缴费类型",'I',0, charge_typeSet, "charge_type");*/

        //组装一组级联参数 省、市、区、街道、小区
        List<ExcelCascadeSelectorDTO> cascadeSelectors= new ArrayList<>();
        cascadeSelectors.add(factoryCascadeSelector);
        cascadeSelectors.add(versionCascadeSelector);
        cascadeSelectors.add(modelCascadeSelector);
        cascadeSelectors.add(directionCascadeSelector);
      /*  cascadeSelectors.add(settlementCascadeSelector);
        cascadeSelectors.add(settlement_modeCascadeSelector);
        cascadeSelectors.add(charge_typeSetCascadeSelector);*/
        //级联参数
        params.setCascadeSelectors(cascadeSelectors);
        params.setTargetSheetIndex(0);

        // feign文件下载
        Response response = gasMeterBizApi.exportCascadeTemplate(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, DEFAULT_FILE_NAME);
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }

    @Override
    public R<List<InputOutputMeterStoryVo>> importExcelDiy(MultipartFile simpleFile) throws Exception {
        Map<String,String> dictionaryItemMap=new HashMap<>();
        List<DictionaryItem> dictionaryItems = commonConfigurationApi.query(new DictionaryItem()).getData();
        for (DictionaryItem dictionaryItem : dictionaryItems) {
            dictionaryItemMap.put(dictionaryItem.getName(),dictionaryItem.getCode());
        }
        Map<Long, InputOutputMeterStoryVo> resultAll=new HashMap<>();

        Set<String> gasMeterSet=new HashSet<>();
        List<GasMeter> newGasMeters=new ArrayList<>();//本身excel表身号
        List<String> importRepeatList=new ArrayList<>();
        List<String> fail=new ArrayList<>();//数据库表身号
        List<GasMeterSaveDTO> saveGasMeters=new ArrayList<>();
        List<GasmeterPoi> gasMeters = gasMeterBizApi.importExcel(simpleFile).getData();
        if(gasMeters ==null || gasMeters.size()==0){
            return R.fail(getLangMessage(IMPORT_DATA));
        }
        List<GasMeterVersion> allVersion=gasMeterVersionBizApi.query(new GasMeterVersion()).getData();
        List<GasMeterFactory> allFactory=gasMeterFactoryBizApi.query(new GasMeterFactory()).getData();
        Map<String,Long> factoryMap=new HashMap<>();
        for (GasMeterFactory temp : allFactory) {
            factoryMap.put(temp.getGasMeterFactoryName(),temp.getId());
        }
//        Map<String,Long> factoryMap=allFactory.stream().collect(Collectors.toMap(GasMeterFactory::getGasMeterFactoryName,obj->obj.getId()));

        List<GasMeterModel> allModel=gasMeterModelBizApi.query(new GasMeterModel()).getData();

        for (GasmeterPoi poiInfo : gasMeters) {

            gasMeterSet.add(poiInfo.getGasMeterNumber());
            GasMeter gasMeter =new GasMeter();
            gasMeter.setGasMeterFactoryId(factoryMap.get(poiInfo.getGasMeterFactoryName()));
            if(gasMeter.getGasMeterFactoryId()==null){
                //返回错误
                throw new BizException("找不到厂家: "+poiInfo.getGasMeterFactoryName());
            }

            Map<String,Long> versionMap=allVersion.stream().filter(obj->
                    gasMeter.getGasMeterFactoryId().equals(obj.getCompanyId()))
                    .collect(Collectors.toMap(GasMeterVersion::getGasMeterVersionName,obj->obj.getId()));
            gasMeter.setGasMeterVersionId(versionMap.get(poiInfo.getGasMeterVersionName()));

            if(gasMeter.getGasMeterVersionId()==null){
                    //返回错误
                throw new BizException(String.format("找不到厂家对应版号:%s ,%s",poiInfo.getGasMeterFactoryName(),poiInfo.getGasMeterVersionName()));
            }
            if(!StringUtils.isEmpty(poiInfo.getGasMeterModelName())){
                Map<String,Long> modelMap=allModel.stream().filter(obj->
                        gasMeter.getGasMeterVersionId().equals(obj.getGasMeterVersionId()))
                        .collect(Collectors.toMap(GasMeterModel::getModelName,obj->obj.getId()));
                gasMeter.setGasMeterModelId(modelMap.get(poiInfo.getGasMeterModelName()));
                if(gasMeter.getGasMeterModelId()==null){
                    //返回错误
                    throw new BizException(String.format("找不到厂家版号对应的型号:%s ,%s,%s",poiInfo.getGasMeterFactoryName()
                            ,poiInfo.getGasMeterVersionName()
                            ,poiInfo.getGasMeterModelName()
                    ));

                }

                GasMeterVersion data = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
                if(data!=null && data.getOrderSourceName().equals(OrderSourceNameEnum.REMOTE_READMETER.getCode())|| data.getOrderSourceName().equals(OrderSourceNameEnum.REMOTE_RECHARGE.getCode())||data.getOrderSourceName().equals(OrderSourceNameEnum.CENTER_RECHARGE.getCode())){
                    log.info("---------------------------物联网表默认设置远程标识全选------------------------------");
                    List<DictionaryItem> dictionaryItemList = commonConfigurationApi.findCommonConfigbytype("REMOTE_SERVICE_FLAG").getData();
                    List<JsonStr> jsonStrList=new ArrayList<>();
                    JsonStr jsonStr=null;
                    for (DictionaryItem dictionaryItem : dictionaryItemList) {
                        jsonStr=new JsonStr();
                        jsonStr.setCode(dictionaryItem.getCode());
                        jsonStr.setName(dictionaryItem.getName());
                        jsonStrList.add(jsonStr);
                    }
                    JSONArray json = JSONArray.fromObject(jsonStrList);
                    gasMeter.setRemoteServiceFlag(json.toString());
                }
            }
            gasMeter.setDirection(poiInfo.getDirection());
            // 没有表身号，生成表身号，有就将表身号保存在缓存中
            GasMeterFactory factory = gasMeterFactoryBizApi.get(gasMeter.getGasMeterFactoryId()).getData();
            gasMeter.setGasMeterNumber(BizCodeNewUtil.genGasMeterNumber(gasMeter.getGasMeterFactoryId(), factory.getGasMeterFactoryCode(), poiInfo.getGasMeterNumber()));
            gasMeter.setAutoGenerated(1);

         /*   gasMeter.setGasMeterNumber(poiInfo.getGasMeterNumber());*/
            if(poiInfo.getCheckUser()!=null){
                gasMeter.setCheckUser(poiInfo.getCheckUser());
            }
            if(!StringUtils.isEmpty(poiInfo.getCheckTime())){
                gasMeter.setCheckTime(LocalDate.parse(poiInfo.getCheckTime()));
            }

            if(!StringUtils.isEmpty(poiInfo.getBuyTime())){
                gasMeter.setBuyTime(LocalDate.parse(poiInfo.getBuyTime()));
            }
            if(poiInfo.getSafeCode()!=null){
                gasMeter.setSafeCode(poiInfo.getSafeCode());
            }
            if(poiInfo.getGasMeterBase()!=null){
                gasMeter.setGasMeterBase(poiInfo.getGasMeterBase());
            }
            if(poiInfo.getRemark()!=null){
                gasMeter.setRemark(poiInfo.getRemark());
            }
            newGasMeters.add(gasMeter);

        }
        if(importRepeatList.size()>0){
            return R.fail(getLangMessage(REPEAT_GAS_METER_NUMBER)+ Arrays.toString(importRepeatList.toArray()));
        }
        for (int i=0;i<newGasMeters.size();i++) {
            GasMeter newGasMeter = newGasMeters.get(i);
             newGasMeter.setGasCode(BizCodeNewUtil.genGasMeterCode(BizBCode.M));
             BigDecimal d= BigDecimal.ZERO;
            /**原有代码**/
            // GasMeterVersion data = gasMeterVersionBizApi.get(newGasMeter.getGasMeterVersionId()).getData();
            /*new GasMeterVersion();
            GasMeterVersion gasMeterVersion = GasMeterVersion
                    .builder()
                    .companyName(newGasMeter.getGasMeterFactoryName()).gasMeterVersionName(newGasMeter.getGasMeterVersionName())
                    .build();
            R<List<GasMeterVersion>> gasMeterVersionsR = gasMeterVersionBizApi.query(gasMeterVersion);
            if(gasMeterVersionsR.getIsError()){
                log.error("查询版号异常");
                throw new BizException("查询版号异常");
            }
            if(gasMeterVersionsR.getData().size()>1){
                log.error("同一厂家不能有相同的版号");
                throw new BizException("同一厂家不能有相同的版号");
            }
            GasMeterVersion data = gasMeterVersionsR.getData().get(0);
            newGasMeter.setGasMeterVersionId(data.getId());*/

            newGasMeter.setNewSource(ArchiveSourceType.IMPORT_ARCHIVE);
            newGasMeter.setDataStatus(GasMeterStatusEnum.Pre_doc.getCode());
            newGasMeter.setVentilateStatus(GasMeterVentilateStatusEnum.NOT_VENTILATE.getValue());

            newGasMeter.setOrgId(BaseContextHandler.getOrgId());
            newGasMeter.setOrgName(BaseContextHandler.getOrgName());
            newGasMeter.setCompanyCode(BaseContextHandler.getTenantId());
            newGasMeter.setCompanyName(BaseContextHandler.getTenantName());
            if(newGasMeter.getDirection().equals(GasMeterDirectionEnum.LEFT.getDesc())){
                newGasMeter.setDirection(GasMeterDirectionEnum.LEFT.getCode());
            }else if(newGasMeter.getDirection().equals(GasMeterDirectionEnum.RIGHT.getDesc())){
                newGasMeter.setDirection(GasMeterDirectionEnum.RIGHT.getCode());
            }
       /*     GasMeterFactory gasMeterFactory= new GasMeterFactory();
            gasMeterFactory.setGasMeterFactoryName(newGasMeter.getGasMeterFactoryName());
//            gasMeterInfoSaveDTO.setGasMeterFactoryName(newGasMeter.getGasMeterFactoryName());
            List<GasMeterFactory> gasMeterFactories = gasMeterFactoryBizApi.query(gasMeterFactory).getData();
            for (GasMeterFactory meterFactory : gasMeterFactories) {
                newGasMeter.setGasMeterFactoryId(meterFactory.getId());
//                gasMeterInfoSaveDTO.setGasMeterFactoryId(meterFactory.getId());
            }
            GasMeterModel gasMeterModel=new GasMeterModel();
            if(!StringUtils.isEmpty(newGasMeter.getGasMeterModelName())){
                gasMeterModel.setGasMeterVersionName(newGasMeter.getGasMeterModelName());
            }
            List<GasMeterModel> gasMeterModels = gasMeterModelBizApi.query(gasMeterModel).getData();
            for (GasMeterModel meterModel : gasMeterModels) {
                newGasMeter.setGasMeterModelId(meterModel.getId());
            }*/
//            GasMeterVersion gasMeterVersion=new GasMeterVersion();
//            gasMeterVersion.setGasMeterVersionName(newGasMeter.getGasMeterVersionName());
//            List<GasMeterVersion> gasMeterVersions = gasMeterVersionBizApi.query(gasMeterVersion).getData();
//            for (GasMeterVersion meterVersion : gasMeterVersions) {
//                newGasMeter.setGasMeterVersionId(meterVersion.getId());
//            }
            GasMeterSaveDTO gasMeterSaveDTO=new GasMeterSaveDTO();
            BeanUtils.copyProperties(newGasMeter,gasMeterSaveDTO);
                InputOutputMeterStoryVo temp=resultAll.get(newGasMeter.getGasMeterFactoryId());
                if(temp==null){
                    log.info("--------------------------同一厂家下没找到库存记录------------------");
                    temp=new InputOutputMeterStoryVo();
                    temp.setGasMeterFactoryId(String.valueOf(newGasMeter.getGasMeterFactoryId()));
                    temp.setGasMeterVersionId(String.valueOf(newGasMeter.getGasMeterVersionId()));
                    temp.setStoreCount(1L);
                    temp.setStatus(InputOutputMeterStoryEnum.inputStore.getCode());
//                    temp.setSerialCode(BizCodeUtil.genBizDataCode(BizBCode.P,BizCodeUtil.ARCHIVE_METER_IN_OUT_STORE));
                    temp.setSerialCode(BizCodeNewUtil.getMeterBatchCode());
                    resultAll.put(newGasMeter.getGasMeterFactoryId(),temp);
                }else{
                    log.info("--------------------------同一厂家下库存记录的批次号增加1------------------");
                    temp.setStoreCount(temp.getStoreCount()+1);
                }
                saveGasMeters.add(gasMeterSaveDTO);
            }
        R<GasMeter> gasMeterR = gasMeterBizApi.saveList(saveGasMeters);
        return R.success(Lists.newArrayList(resultAll.values()));
    }

    @Override
    public R<Customer> findCustomer(CustomerGasMeterRelated customerGasMeterRelated) {
        List<CustomerGasMeterRelated> customerres = customerGasMeterRelatedBizApi.query(customerGasMeterRelated).getData();
        if (customerres==null || customerres.size()==0){
            log.info("-----------------找不到客户---------------------");
            return R.success(new Customer());
        }
        CustomerGasMeterRelated customeres = customerres.get(0);
        Customer customer=new Customer();
        customer.setCustomerCode(customeres.getCustomerCode());
        return R.success(customerBizApi.query(customer).getData().get(0));

    }

    @Override
    public R<GasMeter> update(GasMeterUpdateDTO updateDTO) {
        R<Boolean> check = gasMeterBizApi.check(updateDTO);
        if(! check.getData()){
            GasMeterVersion data = gasMeterVersionBizApi.get(updateDTO.getGasMeterVersionId()).getData();
            if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(data.getOrderSourceName())
                    ||OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(data.getOrderSourceName())
                    ||OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(data.getOrderSourceName())){
                List<GasMeter> gasMetersave=new ArrayList<>();
                GasMeter gasMeter = BeanPlusUtil.toBean(updateDTO,GasMeter.class);
                gasMetersave.add(gasMeter);
                //物联网表新增
                deviceService.addDevice(gasMetersave);
            }
            R<GasMeter> result =  gasMeterBizApi.update(updateDTO);
            updateReadData(result.getData());
            return result;
        }
        return R.fail(i18nUtil.getMessage("gasmeter.not.repeat"));
    }

    public void updateReadData(GasMeter gas) {
        if(null!=gas){
            String gasMeterCode = gas.getGasCode();
            String gasMeterNumber = gas.getGasMeterNumber();
            GasMeterBookRecord record = new GasMeterBookRecord();
            record.setGasMeterNumber(gasMeterNumber).setGasMeterCode(gasMeterCode).setMoreGasMeterAddress(gas.getMoreGasMeterAddress());
            recordApi.updateGasMeterByCode(record);
        }
    }


    @Override
    public GasMeterConfDTO gasMeterConfDTOByCode(String gasMeterCode){
        GasMeterConfDTO result=new GasMeterConfDTO();
        GasMeter meter=gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
        if(meter!=null){
            result.setGasMeterCode(gasMeterCode);
            result.setUseGasTypeId(meter.getUseGasTypeId());

            GasMeterFactory factory=gasMeterFactoryBizApi.get(meter.getGasMeterFactoryId()).getData();

            GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
            if(version!=null){
                BeanPlusUtil.copyProperties(version,result);
                result.setVersionId(version.getId());
            }

            GasMeterModel model=gasMeterModelBizApi.get(meter.getGasMeterModelId()).getData();
            if(model!=null){
                result.setModelId(model.getId());
                result.setModelName(model.getModelName());
                result.setSpecification(model.getSpecification());
            }
            if(factory!= null) {
                result.setFactoryId(factory.getId());
                result.setGasMeterFactoryCode(factory.getGasMeterFactoryCode());
                result.setGasMeterFactoryName(factory.getGasMeterFactoryName());
                result.setGasMeterFactoryMarkCode(factory.getGasMeterFactoryMarkCode());
            }
        }
        return result;
    }

    /**
     * 拆除的设备重新加入网关
     *
     * @param gasMeterList
     * @return
     */
    @Override
    public IotR addDeviceAgain(List<GasMeter> gasMeterList) {
        IotR iotR = deviceService.addDevice(gasMeterList);
        List<GasMeterUpdateDTO> collect = gasMeterList.stream().map(temp -> GasMeterUpdateDTO.builder()
                .id(temp.getId())
                .dataStatus(GasMeterStatusEnum.Pre_doc.getCode()).build())
                .collect(Collectors.toList());
        if(iotR.getIsSuccess()){
            gasMeterBizApi.updateBatchById(collect);
        }
        return iotR;
    }
}
