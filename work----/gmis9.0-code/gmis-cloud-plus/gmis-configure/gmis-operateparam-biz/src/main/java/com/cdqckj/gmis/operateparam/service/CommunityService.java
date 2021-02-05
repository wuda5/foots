package com.cdqckj.gmis.operateparam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dto.CommunityPageDTO;
import com.cdqckj.gmis.operateparam.dto.CommunitySaveDTO;
import com.cdqckj.gmis.operateparam.dto.CommunityUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.StreetPageDTO;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-22
 */
public interface CommunityService extends SuperService<Community> {

    /**
     * @param pageNo
     * @param pageSize
     * @param streetId
     * @return
     */
    IPage<Community> getCommunityListByStreetId(Integer pageNo, Integer pageSize, Long streetId);

    /**
     * 小区数据权限查询列表
      * @param page
     * @param communityPageDTO
     * @return
     */
    IPage<Community> findPage(IPage<Community> page, CommunityPageDTO communityPageDTO);

    /**
     *
     * @param communityUpdateDTO
     * @return
     */
    Boolean check(CommunityUpdateDTO communityUpdateDTO);
}
