package com.cdqckj.gmis.tenant.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.cdqckj.gmis.tenant.entity.GlobalUser;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 全局账号
 * </p>
 *
 * @author gmis
 * @date 2019-10-25
 */
@Repository
@SqlParser(filter = true)
public interface GlobalUserMapper extends SuperMapper<GlobalUser> {

}
