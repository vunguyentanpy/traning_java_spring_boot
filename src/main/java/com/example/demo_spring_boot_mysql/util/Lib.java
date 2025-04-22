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
            this.token = null;
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
        //      modbus
        private int byteSwap(int register) {
            return ((register & 0xFF) << 8) | ((register >> 8) & 0xFF);
        }
        //      Float32
        public  float toFloat32BigEndian(int[] values) {
            int combined = (values[0] << 16) | (values[1] & 0xFFFF);
            return Float.intBitsToFloat(combined);
        }
        public  float toFloat32LittleEndian(int[] values) {
            int combined = (values[1] << 16) | (values[0] & 0xFFFF);
            return Float.intBitsToFloat(combined);
        }
        public  float toFloat32BigEndianByteSwap(int[] values) {
            int swappedHigh = byteSwap(values[0]);
            int swappedLow = byteSwap(values[1]);
            int combined = (swappedHigh << 16) | (swappedLow & 0xFFFF);
            return Float.intBitsToFloat(combined);
        }
        public float toFloat32LittleEndianByteSwap(int[] values) {
            int swappedHigh = byteSwap(values[1]);
            int swappedLow = byteSwap(values[0]);
            int combined = (swappedHigh << 16) | (swappedLow & 0xFFFF);
            return Float.intBitsToFloat(combined);
        }
        //      Signed32
        public int toSigned32BigEndian(int[] values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("Array must have exactly 2 elements for signed 32-bit conversion.");
            }
            return (values[0] << 16) | (values[1] & 0xFFFF);
        }

        public int toSigned32LittleEndian(int[] values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("Array must have exactly 2 elements for signed 32-bit conversion.");
            }
            return (values[1] << 16) | (values[0] & 0xFFFF);
        }
        public int toSigned32BigEndianByteSwap(int[] values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("Array must have exactly 2 elements for signed 32-bit conversion.");
            }

            int swappedHigh = byteSwap(values[0]);
            int swappedLow = byteSwap(values[1]);
            return (swappedHigh << 16) | (swappedLow & 0xFFFF);
        }

        public int toSigned32LittleEndianByteSwap(int[] values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("Array must have exactly 2 elements for signed 32-bit conversion.");
            }

            int swappedHigh = byteSwap(values[1]);
            int swappedLow = byteSwap(values[0]);
            return (swappedHigh << 16) | (swappedLow & 0xFFFF);
        }
        //      Float64
        public double toFloat64BigEndianByteSwap(int[] values) {
            if (values.length != 4) {
                throw new IllegalArgumentException("Array must have exactly 4 elements for double conversion.");
            }

            int swappedHigh1 = byteSwap(values[0]);
            int swappedLow1 = byteSwap(values[1]);
            int swappedHigh2 = byteSwap(values[2]);
            int swappedLow2 = byteSwap(values[3]);

            long combined = ((long) swappedHigh1 << 48) |
                    ((long) swappedLow1 << 32) |
                    ((long) swappedHigh2 << 16) |
                    (swappedLow2 & 0xFFFF);

            return Double.longBitsToDouble(combined);
        }
        //      Signed16
        public  int toSigned16Bit(int value) {
            if (value > 32767) {
                return value - 65536;
            }
            return value;
        }
        //      UnSigned16
        public  int toUnsigned16Bit(int value) {
            if (value < 0 || value > 65535) {
                throw new IllegalArgumentException("Value out of range for unsigned 16-bit integer.");
            }
            return value + 1000;
        }


}
