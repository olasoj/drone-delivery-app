package com.example.dronedeliveryapp.exception.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class EnumConstraintValidator implements ConstraintValidator<EnumValidator, String> {
    private Set<String> values = new HashSet<>();

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        values = Stream.of(constraintAnnotation.enumClazz().getEnumConstants())
                .map(Enum::name)
                .collect(toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return values.contains(value);
    }

}