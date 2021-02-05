package com.cdqckj.gmis.readmeter.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.ReadMeterDataErrorApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorSaveDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataError;
import feign.Response;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class ReadMeterDataErrorApiFallback implements ReadMeterDataErrorApi {

    @Override
    public R<ReadMeterDataError> saveList(List<ReadMeterDataErrorSaveDTO> readMeterDataErrorList) {
        return R.timeout();
    }

    @Override
    public R<Page<ReadMeterDataError>> page(PageParams<ReadMeterDataErrorPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterDataError>> queryList(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterDataError>> query(ReadMeterDataError data) {
        return R.timeout();
    }

    @Override
    public Response exportCombobox(PageParams<ReadMeterDataErrorPageDTO> params) {
        return null;
    }


}
