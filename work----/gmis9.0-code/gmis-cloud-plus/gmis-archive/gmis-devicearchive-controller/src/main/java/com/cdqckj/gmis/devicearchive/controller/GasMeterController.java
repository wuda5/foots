package com.cdqckj.gmis.devicearchive.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.ExcelCascadeSelectorDTO;
import com.cdqckj.gmis.base.utils.ExcelCascadeUtils;
import com.cdqckj.gmis.base.utils.ExcelSelectortDTO;
import com.cdqckj.gmis.base.utils.ExcelUtils;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.devicearchive.dto.*;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterDirectionEnum;
import com.cdqckj.gmis.devicearchive.service.GasMeterConfService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.devicearchive.vo.FactoryAndVersion;
import com.cdqckj.gmis.devicearchive.vo.GasmeterPoi;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeter")
@Api(value = "GasMeter", tags = "气表信息")
@PreAuth(replace = "gasMeter:")
public class GasMeterController extends SuperController<GasMeterService, Long, GasMeter, GasMeterPageDTO, GasMeterSaveDTO, GasMeterUpdateDTO> {
    @Autowired
    private GasMeterService gasMeterService;

    @Autowired
    private GasMeterConfService gasMeterConfService;

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

    @ApiOperation(value = "导出动态下拉框级联的Excel模板")
    @RequestMapping(value = "/exportCascadeTemplate", method = RequestMethod.POST, produces = "application/octet-stream")
    @SysLog("'导出动态下拉框级联的Excel模板:'.concat(#params.map[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    @Override
    public void exportCascadeTemplate(@RequestBody PageParams<GasMeterPageDTO> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ExcelCascadeSelectorDTO> excelCascadeSelectors = params.getCascadeSelectors();
        int targetSheetIndex = params.getTargetSheetIndex();
        List<GasMeter> dataList = new ArrayList<>();
        Class clazz = getaClass();
        exportCascadeExcel(dataList, excelCascadeSelectors, clazz, response, targetSheetIndex);
    }

    public void exportCascadeExcel(List<GasMeter> dataList, List<ExcelCascadeSelectorDTO> excelCascadeSelectors, Class clazz, HttpServletResponse response, int targetSheetIndex) {
        ExcelCascadeUtils.exportCascadeExcel2(dataList, excelCascadeSelectors, clazz,"", response, targetSheetIndex);
    }

    @ApiOperation(value = "导入Excel")
    @PostMapping(value = "/importdiy22222")
    @SysLog(value = "'导入Excel:' + #simpleFile?.originalFilename", request = false)
    //@PreAuth("hasPermit('{}import')")
    public R<List<GasmeterPoi>> importExcelDiy(@RequestParam(value = "file") MultipartFile simpleFile, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        List<GasmeterPoi> list = getDataList1(simpleFile, request);
//        List<GasMeter> gasMeters=getEntityList(list);;//得到的集和
        if(list.size()==0){
            return R.fail("请在Excel中添加你需要上传数据");
        }
        for (GasmeterPoi gasMeter : list) {
            if(!StringUtils.isEmpty(gasMeter.getGasMeterNumber())){
                gasMeter.setGasMeterNumber(new BigDecimal(gasMeter.getGasMeterNumber()).toPlainString());
            }
        }
        R<List<GasmeterPoi>> r = R.success(list);
        return r;
    }

    public List<GasmeterPoi> getDataList1(MultipartFile simpleFile, HttpServletRequest request) throws Exception {
        ImportParams params = new ImportParams();
        InputStream stream = removeEmptyLines(simpleFile);
        params.setTitleRows(StrUtil.isEmpty(request.getParameter("titleRows")) ? 0 : Convert.toInt(request.getParameter("titleRows")));
        params.setHeadRows(StrUtil.isEmpty(request.getParameter("headRows")) ? 1 : Convert.toInt(request.getParameter("headRows")));
        return ExcelImportUtil.importExcel(stream, GasmeterPoi.class, params);
    }


    @ApiOperation(value = "根据客户编号查询气表详情信息")
    @GetMapping("/findGasMeterByCustomercode")
    public R<List<GasMeter>> findGasMeterByCustomerCode(@RequestParam("customerCode") String customerCode){
        return R.success(baseService.findGasMeterByCustomerCode(customerCode));
    }

    @ApiOperation(value = "根据客户编号查询气表详情信息，连表查询")
    @GetMapping("/findGasMeterListBygasCode")
    public R<List<PageGasMeter>> findGasMeterListBygasCode(@RequestParam("customerCode") String customerCode){
        return R.success(baseService.findGasMeterListBygasCode(customerCode));
    }

    @ApiOperation(value = "查询气表信息分页列表")
    @PostMapping ("/findGasMeterPage")
    public R<IPage<PageGasMeter>> findGasMeterPage(@RequestBody GasMeterVo params){

        // remove by hc
        //return  R.success(baseService.findGasMeterPage(params));

        return  R.success(baseService.findGasMeterPageEx(params));
    }

    @ApiOperation(value = "获取调价补差气表分页信息")
    @PostMapping ("/findAdjustPriceGasMeterPage")
    public R<IPage<PageGasMeter>> findAdjustPriceGasMeterPage(@RequestBody GasMeterVo params){
        return  R.success(baseService.findAdjustPriceGasMeterPage(params));
    }

    @ApiOperation(value = "根据气表code查询气表")
    @GetMapping("/findgsaMeterByCode")
    public R<GasMeter> findGasMeterByCode(@RequestParam(value = "gascode") String gascode){
        return R.success(baseService.findGasMeterByCode(gascode));
    }

    @ApiOperation(value = "根据气表gasode查询气表")
    @GetMapping("/findgsaMeterBygasCode")
    public R<PageGasMeter> findGasMeterBygasCode(@RequestParam(value = "gasMeterCode") String gasMeterCode){
        return R.success(baseService.findGasMeterBygasCode(gasMeterCode));
    }
    @ApiOperation(value = "新增气表信息")
    @PostMapping("/addGasMeterList")
    public R<List<GasMeter>> addGasMeterList(@RequestBody List<GasMeter> gasMeterList){
        return baseService.addGasMeterList(gasMeterList);
    }
    @ApiOperation(value = "根据表身号查询气表")
    @GetMapping("/findGasMeterByGasMeterNumber/{gasMeterNumber}")
    public R<GasMeter> findGasMeterByGasMeterNumber(@PathVariable(value = "gasMeterNumber") String gasMeterNumber){
        return R.success(baseService.findGasMeterByGasMeterNumber(gasMeterNumber));
    }
    @ApiOperation(value = "根据厂家、版号、型号、表身号查询气表信息")
    @PostMapping("/getMeterByBodyNoFactory")
    public R<GasMeter> getMeterByBodyNoFactory(@RequestBody GasMeter gasMeter){
        return R.success(baseService.getMeterByBodyNoFactory(gasMeter));
    }
    @ApiOperation(value = "查询表身号")
    @PostMapping("/findGasMeterNumber")
    R<Integer> findGasMeterNumber(@RequestBody FactoryAndVersion factoryAndVersion){
        return R.success(baseService.findGasMeterNumber(factoryAndVersion));
    }
    @Override
    public void exportExcel(HttpServletResponse response, List<ExcelSelectortDTO> selectors, List<GasMeter> dataList, Class clazz) {
        for (GasMeter gasMeter : dataList) {
            if(GasMeterDirectionEnum.RIGHT.getCode().equals(gasMeter.getDirection())){
                gasMeter.setDirection(GasMeterDirectionEnum.RIGHT.getDesc());
            }else  if(GasMeterDirectionEnum.LEFT.getCode().equals(gasMeter.getDirection())){
                gasMeter.setDirection(GasMeterDirectionEnum.LEFT.getDesc());
            }
        }
        ExcelUtils.exportExcel(dataList, selectors,  "", "", clazz, "", response);
    }

    @ApiOperation(value = "根据气表编码更新气表信息")
    @PostMapping("/updateByCode")
    public R<Boolean> updateByCode(@RequestBody GasMeter gasMeter){
        return baseService.updateByCode(gasMeter);
    }

    @ApiOperation(value = "通气")
    @PostMapping("/ventilation")
    public R<GasMeter> ventilation(@RequestBody GasMeter gasMeter){
        return baseService.ventilation(gasMeter);
    }

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody GasMeterUpdateDTO gasMeterUpdateDTO){
        return R.success(baseService.check(gasMeterUpdateDTO));
    }
    @ApiOperation(value = "根据气表编号是否存在")
    @PostMapping(value = "/checkByGasCode")
    R<Boolean> checkByGasCode(@RequestBody GasMeterUpdateDTO gasMeterUpdateDTO){
        return R.success(baseService.checkByGasCode(gasMeterUpdateDTO));
    }

