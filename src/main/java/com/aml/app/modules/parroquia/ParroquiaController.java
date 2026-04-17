package com.aml.app.modules.parroquia;

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
import com.aml.app.modules.parroquia.dto.BuscarParroquiaRequest;
import com.aml.app.modules.parroquia.dto.ParroquiaResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/parroquias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ParroquiaController {

    private final ParroquiaService parroquiaService;

    @GetMapping("/{cantonId}")
    public ResponseEntity<ApiResponse<ParroquiaResponse>> buscarPorId(@PathVariable UUID cantonId) {
        ParroquiaResponse parroquiaResponse = parroquiaService.buscarPorId(cantonId);
        return ResponseEntity.ok(ApiResponse.success(parroquiaResponse, "Parroquia obtenida exitosamente"));
    }

    @GetMapping("/canton/{cantonId}")
    public ResponseEntity<ApiResponse<List<ParroquiaResponse>>> buscarTodo(@PathVariable UUID cantonId) {
        List<ParroquiaResponse> listaParroquias = parroquiaService.buscarTodosPorCantonId(cantonId);
        return ResponseEntity.ok(ApiResponse.success(listaParroquias, "Parroquias obtenidas exitosamente"));
    }

    @GetMapping("/canton/{cantonId}/filtro")
    public ResponseEntity<ApiResponse<Page<ParroquiaResponse>>> buscarFiltros(
            @PathVariable UUID cantonId,
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "parroquiaCodigo") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir) {
        BuscarParroquiaRequest buscarParroquiaRequest = BuscarParroquiaRequest.builder()
                .filtro(filtro)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        Page<ParroquiaResponse> listaParroquias = parroquiaService.buscar(cantonId, buscarParroquiaRequest);
        return ResponseEntity.ok(ApiResponse.success(listaParroquias, "Parroquias obtenidas exitosamente"));
    }

}
