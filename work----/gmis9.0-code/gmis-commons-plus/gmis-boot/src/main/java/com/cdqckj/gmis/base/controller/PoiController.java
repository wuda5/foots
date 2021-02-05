package com.cdqckj.gmis.base.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.BaseEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.*;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.utils.DateUtils;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.base.utils.ExcelLangUtils.getAnnotationMap;
import static org.apache.poi.ss.usermodel.CellType.BLANK;

/**
 * 导入导出
 *
 * @param <Entity>  实体
 * @param <PageDTO> 分页查询参数
 * @author gmis
 * @date 2020年03月07日22:02:06
 */
public interface PoiController<Entity, PageDTO> extends PageController<Entity, PageDTO> {

    /**
     * 导出Excel
     *
     * @param params   参数
     * @param request  请求
     * @param response 响应
     * @throws IOException
     */
    @SneakyThrows
    @ApiOperation(value = "导出Excel")
    @RequestMapping(value = "/export", method = RequestMethod.POST, produces = "application/octet-stream")
    @SysLog("'导出Excel:'.concat(#params.map[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    //@PreAuth("hasPermit('{}export')")
    default void exportExcel(@RequestBody @Validated PageParams<PageDTO> params, HttpServletRequest request, HttpServletResponse response) {
        IPage<Entity> page = params.getPage();
        ExportParams exportParams = getExportParams(params, page);
        Class clazz = getaClass();
        Map<String, Object> map = new HashMap<>(5);
        map.put(NormalExcelConstants.DATA_LIST, page.getRecords());
        map.put(NormalExcelConstants.CLASS, clazz);
        map.put(NormalExcelConstants.PARAMS, exportParams);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        map.put(NormalExcelConstants.FILE_NAME, fileName);
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }

    default Class getaClass() throws Exception {
        String tenant = getTenant();

        RedisService redisService = getRedisService();
        int langType = 1;
        if (redisService != null){
            Integer resultType = (Integer) redisService.get("lang" + tenant);
            if (resultType != null){
                langType = resultType;
            }
        }
        //langType = (int) getRedisService().get("lang"+tenant);//1-中文；2-英文
        return ExcelLangUtils.chooseLang(getEntityClass(),langType);
    }

    /**
     * 预览Excel
     *
     *
     *
     * @param params 预览参数
     * @return
     */
    @ApiOperation(value = "预览Excel")
    @SysLog("'预览Excel:' + (#params.map[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    @PreAuth("hasPermit('{}export')")
    default R<String> preview(@RequestBody @Validated PageParams<PageDTO> params) throws Exception {
        IPage<Entity> page = params.getPage();
        ExportParams exportParams = getExportParams(params, page);

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, getaClass(), page.getRecords());
        return success(ExcelXorHtmlUtil.excelToHtml(new ExcelToHtmlParams(workbook)));
    }

    /**
     * 使用自动生成的实体+注解方式导入 对RemoteData 类型的字段不支持，
     * 建议自建实体使用
     * wuwangqiang 为了在聚会服务中处理xxxxxxxxxxxxxxxxxxx  RequestPart！！
     * @param simpleFile 上传文件
     * @param request    请求
     * @param response   响应
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入Excel并返回实体集合对象(为了在聚会服务中处理)")
    @PostMapping(value = "/importBackList")
    @SysLog(value = "'导入Excel:' + #simpleFile?.originalFilename", request = false)
    default R<List<Entity>> importExcelBackList(@RequestPart(value = "file") MultipartFile simpleFile, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map<String, Object>> list = getDataList(simpleFile, request);
        List<Entity> readMeterDataList = getEntityList(list);
        return success(readMeterDataList);
    }


    /**
     * 使用自动生成的实体+注解方式导入 对RemoteData 类型的字段不支持，
     * 建议自建实体使用
     *
     * @param simpleFile 上传文件
     * @param request    请求
     * @param response   响应
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入Excel")
    @PostMapping(value = "/import")
    @SysLog(value = "'导入Excel:' + #simpleFile?.originalFilename", request = false)
    //@PreAuth("hasPermit('{}import')")
    default R<Boolean> importExcel(@RequestParam(value = "file") MultipartFile simpleFile, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map<String, Object>> list = getDataList(simpleFile, request);
        if (list != null && !list.isEmpty()) {
            return handlerImport(list);
        }
        return validFail("导入Excel无有效数据！");
    }

    default List<Map<String, Object>> getDataList(@RequestParam("file") MultipartFile simpleFile, HttpServletRequest request) throws Exception {
        ImportParams params = new ImportParams();
        InputStream stream = removeEmptyLines(simpleFile);
        params.setTitleRows(StrUtil.isEmpty(request.getParameter("titleRows")) ? 0 : Convert.toInt(request.getParameter("titleRows")));
        params.setHeadRows(StrUtil.isEmpty(request.getParameter("headRows")) ? 1 : Convert.toInt(request.getParameter("headRows")));
        return ExcelImportUtil.importExcel(stream, Map.class, params);
    }

    /**
     * 转换后保存
     *
     * @param list
     */
    default R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<Entity> readMeterDataList = getEntityList(list);
        Boolean bool = getBaseService().saveOrUpdateBatch(readMeterDataList);
        return bool ? R.success():R.fail("");
        //return R.successDef(null, "请在子类Controller重写导入方法，实现导入逻辑");
    }

