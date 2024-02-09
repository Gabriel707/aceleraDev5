package br.com.aceleradev5.dtos;

public class ErrorDTO {

    private String error;
    private Integer statusCode;
    
    private String httpStatus;

    public ErrorDTO() {
    }

    public ErrorDTO(String error, Integer statusCode, String httpStatus) {
        this.error = error;
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
}
