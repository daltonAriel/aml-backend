package com.aml.app.modules.agencia;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.agencia.dto.AgenciaResponse;
import com.aml.app.modules.agencia.dto.BuscarAgenciaRequest;
import com.aml.app.modules.agencia.dto.CrearAgenciaRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/agencias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AgenciaController {

    private final AgenciaService agenciaService;

    @GetMapping("/{agenciaId}")
    public ResponseEntity<ApiResponse<AgenciaResponse>> buscarPorId(@PathVariable UUID agenciaId) {
        AgenciaResponse agencia = agenciaService.obtenerPorId(agenciaId);
        return ResponseEntity.ok(ApiResponse.success(agencia, "Agencia Obtenida"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AgenciaResponse>>> buscar(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "agenciaFechaCreacion") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir) {
        BuscarAgenciaRequest buscarAgenciaRequest = BuscarAgenciaRequest.builder()
                .filtro(filtro)
                .estado(estado)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();
        Page<AgenciaResponse> agencias = agenciaService.buscarAgencias(buscarAgenciaRequest);
        return ResponseEntity.ok(ApiResponse.success(agencias, "Agencias obtenidas exitosamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AgenciaResponse>> crear(@Valid @RequestBody CrearAgenciaRequest agencia) {
        AgenciaResponse nuevaAgencia = agenciaService.crearAgencia(agencia);
        return ResponseEntity.ok(ApiResponse.success(nuevaAgencia, "Agencia creada exitosamente"));
    }

    @PutMapping("/{agenciaId}")
    public ResponseEntity<ApiResponse<AgenciaResponse>> actualizar(@PathVariable UUID agenciaId,
            @Valid @RequestBody CrearAgenciaRequest agencia) {
        AgenciaResponse nuevaAgencia = agenciaService.actualizarAgencia(agenciaId, agencia);
        return ResponseEntity.ok(ApiResponse.success(nuevaAgencia, "Agencia actualizada exitosamente"));
    }

}
