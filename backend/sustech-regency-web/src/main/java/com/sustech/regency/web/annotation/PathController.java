package com.sustech.regency.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@RestController
@RequestMapping
@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
public @interface PathController {
	@AliasFor(annotation=RequestMapping.class, attribute="path")
	String[] value() default {};
}
