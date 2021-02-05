package com.cdqckj.gmis.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 删除Controller
 *
 * @param <Entity> 实体
 * @param <Id>     主键
 * @author gmis
 * @date 2020年03月07日22:02:16
 */
public interface DeleteController<Entity, Id extends Serializable> extends BaseController<Entity> {

    /**
     * 删除方法
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除")
    @DeleteMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "主键id", dataType = "array", paramType = "query"),
    })
    @SysLog("'删除:' + #ids")
    @GlobalTransactional
    //@PreAuth("hasPermit('{}delete')")
    default R<Boolean> delete(@RequestParam("ids[]") List<Id> ids) {
        // 如果需要删除几张表的，可能需要重写下面的 handlerDelete
        R<Boolean> result = handlerDelete(ids);
        if (result.getDefExec()) {
            getBaseService().removeByIds(ids);
        }
        return result;
    }

    @ApiOperation(value = "条件删除（真实删除）")
    @DeleteMapping(value = "/deleteReal")
    @SysLog("条件删除（真实删除）")
    @GlobalTransactional
    default R<Boolean> deleteReal(@RequestBody Entity entity)   {
        R<Boolean> result = R.success();
        QueryWrap<Entity> wrapper = Wraps.q(entity);
        Boolean bool = getBaseService().remove(wrapper);
        return R.success();
    }

    @ApiOperation(value = "条件删除（逻辑删除）")
    @DeleteMapping(value = "/deleteByDto")
    @SysLog("条件删除（逻辑删除）")
    @GlobalTransactional
    default R<Boolean> deleteByDto(@RequestBody Entity entity) throws Exception  {
        R<Boolean> result = R.success();
        QueryWrap<Entity> wrapper = Wraps.q(entity);
        //Boolean bool = getBaseService().remove(wrapper);
        List<Entity> list = getBaseService().list(wrapper);
        Boolean bool = getaBoolean(entity, result, (List<Entity>) list);
        return bool ? R.success():R.fail("");
    }

    default Boolean getaBoolean(Entity entity, R<Boolean> result, List<Entity> list) throws NoSuchFieldException, IllegalAccessException {
        Object o = JSON.toJSON(entity);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        Boolean bool = jsonObj.containsKey("deleteStatus");
        if (bool) {
            if (list.size() > 0) {
                for (Entity en : list) {
                    Field fields = en.getClass().getDeclaredField("deleteStatus");
                    fields.setAccessible(true);
                    fields.set(en, 1);
                }
                getBaseService().updateBatchById(list);
            }
        } else {
            result.setMsg(getLangMessage(MessageConstants.MISSING_FIELD_DELETESTATUS));
        }
        return bool;
    }

    /**
     * 自定义删除
     *
     * @param ids
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    default R<Boolean> handlerDelete(List<Id> ids) {
        return R.successDef(true);
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "逻辑删除")
    //@DeleteMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "主键id", dataType = "array", paramType = "query"),
    })
    @SysLog("'逻辑删除:' + #ids")
    @DeleteMapping(value = "/logicalDelete")
    @GlobalTransactional
    //@PreAuth("hasPermit('{}logicalDelete')")
    default R<Boolean> logicalDelete(@RequestParam("ids[]") List<Id> ids) throws Exception {
        R<Boolean> result = handlerLogicalDelete(ids);
        if (result.getDefExec()) {
            List<Entity> list = getBaseService().listByIds(ids);
            Entity entity = list.get(0);
            Boolean bool = getaBoolean(entity, result, list);
        }
        return result;
    }



    /**
     * 自定义删除
     *
     * @param ids
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    @GlobalTransactional
    default R<Boolean> handlerLogicalDelete(List<Id> ids) {
        return R.successDef(true);
    }
}
