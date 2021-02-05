package com.cdqckj.gmis.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.log.annotation.SysLog;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新增
 *
 * @param <Entity>  实体
 * @param <SaveDTO> 保存参数
 * @author gmis
 * @date 2020年03月07日22:07:31
 */
public interface SaveController<Entity, SaveDTO> extends BaseController<Entity> {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    @SysLog(value = "新增", request = false)
    //@PreAuth("hasPermit('{}add')")
    @GlobalTransactional
    default R<Entity> save(@RequestBody @Valid SaveDTO saveDTO) {
        R<Entity> result = handlerSave(saveDTO);
        if (result.getDefExec()) {
            Entity model = setCommonParams(BeanUtil.toBean(saveDTO, getEntityClass()));
            getBaseService().save(model);
            result.setData(model);
        }
        return result;
    }

    /**
     * 自定义新增
     *
     * @param model
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    @GlobalTransactional
    default R<Entity> handlerSave(SaveDTO model) {
        return R.successDef();
    }

    /**
     * 新增
     *
     * @param list 保存参数
     * @return 实体
     */
    @ApiOperation(value = "批量新增")
    @PostMapping(value = "/saveList")
    @SysLog(value = "批量新增", request = false)
//    @PreAuth("hasPermit('{}add')")
    @GlobalTransactional
    default R<Entity> saveList(@RequestBody List<SaveDTO> list) {
        R<Entity> result = R.successDef();
        if (result.getDefExec() && list.size()>0) {
            List<Entity> entityList = list.stream().map(saveDTO -> {
                Entity model = setCommonParams(BeanUtil.toBean(saveDTO, getEntityClass()));
                return model;
            }).collect(Collectors.toList());
            Boolean bool = getBaseService().saveBatch(entityList);
            result.setDefExec(bool);
        }
        return result;
    }

}
