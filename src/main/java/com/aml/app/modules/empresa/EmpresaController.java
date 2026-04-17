package com.aml.app.modules.empresa;

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
import com.aml.app.modules.empresa.dto.BuscarEmpresaRequest;
import com.aml.app.modules.empresa.dto.CrearEmpresaRequest;
import com.aml.app.modules.empresa.dto.EmpresaResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/empresas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpresaController {
    
    private final EmpresaService empresaService;

    @GetMapping("/{empresaId}")
    public ResponseEntity<ApiResponse<EmpresaResponse>> buscarPorId(@PathVariable UUID empresaId) {
        EmpresaResponse empresa = empresaService.buscarPorId(empresaId);
        return ResponseEntity.ok(ApiResponse.success(empresa, "Empresa Obtenida"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmpresaResponse>>> buscar(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "empresaFechaCreacion") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir) {
        BuscarEmpresaRequest buscarEmpresaRequest = BuscarEmpresaRequest.builder()
                .filtro(filtro)
                .estado(estado)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();
        Page<EmpresaResponse> empresas = empresaService.buscar(buscarEmpresaRequest);
        return ResponseEntity.ok(ApiResponse.success(empresas, "Empresas obtenidas exitosamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmpresaResponse>> crear(@Valid @RequestBody CrearEmpresaRequest empresa) {
        EmpresaResponse nuevaEmpresa = empresaService.crearEmpresa(empresa);
        return ResponseEntity.ok(ApiResponse.success(nuevaEmpresa, "Empresa creada exitosamente"));
    }

    @PutMapping("/{empresaId}")
    public ResponseEntity<ApiResponse<EmpresaResponse>> actualizar(@PathVariable UUID empresaId,
            @Valid @RequestBody CrearEmpresaRequest empresa) {
        EmpresaResponse empresaNueva = empresaService.actualizar(empresaId, empresa);
        return ResponseEntity.ok(ApiResponse.success(empresaNueva, "Empresa actualizada exitosamente"));
    }

    @DeleteMapping("/{empresaId}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable UUID empresaId) {
        empresaService.eliminar(empresaId);
        return ResponseEntity.ok(ApiResponse.success(null, "Empresa eliminada exitosamente"));
    }
}
