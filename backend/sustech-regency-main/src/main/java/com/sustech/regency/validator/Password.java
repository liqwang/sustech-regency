package com.sustech.regency.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验顺序：先按顺序校验元注解，再用自身的Validator校验<p>
 * 但是如果使用了{@code @}{@link ReportAsSingleViolation}，那么报错信息并不会使用底层元注解的信息，而是会被上层的默认信息覆盖，影响了注解复用<p>
 * 参考源码：
 * <ul>
 *     <li>{@link org.hibernate.validator.internal.engine.constraintvalidation.ComposingConstraintTree#prepareFinalConstraintViolations}</li>
 *     <li>{@link org.hibernate.validator.internal.engine.constraintvalidation.ComposingConstraintTree#reportAsSingleViolation}</li>
 * </ul>
 */
@SuppressWarnings("JavadocReference")
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
//@ReportAsSingleViolation //约束不满足会直接停止，不再继续判断
//@NotEmpty
//@Range(min = 8,max = 30)
public @interface Password {

    String message() default "密码格式错误";

    Class<?>[] groups() default {}; //没有这行会报ConstraintDefinitionException

    Class<? extends Payload>[] payload() default {}; //没有这行会报ConstraintDefinitionException
}