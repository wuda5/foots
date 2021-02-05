package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.archive.InputOutputMeterStoryBizApi;
import com.cdqckj.gmis.archive.MeterStockBizApi;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterArchiveService;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.domain.excell.*;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterPageDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterVo;
import com.cdqckj.gmis.devicearchive.dto.MeterStockPageDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.entity.MeterStock;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.devicearchive.vo.GasMeterAndInfoVo;
import com.cdqckj.gmis.devicearchive.vo.GasmeterPoi;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.userarchive.entity.Customer;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("/archive/gasmeterarchive")
@Api(value = "devicearchive", tags = "气表信息档案")
public class GasMeterArchiveControllor {
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private MeterStockBizApi meterStockBizApi;
    @Autowired
    private InputOutputMeterStoryBizApi inputOutputMeterStoryBizApi;
    @Autowired
    private GasMeterArchiveService gasMeterArchiveService;
    @Autowired
    private GmisUploadFile gmisUploadFile;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    GasMeterFactoryBizApi gasMeterFactoryBizApi;

    @Autowired
    GasMeterVersionBizApi gasMeterVersionBizApi;

    @Autowired
    AttachmentApi attachmentApi;
    @Autowired
    RedisService redisService;

    @Autowired
    CommonConfigurationApi commonConfigurationApi;

