package com.cdqckj.gmis.calculate.domain.cal;

import com.cdqckj.gmis.calculate.domain.util.BigDecimalUtil;
import com.cdqckj.gmis.common.utils.ReflectionUtil;
import com.cdqckj.gmis.calculate.vo.PriceSchemeVO;
import com.cdqckj.gmis.calculate.vo.SettlementVO;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/25 13:18
 * @remark: 请输入类说明
 */
@Data
@Log4j2
public class GasMoneyCal {

    @ApiModelProperty(value = "气量的KEY")
    private static final String GAS_KEY = "gas";

    @ApiModelProperty(value = "价格的KEY")
    private static final String PRICE_KEY = "price";

    @ApiModelProperty(value = "金额KEY")
    private static final String MONEY_KEY = "money";

    @ApiModelProperty(value = "历史气量")
    private BigDecimal hGas;

    @ApiModelProperty(value = "当前的气量")
    private BigDecimal cGas;

    @ApiModelProperty(value = "计价方案 固定和阶梯两种方案")
    private String priceType;

    @ApiModelProperty(value = "当前计费价格方案")
    private PriceSchemeVO priceScheme;

    @ApiModelProperty(value = "将价格方案转换为列表形式")
    List<GasGradient> gasGradientList;

    /**
     * @auth lijianguo
     * @date 2020/9/25 16:19
     * @remark 构造函数
     */
    public GasMoneyCal(BigDecimal hGas, BigDecimal cGas, String priceType, PriceSchemeVO priceScheme) {
        this.hGas = hGas;
        this.cGas = cGas;
        this.priceType = priceType;
        this.priceScheme = priceScheme;
        this.gasGradientList = covertToGasGradientList();
    }

    /**
     * @auth lijianguo
     * @date 2020/9/27 14:52
     * @remark 添加阶梯递增的用气量 如果为null 则表示不需要递增
     */
    public void gasGradientAddForPeople(BigDecimal gasAddAmount){
        if (gasAddAmount == null) {
            return;
        }
        for (GasGradient gradient: gasGradientList){
            gradient.setGas(gradient.getGas().add(gasAddAmount));
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 16:21
     * @remark 计算价格--先移除价格之外的区间
     */
    public SettlementVO calMoney(){

        SettlementVO settlementVO = initSettlementVO();
        removeBeforeGasGradient();
        int calStrategyType = getCalStrategyType();
        GasMeterCalMoney calLadderMoney = produceCalLadderMoneyFactory(calStrategyType);
        BigDecimal resultMoney = calLadderMoney.calMoney(hGas, cGas);
        settlementVO.setMoney(resultMoney);
        return settlementVO;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 13:28
     * @remark 将价格转换为列表的价格--一条数据转换为多条
     */
    public List<GasGradient> covertToGasGradientList(){

        List<GasGradient> gasGradientList = new ArrayList<>();
        for (int i = 0; i < 6; i ++){

            String gasKey = GAS_KEY + (i + 1);
            String priceKey = PRICE_KEY + (i + 1);
            BigDecimal gasValue = BigDecimalUtil.nullInitZero(ReflectionUtil.getFieldValueByFieldName(priceScheme, gasKey));
            BigDecimal priceValue =  BigDecimalUtil.nullInitZero(ReflectionUtil.getFieldValueByFieldName(priceScheme, priceKey));
            GasGradient gradient = new GasGradient();
            gradient.setGas(priceScheme.getGas1());
            gradient.setPrice(priceScheme.getPrice1());
            gasGradientList.add(gradient);
            log.info("初始化阶梯的列表的值: {} {} {} {}", priceKey, priceKey, gasValue, priceValue);
        }
        return gasGradientList;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/27 9:13
     * @remark 生成对应的计价方案-固定和阶梯计算，阶梯有六种算法
     */
    private GasMeterCalMoney produceCalLadderMoneyFactory(int calStrategyType) {

        if (PriceType.FIXED_PRICE.getCode().equals(priceType)){
            return new CalFixMoney(priceScheme.getFixedPrice());
        }else {
            if (calStrategyType == 0) {
                return new CalLadderMoneyFirst(gasGradientList);
            } else if (calStrategyType == 1) {
                return new CalLadderMoneySec(gasGradientList);
            } else if (calStrategyType == 2) {
                return new CalLadderMoneyThird(gasGradientList);
            } else if (calStrategyType == 3) {
                return new CalLadderMoneyFourth(gasGradientList);
            } else if (calStrategyType == 4) {
                return new CalLadderMoneyFifth(gasGradientList);
            } else {
                return new CalLadderMoneySixth(gasGradientList);
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/27 9:10
     * @remark 计算价格的算法策略的类型
     */
    private int getCalStrategyType() {
        BigDecimal hcGas = hGas.add(cGas);
        for (int calStrategyType = 0; calStrategyType < gasGradientList.size(); calStrategyType ++){
            GasGradient gradient = gasGradientList.get(calStrategyType);
            if (hcGas.compareTo(gradient.getGas()) < 0){
                return calStrategyType;
            }
        }
        return gasGradientList.size() - 1;
    }

    /**
     * @auth lijiangu
     * @date 2020/9/27 9:06
     * @remark 根据列表的算法移除小于这个价格的梯度，公式的计算，至少保留一个梯度
     */
    private void removeBeforeGasGradient() {
        Iterator iterable = gasGradientList.iterator();
        while (iterable.hasNext()) {
            GasGradient gradient = (GasGradient) iterable.next();
            if (hGas.compareTo(gradient.getGas()) <= 0 || gasGradientList.size() <= 1){
                break;
            }else {
                iterable.remove();
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/28 10:30
     * @remark 初始化 SettlementVO 每个区间的价格 小于就设置为0
     */
    public SettlementVO initSettlementVO(){

        SettlementVO settlementVO = new SettlementVO();
        BigDecimal hcGas = hGas.add(cGas);
        for (int i = 0; i < gasGradientList.size(); i ++){
            String gasKey = GAS_KEY + (i + 1);
            String priceKey = PRICE_KEY + (i + 1);
            String moneyKey = MONEY_KEY + (i + 1);
            GasGradient gradient = gasGradientList.get(i);
            BigDecimal gasValue = BigDecimalUtil.intZero();
            BigDecimal priceValue = BigDecimalUtil.intZero();
            BigDecimal moneyValue = BigDecimalUtil.intZero();
            if (hcGas.compareTo(gradient.getGas()) >= 0){

                gasValue = gasGradientList.get(i).getGas();
                priceValue = gasGradientList.get(i).getGas();
                moneyValue = gasValue.multiply(priceValue);
            }
            ReflectionUtil.setFieldValueByFieldName(settlementVO, gasKey, gasValue);
            ReflectionUtil.setFieldValueByFieldName(settlementVO, priceKey, priceValue);
            ReflectionUtil.setFieldValueByFieldName(settlementVO, moneyKey, moneyValue);
        }
        settlementVO.setSettlementTime(LocalDate.now());
        return settlementVO;
    }
}
