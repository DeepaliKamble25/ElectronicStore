package com.electronic.store.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CoverImageValidator.class)
public @interface  CoverImageValid {

    String message() default "Inavlide cover Image Name !!!";

    Class<?>[]  groups()  default   {};


    Class<? extends Payload>[]  payload()  default   {};




}
