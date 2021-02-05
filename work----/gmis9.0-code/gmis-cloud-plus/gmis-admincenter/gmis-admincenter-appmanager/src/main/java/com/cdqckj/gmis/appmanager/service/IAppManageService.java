package com.cdqckj.gmis.appmanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.appmanager.dto.AppSettingDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerPageDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerSaveDTO;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;

public interface IAppManageService {


    /**
     * 新增应用
     * @auther hc
     * @date 2020/09/17
     * @param appmanagerSaveDTO
     * @return
     */
    R<Appmanager> addApplication(AppmanagerSaveDTO appmanagerSaveDTO);

    /**
     * 分页查询
     * @auther hc
     * @date 2020/09/17
     * @param params
     * @return
     */
    R<Page<Appmanager>> queryList(PageParams<AppmanagerPageDTO> params);

    /**
     * 数据修改
     * @auther hc
     * @date 2020/09/17
     * @param settingDTO
     * @return
     */
    R<Appmanager> update(AppSettingDTO settingDTO);

    /**
     * 生成应用密匙
     * @auter hc
     * @param id
     * @return
     */
    R<Appmanager> buildScrect(Long id);

    /**
     * 获取应用详情
     * @param id
     * @return
     */
    R<Appmanager> detail(Long id);

    /**
     * 改变应用状态
     * @auther hc
     * @param id
     * @param item
     * @return
     */
    R<Appmanager> changeStatus(Long id, Boolean item);
}
