package com.cdqckj.gmis.device.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.NodeTypeBizApi;
import com.cdqckj.gmis.node.dto.NodeTypePageDTO;
import com.cdqckj.gmis.node.dto.NodeTypeSaveDTO;
import com.cdqckj.gmis.node.dto.NodeTypeUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeType;
import com.cdqckj.gmis.node.entity.UseNodeType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class NodeTypeBizApiFallback implements NodeTypeBizApi {

    @Override
    public R<NodeType> save(NodeTypeSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<NodeType> update(NodeTypeUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<NodeType>> page(PageParams<NodeTypePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<NodeType>> query(NodeType data) { return R.timeout(); }
}
