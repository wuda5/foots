package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.dto.UseGasTypePageDTO;
import com.cdqckj.gmis.operateparam.dto.UseGasTypeSaveDTO;
import com.cdqckj.gmis.operateparam.dto.UseGasTypeUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/useGasType", qualifier = "useGasTypeBizApi")
public interface UseGasTypeBizApi {

    /**
     * 保存用气类型信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<UseGasType> save(@RequestBody UseGasTypeSaveDTO saveDTO);

    /**
     * 更新用气类型信息
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<UseGasType> update(@RequestBody UseGasTypeUpdateDTO updateDTO);

    /**
     * 删除用气类型信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 根据用气类型id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<UseGasType> get(@PathVariable(value = "id") Long id);

    /**
     * 分页查询用气类型信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<UseGasType>> page(@RequestBody PageParams<UseGasTypePageDTO> params);

    @PostMapping("/queryList")
     R<List<UseGasType>> queryList(@RequestParam("ids[]") List<Long> ids);


    @PostMapping("/query")
     R<List<UseGasType>> query(@RequestBody UseGasType data);

    @GetMapping(value = "/getUseGasType/{useGasTypeName}")
    public R<UseGasType> getUseGasType(@PathVariable(value = "useGasTypeName") String useGasTypeName);

    @GetMapping(value = "/queryRentUseGasType")
    UseGasType queryRentUseGasType();

    @GetMapping(value = "/queryUseGasTypeAndPrice")
    R<UseGasTypeVO> queryUseGasTypeAndPrice(@RequestParam("id") Long id);

    @ApiOperation(value = "检测用气类型名称是否有重复,true代表重复", notes = "检测用气类型名称是否有重复,true代表重复")
    @GetMapping("/checkUseGasType/{useGasTypeName}")
    R<Boolean> checkUseGasType(@RequestParam(value = "id",required = false) Long id, @PathVariable(value = "useGasTypeName") String useGasTypeName);

}
