package com.cdqckj.gmis.bizcenter.read.meter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.base.utils.DataStatus;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterDataBizService;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.operateparam.ExceptionRuleBizApi;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;
import com.cdqckj.gmis.readmeter.*;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ReadMeterStatusEnum;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.base.R.SUCCESS_CODE;
import static org.apache.poi.ss.usermodel.CellType.BLANK;

@Slf4j
@Service
public class ReadMeterDataBizServiceImpl extends SuperCenterServiceImpl implements ReadMeterDataBizService {

    @Autowired
    public ReadMeterDataApi readMeterDataApi;
    @Autowired
    public ReadMeterDataIotApi readMeterDataIotApi;
    @Autowired
    public GasMeterBookRecordApi bookRecordApi;
    @Autowired
    public ReadMeterDataErrorApi errorApi;
    @Autowired
    public ReadMeterLatestRecordApi latestApi;
    @Autowired
    public ReadMeterPlanScopeApi scopeApi;
    @Autowired
    public ReadMeterPlanApi planApi;
    @Autowired
    public ReadMeterDataErrorApi readMeterDataErrorApi;
    @Autowired
    private ExceptionRuleBizApi exceptionRuleBizApi;
    @Autowired
    public ReadMeterMonthGasApi readMeterMonthGasApi;
    @Autowired
    private AttachmentApi attachmentApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    public CalculateApi calculateApi;
    @Autowired
    public GasMeterBizApi gasMeterBizApi;

    @Override
    public R<List<ReadMeterDataErrorSaveDTO>> importExcel(MultipartFile simpleFile, String readTime) {
        List<ReadMeterDataUpdateDTO> successList = new ArrayList<>();
        //解析excel获取数据list
        Map<Integer,ReadMeterData> dataMap = readMeterDataApi.importReadMeterData(simpleFile).getData();
        List<ReadMeterData> readMeterDataList = new ArrayList<>( dataMap.values());
        readMeterDataList = readMeterDataList.stream().filter(item -> null!=item).collect(Collectors.toList());
        //数据库获取抄表数据
        List<ReadMeterData> dataList = readMeterDataApi.getMeterDataByCode(readMeterDataList,null).getData();
        Map<String,ReadMeterData> map1 = getReadMeterMap(dataList);
        //excel获取抄表数据
        Map<String,ReadMeterData> importMap = getReadMeterMap(readMeterDataList);
        //校验唯一性；
        InputStream inputStream = null;
        try {
            inputStream = simpleFile.getInputStream();
            Workbook workBook = new HSSFWorkbook(inputStream);
            Sheet sheet = workBook.getSheetAt(0);
            //int lastRow = sheet.getLastRowNum();
            Boolean isErr = checkExcel(readTime, dataMap, map1, workBook, sheet);
            if(isErr){
                return getErrorUrl(workBook);
            }else{
                return updateSuccessList(successList, dataList, importMap);
            }

        } catch (IOException e) {
            log.error("导入抄表数据错误:"+e.getMessage());
            return R.fail("导入抄表数据错误");
        }
    }

    @Override
    public R<Page<ReadMeterData>> pageReadMeterData(PageParams<ReadMeterDataPageDTO> params) {
        return readMeterDataApi.pageReadMeterData(params);
    }

    @Override
    public R<List<ReadMeterDataErrorSaveDTO>> importExcelByTime(MultipartFile simpleFile, String readTime) throws IOException {
        /*Workbook workbook = readMeterDataApi.importExcelByTime(simpleFile, readTime).getData();
        if(null!=workbook){
            return getErrorUrl(workbook);
        }*/
        return null;
    }

    @Override
    public R<Page<ReadMeterData>> page(PageParams<ReadMeterDataPageDTO> params) {
        if (Objects.equals(ReadMeterStatusEnum.READ.getCode(),params.getModel().getDataStatus())){
            // 查询已超表的列表
            ReadMeterDataPageDTO model = params.getModel();
            String chargeNo = model.getCustomerChargeNo();
            String cusCode = model.getCustomerCode();
            List<CustomerGasMeterRelated> relatedList = null;
            Map<String, CustomerGasMeterRelated> map = new HashMap<>(16);
            if(!chargeNo.isEmpty()){
                CustomerGasMeterRelated related = new CustomerGasMeterRelated();
                related.setCustomerChargeNo(chargeNo);
                relatedList = customerGasMeterRelatedBizApi.queryByChargeNo(chargeNo).getData();
                List<String> codeList = relatedList.stream().map(CustomerGasMeterRelated::getGasMeterCode).collect(Collectors.toList());
                params.getModel().setGasMeterCodeList(codeList);
                map = relatedList.stream().collect(Collectors.toMap(t ->t.getGasMeterCode()+"_"+t.getCustomerCode(), Function.identity(),(v1, v2) -> v2));
            }
            R<Page<ReadMeterData>> page = readMeterDataApi.pageExistData(params);
            List<ReadMeterData> list = page.getData().getRecords();
            if(null==relatedList){
                if(!list.isEmpty()){
                    List<String> codeList = list.stream().map(ReadMeterData::getGasMeterCode).collect(Collectors.toList());
                    relatedList = customerGasMeterRelatedBizApi.findListByCode(codeList).getData();
                    map = relatedList.stream().collect(Collectors.toMap(t ->t.getGasMeterCode()+"_"+t.getCustomerCode(), Function.identity(),(v1, v2) -> v2));
                }
            }
            for(ReadMeterData item:list){
                String code = item.getGasMeterCode()+"_"+item.getCustomerCode();
                CustomerGasMeterRelated related = map.get(code);
                if(null!=related){
                    item.setCustomerChargeNo(related.getCustomerChargeNo());
                }
            };
            return page;
        }
        return readMeterDataApi.pageReadMeterData(params);
    }

