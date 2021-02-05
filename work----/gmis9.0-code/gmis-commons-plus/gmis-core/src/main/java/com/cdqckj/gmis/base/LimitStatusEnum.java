package com.cdqckj.gmis.base;

public enum LimitStatusEnum {
    LIMIT(1,"限制"),
    UNLIMITED(0,"不限制");
     /**
     * 类型值
     */
     private int value;
     /**
     * 类型描述
     */
      private String desc;
      LimitStatusEnum(int value, String desc) {
         this.value = value;
         this.desc = desc;
      }
      public int getValue(){
         return this.value;
      }
      public String getDesc(){
        return this.desc;
     }

}
