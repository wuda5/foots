package com.cdqckj.gmis.archive.hystrix;

import com.cdqckj.gmis.archive.CustomerGasMeterBindBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindPrame;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindResult;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterBind;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerGasMeterBindBizApiFallback implements CustomerGasMeterBindBizApi {
    @Override
    public R<CustomerGasMeterBind> saveList(List<CustomerGasMeterBindSaveDTO> list) {
        return R.timeout();
    }

    @Override
    public R<CustomerGasMeterBind> update(CustomerGasMeterBindUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeterBindResult>> getGasMeterInfo(GasMeterBindPrame gasMeterBindPrame) {
        return R.timeout();
    }
}
