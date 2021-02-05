package com.cdqckj.gmis.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.model.RemoteData;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.data.domain.Page;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import com.cdqckj.gmis.scale.annotation.FieldNoScale;

/**
 * 字符串帮助类
 *
 * @author gmis
 * @date 2019-07-16 22:09
 */
@Slf4j
public class StrHelper {
    public static String getOrDef(String val, String def) {
        return StrUtil.isEmpty(val) ? def : val;
    }

    /**
     * 有 任意 一个 Blank
     *
     * @param css CharSequence
     * @return boolean
     */
    public static boolean isAnyBlank(final CharSequence... css) {
        if (ObjectUtil.isEmpty(css)) {
            return true;
        }
        return Stream.of(css).anyMatch(StrUtil::isBlank);
    }

    /**
     * 是否全非 Blank
     *
     * @param css CharSequence
     * @return boolean
     */
    public static boolean isNoneBlank(final CharSequence... css) {
        if (ObjectUtil.isEmpty(css)) {
            return false;
        }
        return Stream.of(css).allMatch(StrUtil::isNotBlank);
    }


    /**
     * 处理小数位数
     */
    public static void convertBigDecimalDigits(Object data,int count) {

        try {
            if(count>10) return ;
            if(data==null){
                return ;
            }
            Class tc = data.getClass();
            if(tc==String.class){
                return;
            }else if(tc.isPrimitive()){
                return;
            }else if(tc==BigDecimal.class){
                if(tc.getAnnotation(FieldNoScale.class)!=null){
                    ((BigDecimal)data).setScale(4);
                }else{
                    ((BigDecimal)data).setScale(2);
                }
            }else if(data instanceof  IPage){
//                System.out.println("分页对象：" + tc.getName());
                count++;
                IPage page=(IPage)data;
                convertBigDecimalDigits(page.getRecords(),count);
            }else  if(tc==List.class || tc==ArrayList.class){
                count++;
                for (Object datat : (List)data) {
                    convertBigDecimalDigits(datat,count);
                }
            }else{
                if(tc.getName().indexOf("com.cdqckj.gmis")>=0){
                    Field[] fields = tc.getDeclaredFields();
                    count++;
                    for (Field f : fields) {
                        f.setAccessible(true);
                        Class fc = f.getType();
                        if(fc==BigDecimal.class){
                            if(f.get(data)!=null){
                                if(f.getAnnotation(FieldNoScale.class)!=null){
                                    f.set(data,((BigDecimal)f.get(data)).setScale(4, RoundingMode.DOWN));
                                }else{
                                    f.set(data,((BigDecimal)f.get(data)).setScale(2, RoundingMode.DOWN));
                                }
                            }
                        }else if(tc.isPrimitive()){
                            continue;
                        }else if(fc==List.class || fc==ArrayList.class){
                            convertBigDecimalDigits(f.get(data),count);
                        } else {
                            //独立判断其他类型太多了，直接判断是否是业务代码类
                            if(fc.getName().indexOf("com.cdqckj.gmis")>=0){
                                convertBigDecimalDigits(f.get(data),count);
                            }
                        }
                    }
                }
//                System.out.println("循环完成");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
