package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.RechargeRecordPageDTO;
import com.cdqckj.gmis.charges.dto.RechargeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.RechargeRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 充值API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}",
        fallbackFactory = HystrixTimeoutFallbackFactory.class,
        path = "/rechargeRecord", qualifier = "RechargeRecordBizApi")
public interface RechargeRecordBizApi {
    /**
     * 保存充值记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<RechargeRecord> save(@RequestBody RechargeRecordSaveDTO saveDTO);

    /**
     * 更新充值记录
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<RechargeRecord> update(@RequestBody RechargeRecordUpdateDTO updateDTO);

    /**
     * 删除充值记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询充值记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<RechargeRecord>> page(@RequestBody PageParams<RechargeRecordPageDTO> params);

    /**
     * ID查询充值记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<RechargeRecord> get(@PathVariable("id") Long id);

    /**
     * 查询充值记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<RechargeRecord>> query(@RequestBody RechargeRecord queryInfo);

    /**
     * 处理未完成的充值记录
     * @param gasMeterCode
     * @return
     */
    @PostMapping(value = "/dealUnCompleteRecord")
    R<Boolean> dealUnCompleteRecord(@RequestParam(value = "gasMeterCode") String gasMeterCode);



    /**
     * 收费主动调用
     * 物联网表充值指令下发成功回调处理充值记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeOrderCallBack")
    public R<Boolean> iotRechargeOrderCallBack(@RequestParam(value = "chargeNo") String chargeNo,
                                               @RequestParam(value = "serialNo") String serialNo
    );

    /**
     * 物联网表充值下发指令重试成功回调处理充值记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeOrderRetryCallBack")
    R<Boolean> iotRechargeOrderRetryCallBack(@RequestParam(value = "serialNo") String serialNo);


    /**
     * 物联网表充值上表回调处理充值记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeMeterCallBack")
    R<Boolean> iotRechargeMeterCallBack(@RequestParam(value = "serialNo") String serialNo,
                                               @RequestParam(value = "dealStatus") Boolean dealStatus);

    /**
     * 物联网表充值指撤销回调处理充值记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeOrderCancelCallBack")
    R<Boolean> iotRechargeOrderCancelCallBack(@RequestParam(value = "serialNo") String serialNo);


    /**
     * 根据客户编号气表编号查询充值记录
     */
    @GetMapping("/getListByMeterAndCustomerCode")
    R<List<RechargeRecord>> getListByMeterAndCustomerCode(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                                 @RequestParam(value = "customerCode") String customerCode);
    /**
     * 根据缴费编号查询充值记录
     */
    @GetMapping("/getByChargeNo")
    R<RechargeRecord> getByChargeNo(@RequestParam(value = "chargeNo")String chargeNo);


    /**
     * 根据气表编码和状态查询充值记录
     */
    @GetMapping("/getByGasMeterCodeAndStatus")
    R<RechargeRecord> getByGasMeterCodeAndStatus(@RequestParam(value = "gasMeterCode")String gasMeterCode,
                                                 @RequestParam(value = "customerCode")String customerCode,
                                                 @RequestParam(value = "moneyFlowStatus")String moneyFlowStatus
                                                 );

    /**
     * 根据客户编号气表编号分页查询充值记录
     */
    @PostMapping("/getPageByMeterAndCustomerCode")
    R<Page> getPageByMeterAndCustomerCode(@RequestBody PageParams<RechargeRecordPageDTO> params);

    /**
     * 查询表具是否有未完成的充值记录
     *
     * @param gasMeterCode 表具编号
     * @return 未完成的充值记录列表
     */
    @GetMapping("/queryUnfinishedRecord")
    R<List<RechargeRecord>> queryUnfinishedRecord(@RequestParam("gasMeterCode") String gasMeterCode);
}
