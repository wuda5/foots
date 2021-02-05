package com.cdqckj.gmis.bizcenter.temp.counter.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.authority.api.AreaBizApi;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.ExcelCascadeSelectorDTO;
import com.cdqckj.gmis.base.utils.ExcelSelectortDTO;
import com.cdqckj.gmis.bizcenter.customer.archives.service.UserArchiveService;
import com.cdqckj.gmis.bizcenter.device.archives.component.FactoryDictDataComponent;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterArchiveService;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceVo;
import com.cdqckj.gmis.bizcenter.temp.counter.component.IotOpenAccountComponent;
import com.cdqckj.gmis.bizcenter.temp.counter.component.RegionDictDataComponent;
import com.cdqckj.gmis.bizcenter.temp.counter.constants.TempCounterMessageConstants;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.TransferAccountCheckDTO;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.UseGasTypeChangeDTO;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.UseGasTypeChangeVO;
import com.cdqckj.gmis.bizcenter.temp.counter.entity.EstablishAccount;
import com.cdqckj.gmis.bizcenter.temp.counter.service.OpenAccountService;
import com.cdqckj.gmis.bizcenter.temp.counter.validation.*;
import com.cdqckj.gmis.biztemporary.AccountOpenTaskInfoBizApi;
import com.cdqckj.gmis.biztemporary.BusinessTemporaryBizApi;
import com.cdqckj.gmis.biztemporary.CustomerTempBizApi;
import com.cdqckj.gmis.biztemporary.GasTypeChangeRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.CustomerTempSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.EstablishAccountImportPageDTO;
import com.cdqckj.gmis.biztemporary.dto.GasTypeChangeRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.TransferAccountDTO;
import com.cdqckj.gmis.biztemporary.entity.AccountOpenTaskInfo;
import com.cdqckj.gmis.biztemporary.entity.CustomerTemp;
import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.domain.excell.ExcelImportValid;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterExVo;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.operateparam.*;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordSaveDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.userarchive.dto.CustomerSaveDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.enumeration.CustomerStatusEnum;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.I18nUtil;
import feign.Response;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.base.R.FAIL_CODE;

@Slf4j
@Validated
@RestController
@RequestMapping("/tempCounter")
@Api(value = "tempCounter", tags = "临时柜台")
public class TempCounterController {

    @Autowired
    private BusinessTemporaryBizApi businessTemporaryBizApi;
    @Autowired
    private AccountOpenTaskInfoBizApi accountOpenTaskInfoBizApi;
    @Autowired
    private AreaBizApi areaBizApi;
    @Autowired
    private StreetBizApi streetBizApi;
    @Autowired
    private CommunityBizApi communityBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private CustomerBizApi customerBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private AttachmentApi attachmentApi;
    @Autowired
    private RegionDictDataComponent regionDictDataComponent;
    @Autowired
    I18nUtil i18nUtil;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private UserBizApi userBizApi;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private GasTypeChangeRecordBizApi gasTypeChangeRecordBizApi;
    @Autowired
    private FactoryDictDataComponent factoryDictDataComponent;
    @Autowired
    private GasMeterArchiveService gasMeterArchiveService;
    @Autowired
    private UserArchiveService userArchiveService;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private CustomerMeterDuplicateRemovalValid customerMeterDuplicateRemovalValid;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private GasMeterBookRecordApi recordApi;
    @Autowired
    private ReadMeterDataApi dataApi;
    @Autowired
    public ReadMeterLatestRecordApi readMeterLatestApi;

    @Autowired
    public GasMeterInfoBizApi gasMeterInfoBizApi;
    @Resource
    private OpenAccountService openAccountService;
    @Autowired
    private IotOpenAccountComponent iotOpenAccountComponent;
    @Autowired
    public TollItemBizApi tollItemBizApi;
    @Autowired
    private CustomerTempBizApi customerTempBizApi;

    private static final String PROVINCE = "PROVINCE";
    private static final String CITY = "CITY";
    private static final String COUNTY = "COUNTY";
    private static final String CERTIFICATE_TYPE = "CERTIFICATE_TYPE";
    private static final String DEFAULT_FILE_NAME = "临时文件";
    @Autowired
    private Map<String, ValidationHandler> handlerMap;

