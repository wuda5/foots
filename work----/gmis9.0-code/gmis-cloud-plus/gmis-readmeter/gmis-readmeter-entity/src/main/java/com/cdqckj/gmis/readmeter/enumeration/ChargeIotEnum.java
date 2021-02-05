package com.cdqckj.gmis.readmeter.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value = "ChargeIotEnum", description = "收费-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChargeIotEnum implements BaseEnum {

    /**
     * 未收费
     */
    NO_CHARGE("待收费","待收费,NO_CHARGE"),
    /**
     * 欠费
     */
    //ARREARS("欠费","欠费,Arrears"),
    /**
     * 已收费（待出票）
     */
    CHARGED("已收费","已收费,Charged"),
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

    public static ChargeIotEnum match(String val, ChargeIotEnum def) {
        for (ChargeIotEnum enm : ChargeIotEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static String getDesc(String code) {
        ChargeIotEnum[] businessModeEnums = values();
        for (ChargeIotEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getLanguage();
            }
        }
        return null;
    }

    public static ChargeIotEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ChargeIotEnum val) {
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
        Object[]  obj = Arrays.stream(ChargeIotEnum.values()).map(ChargeIotEnum::getDesc).toArray();
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
