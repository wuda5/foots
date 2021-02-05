package com.cdqckj.gmis.archive;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoPageDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import com.cdqckj.gmis.log.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasMeterInfo", qualifier = "GasMeterInfoBizApi")
public interface GasMeterInfoBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasMeterInfo> save(@RequestBody GasMeterInfoSaveDTO saveDTO);

    @PostMapping(value = "/saveList")
     R<GasMeterInfo> saveList(@RequestBody List<GasMeterInfoSaveDTO> list);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)

    R<GasMeterInfo> update(@RequestBody GasMeterInfoUpdateDTO updateDTO);

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
    R<Page<GasMeterInfo>> page(@RequestBody PageParams<GasMeterInfoPageDTO> params);

    /**
     * 根据气表编号查询
     * @param gasMeterCode
     * @return
     */
    @GetMapping(value = "/queryByGasMeterCode")
    R<GasMeterInfo> getByGasMeterCode(@RequestParam(value = "gasMeterCode") String gasMeterCode);

    /**
     * 根据气表code and customer code 查询
     * @param gasMeterCode
     * @return
     */
    @GetMapping(value = "/getByMeterAndCustomerCode")
     R<GasMeterInfo> getByMeterAndCustomerCode(@RequestParam(value = "gasMeterCode")String gasMeterCode,
                                                     @RequestParam(value = "customerCode")String customerCode
    );

    /**
     * 根据气表编号查询
     * @param params
     * @return
     */
    @PostMapping(value = "/query")
    R<List<GasMeterInfo>> query(@RequestBody GasMeterInfo params);

    /**
     * 根据查询--(查询户表 账号)
     * @param data
     * @return
     */
    @ApiOperation(value = "条件查询一条数据", notes = "条件查询一条数据")
    @PostMapping("/queryOne")
    @SysLog("条件查询一条数据")
    R<GasMeterInfo> queryOne(@RequestBody GasMeterInfo data);

    /**
     * 根据价格id更新
     * @param meterInfoVO
     * @return
     */
    @PostMapping(value = "/updateByPriceId")
    R<Integer> updateByPriceId(@RequestBody MeterInfoVO meterInfoVO);

    /**
     * 周期清零
     * @param meterInfoVO
     * @return
     */
    @PostMapping(value = "/updateCycleByPriceId")
    R<Integer> updateCycleByPriceId(@RequestBody MeterInfoVO meterInfoVO);
    /**
     * 根据气表编号更新气表使用情况
     * @param gasCode
     * @param gasMeterInfo
     * @return
     */
    @ApiOperation("根据气表编号更新气表使用情况")
    @PutMapping("/updateByGasMeterCode")
    public R<Boolean> updateByGasMeterCode(@RequestParam("gasCode") String gasCode, @RequestBody GasMeterInfo gasMeterInfo);
}
