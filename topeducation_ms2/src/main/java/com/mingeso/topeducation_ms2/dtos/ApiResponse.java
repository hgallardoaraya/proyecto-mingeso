package com.mingeso.topeducation_ms2.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse extends Response {
    Object data;

    public ApiResponse(Integer status, String message, Object data){
        super(status, message);
        this.data = data;
    }
}
