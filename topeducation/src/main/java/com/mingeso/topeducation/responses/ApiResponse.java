package com.mingeso.topeducation.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    Integer status;
    String message;
    String pathRedirect;
    Object data;

    public ApiResponse(Integer status, String message, String urlRedirect){
        this.status = status;
        this.message = message;
        this.pathRedirect = urlRedirect;
    }
}
