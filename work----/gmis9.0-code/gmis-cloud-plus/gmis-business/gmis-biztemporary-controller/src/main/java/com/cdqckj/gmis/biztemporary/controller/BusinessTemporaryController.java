package com.cdqckj.gmis.biztemporary.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.authority.service.common.AreaService;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.PoiController;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.base.utils.LocationHelper;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterArchiveService;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.biztemporary.OpenAccountRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.EstablishAccountImportPageDTO;
import com.cdqckj.gmis.biztemporary.dto.GasMeterInfoVO;
import com.cdqckj.gmis.biztemporary.dto.OpenAccountRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.TransferAccountDTO;
import com.cdqckj.gmis.biztemporary.entity.*;
import com.cdqckj.gmis.biztemporary.enums.ChargeStateEnum;
import com.cdqckj.gmis.biztemporary.enums.TransferAccountStatusEnum;
import com.cdqckj.gmis.biztemporary.service.GasMeterTempService;
import com.cdqckj.gmis.biztemporary.service.GasmeterTransferAccountService;
import com.cdqckj.gmis.biztemporary.vo.GasMeterTransferAccountVO;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.charges.api.CustomerAccountBizApi;
import com.cdqckj.gmis.charges.api.CustomerSceneChargeOrderBizApi;
import com.cdqckj.gmis.charges.dto.GenSceneOrderDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.service.CustomerAccountService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.enums.CustomerTypeIotEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.gasmeter.MeterCustomerRelatedOperTypeEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.service.BusinessTemplateService;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterFactoryService;
import com.cdqckj.gmis.gasmeter.service.GasMeterModelService;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.iot.qc.vo.UpdateDeviceVO;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.lot.DeviceBizApi;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.operateparam.service.CommunityService;
import com.cdqckj.gmis.operateparam.service.StreetService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordSaveDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import com.cdqckj.gmis.userarchive.dto.CustomerSaveDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.enumeration.CustomerStatusEnum;
import com.cdqckj.gmis.userarchive.enumeration.CustomerTypeEnum;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.I18nUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/bizTemporary")
@Api(value = "bizTemporary", tags = "临时综合")
public class BusinessTemporaryController implements PoiController<EstablishAccountImport, EstablishAccountImportPageDTO> {

    @Autowired
    private CustomerGasMeterRelatedService customerGasMeterRelatedService;
    @Autowired
    protected RedisService redisService;
    @Autowired
    private BusinessTemplateService businessTemplateService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GasMeterService gasMeterService;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private StreetService streetService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private UseGasTypeService useGasTypeService;
    @Autowired
    private OpenAccountRecordBizApi openAccountRecordBizApi;
    @Autowired
    private GasmeterTransferAccountService gasmeterTransferAccountService;
    @Autowired
    private GasMeterFactoryService gasMeterFactoryService;
    @Autowired
    private GasMeterVersionService gasMeterVersionService;
    @Autowired
    private GasMeterModelService gasMeterModelService;
    @Autowired
    private CustomerBizApi customerBizApi;
    @Autowired
    private CustomerSceneChargeOrderBizApi customerSceneChargeOrderBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    I18nUtil i18nUtil;
    @Value(value = "${gmis.baidumap.ak:gixRA2mYVFj5U5LI5wcvVqKp}")
    private String baiduAk;

    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Resource
    private CommonConfigurationApi commonConfigurationApi;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private ReadMeterDataApi readMeterDataApi;

    @Autowired
    private CustomerAccountBizApi customerAccountBizApi;
    @Autowired
    public TollItemBizApi tollItemBizApi;
    @Autowired
    public GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private GasMeterArchiveService gasMeterArchiveService;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    public ReadMeterLatestRecordApi readMeterLatestApi;
    @Autowired
    public GasMeterTempService gasMeterTempService;

    @Autowired
    public CalculateApi calculateApi;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceBizApi deviceBizApi;

    protected Class<EstablishAccountImport> entityClass = null;

