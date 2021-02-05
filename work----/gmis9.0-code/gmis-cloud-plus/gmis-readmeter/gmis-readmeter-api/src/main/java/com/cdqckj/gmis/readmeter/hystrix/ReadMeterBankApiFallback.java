package com.cdqckj.gmis.readmeter.hystrix;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.readmeter.ReadMeterBankApi;
import com.cdqckj.gmis.readmeter.dto.BankWithholdRecordSaveDTO;
import com.cdqckj.gmis.readmeter.entity.BankWithholdRecord;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class ReadMeterBankApiFallback implements ReadMeterBankApi {

    @Override
    public R<BankWithholdRecord> saveList(List<BankWithholdRecordSaveDTO> readMeterBankList) {
        return R.timeout();
    }

    @Override
    public R<BankWithholdRecord> save(BankWithholdRecordSaveDTO saveDTO) {
        return R.timeout();
    }

}
