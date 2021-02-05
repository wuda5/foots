package com.cdqckj.gmis.authority.dao.core;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 组织
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Repository
public interface OrgMapper extends SuperMapper<Org> {
    /**
     * 查询组织
     * @param queryWrapper
     * @param dataScope
     * @return
     */
    List<Org> findOrgList(@Param(Constants.WRAPPER) Wrapper<Org> queryWrapper);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/22 8:56
    * @remark 所有的机构
    */
    List<Org> allOnlineOrg();

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/28 13:56
    * @remark 用户的数据的权限的机构
    */
    List<Org> userAllOrg(@Param("userId") Long userId);
}
