package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.MobileMessageBizApi;
import com.cdqckj.gmis.systemparam.dto.MobileMessagePageDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessageSaveDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessageUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.MobileMessage;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class MobileMessageBizApiFallback implements MobileMessageBizApi {

    @Override
    public R<MobileMessage> save(MobileMessageSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<MobileMessage> update(MobileMessageUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<MobileMessage>> page(PageParams<MobileMessagePageDTO> params) {
        return R.timeout();
    }
}
