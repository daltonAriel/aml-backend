package com.aml.app.modules.provincia;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.provincia.dto.BuscarProvinciaRequest;
import com.aml.app.modules.provincia.dto.ProvinciaResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/provincias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProvinciaController {

    private final ProvinciaService provinciaService;

    @GetMapping("/{provinciaId}")
    public ResponseEntity<ApiResponse<ProvinciaResponse>> buscarPorId(@PathVariable UUID provinciaId) {
        ProvinciaResponse provinciaResponse = provinciaService.buscarPorId(provinciaId);
        return ResponseEntity.ok(ApiResponse.success(provinciaResponse, "Provincias obtenidas exitosamente"));
    }

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse<List<ProvinciaResponse>>> buscarTodo() {
        List<ProvinciaResponse> listaProvincias = provinciaService.buscarTodos();
        return ResponseEntity.ok(ApiResponse.success(listaProvincias, "Provincias obtenidas exitosamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProvinciaResponse>>> buscarFiltros(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "provinciaCodigo") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir) {
        BuscarProvinciaRequest buscarProvinciaRequest = BuscarProvinciaRequest.builder()
                .filtro(filtro)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        Page<ProvinciaResponse> listaProvincias = provinciaService.buscar(buscarProvinciaRequest);
        return ResponseEntity.ok(ApiResponse.success(listaProvincias, "Provincias obtenida exitosamente"));
    }

}
