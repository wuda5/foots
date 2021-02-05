package com.cdqckj.gmis.common.domain.excell.rule;

import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidColRule;
import com.cdqckj.gmis.common.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/12 10:46
 * @remark: 这个列的验证为唯一的
 */
public class UniqueColRule implements ValidColRule {

    /** 验证的列 **/
    private Integer col;

    /** 开始的行 **/
    private Integer startRow;

    /** 结束的行 **/
    private Integer endRow;

    /** 失败说明 **/
    private String failDescribe;

    public UniqueColRule(Integer col, Integer startRow, Integer endRow, String fail) {
        this.col = col;
        this.startRow = startRow;
        this.endRow = endRow;
        this.failDescribe = fail;
    }

    @Override
    public Integer validCol() {
        return col;
    }

    @Override
    public Integer validStartRow() {
        return startRow;
    }

    @Override
    public Integer validEndRow() {
        return endRow;
    }

    @Override
    public String failDescribe() {
        return failDescribe;
    }

    @Override
    public List<ValidResult> validColData(List<String> colData) {

        List<ValidResult> colResult = new ArrayList<>(colData.size());
        HashSet<String> uniqueSet = new HashSet<>(colData.size());
        for (int row = 0; row < colData.size(); row ++) {
            String cellData = colData.get(row);
            ValidResult cellResult = new ValidResult();
            cellResult.setRow(row);
            cellResult.setCol(col);
            cellResult.setColSum(1);
            cellResult.setColDataList(Arrays.asList(cellData));
            cellResult.setStatus(true);
            if (StringUtil.isNoneBlank(cellData)){
                Boolean addResult = uniqueSet.add(cellData);
                if (addResult == false){
                    cellResult.setStatus(false);
                    cellResult.setInvalidDesc(failDescribe);
                }
            }
            colResult.add(cellResult);
        }
        return colResult;
    }
}
