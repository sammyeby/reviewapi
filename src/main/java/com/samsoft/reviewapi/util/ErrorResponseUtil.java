package com.samsoft.reviewapi.util;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseUtil {

    public static ResponseEntity<Map<String, String>> createErrorResponse(String errorMessage) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}