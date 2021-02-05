package com.cdqckj.gmis.common.domain.Separate;

import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: lijianguo
 * @date: 2020/3/31
 * @time: 10:48 上午
 * @description : 这个是用来映射为map的工具类-线程不安全的类型
 */
@Log4j2
public class SeparateListData<T extends SeparateKey>implements Serializable{

    /**
     * 映射的map
     **/
    private Map<String, T> dataIndexMap = new HashMap <>();

    /**
     * @auth lijianguo
     * @date 2020/10/9 11:47
     * @remark 构造函数
     */
    public SeparateListData(List<T> dataList) {
        for (T t: dataList){
            dataIndexMap.put(t.indexKey(),t);
        }
    }

    /**
    * @author lijianguo
    * 2020/3/31 - 1:37 下午
    * 获取一个数据
    **/
    public T getTheDataByKey(String key){
        return dataIndexMap.get(key);
    }

    /**
    * @author lijianguo
    * 2020/5/28 - 11:04 上午
    * 删除by key 返回剩余的数据
    **/
    public Integer deleteBySeparateKey(List<T> dataList){

        for (T t: dataList){
            dataIndexMap.remove(t.indexKey());
        }
        return dataIndexMap.size();
    }

    /**
    * @author lijianguo
    * 2020/3/31 - 2:13 下午
    * 删除多个key的数据
    **/
    public Integer deleteByKey(List<String> keyList){

        for (String key: keyList) {
            dataIndexMap.remove(key);
        }
        return dataIndexMap.size();
    }

    /**
    * @author lijianguo
    * 2020/5/28 - 10:59 上午
    * 删除一个key的数据
    **/
    public T deleteByKey(String key){
        return dataIndexMap.remove(key);
    }

    /**
    * @author lijianguo
    * 2020/5/28 - 11:29 上午
    * 包含这个key
    **/
    public Boolean containSeparateKey(List<T> dataList){
        for (T t: dataList){
            if (dataIndexMap.containsKey(t.indexKey()) == false){
                return false;
            }
        }
        return true;
    }

    /**
    * @author lijianguo
    * 2020/5/28 - 11:37 上午
    * 包含这个key
    **/
    public Boolean containKey(List<String> keyList){
        for (String s: keyList){
            if (dataIndexMap.containsKey(s) == false){
                return false;
            }
        }
        return true;
    }

    /**
     * @author lijianguo
     * 2020/5/28 - 11:37 上午
     * 包含这个key
     **/
    public Boolean containKey(String key){

        if (dataIndexMap.containsKey(key) == false){
            return false;
        }else {
            return true;
        }
    }

    /**
    * @author lijianguo
    * 2020/3/31 - 2:15 下午
    * 获取数据
    **/
    public List<T> getAllData(){
        return new ArrayList<T>(dataIndexMap.values());
    }

    /**
    * @author lijianguo
    * 2020/4/20 - 12:00 下午
    * 获取所有的主键数据
    **/
    public List<String> getAllKeyData(){

        List<T> dataList = getAllData();
        List<String> keyList = new ArrayList <>(dataList.size());
        for (T t: dataList){
            keyList.add(t.indexKey());
        }
        return keyList;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 14:35
     * @remark 添加一个数据
     */
    public void addData(T t){
        T data = dataIndexMap.get(t.indexKey());
        if (data != null){
            throw new RuntimeException("该数据存在");
        }
        dataIndexMap.put(t.indexKey(),t);
    }

}
