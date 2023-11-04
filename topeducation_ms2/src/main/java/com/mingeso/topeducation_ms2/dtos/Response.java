package com.mingeso.topeducation_ms2.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    Integer status;
    String message;
    String pathRedirect;

    public Response(Integer status, String message){
        this.status = status;
        this.message = message;
    }

}