    @GetMapping(value = "/gasMeterCusInfo")
    public R<HashMap<String,Object>> findGasInfo(@RequestParam("gascode") String gascode){
        return R.success(gasMeterService.findGasInfo(gascode));
    }

    @GetMapping(value = "/queryMeterList")
    public R<List<GasMeter>> queryMeterList(@RequestParam(value = "meterNoList") List<String> meterNoList){
        return baseService.queryMeterList(meterNoList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/13 16:07
     * @remark 查询表具档案
     */
    @GetMapping("/findMeterByNumber")
    R<GasMeter> findMeterByNumber(@RequestParam("number") String number){

        GasMeter gasMeter = gasMeterService.findMeterByNumber(number);
        return R.success(gasMeter);
    }


    /**
     * 获取表具详情信息
     * @author hc
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "获取表具详情信息")
    @GetMapping("/findGasMeterInfoByCodeEx")
    R<GasMeterExVo> findGasMeterInfoByCodeEx(@RequestParam("gasMeterCode") String gasMeterCode){

        GasMeterExVo gasMeterExVo = gasMeterService.findGasMeterInfoByCodeEx(gasMeterCode);

        return R.success(gasMeterExVo);
    }

    /**
     * 通过表身号查询有效状态的表具信息
     *
     * @param gasMeterNumber 表身号
     * @return 表具信息
     */
    @GetMapping("/findEffectiveMeterByNumber")
    public R<GasMeter> findEffectiveMeterByNumber(@RequestParam("gasMeterNumber") String gasMeterNumber) {
        GasMeter gasMeter = gasMeterService.findEffectiveMeterByNumber(gasMeterNumber);
        return R.success(gasMeter);
    }


    /**
     * 根据档案号获取气表配置（厂家，版号，型号）信息
     *
     * @param  gasMeterCode
     * @return 表具信息
     */
    @GetMapping("/findGasMeterConfInfoByCode")
    public R<GasMeterConfDTO> findGasMeterConfInfoByCode(@RequestParam("gasMeterCode") String gasMeterCode) {
        GasMeterConfDTO gasMeterConfDTO = gasMeterConfService.findGasMeterConfInfoByCode(gasMeterCode);
        return R.success(gasMeterConfDTO);
    }

    /**
     * 拆除表具时清空档案开户时填写的数据
     *
     * @param updateDTO 表具信息
     * @return 清空结果
     */
    @PostMapping("/clearMeter")
    public R<Boolean> clearMeter(@RequestBody @Valid GasMeterUpdateDTO updateDTO) {
        return R.success(baseService.clearMeter(updateDTO));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/12 10:42
     * @remark 通过表身号查找没有拆表的表具信息
     */
    @GetMapping("/findOnLineMeterByNumber")
    R<GasMeter> findOnLineMeterByNumber(@RequestParam("number") String number){
        GasMeter meter = baseService.findOnLineMeterByNumber(number);
        return R.success(meter);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/15 14:44
     * @remark 获取统计的发卡的表具的数量
     */
    @PostMapping("/sts/sendCardNum")
    R<Long> sendCardNum(StsSearchParam stsSearchParam) {
        Long num = baseService.sendCardNum(stsSearchParam);
        if (num == null) {
            num = 0L;
        }
        return R.success(num);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 9:45
     * @remark 统计每个类型的表具的数量
     */
    @PostMapping("/sts/gasMeterType")
    R<List<StsInfoBaseVo<String, Long>>> stsGasMeterType(@RequestBody StsSearchParam stsSearchParam){
        List<StsInfoBaseVo<String, Long>> typeList = baseService.stsGasMeterType(stsSearchParam);
        return R.success(typeList);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 10:35
     * @remark 统计厂家的数量
     */
    @PostMapping("/sts/stsFactory")
    R<List<StsInfoBaseVo<String, Long>>> stsFactory(@RequestBody StsSearchParam stsSearchParam){
        List<StsInfoBaseVo<String, Long>> factoryList = baseService.stsFactory(stsSearchParam);
        return R.success(factoryList);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 13:48
     * @remark 统计表具的状态 每个状态表具的数量
     */
    @PostMapping("/sts/stsStatus")
    R<List<StsInfoBaseVo<Integer, Long>>> stsStatus(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<Integer, Long>> stsStatusList = baseService.stsStatus(stsSearchParam);
        return R.success(stsStatusList);
    }

    /**
     * 校验表身号是否存在
     * @param bodyNumber
     * @param fachtoryId
     * @return
     */
    @PostMapping("/isExistsBodyNumber")
    public R<Boolean> isExistsBodyNumber(@RequestParam("fachtoryId") Long fachtoryId, @RequestParam("bodyNumber") String bodyNumber){
        return R.success(baseService.isExistsBodyNumber(fachtoryId, bodyNumber));
    }
}
