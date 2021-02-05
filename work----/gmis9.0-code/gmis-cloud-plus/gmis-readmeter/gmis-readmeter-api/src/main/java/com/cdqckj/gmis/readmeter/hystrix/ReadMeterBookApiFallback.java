package com.cdqckj.gmis.readmeter.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.ReadMeterBookApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import feign.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.util.List;

/**
 * @author 65427
 */
@Component
public class ReadMeterBookApiFallback implements ReadMeterBookApi {

    @Override
    public R<Boolean> saveBook(ReadMeterBook book) {
        return R.timeout();
    }


    @Override
    public R<ReadMeterBook> update(ReadMeterBookUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBookDetail(ReadMeterBook book) {
        return null;
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<ReadMeterBook>> page(PageParams<ReadMeterBookPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterBook> getById(@RequestParam("id") Long id) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterBook>> queryList(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public Response export(PageParams<ReadMeterBookPageDTO> params) {
        return null;
    }

    @Override
    public Response exportCombobox(PageParams<ReadMeterBookPageDTO> params) {
        return null;
    }

    @Override
    public R<Page<ReadMeterBook>> queryByWrap(PageParams<ReadMeterPlan> params){
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterBook>> query(ReadMeterBook data) {
        return R.timeout();
    }
}
