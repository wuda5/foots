package com.cdqckj.gmis.controller;

import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.entity.dto.GasMeterBindDTO;
import com.cdqckj.gmis.entity.dto.GasMeterFindBindDTO;
import com.cdqckj.gmis.entity.vo.GasMeterInfoVO;
import com.cdqckj.gmis.service.CusBizGasService;
import com.cdqckj.gmis.userarchive.dto.CustomerGasInfoDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.utils.I18nUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/cusBiz")
@Api(value = "cusBiz", tags = "客户业务相关")
public class CusBizController {

    @Autowired
    private I18nUtil i18nUtil;

    @Autowired
    private CusBizGasService cusBizGasService;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private CustomerBizApi customerBizApi;

    /**
     * 获取表具详情
     * @auther hc
     * @date 2020/10/15
     * @param gascode
     * @return
     */
    @GetMapping("/gasMeter/{gascode}")
    @ApiOperation("获取表具详情")
    public R<HashMap<String,Object>> findGasMeterByCode(@PathVariable(value = "gascode")  String gascode) {


        return cusBizGasService.findGasMeterByCode(gascode);
    }

    /**
     * 根据缴费编号获取表具信息
     * @auther hc
     * @date 2020/11/23
     * @param meterChargeNos 燃气缴费编号集
     * @return
     */
    @ApiOperation("根据缴费编号获取表具信息")
    @GetMapping("/gasMeter")
    public R<List<CustomerGasInfoDTO>> findGasMeters(@RequestParam("meterChargeNos") ArrayList<String> meterChargeNos){

        List<CustomerGasInfoDTO> data = cusBizGasService.findGasMeters(meterChargeNos);
        if(null == data){
            return R.fail("获取数据异常");
        }
        return R.success(data);
    }

    /**
     * 获取需绑定的表具列表
     * @param bindDTO
     * @return
     */
    @ApiOperation("获取需绑定的表具列表")
    @GetMapping("/gasMeter/binds")
    @Deprecated
    public R<List<GasMeterInfoVO>> findNeedBindGasMeters(GasMeterFindBindDTO bindDTO){
        //1、验证当前客户是否在燃气公司内
        Customer querData = new Customer();
        querData.setIdCard(bindDTO.getTargetIdCard());
        querData.setDeleteStatus(0);
        R<List<Customer>> queryR = customerBizApi.query(querData);
        if(queryR.getIsError()){
            return R.fail(i18nUtil.getMessage(MessageConstants.SYS_SERVICE_FAIL,"调用客户档案服务出错"));
        }
        if(CollectionUtils.isEmpty(queryR.getData())){
            return R.fail("该账户不存在,请合适");
        }
        Customer targetCus = queryR.getData().get(0);

        List<GasMeterInfoVO> data = cusBizGasService.findNeedBindGasMeters(targetCus);
        if(null== data){
            return R.fail("获取数据异常");
        }



        return R.success(data);
    }

    /**
     * 同步缴费编号数据
     * @param gasMeterBindDTO
     * @return
     */
    @ApiOperation("同步/获取 缴费编号 数据")
    @PostMapping("/gasMeter/binds")
    public R<List<CustomerGasInfoDTO>> bindGasMeters(@RequestBody GasMeterBindDTO gasMeterBindDTO){
        List<CustomerGasInfoDTO> data = cusBizGasService.bindGasMeters(gasMeterBindDTO);
        if(null == data){
            return R.fail("绑定失败");
        }
        return R.success(data);
    }

    /**
     * 解绑代缴燃气表
     * @auther hc
     * @param binId 绑定关联id
     * @return
     */
    @ApiOperation("解绑代缴燃气表")
    @GetMapping("/gasMeter/unBind")
    @ApiImplicitParam(name = "binId",value = "绑定关联id",required = true)
    @Deprecated
    public R<Boolean> unBindGasMeters(@RequestParam("binId") @NotNull Long binId){

        Boolean data = cusBizGasService.unBindGasMeters(binId);
        if(!data){
            return R.fail("解绑定失败");
        }
        return R.success(true);
    }
}
