package com.samsoft.reviewapi.util;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseUtil {

    public static ResponseEntity<Map<String, String>> createErrorResponse(String errorMessage, HttpStatusCode status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        return ResponseEntity.status(status).body(errorResponse);
    }
}