    /**
     * @param
     * @return
     */
/*    @PostMapping(value = "/gasMeter/addOne")
    @ApiOperation(value = "新增单个气表信息")
    R<Boolean> saveGasMeter(@RequestBody GasMeter meter){

       return  gasMeterArchiveService.addGasMeterList(Lists.newArrayList(meter));
    }*/

    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    @PostMapping(value = "/gasMeter/add")
    @ApiOperation(value = "新增气表信息")
    @CodeNotLost
    @GlobalTransactional
    R<List<GasMeter>>  saveGasMeterList(@RequestBody @Valid List<GasMeter> gasMeterList){
        GasMeter gasMeter = gasMeterList.get(0);
        GasMeterVersion gasMeterVersion =  gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
        if(!gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.IC_RECHARGE.getCode())
                && !gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.READMETER_PAY.getCode())) {
            if(StringUtils.isEmpty(gasMeter.getGasMeterNumber())){
                return R.fail("物联网表必须输入表身号");
            }else if (isLetterDigit(gasMeter.getGasMeterNumber()) == false) {
                return R.fail("表身号只能是字母或数字");
            }
        }
        if (!StringUtils.isEmpty(gasMeter.getGasMeterNumber())&&isLetterDigit(gasMeter.getGasMeterNumber()) == false) {
            return R.fail("表身号只能是字母或数字");
        }
        return gasMeterArchiveService.addGasMeterList(gasMeterList);
    }
    @GetMapping("/findgsaMeterByCode")
    R<PageGasMeter> findGasMeterByCode(@RequestParam(value = "gasMeterCode")  String gasMeterCode){
        PageGasMeter data = gasMeterBizApi.findgsaMeterBygasCode(gasMeterCode).getData();
        return R.success(data);
    }

    /**
     * @param updateDTO
     * @return
     */
    @PutMapping(value = "/gasMeter/enable")
    @ApiOperation(value = "启用/禁用")
    R<GasMeter> enableGasMeter(@RequestBody GasMeterUpdateDTO updateDTO){

        if(updateDTO.getDataStatus().equals(GasMeterStatusEnum.UnVentilation.getCode())||
                updateDTO.getDataStatus().equals(GasMeterStatusEnum.Ventilated.getCode())||
                updateDTO.getDataStatus().equals(GasMeterStatusEnum.Dismantle.getCode())
                ){
            return R.fail("只有在未开的情况下才能禁用！！！！！！！！！！！");
        }
        R<GasMeter> result = gasMeterBizApi.update(updateDTO);
        return result;
    }

    /**
     * @param updateDTO
     * @return
     */
    @PutMapping(value = "/gasMeter/update")
    @ApiOperation(value = "更新气表信息")
    R<GasMeter> updateGasMeter(@RequestBody GasMeterUpdateDTO updateDTO){
        if(StringUtils.isEmpty(updateDTO.getGasMeterNumber())&&isLetterDigit(updateDTO.getGasMeterNumber()) == false){
            return R.fail("表身号只能是字母或数字");
        }
        if(StringUtils.isEmpty(updateDTO.getGasMeterNumber())){
            GasMeterFactory factory = gasMeterFactoryBizApi.get(updateDTO.getGasMeterFactoryId()).getData();
            updateDTO.setGasMeterNumber(BizCodeNewUtil.genGasMeterNumber(updateDTO.getGasMeterFactoryId(), factory.getGasMeterFactoryCode(), updateDTO.getGasMeterNumber()));
            updateDTO.setAutoGenerated(1);
        }
        R<GasMeter> result = gasMeterArchiveService.update(updateDTO);
        return result;
    }
    @PostMapping(value = "/gasMeter/customer")
    @ApiOperation(value = "根据气表编号查询客户详情信息")
    R<Customer> findCustomer(@RequestBody CustomerGasMeterRelated customerGasMeterRelated){
        return gasMeterArchiveService.findCustomer(customerGasMeterRelated);
    }
    /**
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/gasMeter/delete")
    @ApiOperation(value = "删除气表信息")
    R<Boolean> deleteGasMeter(@RequestParam("ids[]") List<Long> ids){
        return gasMeterBizApi.delete(ids);
    }

    /**
     * @param params
     * @return
     */
  /*  @PostMapping(value = "/gasMeter/page")
    @ApiOperation(value = "分页查询气表信息")
    R<Page<GasMeter>> pageGasMeter(@RequestBody PageParams<GasMeterPageDTO> params){
        return gasMeterBizApi.page(params);
    }
*/
    @ApiOperation(value = "查询气表信息分页列表")
    @PostMapping ("/gasMeter/findGasMeterPage")
    public R<Page<PageGasMeter>> findGasMeterPage(@RequestBody GasMeterVo params){
        return  gasMeterBizApi.findGasMeterPage(params);
    }
    @ApiOperation(value = "获取调价补差气表分页信息")
    @PostMapping ("/gasMeter/findAdjustPriceGasMeterPage")
    public R<Page<PageGasMeter>> findAdjustPriceGasMeterPage(@RequestBody GasMeterVo params){
        return  gasMeterBizApi.findAdjustPriceGasMeterPage(params);
    }
    @ApiOperation(value = "逻辑删除气表信息")
    @DeleteMapping(value = "/gasMeter/logicalDelete")
    public R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids){
        return gasMeterBizApi.logicalDelete(ids);
    };

    @ApiOperation(value = "批量导入表具信息")
    @PostMapping(value = "/gasMeter/import")
    @CodeNotLost
    public R<Map<String,Object>> importExcel(@RequestPart(value = "file") MultipartFile file) throws Exception{
        Workbook workBook = new HSSFWorkbook(file.getInputStream());
        ExcelImportValid exCelImportValid = new ExcelImportValid((HSSFWorkbook) workBook, 1);
        VaildRuleFactoryId vaildRuleFactoryId = new VaildRuleFactoryId(gasMeterFactoryBizApi,redisService,"找不到该厂家","找不到该厂家");
        exCelImportValid.addValidBaseRule(vaildRuleFactoryId);
        VaildRuleVersionId versionId = new VaildRuleVersionId(gasMeterFactoryBizApi,gasMeterVersionBizApi,redisService,"找不到该厂家下面的版号","找不到该厂家下面的版号");
        exCelImportValid.addValidBaseRule(versionId);
        VaildRuleModeId modeId = new VaildRuleModeId(gasMeterVersionBizApi,gasMeterModelBizApi,redisService,"找不到版号下面的型号","不能为空");
        exCelImportValid.addValidBaseRule(modeId);
        VaildRuleGasId ruleId = new VaildRuleGasId(gasMeterBizApi,gasMeterFactoryBizApi,redisService,"表身号不能重复",null);
        exCelImportValid.addValidBaseRule(ruleId);
        exCelImportValid.colUniqueRule(4,"表身号唯一");
        exCelImportValid.addValidBaseRule(ValidTypeEnum.DATE,"输入正确的日期格式",null,6);
        exCelImportValid.addValidBaseRule(ValidTypeEnum.DATE,"输入正确的日期格式",null,7);
        List<GasmeterPoi> success = exCelImportValid.getCellDataToArray(DataTypeEnum.SUC, GasmeterPoi.class);
        List<GasmeterPoi> failData = exCelImportValid.getCellDataToArray(DataTypeEnum.FAIL, GasmeterPoi.class);
        List<GasMeterVersion> allVersion=gasMeterVersionBizApi.query(new GasMeterVersion()).getData();
        List<GasMeterFactory> allFactory=gasMeterFactoryBizApi.query(new GasMeterFactory()).getData();
        List<GasMeterModel> allModel=gasMeterModelBizApi.query(new GasMeterModel()).getData();
        Map<String,Long> factoryMap=new HashMap<>();
        Map<String,String> directionMap=new HashMap<>();
        List<DictionaryItem> directions = commonConfigurationApi.findCommonConfigbytype("VENTILATION_DIRECTION").getData();
        for (DictionaryItem dictionaryItem : directions) {
            directionMap.put(dictionaryItem.getName(),dictionaryItem.getCode());
        }
        Map map=new HashMap<>();
        for (GasMeterFactory temp : allFactory) {
            factoryMap.put(temp.getGasMeterFactoryName(),temp.getId());
        }
        List<GasMeter> gasMeters=new ArrayList<>();
        for (GasmeterPoi gasmeterPoi : success) {
            GasMeter  gasMeter=new GasMeter();
            gasMeter.setGasMeterFactoryId(factoryMap.get(gasmeterPoi.getGasMeterFactoryName()));
            long factoryId = gasMeter.getGasMeterFactoryId();
            String versionName = gasmeterPoi.getGasMeterVersionName();
            String modelName=gasmeterPoi.getGasMeterModelName();
            Map<String,Long> gasMeterVersionMap = allVersion.stream().filter(item ->
                    item.getCompanyId() == factoryId &&
                            item.getGasMeterVersionName().equals(versionName))
                    .collect(Collectors.toMap(GasMeterVersion::getGasMeterVersionName, GasMeterVersion::getId));
            gasMeter.setGasMeterVersionId(gasMeterVersionMap.get(gasmeterPoi.getGasMeterVersionName()));
            long gasversionId=gasMeter.getGasMeterVersionId();
            Map<String,Long> gasMeterModelMap = allModel.stream().filter(item ->
                    item.getGasMeterVersionId() == gasversionId &&
                            item.getModelName().equals(modelName))
                    .collect(Collectors.toMap(GasMeterModel::getModelName, GasMeterModel::getId));
            gasMeter.setGasMeterModelId(gasMeterModelMap.get(gasmeterPoi.getGasMeterModelName()));
            gasMeter.setDirection(directionMap.get(gasmeterPoi.getDirection()));
            gasMeter.setGasMeterNumber(gasmeterPoi.getGasMeterNumber());
            gasMeter.setCheckUser(gasmeterPoi.getCheckUser());
            if(!StringUtils.isEmpty(gasmeterPoi.getCheckTime())){
                gasMeter.setCheckTime(LocalDate.parse(gasmeterPoi.getCheckTime()));
            }

            if(!StringUtils.isEmpty(gasmeterPoi.getBuyTime())){
                gasMeter.setBuyTime(LocalDate.parse(gasmeterPoi.getBuyTime()));
            }
            gasMeter.setSafeCode(gasmeterPoi.getSafeCode());
            if(gasmeterPoi.getGasMeterBase()==null){
                gasMeter.setGasMeterBase(new BigDecimal(0));
            }else {
                gasMeter.setGasMeterBase(gasmeterPoi.getGasMeterBase());
            }
            gasMeter.setRemark(gasmeterPoi.getRemark());
            gasMeters.add(gasMeter);
        }
        String des;
        if (gasMeters.size() > 0) {
            gasMeterArchiveService.addGasMeterList(gasMeters);
        }
        map.put("success",  success.size());
        exCelImportValid.setCellWrongStyle();
        exCelImportValid.clearRow(DataTypeEnum.SUC);
        map.put("failData",  failData);
        map.put("fail",  failData.size());
        UploadFileInfo fileInfo = new UploadFileInfo();

        String errUrl = exCelImportValid.uploadFile(fileInfo, gmisUploadFile);
        log.info("失败数据");
        map.put("errUrl", errUrl);
        return R.success(map);
    };

    @ApiOperation(value = "扫码入库前的分页查询表具库存信息")
    @PostMapping(value = "/gasMeter/queryMeterStock")
    public  R<Page<MeterStock>>  queryMeterStock(@RequestBody PageParams<MeterStockPageDTO> params){
        return meterStockBizApi.page(params);
    };
    @ApiOperation(value = "扫码入库中删除接口")
    @DeleteMapping(value = "/gasMeter/delMeterStock")
    public   R<Boolean>  delMeterStock(@RequestParam("ids[]") List<Long> ids){
        return meterStockBizApi.delete(ids);
    };

    @ApiOperation(value = "扫码入库中批量入库")
    @PostMapping(value = "/gasMeter/addMeterStock")
    public  R<Boolean>  addMeterStock(@RequestBody List<GasMeter> list){
        R<List<GasMeter>> listR = gasMeterArchiveService.addGasMeterList(list);
        if(listR.getIsSuccess()){
            return R.success(true);
        }
        return R.fail("扫码新增表具档案失败");
    };
    @ApiOperation(value = "导出表具数据")
    @PostMapping("/gasMeter/exportCustomer")
    public void export(@RequestBody @Validated PageParams<GasMeterPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
        // feign文件下载
        gasMeterArchiveService.export(params,request,httpResponse);
    }
    @ApiOperation(value = "查询气表状态")
    @GetMapping("/gasMeter/getByGasMeterCode")
    public R<GasMeterAndInfoVo> getByGasMeterCode(@RequestParam(value = "gasMeterCode") String gasMeterCode,String customerCode){
        GasMeterInfo gasMeterInfo ;
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(customerCode)){
            gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeterCode,customerCode).getData();
        }else{
            gasMeterInfo = gasMeterInfoBizApi.getByGasMeterCode(gasMeterCode).getData();
        }
        PageGasMeter gasMeter = gasMeterBizApi.findgsaMeterBygasCode(gasMeterCode).getData();
        GasMeterAndInfoVo gasMeterAndInfoVo=new GasMeterAndInfoVo();
        gasMeterAndInfoVo.setPageGasMeter(gasMeter);
        gasMeterAndInfoVo.setGasMeterInfo(gasMeterInfo);
        return R.success(gasMeterAndInfoVo);
    }

    @ApiOperation(value = "通气")
    @PostMapping("/ventilation")
    public R<GasMeter> ventilation(@RequestBody GasMeter gasMeter){
        return gasMeterBizApi.ventilation(gasMeter);
    }

    @ApiOperation(value = "通过表身号查询有效的未开户表具信息")
    @GetMapping("/gasMeter/getEffectiveMeterByNumber")
    public R<GasMeter> findEffectiveMeterByNumber(@RequestParam(value = "gasMeterNumber") String gasMeterNumber){
        GasMeter data = gasMeterBizApi.findEffectiveMeterByNumber(gasMeterNumber).getData();
        if(Objects.isNull(data)){
            return R.success(null);
        }
        if(GasMeterStatusEnum.UnVentilation.getCode().equals(data.getDataStatus())
                || GasMeterStatusEnum.Ventilated.getCode().equals(data.getDataStatus())){
            return R.fail("表具已开户，请重新选择表具");
        }
        return R.success(data);
    }


    @PostMapping(value = "/addDeviceAgain")
    @ApiOperation(value = "拆除的物联网设备重新注册到网关", notes = "拆除的物联网设备重新注册到网关")
    public IotR addDeviceAgain(@RequestBody List<GasMeter> gasMeterList){
        return gasMeterArchiveService.addDeviceAgain(gasMeterList);
    }

}
