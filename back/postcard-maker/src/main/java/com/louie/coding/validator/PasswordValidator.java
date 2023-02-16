package com.louie.coding.validator;


import com.louie.coding.entity.annotation.ValidPassword;
import com.louie.coding.util.RSAUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private String message;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        try {
            String passwordRaw;
            passwordRaw = RSAUtil.decrypt(password);
            Pattern pattern = Pattern.compile("^[^\\s]{6,16}$");
            Matcher matcher = pattern.matcher(passwordRaw);
            boolean matches = matcher.matches();

            if (!matches) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            }
            return matches;
        } catch (Exception e) {
            return false;
        }
    }
}
