package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.PreferentialBizApi;
import com.cdqckj.gmis.operateparam.dto.PreferentialPageDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Preferential;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 优惠活动总览
 * </p>
 * @author lmj
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/preferential")
@Api(value = "preferential", tags = "优惠活动")
//@PreAuth(replace = ":")
public class PreferController {

    @Autowired
    public PreferentialBizApi preferentialBizApi;

    @ApiOperation(value = "新增优惠活动信息")
    @PostMapping("/add")
    public R<Preferential> save(@RequestBody PreferentialSaveDTO preferentialSaveDTO){
        return preferentialBizApi.save(preferentialSaveDTO);
    }

    @ApiOperation(value = "修改优惠活动信息")
    @PutMapping("/update")
    public R<Preferential> update(@RequestBody PreferentialUpdateDTO preferentialUpdateDTO){
        return preferentialBizApi.update(preferentialUpdateDTO);
    }

    @ApiOperation(value = "删除优惠活动信息")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> id){
        return preferentialBizApi.delete(id);
    }


    @ApiOperation(value = "查询优惠活动信息")
    @PostMapping("/page")
    public R<Page<Preferential>> page(@RequestBody PageParams<PreferentialPageDTO> params){
        return preferentialBizApi.page(params);
    }


    @ApiOperation(value = "批量查询优惠活动信息")
    @PostMapping("/query")
    public R<List<Preferential>> query(@RequestBody Preferential data){
        return preferentialBizApi.query(data);
    }

}
