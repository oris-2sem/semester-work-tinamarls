package ru.itis.delivery.validation.validator;

import lombok.RequiredArgsConstructor;
import ru.itis.delivery.repositories.UserRepository;
import ru.itis.delivery.validation.constraints.EmailUnique;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<EmailUnique, String>{

    private final UserRepository userRepository;

    private static final String MESSAGE = "{user.email.exists}";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        buildConstraint(constraintValidatorContext);
        return !userRepository.existsByLogin(email);
    }

    private void buildConstraint(ConstraintValidatorContext context){
        context.buildConstraintViolationWithTemplate(MESSAGE)
//                .addPropertyNode("login")
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
