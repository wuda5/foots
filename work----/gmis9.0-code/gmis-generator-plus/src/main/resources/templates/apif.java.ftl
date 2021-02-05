package com.cdqckj.gmis.${cfg.serviceName}.api.hystrix;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.${cfg.serviceName}.api.${entity}BizApi;
import ${cfg.SaveDTO}.${entity}PageDTO;
import ${cfg.SaveDTO}.${entity}SaveDTO;
import ${cfg.SaveDTO}.${entity}UpdateDTO;
import ${package.Entity}.${entity};
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Component
public class ${entity}BizApiFallback implements ${entity}BizApi {
    @Override
    public R<${entity}> save(${entity}SaveDTO saveDTO){
        return R.timeout();
    }

    @Override
    public R<${entity}> saveList(List<${entity}SaveDTO> list){
        return R.timeout();
    }

    @Override
    public R<${entity}> update(${entity}UpdateDTO updateDTO){
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatchById(List<${entity}UpdateDTO> list){
        return R.timeout();
    }

    @Override
    public R<Page<${entity}>> page(PageParams<${entity}PageDTO> params){
        return R.timeout();
    }

    @Override
    public R<${entity}> get(Long id){
        return R.timeout();
    }

    @Override
    public R<List<${entity}>> query(${entity} queryInfo){
        return R.timeout();
    }

    @Override
    public R<List<${entity}>> queryList(List<Long> ids){
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> ids){
        return R.timeout();
    }

    @Override
    public R<Boolean> deleteByDto(${entity} entity){
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> ids){
        return R.timeout();
    }

    @Override
    public void exportCombobox(PageParams<${entity}PageDTO> params){

    }

    @Override
    public R<Boolean> importExcel(MultipartFile simpleFile){
        return R.timeout();
    }
}
