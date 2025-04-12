package gmbh.conteco.seminarverwaltung.service.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}