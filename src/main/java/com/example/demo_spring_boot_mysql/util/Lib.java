package com.example.demo_spring_boot_mysql.util;

public class Lib {
        public static class JsonResponse {
            private boolean success;
            private String message;
            private Object data;
            private String token;
        public JsonResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.token = null; // Hoặc bạn có thể để trống
        }

            public JsonResponse(boolean success, String message, Object data, String token) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.token = token;
        }

        // Getters and setters
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
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
