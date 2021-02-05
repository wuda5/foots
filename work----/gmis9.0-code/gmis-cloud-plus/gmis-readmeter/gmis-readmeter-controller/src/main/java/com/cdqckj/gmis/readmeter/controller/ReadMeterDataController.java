package com.cdqckj.gmis.readmeter.controller;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.DataStatus;
import com.cdqckj.gmis.base.utils.RecordYear;
import com.cdqckj.gmis.charges.vo.StsCounterStaffVo;
import com.cdqckj.gmis.common.domain.excell.ExcelImportValid;
import com.cdqckj.gmis.common.domain.excell.GmisUploadFile;
import com.cdqckj.gmis.common.domain.excell.UploadFileInfo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.file.service.AttachmentService;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;
import com.cdqckj.gmis.operateparam.service.ExceptionRuleConfService;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.dtoex.OneReadDataInputDTO;
import com.cdqckj.gmis.readmeter.entity.*;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.DataTypeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ReadMeterStatusEnum;
import com.cdqckj.gmis.readmeter.service.*;
import com.cdqckj.gmis.readmeter.vo.GasMeterReadStsVo;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataVo;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterData")
@Api(value = "ReadMeterData", tags = "抄表数据")
@PreAuth(replace = "readMeterData:")
public class ReadMeterDataController extends SuperController<ReadMeterDataService, Long,
        ReadMeterData, ReadMeterDataPageDTO, ReadMeterDataSaveDTO, ReadMeterDataUpdateDTO> {

    @Autowired
    public ReadMeterPlanService planService;
    @Autowired
    private GmisUploadFile gmisUploadFile;
    @Autowired
    public AttachmentService attachmentService;
    @Autowired
    public ReadMeterLatestRecordService latestRecordService;
    @Autowired
    public ReadMeterPlanScopeService scopeService;
    @Autowired
    public GasMeterBookRecordService recordService;
    @Autowired
    public ReadMeterBookService bookService;
    @Autowired
    public ReadMeterMonthGasService monthGasService;
    @Autowired
    public ExceptionRuleConfService ruleConfService;
    @Autowired
    private AttachmentApi attachmentApi;
    @Autowired
    private GasMeterService gasMeterService;
    @Autowired
    public GasMeterBizApi gasMeterBizApi;
    @Autowired
    public ReadMeterDataIotService dataIotService;
    @Autowired
    public ReadMeterDataErrorService errorService;


    @ApiOperation(value = "录入抄表数据信息")
    @PostMapping("/inputData")
//    @Transactional(rollbackFor = Exception.class)
    public R<ReadMeterData> inputData(@RequestBody ReadMeterDataUpdateDTO dto) {
        ReadMeterData oldData = Optional.ofNullable(baseService.getById(dto.getId()))
                .orElseThrow(() -> new BizException("没有此抄表数据信息：" + dto.getId()));

//        boolean update = baseService.update(Wraps.<ReadMeterData>lbU()
//                .set(ReadMeterData::getCurrentTotalGas, dto.getCurrentTotalGas())
//                .set(ReadMeterData::getMonthUseGas, dto.getMonthUseGas())
//                .set(ReadMeterData::getDataStatus, 0) // 代表抄表完成
//                .eq(ReadMeterData::getId, dto.getId()));

        oldData.setCurrentTotalGas(dto.getCurrentTotalGas())
                .setMonthUseGas(dto.getMonthUseGas())
                .setDataStatus(0);// 代表抄表完成
        boolean b = baseService.updateById(oldData);

        //TODO 同步需要修改其他 相关联的 数据信息......

        return success(oldData);
    }

    /**
     * Excel导入后的操作
     *
     * @param simpleFile
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/importReadMeterData")
    public R<Map<Integer, ReadMeterData>> importReadMeterData(@RequestParam(value = "file") MultipartFile simpleFile,
                                                              HttpServletRequest request) throws Exception {
        ImportParams params = new ImportParams();
        //接收原始数据
        //List<Map<String, Object>> list = ExcelImportUtil.importExcel(simpleFile.getInputStream(), Map.class, params);;//getDataList(simpleFile, request);
        List<Map<String, Object>> list = ImportUtil.readExcel(simpleFile.getInputStream());
        Map<Integer, ReadMeterData> map = getEntityMap(list);
        return R.success(map);
    }

    @PostMapping(value = "/importExcelByTime")
    R<Map<String,Object>> importExcelByTime(@RequestPart(value = "file") MultipartFile simpleFile, @RequestParam("readTime")  String readTime) throws Exception {
        //先找数据，没有就添加
        List<Map<String, Object>> list = ImportUtil.readExcel(simpleFile.getInputStream());
        Map<Integer, ReadMeterData> dataMap = getEntityMap(list);
        List<ReadMeterData> readMeterDataList = new ArrayList<>( dataMap.values());

        List<String> meterCodeList = readMeterDataList.stream().map(ReadMeterData::getGasMeterCode).collect(Collectors.toList());
        //过滤空行后的导入数据
        readMeterDataList = readMeterDataList.stream().filter(item -> null!=item).collect(Collectors.toList());
        LambdaQueryWrapper<GasMeterBookRecord> recordLambda = new LambdaQueryWrapper<>();
        recordLambda.in(GasMeterBookRecord::getGasMeterCode, meterCodeList);
        List<GasMeterBookRecord> recordList = recordService.list(recordLambda);
        BizAssert.isTrue(CollectionUtil.isNotEmpty(recordList),"无对应抄表册关联客户表具信息");

        List<Long> bookIdList = recordList.stream().map(GasMeterBookRecord::getReadMeterBook).distinct().collect(Collectors.toList());
        String[] time = readTime.split("-");
        Integer year = Integer.valueOf(time[0]);
        Integer month = Integer.valueOf(time[1]);
        getAndSaveReadMeterData(year, month, bookIdList);
        //校验数据
        LambdaQueryWrapper<ReadMeterLatestRecord> latestLambda = new LambdaQueryWrapper<>();
        latestLambda.in(ReadMeterLatestRecord::getGasMeterCode, meterCodeList);
        List<ReadMeterLatestRecord> latestList = latestRecordService.list(latestLambda);
        List<ReadMeterLatestRecord> updateList = new ArrayList<>();
        if(latestList.size()>0){
            Map<String, Object> resultMap = new HashMap<>(8);
            Map<String, ReadMeterData> existDataMap = new HashMap<>(16);
            Map<String, ReadMeterLatestRecord> LatestMap = latestList.stream().collect(Collectors.toMap(ReadMeterLatestRecord::getGasMeterCode, Function.identity()));
            for (Map.Entry<String, ReadMeterLatestRecord> entry : LatestMap.entrySet()) {
                ReadMeterData data = baseService.getOne(Wraps.<ReadMeterData>lbU().eq(ReadMeterData::getGasMeterCode,entry.getKey())
                        .eq(ReadMeterData::getDataStatus, ReadMeterStatusEnum.READ.getCode()).eq(ReadMeterData::getDataType,DataTypeEnum.ORDINARY_DATA.getCode())
                        .eq(ReadMeterData::getReadTime,entry.getValue().getReadTime()));
                if(null!=data){
                    existDataMap.put(data.getGasMeterCode(),data);
                }
            }
            InputStream inputStream = null;
            try {
                inputStream = simpleFile.getInputStream();
                Workbook workBook = new HSSFWorkbook(inputStream);
                //Sheet sheet = workBook.getSheetAt(0);
                //int lastRow = sheet.getLastRowNum(); excel 数据校验, true 代表 没有问题
                //暂时屏蔽，后期可以删除
                /*Boolean noErr = checkExcel(readTime, dataMap, LatestMap, workBook, sheet);
                if(!noErr){
                    // excel解析数据有问题，失败，返回生成失败详情的excel url
                    return getErrorUrl(workBook);
                }*/
                ExcelImportValid exCelImportValid = new ExcelImportValid((HSSFWorkbook) workBook, 1);
                exCelImportValid.addValidBaseRule(null,null,"气表编码不能为空",2);
                exCelImportValid.addValidBaseRule(null,null,"本期止数不能为空",6);
                exCelImportValid.addValidBaseRule(null,null,"抄表时间不能为空",7);
                /*  exCelImportValid.addValidBaseRule(null,null,"表身号不能不能为空",4);*/
                /*   exCelImportValid.addValidBaseRule(null,null,"型号名称不能为空",3);*/
                ReadDataTimeVaildRule timeRule = new ReadDataTimeVaildRule(readTime, existDataMap);
                ReadDataValueVaildRule valueRule = new ReadDataValueVaildRule(readTime, LatestMap);
                exCelImportValid.addValidBaseRule(timeRule);
                exCelImportValid.addValidBaseRule(valueRule);
                exCelImportValid.setCellWrongStyle();
                List<ReadMeterDataVo> failList = exCelImportValid.getCellDataToArray(com.cdqckj.gmis.common.domain.excell.DataTypeEnum.FAIL, ReadMeterDataVo.class);
                List<ReadMeterDataVo> succList = exCelImportValid.getCellDataToArray(com.cdqckj.gmis.common.domain.excell.DataTypeEnum.SUC, ReadMeterDataVo.class);
                exCelImportValid.clearRow(com.cdqckj.gmis.common.domain.excell.DataTypeEnum.SUC);
                String url = exCelImportValid.uploadFile(new UploadFileInfo(), gmisUploadFile);
                //String url = getErrorUrl(workBook).getData();
                //重写修改
                List<ReadMeterData> dataList = new ArrayList<>();
                if(succList.size()>0){
                    meterCodeList = succList.stream().map(ReadMeterDataVo::getGasMeterCode).collect(Collectors.toList());
                    LambdaQueryWrapper<ReadMeterData> dataLambda = new LambdaQueryWrapper<>();
                    dataLambda.in(ReadMeterData::getGasMeterCode, meterCodeList);
                    dataLambda.eq(ReadMeterData::getReadMeterYear, year);
                    dataLambda.eq(ReadMeterData::getReadMeterMonth, month);
                    dataList = baseService.list(dataLambda);
                    Map<String, ReadMeterData> fileMap = readMeterDataList.stream().collect(Collectors.toMap(ReadMeterData::getGasMeterCode, Function.identity()));
                    dataList.stream().forEach(item -> {
                        ReadMeterData fileData = fileMap.get(item.getGasMeterCode());
                        if (fileData != null) {
                            ReadMeterLatestRecord record = LatestMap.get(item.getGasMeterCode());
                            item.setLastReadTime(record.getCurrentReadTime());
                            item.setCurrentTotalGas(fileData.getCurrentTotalGas());
                            item.setRecordTime(fileData.getRecordTime());// 录入的抄表时间2020-01-02
                            item.setMonthUseGas(fileData.getCurrentTotalGas().subtract(record.getCurrentTotalGas()));
                            item.setDataStatus(ReadMeterStatusEnum.READ.getCode());

                            record.setYear(year);
                            record.setMonth(month);
                            record.setReadTime(DateUtils.getDate8(year, month));
                            record.setCurrentTotalGas(fileData.getCurrentTotalGas());
                            record.setCurrentReadTime(fileData.getRecordTime());
                            updateList.add(record);
                        }
                    });
                    baseService.updateBatchById(dataList);
                    latestRecordService.updateBatchById(updateList);
                }
                resultMap.put("success",dataList.size());
                resultMap.put("fail",failList.size());
                resultMap.put("failData",failList);
                resultMap.put("errUrl",url);
                return R.success(resultMap);
            } catch (IOException e) {
                log.error("导入抄表数据错误:"+e.getMessage());
                return R.fail("导入抄表数据错误");
            }
        }
        return R.success(null);
    };

    public Map<String, ReadMeterData> getReadMeterMap(List<ReadMeterData> dataList) {
        return dataList.size() == 0 ? new HashMap<>() : dataList.stream().collect(
                Collectors.toMap(meter -> meter.getGasMeterCode() + "_" + meter.getReadMeterYear() + "_" + meter.getReadMeterMonth(), a -> a, (k1, k2) -> k1));
    }

    public R<List<ReadMeterDataErrorSaveDTO>> updateSuccessList(List<ReadMeterData> dataList, Map<String, ReadMeterData> importMap) {
        List<ReadMeterData> successList = new ArrayList<>();
        List<ReadMeterDataUpdateDTO> updateList = getReadMeterDataUpdateList(dataList, importMap);
        Map<String, BigDecimal> historyMap = getHistory(updateList).getData();
        Map<Long,Integer> countMap = new HashMap<>();
        //校验用气量是否异常，异常则导入失败
        List<Long> gasTypeIds = updateList.stream().map(ReadMeterDataUpdateDTO :: getUseGasTypeId).distinct().collect(Collectors.toList());
        List<ExceptionRuleConf> exceptlist = ruleConfService.queryByGasTypeId(gasTypeIds);
        Map<Long,ExceptionRuleConf> map = exceptlist==null?
                new HashMap<>():exceptlist.stream().collect(Collectors.toMap(ExceptionRuleConf::getUseGasTypeId, a -> a,(k1, k2)->k1));
        checkData(successList, updateList, historyMap, countMap, map);
        baseService.updateBatchById(successList);
        return R.success(null);
    }

    /**
     * 止数异常判断
     * @param successList
     * @param updateList
     * @param historyMap
     * @param countMap
     * @param map
     */
    public void checkData(List<ReadMeterData> successList, List<ReadMeterDataUpdateDTO> updateList, Map<String, BigDecimal> historyMap, Map<Long, Integer> countMap, Map<Long, ExceptionRuleConf> map) {
        updateList.stream().forEach(dto -> {
            //历史平均用气量
            BigDecimal average = historyMap.get(dto.getGasMeterCode());
            BigDecimal lastTotalGas = dto.getLastTotalGas()==null? new BigDecimal(0):dto.getLastTotalGas();//上期止数
            BigDecimal currentTotalGas = dto.getCurrentTotalGas();//本期止数
            BigDecimal monthUseGas = dto.getMonthUseGas();//本期用气量
            //本期止数为null，本期用气量为null，本期止数<上期止数，本期止数-上期止数！=本期用量；
            Boolean bool = getaBoolean(lastTotalGas, currentTotalGas, monthUseGas);
            Long errorId = System.currentTimeMillis();
            if(!bool){
                Long gasTypeId = dto.getUseGasTypeId();
                ExceptionRuleConf conf = map.get(gasTypeId);
                if(null!=conf){
                    BigDecimal surgeMultiple = conf.getSurgeMultiple();//暴增倍数
                    BigDecimal sharpDecrease = conf.getSharpDecrease();//锐减比例
                    if(average.multiply(surgeMultiple).compareTo(currentTotalGas)==-1){//说明用气暴增异常
                        dto.setDataBias(DataStatus.EXECUTING.getCode());
                    }else if(average.multiply(sharpDecrease).compareTo(currentTotalGas)==1){
                        dto.setDataBias(DataStatus.NOT_STARTED.getCode());
                    }else{
                        dto.setDataBias(DataStatus.FINISH.getCode());
                    }
                }
                dto.setDataStatus(0);//已抄表
                Long planId = dto.getPlanId();
                if(countMap.containsKey(planId)){
                    Integer coun = countMap.get(planId)+1;
                    countMap.put(planId,coun);
                }else{
                    countMap.put(planId,1);
                }
                ReadMeterData data = BeanUtil.toBean(dto, ReadMeterData.class);
                successList.add(data);
            }
        });
    }

    /**
     * 本期止数与上期止数基本校验
     * 本期止数为null，本期用气量为null，本期止数<上期止数，本期止数-上期止数！=本期用量；
     * @param lastTotalGas
     * @param currentTotalGas
     * @param monthUseGas
     * @return
     */
    public Boolean getaBoolean(BigDecimal lastTotalGas, BigDecimal currentTotalGas, BigDecimal monthUseGas) {
        Boolean bool = false;
        if(null==currentTotalGas){
            bool = true;
        }else if(null==monthUseGas){
            bool = true;
        }else if(currentTotalGas.compareTo(lastTotalGas)==-1){
            bool = true;
        }else if(currentTotalGas.subtract(lastTotalGas).compareTo(monthUseGas)!=0){
            bool = true;
        }
        return bool;
    }
    public List<ReadMeterDataUpdateDTO> getReadMeterDataUpdateList(List<ReadMeterData> dataList, Map<String, ReadMeterData> importMap) {
        List<ReadMeterDataUpdateDTO> updateList = new ArrayList<>();
        dataList.stream().forEach(dto -> {
            String key = dto.getGasMeterCode()+"_"+dto.getReadMeterYear()+"_"+dto.getReadMeterMonth();
            ReadMeterData data = importMap.get(key);
            if(null!=data){
                dto.setCurrentTotalGas(data.getCurrentTotalGas());
                dto.setMonthUseGas(data.getMonthUseGas());
                dto.setLastTotalGas(data.getLastTotalGas());
                dto.setLastReadTime(data.getLastReadTime());
                ChargeEnum status = data.getChargeStatus();
                dto.setChargeStatus(status==null? ChargeEnum.NO_CHARGE:status);
                dto.setFreeAmount(data.getFreeAmount());
                dto.setUnitPrice(data.getUnitPrice());
                ReadMeterDataUpdateDTO saveDTO = BeanUtil.toBean(dto, ReadMeterDataUpdateDTO.class);
                saveDTO.setId(dto.getId());
                updateList.add(saveDTO);
            }
        });
        return updateList;
    }

    public R<String> getErrorUrl(Workbook workBook) throws IOException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        workBook.write(bos);
        byte[] barray=bos.toByteArray();
        InputStream is=new ByteArrayInputStream(barray);
        MultipartFile multipartFile = new MockMultipartFile("readMeterErr.xlsx","readMeterErr.xlsx","readMeterErr.xlsx",is);
        AttachmentDTO attachmentDTO = attachmentApi.upload(multipartFile,true,null, null,BizTypeEnum.TEMPORARY_DOCUMENTS.getCode()).getData();
        String url = attachmentDTO.getUrl();
        return R.success(url);
    }
    /**
     * 数据校验, true 代表 没有问题, fasle 不通过， 设置默认为true
     * @param readTime
     * @param dataMap
     * @param map
     * @param workBook
     * @param sheet
     * @return
     */
    public Boolean checkExcel(String readTime, Map<Integer, ReadMeterData> dataMap, Map<String, ReadMeterLatestRecord> map, Workbook workBook, Sheet sheet) {
        String[] time = readTime.split("-");// 选择的录入抄表月份（不是抄表时间）
        Integer year = Integer.valueOf(time[0]);
        Integer month = Integer.valueOf(time[1]);
        Boolean noErr = true; //
        for (Row row : sheet) {
            if (isRowEmpty(row)) {
                continue;
            }
            int num = row.getRowNum() + 1;
            if (num > 1) {
                ReadMeterData dto = dataMap.get(num);
                String code = dto.getGasMeterCode();
                ReadMeterLatestRecord latestRecord = map.get(code);
                BigDecimal currentTotalGas = dto.getCurrentTotalGas();
                BigDecimal lastTotalGas = latestRecord.getCurrentTotalGas();

                Boolean codeBool = code == null || "".equals(code);
                Boolean currentTotalGasBool = currentTotalGas == null;
                if (codeBool) {
                    Cell cell = getCell(row, 2);
                    setStyle(workBook, sheet, cell, "气表编码不能为空");
                    noErr =false;
                }
                if (currentTotalGasBool) {
                    Cell cell = getCell(row, 6);
                    setStyle(workBook, sheet, cell, "本期止数不能为空");
                    noErr =false;
                }
                if (dto.getRecordTime()==null) {
                    Cell cell = getCell(row, 7);
                    setStyle(workBook, sheet, cell, "抄表时间不能为空(格式为：2020-02-04)");
                    noErr =false;
                }
                if (codeBool){
                    System.out.println("气表标号不能空");
                    noErr =false;
                }
                Integer latestYear = latestRecord.getYear();
                Integer latestMonth = latestRecord.getMonth();

                if (DateUtils.getDate8(year,month).isBefore(DateUtils.getDate8(latestYear,latestMonth)) ||
                        DateUtils.getDate8(year,month).isEqual(DateUtils.getDate8(latestYear,latestMonth))){
                    Cell cell = getCell(row, 2);
                    String datetime = latestYear + "年" + latestMonth + "月";
                    setStyle(workBook, sheet, cell, "该气表已完成" + datetime + "抄表，故无法当前数据无法导入");
                    noErr =false;
                }
                if (!currentTotalGasBool && BigDecimalUtils.lessThan(currentTotalGas, lastTotalGas)) {
                    Cell cell = getCell(row, 6);
                    setStyle(workBook, sheet, cell, "本期止数不能小于上期止数");
                    noErr = false;

                }
            }
        }
        return noErr;
    }
