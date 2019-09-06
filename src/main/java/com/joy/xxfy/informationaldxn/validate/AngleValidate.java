package com.joy.xxfy.informationaldxn.validate;

import com.joy.xxfy.informationaldxn.validate.annotates.Angle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class AngleValidate implements ConstraintValidator<Angle, BigDecimal> {
    private static final BigDecimal LOW = new BigDecimal(-360);
    private static final BigDecimal HIGH = new BigDecimal(360);

    @Override
    public void initialize(Angle constraintAnnotation) {

    }
    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        if(bigDecimal == null){
            return true;
        }
        if(bigDecimal.compareTo(LOW) < 0){
            return false;
        }
        return bigDecimal.compareTo(HIGH) <= 0;
    }
}
