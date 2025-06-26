package sa.mhmdfayedh.CourseConnect.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sa.mhmdfayedh.CourseConnect.dto.v1.ErrorResponseDTO;

import java.time.ZonedDateTime;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e){

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException e){

        String validationMessage = Optional.ofNullable(e.getBindingResult().getFieldError())
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request data");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                validationMessage
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }


    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleRateLimitExceededException(RateLimitExceededException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();

        errorResponseDTO.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setError("Rate Limit Exceeded");

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCourseNotFoundException(CourseNotFoundException e){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());
        errorResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setError("Course Not Found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());
        errorResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setError("User Not Found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(errorResponseDTO);
    }


}


