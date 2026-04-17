package com.aml.app.shared.validation.fileImageValidation;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileImageValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {
    private long maxSize;
    private List<String> allowedTypes;

    @Override
    public void initialize(ValidImageFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = Arrays.asList(constraintAnnotation.allowedTypes());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty())
            return true; // @NotNull se encarga de esto

        // 1. Validar Tamaño
        if (file.getSize() > maxSize) {
            return false;
        }

        // 2. Validar Tipo Archivo
        return allowedTypes.contains(file.getContentType());
    }

}
