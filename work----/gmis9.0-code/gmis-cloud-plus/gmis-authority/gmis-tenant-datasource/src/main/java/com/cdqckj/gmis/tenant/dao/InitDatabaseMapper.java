package com.cdqckj.gmis.tenant.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 初始化数据库DAO
 *
 * @author gmis
 * @date 2019/09/02
 */
@Repository
@SqlParser(filter = true)
public interface InitDatabaseMapper {
    /**
     * 创建数据库
     *
     * @param database
     * @return
     */
    int createDatabase(@Param("database") String database);


    /**
     * 删除数据库
     *
     * @param database
     * @return
     */
    int dropDatabase(@Param("database") String database);

    /**
     * 根据条件查询租户列表
     *
     * @param status 状态
     * @return
     */
    List<String> selectTenantCodeList(@Param("status") String status);
}
