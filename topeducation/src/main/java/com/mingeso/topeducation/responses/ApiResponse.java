package com.mingeso.topeducation.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    int status;
    String message;
    Object data;
}