    @ApiOperation(value = "开户")
    @PostMapping(value = "/establishAccount")
    @GlobalTransactional
    @CodeNotLost
    public R<GasMeter> establishAccount(@RequestBody GasMeter gasMeter,
                                        @RequestParam(value = "customerCode") String customerCode, @RequestParam(value = "chargeNo") String chargeNo,
                                        @RequestParam("isAddGasMeter") boolean isAddGasMeter){
        R<GasMeter> result = R.successDef();
        String gasCode = gasMeter.getGasCode();
        try {
            //1、查询关联表中是否有有效的关联关系
            List<CustomerGasMeterRelated> listCustomerGasMeterRelated = getCustomerGasMeterRelateds(gasCode, customerCode);
            if(ObjectUtil.isNull(listCustomerGasMeterRelated) || listCustomerGasMeterRelated.size() == 0){
                //2、客户和表具关联
                CustomerGasMeterRelated customerGasMeterRelated = saveCustomerMeterRelation(gasCode, customerCode, chargeNo);
                //3、查询客户信息
                Customer customer = customerService.findCustomer(customerCode);
                //4、保存开户记录
                OpenAccountRecord openAccountRecord = saveOpenAccountRecord(gasMeter, customer, customerGasMeterRelated);
                gasMeter.setBizNoOrId(String.valueOf(openAccountRecord.getId()));
                R<Boolean> whetherSceneChargeR = whetherSceneCharge();
                if (whetherSceneChargeR.getIsError()) {
                    throw new BizException("判断场景是否收费异常");
                }
                if(!whetherSceneChargeR.getData()){
                    //5、创建账户信息
                    createAccount(customer);
                }
                if(isAddGasMeter && whetherSceneChargeR.getData()){
                    //已有表具保存临时表
                    saveGasMeterTemp(gasMeter);
                    //更新表具信息
                    Long gasMeterId = gasMeter.getId();
                    GasMeter gasMeter1 = GasMeter
                            .builder()
                            .id(gasMeterId)
                            .useGasTypeId(gasMeter.getUseGasTypeId())
                            .build();
                    updateGasMeterMsg(gasMeter1);
                }
                else {
                    //6、更新表具信息
                    updateGasMeterMsg(gasMeter);
                }
                if(whetherSceneChargeR.getData()) {
                    //7、生成场景收费
                    customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                            .customerCode(customer.getCustomerCode())
                            .gasMeterCode(gasMeter.getGasCode())
                            .sceneCode(TolltemSceneEnum.OPEN_ACCOUNT.getCode())
                            .bizNoOrId(openAccountRecord.getId().toString()).build());
                }
            }else{
                //已关联则返回客户表具已关联异常
                return R.fail(i18nUtil.getMessage(MessageConstants.CUSTOMER_METER_RELATION_EXCEPTION),"客户表具已关联,开户异常");
            }
        } catch (Exception e) {
            log.error("开户异常，{}",e.getMessage(),e);
            return R.fail(i18nUtil.getMessage(MessageConstants.ESTABLISH_ACCOUNT_EXCEPTION));
        }
        result.setData(gasMeter);
        return result;
    }

    /**
     * 已有表具保存临时表
     * @param gasMeter
     */
    private void saveGasMeterTemp(@RequestBody GasMeter gasMeter) {
        GasMeterTemp gasMeterTemp = GasMeterTemp
                .builder()
                .gasCode(gasMeter.getGasCode())
                .gasMeterFactoryId(gasMeter.getGasMeterFactoryId())
                .gasMeterVersionId(gasMeter.getGasMeterVersionId())
                .gasMeterModelId(gasMeter.getGasMeterModelId())
                .installNumber(gasMeter.getInstallNumber())
                .installTime(gasMeter.getInstallTime())
                .direction(gasMeter.getDirection())
                .provinceCode(gasMeter.getProvinceCode())
                .cityCode(gasMeter.getCityCode())
                .areaCode(gasMeter.getAreaCode())
                .streetId(gasMeter.getStreetId())
                .communityId(gasMeter.getCommunityId())
                .communityName(gasMeter.getCommunityName())
                .gasMeterAddress(gasMeter.getGasMeterAddress())
                .moreGasMeterAddress(gasMeter.getMoreGasMeterAddress())
                .useGasTypeId(gasMeter.getUseGasTypeId())
                .useGasTypeName(gasMeter.getUseGasTypeName())
                .population(gasMeter.getPopulation())
                .nodeNumber(gasMeter.getNodeNumber())
                .longitude(gasMeter.getLongitude())
                .latitude(gasMeter.getLatitude())
                .gasMeterNumber(gasMeter.getGasMeterNumber())
                .gasMeterBase(gasMeter.getGasMeterBase())
                .build();
        UpdateWrapper<GasMeterTemp> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(GasMeterTemp::getGasCode, gasMeter.getGasCode());
        saveOrUpdate(gasMeterTemp, updateWrapper);
    }

    private void saveOrUpdate(GasMeterTemp gasMeterTemp, UpdateWrapper<GasMeterTemp> updateWrapper){
        Boolean isExists = gasMeterTempService.check(gasMeterTemp);
        if(isExists) {
            gasMeterTempService.update(gasMeterTemp, updateWrapper);
        }
        else {
            gasMeterTempService.save(gasMeterTemp);
        }
    }

    /**
     * 保存开户记录
     * @param gasMeter
     * @param customer
     */
    private OpenAccountRecord saveOpenAccountRecord(GasMeter gasMeter, Customer customer, CustomerGasMeterRelated customerGasMeterRelated ) {

        //表类型ID
        Long gasMeterVersionId = gasMeter.getGasMeterVersionId();
        R<GasMeterVersion> gasMeterVersionR = gasMeterVersionBizApi.get(gasMeterVersionId);
        if(gasMeterVersionR.getIsError() || ObjectUtil.isNull(gasMeterVersionR.getData())){
            throw new BizException("查询版号异常,或者不存在版号");
        }
        R<Boolean> whetherSceneChargeR = whetherSceneCharge();
        Integer status = ChargeStateEnum.NO_CHARGE.getCode();
        if(whetherSceneChargeR.getData()){
            status = ChargeStateEnum.WAIT_CHARGE.getCode();
        }
        OpenAccountRecord openAccountRecord = OpenAccountRecord
                .builder()
                .customerId(customer.getCustomerCode())
                .customerName(customer.getCustomerName())
                .relateId(customerGasMeterRelated.getId())
                .installId(gasMeter.getInstallNumber())
                .gasMeterId(gasMeter.getGasCode())
                .gasMeterTypeId(gasMeterVersionR.getData().getOrderSourceName())
                .gasMeterFactoryId(String.valueOf(gasMeter.getGasMeterFactoryId()))
                .gasMeterVersionId(String.valueOf(gasMeter.getGasMeterVersionId()))
                .status(status)
                .build();
        R<OpenAccountRecord> openAccountRecordR = openAccountRecordBizApi.save(BeanUtil.toBean(openAccountRecord, OpenAccountRecordSaveDTO.class));
        if(openAccountRecordR.getIsError()){
            throw new BizException("保存开户记录异常");
        }
        return openAccountRecordR.getData();
    }


    /**
     * 更新表具信息
     * @param gasMeter
     */
    private void updateGasMeterMsg(GasMeter gasMeter) {
        R<Boolean> whetherSceneChargeR = whetherSceneCharge();
        Integer dataStatus = gasMeter.getDataStatus();
        if(!whetherSceneChargeR.getData()){
            dataStatus = GasMeterStatusEnum.UnVentilation.getCode();
        }
        gasMeter.setDataStatus(dataStatus);
        gasMeter.setOpenAccountTime(LocalDateTime.now());
        gasMeter.setOpenAccountUserId(BaseContextHandler.getUserId());
        gasMeter.setOpenAccountUserName(BaseContextHandler.getName());
        gasMeter.setSendCardStauts(SendCardState.WAITE_SEND.getCode());
        gasMeterService.updateById(gasMeter);
    }

    /**
     * 创建账户
     * @param customer
     */
    private void createAccount(Customer customer) {
        //判断账户是否存在
//        new CustomerAccountUpdateDTO();
//        CustomerAccountUpdateDTO customerAccountUpdateDTO = CustomerAccountUpdateDTO
//                .builder()
//                .customerCode(customer.getCustomerCode())
//                .build();
        CustomerAccount account = customerAccountService.queryByCustomerCode(customer.getCustomerCode());
        //不存在
        if(account==null) {
            CustomerAccount customerAccount = CustomerAccount
                    .builder()
                    .companyCode(BaseContextHandler.getTenantId())
                    .companyName(BaseContextHandler.getTenantName())
                    .orgId(BaseContextHandler.getOrgId())
                    .orgName(BaseContextHandler.getOrgName())
                    //.accountCode(BizCodeUtil.genAccountDataCode(BizCCode.T, BizCodeUtil.ACCOUNT_NO))
                    .accountCode(BizCodeNewUtil.genAccountDataCode())
                    .customerCode(customer.getCustomerCode())
                    .customerName(customer.getCustomerName())
                    .accountMoney(BigDecimal.ZERO)
                    .giveMoney(BigDecimal.ZERO)
                    .status(DataStatusEnum.NORMAL.getValue())
                    .build();
            customerAccountService.save(customerAccount);
        }else{
            if(DataStatusEnum.NOT_AVAILABLE.getValue()==account.getStatus().intValue()){
                CustomerAccount updateAccount=new CustomerAccount();
                updateAccount.setId(account.getId());
                updateAccount.setStatus(DataStatusEnum.NORMAL.getValue());
                customerAccountService.updateById(updateAccount);
            }
        }
    }


    /**
     * 根据url下载导入开户模板
     * @param
     */
    @ApiOperation(value = "根据url下载导入开户模板")
    @GetMapping(value = "/exportEstablishAccountTemplate")
    @SysLog("'导出Excel:'.concat(#params.map[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    public R<String> exportEstablishAccountTemplate(@RequestParam(value = "templateCode") String templateCode,
                                                     HttpServletRequest request, HttpServletResponse response){

        String url = null;
        try {
            R<String> stringR = businessTemplateService.exportTemplate(templateCode);
            url = stringR.getData();
        } catch (Exception e) {
            log.error("下载导入开户模板失败,{}",e.getMessage(),e);
            return R.fail(i18nUtil.getMessage(MessageConstants.EXPORT_ESTABLISH_ACCOUNT_TEMPLATE_EXCEPTION),"导入开户模板异常");
        } finally {
        }
        return R.success(url);
    }
    /**
     * 根据模板编码下载导入开户模板
     * @param templateCode
     */
    @ApiOperation(value = "根据模板编码下载导入开户模板")
    @GetMapping(value = "/exportTemplateFile", produces = "application/octet-stream")
    public R<Boolean> exportTemplateFile(@RequestParam(value = "templateCode") String templateCode, HttpServletRequest request, HttpServletResponse response){
        return businessTemplateService.exportTemplateFile(templateCode, request, response);
    }


    @ApiOperation(value = "预开户")
    @PostMapping(value = "/establishAccountBeforehand")
    public R<Boolean> establishAccountBeforehand(@RequestBody GasMeter gasMeter, @RequestParam(value = "rechargeAmount") String rechargeAmount){
        try {
            updateGasMeter(gasMeter);
        } catch (Exception e) {
            log.error("预开户失败,{}",e.getMessage(),e);
            return R.fail(i18nUtil.getMessage(MessageConstants.ACCOUNT_OPENING_IN_ADVANCE_FAILED));
        } finally {
        }

        return R.success(true);
    }

    @ApiOperation(value = "批量预开户")
    @PostMapping(value = "/establishAccountBeforehandBatch")
    public R<Boolean> establishAccountBeforehandBatch(@RequestBody List<GasMeter> gasMeterList, @RequestParam(value = "rechargeAmount") String rechargeAmount){
        try {
            gasMeterList.stream().forEach(gasMeter->{
                updateGasMeter(gasMeter);
            });
        } catch (Exception e) {
            log.error("预开户失败,{}",e.getMessage(),e);
            return R.fail(i18nUtil.getMessage(MessageConstants.ACCOUNT_OPENING_IN_ADVANCE_FAILED));
        } finally {
        }
        return R.success(true);
    }

    private void updateGasMeter(GasMeter gasMeter) {
//        String gasMeterTypeId = gasMeter.getGasMeterTypeCode();
        int gasMeterStatus = gasMeter.getDataStatus();
//        BizAssert.equals(gasMeterTypeId, CommonConstants.IOT_GAS_METER, i18nUtil.getMessage(MessageConstants.ONLY_IOT_GAS_METER_ESTABLISH_ACCOUNT));
        BizAssert.equals(String.valueOf(gasMeterStatus), "0", i18nUtil.getMessage(MessageConstants.OPEN_ACCOUNT_IN_ADVANCE_MUST_WAREHOUSING));

        //更新表具状态为预开户
        gasMeter.setDataStatus(0);
        gasMeterService.updateById(gasMeter);
        //TODO 下发开户指令 下发充值指令
    }

    /**
     * 导入开户 Excel导入后的操作
     * @param list
     */
    @Override
    @CodeNotLost
    public R<JSONObject> handlerImportBackJson(List<Map<String, Object>> list){
        //1、拼装返回的数据
        List<EstablishAccountImport> establishAccountImportList = getEntityList(list);
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        establishAccountImportList.stream().forEach(establishAccountImport-> {
            //2、处理客户信息
            Customer customer = handleCustomer(establishAccountImport);
            //3、处理表具信息
            GasMeter gasMeter = handleGasMeter(establishAccountImport);
            //远传业务标志
            setRemoteServiceFlag(gasMeter);
            String customerChargeNo = establishAccountImport.getCustomerChargeNo();
            map.put("customerCode",customer.getCustomerCode());
            map.put("gasMeter",gasMeter);
            maps.add(map);
            //4、没有有效的气价方案不能开户
            PriceScheme priceScheme = getEffectivePriceScheme(gasMeter);
            if(ObjectUtil.isNull(priceScheme)){
                throw new BizException("没有有效的气价方案");
            }
            //5、新增缴费编号
            if(StringUtils.isBlank(customerChargeNo)){
                if (GmisSysSettingUtil.getOpenCustomerPrefix() == 1){
                    customerChargeNo = BizCodeNewUtil.genCustomerChargeNo(customerChargeNo,GmisSysSettingUtil.getCustomerPrefix(), customer.getCustomerCode(), gasMeter.getGasCode());
                }else{
                    throw new BizException("需手工录入缴费编号");
                }
            }

            //6、关联客户表具档案
            CustomerGasMeterRelated customerGasMeterRelated = saveBatchCustomerMeterRelation(gasMeter.getGasCode(), customer.getCustomerCode(), customerChargeNo);
            //7、保存开户记录
            OpenAccountRecord openAccountRecord = saveOpenAccountRecord(gasMeter, customer, customerGasMeterRelated);
            //8、创建账户信息
            createAccount(customer);
            //9、更新表具信息
            updateGasMeterByCode(establishAccountImport, gasMeter);
            R<Boolean> whetherSceneChargeR = whetherSceneCharge();
            if(whetherSceneChargeR.getData()) {
                //10、生成场景收费
                customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                        .customerCode(customer.getCustomerCode())
                        .gasMeterCode(gasMeter.getGasCode())
                        .sceneCode(TolltemSceneEnum.OPEN_ACCOUNT.getCode())
                        .bizNoOrId(openAccountRecord.getId().toString())
                        .isImportOpenAccount(true).build());

            }
            //查询版号
            R<GasMeterVersion> gasMeterVersionR = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId());
            if(gasMeterVersionR.getIsError() || ObjectUtil.isNull(gasMeterVersionR.getData())){
                throw new BizException("查询版号异常");
            }
            //新增气表使用情况
            GasMeterInfoSaveDTO gasMeterInfoSave= GasMeterInfoSaveDTO
                    .builder()
                    .gasmeterCode(gasMeter.getGasCode())
                    .customerCode(customer.getCustomerCode())
                    .customerName(customer.getCustomerName())
                    .priceSchemeId(priceScheme.getId())
                    .dataStatus(DataStatusEnum.NORMAL.getValue())
                    .build();
            gasMeterInfoBizApi.save(gasMeterInfoSave);
            //保存抄表初始数据
            saveInitMeterLatest(gasMeter, customer, customerChargeNo);

        });
        //10、转换为前端需要的数据返回
        JSONObject json = getJsonObject(maps, establishAccountImportList);
        return R.success(json);
    }

    /**
     * 远传业务标志
     * @param gasMeter
     */
    private void setRemoteServiceFlag(GasMeter gasMeter) {
        R<GasMeterVersion> gasMeterVersionListR = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId());
        if(gasMeterVersionListR.getIsError()){
            throw new BizException("查询版号信息异常");
        }
        if(ObjectUtil.isNull(gasMeterVersionListR.getData())){
            throw new BizException("没有版号信息");
        }
        if(!(gasMeterVersionListR.getData().getOrderSourceName().equals(OrderSourceNameEnum.IC_RECHARGE.getCode())
                && gasMeterVersionListR.getData().getOrderSourceName().equals(OrderSourceNameEnum.READMETER_PAY.getCode()))){
            List<DictionaryItem> dictionaryItems = commonConfigurationApi.findCommonConfigbytype("REMOTE_SERVICE_FLAG").getData();
            List<JsonStr> jsonStrList=new ArrayList<>();
            JsonStr jsonStr = null;
            for (DictionaryItem dictionaryItem : dictionaryItems) {
                jsonStr = new JsonStr();
                jsonStr.setCode(dictionaryItem.getCode());
                jsonStr.setName(dictionaryItem.getName());
                jsonStrList.add(jsonStr);
            }
            JSONArray json = new JSONArray(jsonStrList);
            gasMeter.setRemoteServiceFlag(json.toString());
        }
    }

    /**
     * 保存抄表初始数据
     * @param gasMeter
     * @param customer
     * @param chargeNo
     */
    private void saveInitMeterLatest(GasMeter gasMeter,Customer customer, String chargeNo) {
        String gasCode = gasMeter.getGasCode();
        List<ReadMeterLatestRecord> latestRecords = readMeterLatestApi.queryListByGasCodes(Arrays.asList(gasCode)).getData();
        if(latestRecords.size()==0){
            ReadMeterLatestRecordSaveDTO save = new ReadMeterLatestRecordSaveDTO();
            save.setYear(2000).setMonth(1);// 还得 设置此 表具初始的年月（2000.01） 和 --》底数（从表具档案获得）
            save.setReadTime(DateUtils.getDate8(2000, 1));
            BigDecimal gasMeterBase = gasMeter.getGasMeterBase();
            save.setCurrentTotalGas(gasMeterBase!=null?gasMeterBase:new BigDecimal(0.0000));
            save.setCustomerId(customer.getId().toString())
                    .setCustomerCode(customer.getCustomerCode()).setCustomerName(customer.getCustomerName())
                    .setGasMeterCode(gasMeter.getGasCode()).setGasMeterNumber(gasMeter.getGasMeterNumber());
            readMeterLatestApi.save(save);
        }
    }


    private R<Boolean> whetherSceneCharge() {
        R<Boolean> whetherSceneChargeR = tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.OPEN_ACCOUNT.getCode());
        if (whetherSceneChargeR.getIsError()) {
            throw new BizException("判断场景是否收费异常");
        }
        return whetherSceneChargeR;
    }

    /**
     * 查询气价方案
     * @param gasMeter
     */
    private PriceScheme getEffectivePriceScheme(GasMeter gasMeter) {
        Long useGasTypeId = gasMeter.getUseGasTypeId();
        R<UseGasType> useGasTypeR = useGasTypeBizApi.get(useGasTypeId);
        if(useGasTypeR.getIsError()){
            throw new BizException("查询用气类型异常");
        }
        PriceScheme priceScheme = null;
        if(useGasTypeR.getData().getPriceType().equals(PriceType.HEATING_PRICE.getCode())){
            PriceScheme priceSchemeHeating = priceSchemeBizApi.queryPriceHeatingByTime(useGasTypeId, LocalDate.now());
            LocalDate heatingDate = priceSchemeHeating.getCycleStartTime();
            LocalDate reviewDate = LocalDate.now();
            //判断抄表数据是否处于采暖季
            if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)){
                log.info("=================抄表数据处于采暖季，以采暖方案计算===============");
                priceScheme = priceSchemeHeating;
            }else{
                log.info("===============抄表数据处于非采暖季，以非采暖方案计算===============");
                priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
            }
        }else {
            priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
        }
        //启用人口递增后的价格方案
        priceScheme = isPopulationAddPriceNode(priceScheme,gasMeter.getPopulation(),useGasTypeR.getData());
        return priceScheme;
    }

    private PriceScheme isPopulationAddPriceNode(PriceScheme price,Integer population,UseGasType useGasType){
        //为1标识启用人口递增
        if(useGasType.getPopulationGrowth()==1){
            //根据气表人口数计算递增气量
            Integer cusBaseNum = useGasType.getPopulationBase();
            Integer cusNum = population;
            if(cusNum>cusBaseNum){
                BigDecimal cus = BigDecimal.valueOf(cusNum-cusBaseNum);
                //每阶梯地增量
                BigDecimal addGas = useGasType.getPopulationIncrease().multiply(cus);
                //设置每个阶梯的地增量
                price.setGas1(price.getGas1().add(addGas));
                if(price.getGas2()!=null){price.setGas2(price.getGas2().add(addGas.multiply(new BigDecimal(2))));}
                if(price.getGas3()!=null){price.setGas3(price.getGas3().add(addGas.multiply(new BigDecimal(3))));}
                if(price.getGas4()!=null){price.setGas4(price.getGas4().add(addGas.multiply(new BigDecimal(4))));}
                if(price.getGas5()!=null){price.setGas5(price.getGas5().add(addGas.multiply(new BigDecimal(5))));}
                if(price.getGas6()!=null){price.setGas6(price.getGas6().add(addGas.multiply(new BigDecimal(6))));}
                log.info("**********************启用人口递增后的价格方案**********************");
                log.info(JSON.toJSONString(price));
            }
        }
        return price;
    }

    /**
     * 更新表具信息
     * @param establishAccountImport
     * @param gasMeter
     */
    private void updateGasMeterByCode(EstablishAccountImport establishAccountImport, GasMeter gasMeter) {
        Integer dataStatus = GasMeterStatusEnum.UnVentilation.getCode();
        Map<String,Object> map = getRegionAndGasTypeInfo(establishAccountImport);
        gasMeter.setGasMeterNumber(establishAccountImport.getGasMeterNumber());
        gasMeter.setProvinceCode(map.get("provinceCode").toString());
        gasMeter.setCityCode(map.get("cityCode").toString());
        gasMeter.setAreaCode(map.get("countyCode").toString());
        gasMeter.setStreetId(Long.parseLong(map.get("streetId").toString()));
        gasMeter.setCommunityId(Long.parseLong(map.get("communityId").toString()));
        gasMeter.setMoreGasMeterAddress(map.get("moreGasMeterAddress").toString());
        gasMeter.setGasMeterAddress(establishAccountImport.getGasMeterAddress());
        gasMeter.setPopulation(establishAccountImport.getPopulation());
        gasMeter.setNodeNumber(establishAccountImport.getNodeNumber());
        Map<String, BigDecimal> latLngMap = new HashMap<>(2);

        if(establishAccountImport.getLongitude() == null
                || establishAccountImport.getLatitude() == null){
            latLngMap = LocationHelper.getLatAndLngByAddress(baiduAk, map.get("moreGasMeterAddress").toString());
            gasMeter.setLongitude(latLngMap.get("longitude"));
            gasMeter.setLatitude(latLngMap.get("latitude"));
        }else{
            gasMeter.setLongitude(establishAccountImport.getLongitude());
            gasMeter.setLatitude(establishAccountImport.getLatitude());
        }
        gasMeter.setUseGasTypeName(establishAccountImport.getUseGasTypeName());
        gasMeter.setUseGasTypeCode(map.get("userTypeCode").toString());
        gasMeter.setUseGasTypeId(Long.parseLong(String.valueOf(map.get("useGasTypeId"))));
        gasMeter.setDataStatus(dataStatus);
        gasMeter.setOpenAccountTime(LocalDateTime.now());
        gasMeter.setOpenAccountUserId(BaseContextHandler.getUserId());
        gasMeter.setOpenAccountUserName(BaseContextHandler.getName());
        gasMeterService.updateByCode(gasMeter);
    }

    private UseGasType getUseGasType(String useGasTypeName) {
        return useGasTypeService.getOne(Wraps.<UseGasType>lbQ().eq(UseGasType::getUseGasTypeName, useGasTypeName));
    }

    private Area getProvince(EstablishAccountImport establishAccountImport) {
        LbqWrapper<Area> wrapper = Wraps.lbQ();
        wrapper.eq(Area::getLevel, new RemoteData<>("PROVINCE"))
                .eq(Area::getLabel, establishAccountImport.getProvinceName());
        return areaService.getOne(wrapper);
    }

    private Area getCity(EstablishAccountImport establishAccountImport, Area provinceArea) {
        LbqWrapper<Area> wrapper = Wraps.lbQ();
        wrapper.eq(Area::getLevel, new RemoteData<>("CITY"))
                .eq(Area::getLabel, establishAccountImport.getCityName())
                .eq(Area::getParentId, provinceArea.getId());
        return areaService.getOne(wrapper);
    }

    private Area getArea(EstablishAccountImport establishAccountImport, Area cityArea) {
        LbqWrapper<Area> wrapper = Wraps.lbQ();
        wrapper.eq(Area::getLevel, new RemoteData<>("COUNTY"))
                .eq(Area::getLabel, establishAccountImport.getAreaName())
                .eq(Area::getParentId, cityArea.getId());
        return areaService.getOne(wrapper);
    }

    private Street getStreet(EstablishAccountImport establishAccountImport, Area countyArea) {
        LbqWrapper<Street> wrapper = Wraps.lbQ();
        wrapper.eq(Street::getAreaCode, countyArea.getCode())
                .eq(Street::getStreetName, establishAccountImport.getStreetName());
        return streetService.getOne(wrapper);
    }

    private Community getCommunity(EstablishAccountImport establishAccountImport, Street street) {
        LbqWrapper<Community> wrapper = Wraps.lbQ();
        wrapper.eq(Community::getStreetId, street.getId())
                .eq(Community::getCommunityName, establishAccountImport.getCommunityName());
        return communityService.getOne(wrapper);
    }

    /**
     * 组装详细地址
     * @param establishAccountImport
     * @param street
     * @param community
     * @return
     */
    private StringBuffer getMoreGasMeterAddr(EstablishAccountImport establishAccountImport, Street street, Community community) {
        StringBuffer stringBuffer = new StringBuffer();
        if(establishAccountImport.getProvinceName() != null && !"".equals(establishAccountImport.getProvinceName())){
            stringBuffer.append(establishAccountImport.getProvinceName());
        }
        if(establishAccountImport.getCityName() != null && !"".equals(establishAccountImport.getCityName())){
            stringBuffer.append(establishAccountImport.getCityName());
        }
        if(establishAccountImport.getAreaName() != null && !"".equals(establishAccountImport.getAreaName())){
            stringBuffer.append(establishAccountImport.getAreaName());
        }
        if(street.getStreetName() != null && !"".equals(street.getStreetName())){
            stringBuffer.append(street.getStreetName());
        }
        if(community.getCommunityName() != null && !"".equals(community.getCommunityName())){
            stringBuffer.append(community.getCommunityName());
        }
        return stringBuffer;
    }

    /**
     * 装返回json对象
     * @param maps
     * @param establishAccountImportList
     * @return
     */
    private JSONObject getJsonObject(List<Map<String, Object>> maps, List<EstablishAccountImport> establishAccountImportList) {
        JSONObject json=new JSONObject();
        json.accumulate("gasMeterList", maps);
        json.accumulate("total", establishAccountImportList.size());
        return json;
    }

    /**
     * 保存客户表具关联关系
     * @param gasCode
     * @param customerCode
     */
    private CustomerGasMeterRelated saveBatchCustomerMeterRelation(String gasCode, String customerCode, String chargeNo) {
        Integer dataStatus = DataStatusEnum.NORMAL.getValue();
        new CustomerGasMeterRelated();
        CustomerGasMeterRelated customerGasMeterRelated = CustomerGasMeterRelated
                .builder()
                .customerCode(customerCode)
                .gasMeterCode(gasCode)
                .customerChargeNo(chargeNo)
                .operType(MeterCustomerRelatedOperTypeEnum.OPEN_ACCOUNT.getCode())
                .dataStatus(dataStatus)
                .customerChargeFlag(GmisSysSettingUtil.getOpenCustomerPrefix() == 1 ? 1 : 0)
                .build();
        customerGasMeterRelated.setCompanyCode(BaseContextHandler.getTenant());
        customerGasMeterRelated.setCompanyName(BaseContextHandler.getTenantName());
        customerGasMeterRelated.setOrgId(BaseContextHandler.getOrgId());
        customerGasMeterRelated.setOrgName(BaseContextHandler.getOrgName());
        customerGasMeterRelatedService.save(customerGasMeterRelated);
        return customerGasMeterRelated;
    }

    /**
     * 保存客户表具关联关系
     * @param gasCode
     * @param customerCode
     */
    private CustomerGasMeterRelated saveCustomerMeterRelation(String gasCode, String customerCode, String chargeNo) {
        R<Boolean> whetherSceneChargeR = whetherSceneCharge();
        Integer dataStatus = DataStatusEnum.NORMAL.getValue();
        if(whetherSceneChargeR.getData()){
            dataStatus = DataStatusEnum.NOT_AVAILABLE.getValue();
        }
        new CustomerGasMeterRelated();
        CustomerGasMeterRelated customerGasMeterRelated = CustomerGasMeterRelated
                .builder()
                .customerCode(customerCode)
                .gasMeterCode(gasCode)
                .customerChargeNo(chargeNo)
                .operType(MeterCustomerRelatedOperTypeEnum.OPEN_ACCOUNT.getCode())
                .dataStatus(dataStatus)
                .customerChargeFlag(GmisSysSettingUtil.getOpenCustomerPrefix() == 1 ? 1 : 0)
                .build();
        customerGasMeterRelated.setCompanyCode(BaseContextHandler.getTenant());
        customerGasMeterRelated.setCompanyName(BaseContextHandler.getTenantName());
        customerGasMeterRelated.setOrgId(BaseContextHandler.getOrgId());
        customerGasMeterRelated.setOrgName(BaseContextHandler.getOrgName());
        customerGasMeterRelatedService.save(customerGasMeterRelated);
        return customerGasMeterRelated;
    }

    /**
     * 获取客户表具关联关系
     * @param gasCode
     * @param customerCode
     * @return
     */
    private List<CustomerGasMeterRelated> getCustomerGasMeterRelateds(String gasCode, String customerCode) {
        new CustomerGasMeterRelated();
        CustomerGasMeterRelated customerGasMeterRelated = CustomerGasMeterRelated
                                                .builder()
                                                .gasMeterCode(gasCode)
                                                .dataStatus(DataStatusEnum.NORMAL.getValue())
                                                .build();
        List<CustomerGasMeterRelated> customerGasMeterRelatedList = customerGasMeterRelatedService.findCustomerAndGasMeterList(customerGasMeterRelated);
        return customerGasMeterRelatedList;
    }

    /**
     * 处理表具信息
     * @param establishAccountImport
     * @return
     */
    private GasMeter handleGasMeter(EstablishAccountImport establishAccountImport) {
        GasMeter gasMeter = null;
        //厂家
        LbqWrapper<GasMeterFactory> gasMeterFactoryWrapper = Wraps.lbQ();
        gasMeterFactoryWrapper.eq(GasMeterFactory::getGasMeterFactoryName, establishAccountImport.getGasMeterFactoryName());
        GasMeterFactory gasMeterFactory = gasMeterFactoryService.getOne(gasMeterFactoryWrapper);
        //版号
        LbqWrapper<GasMeterVersion> gasMeterVersionWrapper = Wraps.lbQ();
        gasMeterVersionWrapper.eq(GasMeterVersion::getGasMeterVersionName, establishAccountImport.getGasMeterVersionName())
                .eq(GasMeterVersion::getCompanyId, gasMeterFactory.getId());
        GasMeterVersion gasMeterVersion = gasMeterVersionService.getOne(gasMeterVersionWrapper);
        //型号
        LbqWrapper<GasMeterModel> gasMeterModelWrapper = Wraps.lbQ();
        gasMeterModelWrapper.eq(GasMeterModel::getModelName, establishAccountImport.getGasMeterModelName())
                .eq(GasMeterModel::getCompanyId, gasMeterFactory.getId())
                .eq(GasMeterModel::getGasMeterVersionId, gasMeterVersion.getId());
        GasMeterModel gasMeterModel = gasMeterModelService.getOne(gasMeterModelWrapper);
        //气表编号为空
        if(StringUtils.isBlank(establishAccountImport.getGasCode())) {
            if(StringUtils.isNotBlank(establishAccountImport.getGasMeterNumber())) {
                //表具
                LbqWrapper<GasMeter> wrapper = Wraps.lbQ();
                wrapper.eq(GasMeter::getGasMeterNumber, establishAccountImport.getGasMeterNumber())
                        .eq(GasMeter::getGasMeterFactoryId, gasMeterFactory.getId())
                        .eq(GasMeter::getGasMeterVersionId, gasMeterVersion.getId())
                        .eq(GasMeter::getGasMeterModelId, gasMeterModel.getId());
                gasMeter = gasMeterService.getOne(wrapper);
            }
        }
        //气表编号非空
        else{
            //表具
            LbqWrapper<GasMeter> wrapper = Wraps.lbQ();
            wrapper.eq(GasMeter::getGasCode, establishAccountImport.getGasCode());
            gasMeter = gasMeterService.getOne(wrapper);
        }
        //通气方向
        DictionaryItem ventilationDirectionItem = DictionaryItem
                .builder()
                .dictionaryType("VENTILATION_DIRECTION")
                .name(establishAccountImport.getDirection())
                .build();
        List<DictionaryItem>  ventilationDirectionList = getDictionaryItemList(ventilationDirectionItem);
        if(ventilationDirectionList.size()>1){
            throw new BizException("有大于1个相同的通气方向名称");
        }
        //无表具信息
        Map<String,Object> map = getRegionAndGasTypeInfo(establishAccountImport);
        if(gasMeter == null){
            //新增表具信息
            GasMeter gasMeterNew = GasMeter
                    .builder()
                    .installNumber(establishAccountImport.getInstallNumber())
                    .gasMeterFactoryId(gasMeterFactory.getId())
                    .gasMeterVersionId(gasMeterVersion.getId())
                    .gasMeterModelId(gasMeterModel.getId())
                    .gasMeterNumber(establishAccountImport.getGasMeterNumber())
                    .direction(ventilationDirectionList.get(0).getCode())
                    .useGasTypeId(Long.parseLong(String.valueOf(map.get("useGasTypeId"))))
                    .useGasTypeCode(map.get("userTypeCode").toString())
                    .useGasTypeName(establishAccountImport.getUseGasTypeName())
                    .latitude(establishAccountImport.getLatitude())
                    .longitude(establishAccountImport.getLongitude())
                    .gasMeterAddress(establishAccountImport.getGasMeterAddress())
                    .population(establishAccountImport.getPopulation())
                    .nodeNumber(establishAccountImport.getNodeNumber())
                    .provinceCode(map.get("provinceCode").toString())
                    .cityCode(map.get("cityCode").toString())
                    .areaCode(map.get("countyCode").toString())
                    .streetId(Long.parseLong(map.get("streetId").toString()))
                    .communityId(Long.parseLong(map.get("communityId").toString()))
                    .moreGasMeterAddress(map.get("moreGasMeterAddress").toString())
                    .build();
            List<GasMeter> gasMeterList = new ArrayList<>();
            gasMeterList.add(gasMeterNew);
            R<List<GasMeter>> gasMeterListR = gasMeterArchiveService.addGasMeterList(gasMeterList);
            if(gasMeterListR.getIsError() || ObjectUtil.isNull(gasMeterListR.getData())
                    || gasMeterListR.getData().size() == 0){
                throw new BizException("新增表具信息异常");
            }
            gasMeter = gasMeterListR.getData().get(0);
        }else{
            gasMeter.setInstallNumber(establishAccountImport.getInstallNumber())
                    .setGasMeterFactoryId(gasMeterFactory.getId())
                    .setGasMeterVersionId(gasMeterVersion.getId())
                    .setGasMeterModelId(gasMeterModel.getId())
                    .setGasMeterNumber(establishAccountImport.getGasMeterNumber())
                    .setDirection(ventilationDirectionList.get(0).getCode())
                    .setUseGasTypeId(Long.parseLong(String.valueOf(map.get("useGasTypeId"))))
                    .setUseGasTypeCode(map.get("userTypeCode").toString())
                    .setUseGasTypeName(establishAccountImport.getUseGasTypeName())
                    .setLatitude(establishAccountImport.getLatitude())
                    .setLongitude(establishAccountImport.getLongitude())
                    .setGasMeterAddress(establishAccountImport.getGasMeterAddress())
                    .setPopulation(establishAccountImport.getPopulation())
                    .setNodeNumber(establishAccountImport.getNodeNumber())
                    .setProvinceCode(map.get("provinceCode").toString())
                    .setCityCode(map.get("cityCode").toString())
                    .setAreaCode(map.get("countyCode").toString())
                    .setStreetId(Long.parseLong(map.get("streetId").toString()))
                    .setCommunityId(Long.parseLong(map.get("communityId").toString()))
                    .setMoreGasMeterAddress(map.get("moreGasMeterAddress").toString());
            gasMeterBizApi.update(BeanUtil.toBean(gasMeter,GasMeterUpdateDTO.class));
        }
        return gasMeter;
    }

    /**
     * 获取省、市、区、街道、小区、用气类型信息
     * @param establishAccountImport
     * @return
     */
    private Map<String,Object> getRegionAndGasTypeInfo(EstablishAccountImport establishAccountImport) {

        //省
        Area provinceArea = getProvince(establishAccountImport);
        String provinceCode = provinceArea.getCode();
        //市
        Area cityArea = getCity(establishAccountImport, provinceArea);
        String cityCode = cityArea.getCode();
        //区
        Area countyArea = getArea(establishAccountImport, cityArea);
        String countyCode = countyArea.getCode();
        //查询街道
        Street street = getStreet(establishAccountImport, countyArea);
        Long streetId = street.getId();
        //查询小区
        Community community = getCommunity(establishAccountImport, street);
        Long communityId = community.getId();
        //详细地址
        StringBuffer moreGasMeterAddress = getMoreGasMeterAddr(establishAccountImport, street, community);
        //用气类型
        String useGasTypeName = establishAccountImport.getUseGasTypeName();
        UseGasType useGasTypeGet = getUseGasType(useGasTypeName);
        String userTypeCode = useGasTypeGet.getUserTypeCode();
        Long useGasTypeId = useGasTypeGet.getId();
        Map<String,Object>  map = new HashMap<>();
        map.put("provinceCode", provinceCode);
        map.put("cityCode", cityCode);
        map.put("countyCode", countyCode);
        map.put("streetId", streetId);
        map.put("communityId", communityId);
        map.put("moreGasMeterAddress", moreGasMeterAddress);
        map.put("userTypeCode", userTypeCode);
        map.put("useGasTypeId", useGasTypeId);

        return map;
    }

    /**
     * 处理客户信息
     * @param establishAccountImport
     */
    private Customer handleCustomer(EstablishAccountImport establishAccountImport) {
        //查询客户信息
        LbqWrapper<Customer> wrapper = Wraps.lbQ();
        wrapper.eq(Customer::getIdCard, establishAccountImport.getIdCard());
        wrapper.eq(Customer::getCustomerStatus, CustomerStatusEnum.EFFECTIVE.getCode());
        Customer customer = customerService.getOne(wrapper);
        //无客户信息
        if(customer == null){
            //格式化手机号
            String telphone = getFormatString(establishAccountImport.getTelphone());
            //格式化身份证
            String idCard = getFormatString(establishAccountImport.getIdCard());
            //客户类型
            DictionaryItem customerTypeItem = DictionaryItem
                    .builder()
                    .dictionaryType("USER_TYPE")
                    .name(establishAccountImport.getCustomerTypeName())
                    .build();
            List<DictionaryItem>  customerTypeItemList = getDictionaryItemList(customerTypeItem);
            //客户性别
            DictionaryItem sexItem = DictionaryItem
                    .builder()
                    .dictionaryType("SEX")
                    .name(establishAccountImport.getSex())
                    .build();
            List<DictionaryItem>  sexItemList = getDictionaryItemList(sexItem);
            new CustomerSaveDTO();
            CustomerSaveDTO customerSaveDTO = CustomerSaveDTO
                    .builder()
                    .customerName(establishAccountImport.getCustomerName())
                    .idCard(idCard)
                    .telphone(telphone)
                    .customerTypeCode(customerTypeItemList.get(0).getCode())
                    .customerTypeName(establishAccountImport.getCustomerTypeName())
                    .sex(sexItemList.get(0).getCode())
                    .contactAddress(establishAccountImport.getContactAddress())
                    .customerStatus(CustomerStatusEnum.EFFECTIVE.getCode())
                    .build();
            List<CustomerSaveDTO> customerSaveDTOs = new ArrayList<>();
            customerSaveDTOs.add(customerSaveDTO);
            R<List<Customer>> customerListR = customerBizApi.saveBatch(customerSaveDTOs);
            if(customerListR.getIsError()){
                throw new BizException("保存客户信息异常");
            }
            return customerListR.getData().get(0);
        }
        return customer;
    }

    private String getFormatString(String telphone2) {
        if(telphone2.indexOf("E") > 0 || telphone2.indexOf(".") > 0){

            Double num = Double.parseDouble(telphone2);
            BigDecimal bd = new BigDecimal(num);
            return bd.toPlainString();
        }
        return telphone2;
    }

    /**
     * 查询数据字典项
     */
    private List<DictionaryItem> getDictionaryItemList(DictionaryItem dictionaryItem){
        R<List<DictionaryItem>>  dictionaryItemListR= commonConfigurationApi.query(dictionaryItem);
        if(dictionaryItemListR.getIsError() || ObjectUtil.isNull(dictionaryItemListR.getData())){
            throw new BizException("查询数据字典项异常,或者"+dictionaryItem.getName()+"为空");
        }
        List<DictionaryItem> dictionaryItemList = dictionaryItemListR.getData();
        return dictionaryItemList;
    }

    /**
     * 过户验证:校验是否存在未完成的过户业务单
     * @author hc
     * @param gasMeterCode
     * @date 2020/11/07
     * @return step:业务步骤 ,flag:是否有未完成的业务,busData:业务单数据
     */
    @ApiOperation(value = "过户验证:如存在业务单则返回当前业务单，否则只返回表具编号")
    @GetMapping("/transferAccount/check")
    public R<HashMap<String,Object>> transferAccountCheck(@RequestParam("gasMeterCode") @NotEmpty String gasMeterCode,
                                                           @RequestParam("customerCode") @NotEmpty String customerCode){

        // 获取表具信息
        R<HashMap<String,Object>> gasMeterInfoR = gasMeterBizApi.findGasMeterInfoByCode(gasMeterCode);
        if(gasMeterInfoR.getIsError()){
            return R.fail(gasMeterInfoR.getMsg());
        }else if(null == gasMeterInfoR.getData()){
            return R.fail("表具信息获取失败");
        }
        HashMap<String,Object> gasMeterInfo = gasMeterInfoR.getData();
        //气表详情
        GasMeterInfoVO gasMeter = JSON.parseObject(JSON.toJSONString(gasMeterInfo), GasMeterInfoVO.class);
        //验证结清
        BigDecimal money = whetherSettleAccount(gasMeter);
        if(money.equals(BigDecimal.ZERO)){
            return R.fail(10032,"该表尚未结清,需结清");
        }else if(money.compareTo(BigDecimal.ZERO)<0){
            return R.fail(10032,"该预付费表尚有欠费,请结清");
        }

        HashMap<String,Object> resultData = new HashMap<>();

        //验证表具是否欠费
        if(gasmeterTransferAccountService.checkCharge(customerCode,gasMeterCode)){
            return R.fail(10032,"该表具已欠费需先交清");
        }
        //验证是否存在收费项
        R<Boolean> booleanR = tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.TRANSFER.getCode());

        resultData.put("step",1);
        resultData.put("flag",booleanR.getData());

        return  R.success(resultData);
    }

    /**
     * 过户:生成业务单
     * @author hc
     * @date 2020/11/05
     * @return
     */
    @ApiOperation(value = "过户")
    @PostMapping(value = "/transferAccount")
    @CodeNotLost
    @GlobalTransactional
    public R<GasmeterTransferAccount> transferAccount(@RequestBody TransferAccountDTO transferAccountDTO){
//        //验证是否存在业务单，存在业务单直接返回后续流
//        GasmeterTransferAccount oneByGasCode = gasmeterTransferAccountService.getOneByGasCode(transferAccountDTO.getGasMeterCode());
//        if(null!=oneByGasCode){
//            return R.fail("该表具尚存未完成的业务单,不能过户");
//        }

        //过户不能过给自己
        if(transferAccountDTO.getOldCustomerCode().equals(transferAccountDTO.getCustomerCode())){
            return R.fail("不能过户给自己");
        }

        // 获取表具信息
        R<HashMap<String,Object>> gasMeterInfoR = gasMeterBizApi.findGasMeterInfoByCode(transferAccountDTO.getGasMeterCode());
        if(gasMeterInfoR.getIsError()){
            return R.fail(gasMeterInfoR.getMsg());
        }else if(null == gasMeterInfoR.getData()){
            return R.fail("表具信息获取失败");
        }
        HashMap<String,Object> gasMeterInfo = gasMeterInfoR.getData();
        //气表详情
        GasMeterInfoVO gasMeter = JSON.parseObject(JSON.toJSONString(gasMeterInfo), GasMeterInfoVO.class);

        //该场景是否存在收费项
        R<Boolean> booleanR = tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.TRANSFER.getCode());

        //是否新开户
        if(StringUtils.isEmpty(transferAccountDTO.getCustomerCode())){
            //查看客户是否存在
//            Customer querDa = new Customer();
//            querDa.setIdCard(transferAccountDTO.getIdCard());
//            querDa.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
//            R<List<Customer>> queryR = customerBizApi.query(querDa);
//            if(CollectionUtils.isEmpty(queryR.getData())) {

                //建档
                CustomerSaveDTO item = new CustomerSaveDTO();
                item.setCustomerName(transferAccountDTO.getCustomerName());
                item.setSex(transferAccountDTO.getSex());
                item.setTelphone(transferAccountDTO.getTelphone());
                item.setContactAddress(transferAccountDTO.getContactAddress());
                item.setIdCard(transferAccountDTO.getIdCard());
                item.setCustomerTypeCode(transferAccountDTO.getCustomerTypeCode());
                item.setCustomerTypeName(CustomerTypeEnum.get(transferAccountDTO.getCustomerTypeCode()).getDesc());
                //预建档
                item.setCustomerStatus(0);

                //身份证重复性校验
                 R<Boolean> check = customerBizApi.checkAdd(item);
                if(check.getData()){
                    return R.fail("身份证号码重复");
                }

                List<CustomerSaveDTO>  saveList = new ArrayList<>();
                saveList.add(item) ;
                R<List<Customer>> r= customerBizApi.saveBatch(saveList);
                transferAccountDTO.setCustomerCode(r.getData().get(0).getCustomerCode());
                //过户不收费，创建账户 ;否者缴费创建账户
                if(!booleanR.getData()) {
                    createAccount(r.getData().get(0));
                }
//            }else{
//                Customer customer = queryR.getData().get(0);
//                //判断是否过给自己
//                if(transferAccountDTO.getOldCustomerCode().equals(customer.getCustomerCode())){
//                    return R.fail("该新增客户是自己,不能过给自己");
//                }
//                transferAccountDTO.setCustomerCode(customer.getCustomerCode());
//            }
        }


        GasmeterTransferAccount saveData = GasmeterTransferAccount.builder()
                .oldCustomerCode(gasMeter.getCustomerCode())
                .customerCode(transferAccountDTO.getCustomerCode())
                .gasMeterCode(gasMeter.getGasCode())
                .orgId(BaseContextHandler.getOrgId())
                .build();

        //是否修改缴费编号
        String chargeNo = null;
        if(GmisSysSettingUtil.getTransferAccountFlag().equals(1)) {
            //是否自动生成
            if(GmisSysSettingUtil.getOpenCustomerPrefix().equals(0)) {
                //人工录入
                //校验缴费编号是否重复
                if(StringUtils.isEmpty(transferAccountDTO.getCustomerChargeNo())){
                    return R.fail("人工录入缴费编号不为空");
                }
                new CustomerGasMeterRelatedUpdateDTO();
                CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO = CustomerGasMeterRelatedUpdateDTO
                        .builder()
                        .customerChargeNo(transferAccountDTO.getCustomerChargeNo())
                        .build();
                R<Boolean> checkChargeNoR = customerGasMeterRelatedBizApi.checkChargeNo(customerGasMeterRelatedUpdateDTO);
                if (checkChargeNoR.getIsError() || checkChargeNoR.getData()) {
                    return R.fail("缴费编号已存在");
                }
                chargeNo = transferAccountDTO.getCustomerChargeNo();
            }else{
                //chargeNo = BizCodeUtil.genCustomerChargeNo(12,GmisSysSettingUtil.getCustomerPrefix());
                chargeNo = BizCodeNewUtil.genCustomerChargeNo(chargeNo, GmisSysSettingUtil.getCustomerPrefix(), transferAccountDTO.getCustomerCode(), transferAccountDTO.getGasMeterCode());
            }
        }else{
            chargeNo = gasMeter.getCustomerChargeNo();
        }
        saveData.setCustomerChargeNo(chargeNo);
        saveData.setStatus(TransferAccountStatusEnum.WAIT_PAY.getKey());
        saveData.setPopulation(transferAccountDTO.getPopulation());
        gasmeterTransferAccountService.save(saveData);

        if(booleanR.getData()){
            /**
             * 生成过户收费单
             */
            customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                    .gasMeterCode(transferAccountDTO.getGasMeterCode())
                    .customerCode(transferAccountDTO.getCustomerCode())
                    .sceneCode(TolltemSceneEnum.TRANSFER.getCode())
                    .bizNoOrId(saveData.getId().toString()).build());
        }else{
            //自主回调
            this.transferAccountCallBack(saveData.getId());
        }

        return R.success(saveData);
    }

    /**
     * 过户：缴费业务回调接口
     * @author hc
     * @date 2020/11/06
     * @param businessNo 业务单号及过户记录表id
     * @return
     */
    @ApiOperation("过户：缴费业务回调接口")
    @GetMapping("/transferAccount/charge")
    @GlobalTransactional
    public R<GasmeterTransferAccount> transferAccountCallBack(@RequestParam("businessNo") Long businessNo){
        //获取业务单
        GasmeterTransferAccount transferAccount = gasmeterTransferAccountService.getById(businessNo);
        if(null==transferAccount || !TransferAccountStatusEnum.WAIT_PAY.getKey().equals(transferAccount.getStatus())){
            return R.success(null);
        }

        // 获取表具信息
        R<HashMap<String,Object>> gasMeterInfoR = gasMeterBizApi.findGasMeterInfoByCode(transferAccount.getGasMeterCode());
        if(gasMeterInfoR.getIsError()){
            return R.fail(gasMeterInfoR.getMsg());
        }else if(null == gasMeterInfoR.getData()){
            return R.fail("表具信息获取失败");
        }
        HashMap<String,Object> gasMeterInfo = gasMeterInfoR.getData();
        //气表详情
        GasMeterInfoVO gasMeter = JSON.parseObject(JSON.toJSONString(gasMeterInfo), GasMeterInfoVO.class);

        /**获取客户信息 **/
        Customer customer = customerBizApi.findCustomerByCode(transferAccount.getCustomerCode()).getData();

        //过户业务处理
        GasmeterTransferAccount dataDeal = gasmeterTransferAccountService.transferAccountDataDeal(transferAccount,gasMeter,customer);


        //物联网表 远传表
        if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeter.getGasMeterTypeCode())
                ||OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeter.getGasMeterTypeCode())){

            UpdateDeviceVO updateDeviceVO = new UpdateDeviceVO();
            updateDeviceVO.setCustomerType(CustomerTypeIotEnum.getByCode(gasMeter.getCustomerTypeCode()).getIotCode());
            updateDeviceVO.setAddress(gasMeter.getMoreGasMeterAddress());
            updateDeviceVO.setBaseNumber(gasMeter.getGasMeterBase());
            updateDeviceVO.setCustomerCode(customer.getCustomerCode());
            updateDeviceVO.setCustomerName(customer.getCustomerName());
            updateDeviceVO.setDeviceType(0);
            updateDeviceVO.setDirection(gasMeter.getDirection());
            updateDeviceVO.setGasMeterNumber(gasMeter.getGasMeterNumber());
            updateDeviceVO.setIgnitionTime(gasMeter.getVentilateTime());
            updateDeviceVO.setInstallationTime(gasMeter.getInstallTime());
            updateDeviceVO.setLatitude(gasMeter.getLatitude());
            updateDeviceVO.setLongitude(gasMeter.getLongitude());
            updateDeviceVO.setTelephone(customer.getTelphone());
            deviceService.updateDevice(updateDeviceVO);
        }
        return R.success(dataDeal);
    }

    /**
     * 过户：缴费业务退费回调接口
     * @author hc
     * @date 2020/11/19
     * @param businessNo 业务单号及过户记录表id
     * @return
     */
    @ApiOperation("过户：缴费业务退费回调接口")
    @GetMapping("/transferAccount/charge/back")
    public R<Boolean> transferAccountCallChargeBack(@RequestParam("businessNo") Long businessNo){

        return R.success(true);
    }

