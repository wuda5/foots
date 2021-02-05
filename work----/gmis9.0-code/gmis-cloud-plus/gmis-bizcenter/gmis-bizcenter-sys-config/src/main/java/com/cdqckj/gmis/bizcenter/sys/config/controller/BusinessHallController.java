package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.systemparam.dto.BusinessHallPageDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallSaveDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 营业厅配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/bizhall")
@Api(value = "bizhall", tags = "营业厅配置")
//@PreAuth(replace = "businessHall:")
public class BusinessHallController {

    @Autowired
    public BusinessHallBizApi businessHallBizApi;

    @ApiOperation(value = "新增营业厅信息")
    @PostMapping("/businessHall/add")
    //@PreAuth("hasPermit('{}add')")
    public R<BusinessHall> saveBusinessHall(@RequestBody BusinessHallSaveDTO businessHallSaveDTO){
        return businessHallBizApi.save(businessHallSaveDTO);
    }

    @ApiOperation(value = "更新营业厅信息")
    @PutMapping("/businessHall/update")
    public R<BusinessHall> updateBusinessHall(@RequestBody BusinessHallUpdateDTO businessHallUpdateDTO){
        return businessHallBizApi.update(businessHallUpdateDTO);
    }

    @ApiOperation(value = "删除营业厅信息")
    @DeleteMapping("/businessHall/delete")
    public R<Boolean> deleteBusinessHall(@RequestParam("ids[]") List<Long> ids){
        return businessHallBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "批量更新营业厅信息")
    @PutMapping("/businessHall/updateBatch")
    public R<Boolean> updateBusinessHall(@RequestBody List<BusinessHallUpdateDTO> list){
        return businessHallBizApi.updateBatch(list);
    }

    @ApiOperation(value = "根据id查询营业厅详情")
    @GetMapping("/{id}")
    R<BusinessHall> queryById(@PathVariable Long id){
        return businessHallBizApi.queryById(id);
    }

    @ApiOperation(value = "根据orgId查询营业厅详情")
    @GetMapping("/queryByOrgId/{orgId}")
    BusinessHall queryByOrgId(@PathVariable Long orgId){
        return businessHallBizApi.queryByOrgId(orgId);
    }

    @ApiOperation(value = "分页查询营业厅信息信息")
    @PostMapping("/businessHall/page")
    public R<Page<BusinessHall>> pageBusinessHall(@RequestBody PageParams<BusinessHallPageDTO> params){
        return businessHallBizApi.page(params);
    }

    /*@ApiOperation(value = "根据模板导出excel")
    @PostMapping("/businessHall/exportExcel")
    public void exportExcel(String name){
        businessHallBizApi.exportExcel(name);
    }*/

}
