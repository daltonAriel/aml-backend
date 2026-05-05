package com.aml.app.modules.clientes;

import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.Validator;

import org.springframework.stereotype.Service;

import com.aml.app.modules.clientes.dto.ClienteBulkRequest;
import com.aml.app.modules.clientes.dto.ClienteBulkResponse;
import com.aml.app.modules.empresa.EmpresaService;
import com.aml.app.shared.BulkErrorDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final Validator validator;
    private final EmpresaService empresaService;

    public ClienteBulkResponse cargaMasiva(ClienteBulkRequest request, String empresaCodigo) {
        List<BulkErrorDetail> errores = new ArrayList<>();
        int exitosos = 0;
        int fallidos = 0;

        // Optional<EmpresaEntity> empresaOpt =
        // empresaService.buscarPorCodigo(request.getEmpresaCodigo());

        return null;
    }

}
