package sa.mhmdfayedh.CourseConnect.common.exceptions;

import jakarta.persistence.PersistenceException;
import org.hibernate.JDBCException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sa.mhmdfayedh.CourseConnect.dto.v1.ErrorResponseDTO;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException e){

        String validationMessage = Optional.ofNullable(e.getBindingResult().getFieldError())
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request data");

        ErrorResponseDTO error = ErrorResponseDTO
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message(validationMessage)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleRateLimitExceededException(RateLimitExceededException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .message(e.getMessage())
                .error("Rate Limit Exceeded")
                .build();

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCourseNotFoundException(CourseNotFoundException e){
        ErrorResponseDTO errorResponseDTO =  ErrorResponseDTO
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .error("Course Not Found")
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .error("User Not Found")
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .error("Invalid Credentials Exception")
                .build();

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataAccessException(DataAccessException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Something went wrong")
                .error("Internal Server Error")
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorResponseDTO> handlePersistenceException(PersistenceException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Something went wrong")
                .error("Internal Server Error")
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(JDBCException.class)
    public ResponseEntity<ErrorResponseDTO> handleJDBCException(JDBCException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Something went wrong")
                .error("Internal Server Error")
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e){

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponseDTO);
    }
}


