package com.joy.xxfy.informationaldxn.publish.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 更新操作工具类
 *
 * @author Alan
 */
public class JoyBeanUtil {

    /**
     * 拷贝source到target，不覆盖target中有值的属性,相等于只覆盖target中的空值属性
     */
    public static void copyPropertiesIgnoreTargetNotNullProperties(Object source, Object target) {
        if (source == null) {
            return;
        }
        BeanUtils.copyProperties(source, target, getNoNullProperties(target));
    }

    /**
     * 拷贝source到target，排除source中的空值属性，相等于只让source的有值的属性覆盖
     */
    public static void copyPropertiesIgnoreSourceNullProperties(Object source, Object target) {
        if (source == null) {
            return;
        }
        BeanUtils.copyProperties(source, target, getNullProperties(source));
    }


    private static String[] getNullProperties(Object obj) {
        Field[] field = getAllFields(obj.getClass());
        Set<String> emptyName = new HashSet<>();
        for (int j = 0; j < field.length; j++) {
            String name = field[j].getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String type = field[j].getGenericType().toString();
            Method m;
            Object value;
            if ("SerialVersionUID".equals(name) || "CreateTime".equals(name)) {
                continue;
            }
            try {
                m = obj.getClass().getMethod("get" + name);
                value = m.invoke(obj);
                if (value == null) {
                    name = name.substring(0, 1).toLowerCase() + name.substring(1);
                    emptyName.add(name);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println(emptyName);
        String[] result = new String[emptyName.size()];
        return emptyName.toArray(result);
    }

    private static String[] getNoNullProperties(Object obj) {
        Field[] field = getAllFields(obj.getClass());
        Set<String> noEmptyName = new HashSet<>();
        for (int j = 0; j < field.length; j++) {
            String name = field[j].getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String type = field[j].getGenericType().toString();
            Method m;
            Object value;
            if ("SerialVersionUID".equals(name) || "CreateTime".equals(name)) {
                continue;
            }
            try {
                m = obj.getClass().getMethod("get" + name);
                value = m.invoke(obj);
                if (value != null) {
                    name = name.substring(0, 1).toLowerCase() + name.substring(1);
                    noEmptyName.add(name);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }

    private static Field[] getAllFields(final Class<?> cls) {
        final List<Field> allFieldsList = getAllFieldsList(cls);
        return allFieldsList.toArray(new Field[allFieldsList.size()]);
    }

    private static List<Field> getAllFieldsList(final Class<?> cls) {
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (final Field field : declaredFields) {
                allFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

}
