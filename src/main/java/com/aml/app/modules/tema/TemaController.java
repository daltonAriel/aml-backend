package com.aml.app.modules.tema;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.tema.dto.ActualizarTemaRequest;
import com.aml.app.modules.tema.dto.CargarLogoRequest;
import com.aml.app.modules.tema.dto.TemaResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/empresas/{empresaId}/temas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TemaController {

    private final TemaService temaService;

    @GetMapping
    public ResponseEntity<ApiResponse<TemaResponse>> obetenerTemaPorEmpresaId(@PathVariable UUID empresaId) {
        TemaResponse temaResponse = temaService.obtenerPorEmpresaId(empresaId);
        return ResponseEntity.ok(ApiResponse.success(temaResponse, "Tema Obtenido"));
    }

    @PutMapping(value = "/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<TemaResponse>> actualizarLogo(@PathVariable UUID empresaId,
            @Valid @ModelAttribute CargarLogoRequest request) {
        try {
            byte[] logoBytes = request.getLogo().getBytes();
            TemaResponse temaResponse = temaService.cargarLogo(empresaId, logoBytes);
            return ResponseEntity.ok(ApiResponse.success(temaResponse, "Logo actualizado exitosamente"));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo de imagen: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<TemaResponse>> actualizarTema(@PathVariable UUID empresaId,
            @Valid @RequestBody ActualizarTemaRequest actualizarTemaRequest) {
        TemaResponse temaResponse = temaService.actualizar(empresaId, actualizarTemaRequest);
        return ResponseEntity.ok(ApiResponse.success(temaResponse, "Tema actualizado exitosamente"));
    }

}
