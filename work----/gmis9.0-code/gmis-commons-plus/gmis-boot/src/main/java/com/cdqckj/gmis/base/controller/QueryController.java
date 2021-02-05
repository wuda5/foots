package com.cdqckj.gmis.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.log.annotation.SysLog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 查询Controller
 *
 * @param <Entity>  实体
 * @param <Id>      主键
 * @param <PageDTO> 分页参数
 * @author gmis
 * @date 2020年03月07日22:06:35
 */
public interface QueryController<Entity, Id extends Serializable, PageDTO> extends PageController<Entity, PageDTO> {

    /**
     * 查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
    })
    @ApiOperation(value = "查询", notes = "查询")
    @GetMapping("/{id}")
    @SysLog("'查询:' + #id")
    //@PreAuth("hasPermit('{}view')")
    default R<Entity> get(@PathVariable(value = "id") Id id) {
        return success(getBaseService().getById(id));
    }

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    @SysLog(value = "'分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    //@PreAuth("hasPermit('{}view')")
    default R<IPage<Entity>> page(@RequestBody @Validated PageParams<PageDTO> params) {
        // 处理参数
        PageDTO pageDTO = params.getModel();
        isDeleteStatusHand(pageDTO);
        IPage<Entity> page = params.getPage();
        query(params, page, null);
        return success(page);
    }

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    @SysLog("批量查询")
//    @PreAuth("hasPermit('{}view')")
    default R<List<Entity>> query(@RequestBody Entity data) {
        // 处理参数
        isDeleteStatusHand(data);//?自己加上了个默认如果数据库表有 deleteStatus 字段，给他查询强行限制加上0   fields.set(q, 0);
        QueryWrap<Entity> wrapper = Wraps.q(data);

        return success(getBaseService().list(wrapper));
    }

     default void isDeleteStatusHand(Object q) {
        Object o = JSON.toJSON(q);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        Boolean bool = jsonObj.containsKey("deleteStatus");
        if (bool) {
            Field fields = null;
            try {
                fields = q.getClass().getDeclaredField("deleteStatus");
                fields.setAccessible(true);
                if (null == fields.get(q)) {
                    fields.set(q, 0);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 条件查询一条数据
     *
     * @param data 条件查询一条数据
     * @return 查询结果
     */
    @ApiOperation(value = "条件查询一条数据", notes = "条件查询一条数据")
    @PostMapping("/queryOne")
    @SysLog("条件查询一条数据")
    default R<Entity> queryOne(@RequestBody Entity data) {
        isDeleteStatusHand(data);//?自己加上了个默认如果数据库表有 deleteStatus 字段，给他查询强行限制加上0   fields.set(q, 0);
        QueryWrap<Entity> wrapper = Wraps.q(data);
//        QueryWrapper<Entity> s = Wrappers.query(data);
        wrapper.last("LIMIT 1");
        return success(getBaseService().getOne(wrapper));

    }

    /**
     * 查询
     *
     * @param ids 主键id
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "主键", dataType = "array", paramType = "query"),
    })
    @ApiOperation(value = "根据id集合批量查询", notes = "根据id集合批量查询")
    @PostMapping("/queryList")
    @SysLog("根据id集合批量查询")
    //@PreAuth("hasPermit('{}view')")
    default R<List<Entity>> queryList(@RequestParam("ids[]") List<Id> ids) {
        return success(getBaseService().listByIds(ids));
    }

    /**
     * 根据id查询实体
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
    })
    @ApiOperation(value = "根据id查询实体", notes = "根据id查询实体")
    @PostMapping("/getById")
    @SysLog("根据id查询实体")
    //@PreAuth("hasPermit('{}view')")
    default R<Entity> getById(@RequestParam("id") Id id) {
        return success(getBaseService().getById(id));
    }

    /**
     * 自定义条件查询
     * @param wrapper
     * @return
     *//*
    @ApiOperation(value = "自定义条件查询", notes = "自定义条件查询")
    @PostMapping("/queryByWrap")
    @SysLog("根据id查询实体")
    //@PreAuth("hasPermit('{}view')")
    default R<List<Entity>> queryByWrap(@RequestBody LbqWrapper<Entity> wrapper) {
        return success(getBaseService().list(wrapper));
    }*/
}
