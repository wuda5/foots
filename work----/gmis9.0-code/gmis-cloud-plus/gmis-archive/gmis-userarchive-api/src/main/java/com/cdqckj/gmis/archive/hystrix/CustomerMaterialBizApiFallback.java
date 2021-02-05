package com.cdqckj.gmis.archive.hystrix;

import com.cdqckj.gmis.archive.CustomerMaterialBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.CustomerMaterial;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMaterialBizApiFallback implements CustomerMaterialBizApi {
    @Override
    public R<CustomerMaterial> saveList(List<CustomerMaterialSaveDTO> list) {
        return R.timeout();
    }

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    @Override
    public R<List<CustomerMaterial>> query(CustomerMaterial query) {
        return R.timeout();
    }

    /**
     * 逻辑删除数据
     *
     * @param ids id数组
     * @return true or false
     */
    @Override
    public R<Boolean> logicalDelete(List<Long> ids) {
        return R.timeout();
    }

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    @Override
    public R<Boolean> updateBatchById(List<CustomerMaterialUpdateDTO> list) {
        return R.timeout();
    }

}
