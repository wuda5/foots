package com.cdqckj.gmis.systemparam.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.dto.auth.UserPageDTO;
import com.cdqckj.gmis.authority.dto.core.StationPageDTO;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.dto.BusinessHallPageDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallSaveDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.systemparam.service.BusinessHallService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 营业厅信息表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/businessHall")
@Api(value = "BusinessHall", tags = "营业厅信息表")
@SysLog(enabled = false)
//@PreAuth(replace = "businessHall:")
public class BusinessHallController extends SuperController<BusinessHallService, Long, BusinessHall, BusinessHallPageDTO, BusinessHallSaveDTO, BusinessHallUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<BusinessHall> businessHallList = list.stream().map((map) -> {
            BusinessHall businessHall = BusinessHall.builder().build();
            //TODO 请在这里完成转换
            return businessHall;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(businessHallList));
    }

    @Override
    public void query(PageParams<BusinessHallPageDTO> params, IPage<BusinessHall> page, Long defSize) {
        baseService.findPage(page, params.getModel());
    }

    @GetMapping(value = "/queryByOrgId/{orgId}")
    public BusinessHall queryByOrgId(@PathVariable("orgId")Long orgId){
        return baseService.queryByOrgId(orgId);
    }
}
