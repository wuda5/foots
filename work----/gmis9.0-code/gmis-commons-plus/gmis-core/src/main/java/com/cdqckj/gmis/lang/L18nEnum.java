package com.cdqckj.gmis.lang;

public enum L18nEnum {
    ZH_CN(1,"zh"),
    EN_US(2,"en"),
    LANG(3,"lang");
     /**
     * 类型值
     */
     private int ordial;
     /**
     * 类型描述
     */
      private String l18nDesc;
      private L18nEnum(int ordial, String l18nDesc) {
         this.ordial = ordial;
         this.l18nDesc = l18nDesc;
      }
      public int getOrdial(){
         return this.ordial;
      }
      public String getL18nDesc(){
        return this.l18nDesc;
     }

}
