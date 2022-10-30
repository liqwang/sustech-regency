package com.sustech.regency.web.annotation;

import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用于反序列化
 */
@Target({PARAMETER,FIELD})
@Retention(RUNTIME)
@DateTimeFormat(pattern="yyyy-MM-dd")
public @interface DateParam {}