//    /**
//     * 创建客户账户
//     * @param r
//     * @return
//     */
//   private Customer createCustomerAccount(R<List<Customer>> r){
//        Customer customer = r.getData().get(0);
//
//        if(customer!=null){
//
//            //验证是否存在账户了
//            R<CustomerAccount> accountR = customerAccountBizApi.queryByCustomerCode(customer.getCustomerCode());
//            if(accountR.getData()!=null){
//                return r.getData().get(0);
//            }
//
//            List<CustomerAccountSaveDTO> saveDTOS=new ArrayList<>();
//            for (Customer cust : r.getData()) {
//                customer=cust;
//                saveDTOS.add(CustomerAccountSaveDTO
//                        .builder()
////                        .accountCode(BizCodeUtil.genAccountDataCode(BizCCode.T, BizCodeUtil.ACCOUNT_NO))
//                        .accountCode(BizCodeNewUtil.genAccountDataCode())
//                        .customerCode(customer.getCustomerCode())
//                        .customerName(customer.getCustomerName())
//                        .accountMoney(BigDecimal.ZERO)
//                        .giveMoney(BigDecimal.ZERO)
//                        .status(AccountStateEnum.ACTIVE.getCode())
//                        .build()) ;
//            }
//            if(saveDTOS.size()>0){
//                customerAccountBizApi.saveList(saveDTOS);
//            }
//        }
//        return customer;
//    }


    /**
     * 验证是否结账
     * @auther hc
     * @param gasMeter
     * @return 0 未; 1 已 ; 小于0 物联网表已欠费，需结清
     */
    private BigDecimal whetherSettleAccount(GasMeterInfoVO gasMeter){
        BigDecimal flag = BigDecimal.ZERO;

        String gasTypeCode = gasMeter.getGasMeterTypeCode();
        String customerCode = gasMeter.getCustomerCode();
        String gasMeterCode = gasMeter.getGasCode();

        //物联网中心计费 预付费表 :cb_read_meter_data_iot 是否存在未结算的数据 并结算;
        // 再验证账户余额da_gas_meter_info ->gas_meter_balance是否小于零，为负就提示去充值
        if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasTypeCode)){

            //验证未结算的抄表数据
            List<ReadMeterDataIot> dataIots = calculateApi.findSettleData(gasMeterCode,customerCode);
            if(CollectionUtils.isNotEmpty(dataIots)){
                //主动结算
                calculateApi.settlementIot(dataIots,1);
                //验证账户余额
                R<GasMeterInfo> infoR = gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeterCode, customerCode);
                if(infoR.getData()!=null){
                    if(BigDecimal.ZERO.compareTo(infoR.getData().getGasMeterBalance())>0){
                        return infoR.getData().getGasMeterBalance();
                    }
                }
            }

            flag = BigDecimal.ONE;

        //物联网中心计费 后付费表 :cb_read_meter_data_iot 是否存在未结算的数据 或 已结算未生成账单 gt_gasmeter_arrears_detail -> settlement_no
        }else if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasTypeCode)){
            //验证未结算的抄表数据
            List<ReadMeterDataIot> dataIots = calculateApi.findSettleData(gasMeterCode,customerCode);
            if(CollectionUtils.isEmpty(dataIots)){
                flag = BigDecimal.ONE;
            }
        //谱表
        }else if(OrderSourceNameEnum.READMETER_PAY.getCode().equals(gasTypeCode)){
            //验证是否存在未结算的
            CustomerGasDto queryData= new CustomerGasDto();
            queryData.setGasCode(gasMeterCode);
            queryData.setCustomerCode(customerCode);
            queryData.setOrderSourceName(gasMeter.getGasMeterTypeCode());

            if(readMeterDataApi.isFinished(queryData).getData()){
                flag = BigDecimal.ONE;
            }
        //ic卡表
        }else{
            flag = BigDecimal.ONE;;
        }

        return flag;
    }

    @Override
    public Class<EstablishAccountImport> getEntityClass() {
        return  EstablishAccountImport.class;
    }

    @Override
    public SuperService<EstablishAccountImport> getBaseService() {
        return null;
    }

    @Override
    public RedisService getRedisService() {
        return redisService;
    }

    @Override
    public String getLangMessage(String key) {
        String message = null;
        Integer langType = (Integer) redisService.get("lang"+ BaseContextHandler.getTenant());
        switch(langType){
            case 1:
                message = (String) redisService.hmget("langZh").get(key);
                break;
            case 2:
                message = (String) redisService.hmget("langEn").get(key);
                break;
            default:
                message = null;
        }
        return message;
    }

    @Override
    public EstablishAccountImport setCommonParams(EstablishAccountImport obj) {
        return null;
    }

    @ApiOperation(value = "查询业务关注点数据", notes = "查询业务关注点数据")
    @GetMapping("/queryFocusInfo")
    public R<List<GasMeterTransferAccountVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                             @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode) {
        List<GasMeterTransferAccountVO> transferAccountList = gasmeterTransferAccountService.queryFocusInfo(customerCode,gasMeterCode);
        return R.success(transferAccountList);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 16:03
     * @remark 统计过户的数据
     */
    @ApiOperation(value = "统计过户的数据")
    @PostMapping("/sts/stsTransferNum")
    R<Long> stsTransferNum(@RequestBody StsSearchParam stsSearchParam){
        Long num = gasmeterTransferAccountService.stsTransferNum(stsSearchParam);
        return R.success(num);
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                