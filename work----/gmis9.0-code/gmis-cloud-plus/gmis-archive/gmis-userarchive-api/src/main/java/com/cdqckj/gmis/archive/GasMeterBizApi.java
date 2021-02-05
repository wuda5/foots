package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.devicearchive.dto.*;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.vo.FactoryAndVersion;
import com.cdqckj.gmis.devicearchive.vo.GasmeterPoi;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasMeter", qualifier = "gasMeterbizapi")
public interface GasMeterBizApi {

    @RequestMapping(method = RequestMethod.POST)
    R<GasMeter> save(@RequestBody GasMeterSaveDTO saveDTO);

    @PostMapping("/addGasMeterList")
    R<List<GasMeter>> addGasMeterList(@RequestBody List<GasMeter> gasMeterList);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)

    R<GasMeter> update(@RequestBody GasMeterUpdateDTO updateDTO);

    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<GasMeterUpdateDTO> list);

    @PostMapping("/clearMeter")
    R<Boolean> clearMeter(@RequestBody GasMeterUpdateDTO updateDTO);

    @PostMapping("/updateByCode")
    public R<Boolean> updateByCode(@RequestBody GasMeter gasMeter);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GasMeter>> page(@RequestBody PageParams<GasMeterPageDTO> params);


    @ApiOperation(value = "根据客户编号查询表具信息")
    @GetMapping("/findGasMeterByCustomercode")
    R<List<GasMeter>> findGasMeterByCustomerCode(@RequestParam("customerCode") String customerCode);

    @ApiOperation(value = "分页查询气表信息")
    @PostMapping ("/findGasMeterPage")
    R<Page<PageGasMeter>> findGasMeterPage(@RequestBody GasMeterVo params);
    /**
     * 获取调价补差气表分页信息
     * @param params
     * @return
     */
    @ApiOperation(value = "获取调价补差气表分页信息")
    @PostMapping ("/findAdjustPriceGasMeterPage")
    R<Page<PageGasMeter>> findAdjustPriceGasMeterPage(@RequestBody GasMeterVo params);
    /*删除气表信息* */
    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    @PostMapping(value = "/importdiy22222", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<List<GasmeterPoi>> importExcel(@RequestPart(value = "file") MultipartFile file) throws Exception;

    @GetMapping("/findgsaMeterByCode")
    R<GasMeter> findGasMeterByCode(@RequestParam(value = "gascode")  String gascode);

    @GetMapping("/findgsaMeterBygasCode")
    R<PageGasMeter> findgsaMeterBygasCode(@RequestParam(value = "gasMeterCode")  String gasMeterCode);

    @PostMapping("/query")
    R<List<GasMeter>> query(@RequestBody GasMeter gasMeter);

    @GetMapping("/findGasMeterByGasMeterNumber/{gasMeterNumber}")
    R<GasMeter> findGasMeterByGasMeterNumber(@PathVariable(value = "gasMeterNumber") String gasMeterNumber);
    @ApiOperation(value = "根据厂家、版号、型号、表身号查询气表信息")
    @PostMapping("/getMeterByBodyNoFactory")
    R<GasMeter> getMeterByBodyNoFactory(@RequestBody GasMeter gasMeter);
    @PostMapping("/findGasMeterNumber")
    R<Integer> findGasMeterNumber(@RequestBody FactoryAndVersion factoryAndVersion);

    @PostMapping(value = "/exportCombobox")
    Response exportCombobox(@RequestBody @Validated PageParams<GasMeterPageDTO> params);
    @PostMapping(value = "/saveList")
    R<GasMeter> saveList(@RequestBody List<GasMeterSaveDTO> list);

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody GasMeterUpdateDTO gasMeterUpdateDTO);
    @ApiOperation(value = "根据气表编号是否存在")
    @PostMapping(value = "/checkByGasCode")
    R<Boolean> checkByGasCode(@RequestBody GasMeterUpdateDTO gasMeterUpdateDTO);

    @GetMapping(value = "/gasMeterCusInfo")
    R<HashMap<String,Object>> findGasMeterInfoByCode(@RequestParam("gascode") String gascode);

    @ApiOperation(value = "通气")
    @PostMapping("/ventilation")
    public R<GasMeter> ventilation(@RequestBody GasMeter gasMeter);

    @ApiOperation(value = "下拉框级联导入开户模板")
    @PostMapping(value = "/exportCascadeTemplate")
    Response exportCascadeTemplate(@RequestBody @Validated PageParams<GasMeterPageDTO> params) throws Exception;

    @GetMapping(value = "/queryMeterList")
    R<List<GasMeter>> queryMeterList(@RequestParam(value = "meterNoList") List<String> meterNoList);

    @GetMapping("/findMeterByNumber")
    R<GasMeter> findMeterByNumber(@RequestParam("number") String number);

    /**
     * 获取表具详情信息
     * @author hc
     * @param gasMeterCode
     * @return
     */
    @GetMapping("/findGasMeterInfoByCodeEx")
    R<GasMeterExVo> findGasMeterInfoByCodeEx(@RequestParam("gasMeterCode") String gasMeterCode);

    /**
     * 通过表身号查询有效状态的表具信息
     *
     * @param gasMeterNumber 表身号
     * @return
     */
    @GetMapping("/findEffectiveMeterByNumber")
    R<GasMeter> findEffectiveMeterByNumber(@RequestParam("gasMeterNumber") String gasMeterNumber);

    /**
     * 根据档案号获取气表配置（厂家，版号，型号）信息
     *
     * @param  gasMeterCode
     * @return 表具信息
     */
    @GetMapping("/findGasMeterConfInfoByCode")
    R<GasMeterConfDTO> findGasMeterConfInfoByCode(@RequestParam("gasMeterCode") String gasMeterCode);

    @GetMapping("/findGasMeterListBygasCode")
    R<List<PageGasMeter>> findGasMeterListBygasCode(@RequestParam("customerCode") String customerCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/12 10:42
    * @remark 通过表身号查找没有拆表的表具信息
    */
    @GetMapping("/findOnLineMeterByNumber")
    R<GasMeter> findOnLineMeterByNumber(@RequestParam("number") String number);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 14:44
    * @remark 获取统计的发卡的表具的数量
    */
    @PostMapping("/sts/sendCardNum")
    R<Long> stsSendCardNum(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 9:45
    * @remark 统计每个类型的表具的数量
    */
    @PostMapping("/sts/gasMeterType")
    R<List<StsInfoBaseVo<String, Long>>> stsGasMeterType(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 10:35
    * @remark 统计厂家的数量
    */
    @PostMapping("/sts/stsFactory")
    R<List<StsInfoBaseVo<String, Long>>> stsFactory(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 13:48
    * @remark 统计表具的状态 每个状态表具的数量
    */
    @PostMapping("/sts/stsStatus")
    R<List<StsInfoBaseVo<Integer, Long>>> stsStatus(@RequestBody StsSearchParam stsSearchParam);

    /**
     * 校验表身号是否存在
     * @param bodyNumber
     * @param fachtoryId
     * @return
     */
    @PostMapping("/isExistsBodyNumber")
    R<Boolean> isExistsBodyNumber(@RequestParam("fachtoryId") Long fachtoryId, @RequestParam("bodyNumber") String bodyNumber);

}
