package com.sustech.regency.db.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用于序列化<p>
 * 加上{@link JacksonAnnotationsInside @JacksonAnnotationsInside}才能被标记为组合注解，而Spring中则不需要，取决于框架底层的反射判断逻辑<p>
 * Jackson的核心判断方法是{@link com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector#isAnnotationBundle(Annotation) isAnnotationBundle}<p>
 * <a href="https://blog.csdn.net/wangpengfei_p/article/details/125564903">此部分的Jackson源码解析</a>
 */
@Target(FIELD)
@Retention(RUNTIME) //组合注解必须要加这个
@JacksonAnnotationsInside //Jackson中需要在自定义组合注解中加上该注解
@JsonFormat(pattern = "yyyy-MM-dd")
public @interface DateField {}
