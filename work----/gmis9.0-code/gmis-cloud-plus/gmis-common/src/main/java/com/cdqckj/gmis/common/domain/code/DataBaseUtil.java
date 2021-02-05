package com.cdqckj.gmis.common.domain.code;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.cdqckj.gmis.context.BaseContextHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: lijianguo
 * @time: 2020/12/23 11:38
 * @remark: 从数据库获取数据
 */
@Log4j2
@Component
public class DataBaseUtil {

    @Autowired(required = false)
    private SqlSessionFactory sqlSessionFactoryInit;

    /** 请求的工厂 **/
    private static SqlSessionFactory sqlSessionFactory;

    /** 线程池 **/
    private static ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    @PostConstruct
    public void initStaticData() {
        DataBaseUtil.sqlSessionFactory = sqlSessionFactoryInit;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/30 8:43
     * @remark 请添加函数说明
     */
    public static List<String> getOneColData(String sql, String colName){
        String tenant = BaseContextHandler.getTenant();
        FutureTask<List<String>> futureTask = new FutureTask<>(() -> getOneColDataReal(sql, colName, tenant));
        try {
            cachedThreadPool.execute(futureTask);
            List<String> result = futureTask.get();
            return result;
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/24 9:50
     * @remark 得到一个列的数据
     */
    public static List<String> getOneColDataReal(String sql, String colName, String tenant){
        SqlSession sqlSession = null;
        try {
            DynamicDataSourceContextHolder.push(tenant);
            sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return covertOneColResultSet(resultSet, colName);
        }catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }finally {
            closeSqlSession(sqlSession);
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/23 11:43
     * @remark 查询数据
     */
    public static List<List<String>> getMulColData(String sql, String ... cols){

        HashMap hashMap = getDataBaseResultSet(sql);
        ResultSet resultSet = (ResultSet) hashMap.get("resultSet");
        List<List<String>> allData = covertResultSet(resultSet, cols);
        SqlSession sqlSession = (SqlSession) hashMap.get("sqlSession");
        closeSqlSession(sqlSession);
        return allData;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/24 9:58
     * @remark 获取数据库的数据
     */
    private static HashMap getDataBaseResultSet(String sql){
        SqlSession sqlSession = null;
        try {
            String tenant = BaseContextHandler.getTenant();
            DynamicDataSourceContextHolder.push(tenant);
            sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap hashMap = new HashMap();
            hashMap.put("resultSet", resultSet);
            hashMap.put("sqlSession", sqlSession);
            return hashMap;
        }catch (Exception e) {
            log.error(e);
            closeSqlSession(sqlSession);
            throw new RuntimeException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/23 11:48
     * @remark 得到固定的列的数据
     */
    private static List<List<String>> covertResultSet(ResultSet resultSet, String ... cols) {
        try {
            List<List<String>> result = new ArrayList(resultSet.getFetchSize());
            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                for (String colName: cols){
                    String value = resultSet.getString(colName);
                    row.add(value);
                }
                result.add(row);
            }
            return result;
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/24 10:01
     * @remark 得到一列的数据
     */
    private static List<String> covertOneColResultSet(ResultSet resultSet, String colName){
        try {
            List<String> result = new ArrayList(resultSet.getFetchSize());
            while (resultSet.next()) {
                String value = resultSet.getString(colName);
                if (value != null) {
                    result.add(value);
                }
            }
            return result;
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/26 14:04
     * @remark 关闭连接
     */
    private static void closeSqlSession(SqlSession sqlSession){
        if(sqlSession != null){
            sqlSession.close();
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/28 15:37
    * @remark 运行sql
    */
    public static Boolean runSql(String sql){

        String tenant = BaseContextHandler.getTenant();
        DynamicDataSourceContextHolder.push(tenant);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection connection = sqlSession.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException exception) {
            log.error(exception);
            closeSqlSession(sqlSession);
            throw new RuntimeException("执行sql错误");
        }finally {
            DynamicDataSourceContextHolder.poll();
        }
        closeSqlSession(sqlSession);
        return true;
    }

}
