package com.aml.app.modules.canton;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.canton.dto.BuscarCantonRequest;
import com.aml.app.modules.canton.dto.CantonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cantones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CantonController {

    private final CantonService cantonService;

    @GetMapping("/{cantonId}")
    public ResponseEntity<ApiResponse<CantonResponse>> buscarPorId(@PathVariable UUID cantonId) {
        CantonResponse cantonResponse = cantonService.buscarPorId(cantonId);
        return ResponseEntity.ok(ApiResponse.success(cantonResponse, "Canton obtenido exitosamente"));
    }

    @GetMapping("/provincia/{provinciaId}")
    public ResponseEntity<ApiResponse<List<CantonResponse>>> buscarTodo(@PathVariable UUID provinciaId) {
        List<CantonResponse> listaCantones = cantonService.buscarTodosPorProvinciaId(provinciaId);
        return ResponseEntity.ok(ApiResponse.success(listaCantones, "Cantones obtenidos exitosamente"));
    }

    @GetMapping("/provincia/{provinciaId}/filtro")
    public ResponseEntity<ApiResponse<Page<CantonResponse>>> buscarFiltros(
            @PathVariable UUID provinciaId,
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "cantonCodigo") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir) {
        BuscarCantonRequest buscaCantonRequest = BuscarCantonRequest.builder()
                .filtro(filtro)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        Page<CantonResponse> listaCantones = cantonService.buscar(provinciaId, buscaCantonRequest);
        return ResponseEntity.ok(ApiResponse.success(listaCantones, "Provincia obtenida exitosamente"));
    }
}
