package git.niklogik.error;

import git.niklogik.error.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException error) {
        return errorResponse(NOT_FOUND, error.getMessage());
    }

    private ErrorResponse errorResponse(HttpStatus status, String message) {
        return new ErrorResponse(status.value(),
                                 status.getReasonPhrase(),
                                 message);
    }

    public record ErrorResponse(int code, String status, String message) {}
}
