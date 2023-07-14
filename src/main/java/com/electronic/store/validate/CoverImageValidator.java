package com.electronic.store.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoverImageValidator implements ConstraintValidator<CoverImageValid,String> {

    private static Logger logger = LoggerFactory.getLogger(CoverImageValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("Message from invalid coverImage : {}" + value);
        if (value.isBlank()) {
            return false;
        } else {
            return true;
        }
    }
}