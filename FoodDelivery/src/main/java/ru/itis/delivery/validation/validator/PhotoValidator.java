package ru.itis.delivery.validation.validator;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.delivery.validation.constraints.PhotoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PhotoValidator implements ConstraintValidator<PhotoConstraint, MultipartFile> {
    private static final String MESSAGE = "{newProduct.photo.isEmpty}";

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        buildConstraint(constraintValidatorContext, "photo");
        return !Objects.requireNonNull(file.getOriginalFilename()).isEmpty();
    }

    private void buildConstraint(ConstraintValidatorContext context, String fieldName){
        context.buildConstraintViolationWithTemplate(MESSAGE)
//                .addPropertyNode(fieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

}
