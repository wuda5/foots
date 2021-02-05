package com.cdqckj.gmis.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.utils.BeanPlusUtil;
//import io.seata.spring.annotation.GlobalTransactional;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 修改
 *
 * @param <Entity>    实体
 * @param <UpdateDTO> 修改参数
 * @author gmis
 * @date 2020年03月07日22:30:37
 */
public interface UpdateController<Entity, UpdateDTO> extends BaseController<Entity> {

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    @SysLog(value = "'修改:' + #updateDTO?.id", request = false)
//    @PreAuth("hasPermit('{}update')")
    @GlobalTransactional
    default R<Entity> update(@RequestBody @Validated(SuperEntity.Update.class) UpdateDTO updateDTO) {
        R<Entity> result = handlerUpdate(updateDTO);
        if (result.getDefExec()) {
            Entity model = BeanUtil.toBean(updateDTO, getEntityClass());
            // 默认只是更新一条记录，byid, 可以自己重写 handlerUpdate(updateDTO); xx,使其按 指定条件更新特定字段等xxx
            getBaseService().updateById(model);
            result.setData(model);
        }
        return result;
    }


    /**
     * 批量修改
     *
     * @param list
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping(value = "/updateBatch")
    //@PreAuth("hasPermit('{}update')")
    @GlobalTransactional
    default R<Boolean> updateBatchById(@RequestBody List<UpdateDTO> list) {
        R<Boolean> result = R.successDef(true);
        if (result.getDefExec()) {
            List<Entity> modelList = BeanPlusUtil.toBeanList(list,getEntityClass());
            getBaseService().updateBatchById(modelList);
        }
        return result;
    }

    /**
     * 自定义更新
     *
     * @param model
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    default R<Entity> handlerUpdate(UpdateDTO model) {
        return R.successDef();
    }
}
