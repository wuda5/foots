package com.cdqckj.gmis.bizcenter.temp.counter.validation;

import cn.hutool.core.util.ObjectUtil;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.authority.api.AreaBizApi;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.temp.counter.component.SpecificationConfigurationComponent;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyz
 */
@Component
@Slf4j
public class CustomerMeterDuplicateRemovalValid {
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private CustomerBizApi customerBizApi;
    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private SpecificationConfigurationComponent specificationConfigurationComponent;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Resource
    private CommonConfigurationApi commonConfigurationApi;
    @Resource
    private AreaBizApi areaBizApi;
    @Autowired
    private StreetBizApi streetBizApi;
    @Autowired
    private CommunityBizApi communityBizApi;

    @Autowired
    I18nUtil i18nUtil;

    /**
     * @remark 验证成功 返回true 失败返回false 对失败的cell添加样式
     */
    public boolean validWrongSetStyle(HSSFWorkbook workBook, List<Row> rowList, short lastCellNum) throws Exception{
        Sheet sheet = workBook.getSheetAt(0);
        HSSFCellStyle style = workBook.createCellStyle();
        HSSFFont redFont = workBook.createFont();
        redFont.setColor(Font.COLOR_RED);
        style.setFont(redFont);
        return duplicateCheck(sheet, style, rowList, lastCellNum);
    }