    /**
     * 使用自动生成的实体+注解方式导入 对RemoteData 类型的字段不支持，
     * 建议自建实体使用
     *
     * @param file 上传文件
     * @param request    请求
     * @param response   响应
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入Excel,返回json")
    @PostMapping(value = "/importBackJson")
    @SysLog(value = "'导入Excel,返回json:' + #simpleFile?.originalFilename", request = false)
    @GlobalTransactional
    //@PreAuth("hasPermit('{}import')")
    default R<JSONObject> importExcelBackJsonObject(@RequestPart(value = "file") MultipartFile file, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        ImportParams params = new ImportParams();

        params.setTitleRows(StrUtil.isEmpty(request.getParameter("titleRows")) ? 0 : Convert.toInt(request.getParameter("titleRows")));
        params.setHeadRows(StrUtil.isEmpty(request.getParameter("headRows")) ? 1 : Convert.toInt(request.getParameter("headRows")));
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(file.getInputStream(), Map.class, params);

        if (list != null && !list.isEmpty()) {
            return handlerImportBackJson(list);
        }
        return validFail("导入Excel无有效数据！");
    }

    /**
     * 转换后保存
     *
     * @param list
     */
    default R<JSONObject> handlerImportBackJson(List<Map<String, Object>> list) {
        return R.successDef(null, "请在子类Controller重写导入方法，实现导入逻辑");
    }

    /**
     * 根据注解解析excel获取list
     * @param list
     * @return
     */
    default List<Entity> getEntityList(List<Map<String, Object>> list) {
        return list.stream().map((map) -> {
            Entity entity = getEntity(map);
            return entity;
        }).collect(Collectors.toList());
    }

    /**
     * 数据类型转换
     * @param value
     * @param type
     * @return
     */
    default Object getValue(String value,Class type){
        if(null==value){
            return value;
        }else{
            if(type.isEnum()){
                BaseEnum base = BaseEnum.getDescMap((BaseEnum[]) type.getEnumConstants(),value);
                return base;
            }else if(type.equals(String.class)){
                return value;
            }else if(type.equals(Integer.class)){
                return Integer.valueOf(value);
            }else if(type.equals(BigDecimal.class)){
                return new BigDecimal(value);
            }else if(type.equals(Long.class)){
                return Long.valueOf(value);
            }else if(type.equals(LocalDateTime.class)){
                //判断是否是数字
//                LocalDateTime date=LocalDateTime.of(1900,01,01,01,01,01).plusDays(Integer.parseInt(value));
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//                return LocalDateTime.parse(value,df);
            }else if(type.equals(LocalDate.class)){
                //判断是否是数字
                if(value.contains("/")){
                    value = value.replaceAll("/","-");
                }
                LocalDate date = DateUtils.StringToDate(value);//LocalDate.of(1900,01,01).plusDays(Integer.parseInt(value));
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                System.out.println();
                return date;
            }


            /*switch (type){
                case String.class:
                    return value;
                case "class java.lang.Integer":
                    return Integer.valueOf(value);
                case "class java.math.BigDecimal":
                    return new BigDecimal(value);
                case "class java.lang.Long":
                    return Long.valueOf(value);
                case "class java.time.LocalDateTime":
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    return LocalDateTime.parse(value,df);
                default:
                    return null;
            }*/
        }
        return null;
    }

