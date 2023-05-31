package ru.itis.delivery.validation.constraints;

import ru.itis.delivery.validation.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface EmailUnique {
    String message() default "{user.email.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
