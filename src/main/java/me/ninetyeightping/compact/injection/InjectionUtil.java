package me.ninetyeightping.compact.injection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InjectionUtil {

    public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InjectionConfig.class);

    public static <T> T get(Class<T> tClass) {
        return context.getBean(tClass);
    }
}