    /**
     * 开户
     * @param establishAccount
     * @return
     */
    @ApiOperation(value = "开户")
    @PostMapping(value = "/establishAccount")
    @GlobalTransactional
    @CodeNotLost
    public R<EstablishAccount> establishAccount(@RequestBody EstablishAccount establishAccount){
        //获取表具信息、客户信息
        GasMeter gasMeter = establishAccount.getGasMeter();
        Customer customer = establishAccount.getCustomer();

        //根据身份证号查询客户信息
        Customer customerQuery = Customer
                .builder()
                .idCard(customer.getIdCard())
                .customerStatus(CustomerStatusEnum.EFFECTIVE.getCode())
                .build();
        R<List<Customer>> customerListR = customerBizApi.query(customerQuery);
        if(customerListR.getIsError()){
            throw new BizException("查询客户信息异常");
        }
        //校验缴费编号
        String chargeNo = establishAccount.getChargeNo();
        if(StringUtils.isNotBlank(chargeNo)) {
            if(StringUtils.isBlank(customer.getCustomerCode())) {
                if (ObjectUtil.isNotNull(customerListR.getData()) && customerListR.getData().size() > 0) {
                    return R.fail("身份证号重复");
                }
            }
        }
        if(ObjectUtil.isNotNull(customerListR.getData()) && customerListR.getData().size() == 1) {
            customer = customerListR.getData().get(0);
        }
        //没有有效的气价方案不能开户
        PriceScheme priceScheme = null;
        try {
            priceScheme = getEffectivePriceScheme(gasMeter);
        } catch (Exception e) {
            log.error("查询气价方案异常，{}", e.getMessage(), e);
            return R.fail("查询气价方案异常");
        } finally {
        }
        if(ObjectUtil.isNull(priceScheme)){
            return R.fail("没有有效的气价方案");
        }
        if(StringUtils.isNotBlank(chargeNo)) {
            new CustomerGasMeterRelatedUpdateDTO();
            CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO = CustomerGasMeterRelatedUpdateDTO
                    .builder()
                    .customerChargeNo(chargeNo)
                    .build();
            R<Boolean> checkChargeNoR = customerGasMeterRelatedBizApi.checkChargeNo(customerGasMeterRelatedUpdateDTO);
            if (checkChargeNoR.getIsError() || ObjectUtil.isNull(checkChargeNoR.getData())) {
                throw new BizException("校验缴费编号异常");
            }
            if(checkChargeNoR.getData()){
                return R.fail("缴费编号已存在");
            }
        }
        //新增缴费编号
        else if(StringUtils.isBlank(chargeNo)) {
            if (GmisSysSettingUtil.getOpenCustomerPrefix() == 1){
                chargeNo = BizCodeNewUtil.genCustomerChargeNo(chargeNo, GmisSysSettingUtil.getCustomerPrefix(), customer.getCustomerCode(), gasMeter.getGasCode());
            }else{
                throw new BizException("缴费编码规则设置为手工录入，需手工录入缴费编号");
            }
        }
        //判断物联网表表身号不能为空
        checkIotGasMeter(gasMeter);

        //新增表具信息
        Boolean isAddGasMeter = false;
        if(StringUtils.isBlank(gasMeter.getGasCode())){
            isAddGasMeter =true;
            List<GasMeter> gasMeterList = new ArrayList<>();
            gasMeter.setDataStatus(GasMeterStatusEnum.UNVALID.getCode());
            gasMeter.setRemoveGasmeter(UUID.randomUUID().toString().replaceAll("-", ""));
            gasMeterList.add(gasMeter);
            GasMeterVersion gasMeterVersion =  gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
            if(!gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.IC_RECHARGE.getCode())
                    && !gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.READMETER_PAY.getCode())) {
                if(StringUtils.isBlank(gasMeter.getGasMeterNumber())){
                    return R.fail("物联网表必须输入表身号");
                }else if (!ValidationUtil.isLetterDigit(gasMeter.getGasMeterNumber())) {
                    return R.fail("表身号只能是字母或数字");
                }
            }
            if (StringUtils.isNotBlank(gasMeter.getGasMeterNumber()) && !ValidationUtil.isLetterDigit(gasMeter.getGasMeterNumber())) {
                return R.fail("表身号只能是字母或数字");
            }
            if(StringUtils.isNotBlank(gasMeter.getGasMeterNumber())){

                R<Boolean> existsBodyNoR = gasMeterBizApi.isExistsBodyNumber(gasMeter.getGasMeterFactoryId(), gasMeter.getGasMeterNumber());
                if(existsBodyNoR.getIsError()){
                    throw new BizException("判断是否存在表身号异常");
                }
                if(existsBodyNoR.getData()){
                    return R.fail("表身号重复");
                }
            }
            R<List<GasMeter>> gasMeterListR = gasMeterArchiveService.addGasMeterList(gasMeterList);
            if(gasMeterListR.getIsError() || ObjectUtil.isNull(gasMeterListR.getData())
                    || gasMeterListR.getData().size() == 0){
                throw new BizException("新增表具信息异常");
            }
            gasMeter = gasMeterListR.getData().get(0);
            establishAccount.setGasMeter(gasMeter);
        }
        //新增客户信息
        if(StringUtils.isBlank(customer.getCustomerCode())){
            if(ObjectUtil.isNull(customerListR.getData()) || customerListR.getData().size() == 0) {
                customer.setCustomerStatus(CustomerStatusEnum.PREDOC.getCode());
                ValidationHandler validationHandler = handlerMap.get("SpecialSign");
                if(!validationHandler.validate(customer.getCustomerName())){
                    return R.fail("不能输入特殊字符");
                }
                if(StringUtils.isBlank(customer.getCustomerName())){
                    return R.fail("用户名称不能为空");
                }
                if(!PhoneValidation.isMobileNumber(customer.getTelphone())){
                    return R.fail("输入正确的电话号码");
                }
                if(IdCardValidation.check(customer.getIdCard())){
                    return R.fail("输入正确的身份证号码");
                }
                customer = saveCustomer(establishAccount, customer);
            }
            if(ObjectUtil.isNotNull(customerListR.getData()) && customerListR.getData().size() == 1) {
                customerTempBizApi.deleteReal(new CustomerTemp().setCustomerCode(customer.getCustomerCode()));
                CustomerTempSaveDTO customerTempSaveDTO = BeanPlusUtil.copyProperties(customer, CustomerTempSaveDTO.class);
                customerTempBizApi.save(customerTempSaveDTO);
            }
        }
        else{
            customerTempBizApi.deleteReal(new CustomerTemp().setCustomerCode(customer.getCustomerCode()));
            CustomerTempSaveDTO customerTempSaveDTO = BeanPlusUtil.copyProperties(customer, CustomerTempSaveDTO.class);
            customerTempBizApi.save(customerTempSaveDTO);
        }
        //开户
        R<GasMeter> gasMeterR = businessTemporaryBizApi.establishAccount(gasMeter, customer.getCustomerCode(), chargeNo, isAddGasMeter);
        if(gasMeterR.getIsError() || ObjectUtil.isNull(gasMeterR.getData())){
            throw new BizException("开户异常");
        }
        //无开户费直接回调
        R<Boolean> whetherSceneChargeR = whetherSceneCharge();
        if(!whetherSceneChargeR.getData()) {
            openAccountService.noChargeCallback(gasMeterR.getData().getBizNoOrId(), priceScheme.getId());
        }
        establishAccount.setGasMeter(gasMeterR.getData());
        establishAccount.setChargeNo(chargeNo);
        return R.success(establishAccount);
    }



    /**
     * 校验物联网表表身号
     * @param gasMeter
     */
    private void checkIotGasMeter(GasMeter gasMeter) {
        R<GasMeterVersion> gasMeterVersionR = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId());
        if(gasMeterVersionR.getIsError()){
            throw new BizException("查询版号信息异常");
        }
        if(ObjectUtil.isNull(gasMeterVersionR.getData())){
            throw new BizException("没有版号信息");
        }
        if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName().trim())||
                OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName())||
                OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersionR.getData().getOrderSourceName())){
            if(StringUtils.isBlank(gasMeter.getGasMeterNumber())){
                throw new BizException("物联网表表身号不能为空");
            }
        }
    }

    private R<Boolean> whetherSceneCharge() {
        R<Boolean> whetherSceneChargeR = tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.OPEN_ACCOUNT.getCode());
        if (whetherSceneChargeR.getIsError()) {
            throw new BizException("判断场景是否收费异常");
        }
        return whetherSceneChargeR;
    }


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

    private Customer saveCustomer(EstablishAccount establishAccount, Customer customer) {
        CustomerSaveDTO customerSaveDTO = BeanUtil.toBean(customer, CustomerSaveDTO.class);
        List<CustomerSaveDTO> customerSaveDTOs = new ArrayList<>();
        customerSaveDTOs.add(customerSaveDTO);
        R<Customer> customerR = userArchiveService.saveList(customerSaveDTOs);
        customer = customerR.getData();
        establishAccount.setCustomer(customer);
        return customer;
    }

    /**
     * 预开户
     * @param gasMeter
     * @param rechargeAmount
     * @return
     */
    @ApiOperation(value = "预开户")
    @PostMapping(value = "/establishAccountBeforehand")
    public R<Boolean> establishAccountBeforehand(@RequestBody GasMeter gasMeter,
                                                 @RequestParam(value = "rechargeAmount") String rechargeAmount){
        return businessTemporaryBizApi.establishAccountBeforehand(gasMeter, rechargeAmount);
    }

    /**
     * 批量预开户
     * @param gasMeterList
     * @param rechargeAmount
     * @return
     */
    @ApiOperation(value = "批量预开户")
    @PostMapping(value = "/establishAccountBeforehandBatch")
    public R<Boolean> establishAccountBeforehandBatch(@RequestBody List<GasMeter> gasMeterList,
                                                      @RequestParam(value = "rechargeAmount") String rechargeAmount){
        return businessTemporaryBizApi.establishAccountBeforehandBatch(gasMeterList, rechargeAmount);
    }

    /**
     * 加载最后的导入开户任务信息
     * @return 查询结果
     */
    @ApiOperation(value = "加载最后的导入开户任务信息", notes = "加载最后的导入开户任务信息")
    @PostMapping("/queryImportTaskInfo")
    @SysLog("加载最后的导入开户任务信息")
    public R<AccountOpenTaskInfo> queryImportTaskInfo(){
        return accountOpenTaskInfoBizApi.queryImportTaskInfo();
    }

    /**
     *导入Excel,返回json
     * @param simpleFile 上传文件
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入开户", notes = "导入开户")
    @PostMapping(value = "/importBackJson")
    public R<JSONObject> importEstablishAccountTemplate(@RequestPart(value = "file") MultipartFile simpleFile, HttpServletResponse response,  HttpServletRequest request) throws Exception{
        //1、处理导入的excel
        Workbook workBook = new HSSFWorkbook(simpleFile.getInputStream());
        Sheet sheetAt = workBook.getSheetAt(0);
        List<Row> rowList = new ArrayList<>(sheetAt.getPhysicalNumberOfRows());
        Row fristRow = sheetAt.getRow(0);
        short lastCellNum = fristRow.getLastCellNum();
        for (int i = 1; i < sheetAt.getPhysicalNumberOfRows(); i++) {
            Row row = sheetAt.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }
        //2、获取excel校验结果
        Boolean excelCheck = getValidExcelResult((HSSFWorkbook) workBook);
        //excel一条数据去重判断
        Boolean customerMeterDuplicateCheck = customerMeterDuplicateRemovalValid.validWrongSetStyle((HSSFWorkbook) workBook, rowList, lastCellNum);
        if (excelCheck && customerMeterDuplicateCheck){
            log.info("成功处理");
            //3、开户
            R<JSONObject> jsonObjectR = businessTemporaryBizApi.importExcelBackJsonObject(simpleFile);
            if(jsonObjectR.getIsError() || ObjectUtil.isNull(jsonObjectR.getData())){
                throw new BizException("导入开户异常");
            }
//            R<Boolean> whetherSceneChargeR = whetherSceneCharge();
//            if(!whetherSceneChargeR.getData()) {
            JSONObject jsonObject = jsonObjectR.getData();
            List<Map<String, Object>> gasMeterList = (List<Map<String, Object>>) jsonObject.get("gasMeterList");
            gasMeterList.stream().forEach(map -> {
                String customerCode = map.get("customerCode").toString();
                GasMeter gasMeter = Convert.convert(GasMeter.class, map.get("gasMeter"));
                //查询版号
                R<GasMeterVersion> gasMeterVersionR = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId());
                if (gasMeterVersionR.getIsError() || ObjectUtil.isNull(gasMeterVersionR.getData())) {
                    throw new BizException("查询版号异常");
                }
                //物联网表判断
                if (OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName().trim()) ||
                        OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName()) ||
                        OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersionR.getData().getOrderSourceName())) {
                    //物联网表下发开户指令
                    iotOpenAccountComponent.iotOpenAccount(gasMeter);
                    //物联网表更新远传设备
                    iotOpenAccountComponent.updateIotDevice(gasMeter, customerCode);
                }
            });
//            }
            return jsonObjectR;
        }else {
            log.info("失败处理");
            //8、校验失败，生成错误文件并下载
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            workBook.write(bos);
            byte[] barray=bos.toByteArray();
            InputStream is=new ByteArrayInputStream(barray);
            MultipartFile multipartFile = new MockMultipartFile("文件名1.xls","文件名2.xls","文件名3.xls",is);
            R<AttachmentDTO> result = attachmentApi.upload(multipartFile,true,null,null, BizTypeEnum.TEMPORARY_DOCUMENTS.getCode());
            if(result.getIsError() || ObjectUtil.isNull(result.getData())){
                log.error("下载导入开户失败文件异常");
                throw new BizException(i18nUtil.getMessage(MessageConstants.DOWDLOAD_ESTABLISH_ACCOUNT_ERROR_FILE_FAILED));
            }
            String url=result.getData().getUrl();
            return new R(FAIL_CODE, url, i18nUtil.getMessage(MessageConstants.VALID_SUC_SET_WRONG_MSG));
        }
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
     * 获取excel校验结果
     * @param workBook
     * @return
     */
    private Boolean getValidExcelResult(HSSFWorkbook workBook) {
        ExcelImportValid excelImportValid = new ExcelImportValid(workBook,1);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.CUSTMOER_NAME_NOT_EMPTY),0);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.IDCARD_NO_CANNOT_EMPTY),1);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(TempCounterMessageConstants.TELEPHONE_CANNOT_BE_EMPTY),2);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(TempCounterMessageConstants.CUSTOMER_TYPE_CANNOT_BE_EMPTY),3);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(TempCounterMessageConstants.CUSTOMER_SEX_CANNOT_BE_EMPTY),4);
