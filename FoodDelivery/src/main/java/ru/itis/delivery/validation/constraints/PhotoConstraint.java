package ru.itis.delivery.validation.constraints;

import ru.itis.delivery.validation.validator.PhotoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhotoValidator.class)
public @interface PhotoConstraint {
    String message() default "{newProduct.photo.isEmpty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
