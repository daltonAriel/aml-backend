package com.aml.app.shared.validation.fileImageValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileImageValidator.class)
public @interface ValidImageFile {
    String message() default "Archivo no válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 5MB Max por defecto
    long maxSize() default 1024 * 1024 * 5;

    // Tipos de archivo permitidos por defecto
    String[] allowedTypes() default { "image/jpeg", "image/png" };
}
