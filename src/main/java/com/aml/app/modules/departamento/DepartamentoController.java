package com.aml.app.modules.departamento;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.departamento.dto.BuscarDepartamentoRequest;
import com.aml.app.modules.departamento.dto.CrearDepartamentoRequest;
import com.aml.app.modules.departamento.dto.DepartamentoResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/departamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping("/{departamentoId}")
    public ResponseEntity<ApiResponse<DepartamentoResponse>> buscarPorId(@PathVariable UUID departamentoId) {
        DepartamentoResponse departamento = departamentoService.obtenerPorId(departamentoId);
        return ResponseEntity.ok(ApiResponse.success(departamento, "Departamento obtenido exitosamente"));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<ApiResponse<Page<DepartamentoResponse>>> buscar(
            @PathVariable UUID empresaId,
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "departamentoFechaCreacion") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir) {
        BuscarDepartamentoRequest buscarDepartamentoRequest = BuscarDepartamentoRequest.builder()
                .filtro(filtro)
                .estado(estado)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();
        Page<DepartamentoResponse> departamentos = departamentoService
                .buscarDepartamentosPorEmpresaId(buscarDepartamentoRequest, empresaId);
        return ResponseEntity.ok(ApiResponse.success(departamentos, "Departamentos obtenidos exitosamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartamentoResponse>> crearDepartamento(@Valid @RequestBody CrearDepartamentoRequest request) {
        DepartamentoResponse departamento = departamentoService.crearDepartamento(request);
        return ResponseEntity.ok(ApiResponse.success(departamento, "Departamento creado exitosamente"));
    }

    @PutMapping("/{departamentoId}")
    public ResponseEntity<ApiResponse<DepartamentoResponse>> actualizarDepartamento(@PathVariable UUID departamentoId, @Valid @RequestBody CrearDepartamentoRequest request) {
        DepartamentoResponse departamento = departamentoService.actualizarDepartamento(departamentoId, request);
        return ResponseEntity.ok(ApiResponse.success(departamento, "Departamento actualizado exitosamente"));
    }

    @DeleteMapping("/{departamentoId}")
    public ResponseEntity<ApiResponse<DepartamentoResponse>> eliminarDepartamento(@PathVariable UUID departamentoId) {
        departamentoService.eliminarDepartamento(departamentoId);
        return ResponseEntity.ok(ApiResponse.success(null, "Departamento eliminado exitosamente"));
    }

}
