package com.cdqckj.gmis.tenant.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.tenant.dao.InitDatabaseMapper;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.tenant.service.DataSourceService;
import com.cdqckj.gmis.tenant.service.InitDsService;
import com.cdqckj.gmis.tenant.strategy.InitSystemStrategy;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.cdqckj.gmis.common.constant.BizConstant.BASE_DATABASE;

/**
 * 初始化系统
 * <p>
 * 初始化规则：
 * gmis-authority-server/src/main/resources/sql 路径存放8个sql文件 (每个库对应一个文件)
 * gmis_base.sql            # 基础库：权限、消息，短信，邮件，文件等
 * data_gmis_base.sql       # 基础库数据： 如初始用户，初始角色，初始菜单
 *
 * @author gmis
 * @date 2019/10/25
 */
@Service("DATASOURCE")
@Slf4j
@Primary
public class DatasourceInitSystemStrategy implements InitSystemStrategy {
    /**
     * 需要初始化的sql文件在classpath中的路径
     */
    private final static String SQL_RESOURCE_PATH = "sqls/%s.sql";

    /**
     * 需要初始化的库
     * 可能不同的服务，会连接不同的库
     */
    private final static List<String> INIT_DATABASE_LIST = Arrays.asList(BASE_DATABASE);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private InitDatabaseMapper initDbMapper;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    @Qualifier("mqInitDsServiceImpl")
    private InitDsService initDsService;
    @Autowired
    private DatabaseProperties databaseProperties;
    @Value("${gmis.mysql.database}")
    private String defaultDatabase;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MulTenantProcess mulTenantProcess;

    /** 初始化数据库的单个线程池 **/
    private Executor singleThreadExecutor = Executors.newSingleThreadExecutor();

    public InitDsService getInitDsService() {
        return initDsService;
    }

    /**
     * 启动项目时，调用初始化数据源
     *
     * @return
     */
    @DS("master")
    public boolean initDataSource() {
        List<String> list = initDbMapper.selectTenantCodeList(TenantStatusEnum.NORMAL.name());

        list.forEach(item -> {
            dataSourceService.initDataSource(item);
        });
        return true;
    }

    @Override
    public boolean init(String tenant) {

//        this.initDatabases(tenant);

//        this.initTables(tenant);
//
//        this.initData(tenant);
//        // 切换为默认数据源
//        this.resetDatabase();
//        // 动态初始化数据源
//        initDsService.initDataSource(tenant);

        // 初始化数据库
        this.initDatabases(tenant);
        // 重新加载数据库
        this.initDataSource();
        // 异步加载 数据库表 表里面的数据
        InitDataBaseThread initDataBaseThread = new InitDataBaseThread(this, tenant, rabbitTemplate);
        Thread thread = new Thread(initDataBaseThread);
        singleThreadExecutor.execute(thread);
        mulTenantProcess.clearTenantCache();
        return true;
    }

    @Override
    public boolean reset(String tenant) {
        ScriptRunner runner = null;
        try {
            runner = getScriptRunner();

            String tenantDatabasePrefix = databaseProperties.getTenantDatabasePrefix();

            useDb(tenant, runner, tenantDatabasePrefix);
            String dataScript = tenantDatabasePrefix + "_" + tenant;
            runner.runScript(Resources.getResourceAsReader(String.format(SQL_RESOURCE_PATH, dataScript)));
        } catch (Exception e) {
            log.error("重置数据失败", e);
            return false;
        } finally {
            try {
                if (runner != null) {
                    runner.closeConnection();
                }
            } catch (Exception e) {
                log.error("关闭失败", e);
            }
            resetDatabase();
        }
        return true;
    }


    public void initDatabases(String tenant) {
        INIT_DATABASE_LIST.forEach((database) -> this.initDbMapper.createDatabase(StrUtil.join(StrUtil.UNDERLINE, database, tenant)));
    }

    public void initTables(String tenant) {
        ScriptRunner runner = null;
        try {
            runner = this.getScriptRunner();
            for (String database : INIT_DATABASE_LIST) {
                this.useDb(tenant, runner, database);
                runner.runScript(Resources.getResourceAsReader(String.format(SQL_RESOURCE_PATH, database)));
            }
        } catch (Exception e) {
            log.error("初始化表失败", e);
            throw new BizException(-1, "初始化表失败","Failed to initialize table");
        } finally {
            try {
                if (runner != null) {
                    runner.closeConnection();
                }
            } catch (Exception e) {
                throw new BizException(-1, "提交失败","Submit failed");
            }
        }
    }

    /**
     * 角色表
     * 菜单表
     * 资源表
     *
     * @param tenant
     */
    public void initData(String tenant) {
        ScriptRunner runner = null;
        try {
            runner = this.getScriptRunner();

            for (String database : INIT_DATABASE_LIST) {
                this.useDb(tenant, runner, database);
                String dataScript = database + "_data";
                runner.runScript(Resources.getResourceAsReader(String.format(SQL_RESOURCE_PATH, dataScript)));
            }
        } catch (Exception e) {
            log.error("初始化数据失败", e);
            throw new BizException(-1, "初始化数据失败","Failed to initialize data");
        } finally {
            try {
                if (runner != null) {
                    runner.closeConnection();
                }
            } catch (Exception e) {
                throw new BizException(-1, "提交失败","Submit failed");
            }
        }
    }

    public void resetDatabase() {
        ScriptRunner runner = null;
        try {
            runner = this.getScriptRunner();
            Reader reader = new StringReader("use " + this.defaultDatabase + ";");
            runner.runScript(reader);
        } catch (Exception e) {
            log.error("切换为默认数据源失败", e);
            throw new BizException(-1, "切换为默认数据源失败", "Switch to default data source failed");
        } finally {
            try {
                if (runner != null) {
                    runner.closeConnection();
                }
            } catch (Exception e) {
                throw new BizException(-1, "切换为默认数据源失败", "Switch to default data source failed");
            }
        }
    }

    public String useDb(String tenant, ScriptRunner runner, String database) {
        String db = StrUtil.join(StrUtil.UNDERLINE, database, tenant);
        Reader reader = new StringReader("use " + db + ";");
        runner.runScript(reader);
        return db;
    }

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    public ScriptRunner getScriptRunner() {
        try {
            Connection connection = this.dataSource.getConnection();
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setAutoCommit(false);
            //遇见错误是否停止
            runner.setStopOnError(true);
            /*
             * 按照那种方式执行 方式一：true则获取整个脚本并执行； 方式二：false则按照自定义的分隔符每行执行；
             */
            runner.setSendFullScript(true);
            // 设置是否输出日志，null不输出日志，不设置自动将日志输出到控制台
            // runner.setLogWriter(null);

            Resources.setCharset(Charset.forName("UTF8"));

//            设置分隔符 runner.setDelimiter(";");
            runner.setFullLineDelimiter(false);
            return runner;
        } catch (Exception ex) {
            throw new BizException(-1, "获取连接失败","Failed to get connection");
        }
    }

    @Override
    public boolean delete(List<String> tenantCodeList) {
        if (tenantCodeList.isEmpty()) {
            return true;
        }
        tenantCodeList.forEach((tenant) -> {
            String databasePrefix = databaseProperties.getTenantDatabasePrefix();
            String database = new StringBuilder().append(databasePrefix).append(StrPool.UNDERSCORE).append(tenant).toString();
            initDbMapper.dropDatabase(database);

            initDsService.removeDataSource(tenant);
        });

        return true;
    }
}
