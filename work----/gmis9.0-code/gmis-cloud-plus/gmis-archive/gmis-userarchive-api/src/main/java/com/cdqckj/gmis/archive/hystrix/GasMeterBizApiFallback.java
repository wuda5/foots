package com.cdqckj.gmis.archive.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.dto.GasMeterPageDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterVo;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.vo.FactoryAndVersion;
import com.cdqckj.gmis.devicearchive.vo.GasmeterPoi;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import feign.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Component
public abstract class GasMeterBizApiFallback implements GasMeterBizApi {
   @Override
    public R<GasMeter> save(GasMeterSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeter>> addGasMeterList(List<GasMeter> gasMeterList) {
        return R.timeout();
    }

    @Override

    public R<GasMeter> update(GasMeterUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateByCode(GasMeter gasMeter) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Page<GasMeter>> page(PageParams<GasMeterPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeter>> findGasMeterByCustomerCode(String customerCode) {
        return R.timeout();
    }

    @Override
    public R<Page<PageGasMeter>> findGasMeterPage(GasMeterVo params) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<List<GasmeterPoi>> importExcel(MultipartFile file) throws Exception {
        return R.timeout();
    }
    @Override
    public R<GasMeter> findGasMeterByCode(String gascode) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeter>> query(GasMeter gasMeter) {
        return R.timeout();
    }

    @Override
    public R<GasMeter> findGasMeterByGasMeterNumber(String gasMeterNumber) {
        return R.timeout();
    }

    @Override
    public R<Integer> findGasMeterNumber(FactoryAndVersion factoryAndVersion) {
        return R.timeout();
    }

    @Override
    public Response exportCombobox(PageParams<GasMeterPageDTO> params) {
        return null;
    }

    @Override
    public R<GasMeter> saveList(List<GasMeterSaveDTO> list) {
        return R.timeout();
    }

    @Override
    public R<Boolean> check(GasMeterUpdateDTO gasMeterUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<HashMap<String, Object>> findGasMeterInfoByCode(String gascode) {
        return R.timeout();
    }

    @Override
    public R<GasMeter> ventilation(GasMeter gasMeter) {
        return R.timeout();
    }

    @Override
    public Response exportCascadeTemplate(PageParams<GasMeterPageDTO> params) throws Exception {
        HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
        response.getWriter().write("导出表具级联模板超时");
        response.getWriter().flush();
        return null;
    }

    @Override
    public R<List<GasMeter>> queryMeterList(List<String> meterNoList) {
        return R.timeout();
    }
}