    /**
     * @remark 验证成功 返回true 失败返回false 对失败的cell添加样式
     */
    public boolean duplicateCheck(Sheet sheet, HSSFCellStyle style, List<Row> rowList, short lastCellNum){

        boolean validResult = true;
//        for (int i = 0; i < rowList.size(); i++){
//            Row rowI = rowList.get(i);
//            rowI.getCell(9).setCellType(CellType.STRING);
//            Cell factoryNameCellI = rowI.getCell(9);
//            String factoryNameI = factoryNameCellI.getStringCellValue();
//            rowI.getCell(12).setCellType(CellType.STRING);
//            Cell bodyNumberCellI = rowI.getCell(12);
//            String bodyNumberI = bodyNumberCellI.getStringCellValue();
//            for (int j = i+1; j < rowList.size(); j++){
//                Row rowJ = rowList.get(j);
//                rowJ.getCell(9).setCellType(CellType.STRING);
//                Cell factoryNameCellJ = rowJ.getCell(9);
//                String factoryNameJ = factoryNameCellJ.getStringCellValue();
//                rowJ.getCell(12).setCellType(CellType.STRING);
//                Cell bodyNumberCellJ = rowJ.getCell(12);
//                String bodyNumberJ = bodyNumberCellJ.getStringCellValue();
//                Cell lastCell = rowJ.getCell(lastCellNum+1);
//                if(factoryNameI.equals(factoryNameJ) && bodyNumberI.equals(bodyNumberJ)){
//                    Cell cell = rowJ.createCell(lastCellNum+1);
//                    String columnLetter = CellReference.convertNumToColString(i);
//                    validResult = setValidResult(sheet, style, cell, "表身号与"+columnLetter+"行重复");
//                }
//            }
//        }
        for (int i = 0; i < rowList.size(); i++){
            Row row = rowList.get(i);
            row.getCell(1).setCellType(CellType.STRING);
            Cell idCardCell = row.getCell(1);
            row.getCell(2).setCellType(CellType.STRING);
            Cell phoneCell = row.getCell(2);
            Cell customerTypeCell = row.getCell(3);
            Cell genderCell = row.getCell(4);
            row.getCell(6).setCellType(CellType.STRING);
            Cell gasCodeCell = row.getCell(6);
            row.getCell(7).setCellType(CellType.STRING);
            Cell customerChargeCodeCell = row.getCell(7);
            Cell factoryNameCell = row.getCell(9);
            row.getCell(10).setCellType(CellType.STRING);
            Cell versionCell = row.getCell(10);
            row.getCell(11).setCellType(CellType.STRING);
            Cell modelCell = row.getCell(11);
            row.getCell(12).setCellType(CellType.STRING);
            Cell bodyNumberCell = row.getCell(12);
            String bodyNumber = getFormatString(bodyNumberCell.getStringCellValue());
            bodyNumberCell.setCellValue(bodyNumber);
            Cell directionCell = row.getCell(13);
            Cell useGasTypeCell = row.getCell(14);
            Cell provinceCell = row.getCell(15);
            Cell cityCell = row.getCell(16);
            Cell countyCell = row.getCell(17);
            Cell streetCell = row.getCell(18);
            Cell communityCell = row.getCell(19);
            //身份证校验
            String idCard = getFormatString(idCardCell.getStringCellValue());
            idCardCell.setCellValue(idCard);
            int idCardLength = idCard.trim().length();
            if(idCardLength != IdCardValidation.NEW_CARD_NUMBER_LENGTH && idCardLength != IdCardValidation.OLD_CARD_NUMBER_LENGTH){
                validResult = setValidResult(sheet, style, idCardCell, "身份证长度不正确");
            }else {
                if (!IdCardValidation.check(idCard)) {
                    validResult = setValidResult(sheet, style, idCardCell, "身份证校验不通过");
                }
            }
            //手机号校验
            String phone = getFormatString(phoneCell.getStringCellValue());
            phoneCell.setCellValue(phone);
            if(!PhoneValidation.isMobileNumber(phone)){
                validResult = setValidResult(sheet, style, phoneCell, "手机号校验不通过");
            }
            //缴费编号校验
            if (GmisSysSettingUtil.getOpenCustomerPrefix() == 0){
                if(ObjectUtil.isNull(customerChargeCodeCell.getStringCellValue()) || "".equals(customerChargeCodeCell.getStringCellValue())) {
                    validResult = setValidResult(sheet, style, customerChargeCodeCell, "缴费编号规则为手工录入，需手工录入缴费编号");
                }
            }
            //进气方向校验
            R<List<DictionaryItem>>  ventilationListR= commonConfigurationApi.findCommonConfigbytype("VENTILATION_DIRECTION");
            if(ventilationListR.getIsError() || ObjectUtil.isNull(ventilationListR.getData()) || ventilationListR.getData().size() == 0){
                throw new BizException("查询进气方向异常");
            }
            List<DictionaryItem> ventilationList = ventilationListR.getData();
            ventilationList = ventilationList.stream().filter(item->item.getName().equals(directionCell.getStringCellValue())).collect(Collectors.toList());
            if(ObjectUtil.isNull(ventilationList) || ventilationList.size() == 0){
                validResult = setValidResult(sheet, style, directionCell, "无效进气方向");
            }

            //厂家校验
            R<List<GasMeterFactory>> gasMeterFactoryListR = gasMeterFactoryBizApi.query(new GasMeterFactory().setGasMeterFactoryName(factoryNameCell.getStringCellValue()));
            if(gasMeterFactoryListR.getIsError()){
                throw new BizException("查询厂家信息异常");
            }
            if(ObjectUtil.isNull(gasMeterFactoryListR.getData()) || gasMeterFactoryListR.getData().size() == 0){
                validResult = setValidResult(sheet, style, factoryNameCell, "无效厂家");
            }
            //版号校验
            R<List<GasMeterVersion>> gasMeterVersionListR = gasMeterVersionBizApi.query(new GasMeterVersion()
                    .setGasMeterVersionName(versionCell.getStringCellValue())
                    .setCompanyId(gasMeterFactoryListR.getData().get(0).getId()));
            if(gasMeterVersionListR.getIsError()){
                throw new BizException("查询版号信息异常");
            }
            if(ObjectUtil.isNull(gasMeterVersionListR.getData()) || gasMeterVersionListR.getData().size() == 0){
                validResult = setValidResult(sheet, style, versionCell, "无效版号");
            }
            //型号校验
            R<List<GasMeterModel>> gasMeterModelListR = gasMeterModelBizApi.query(new GasMeterModel()
                    .setModelName(modelCell.getStringCellValue())
                    .setGasMeterVersionId(gasMeterVersionListR.getData().get(0).getId()));
            if(gasMeterModelListR.getIsError()){
                throw new BizException("查询型号异常");
            }
            if(ObjectUtil.isNull(gasMeterModelListR.getData()) || gasMeterModelListR.getData().size() == 0){
                validResult = setValidResult(sheet, style, modelCell, "无效型号");
            }
            //客户类型校验
            R<List<DictionaryItem>>  dictionaryItemListR= commonConfigurationApi.findCommonConfigbytype("USER_TYPE");
            if(dictionaryItemListR.getIsError() || ObjectUtil.isNull(dictionaryItemListR.getData()) || dictionaryItemListR.getData().size() == 0){
                throw new BizException("查询数据字典项异常");
            }
            List<DictionaryItem> dictionaryItemList = dictionaryItemListR.getData();
            dictionaryItemList = dictionaryItemList.stream().filter(item->item.getName()
                    .equals(customerTypeCell.getStringCellValue())).collect(Collectors.toList());
            if(ObjectUtil.isNull(dictionaryItemList) || dictionaryItemList.size() == 0){
                validResult = setValidResult(sheet, style, customerTypeCell, "无效客户类型");
            }
            //客户性别校验
            if(!"男".equals(genderCell.getStringCellValue()) && !"女".equals(genderCell.getStringCellValue())){
                validResult = setValidResult(sheet, style, useGasTypeCell, "无效客户性别");
            }
            //省份校验
            List<Area> provinces = getAreas(new RemoteData<>("PROVINCE"), provinceCell.getStringCellValue(), false, null);
            if(ObjectUtil.isNull(provinces) || provinces.size() == 0){
                validResult = setValidResult(sheet, style, provinceCell, "无效省份名称");
            }
            //市校验
            List<Area> citys = getAreas(new RemoteData<>("CITY"), cityCell.getStringCellValue(), true , provinces.get(0).getId());
            if(ObjectUtil.isNull(citys) || citys.size() == 0){
                validResult = setValidResult(sheet, style, cityCell, "无效市名称");
            }
            //区、县校验
            List<Area> countys = getAreas(new RemoteData<>("COUNTY"), countyCell.getStringCellValue(), true, citys.get(0).getId());
            if(ObjectUtil.isNull(countys) || countys.size() == 0){
                validResult = setValidResult(sheet, style, countyCell, "无效区、县名称");
            }
            //街道校验
            R<List<Street>> streetListR = streetBizApi.query(new Street().setStreetName(streetCell.getStringCellValue()).setAreaCode(countys.get(0).getCode()));
            if(gasMeterModelListR.getIsError()){
                throw new BizException("查询街道异常");
            }
            if(ObjectUtil.isNull(streetListR.getData()) || streetListR.getData().size() == 0){
                validResult = setValidResult(sheet, style, streetCell, "无效街道");
            }
            //小区校验
            R<List<Community>> communityListR = communityBizApi.query(new Community()
                    .setCommunityName(communityCell.getStringCellValue())
                    .setStreetId(streetListR.getData().get(0).getId()));
            if(communityListR.getIsError()){
                throw new BizException("查询小区异常");
            }
            if(ObjectUtil.isNull(communityListR.getData()) || communityListR.getData().size() == 0){
                validResult = setValidResult(sheet, style, streetCell, "无效小区");
            }
            //用气类型
            if(StringUtils.isNotBlank(useGasTypeCell.getStringCellValue())) {
                UseGasType useGasTypeParam =UseGasType
                        .builder()
                        .useGasTypeName(useGasTypeCell.getStringCellValue())
                        .build();
                R<List<UseGasType>> useGasTypeListR = useGasTypeBizApi.query(useGasTypeParam);
                if(useGasTypeListR.getIsError()){
                    throw new BizException("查询用气类型异常");
                }
                //用气类型校验
                if(ObjectUtil.isNull(useGasTypeListR.getData()) || useGasTypeListR.getData().size() == 0){
                    validResult = setValidResult(sheet, style, useGasTypeCell, "无效用气类型");
                }
                //没有有效的气价方案不能开户
                Boolean flag = isThereEffectivePriceScheme(useGasTypeListR.getData().get(0).getId());
                if (!flag) {
                    validResult = setValidResult(sheet, style, useGasTypeCell, "没有有效的气价方案,或者气价方案冗余");
                }
            }
            //气表编号
            if(StringUtils.isNotBlank(gasCodeCell.getStringCellValue())) {
                new GasMeterUpdateDTO();
                GasMeterUpdateDTO gasMeterUpdateDTO = GasMeterUpdateDTO
                        .builder()
                        .gasCode(gasCodeCell.getStringCellValue())
                        .build();
                Boolean existsGasMeter = gasMeterBizApi.checkByGasCode(gasMeterUpdateDTO).getData();
                if (!existsGasMeter) {
                     validResult = setValidResult(sheet, style, gasCodeCell, "不存在该气表编号的表具信息");
                }else {
                    List<CustomerGasMeterRelated> customerGasMeterRelatedList = getCustomerGasMeterRelateds(gasCodeCell.getStringCellValue(), null);
                    if (ObjectUtil.isNotNull(customerGasMeterRelatedList) || customerGasMeterRelatedList.size() > 0) {
                        validResult = setValidResult(sheet, style, gasCodeCell, "该表具已开户");
                    }
                }
            }
            GasMeterFactory gasMeterFactory = specificationConfigurationComponent.getGasMeterFactoryByName(factoryNameCell.getStringCellValue());
            GasMeterVersion gasMeterVersion = specificationConfigurationComponent.getGasMeterVersionByName(gasMeterFactory.getId(), versionCell.getStringCellValue());
            if(StringUtils.isNotBlank(bodyNumberCell.getStringCellValue())){
                R<Boolean> isExistsR = gasMeterBizApi.isExistsBodyNumber(gasMeterFactory.getId(), bodyNumberCell.getStringCellValue());
                if(isExistsR.getIsError()){
                    throw new BizException("校验表身号异常");
                }
                if(isExistsR.getData()){
                    validResult = setValidResult(sheet, style, bodyNumberCell, "数据库中已存在该表身号");
                }
            }
            //物联网表
            if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName().trim())||
                    OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())||
                    OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersion.getOrderSourceName())){
                if(StringUtils.isBlank(bodyNumberCell.getStringCellValue())){
                     validResult = setValidResult(sheet, style, bodyNumberCell, "物联网表必填表身号");
                }else {
                    //查询客户表具关联表是否有重复的数据
                    List<CustomerGasMeterRelated> customerGasMeterRelatedList = getIoTRelation(idCard, bodyNumberCell.getStringCellValue(),
                            factoryNameCell.getStringCellValue(), versionCell.getStringCellValue(), modelCell.getStringCellValue());
                    Cell cell = row.createCell(lastCellNum);
                    if (ObjectUtil.isNotNull(customerGasMeterRelatedList) && customerGasMeterRelatedList.size() > 0) {
                         validResult = setValidResult(sheet, style, cell, i18nUtil.getMessage(MessageConstants.CUSTOMER_METER_RELATED));
                    }
                }
            }
            //其他表
            else{
                if(StringUtils.isNotBlank(gasCodeCell.getStringCellValue())) {
                    List<CustomerGasMeterRelated> customerGasMeterRelatedList = getGeneralRelation(idCard, gasCodeCell.getStringCellValue());
                    Cell cell = row.createCell(lastCellNum);
                    if (ObjectUtil.isNotNull(customerGasMeterRelatedList) && customerGasMeterRelatedList.size() > 0) {
                         validResult = setValidResult(sheet, style, cell, i18nUtil.getMessage(MessageConstants.CUSTOMER_METER_RELATED));
                    }
                }
            }
        }
        return validResult;
    }

    private String getFormatString(String str) {
        if(str.indexOf("E") > 0 || str.indexOf(".") > 0){

            Double num = Double.parseDouble(str);
            BigDecimal bd = new BigDecimal(num);
            return bd.toPlainString();
        }
        return str;
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
        R<List<CustomerGasMeterRelated>> customerGasMeterRelatedListR = customerGasMeterRelatedBizApi.findCustomerAndGasMeterList(customerGasMeterRelated);
        if(customerGasMeterRelatedListR.getIsError()){
            throw new BizException("查询客户关联关系异常");
        }
        return customerGasMeterRelatedListR.getData();
    }

    /**
     * 查询地区信息
     * @return
     */
    private List<Area> getAreas(RemoteData level, String label, Boolean flag, Long parentId) {
        new Area();
        Area areaParam = Area
                .builder()
                .level(level)
                .label(label)
                .build();
        if(flag){
            areaParam.setParentId(parentId);
        }
        R<List<Area>> areaListR = areaBizApi.query(areaParam);
        if (areaListR.getIsError()) {
            log.error("查询地区信息异常");
            throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_REGION_THROW_EXCEPTION));
        }
        return ObjectUtil.isNull(areaListR.getData()) ? null : areaListR.getData();
    }

    private boolean setValidResult(Sheet sheet, HSSFCellStyle style, Cell gasCodeCell, String errMsg) {
        boolean validResult;
        validResult = false;
        gasCodeCell.setCellStyle(style);
        HSSFPatriarch p = (HSSFPatriarch) sheet.createDrawingPatriarch();
        HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
        comment.setString(new HSSFRichTextString(errMsg));
        gasCodeCell.setCellComment(comment);
        return validResult;
    }

    /**
     * 获取客户表具关联关系
     * @param idCard
     * @param bodyNumber
     * @param factoryName
     * @param version
     * @param model
     * @return
     */
    private  List<CustomerGasMeterRelated> getIoTRelation(String idCard, String bodyNumber,
                                                                       String factoryName, String version, String model) {
        //查询客户信息
        Customer customerParam = Customer
                .builder()
                .idCard(idCard)
                .build();
        R<List<Customer>> customerListR = customerBizApi.query(customerParam);
        if(customerListR.getIsError()){
            throw new BizException("查询客户信息异常");
        }
        R<List<CustomerGasMeterRelated>> customerGasMeterRelatedListR = null;
        if(ObjectUtil.isNotNull(customerListR.getData())
                && customerListR.getData().size() > 0) {
            //查询表具信息
            GasMeter gasMeter = getGasMeter(bodyNumber, factoryName, version, model);
            if(ObjectUtil.isNotNull(gasMeter)) {
                new CustomerGasMeterRelated();
                CustomerGasMeterRelated customerGasMeterRelated = CustomerGasMeterRelated
                        .builder()
                        .customerCode(customerListR.getData().get(0).getCustomerCode())
                        .gasMeterCode(gasMeter.getGasCode())
                        .dataStatus(DataStatusEnum.NORMAL.getValue())
                        .build();
                customerGasMeterRelatedListR = customerGasMeterRelatedBizApi.findCustomerAndGasMeterList(customerGasMeterRelated);
                if (customerGasMeterRelatedListR.getIsError()) {
                    throw new BizException("查询表具客户关联信息异常");
                }
            }
        }
        if(ObjectUtil.isNull(customerGasMeterRelatedListR)){
            return null;
        }
        return (ObjectUtil.isNull(customerGasMeterRelatedListR.getData()) || customerGasMeterRelatedListR.getData().size() == 0) ? null : customerGasMeterRelatedListR.getData();
    }
    /**
     * 获取客户表具关联关系
     * @param idCard
     * @param gasCode
     * @return
     */
    private  List<CustomerGasMeterRelated> getGeneralRelation(String idCard, String gasCode) {
        //查询客户信息
        Customer customerParam = Customer
                .builder()
                .idCard(idCard)
                .build();
        R<List<Customer>> customerListR = customerBizApi.query(customerParam);
        if(customerListR.getIsError()){
            throw new BizException("查询客户信息异常,或者客户信息为空");
        }
        R<List<CustomerGasMeterRelated>> customerGasMeterRelatedListR = null;
        List<Customer> customerList = customerListR.getData();
        if(ObjectUtil.isNotNull(customerList) && customerList.size() > 0) {
            //查询表具信息
            GasMeter gasMeterParam = GasMeter.builder().build();
            R<List<GasMeter>> gasMeterListR = gasMeterBizApi.query(gasMeterParam);
            if (gasMeterListR.getIsError() || ObjectUtil.isNull(gasMeterListR.getData())
                    || gasMeterListR.getData().size() == 0) {
                throw new BizException("查询表具信息异常,或者表具信息为空");
            }
            new CustomerGasMeterRelated();
            CustomerGasMeterRelated customerGasMeterRelated = CustomerGasMeterRelated
                    .builder()
                    .customerCode(customerListR.getData().get(0).getCustomerCode())
                    .gasMeterCode(gasMeterListR.getData().get(0).getGasCode())
                    .dataStatus(DataStatusEnum.NORMAL.getValue())
                    .build();
            customerGasMeterRelatedListR = customerGasMeterRelatedBizApi.findCustomerAndGasMeterList(customerGasMeterRelated);
            if (customerGasMeterRelatedListR.getIsError()) {
                throw new BizException("查询表具客户关联信息异常");
            }
        }
        if(ObjectUtil.isNull(customerGasMeterRelatedListR)){
            return null;
        }
        return (ObjectUtil.isNull(customerGasMeterRelatedListR.getData()) || customerGasMeterRelatedListR.getData().size() == 0) ? null : customerGasMeterRelatedListR.getData();
    }

    /**
     * 查询表具信息
     * @param bodyNumber
     * @param factoryName
     * @param version
     * @param model
     * @return
     */
    private GasMeter getGasMeter(String bodyNumber, String factoryName, String version, String model){
        //厂家
        GasMeterFactory gasMeterFactoryParam = GasMeterFactory
                .builder()
                .gasMeterFactoryName(factoryName)
                .build();
        R<List<GasMeterFactory>> gasMeterFactoryListR = gasMeterFactoryBizApi.query(gasMeterFactoryParam);
        if(gasMeterFactoryListR.getIsError() || ObjectUtil.isNull(gasMeterFactoryListR.getData())
                || gasMeterFactoryListR.getData().size() == 0){
            throw new BizException("查询厂家异常");
        }
        //版号
        GasMeterVersion gasMeterVersionParam = GasMeterVersion
                .builder()
                .gasMeterVersionName(version)
                .companyId(gasMeterFactoryListR.getData().get(0).getId())
                .build();
        R<List<GasMeterVersion>> gasMeterVersionListR = gasMeterVersionBizApi.query(gasMeterVersionParam);
        if(gasMeterVersionListR.getIsError() || ObjectUtil.isNull(gasMeterVersionListR.getData())
                || gasMeterVersionListR.getData().size() == 0){
            throw new BizException("查询版号异常");
        }
        //型号
        GasMeterModel gasMeterModelParam = GasMeterModel
                .builder()
                .modelName(model)
                .companyId(gasMeterFactoryListR.getData().get(0).getId())
                .gasMeterVersionId(gasMeterVersionListR.getData().get(0).getId())
                .build();
        R<List<GasMeterModel>> gasMeterModelListR = gasMeterModelBizApi.query(gasMeterModelParam);
        if(gasMeterModelListR.getIsError() || ObjectUtil.isNull(gasMeterModelListR.getData())
                || gasMeterModelListR.getData().size() == 0){
            throw new BizException("查询型号异常");
        }
        //表具
        GasMeter gasMeterParam = GasMeter
                .builder()
                .gasMeterNumber(bodyNumber)
                .gasMeterFactoryId(gasMeterFactoryListR.getData().get(0).getId())
                .gasMeterVersionId(gasMeterVersionListR.getData().get(0).getId())
                .gasMeterModelId(gasMeterModelListR.getData().get(0).getId())
                .build();
        R<List<GasMeter>> gasMeterListR = gasMeterBizApi.query(gasMeterParam);
        if(gasMeterListR.getIsError()){
            throw new BizException("查询表具异常");
        }
        return (ObjectUtil.isNull(gasMeterListR.getData()) || gasMeterListR.getData().size() == 0) ? null : gasMeterListR.getData().get(0);
    }
    /**
     * 查询气价方案
     * @param useGasTypeId
     */
    private Boolean isThereEffectivePriceScheme(Long useGasTypeId) {
        R<UseGasType> useGasTypeR = useGasTypeBizApi.get(useGasTypeId);
        if(useGasTypeR.getIsError()){
            throw new BizException("查询用气类型异常");
        }
        PriceScheme priceScheme = null;
        if(useGasTypeR.getData().getPriceType().equals(PriceType.HEATING_PRICE.getCode())){
            priceScheme = priceSchemeBizApi.queryPriceHeatingByTime(useGasTypeId, LocalDate.now());
        }else {
            priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
        }
        if (ObjectUtil.isNull(priceScheme)) {
            return false;
        }
        return true;
    }

}
