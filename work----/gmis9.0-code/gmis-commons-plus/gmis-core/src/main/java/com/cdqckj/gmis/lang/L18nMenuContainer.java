package com.cdqckj.gmis.lang;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   * @description 国际化菜单数据容器
 *   * @autor Mr.Yang
 *   * @date 2020年06月8日
 *  
 */
public class L18nMenuContainer {
    /**
   * 中文容器
   */
    public static Map<String, Object> L18N_ZH_MAP = new ConcurrentHashMap<>();
    /**
    * 英文容器
    */
    public static Map<String, Object> L18N_EN_MAP = new ConcurrentHashMap<>();

    public static Map<String, Object> LANG_MAP = new ConcurrentHashMap<>();

    public volatile static Integer Lang = 1;

     /**
     * 清空
     */
    protected static void clearAll() {
      if (!L18N_EN_MAP.isEmpty()) {
          L18N_EN_MAP.clear();
      }
      if (!L18N_ZH_MAP.isEmpty()) {
          L18N_ZH_MAP.clear();
      }
    }
    /**
   * 获取容器
   * @return
   */
    public static Map<String, Object> getContainerByType(int type) {
        if (type == L18nEnum.EN_US.getOrdial()) {
             return L18N_EN_MAP;
        } else if (type == L18nEnum.ZH_CN.getOrdial()) {
             return L18N_ZH_MAP;
        }
        throw new RuntimeException("get lang type error");
    }
}
