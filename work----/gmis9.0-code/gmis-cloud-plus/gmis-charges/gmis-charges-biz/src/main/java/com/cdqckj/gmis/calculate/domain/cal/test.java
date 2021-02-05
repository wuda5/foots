package com.cdqckj.gmis.calculate.domain.cal;

import com.cdqckj.gmis.calculate.vo.PriceSchemeVO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/9/25 13:14
 * @remark: 请输入类说明
 */
@Slf4j
public class test {

    public static void main(String[] args) {

        //本次结算历史用气量、当前用气量
        BigDecimal hGas = new BigDecimal(12.0);
        BigDecimal cGas = new BigDecimal(25.0);

        PriceSchemeVO priceScheme = new PriceSchemeVO();

        priceScheme.setGas1(new BigDecimal(10));
        priceScheme.setPrice1(new BigDecimal(10));

        priceScheme.setGas2(new BigDecimal(20));
        priceScheme.setPrice2(new BigDecimal(20));

        priceScheme.setGas3(new BigDecimal(30));
        priceScheme.setPrice3(new BigDecimal(30));

        priceScheme.setGas4(new BigDecimal(40));
        priceScheme.setPrice4(new BigDecimal(40));

        priceScheme.setGas5(new BigDecimal(50));
        priceScheme.setPrice5(new BigDecimal(50));

        priceScheme.setGas6(new BigDecimal(60));
        priceScheme.setPrice6(new BigDecimal(60));

        priceScheme.setGas7(new BigDecimal(70));
        priceScheme.setPrice7(new BigDecimal(70));

        GasMoneyCal cal = new GasMoneyCal(hGas, cGas, null, priceScheme);
//        SettlementVO settlementVO = cal.calMoney();
//        log.info("result is {}", settlementVO);

    }
}
