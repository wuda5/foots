package com.cdqckj.gmis.operateparam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.CommunityPageDTO;
import com.cdqckj.gmis.operateparam.dto.CommunitySaveDTO;
import com.cdqckj.gmis.operateparam.dto.CommunityUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.StreetPageDTO;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.service.CommunityService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/community")
@Api(value = "Community", tags = "小区配置管理")
//@PreAuth(replace = "community:")
public class CommunityController extends SuperController<CommunityService, Long, Community, CommunityPageDTO, CommunitySaveDTO, CommunityUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Community> communityList = list.stream().map((map) -> {
            Community community = Community.builder().build();
            //TODO 请在这里完成转换
            return community;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(communityList));
    }
//    @Override
//    public void query(PageParams<CommunityPageDTO> params, IPage<Community> page, Long defSize) {
//        baseService.findPage(page, params.getModel());
//    }
    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody  CommunityUpdateDTO communityUpdateDTO){
        return R.success(baseService.check(communityUpdateDTO));
    };
}
