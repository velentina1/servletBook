package com.book.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class FormBeanUtil {
    public static <T> T formToBean(Map<String,String[]> map , Class<T> tClass){
        T t = null;
        try {
//            t = tClass.newInstance(); 已弃用
            Constructor<T> constructor = tClass.getDeclaredConstructor();//要求被操作的类必须具有无参数的构造函数
            t = constructor.newInstance();
            BeanUtils.populate(t,map);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}
