package git.niklogik.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author niklogik
 */
@RestControllerAdvice
public class ApiExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ApiError notFoundException(NotFoundException e) {
        return new ApiError(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    public record ApiError(String message, Integer code) {}
}
