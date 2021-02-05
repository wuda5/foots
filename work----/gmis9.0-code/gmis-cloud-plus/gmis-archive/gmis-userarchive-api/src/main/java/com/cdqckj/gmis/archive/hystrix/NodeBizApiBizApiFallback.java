package com.cdqckj.gmis.archive.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.archive.NodeBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.nodearchive.dto.NodePageDTO;
import com.cdqckj.gmis.nodearchive.dto.NodeSaveDTO;
import com.cdqckj.gmis.nodearchive.dto.NodeUpdateDTO;
import com.cdqckj.gmis.nodearchive.entity.Node;
import com.cdqckj.gmis.sim.entity.Card;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeBizApiBizApiFallback implements NodeBizApi {
    @Override
    public R<Node> saveNode(NodeSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Node> updateNode(NodeUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> deleteNode(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<IPage<Node>> page(PageParams<NodePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Card> logicalDelete(List<Id> ids) throws Exception {
        return R.timeout();
    }
}
