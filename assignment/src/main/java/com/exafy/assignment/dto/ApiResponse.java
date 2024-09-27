package com.exafy.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiResponse {

    private Object data;
    private String error;

    public ApiResponse(final Object data) {
        this.data = data;
    }

    public ApiResponse(final String error) {
        this.error = error;
    }
}
