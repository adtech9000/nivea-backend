package com.nivea_be.nivea_ad.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, true, message);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(null, false, message);
    }
}

