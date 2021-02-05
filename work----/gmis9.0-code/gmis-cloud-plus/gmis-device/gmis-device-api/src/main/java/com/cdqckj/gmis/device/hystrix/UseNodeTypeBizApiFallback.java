package com.cdqckj.gmis.device.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.UseNodeTypeBizApi;
import com.cdqckj.gmis.node.dto.UseNodeTypePageDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.UseNodeTypeUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeFactory;
import com.cdqckj.gmis.node.entity.UseNodeType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class UseNodeTypeBizApiFallback implements UseNodeTypeBizApi {

    @Override
    public R<UseNodeType> save(UseNodeTypeSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<UseNodeType> update(UseNodeTypeUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<UseNodeType>> page(PageParams<UseNodeTypePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<UseNodeType>> query(UseNodeType data) { return R.timeout(); }
}
