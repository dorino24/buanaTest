package ridhopriambodo.buana.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ridhopriambodo.buana.model.Response;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.<String>builder().errors(exception.getMessage()).build());
    };

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<String>> apiExceptioni(ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatusCode())
                .body(Response.<String>builder().errors(exception.getReason()).build());
    }


}
