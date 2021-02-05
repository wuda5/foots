package com.cdqckj.gmis.readmeter;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.readmeter.dto.BankWithholdRecordSaveDTO;
import com.cdqckj.gmis.readmeter.entity.BankWithholdRecord;
import com.cdqckj.gmis.readmeter.hystrix.ReadMeterBankApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 抄表导入
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/bankWithholdRecord", qualifier = "bankWithholdRecordApi")
public interface ReadMeterBankApi {

    /**
     * @param
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    R<BankWithholdRecord> saveList(@RequestBody List<BankWithholdRecordSaveDTO> readMeterBankList);

    /**
     *
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<BankWithholdRecord> save(@RequestBody BankWithholdRecordSaveDTO saveDTO);

}
