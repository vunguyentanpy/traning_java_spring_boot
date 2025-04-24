package com.example.demo_spring_boot_mysql.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<JsonResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        JsonResponse errorResponse = new JsonResponse(false, e.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponse> handleAllExceptions(Exception e) {
        JsonResponse errorResponse = new JsonResponse(false, "Operation failed: " + e.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class JsonResponse {
        private boolean success;
        private String message;
        private Object data;


        public JsonResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;

        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class ResponseData {
        private Long id;
        private String accessToken;
        private String refreshToken;

        public ResponseData(Long id, String accessToken, String refreshToken) {
            this.id = id;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        // Getters và setters...
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }


}