    /**
     * 构建导出参数
     *
     * @param params 分页参数
     * @param page
     * @return
     */
    default ExportParams getExportParams(PageParams<PageDTO> params, IPage<Entity> page) {
        query(params, page, params.getSize() == -1 ? Convert.toLong(Integer.MAX_VALUE) : params.getSize());

        String title = params.getMap().get("title");
        String type = params.getMap().getOrDefault("type", ExcelType.XSSF.name());
        String sheetName = params.getMap().getOrDefault("sheetName", "SheetName");

        ExcelType excelType = ExcelType.XSSF.name().equals(type) ? ExcelType.XSSF : ExcelType.HSSF;
        return new ExportParams(title, sheetName, excelType);
    }

    /**
     * 根据模板导出excel
     * @param name 文件名
     * @param dataMap 导出数据
     * @param filePath 模板路径
     * @param num 标题行数
     * @param response
     */
    @ApiOperation(value = "根据模板导出excel")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST, produces = "application/octet-stream")
    @ResponseBody
    default void exportExcel(String name,Map<String, Object> dataMap,String filePath,Integer num, HttpServletResponse response) {
        TemplateExportParams params = new TemplateExportParams();
        // 标题开始行
        params.setHeadingStartRow(0);
        // 标题行数
        params.setHeadingRows(num);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        //params.setSheetName(name);
        // 获取模板文件路径
        // easypoi的API只能接收文件路径，无法读取文件流
        // 设置模板路径
        params.setTemplateUrl(filePath);
        // 获取workbook
        Workbook workbook = ExcelExportUtil.exportExcel(params, dataMap);
        // exportFileName代表导出的文件名称
        try {
            ReportUtils.exportExcel(response, workbook, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出动态生成带下拉框的Excel模板
     * @param params
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出动态生成带下拉框的Excel模板")
    @RequestMapping(value = "/exportCombobox", method = RequestMethod.POST, produces = "application/octet-stream")
    @SysLog("'导出动态生成带下拉框的Excel:'.concat(#params.map[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    default void exportCombobox(@RequestBody @Validated PageParams<PageDTO> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            List<ExcelSelectortDTO> selectors = params.getComboboxList();
            long size = params.getSize();
            List<Entity> dataList = new ArrayList<>();
            Class clazz = getaClass();
            dataList = getEntities(params, selectors, size, dataList, clazz);
            exportExcel(response, selectors, dataList, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void exportExcel(HttpServletResponse response, List<ExcelSelectortDTO> selectors, List<Entity> dataList, Class clazz) {
        ExcelUtils.exportExcel(dataList, selectors,  "", "", clazz, "", response);
    }

    /**
     * 导出动态下拉框级联的Excel模板
     * N级级联第一列不能为数字，数字项加下划线，后续业务逻辑处理自行去除下划线，其他列不用加下划线
     * @param params
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出动态下拉框级联的Excel模板")
    @RequestMapping(value = "/exportCascadeTemplate", method = RequestMethod.POST, produces = "application/octet-stream")
    @SysLog("'导出动态下拉框级联的Excel模板:'.concat(#params.map[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    default void exportCascadeTemplate(@RequestBody PageParams<PageDTO> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ExcelCascadeSelectorDTO> excelCascadeSelectors = params.getCascadeSelectors();
        int targetSheetIndex = params.getTargetSheetIndex();
        List<Entity> dataList = new ArrayList<>();
        Class clazz = getaClass();
        exportCascadeExcel(dataList, excelCascadeSelectors, clazz, response, targetSheetIndex);
    }

    default void exportCascadeExcel(List<Entity> dataList, List<ExcelCascadeSelectorDTO> excelCascadeSelectors, Class clazz, HttpServletResponse response, int targetSheetIndex) {
        ExcelCascadeUtils.exportCascadeExcel(dataList, excelCascadeSelectors, clazz,"", response, targetSheetIndex);
    }

    /**
     * 获取对象list
     * @param params
     * @param selectors
     * @param size
     * @param dataList
     * @param clazz
     * @return
     * @throws Exception
     */
    default List<Entity> getEntities(@Validated @RequestBody PageParams<PageDTO> params, List<ExcelSelectortDTO> selectors, long size, List<Entity> dataList, Class clazz) throws Exception {
        if(size!=0){
            Entity model = (Entity) BeanUtil.toBean(params.getModel(), clazz);
            QueryWrap<Entity> wrapper = Wraps.q(model);
            // 要导出的数据
            dataList = getBaseService().list(wrapper);
            /*String tenant = getTenant();
            int langType = (int) getRedisService().get("lang"+tenant);//1-中文；2-英文*/
        }
        // 表格下拉框信息
        if(selectors==null || selectors.size()==0){
            Field[] fields = clazz.getDeclaredFields();
            int fixedIndex = 0;
            for(Field field:fields){
                Excel excel = field.getAnnotation(Excel.class);
                if(excel!=null){
                    Class<BaseEnum> claz = (Class<BaseEnum>) field.getType();
                    Boolean bool = claz.isEnum();
                    if(bool){
                        BaseEnum[] objects = claz.getEnumConstants();
                        Map<String, Object> values = getAnnotationMap(excel);
                        //fixedIndex = (int) values.get("fixedIndex");
                        if(fixedIndex>-1){
                            String[] str = BaseEnum.getDescStr(objects);
                            ExcelSelectortDTO selectortDTO = new ExcelSelectortDTO(fixedIndex,fixedIndex,str);
                            selectors.add(selectortDTO);
                        }
                    }
                    fixedIndex++;
                }
            }
        }
        return dataList;
    }

    /**
     * 级联 获取对象list
     * @param params
     * @param size
     * @param dataList
     * @param clazz
     * @return
     * @throws Exception
     */
    default List<Entity> getCascadeDatas(PageParams<PageDTO> params, long size,
                                            List<Entity> dataList, Class clazz) throws Exception {
        if(size!=0){
            Entity model = (Entity) BeanUtil.toBean(params.getModel(), clazz);
            QueryWrap<Entity> wrapper = Wraps.q(model);
            // 要导出的数据
            dataList = getBaseService().list(wrapper);
            /*String tenant = getTenant();
            int langType = (int) getRedisService().get("lang"+tenant);//1-中文；2-英文*/
        }
        return dataList;
    }

    /**
     * 删除excel空行
     * @param simpleFile
     * @return
     * @throws Exception
     */
    default InputStream removeEmptyLines(MultipartFile simpleFile) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(simpleFile.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);
        //总行数
        int lastRowNum = sheet.getLastRowNum();
        for(int i =1;i<lastRowNum+1;i++){
            Row row = sheet.getRow(i);
            if (row == null){
                row = sheet.createRow(i);
            }
            Boolean bool = isRowEmpty(row);
            if(bool){
                sheet.removeRow(row);
            }
        }
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray=bos.toByteArray();
        return new ByteArrayInputStream(barray);

    }

    /**
     * 判断当前行是否为空数据
     * @param row
     * @return
     */
    default boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != BLANK){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据注解解析excel获取list
     * @param list
     * @return
     */
    default Map<Integer,Entity> getEntityMap(List<Map<String, Object>> list) {
        Map<Integer,Entity>  resultMap = new HashMap<>();
        list.stream().forEach((map) -> {
            Entity entity = getEntity(map);
            resultMap.put((Integer) map.get("lineNumber"),entity);
        });
        return resultMap;
    }

    default Entity getEntity(Map<String, Object> map) {
        Entity entity = null;
        try {
            Class clazz = getaClass();
            entity = (Entity) clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //字段类型
                String type = field.getGenericType().toString();
                Class calzz = field.getType();
                // 获取字段上的注解
                Excel excel = field.getAnnotation(Excel.class);
                if (excel != null) {
                    Map<String, Object> memberValues = getAnnotationMap(excel);
                    // 获取属性值
                    String excelName = (String) memberValues.get("name");
                    if (map.containsKey(excelName)) {
                        Object object = map.get(excelName);
                        if (null != object) {
                            Object value = getValue(String.valueOf(object), calzz);
                            field.setAccessible(true);
                            field.set(entity, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

}