//    /**
//     * 数据校验
//     * @param readTime
//     * @param dataMap
//     * @param map
//     * @param workBook
//     * @param sheet
//     * @return
//     */
//    public Boolean checkExcel(String readTime, Map<Integer, ReadMeterData> dataMap, Map<String, ReadMeterLatestRecord> map, Workbook workBook, Sheet sheet) {
//        String[] time = readTime.split("-");
//        Integer year = Integer.valueOf(time[0]);
//        Integer month = Integer.valueOf(time[1]);
//        Boolean isErr = true;
//        for(Row row : sheet){
//            Boolean bool= isRowEmpty(row);
//            if(!bool){
//                int num = row.getRowNum()+1;
//                if(num>1){
//                    ReadMeterData dto = dataMap.get(num);
//                    String code = dto.getGasMeterCode();
//                    ReadMeterLatestRecord latestRecord = map.get(code);
//                    BigDecimal currentTotalGas = dto.getCurrentTotalGas();
//                    BigDecimal lastTotalGas = latestRecord.getCurrentTotalGas();
//                    Boolean codeBool = code==null || "".equals(code);
//                    Boolean currentTotalGasBool = currentTotalGas==null;
//                    if(codeBool){
//                        Cell cell = getCell(row, 2);
//                        setStyle(workBook, sheet, cell,"气表编码不能为空");
//                    }
//                    if(currentTotalGasBool){
//                        Cell cell = getCell(row, 6);
//                        setStyle(workBook, sheet, cell,"本期止数不能为空");
//                    }
//                    if(currentTotalGasBool){
//                        Cell cell = getCell(row, 7);
//                        setStyle(workBook, sheet, cell,"抄表时间不能为空");
//                    }
//                    if(!codeBool){
//                        Integer latestYear = latestRecord.getYear();
//                        Integer latestMonth = latestRecord.getMonth();
//                        Boolean isError= false;
//                        if(latestYear>year){
//                            isError = true;
//                        }else if(latestYear.equals( year)){
//                            if(latestMonth>month){
//                                isError = true;
//                            }
//                        }
//                        if(isError){
//                            Cell cell = getCell(row, 2);
//                            String datetime = latestYear+"年"+latestMonth+"月";
//                            setStyle(workBook, sheet, cell,"该气表已完成"+datetime+"抄表，故无法当前数据无法导入");
//                        }else{
//                            if(!currentTotalGasBool){
//                                //BigDecimal lastTotalGas = data.getLastTotalGas()==null? new BigDecimal(0):dto.getLastTotalGas();//上期止数
//                                if(currentTotalGas.compareTo(lastTotalGas)==-1){
//                                    Cell cell = getCell(row, 6);
//                                    setStyle(workBook, sheet, cell,"本期止数不能小于上期止数");
//                                }else{
//                                    isErr =false;
//                                }
//                            }else{
//                                isErr =true;
//                            }
//                        }
//                    }else{
//                        isErr =true;
//                    }
//                }
//            }
//        }
//        return isErr;
//    }

    public Cell getCell(Row row, int i) {
        return row.getCell(i)==null? row.createCell(i):row.getCell(i);
    }
    public void setStyle(Workbook workBook, Sheet sheet, Cell cell,String desc) {
        HSSFCellStyle style = (HSSFCellStyle) workBook.createCellStyle();
        HSSFFont redFont = (HSSFFont) workBook.createFont();
        redFont.setColor(Font.COLOR_RED);
        style.setFont(redFont);
        cell.setCellStyle(style);
        HSSFPatriarch p = (HSSFPatriarch) sheet.createDrawingPatriarch();
        HSSFComment comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)0,0,(short)0,0));
        comment.setString(new HSSFRichTextString(desc));
        cell.setCellComment(comment);
    }
    /**
     * 根据气表编码，抄表年月，查询抄表数据(默认查询未通过的)
     *
     * @param
     * @return
     */
    @ApiOperation(value = "根据气表编码，抄表年月，查询抄表数据", notes = "根据气表编码，抄表年月，查询抄表数据")
    @PostMapping("/getMeterDataByCode")
    @SysLog("根据气表编码，抄表年月，查询抄表数据")
    public R<List<ReadMeterData>> getMeterDataByCode(@RequestBody List<ReadMeterData> readMeterDataList, @RequestParam(value = "processEnum", required = false) ProcessEnum process) {//, List<String> meterCodelist, List<Integer> yearlist, List<Integer> monthlist
        List<String> meterCodelist = readMeterDataList.stream().map(ReadMeterData::getGasMeterCode).collect(Collectors.toList());
        List<Integer> yearlist = readMeterDataList.stream().map(ReadMeterData::getReadMeterYear).distinct().collect(Collectors.toList());
        List<Integer> monthlist = readMeterDataList.stream().map(ReadMeterData::getReadMeterMonth).distinct().collect(Collectors.toList());
        return baseService.getMeterDataByCode(meterCodelist, yearlist, monthlist, process);
    }

    ;

    @PostMapping("/getHistory")
    @SysLog("获取最近三次历史数据")
    public R<Map<String, BigDecimal>> getHistory(@RequestBody List<ReadMeterDataUpdateDTO> list) {
        Map<String, BigDecimal> map = new HashMap<>();
        Map<String, List<ReadMeterData>> groupByCode = new HashMap<>();
        //按年月分组
        Map<LocalDate, List<ReadMeterDataUpdateDTO>> groupByTime = list.stream().collect(Collectors.groupingBy(ReadMeterDataUpdateDTO::getReadTime));
        for (Map.Entry<LocalDate, List<ReadMeterDataUpdateDTO>> entry : groupByTime.entrySet()) {
            List<String> meterCodeList = entry.getValue().stream().map(ReadMeterDataUpdateDTO::getGasMeterCode).collect(Collectors.toList());
            List<ReadMeterData> dataList = baseService.getHistory(meterCodeList, entry.getKey()).getData();
            Map<String, List<ReadMeterData>> m = dataList.stream().collect(Collectors.groupingBy(ReadMeterData::getGasMeterCode));
            groupByCode.putAll(m);
        }
        for (Map.Entry<String, List<ReadMeterData>> entry : groupByCode.entrySet()) {
            List<ReadMeterData> li = entry.getValue();
            if (null != li && li.size() > 0) {
                BigDecimal monthUseGas = li.stream().map(ReadMeterData::getMonthUseGas).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(li.size()), 2, BigDecimal.ROUND_HALF_UP);
                map.put(entry.getKey(), monthUseGas);
            } else {
                map.put(entry.getKey(), new BigDecimal(0));
            }
        }
        return R.success(map);
    }

    ;

    @ApiOperation(value = "抄表数据审核")
    @PostMapping("/examine")
    public R<List<ReadMeterData>> examine(@RequestBody List<ReadMeterData> dataList) {
        ProcessEnum status = dataList.get(0).getProcessStatus();// 前端传递的设置状态
        String rejectMsg = dataList.get(0).getReviewObjection();

        List<Long> ids = dataList.stream().map(ReadMeterData::getId).collect(Collectors.toList());
        List<ReadMeterData> list = getReadMeterData(ids);
        //list = baseService.listByIds(ids);
        if (list.size() > 0) {
            List<ProcessEnum> statusList = list.stream().map(ReadMeterData::getProcessStatus).distinct().collect(Collectors.toList());
            if (statusList.size() != 1) {
                R.fail("审核数据状态不一致，请重新选择");
            } else {
                //当前数据的 状态
                ProcessEnum process = statusList.get(0);
                switch (status) {
                    case TO_BE_REVIEWED:
                        //相当于撤回（只有已收费状态+无法撤回）--》
                        /*if (Objects.equals(ChargeEnum.CHARGED,list.get(0).getChargeStatus())) {
//                        if (!process.eq(ProcessEnum.SUBMIT_FOR_REVIEW)) {
                            return R.fail("当前状态无法撤回");
                        }*/
                        // 所有状态设置为待提审，收费状态设置为 null
                        /*list.forEach(x -> x.setProcessStatus(ProcessEnum.TO_BE_REVIEWED)
                                .setChargeStatus(null));*/
                        filter(ids,0);
                        baseService.update(Wraps.<ReadMeterData>lbU()
                                .set(ReadMeterData::getProcessStatus,ProcessEnum.TO_BE_REVIEWED)
                                .set(ReadMeterData::getChargeStatus,null)
                                .in(ReadMeterData::getId,ids)
                        );
                        return success(list);
                    case SUBMIT_FOR_REVIEW:
                        //提审
                        if (!process.eq(ProcessEnum.TO_BE_REVIEWED) && !process.eq(ProcessEnum.REVIEW_REJECTED)) {
                            return R.fail("当前状态无法再次提审");
                        }
                        break;
                    case APPROVED:
                        //审核通过
                        if (!process.eq(ProcessEnum.SUBMIT_FOR_REVIEW)) {
                            return R.fail("非提审状态无法审核");
                        }
                        break;
                    case REVIEW_REJECTED:
                        if (!process.eq(ProcessEnum.SUBMIT_FOR_REVIEW)) {
                            return R.fail("非提审状态无法驳回");
                        }
                        break;
                    default:
                        return R.fail("无效操作");
                }

                list.forEach(x -> x.setProcessStatus(status)
                        .setReviewObjection(rejectMsg));
                baseService.updateBatchById(list);

            }
        }
        return success(list);
    }

    @ApiOperation(value = "抄表数据审核")
    @PostMapping("/settlement")
    public R<List<ReadMeterData>> settlement(@RequestBody List<Long> dataList) {
        List<ReadMeterData> list = getReadMeterData(dataList);
        return R.success(list);
    }

    /**
     * 结算排序
     *
     * @param ids
     * @return
     */
    public List<ReadMeterData> getReadMeterData(@RequestBody List<Long> ids) {
        LambdaQueryWrapper<ReadMeterData> lambda = new LambdaQueryWrapper<>();
        lambda.orderByAsc(ReadMeterData::getGasMeterCode);
        lambda.orderByAsc(ReadMeterData::getCreateTime);
        lambda.in(ReadMeterData::getId, ids);
        List<ReadMeterData> list = baseService.list(lambda);
        return list;
    }

    @ApiOperation(value = "根据抄表册id获取抄表数据")
    @PostMapping("/getDataByBookId")
    R<List<ReadMeterData>> getDataByBookId(@RequestBody List<Long> ids) {
        LambdaQueryWrapper<ReadMeterData> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterData::getBookId, ids);
        lambda.ne(ReadMeterData::getDataStatus, 0);
        return R.success(baseService.list(lambda));
    }

    ;

    @ApiOperation(value = "根据客户编码获取抄表数据")
    @PostMapping("/getDataByCustomerCode")
    R<List<ReadMeterData>> getDataByCustomerCode(@RequestBody List<String> list) {
        LambdaQueryWrapper<ReadMeterData> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterData::getCustomerCode, list);
        lambda.ne(ReadMeterData::getDataStatus, 0);
        return R.success(baseService.list(lambda));
    }

    @ApiOperation(value = "根据气表编码获取抄表数据")
    @PostMapping("/deleteByGasCode")
    R<Boolean> deleteByGasCode(@RequestBody List<String> list) {
        return baseService.deleteByGasCode(list);
    }

    @ApiOperation(value = "抄表数据 <纠正>修改接口")
    @PostMapping("/input")
    R<ReadMeterData> input(@RequestBody ReadMeterData updateDTO) {
        ReadMeterData red = Optional.ofNullable(baseService.getById(updateDTO.getId())).orElseThrow(() -> new BizException("无此抄表数据"));
        red.setCurrentTotalGas(updateDTO.getCurrentTotalGas());// 只需要此参数和上面的id
        if (!Objects.equals(red.getProcessStatus(), ProcessEnum.TO_BE_REVIEWED)
                && !Objects.equals(red.getProcessStatus(), ProcessEnum.REVIEW_REJECTED)) {
            throw new BizException("2此月份的抄表数据此前录入已审核，其当前是 非待提审和驳回状态 不能修改录入 抄表数据");
        }
        LocalDate inputDate = red.getReadTime();

        // 根据气表编号 判断此表在此抄表月份 中，查询此表的 对应的最新月份抄表数值 b ，1.如果传入的 年月 <(早于) 系统现在a的 年月，不能抄表
        // 2. 此时传入的抄表指数 x 必须大于 系统现在a 的最新指数， 否则 提升失败
        ReadMeterLatestRecord latestRecord = latestRecordService.getOne(Wraps.<ReadMeterLatestRecord>lbQ()
                .eq(ReadMeterLatestRecord::getGasMeterCode, red.getGasMeterCode()));

        if (Objects.isNull(latestRecord)){
            throw new BizException("2此表还没有初始的 最新抄表记录 ，请先从抄表册移除该表具用户后重新添加到抄表册后，再录入抄表数据");
        }
        if (inputDate.isBefore(DateUtils.getDate8(latestRecord.getYear(), latestRecord.getMonth()))){
            throw new BizException("2选择纠正的数据年月早于系统最新的录入时间，不能录入以往老数据造成止数错乱");
        }
        /*if (BigDecimalUtils.lessThan(red.getCurrentTotalGas(), latestRecord.getCurrentTotalGas())){
            throw new BizException("2选择纠正的数据的年月的抄表数据指数不能小于之前系统存在的数据 ");
        }*/
        red.setCurrentTotalGas(red.getCurrentTotalGas())
                .setMonthUseGas(red.getCurrentTotalGas().subtract(red.getLastTotalGas()));
        baseService.updateById(red);

        // 最后还需要同步修改掉 系统中这只表具的的最新数据
        latestRecordService.updateById(latestRecord
                .setCurrentTotalGas(red.getCurrentTotalGas()));

        return R.success(red);
    }

    @ApiOperation(value = "抄表数据录入及修改接口")
    @PostMapping("/inputList")
    R<Boolean> inputList(@RequestBody List<ReadMeterData> list){
        List<String> codeList = list.stream().map(ReadMeterData::getGasMeterCode).collect(Collectors.toList());
        LambdaQueryWrapper<ReadMeterLatestRecord> wrapper = new LambdaQueryWrapper();
        wrapper.in(ReadMeterLatestRecord::getGasMeterCode, codeList);
        List<ReadMeterLatestRecord> latestRecords = latestRecordService.list(wrapper);
        if(latestRecords.size()>0){
            Map<String, ReadMeterLatestRecord> map = latestRecords.stream().collect(Collectors.toMap(ReadMeterLatestRecord::getGasMeterCode, Function.identity()));
            list.stream().forEach(item ->{
                ReadMeterLatestRecord latestRecord = map.get(item.getGasMeterCode());

                LocalDate recordTime = latestRecord.getReadTime();
                BigDecimal lastgas = latestRecord.getCurrentTotalGas();// 用它做本次的上期止数！（且本次输入的止数不应该小于此 lastgas）

                Integer year = item.getReadMeterYear();
                Integer month = item.getReadMeterMonth();
                LocalDate readTime = item.getReadTime()==null? DateUtils.getDate8(year, month):item.getReadTime();
                if(recordTime.isBefore(readTime) && BigDecimalUtils.lessEqual(lastgas,item.getCurrentTotalGas())){
                    item.setLastReadTime(latestRecord.getCurrentReadTime());
                    item.setDataStatus(ReadMeterStatusEnum.READ.getCode());// 只有满足上if才会设置下面的抄表数据！
                    item.setLastTotalGas(lastgas);// 用最新系统的当前值做为 上期止数
                    item.setMonthUseGas(item.getCurrentTotalGas().subtract(lastgas));// 重新计算得用量

                    latestRecord.setYear(year);
                    latestRecord.setMonth(month);
                    latestRecord.setReadTime(readTime);
                    latestRecord.setCurrentTotalGas(item.getCurrentTotalGas());// 设置跟新 系统的当前值为
                    latestRecord.setCurrentReadTime(item.getRecordTime());
                }
            });
            baseService.updateBatchById(list);
            //因为统计抄表数据需要未抄数据，所以不删除未抄数据
            /*List<ReadMeterData> delData = baseService.list(Wraps.<ReadMeterData>lbQ()
                    .in(ReadMeterData::getGasMeterCode, codeList).eq(ReadMeterData::getDataStatus,ReadMeterStatusEnum.WAIT_READ.getCode()));
            if(delData.size()>0){
                baseService.deleteData(delData);
            }*/
            latestRecordService.updateBatchById(latestRecords);
        }

        return R.success();
    }

    /**
     * 反射设置  meterMonthGas 根据月份查询字段并赋值
     *
     * @param lastMonth
     * @param meterMonthGas
     */
    public void setLastTotalGas(Integer lastMonth, ReadMeterMonthGas meterMonthGas, BigDecimal currentTotalGas) {
        String monthStr = RecordYear.getType(lastMonth);
        try {
            if (null != meterMonthGas) {
                Field field = ReadMeterMonthGas.class.getDeclaredField(monthStr);
                field.setAccessible(true);
                field.set(meterMonthGas, currentTotalGas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "导出抄表名单")
    @PostMapping("/exportReadMeterList")
    public void exportReadMeterList(@RequestBody List<ReadMeterPlanScope> list, HttpServletResponse response) {
        try {
            List<ReadMeterData> dataList = new ArrayList<>();
            LambdaQueryWrapper<ReadMeterData> lambda = new LambdaQueryWrapper<>();
            for (ReadMeterPlanScope item : list) {
                Long bookId = item.getBookId();
                Long planId = item.getReadmeterPlanId();
                lambda.eq(ReadMeterData::getBookId, bookId);
                lambda.eq(ReadMeterData::getPlanId, planId);
                // 只导出未抄表的数据列表xx
                lambda.eq(ReadMeterData::getDataStatus, ReadMeterStatusEnum.WAIT_READ.getCode());
                List<ReadMeterData> list1 = baseService.list(lambda);
                dataList.addAll(list1);
                lambda.clear();
            }
            exportExcel(response, null, dataList, getaClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    @ApiOperation(value = "根据抄表月份和抄表册id导出待抄表数据")
    @PostMapping("/exportReadMeterData")
    public void exportReadMeterData(@RequestBody ReadMeterData readMeterData, HttpServletResponse response) {
        try {
            Long bookId = readMeterData.getBookId();
            List<String> dataList = getAndSaveReadMeterData(readMeterData.getReadMeterYear(),readMeterData.getReadMeterMonth(),Arrays.asList(bookId));
            List<ReadMeterData> dataList1 = baseService.list();
            exportExcel(response, null, dataList1, getaClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "分页查询抄表数据")
    @PostMapping("/pageReadMeterData")
    public R<IPage<ReadMeterData>> pageReadMeterData(@RequestBody PageParams<ReadMeterDataPageDTO> params) {
        ReadMeterDataPageDTO pageDTO = params.getModel();
        Integer year = pageDTO.getReadMeterYear();
        Integer month = pageDTO.getReadMeterMonth();
        Long bookId = pageDTO.getBookId();
        List<String> codeList = getAndSaveReadMeterData(year, month, Arrays.asList(bookId));
        try {
            if(codeList==null){
                return R.success(null);
            }
            return R.success(baseService.pageReadMeterData(params, codeList));
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("");
        }
    }

    @ApiOperation(value = "分页查询抄表数据")
    @PostMapping("/pageExistData")
    public R<IPage<ReadMeterData>> pageExistData(@RequestBody PageParams<ReadMeterDataPageDTO> params) {
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        ReadMeterData data = BeanUtil.toBean(params.getModel(), ReadMeterData.class);
        QueryWrap<ReadMeterData> wrapper = Wraps.q(data);
        wrapper.in("gas_meter_code", params.getModel().getGasMeterCodeList());
        wrapper.in("org_id", orgIds);
        wrapper.eq("data_type",DataTypeEnum.ORDINARY_DATA.getCode());
        IPage<ReadMeterData> page = params.getPage();
        return R.success(baseService.page(page, wrapper));
    }

    public List<String> getAndSaveReadMeterData(Integer year, Integer month, List<Long> bookIds) {
        synchronized (this){
            List<GasMeterBookRecord> recordList = recordService.queryByBookId(bookIds).getData();
            if (CollectionUtil.isEmpty(recordList)){
                return null;
            }
            List<String> codeList = recordList.stream().map(GasMeterBookRecord::getGasMeterCode).collect(Collectors.toList());
            LbqWrapper<GasMeter> gasWrapper = Wraps.lbQ();
            gasWrapper.in(GasMeter::getGasCode,codeList);
            List<GasMeter> gasList = gasMeterService.list(gasWrapper);
            Map<String, GasMeter> gasMap = gasList.stream().collect(Collectors.toMap(GasMeter::getGasCode, Function.identity(),(oldValue, newValue) -> newValue));
            LambdaQueryWrapper<ReadMeterData> dataCountWrapper = new LambdaQueryWrapper();
            dataCountWrapper.in(ReadMeterData::getGasMeterCode, codeList);
            dataCountWrapper.eq(ReadMeterData::getReadMeterYear,year);
            dataCountWrapper.eq(ReadMeterData::getReadMeterMonth,month);
            dataCountWrapper.eq(ReadMeterData::getDataType,DataTypeEnum.ORDINARY_DATA.getCode());
            List<ReadMeterData> dataList = baseService.list(dataCountWrapper);
            Map<String,ReadMeterData> existMap = dataList.stream().collect(Collectors.toMap(ReadMeterData::getGasMeterCode, Function.identity()));

            LambdaQueryWrapper<ReadMeterLatestRecord> wrapper = new LambdaQueryWrapper();
            wrapper.in(ReadMeterLatestRecord::getGasMeterCode, codeList);
            List<ReadMeterLatestRecord> latestRecords = latestRecordService.list(wrapper);
            Map<String, ReadMeterLatestRecord> map = latestRecords.stream().collect(Collectors.toMap(ReadMeterLatestRecord::getGasMeterCode, Function.identity()));
            List<ReadMeterData> historyList = baseService.selectGasCode(codeList).getData();
            Map<String,ReadMeterData> historyListMap = historyList.stream().collect(Collectors.toMap(ReadMeterData::getGasMeterCode, Function.identity()));
            codeList = codeList.stream().filter(item -> {
                ReadMeterData data = historyListMap.get(item);
                if(Objects.isNull(data)){
                    return true;
                }else{
                    return  data.getDataStatus().equals(ReadMeterStatusEnum.WAIT_READ.getCode())
                            || (data.getDataStatus().equals(ReadMeterStatusEnum.READ.getCode()) && data.getProcessStatus().eq(ProcessEnum.SETTLED.getCode()));
                }
            }).collect(Collectors.toList());

            //生成数据
            List<ReadMeterData> saveList = new ArrayList<>();
            List<String> finalCodeList = codeList;
            recordList.stream().forEach(record ->{
                ReadMeterLatestRecord record1 = map.get(record.getGasMeterCode());
                BigDecimal currentTotalGas = record1.getCurrentTotalGas();
                LocalDate time = record1.getCurrentReadTime();
                ReadMeterData readMeterData = null;
                String gasCode = record.getGasMeterCode();
                if(finalCodeList.contains(gasCode)){
                    if(!existMap.keySet().contains(gasCode)){
                        readMeterData = new ReadMeterData();
                        GasMeter gas = gasMap.get(gasCode);
                        readMeterData.setCompanyCode(gas.getCompanyCode());
                        readMeterData.setCompanyName(gas.getCompanyName());
                        readMeterData.setOrgId(UserOrgIdUtil.getUserOrgId());
                        readMeterData.setOrgName(gas.getOrgName());
                        readMeterData.setCustomerCode(record.getCustomerCode());
                        readMeterData.setCustomerName(record.getCustomerName());
                        readMeterData.setGasMeterCode(gasCode);
                        readMeterData.setMoreGasMeterAddress(record.getMoreGasMeterAddress());
                        readMeterData.setBookId(record.getReadMeterBook());
                        readMeterData.setGasMeterNumber(record.getGasMeterNumber());
                        readMeterData.setUseGasTypeId(record.getUseGasTypeId());
                        readMeterData.setUseGasTypeName(record.getUseGasTypeName());
                        // 关联抄表员id
                        readMeterData.setReadMeterUserId(record.getReadMeterUser());
                        readMeterData.setReadMeterUserName(record.getReadMeterUserName());
                        readMeterData.setReadMeterYear(year);
                        readMeterData.setReadMeterMonth(month);
                        readMeterData.setProcessStatus(ProcessEnum.TO_BE_REVIEWED);
                        readMeterData.setLastTotalGas(currentTotalGas);
                        readMeterData.setLastReadTime(time);
                        readMeterData.setDataType(DataTypeEnum.ORDINARY_DATA.getCode());
                        try {
                            LocalDate date = DateUtils.getDate8(year,month);
                            readMeterData.setReadTime(date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        saveList.add(readMeterData);
                    }else{
                        readMeterData = existMap.get(record.getGasMeterCode());
                        if(readMeterData.getReadTime().equals(record1.getReadTime()) && readMeterData.getDataStatus().equals(ReadMeterStatusEnum.WAIT_READ.getCode())){
                            readMeterData.setLastTotalGas(currentTotalGas);
                            readMeterData.setLastReadTime(time);
                            saveList.add(readMeterData);
                        }
                    }
                }
            });
            Boolean bool = baseService.saveOrUpdateBatch(saveList);
            if(bool){
                return codeList;
            }

            return codeList;
        }
    };

    @ApiOperation(value = "根据抄表册导出抄表名单")
    @PostMapping("/exportReadMeterByBook")
    void exportReadMeterByBook(@RequestParam("bookId") Long bookId, HttpServletResponse response) {
        try {
            List<GasMeterBookRecord> dataList = recordService.queryByBookId(Arrays.asList(bookId)).getData();
            List<ReadMeterData> saveList = new ArrayList<>();
            dataList.stream().forEach(record ->{
                ReadMeterData readMeterData = new ReadMeterData();
                //readMeterData.setCustomerCode(record.getCustomerCode());
                readMeterData.setCustomerChargeNo(record.getCustomerChargeNo());
                readMeterData.setCustomerName(record.getCustomerName());
                readMeterData.setGasMeterCode(record.getGasMeterCode());
                readMeterData.setMoreGasMeterAddress(record.getMoreGasMeterAddress());
                readMeterData.setGasMeterNumber(record.getGasMeterNumber());
                readMeterData.setReadMeterUserName(record.getReadMeterUserName());
                saveList.add(readMeterData);
            });
            exportExcel(response, null, saveList, getaClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public LocalDate getDate(Integer year, Integer month) throws Exception {
//        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//        String m = month<10?month+"":"0"+month;
//        Date date = dateFormat1.parse(year+"-"+m+"-01");
//        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//    }

    /**
     * 通过表具编号查询未缴费的抄表数据
     * @param gasMeterCode 表具编号
     * @return 未缴费抄表数据
     */
    @ApiOperation("通过表具编号查询未缴费的抄表数据")
    @GetMapping("/queryUnChargedData")
    public R<List<ReadMeterData>> queryUnChargedData(@RequestParam("gasMeterCode")String gasMeterCode) {
        LbqWrapper<ReadMeterData> lqw = Wraps.lbQ();
        lqw.eq(ReadMeterData::getGasMeterCode, gasMeterCode)
                .eq(ReadMeterData::getDataStatus, 0)
                .ne(ReadMeterData::getMonthUseGas, 0)
                .eq(ReadMeterData::getDataType, DataTypeEnum.ORDINARY_DATA.getCode())
                .and(wrapper -> wrapper.eq(ReadMeterData::getChargeStatus, ChargeEnum.NO_CHARGE.getCode())
                                        .or().isNull(ReadMeterData::getChargeStatus));
        List<ReadMeterData> list = baseService.list(lqw);
        return R.success(list);
    }
    /**
     * 查询抄表次数
     * @param gasMeterCode 表具编号
     * @return 表具抄表次数
     */
    @GetMapping("/countReadMeterData")
    public R<Integer> countReadMeterData(@RequestParam("gasMeterCode")String gasMeterCode){
        LbqWrapper<ReadMeterData> lqw = Wraps.lbQ();
        lqw.eq(ReadMeterData::getGasMeterCode, gasMeterCode)
                .eq(ReadMeterData::getDataStatus, 0)
                .eq(ReadMeterData::getDataType, DataTypeEnum.ORDINARY_DATA.getCode());
        return R.success(baseService.count(lqw));
    }

    /**
     * 检测确保 某表具 对应的月份初始化数据
     *
     * @param
     * @return
     */
    @ApiOperation(value = "检测确保 某表具 对应的月份初始化数据", notes = "检测确保 某表具 对应的月份初始化数据")
    @PostMapping("/checkInitOneMeterData")
    public R<ReadMeterData> checkInitOneMeterData(@RequestBody OneReadDataInputDTO dto) {//, List<String> meterCodelist, List<Integer> yearlist, List<Integer> monthlist
        // 一：先查询是 此表否已经 初始化此月份数据（如果没有先初始化），录入过此月份下的数据，如录入过，提示已经录入，可以在表的历史抄表记录里面查查看修改--基础服务提供个方法？
        ReadMeterData resutl = super.queryOne(new ReadMeterData().setGasMeterCode(dto.getGasMeterCode())
                .setReadMeterYear(dto.getReadMeterYear())
                .setReadMeterMonth(dto.getReadMeterMonth())).getData();

        if (null == resutl){
            // 初始化xx
            // 三.构建抄表数据信息,gasMeterCode 查询抄表册关联客户表具 得到xx
            GasMeterBookRecord relate = recordService.queryByGasCode(dto.getGasMeterCode());
            // TODO 这也可考虑调用 getReadMeterData
            // 大部分字段命名同，个别不同再单独设置值
            ReadMeterDataSaveDTO data = BeanPlusUtil.toBean(relate, ReadMeterDataSaveDTO.class);
            data.setBookId(relate.getReadMeterBook())
                    .setReadMeterUserId(relate.getReadMeterUser())
                    .setReadMeterYear(dto.getReadMeterYear()) // 年
                    .setReadMeterMonth(dto.getReadMeterMonth()) // 月份
                    .setReadTime(DateUtils.getDate8(dto.getReadMeterYear(),dto.getReadMeterMonth()))// 抄表年月
                    .setProcessStatus(ProcessEnum.TO_BE_REVIEWED)
                    .setDataStatus(ReadMeterStatusEnum.WAIT_READ.getCode());// 初始未抄表
            // 再设值抄表数据(本期，上期，用量)相关, 它的上期止数来源 当前系统的最新抄表值 latestRecord.getCurrentTotalGas()
            resutl= super.save(data).getData();

        }
        return success(resutl);
    }

    /**
     *
     * @param ids
     * @param type：0-撤回，1-删除数据
     * @return
     */
    @ApiOperation(value = "筛选可以撤回的数据，可以的话直接撤回", notes = "筛选可以撤回的数据")
    @PostMapping("/filter")
    R<Boolean> filter(@RequestParam("ids[]") List<Long> ids, @RequestParam("type") int type){
        List<ReadMeterData> dataList = baseService.listByIds(ids);
        List<String> codeList = dataList.stream().map(ReadMeterData::getGasMeterCode).collect(Collectors.toList());
        List<ReadMeterLatestRecord> latestList = latestRecordService.list(
                Wraps.<ReadMeterLatestRecord>lbU().in(ReadMeterLatestRecord::getGasMeterCode, codeList)
        );
        Map<String, ReadMeterLatestRecord> LatestMap = latestList.stream().collect(Collectors.toMap(ReadMeterLatestRecord::getGasMeterCode, Function.identity()));
        List<String> failList = new ArrayList<>();
        Map<String,List<ReadMeterData>> map = new HashMap<>(16);
        map.put("success",new ArrayList<>());
        map.put("fail",new ArrayList<>());
        List<ReadMeterLatestRecord> updateList = new ArrayList<>();
        List<ReadMeterData> deleteList = new ArrayList<>();
        dataList.forEach(item ->{
            String code = item.getGasMeterCode();
            ReadMeterLatestRecord record = LatestMap.get(code);
            if(item.getReadTime().isBefore(record.getReadTime())){
                map.get("fail").add(item);
                failList.add(code);
            }else{
                map.get("success").add(item);
                if(type==1){
                    //考虑到撤回数据不多
                    /*List<ReadMeterData> dataLi = baseService.list(
                            Wraps.<ReadMeterData>lbU()
                                    .in(ReadMeterData::getGasMeterCode, item.getGasMeterCode()).ge(ReadMeterData::getReadTime,item.getReadTime())
                    );
                    deleteList.addAll(dataLi);*/
                    deleteList.addAll(dataList);
                    //boolean bool = deleteReal(item).getData();
                    ReadMeterData data = baseService.getOne(
                            Wraps.<ReadMeterData>lbU()
                                    .eq(ReadMeterData::getGasMeterCode, item.getGasMeterCode())
                                    .eq(ReadMeterData::getDataStatus,ReadMeterStatusEnum.READ.getCode())
                                    .lt(ReadMeterData::getReadTime,item.getReadTime())
                                    .orderByDesc(ReadMeterData::getReadTime).last("limit 1")
                    );
                    if(null!=data){
                        record.setCurrentTotalGas(data.getCurrentTotalGas());
                        record.setCurrentReadTime(data.getRecordTime());
                        record.setYear(data.getReadMeterYear());
                        record.setMonth(data.getReadMeterMonth());
                        record.setReadTime(data.getReadTime());
                        updateList.add(record);
                    }else{
                        record.setYear(2000).setMonth(1);// 还得 设置此 表具初始的年月（2000.01） 和 --》底数（从表具档案获得）
                        record.setReadTime(DateUtils.getDate8(2000, 1));
                        record.setCurrentReadTime(null);
                        BigDecimal gasMeterBase = gasMeterBizApi.findGasMeterByCode(code).getData().getGasMeterBase();
                        record.setCurrentTotalGas(gasMeterBase!=null?gasMeterBase:new BigDecimal(0.0000));
                        updateList.add(record);
                    }
                }
            }
        });
        if(failList.size()>0){
            return fail("以下表具已经产生新的数据，如需撤回，请从最新数据依次撤回");
        }
        if(updateList.size()>0){
            latestRecordService.updateBatchById(updateList);
        }
        if(deleteList.size()>0){
            baseService.deleteData(deleteList);
        }
        return R.success();
    };

    /**
     * 撤回到未抄表
     * @param ids
     * @return
     */
    @ApiOperation(value = "撤回到未抄表", notes = "撤回到未抄表")
    @PostMapping("/withdraw")
    R<Boolean> withdraw(@RequestBody List<Long> ids){
        return filter(ids,1);
        //return delete(ids);
    };

    @ApiOperation(value = "修改客户")
    @PostMapping("/updateDataByCustomer")
    R<Boolean> updateDataByCustomer(@RequestBody ReadMeterDataUpdateDTO updateDTO) {
        return R.success(baseService.update(Wraps.<ReadMeterData>lbU()
                .set(ReadMeterData::getCustomerName,updateDTO.getCustomerName())
                .eq(ReadMeterData::getCustomerCode,updateDTO.getCustomerCode())
                .eq(ReadMeterData::getDataStatus,ReadMeterStatusEnum.WAIT_READ.getCode())));
    };

    @ApiOperation(value = "修改用气类型")
    @PostMapping("/updateDataByGasType")
    R<Boolean> updateDataByGasType(@RequestBody ReadMeterDataUpdateDTO updateDTO) {
        if(StringUtils.isNullOrEmpty(updateDTO.getGasMeterCode())){
            return R.success(baseService.update(Wraps.<ReadMeterData>lbU()
                    .set(ReadMeterData::getUseGasTypeName,updateDTO.getUseGasTypeName())
                    .eq(ReadMeterData::getUseGasTypeId,updateDTO.getUseGasTypeId())));
        }else{
            return R.success(baseService.update(Wraps.<ReadMeterData>lbU()
                    .set(ReadMeterData::getUseGasTypeName,updateDTO.getUseGasTypeName())
                    .set(ReadMeterData::getUseGasTypeId,updateDTO.getUseGasTypeId())
                    .eq(ReadMeterData::getGasMeterCode,updateDTO.getGasMeterCode())
                    .ne(ReadMeterData::getProcessStatus,ProcessEnum.SETTLED.getCode())));
        }
    };

    @ApiOperation(value = "退费时修改数据")
    @PostMapping("/updateDataRefund")
    R<Boolean> updateDataRefund(@RequestBody List<Long> idsList) {
        return R.success(baseService.update(Wraps.<ReadMeterData>lbU()
                .set(ReadMeterData::getSettlementTime,null)
                .set(ReadMeterData::getSettlementUserId,null)
                .set(ReadMeterData::getSettlementUserName,null)
                .set(ReadMeterData::getChargeStatus,null)
                .set(ReadMeterData::getProcessStatus,ProcessEnum.TO_BE_REVIEWED)
                .set(ReadMeterData::getCycleTotalUseGas,null)
                .in(ReadMeterData::getId,idsList)));
    };

    /**
     * 拆表，换表时旧表费用结算
     * @param readMeterData
     * @return
     */
    @ApiOperation(value = "拆表，换表时旧表费用结算")
    @PostMapping("/costSettlement")
    R<ReadMeterData> costSettlement(@RequestBody ReadMeterData readMeterData){
        String gasCode = readMeterData.getGasMeterCode();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        LocalDate recordTime = LocalDate.now();
        LocalDate readTime = DateUtils.getDate8(year, month);
        LbqWrapper<GasMeter> gasWrapper = Wraps.lbQ();
        gasWrapper.eq(GasMeter::getGasCode,gasCode);
        //GasMeter gas = gasMeterService.getOne(gasWrapper);
        LbqWrapper<ReadMeterLatestRecord> latestWrapper = Wraps.lbQ();
        latestWrapper.eq(ReadMeterLatestRecord::getGasMeterCode,gasCode);
        ReadMeterLatestRecord latestRecord = latestRecordService.getOne(latestWrapper);
        if(null!=latestRecord){
            /*readMeterData.setCompanyCode(gas.getCompanyCode());
            readMeterData.setCompanyName(gas.getCompanyName());
            readMeterData.setOrgId(gas.getOrgId());
            readMeterData.setOrgName(gas.getOrgName());
            readMeterData.setMoreGasMeterAddress(gas.getMoreGasMeterAddress());
            readMeterData.setGasMeterNumber(gas.getGasMeterNumber());
            readMeterData.setUseGasTypeId(gas.getUseGasTypeId());
            readMeterData.setUseGasTypeName(gas.getUseGasTypeName());*/
            readMeterData.setReadMeterYear(year);
            readMeterData.setReadMeterMonth(month);
            readMeterData.setReadTime(readTime);
            readMeterData.setProcessStatus(ProcessEnum.APPROVED);
            readMeterData.setLastTotalGas(latestRecord.getCurrentTotalGas());
            readMeterData.setLastReadTime(LocalDate.now());
            BigDecimal monthUseGas = readMeterData.getCurrentTotalGas().subtract(readMeterData.getLastTotalGas());
            readMeterData.setMonthUseGas(monthUseGas);
            readMeterData.setRecordTime(recordTime);
            readMeterData.setDataStatus(ReadMeterStatusEnum.TERMINATION.getCode());
            latestRecord.setYear(year);
            latestRecord.setMonth(month);
            latestRecord.setReadTime(readTime);
            latestRecord.setCurrentTotalGas(readMeterData.getCurrentTotalGas());
            latestRecord.setCurrentReadTime(recordTime);
        }
        if(null!=readMeterData){
            baseService.save(readMeterData);
            //拆表、换表不更新最新抄表数据  防止拆表、换表中途取消继续使用表具导致后续正常抄表起止数不正确
            //latestRecordService.updateById(latestRecord);
        }
        return R.success(readMeterData);
    }

    @ApiOperation("查询未完结的抄表数据")
    @PostMapping("/isFinished")
    public R<Boolean> isFinished(@RequestBody CustomerGasDto gasMeter) {
        String orderSourceName = gasMeter.getOrderSourceName();
        if(null!=orderSourceName){
            OrderSourceNameEnum sourceName = OrderSourceNameEnum.get(orderSourceName);
            String gasCode = gasMeter.getGasCode();
            Integer count = 0;
            switch (sourceName){
                case READMETER_PAY:
                    count = baseService.count(Wraps.<ReadMeterData>lbU()
                            .eq(ReadMeterData::getGasMeterCode,gasCode)
                            .eq(ReadMeterData::getDataStatus,ReadMeterStatusEnum.READ.getCode())
                            .and(o->o.ne(ReadMeterData::getChargeStatus, ChargeEnum.CHARGED).or().isNull(ReadMeterData::getChargeStatus))
                    );
                    break;
                case REMOTE_READMETER:
                    count = dataIotService.count(Wraps.<ReadMeterDataIot>lbU()
                            .eq(ReadMeterDataIot::getGasMeterCode,gasCode)
                            .eq(ReadMeterDataIot::getDataStatus,ReadMeterStatusEnum.READ.getCode())
                            .ne(ReadMeterDataIot::getChargeStatus, ChargeEnum.CHARGED));
                    break;
                default:
                    break;
            }
            return R.success(count>0? false:true);
        }

        return R.fail("缺少参数：订单来源名称");
    }

    @ApiOperation("统计抄表完成率")
    @PostMapping("/stsReadMeter")
    R<IPage<MeterPlanNowStsVo>> stsReadMeter(@RequestBody StsSearchParam stsSearchParam){
        List<String> readMeterDate = new ArrayList<>();
        if (stsSearchParam.getStartDay() == null){
            readMeterDate.add(LocalDateUtil.monthBegin(LocalDate.now()).toString());
        }else {
            LocalDate start = stsSearchParam.getStartDay();
            if (stsSearchParam.getEndDay() == null){
                stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(LocalDate.now()));
            }
            while (stsSearchParam.getEndDay().isAfter(start)){
                readMeterDate.add(start.toString());
                start = LocalDateUtil.nextMonthBegin(start);
            }
            if (start.isEqual(stsSearchParam.getEndDay())){
                readMeterDate.add(start.toString());
            }
        }
        Page<MeterPlanNowStsVo> resultPage = new Page<>();
        if (stsSearchParam.getPageNo() == null){
            resultPage.setCurrent(1L);
        }else {
            resultPage.setCurrent(stsSearchParam.getPageNo());
        }
        List<MeterPlanNowStsVo> dataList = baseService.stsReadMeterByMonth(resultPage, readMeterDate, stsSearchParam);
        for (MeterPlanNowStsVo vo: dataList){
            vo.setReadMeterDate(vo.getReadMeterDate().substring(0,7));
            vo.setNotFinishedCount(vo.getTotalMeter() - vo.getReadMeterCount());
            if (vo.getTotalMeter() <= 0){
                vo.setCompletionRate(BigDecimal.ZERO);
            }else {
                vo.setCompletionRate(BigDecimal.valueOf(vo.getReadMeterCount()*100.0/vo.getTotalMeter()));
            }
        }
        resultPage.setRecords(dataList);
//        IPage<MeterPlanNowStsVo> page = baseService.stsReadMeter(stsSearchParam);
//        if(page.getSize()>0){
//            List<MeterPlanNowStsVo> list = page.getRecords();
//            list.forEach(item ->{
//                Integer totalMeter = item.getTotalMeter();
//                Integer readMeterCount = item.getReadMeterCount();
//                Integer notFinishedCount = totalMeter-readMeterCount;
//                BigDecimal completionRate;
//                if(totalMeter==0){
//                    completionRate = BigDecimal.ZERO;
//                }else{
//                    completionRate = new BigDecimal(readMeterCount*100).divide(new BigDecimal(totalMeter),2,BigDecimal.ROUND_HALF_UP);
//                }
//                item.setNotFinishedCount(notFinishedCount);
//                item.setCompletionRate(completionRate);
//            });
//        }
        return R.success(resultPage);
    }

    @ApiOperation(value = "抄表业务管理-普表抄表-抄表统计")
    @PostMapping("/generalGasMeterReadSts")
    R<GasMeterReadStsVo> generalGasMeterReadSts(@RequestBody StsSearchParam stsSearchParam){

        GasMeterReadStsVo vo = this.baseService.generalGasMeterReadSts(stsSearchParam);
        return R.success(vo);
    }

    /**
     * 获取最新一条抄表数据-不区分dataType
     * @param gasMeterCode 表身编号
     * @param customerCode 客户编号
     * @return 最新数据
     */
    @GetMapping(value = "/getLatestData")
    R<ReadMeterData> getLatestData(@RequestParam("gasMeterCode") String gasMeterCode, @RequestParam("customerCode") String customerCode){
        LbqWrapper<ReadMeterData> lqw = Wraps.lbQ();
        lqw.eq(ReadMeterData::getGasMeterCode, gasMeterCode)
                .eq(ReadMeterData::getCustomerCode, customerCode)
                .orderByDesc(Entity::getUpdateTime)
                .last("limit 1");
        return R.success(baseService.getOne(lqw));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/21 14:57
     * @remark 售气数据-普表
     */
    @PostMapping(value = "sts/stsGeneralGasMeter")
    R<StsDateVo> stsGeneralGasMeter(@RequestBody StsSearchParam stsSearchParam){

        StsDateVo vo = new StsDateVo();
        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        BigDecimal today = this.baseService.stsGeneralGasMeter(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        BigDecimal month = this.baseService.stsGeneralGasMeter(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        BigDecimal year = this.baseService.stsGeneralGasMeter(stsSearchParam);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal all = this.baseService.stsGeneralGasMeter(stsSearchParam);

        vo.setTodayNum(today);
        vo.setMonthNum(month);
        vo.setYearNum(year);
        vo.setAllNum(all);
        return R.success(vo);
    }


}
