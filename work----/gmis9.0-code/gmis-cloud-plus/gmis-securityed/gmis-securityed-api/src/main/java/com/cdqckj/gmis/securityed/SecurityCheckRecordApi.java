package com.cdqckj.gmis.securityed;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordPageDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordUpdateDTO;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${gmis.feign.securityed-server:gmis-securityed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/securityCheckRecord", qualifier = "securityCheckRecordApi")
public interface SecurityCheckRecordApi {
    /*新增安检计划
    * */
    @PostMapping(value = "/saveList")
    R<SecurityCheckRecord> saveList(@RequestBody List<SecurityCheckRecordSaveDTO> list);

    /*修改安检计划
    * */
    @PutMapping
    R<SecurityCheckRecord> update(@RequestBody @Validated(SuperEntity.Update.class) SecurityCheckRecordUpdateDTO updateDTO);

    /*查询安检计划
    * */
    @PostMapping(value = "/page")
    R<Page<SecurityCheckRecord>> page(@RequestBody @Validated PageParams<SecurityCheckRecordPageDTO> params);

    /*
    * @TODO 新增安检计划
    * */
    @PostMapping("/saveSecurityCheckRecord")
    R<Boolean> saveSecurityCheckRecord(@RequestBody @Validated List<SecurityCheckRecord> securityCheckRecord);

    /* @TODO 审核
    * 、*/
    @PostMapping("/approvaled")
     R<Integer> approvaledSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params);

   /* @TODO 驳回
   * */
    @PostMapping(value = "/reject")
    R<Integer> reject(@RequestBody @Validated SecurityCheckRecord params);

     /*@TODO  结束
    */
    @PostMapping(value = "/end")
    R<Integer> endSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params);
   // @TODO  派工
    @PostMapping(value = "/leaflet")
    R<Integer> leafletSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params);

    // @TODO  派工
    @PostMapping(value = "/giveOrder")
    R<Integer> giveOrder(@RequestBody @Validated SecurityCheckRecord params);

    // @TODO  完成工單
    @PostMapping(value = "/completeOrder")
    R<Integer> completeOrder(@RequestBody @Validated SecurityCheckRecord params);

    @PostMapping("/queryOne")
    R<SecurityCheckRecord> queryOne(@RequestBody SecurityCheckRecord query);
}
