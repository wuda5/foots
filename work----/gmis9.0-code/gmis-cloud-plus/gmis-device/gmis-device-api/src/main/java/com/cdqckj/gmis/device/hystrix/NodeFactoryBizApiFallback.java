package com.cdqckj.gmis.device.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.NodeFactoryBizApi;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.node.dto.NodeFactoryPageDTO;
import com.cdqckj.gmis.node.dto.NodeFactorySaveDTO;
import com.cdqckj.gmis.node.dto.NodeFactoryUpdateDTO;
import com.cdqckj.gmis.node.entity.NodeFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class NodeFactoryBizApiFallback implements NodeFactoryBizApi {

    @Override
    public R<NodeFactory> save(NodeFactorySaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<NodeFactory> update(NodeFactoryUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<NodeFactory>> page(PageParams<NodeFactoryPageDTO> params) {
        return R.timeout();
    }


    @Override
    public R<List<NodeFactory>> query(NodeFactory data) { return R.timeout(); }
}
