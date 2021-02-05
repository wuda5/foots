package com.cdqckj.gmis.common.domain.excell;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/18 13:04
 * @remark: 每一行的数据,行号+数据
 */
@Data
public class RowDataInfo {

    /** 行号 **/
    private Integer row;

    /** 行号 **/
    private List<String> colDataList = new ArrayList<>();

    public RowDataInfo(Integer row) {
        this.row = row;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/18 13:07
    * @remark 添加值
    */
    public void addColValue(String str){
        colDataList.add(str);
    }
}
