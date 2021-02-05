package com.cdqckj.gmis.readmeter.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;
import feign.Response;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class GasMeterBookRecordApiFallback implements GasMeterBookRecordApi {

    @Override
    public R<GasMeterBookRecord> save(GasMeterBookRecordSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<GasMeterBookRecord> saveList(List<GasMeterBookRecordSaveDTO> list) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeterBookRecord>> saveRecordList(List<GasMeterBookRecordSaveDTO> list) {
        return null;
    }


    @Override
    public R<GasMeterBookRecord> update(GasMeterBookRecordUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatch(List<GasMeterBookRecordUpdateDTO> updateList) {
        return null;
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Boolean> deleteByDto(GasMeterBookRecord gasMeterBookRecord) {
        return R.timeout();
    }

    @Override
    public R<Page<GasMeterBookRecord>> page(PageParams<GasMeterBookRecordPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Page<GasMeterBookRecordVo>> pageBookRecord(PageParams<GasMeterBookRecordPageDTO> params) {
        return null;
    }

    @Override
    public R<List<GasMeterBookRecord>> queryList(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeterBookRecord>> queryByBookId(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeterBookRecord>> query(GasMeterBookRecord record) {
        return R.timeout();
    }

    @Override
    public R<GasMeterBookRecord> queryOne(GasMeterBookRecord data) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateGasMeterByCode(GasMeterBookRecord record) {
        return null;
    }

    @Override
    public R<Boolean> updateByCustomer(GasMeterBookRecordUpdateDTO updateDTO) {
        return null;
    }

    @Override
    public R<Boolean> updateByGasType(GasMeterBookRecordUpdateDTO updateDTO) {
        return null;
    }

    @Override
    public R<Integer> getMaxNumber(Long readMeterBookId) {
        return null;
    }

}
