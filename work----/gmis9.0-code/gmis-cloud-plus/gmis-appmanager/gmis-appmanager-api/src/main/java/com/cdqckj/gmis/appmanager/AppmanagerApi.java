package com.cdqckj.gmis.appmanager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.appmanager.dto.AppmanagerPageDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerUpdateDTO;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hc
 */
@FeignClient(name = "${gmis.feign.appmanager-server:gmis-appmanager-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/appmanager", qualifier = "appmanagerApi")
public interface AppmanagerApi{

    /**
     * 保存
     * @param appmanager
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<Appmanager> save(@RequestBody Appmanager appmanager);

    /**
     * 分页查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    R<Page<Appmanager>> page(@RequestBody PageParams<AppmanagerPageDTO> params);


    /**
     * 数据更新
     * @param appmanagerUpdateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<Appmanager> update(@RequestBody AppmanagerUpdateDTO appmanagerUpdateDTO);

    /**
     * 根据id获取数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<Appmanager> get(@PathVariable("id") Long id);

    /**
     * 按条件批量查询
     * @param appmanager
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    R<List<Appmanager>> query(@RequestBody Appmanager appmanager);

}
