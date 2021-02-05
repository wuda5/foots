package com.cdqckj.gmis.common.domain.excell.rule;

import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.ValidTypeEnum;
import com.cdqckj.gmis.common.domain.excell.rule.AbstractValidRule;
import com.cdqckj.gmis.utils.ValidatorUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: lijianguo
 * @time: 2020/9/30 9:26
 * @remark: 默认的验证算法
 */
@Log4j2
@Data
public class ValidRuleDefault extends AbstractValidRule {

    public ValidRuleDefault(Integer type, String validDesc, String emptyDesc, Integer colStart) {
        this(type,  validDesc, emptyDesc, colStart, 1);
    }

    public ValidRuleDefault(Integer type, String validDesc, String emptyDesc, Integer colStart, Integer colSum) {
        this.type = type;
        this.validDesc = validDesc;
        this.emptyDesc = emptyDesc;
        this.colStart = colStart;
        this.colSum = colSum;
    }

    @Override
    public Integer validColStart() {
        return colStart;
    }

    @Override
    public Integer validColSum() {
        return colSum;
    }

    @Override
    public int validType() {
        return type;
    }

    @Override
    public String validFailDesc() {
        return validDesc;
    }

    @Override
    public String validEmptyFailDesc() {
        return emptyDesc;
    }

    @Override
    public void validProcess(ValidResult validResult) {

        List<String> valueList = validResult.getColDataList();
        if (valueList.size() == 1) {
            String value = valueList.get(0);
            if (ValidTypeEnum.REGEX_ID_CARD.getValue().equals(validType())) {
                setResult(validResult, value, ValidatorUtil.REGEX_ID_CARD);
            } else if (ValidTypeEnum.REGEX_MOBILE.getValue().equals(validType())) {
                setResult(validResult, value, ValidatorUtil.REGEX_MOBILE);
            } else if (ValidTypeEnum.NUM.getValue().equals(validType())) {
                setResult(validResult, value, ValidatorUtil.REGEX_NUM);
            }else if (ValidTypeEnum.DATE.getValue().equals(validType())) {
                setResult(validResult, value, ValidatorUtil.DATE);
            } else if (ValidTypeEnum.customerName.getValue().equals(validType())) {
                setResult(validResult, value, ValidatorUtil.customerName);
            }
            else if (ValidTypeEnum.NOT_VALID.getValue().equals(validType())) {
                log.info("不需要验证");
            } else {
                log.info("没有处理的验证");
            }
        }else {
            log.info("多列的验证");
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 10:54
     * @remark 具体的验证
     */
    private void setResult(ValidResult validResult, String value, String regex) {
        if (value == null){
            return;
        }
        Boolean result = Pattern.matches(regex, value);
        if (result == false) {
            validResult.setStatus(false);
            validResult.setInvalidDesc(validFailDesc());
        }
    }
}
