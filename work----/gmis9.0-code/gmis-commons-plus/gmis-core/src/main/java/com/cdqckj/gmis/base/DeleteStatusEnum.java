package com.cdqckj.gmis.base;

public enum DeleteStatusEnum {
    DELETE(1,"删除"),
    NORMAL(0,"正常");
     /**
     * 类型值
     */
     private int value;
     /**
     * 类型描述
     */
      private String desc;
      DeleteStatusEnum(int value, String desc) {
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
