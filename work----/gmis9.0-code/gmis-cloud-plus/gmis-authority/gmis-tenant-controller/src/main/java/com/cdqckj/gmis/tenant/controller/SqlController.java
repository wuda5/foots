package com.cdqckj.gmis.tenant.controller;

import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.code.DataBaseUtil;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.TransactionalTenantRest;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.log.annotation.SysLog;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/28 15:23
 * @remark: 请添加类说明
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sql")
@Api(value = "sql", tags = "整个系统的sql危险操作")
@SysLog(enabled = false)
public class SqlController {

    @Autowired
    MulTenantProcess mulTenantProcess;

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/28 15:25
    * @remark 管理员重新在所有的租户上执行sql
    */
    @ApiOperation(value = "管理员重新在所有的租户上执行sql")
    @PostMapping("/runAllTenant")
    @SysLog("管理员重新在所有的租户上执行sql")
    @TransactionalTenantRest(type = 1)
    public R<Boolean> runAllTenant(@RequestBody String sql) {
        String text = sql.substring(8, sql.length() - 2);
        String textReplace = text.replaceAll("\\\\n","\r\n");
        mulTenantProcess.runInOtherTenant(()->{
            DataBaseUtil.runSql(textReplace);
            return true;
        }, mulTenantProcess.getAllTenant());
        return R.success(true);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 8:49
    * @remark 检查表结构
    */
    @ApiOperation(value = "检查表结构")
    @GetMapping("/checkTableStruct")
    @SysLog("检查表结构")
    public R<Boolean> checkTableStruct(@RequestParam String tenantCode) {

        // 获取数据库的所有的表的名称
        String dataBase = "gmis_base_0000";
        StringBuilder sql = new StringBuilder();
        sql.append("select table_name from information_schema.tables where table_schema='").append(dataBase).append("' and table_type='BASE TABLE'");
        BaseContextHandler.setTenant("0000");
        List<String> names = DataBaseUtil.getOneColData(sql.toString(), "table_name");





        return R.success();
    }
}
