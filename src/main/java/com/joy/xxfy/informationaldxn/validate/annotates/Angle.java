package com.joy.xxfy.informationaldxn.validate.annotates;

import com.joy.xxfy.informationaldxn.validate.AngleValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 角度
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = AngleValidate.class)
public @interface Angle {
    String message() default "angle is not validated(should be in [-360,360].";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