    @Override
    public R<ReadMeterData> costSettlement(ReadMeterData data) {
        data = readMeterDataApi.costSettlement(data).getData();
        List<ReadMeterData> resultList = calculateApi.settlement(Arrays.asList(data), 1).getData();
        if(resultList.size()>0){
            return R.success(resultList.get(0));
        }
        return null;
    }

    @Override
    public R<Boolean> isFinished(CustomerGasDto gasMeter) {
        return readMeterDataApi.isFinished(gasMeter);
    }

    public R<List<ReadMeterDataErrorSaveDTO>> updateSuccessList(List<ReadMeterDataUpdateDTO> successList, List<ReadMeterData> dataList, Map<String, ReadMeterData> importMap) {
        List<ReadMeterDataUpdateDTO> updateList = getReadMeterDataUpdateList(dataList, importMap);
        Map<String, BigDecimal> historyMap = readMeterDataApi.getHistory(updateList).getData();
        Map<Long,Integer> countMap = new HashMap<>();
        //校验用气量是否异常，异常则导入失败
        List<Long> gasTypeIds = updateList.stream().map(ReadMeterDataUpdateDTO :: getUseGasTypeId).distinct().collect(Collectors.toList());
        List<ExceptionRuleConf> exceptlist = exceptionRuleBizApi.queryByGasTypeId(gasTypeIds).getData();
        Map<Long,ExceptionRuleConf> map = exceptlist==null?
                new HashMap<>():exceptlist.stream().collect(Collectors.toMap(ExceptionRuleConf::getUseGasTypeId, a -> a,(k1, k2)->k1));
        checkData(successList, updateList, historyMap, countMap, map);
        Boolean bool = readMeterDataApi.updateBatch(successList).getData();
        if(bool){
            Map<String,Integer> map1 = new HashMap<>();
            successList.stream().forEach(item ->{
                String key = item.getBookId().toString()+"_"+item.getPlanId().toString();
                if(map1.containsKey(key)){
                    Integer value = map1.get(key)+1;
                    map1.put(key, value);
                }else {
                    map1.put(key, 1);
                }
            });
            List<ReadMeterPlanScopeUpdateDTO> updateDTOList = new ArrayList<>();
            for(Map.Entry<String,Integer> m:map1.entrySet()){
                String key[] = m.getKey().split("_");
                Long bookId = Long.valueOf(key[0]);
                Long planId = Long.valueOf(key[1]);
                ReadMeterPlanScope plan = new ReadMeterPlanScope();
                plan.setBookId(bookId).setReadmeterPlanId(planId);
                List<ReadMeterPlanScope> list = scopeApi.query(plan).getData();
                if(list.size()>0){
                    ReadMeterPlanScope data = list.get(0);
                    ReadMeterPlanScopeUpdateDTO dto = BeanUtil.toBean(data, ReadMeterPlanScopeUpdateDTO.class);
                    dto.setId(data.getId());
                    Integer nowCount = dto.getReadMeterCount();
                    Integer newCount = nowCount+m.getValue();
                    if(newCount <= dto.getTotalReadMeterCount()){
                        dto.setReadMeterCount(newCount);
                    }
                    updateDTOList.add(dto);
                }
            }
            Map<Long, Long> planCountMap = updateDTOList.stream().collect(Collectors.groupingBy(ReadMeterPlanScopeUpdateDTO::getReadmeterPlanId, Collectors.counting()));
            List<Long> planIds = new ArrayList<>(planCountMap.keySet());
            List<ReadMeterPlan> planList = planApi.queryList(planIds).getData();
            List<ReadMeterPlanUpdateDTO> updateplanList = planList.stream().map(dto ->{
                ReadMeterPlanUpdateDTO updateDTO = BeanUtil.toBean(dto, ReadMeterPlanUpdateDTO.class);
                updateDTO.setId(dto.getId());
                Integer nowCount = dto.getReadMeterCount();
                Integer newCount = nowCount+planCountMap.get(dto.getId()).intValue();
                if(newCount <= dto.getTotalReadMeterCount()){
                    updateDTO.setReadMeterCount(newCount);
                }
                return updateDTO;
            }).collect(Collectors.toList());
            scopeApi.updateBatch(updateDTOList);
            planApi.updateBatch(updateplanList);
        }
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
    public void checkData(List<ReadMeterDataUpdateDTO> successList, List<ReadMeterDataUpdateDTO> updateList, Map<String, BigDecimal> historyMap, Map<Long, Integer> countMap, Map<Long, ExceptionRuleConf> map) {
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
                successList.add(dto);
            }
        });
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

    /**
     * 上传错误文件并返回url
     * @param workBook
     * @return
     * @throws IOException
     */
    public R<List<ReadMeterDataErrorSaveDTO>> getErrorUrl(Workbook workBook) throws IOException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        workBook.write(bos);
        byte[] barray=bos.toByteArray();
        InputStream is=new ByteArrayInputStream(barray);
        MultipartFile multipartFile = new MockMultipartFile("readMeterErr.xlsx","readMeterErr.xlsx","readMeterErr.xlsx",is);
        R<AttachmentDTO> result = attachmentApi.upload(multipartFile,true,null, null,BizTypeEnum.TEMPORARY_DOCUMENTS.getCode());
        String url = result.getData().getUrl();
        return new R(SUCCESS_CODE, url, "导入错误");
    }

    /**
     * 数据校验
     * @param readTime
     * @param dataMap
     * @param map1
     * @param workBook
     * @param sheet
     * @return
     */
    public Boolean checkExcel(String readTime, Map<Integer, ReadMeterData> dataMap, Map<String, ReadMeterData> map1, Workbook workBook, Sheet sheet) {
        String[] time = readTime.split("-");
        Integer year = Integer.valueOf(time[0]);
        Integer month = Integer.valueOf(time[1]);
        Boolean isErr = true;
        for(Row row : sheet){
            Boolean bool= isRowEmpty(row);
            if(!bool){
                int num = row.getRowNum()+1;
                if(num>1){
                    ReadMeterData dto = dataMap.get(num);
                    String code = dto.getGasMeterCode();

                    BigDecimal currentTotalGas = dto.getCurrentTotalGas();
                    BigDecimal lastTotalGas = dto.getLastTotalGas();
                    BigDecimal monthUseGas = dto.getMonthUseGas();
                    Boolean codeBool = code==null || "".equals(code);
                    Boolean currentTotalGasBool = currentTotalGas==null;
                    Boolean monthUseGasBool = monthUseGas==null;
                    if(codeBool){
                        Cell cell = getCell(row, 2);
                        setStyle(workBook, sheet, cell,"气表编码不能为空");
                    }
                    if(currentTotalGasBool){
                        Cell cell = getCell(row, 5);
                        setStyle(workBook, sheet, cell,"本期止数不能为空");
                    }
                    if(currentTotalGasBool){
                        Cell cell = getCell(row, 7);
                        setStyle(workBook, sheet, cell,"抄表时间不能为空");
                    }
                    if(!codeBool){
                        String key = code+"_"+year+"_"+month;
                        ReadMeterData data = map1.get(key);
                        if(null==data){
                            Cell cell = getCell(row, 2);
                            String datetime = year+"年"+month+"月";
                            setStyle(workBook, sheet, cell,"该气表下没有找到"+datetime+"的抄表计划或该气表已完成"+datetime+"抄表");
                        }else{
                            if(!currentTotalGasBool && !monthUseGasBool){
                                //BigDecimal lastTotalGas = data.getLastTotalGas()==null? new BigDecimal(0):dto.getLastTotalGas();//上期止数
                                if(currentTotalGas.compareTo(lastTotalGas)==-1){
                                    Cell cell = getCell(row, 5);
                                    setStyle(workBook, sheet, cell,"本期止数不能小于上期止数");
                                }else{
                                    isErr =false;
                                }
                            }else{
                                isErr =true;
                            }
                        }
                    }else{
                        isErr =true;
                    }
                }
            }
        }
        return isErr;
    }

    /**
     * 获取抄表数据
     * @param dataList
     * @return
     */
    public Map<String, ReadMeterData> getReadMeterMap(List<ReadMeterData> dataList) {
        return dataList.size() == 0 ? new HashMap<>() : dataList.stream().collect(
                Collectors.toMap(meter -> meter.getGasMeterCode() + "_" + meter.getReadMeterYear() + "_" + meter.getReadMeterMonth(), a -> a, (k1, k2) -> k1));
    }

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
     * 获取年份
     * @param dataList
     * @return
     */
    public List<Integer> getYears(List<ReadMeterDataUpdateDTO> dataList) {
        List<Integer> yearList = new ArrayList<>();
        dataList.stream().forEach(data -> {
            Integer year = data.getReadMeterYear();
            Integer month = data.getReadMeterMonth();
            yearList.add(year);
            Integer lastYear  =year-1;
            if(month<4 && !yearList.contains(lastYear)){
                yearList.add(lastYear);
            }
        });
        return yearList;
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

    public boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = getCell(row, c);
            if (cell != null && cell.getCellType() != BLANK){
                return false;
            }
        }
        return true;
    }


}