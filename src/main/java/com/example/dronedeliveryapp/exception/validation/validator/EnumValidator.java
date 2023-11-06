package com.example.dronedeliveryapp.exception.validation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EnumConstraintValidator.class)
@Documented
@NotNull
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClazz();

    String message() default "must be any of enum {enum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
