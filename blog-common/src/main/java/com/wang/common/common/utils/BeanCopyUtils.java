package com.wang.common.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
public class BeanCopyUtils extends BeanUtils {
        /**
         * @description <p>获取到对象中属性为null的属性名  </P>
         * @param source 要拷贝的对象
         * @return
         */
        private static String[] getNullPropertyNames(Object source) {
            final BeanWrapper src = new BeanWrapperImpl(source);
            java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

            Set<String> emptyNames = new HashSet<String>();
            for (java.beans.PropertyDescriptor pd : pds) {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null) {
                    emptyNames.add(pd.getName());
                }
            }
            String[] result = new String[emptyNames.size()];
            return emptyNames.toArray(result);
        }

        /**
         * @description <p> 拷贝非空对象属性值 </P>
         * @param source 源对象
         * @param target 目标对象
         */
        public static void copyPropertiesIgnoreNull(Object source, Object target) {
            BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        }

}
