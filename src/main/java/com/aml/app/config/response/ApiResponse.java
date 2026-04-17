package com.aml.app.config.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String code;
    private int status;
    private T data;
    private String technicalDetail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Devuelve una respuesta de exito con el estado true y el mensaje especificado.
     * El codigo es "SUCCESS" y el status es 200 (OK).
     * La informacion adicional se almacena en el campo data.
     * La fecha y hora actuales se almacenan en el campo timestamp.
     * 
     * @param data    La informaci n adicional a incluir en la respuesta.
     * @param message El mensaje a incluir en la respuesta.
     * @return Una respuesta de exito con el estado true y el mensaje especificado.
     */

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .code("SUCCESS")
                .status(HttpStatus.OK.value())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Devuelve una respuesta de error con el estado false y el mensaje
     * especificado.
     * El c digo es el especificado en el par metro code y el status es el
     * especificado en el par metro status.
     * La informaci n adicional se almacena en el campo technicalDetail.
     * La fecha y hora actuales se almacenan en el campo timestamp.
     * 
     * @param message         El mensaje a incluir en la respuesta.
     * @param code            El c digo de la respuesta.
     * @param status          El status de la respuesta.
     * @param technicalDetail La informaci n adicional a incluir en la respuesta.
     * @return Una respuesta de error con el estado false y el mensaje especificado.
     */

    public static <T> ApiResponse<T> error(String message, String code, int status, String technicalDetail) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .code(code)
                .status(status)
                .technicalDetail(technicalDetail)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
