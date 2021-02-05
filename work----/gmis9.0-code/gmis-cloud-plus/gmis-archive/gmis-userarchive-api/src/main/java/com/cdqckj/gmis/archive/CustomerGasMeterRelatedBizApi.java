package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.vo.BatchUserDeviceVO;
import com.cdqckj.gmis.devicearchive.vo.MeterRelatedVO;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户气表信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/customerGasMeterRelated", qualifier = "customerGasMeterRelatedBizApi")
public interface CustomerGasMeterRelatedBizApi {

    /**
     * 客户气表关联列表
     * @param params
     * @return
     */
    @PostMapping("/customerList")
    R<Page<CustomerDeviceDTO>> findLimitCustomerPage(@RequestBody CustomerDeviceVO params);

    @PostMapping("/findCustomerAndGasMeter")
    R<CustomerGasMeterRelated> findGasCodeByCode(@RequestParam("gascode")String gascode);

    @RequestMapping(method = RequestMethod.POST)
    R<CustomerGasMeterRelated> save(@RequestBody CustomerGasMeterRelatedSaveDTO customerGasMeterRelatedSaveDTO);

    @PostMapping("/findCustomerAndGasMeterList")
    R<List<CustomerGasMeterRelated>>findCustomerAndGasMeterList(@RequestBody  CustomerGasMeterRelated customerGasMeterRelated);

    @PostMapping("/updateBatchUserGas")
    R<Integer> updateBatchUserGas(@RequestBody List<BatchUserDeviceVO> userGasVo);

    @PostMapping("/findPreOpenAccount")
    R<List<CustomerGasMeterRelated>>findPreOpenAccount();

    @PostMapping("/findNormalOpenAccount")
    R<List<CustomerGasMeterRelated>>findNormalOpenAccount();

    @PostMapping("/query")
    R<List<CustomerGasMeterRelated>> query(@RequestBody CustomerGasMeterRelated data);

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO);

    @ApiOperation(value = "校验缴费编号")
    @PostMapping(value = "/checkChargeNo")
    R<Boolean> checkChargeNo(@RequestBody CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO);

    /**
     * 修改
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    R<CustomerGasMeterRelated> update(@RequestBody @Validated(SuperEntity.Update.class) CustomerGasMeterRelatedUpdateDTO updateDTO);

    /**
     * 逻辑删除表具和客户的关联关系
     *
     * @param updateDTO 更新数据DTO
     * @return 更新后数据实体
     */
    @PostMapping(value = "/deleteByCustomerAndMeter")
    R<CustomerGasMeterRelated> deleteByCustomerAndMeter(@RequestBody CustomerGasMeterRelatedUpdateDTO updateDTO);

    /**
     * 根据表具编号和客户编号查询 客户缴费编号
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    @PostMapping("/findRelatedInfo")
    R<CustomerGasMeterRelated> findRelatedInfo(@RequestParam("gasMeterCode")String gasMeterCode,@RequestParam("customerCode")String customerCode);

    /**
     * 根据 客户缴费编号 查 表具编号和客户编号查询
     * @param customerChargeNo
     * @return
     */
    @PostMapping("/findRelatedInfoByChargeNo")
    R<CustomerGasMeterRelated> findRelatedInfoByChargeNo(@RequestParam("customerChargeNo")String customerChargeNo);

    /**
     * 通过客户编号查询所有有效的关联关系和表具状态
     *
     * @param customerCode 客户编号
     * @return 关联关系列表
     */
    @GetMapping("/queryAllRelation")
    R<List<MeterRelatedVO>>queryAllRelation(@RequestParam("customerCode")String customerCode);

    @PostMapping("/findListByCode")
    R<List<CustomerGasMeterRelated>>findListByCode(@RequestBody List<String> codeList);

    @PostMapping("/queryByChargeNo")
    R<List<CustomerGasMeterRelated>> queryByChargeNo(@RequestBody String chargeNo);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 13:42
    * @remark 统计开户信息
    */
    @PostMapping("/sts/accountNowTypeFrom")
    R<List<StsInfoBaseVo<String, Long>>> stsAccountNowTypeFrom(@RequestBody StsSearchParam stsSearchParam);
}
