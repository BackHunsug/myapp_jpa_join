package com.woori.myapp.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@ToString
public class ErrorResponseDTO {
    private String result="fail";
    private String message;  // 에러 메시지
    private String details;  // 에러에 대한 추가 정보
    private String timestamp; // 에러 발생 시각
    private String classname;
    private int code;

    public ErrorResponseDTO(String message, String details, int code) {
        this.message = message;
        this.details = details;
        this.timestamp = java.time.LocalDateTime.now().toString(); // 현재 시간
        this.code = code;
        this.result="Fail";
        this.classname="Test";
        //this.classname=classname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Optional: Setters (if needed)
    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
