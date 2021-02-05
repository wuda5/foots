package com.cdqckj.gmis.devicearchive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedPageDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.vo.BatchUserDeviceVO;
import com.cdqckj.gmis.devicearchive.vo.MeterRelatedVO;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerGasMeterRelated")
@Api(value = "CustomerGasMeterRelated", tags = "")
//@PreAuth(replace = "customerGasMeterRelated:")
public class CustomerGasMeterRelatedController extends SuperController<CustomerGasMeterRelatedService, Long, CustomerGasMeterRelated, CustomerGasMeterRelatedPageDTO, CustomerGasMeterRelatedSaveDTO, CustomerGasMeterRelatedUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerGasMeterRelated> customerGasMeterRelatedList = list.stream().map((map) -> {
            CustomerGasMeterRelated customerGasMeterRelated = CustomerGasMeterRelated.builder().build();
            //TODO 请在这里完成转换
            return customerGasMeterRelated;
        }).collect(Collectors.toList());
        return R.success(baseService.saveBatch(customerGasMeterRelatedList));
    }
    @ApiOperation(value = "查询用户设备列表(待 添加到限购方案下的客户表具)")
    @PostMapping("/customerList")
    public R<Page<CustomerDeviceDTO>> findLimitCustomerPage(@RequestBody CustomerDeviceVO params){
        return R.success(baseService.findCustomerDevicePage(params));
    }

    @ApiOperation(value = "查询表具编号")
    @PostMapping("/findCustomerAndGasMeter")
    R<CustomerGasMeterRelated> findGasCodeByCode(@RequestParam("gascode") String gascode){
        return R.success(baseService.findOneByCode(gascode));
    }
    @ApiOperation(value = "查询客户表具列表")
    @PostMapping("/findCustomerAndGasMeterList")
    R<List<CustomerGasMeterRelated>>findCustomerAndGasMeterList(@RequestBody  CustomerGasMeterRelated customerGasMeterRelated){
        return R.success(baseService.findCustomerAndGasMeterList(customerGasMeterRelated));
    }

    @ApiOperation(value = "根据气表code批量更新")
    @PostMapping("/updateBatchUserGas")
    R<Integer> updateBatchUserGas(@RequestBody List<BatchUserDeviceVO> userGasVo) {
        return R.success(baseService.updateBatchUserDevice(userGasVo));
    }
    @ApiOperation(value = "查询预开户")
    @PostMapping("/findPreOpenAccount")
    R<List<CustomerGasMeterRelated>>findPreOpenAccount(){
        return R.success(baseService.findPreOpenAccount());
    }

    @ApiOperation(value = "查询不是预开户")
    @PostMapping("/findNormalOpenAccount")
    R<List<CustomerGasMeterRelated>>findNormalOpenAccount(){
        return R.success(baseService.findNormalOpenAccount());
    }

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO){
        return R.success(baseService.check(customerGasMeterRelatedUpdateDTO));
    }
    @ApiOperation(value = "校验缴费编号")
    @PostMapping(value = "/checkChargeNo")
    R<Boolean> checkChargeNo(@RequestBody CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO){
        return R.success(baseService.checkChargeNo(customerGasMeterRelatedUpdateDTO));
    }

    /**
     * 通过表具code和客户code逻辑删除有效的关联管理
     *
     * @param updateDTO 参数
     * @return 逻辑删除的对象
     */
    @ApiOperation(value = "通过表具code和客户code逻辑删除有效的关联管理")
    @PostMapping(value = "/deleteByCustomerAndMeter")
    @GlobalTransactional
    R<CustomerGasMeterRelated> deleteByCustomerAndMeter(@RequestBody CustomerGasMeterRelatedUpdateDTO updateDTO) {
        if (Objects.isNull(updateDTO.getCustomerCode()) || Objects.isNull(updateDTO.getGasMeterCode())) {
            return R.fail("客户编号或表具编号不能为空");
        }
        return R.success(baseService.deleteByCustomerAndMeter(updateDTO));
    }


    /**
     * 根据表具编号和客户编号查询 客户缴费编号
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    @PostMapping("/findRelatedInfo")
    public R<CustomerGasMeterRelated> findRelatedInfo(@RequestParam("gasMeterCode")String gasMeterCode,@RequestParam("customerCode")String customerCode){
        return R.success(baseService.getOne(new LbqWrapper<CustomerGasMeterRelated>()
                .eq(CustomerGasMeterRelated::getGasMeterCode,gasMeterCode)
                .eq(CustomerGasMeterRelated::getCustomerCode,customerCode)
                .eq(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue())
        ));
    }

    /**
     * 根据 客户缴费编号 查 表具编号和客户编号查询
     * @param customerChargeNo
     * @return
     */
    @PostMapping("/findRelatedInfoByChargeNo")
    public R<CustomerGasMeterRelated> findRelatedInfoByChargeNo(@RequestParam("customerChargeNo")String customerChargeNo){
        return R.success(baseService.getOne(new LbqWrapper<CustomerGasMeterRelated>()
                .eq(CustomerGasMeterRelated::getCustomerChargeNo,customerChargeNo)
                .eq(CustomerGasMeterRelated::getDataStatus,DataStatusEnum.NORMAL.getValue())
        ));
    }

    /**
     * 通过客户编号查询所有有效的关联关系和表具状态
     *
     * @param customerCode 客户编号
     * @return 关联关系列表
     */
    @GetMapping("/queryAllRelation")
    public R<List<MeterRelatedVO>>queryAllRelation(@RequestParam("customerCode")String customerCode){
        return R.success(baseService.queryAllRelation(customerCode));
    }

    @PostMapping("/findListByCode")
    R<List<CustomerGasMeterRelated>>findListByCode(@RequestBody List<String> codeList){
        return R.success(baseService.list(Wraps.<CustomerGasMeterRelated>lbQ().in(CustomerGasMeterRelated::getGasMeterCode,codeList)));
    }

    @PostMapping("/queryByChargeNo")
    R<List<CustomerGasMeterRelated>> queryByChargeNo(@RequestBody String chargeNo){
        return R.success(baseService.list(Wraps.<CustomerGasMeterRelated>lbQ().like(CustomerGasMeterRelated::getCustomerChargeNo,chargeNo)));
    };

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/15 13:42
     * @remark 统计开户信息
     */
    @PostMapping("/sts/accountNowTypeFrom")
    R<List<StsInfoBaseVo<String, Long>>> accountNowTypeFrom(StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String, Long>> stsInfoBaseVos = baseService.accountNowTypeFrom(stsSearchParam);
        return R.success(stsInfoBaseVos);
    }

}
