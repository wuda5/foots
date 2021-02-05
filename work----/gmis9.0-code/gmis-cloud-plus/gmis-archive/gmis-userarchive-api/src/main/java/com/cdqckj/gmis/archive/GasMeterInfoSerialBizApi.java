package com.cdqckj.gmis.archive;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSerialPageDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSerialSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSerialUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfoSerial;
import com.cdqckj.gmis.log.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "gasMeterInfoSerial", qualifier = "GasMeterInfoSerialBizApi")
public interface GasMeterInfoSerialBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasMeterInfoSerial> save(@RequestBody GasMeterInfoSerialSaveDTO saveDTO);

    @PostMapping(value = "/saveList")
     R<GasMeterInfoSerial> saveList(@RequestBody List<GasMeterInfoSerialSaveDTO> list);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GasMeterInfoSerial> update(@RequestBody GasMeterInfoSerialUpdateDTO updateDTO);

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
    R<Page<GasMeterInfoSerial>> page(@RequestBody PageParams<GasMeterInfoSerialPageDTO> params);

//    /**
//     * 根据气表编号查询
//     * @param gasMeterCode
//     * @return
//     */
//    @GetMapping(value = "/queryByGasMeterCode")
//    R<GasMeterInfoSerial> getByGasMeterCode(@RequestParam(value = "gasMeterCode") String gasMeterCode);


    /**
     * 根据气表编号查询
     * @param params
     * @return
     */
    @PostMapping(value = "/query")
    R<List<GasMeterInfoSerial>> query(@RequestBody GasMeterInfoSerial params);

    /**
     * 根据查询--(查询户表 账号)
     * @param data
     * @return
     */
    @ApiOperation(value = "条件查询一条数据", notes = "条件查询一条数据")
    @PostMapping("/queryOne")
    @SysLog("条件查询一条数据")
    R<GasMeterInfoSerial> queryOne(@RequestBody GasMeterInfoSerial data);



}
