package com.cdqckj.gmis.readmeter.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.ReadMeterMonthGasApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterMonthGas;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author 65427
 */
@Component
public class ReadMeterMonthGasApiFallback implements ReadMeterMonthGasApi {

    @Override
    public R<ReadMeterMonthGas> save(ReadMeterMonthGasSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterMonthGas> saveList(List<ReadMeterMonthGasSaveDTO> readMeterMonthGasSaveDTOS) {
        return null;
    }


    @Override
    public R<ReadMeterMonthGas> update(ReadMeterMonthGasUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<ReadMeterMonthGas>> page(PageParams<ReadMeterMonthGasPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterMonthGas> getById(@RequestParam("id") Long id) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterMonthGas>> queryList(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterMonthGas>> getByYearAndGasCode(@RequestParam Integer year,@RequestParam List<String> gasCode) {
        return R.timeout();
    }

    @Override
    public R<Map<Integer,Map<String,ReadMeterMonthGas>>> getByYearsAndGasCode(List<Integer> year, List<String> gasCode) {
        return R.timeout();
    }
}
