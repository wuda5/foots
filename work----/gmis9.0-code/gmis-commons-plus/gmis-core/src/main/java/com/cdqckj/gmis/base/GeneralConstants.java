package com.cdqckj.gmis.base;

import java.io.Serializable;

public class GeneralConstants implements Serializable  {
    private static final long serialVersionUID = GeneralConstants.class.hashCode();
    /**税率**/
    public static String TAX_RATE = "TAX_RATE";
    /**合计税额**/
    public static String TOTAL_TAX = "totalTax";
    /**合计金额**/
    public static String TOTAL_AMOUNT = "totalAmount";
    /**价税合计（小写）**/
    public static String PRICE_TAX_TOTAL_LOWER = "priceTaxTotalLower";
    /**价税合计（大写）**/
    public static String PRICE_TAX_TOTAL_UPPER = "priceTaxTotalUpper";
}
