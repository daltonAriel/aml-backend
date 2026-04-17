package com.aml.app.modules.tema.dto;

import org.springframework.web.multipart.MultipartFile;

import com.aml.app.shared.validation.fileImageValidation.ValidImageFile;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CargarLogoRequest {
    @NotNull(message = "El archivo de imagen es obligatorio.")
    @ValidImageFile(maxSize = 5 * 1024
            * 1024, message = "La imagen debe ser una imagen válida (JPEG, PNG) y no debe exceder los 5 MB.")
    private MultipartFile logo;
}
