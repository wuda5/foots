package com.cdqckj.gmis.biztemporary;

import cn.hutool.json.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.biztemporary.dto.EstablishAccountImportPageDTO;
import com.cdqckj.gmis.biztemporary.dto.TransferAccountDTO;
import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import com.cdqckj.gmis.biztemporary.hystrix.BusinessTemporaryBizApiFallback;
import com.cdqckj.gmis.biztemporary.vo.GasMeterTransferAccountVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallback = BusinessTemporaryBizApiFallback.class
        , path = "/bizTemporary", qualifier = "BusinessTemporaryBizApi")
public interface BusinessTemporaryBizApi {

    /**
     * 开户
     * @param gasMeter
     * @param customerCode
     * @return
     */
    @ApiOperation(value = "开户")
    @PostMapping(value = "/establishAccount")
    public R<GasMeter> establishAccount(@RequestBody GasMeter gasMeter,
                                        @RequestParam(value = "customerCode") String customerCode, @RequestParam(value = "chargeNo") String chargeNo,
                                        @RequestParam("isAddGasMeter") boolean isAddGasMeter);

    /**
     * 预开户
     * @param gasMeter
     * @param rechargeAmount
     * @return
     */
    @ApiOperation(value = "预开户")
    @PostMapping(value = "/establishAccountBeforehand")
    public R<Boolean> establishAccountBeforehand(@RequestBody GasMeter gasMeter,
                                                 @RequestParam(value = "rechargeAmount") String rechargeAmount);

    /**
     * 批量预开户
     * @param gasMeterList
     * @param rechargeAmount
     * @return
     */
    @ApiOperation(value = "批量预开户")
    @PostMapping(value = "/establishAccountBeforehandBatch")
    public R<Boolean> establishAccountBeforehandBatch(@RequestBody List<GasMeter> gasMeterList,
                                                      @RequestParam(value = "rechargeAmount") String rechargeAmount);

    /**
     *导入Excel,返回json
     * @param file 上传文件
     * @return
     */
    @ApiOperation(value = "导入Excel,返回json")
    @PostMapping(value = "/importBackJson", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<JSONObject> importExcelBackJsonObject(@RequestPart(value = "file") MultipartFile file);

    /**
     * 导入开户模板下载
     * @param templateCode
     * @return
     */
    @ApiOperation(value = "导入开户模板下载")
    @GetMapping(value = "/exportTemplateFile", produces = "application/octet-stream")
    public R<Boolean> exportTemplateFile(@RequestParam(value = "templateCode") String templateCode);

    /**
     * 组合框导入开户模板
     * @param params
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "组合框导入开户模板")
    @PostMapping(value = "/exportCombobox")
    public Response exportCombobox(@RequestBody @Validated PageParams<EstablishAccountImportPageDTO> params) throws Exception;
    /**
     * 下拉框级联导入开户模板
     * @param params
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "下拉框级联导入开户模板")
    @PostMapping(value = "/exportCascadeTemplate")
    public Response exportCascadeTemplate(@RequestBody @Validated PageParams<EstablishAccountImportPageDTO> params) throws Exception;


    /**
     * 过户验证:校验是否存在未完成的过户业务单
     * @author hc
     * @param gasMeterCode
     * @date 2020/11/07
     * @return step:业务步骤 ,flag:是否有未完成的业务,busData:业务单数据
     */
    @GetMapping("/transferAccount/check")
    R<HashMap<String,Object>> transferAccountCheck(@RequestParam("gasMeterCode") String gasMeterCode, @RequestParam("customerCode") String customerCode);

    /**
     * 过户:生成业务单
     * @author hc
     * @date 2020/11/05
     * @return
     */
    @PostMapping(value = "/transferAccount")
    R<GasmeterTransferAccount>  transferAccount(@RequestBody TransferAccountDTO transferAccountDTO);


    /**
     * 过户：缴费业务回调接口
     * @author hc
     * @date 2020/11/06
     * @param businessNo 业务单号及过户记录表id
     * @return
     */
    @GetMapping("/transferAccount/charge")
    R<GasmeterTransferAccount> transferAccountCallBack(@RequestParam("businessNo") Long businessNo);


    /**
     * 过户：缴费业务退费回调接口
     * @author hc
     * @date 2020/11/19
     * @param businessNo 业务单号及过户记录表id
     * @return
     */
    @GetMapping("/transferAccount/charge/back")
    R<Boolean> transferAccountCallChargeBack(@RequestParam("businessNo") Long businessNo);

    /**
     * 业务关注点查询过户数据列表
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    R<List<GasMeterTransferAccountVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                      @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 16:03
    * @remark 统计过户的数据
    */
    @ApiOperation(value = "统计过户的数据")
    @PostMapping("/sts/stsTransferNum")
    R<Long> stsTransferNum(@RequestBody StsSearchParam stsSearchParam);
}
