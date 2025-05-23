package com.rsm.util;

import com.rsm.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Opération réussie", data);
    }


    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
