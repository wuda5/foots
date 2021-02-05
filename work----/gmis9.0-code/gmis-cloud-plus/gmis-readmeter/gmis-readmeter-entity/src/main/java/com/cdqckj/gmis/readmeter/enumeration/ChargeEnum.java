package com.cdqckj.gmis.readmeter.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 抄表数据审核状态
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ChargeEnum", description = "收费-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChargeEnum implements BaseEnum {

    /**
     * 未收费
     */
    NO_CHARGE("待收费（已结算）","待收费,NO_CHARGE"),
    /**
     * 欠费
     */
    //ARREARS("欠费","欠费,Arrears"),
    /**
     * 已收费（待出票）
     */
    CHARGED("已收费（待出票）","已收费（待出票）,Charged"),
    /**
     * 已出票
     */
    INVOICED("已出票","已出票,Invoiced"),
    /**
     * 退费
     */
    REFUND("退费","退费,refund");

    //@JsonValue
    @ApiModelProperty(value = "描述（中英文）")
    private String desc;

    @ApiModelProperty(value = "编码")
    private String language;

    public static ChargeEnum match(String val, ChargeEnum def) {
        for (ChargeEnum enm : ChargeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static String getDesc(String code) {
        ChargeEnum[] businessModeEnums = values();
        for (ChargeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getLanguage();
            }
        }
        return null;
    }

    public static ChargeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ChargeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public static String[] getDescStr(){
        /*int type = 1;
        Map<String,String> map = BaseSelfEnum.getDescMap(ChargeEnum.values(),type);
        Collection<String> valueCollection = map.values();
        final int size = valueCollection.size();
        List<String> valueList = new ArrayList<String>(valueCollection);
        String[] valueArray = new String[size];
        map.keySet().toArray(valueArray);
        return valueArray;*/
        Object[]  obj = Arrays.stream(ChargeEnum.values()).map(ChargeEnum::getDesc).toArray();
        return Arrays.copyOf(obj, obj.length, String[].class);
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "TO_BE_CHARGED,ARREARS,CHARGED,INVOICED", example = "TO_BE_CHARGED")
    public String getCode() {
        return this.name();
    }

    /*@Override
    public void setDesc(String desc){
        this.desc = desc;
    }*/
}