//        excelImportValid.addValidBaseRule(new GasCodeValidRule(gasMeterBizApi, "不存在该气表编号的表具信息"));
        excelImportValid.addValidBaseRule(new CustomerChargeNoVaildRule(customerGasMeterRelatedBizApi,i18nUtil.getMessage(TempCounterMessageConstants.CHARGE_NO_EXISTS)));
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.FACTORY_NAME_CANNOT_EMPTY),9);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.VERSION_NUM_CANNOT_EMPTY),10);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.MODEL_NUM_CANNOT_EMPTY),11);
//        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.METER_BODY_NUM_CANNOT_EMPTY),12);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(TempCounterMessageConstants.VENTILATION_DIRECTION_CANNOT_BE_EMPTY),13);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.USE_GAS_TYPE_NAME_CANNOT_EMPTY),14);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.PROVINCE_NAME_CANNOT_EMPTY),15);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.CITY_NAME_CANNOT_EMPTY),16);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.COUNTY_NAME_CANNOT_EMPTY),17);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.STREET_NAME_CANNOT_EMPTY), 18);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(MessageConstants.COMMUNITY_NAME_CANNOT_EMPTY),19);
//        excelImportValid.addValidBaseRule(null,null, "安装地址必填",20);
//        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(TempCounterMessageConstants.GAS_METER_BASE_CANNOT_BE_EMPTY),21);
        excelImportValid.addValidBaseRule(null,null, i18nUtil.getMessage(TempCounterMessageConstants.NUMBER_OF_PEOPLE_USED_CANNOT_BE_EMPTY),22);

        excelImportValid.setCellWrongStyle();
        return excelImportValid.validAllSuc();
    }

    /**
     * 导入开户模板下载
     * @param templateCode
     */
    @ApiOperation(value = "根据模板编码下载导入开户模板", notes = "导入开户模板下载")
    @GetMapping(value = "/exportTemplateFile", produces = "application/octet-stream")
    public R<Boolean> exportTemplateFile(@RequestParam(value = "templateCode") String templateCode){
        return businessTemporaryBizApi.exportTemplateFile(templateCode);
    }

    @ApiOperation(value = "下载导入开户(模板)")
    @PostMapping("/exportCombobox")
    public void exportCombobox(@RequestBody @Validated PageParams<EstablishAccountImportPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
        EstablishAccountSelector establishAccountSelector = new EstablishAccountSelector().invoke();
        List<String> provinceList = establishAccountSelector.getProvinceList();
        List<String> cityList = establishAccountSelector.getCityList();
        List<String> countyList = establishAccountSelector.getCountyList();
        List<String> streetList = establishAccountSelector.getStreetList();
        List<String> communityList = establishAccountSelector.getCommunityList();
        List<String> useGasTypeList = establishAccountSelector.getUseGasTypeList();

        //下拉框
        ExcelSelectortDTO provinceExcelSelector = new ExcelSelectortDTO(6,6,provinceList.toArray(new String[(provinceList.size())]));
        ExcelSelectortDTO cityExcelSelector = new ExcelSelectortDTO(7,7,cityList.toArray(new String[(cityList.size())]));

        ExcelSelectortDTO countyExcelSelector = new ExcelSelectortDTO(8,8,countyList.toArray(new String[(countyList.size())]));
        ExcelSelectortDTO streetExcelSelector = new ExcelSelectortDTO(9,9,streetList.toArray(new String[(streetList.size())]));
        ExcelSelectortDTO communityExcelSelector = new ExcelSelectortDTO(10,10,communityList.toArray(new String[(communityList.size())]));
        ExcelSelectortDTO useGasTypeExcelSelector = new ExcelSelectortDTO(14,14,useGasTypeList.toArray(new String[(useGasTypeList.size())]));
        //
        List<ExcelSelectortDTO> comboboxList = new ArrayList<>();
        comboboxList.add(provinceExcelSelector);
        comboboxList.add(cityExcelSelector);
        comboboxList.add(countyExcelSelector);
        comboboxList.add(streetExcelSelector);
        comboboxList.add(communityExcelSelector);
        comboboxList.add(useGasTypeExcelSelector);
        params.setComboboxList(comboboxList);
        params.setSize(0);
        // feign文件下载
        Response response = businessTemporaryBizApi.exportCombobox(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, DEFAULT_FILE_NAME);
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }

    @ApiOperation(value = "下载导入开户下拉框级联模板")
    @PostMapping("/exportCascadeTemplate")
    public void exportCascadeTemplate(@RequestBody @Validated PageParams<EstablishAccountImportPageDTO> params,
                               HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {


        regionDictDataComponent.setDict();
        //省
        TreeSet<String> provinceSet= (TreeSet<String>)regionDictDataComponent.getDict(RegionDictDataComponent.PROVINCE);
        ExcelCascadeSelectorDTO provinceCascadeSelector =new ExcelCascadeSelectorDTO("省份",'P',0, provinceSet, "area");
        //省、市
        Map<String, Object> provinceCityMap= (Map<String, Object>)regionDictDataComponent.getDict(RegionDictDataComponent.PROVINCE_CITY);
        ExcelCascadeSelectorDTO provinceCityCascadeSelector =new ExcelCascadeSelectorDTO("省市",'Q',1, provinceCityMap, "area");
        //市、区
        Map<String, Object> cityCountyMap= (Map<String, Object>)regionDictDataComponent.getDict(RegionDictDataComponent.CITY_COUNTY);
        ExcelCascadeSelectorDTO cityCountyCascadeSelector =new ExcelCascadeSelectorDTO("市区",'R',2, cityCountyMap, "area");
        //区、街道
        Map<String, Object> countyStreetMap= (Map<String, Object>)regionDictDataComponent.getDict(RegionDictDataComponent.COUNTY_STREET);
        ExcelCascadeSelectorDTO countyStreetCascadeSelector =new ExcelCascadeSelectorDTO("街道",'S',3, countyStreetMap, "area");
        //街道、小区
        Map<String, Object> streetCommunityMap= (Map<String, Object>)regionDictDataComponent.getDict(RegionDictDataComponent.STREET_COMMUNITY);
        ExcelCascadeSelectorDTO streetCommunityCascadeSelector =new ExcelCascadeSelectorDTO("小区",'T',4, streetCommunityMap, "area");

        EstablishAccountSelector establishAccountSelector = new EstablishAccountSelector().invoke();
        //用气类型
        List<String> useGasTypeList = establishAccountSelector.getUseGasTypeList();
        TreeSet<String> useGasTypeSet = useGasTypeList.stream().collect(Collectors.toCollection(TreeSet::new));
        ExcelCascadeSelectorDTO useGasTypeCascadeSelector =new ExcelCascadeSelectorDTO("用气类型",'O',0, useGasTypeSet, "useGasType");

        factoryDictDataComponent.setDict();
        //厂
        TreeSet<String> gasMeterFactorySet= (TreeSet<String>)factoryDictDataComponent.getDict("factory");
        ExcelCascadeSelectorDTO factoryCascadeSelector =new ExcelCascadeSelectorDTO("厂家",'J',0, gasMeterFactorySet, "factory");
        //版号
        Map<String, Object> factoryVersionMap= (Map<String, Object>)factoryDictDataComponent.getDict("version");
        ExcelCascadeSelectorDTO versionCascadeSelector =new ExcelCascadeSelectorDTO("版号",'K',1, factoryVersionMap, "factory");
        //型号
        Map<String, Object> versionModelMap= (Map<String, Object>)factoryDictDataComponent.getDict("model");
        ExcelCascadeSelectorDTO modelCascadeSelector =new ExcelCascadeSelectorDTO("型号",'L',2, versionModelMap, "factory");

        //查询客户类型
        TreeSet<String> customerTypeSet = (TreeSet<String>)regionDictDataComponent.getDict(RegionDictDataComponent.CUSTOMER_TYPE);
        ExcelCascadeSelectorDTO customerTypeCascadeSelector =new ExcelCascadeSelectorDTO("客户类型",'D',0, customerTypeSet, "customerType");
        //查询客户性别
        TreeSet<String> sexSet = (TreeSet<String>)regionDictDataComponent.getDict(RegionDictDataComponent.SEX);
        ExcelCascadeSelectorDTO sexCascadeSelector =new ExcelCascadeSelectorDTO("客户性别",'E',0, sexSet, "sex");
        //通气方向
        TreeSet<String> ventilationDirectionSet = (TreeSet<String>)regionDictDataComponent.getDict(RegionDictDataComponent.VENTILATION_DIRECTION);
        ExcelCascadeSelectorDTO ventilationDirectionCascadeSelector =new ExcelCascadeSelectorDTO("通气方向",'N',0, ventilationDirectionSet, "ventilationDirection");

        //组装一组级联参数 省、市、区、街道、小区
        List<ExcelCascadeSelectorDTO> cascadeSelectors= new ArrayList<>();
        cascadeSelectors.add(provinceCascadeSelector);
        cascadeSelectors.add(provinceCityCascadeSelector);
        cascadeSelectors.add(cityCountyCascadeSelector);
        cascadeSelectors.add(countyStreetCascadeSelector);
        cascadeSelectors.add(streetCommunityCascadeSelector);
        cascadeSelectors.add(useGasTypeCascadeSelector);
        cascadeSelectors.add(factoryCascadeSelector);
        cascadeSelectors.add(versionCascadeSelector);
        cascadeSelectors.add(modelCascadeSelector);
        cascadeSelectors.add(customerTypeCascadeSelector);
        cascadeSelectors.add(sexCascadeSelector);
        cascadeSelectors.add(ventilationDirectionCascadeSelector);
        //级联参数
        params.setCascadeSelectors(cascadeSelectors);
        params.setTargetSheetIndex(0);

        // feign文件下载
        Response response = businessTemporaryBizApi.exportCascadeTemplate(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, DEFAULT_FILE_NAME);
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }


    /**
     * 过户验证:校验是否存在未完成的过户业务单
     * @author hc
     * @param gasMeterCode
     * @date 2020/11/07
     * @return
     */
    @ApiOperation(value = "过户验证:如存在业务单则返回当前业务单，否则只返回表具编号")
    @GetMapping("/transferAccount/check")
    public R<TransferAccountCheckDTO> transferAccountCheck(@RequestParam("gasMeterCode") @NotEmpty String gasMeterCode,
                                                           @RequestParam("customerCode") @NotEmpty String customerCode){
        R<HashMap<String, Object>> hashMapR = businessTemporaryBizApi.transferAccountCheck(gasMeterCode, customerCode);
        if(hashMapR.getIsError()){
            return R.fail("过户验证服务调用失败");
        }
        HashMap<String, Object> Re = hashMapR.getData();
        TransferAccountCheckDTO data = new TransferAccountCheckDTO();
        data.setStep((Integer) Re.get("step"));
        data.setFlag((Boolean)Re.get("flag"));
        data.setBusData(Re.get("busData"));

        Integer a = GmisSysSettingUtil.getTransferAccountFlag();
        Integer b = GmisSysSettingUtil.getOpenCustomerPrefix();
        if ( a.equals(1)&& b.equals(0)){
            data.setOpenChargeFlag(true);
        }else {
            data.setOpenChargeFlag(false);
        }
        return R.success(data);
    }

    /**
     * 过户:生成业务单
     * @author hc
     * @date 2020/11/05
     * @return
     */
    @ApiOperation(value = "过户")
    @PostMapping(value = "/transferAccount")
    public R<GasmeterTransferAccount> transferAccount(@RequestBody TransferAccountDTO transferAccountDTO){

        return businessTemporaryBizApi.transferAccount(transferAccountDTO);
    }

    /**
     * 过户：缴费业务回调接口
     * @author hc
     * @date 2020/11/06
     * @param businessNo 业务单号及过户记录表id
     * @return
     */
    @ApiOperation("过户：缴费业务回调接口")
    @PostMapping("/transferAccount/charge")
    public R<GasmeterTransferAccount> transferAccountCallBack(@RequestParam("businessNo") Long businessNo){

        return businessTemporaryBizApi.transferAccountCallBack(businessNo);
    }


    /**
     * 用气类型变更
     * @auther hc
     * @date 2020/11/16
     * @param useGasTypeChangeDTO
     * @return
     */
    @ApiOperation("用气类型变更：保存")
    @PostMapping("/useGasTypeChange")
    @GlobalTransactional
    public R<Boolean> useGasTypeChange(@RequestBody UseGasTypeChangeDTO useGasTypeChangeDTO){

        /**登录操作人**/
        Long userId = BaseContextHandler.getUserId();
        if(null==userId){
            userId = 3L;
        }
        R<User> userR = userBizApi.get(userId);
        if(userR.getIsError() || null == userR.getData()){
            return R.fail("账户信息获取失败");
        }
        User user = userR.getData();

        //获取表具及客户信息

        R<GasMeterExVo> gasMeterInfoExR = gasMeterBizApi.findGasMeterInfoByCodeEx(useGasTypeChangeDTO.getGasMeterCode());

        if(gasMeterInfoExR.getIsError() || null == gasMeterInfoExR.getData()){
            return R.fail("表具信息获取失败");
        }

        GasMeterExVo gasMeterInfo = gasMeterInfoExR.getData();
        //查询气表使用情况
        GasMeterInfo gasMeterStatus = gasMeterInfoBizApi.getByGasMeterCode(useGasTypeChangeDTO.getGasMeterCode()).getData();
        if(null == gasMeterStatus){
           throw new BizException("未找到到气表使用情况数据");
        }
        //获取气价方案
        //旧
        List<PriceScheme> priceSchemeOldList = priceSchemeBizApi.queryRecord(gasMeterInfo.getUseGasTypeId());

        //旧用气类型
        UseGasType useGasTypeOld = useGasTypeBizApi.get(gasMeterInfo.getUseGasTypeId()).getData();

        if(null == useGasTypeOld||priceSchemeOldList.size()==0){
            throw new BizException("旧用气类型没有当前生效的价格方案");
        }
        PriceScheme priceSchemeOld = price(useGasTypeOld,priceSchemeOldList);
        if(null == priceSchemeOld){
            throw new BizException("旧用气类型方案不存在");
        }
        //新
        List<PriceScheme> priceSchemeNewList = priceSchemeBizApi.queryRecord(useGasTypeChangeDTO.getUseGasTypeId());
        //新用气类型
        UseGasType useGasTypeNew = useGasTypeBizApi.get(useGasTypeChangeDTO.getUseGasTypeId()).getData();
        if(null == priceSchemeNewList|| priceSchemeNewList.size()==0){
            return R.fail("新用气类型下没有当前生效的价格方案");
        }
        PriceScheme priceSchemeNew = price(useGasTypeNew,priceSchemeNewList);
        if(null == priceSchemeNew){
            throw new BizException("新用气类型方案不存在");
        }
        //存储用气类型变更记录
        GasTypeChangeRecordSaveDTO changeRecord = new GasTypeChangeRecordSaveDTO();

        changeRecord.setCompanyCode(gasMeterInfo.getCompanyCode());
        changeRecord.setCompanyName(gasMeterInfo.getCompanyName());

        changeRecord.setOrgId(gasMeterInfo.getOrgId());
        changeRecord.setOrgName(gasMeterInfo.getOrgName());

        changeRecord.setBusinessBallId(user.getBusinessBallId());
        changeRecord.setBusinessBallName(user.getBusinessBallName());

        changeRecord.setCustomerCode(gasMeterInfo.getCustomerCode());
        changeRecord.setCustomerName(gasMeterInfo.getCustomerName());

        changeRecord.setGasMeterCode(gasMeterInfo.getGasCode());

        changeRecord.setChangeTime(LocalDateTime.now());

        changeRecord.setOldGasTypeId(gasMeterInfo.getUseGasTypeId());
        changeRecord.setOldGasTypeName(gasMeterInfo.getUseGasTypeName());
        changeRecord.setNewGasTypeId(useGasTypeChangeDTO.getUseGasTypeId());
        changeRecord.setNewGasTypeName(useGasTypeChangeDTO.getUseGasTypeName());

        changeRecord.setStartTimeOld(priceSchemeOld.getStartTime());
        changeRecord.setEndTimeOld(priceSchemeOld.getEndTime());

        changeRecord.setStartTimeNew(priceSchemeNew.getStartTime());
        changeRecord.setEndTimeNew(priceSchemeNew.getEndTime());

        changeRecord.setOldPriceNum(priceSchemeOld.getPriceNum());
        changeRecord.setNewPriceNum(priceSchemeNew.getPriceNum());

        changeRecord.setOldPriceId(priceSchemeOld.getId());
        changeRecord.setNewPriceId(priceSchemeNew.getId());

        changeRecord.setCycleSumControl(useGasTypeChangeDTO.getReset());

        R<GasTypeChangeRecord> recordR = gasTypeChangeRecordBizApi.save(changeRecord);
        GasMeterBookRecordUpdateDTO recordUpdateDTO = new GasMeterBookRecordUpdateDTO();
        recordUpdateDTO.setUseGasTypeId(useGasTypeChangeDTO.getUseGasTypeId())
                .setUseGasTypeName(useGasTypeChangeDTO.getUseGasTypeName())
                .setGasMeterCode(useGasTypeChangeDTO.getGasMeterCode());
        Boolean bool = recordApi.updateByGasType(recordUpdateDTO).getData();
        ReadMeterDataUpdateDTO data = new ReadMeterDataUpdateDTO();
        data.setUseGasTypeId(useGasTypeChangeDTO.getUseGasTypeId())
                .setUseGasTypeName(useGasTypeChangeDTO.getUseGasTypeName())
                .setGasMeterCode(useGasTypeChangeDTO.getGasMeterCode());
        dataApi.updateDataByGasType(data).getData();

//        //判断是否调价清零(新类型必须是阶梯价格)
//        if((PriceType.LADDER_PRICE.getCode().equals(useGasTypeNew.getPriceType())
//                ||PriceType.HEATING_PRICE.getCode().equals(useGasTypeNew.getPriceType()))
//                &&priceSchemeNew.getPriceChangeIsClear()==1){
//            MeterInfoVO meterInfoVO = new MeterInfoVO();
//            meterInfoVO.setPriceSchemeOldId(priceSchemeOld.getId());
//            meterInfoVO.setPriceSchemeNewId(priceSchemeNew.getId());
//            gasMeterInfoBizApi.updateByPriceId(meterInfoVO);
//        }
//        //是否周期清零(新类型必须是阶梯价格)
//        if((PriceType.LADDER_PRICE.getCode().equals(useGasTypeNew.getPriceType())
//                ||PriceType.HEATING_PRICE.getCode().equals(useGasTypeNew.getPriceType()))
//                &&isClear(priceSchemeNew.getCycleEnableDate(),LocalDate.now(),priceSchemeNew.getCycle())){
//            gasMeterStatus.setCycleUseGas(BigDecimal.ZERO);
//            GasMeterInfoUpdateDTO updateDTO = BeanPlusUtil.toBean(gasMeterStatus,GasMeterInfoUpdateDTO.class);
//            gasMeterInfoBizApi.update(updateDTO);
//        }


        //修改表具相关信息 物联网标段结算需要发送指令 其余类型改数据库就行
        if(recordR.getIsSuccess()){
            GasMeter gasMeter = new GasMeter();
            gasMeter.setGasCode(useGasTypeChangeDTO.getGasMeterCode());
            gasMeter.setUseGasTypeId(useGasTypeChangeDTO.getUseGasTypeId());
            gasMeter.setUseGasTypeName(useGasTypeChangeDTO.getUseGasTypeName());
            gasMeterBizApi.updateByCode(gasMeter);

            //物联网表端计费需要下方指令
            // TODO
            if(OrderSourceNameEnum.REMOTE_RECHARGE.eq(gasMeterInfo.getOrderSourceName())){
                //下发指令
                PriceVo priceVo = new PriceVo();
                priceVo.setCurGasTypeId(priceSchemeOld.getUseGasTypeId());
                priceVo.setGasMeterCode(gasMeterInfo.getGasCode());
                priceVo.setGasMeterNumber(gasMeterInfo.getGasMeterNumber());
                priceVo.setNewGasTypeId(priceSchemeNew.getUseGasTypeId());
                IotR iotR = businessService.changePrice(priceVo);
                if(iotR.getIsError()){
                    log.info("jsdhsahdj");
                }
            }
        }

        return R.success(true);
    }

    /**
     * 获取用气类型变更记录
     * @author hc
     * @date 2020/11/17
     * @return
     */
    @ApiOperation("气类型变更：记录")
    @GetMapping("/useGasTypeChange")
    public R<List<UseGasTypeChangeVO>> findChangeRecord(@RequestParam("gasCode") @NotEmpty String gasCode){

        ArrayList<UseGasTypeChangeVO> list = new ArrayList<>();

        GasTypeChangeRecord record = new GasTypeChangeRecord();
        record.setGasMeterCode(gasCode);
        R<List<HashMap<String, Object>>> listR = gasTypeChangeRecordBizApi.queryEx(record);
        List<HashMap<String, Object>> listRData = listR.getData();
        listRData.stream().forEach(item->{
            UseGasTypeChangeVO changeVO = JSON.parseObject(JSON.toJSONString(item), UseGasTypeChangeVO.class);
            list.add(changeVO);
        });
        return R.success(list);
    }


    /**
     * 查询地区信息
     * @return
     */
    private List<String> getAreaList(RemoteData level) {
        new Area();
        Area areaParam = Area
                .builder()
                .level(level)
                .build();

        R<List<Area>> areaListR = areaBizApi.query(areaParam);
        if (areaListR.getIsError()) {
            log.error("查询地区信息");
            throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_REGION_THROW_EXCEPTION));
        }
        List<String> list = areaListR.getData().stream().map(Area::getLabel).collect(Collectors.toList());
        return list;
    }

    private class EstablishAccountSelector {
        private List<String> provinceList;
        private List<String> cityList;
        private List<String> countyList;
        private List<String> streetList;
        private List<String> communityList;
        private List<String> useGasTypeList;

        public List<String> getProvinceList() {
            return provinceList;
        }

        public List<String> getCityList() {
            return cityList;
        }

        public List<String> getCountyList() {
            return countyList;
        }

        public List<String> getStreetList() {
            return streetList;
        }

        public List<String> getCommunityList() {
            return communityList;
        }

        public List<String> getUseGasTypeList() {
            return useGasTypeList;
        }

        public EstablishAccountSelector invoke() {
            //省、市、区下拉框
            provinceList = getAreaList(new RemoteData<>(PROVINCE));
            cityList = getAreaList(new RemoteData<>(CITY));
            countyList = getAreaList(new RemoteData<>(COUNTY));
            //街道
            Street street = new Street();
            R<List<Street>> streetsR = streetBizApi.query(street);
            if (streetsR.getIsError() || ObjectUtil.isNull(streetsR.getData())) {
                log.error("查询街道异常");
                throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_STREET_THROW_EXCEPTION));
            }
            streetList = streetsR.getData().stream().map(Street::getStreetName).collect(Collectors.toList());
            //小区
            Community community = new Community();
            R<List<Community>> communitiesR = communityBizApi.query(community);
            if (communitiesR.getIsError() || ObjectUtil.isNull(communitiesR.getData())) {
                log.error("查询小区异常");
                throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_COMMUNITY_THROW_EXCEPTION));
            }
            communityList = communitiesR.getData().stream().map(Community::getCommunityName).collect(Collectors.toList());
            //用气类型
            new UseGasType();
            UseGasType useGasType = UseGasType
                    .builder()
                    .build();
            R<List<UseGasType>> useGasTypesR = useGasTypeBizApi.query(useGasType);
            if (useGasTypesR.getIsError() || ObjectUtil.isNull(useGasTypesR.getData())) {
                log.error("查询用气类型异常");
                throw new BizException(i18nUtil.getMessage(MessageConstants.QUERY_USE_GAS_TYPE_THROW_EXCEPTION));
            }
            useGasTypeList = useGasTypesR.getData().stream().map(UseGasType::getUseGasTypeName).collect(Collectors.toList());
            return this;
        }
    }

    /**
     * 周期累积量是否清零
     * @param begin 周期开始时间
     * @param end 冻结时间
     * @param cycle 计费周期-单位月
     * @return
     */
    private Boolean isClear(LocalDate begin, LocalDate end, int cycle){
        int month = (end.getYear() - begin.getYear())*12 + (end.getMonthValue()-begin.getMonthValue());
        return (month%cycle==0)&&(begin.getDayOfMonth()==end.getDayOfMonth());
    }

    /**
     * 判断并获取价格方案
     * @param useGasType
     * @param priceSchemeList
     * @return
     */
    private PriceScheme price(UseGasType useGasType,List<PriceScheme> priceSchemeList){
        //是否采暖期，采暖期优先级>非采暖
        if((PriceType.HEATING_PRICE.getCode().equals(useGasType.getPriceType()))){
            PriceScheme priceHeating = null;
            PriceScheme price = null;
            for(PriceScheme priceScheme:priceSchemeList){
                if(priceScheme.getIsHeating()==1){
                    priceHeating = priceScheme;
                }else{
                    price = priceScheme;
                }
            }
            if(priceHeating==null){return null;}
            LocalDate heatingDate = priceHeating.getCycleStartTime();
            LocalDate reviewDate = LocalDate.now();
            //判断抄表数据是否处于采暖季
            if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)){
                return priceHeating;
            }else{
                return price;
            }
        }else{
            return  priceSchemeList.get(0);
        }
    }
}
