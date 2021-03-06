package cn.wanghaomiao.seimi.utils;

import cn.wanghaomiao.seimi.core.CastToNumber;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现工具类，检查参数化类型的参数类型。
 */
public class GenericUtils {

    private static final Class<?>[] EMPTY_CLASSES = new Class<?>[0];
    private static final Map<Class,CastToNumber> numberClass = new HashMap<Class,CastToNumber>(){{
        put(int.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).intValue();
            }
        });
        put(Integer.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).intValue();
            }
        });
        put(long.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).longValue();
            }
        });
        put(Long.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).longValue();
            }
        });
        put(short.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).shortValue();
            }
        });
        put(Short.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).shortValue();
            }
        });
        put(float.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).floatValue();
            }
        });
        put(Float.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).floatValue();
            }
        });
        put(double.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).doubleValue();
            }
        });
        put(Double.class, new CastToNumber() {
            @Override
            public Object castTo(String ori) {
                return new BigDecimal(ori).doubleValue();
            }
        });
    }};

    /**
     * 从参数, 返回值, 基类的: Generic 类型信息获取传入的实际类信息。
     * 
     * @param genericType - Generic 类型信息
     * @return 实际类信息
     */
    public static Class<?>[] getActualClass(Type genericType) {

        if (genericType instanceof ParameterizedType) {

            Type[] actualTypes = ((ParameterizedType) genericType).getActualTypeArguments();
            Class<?>[] actualClasses = new Class<?>[actualTypes.length];

            int i = 0;
            while (i < actualTypes.length) {
                Type actualType = actualTypes[i];
                if (actualType instanceof Class<?>) {
                    actualClasses[i] = (Class<?>) actualType;
                } else if (actualType instanceof GenericArrayType) {
                    Type componentType = ((GenericArrayType) actualType).getGenericComponentType();
                    actualClasses[i] = Array.newInstance((Class<?>) componentType, 0).getClass();
                }
                i++;
            }

            return actualClasses;
        }

        return EMPTY_CLASSES;
    }

    /**
     * 判断给定类是否是支持的数字类型
     * @param cls
     * @return
     */
    public static boolean isNumber(Class cls){
        return numberClass.containsKey(cls);
    }

    public static Object castToNumber(Class cls,String val){
        return numberClass.get(cls).castTo(val);
    }
}
