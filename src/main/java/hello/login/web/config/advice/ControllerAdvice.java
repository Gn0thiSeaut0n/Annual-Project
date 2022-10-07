package hello.login.web.config.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    // @RequestBody @Validated 사용 시 해당 객체에 설정되어 있는 검증과 다를 시 예외가터짐.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> messageConverterValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse("ERROR", "Exception", 400, e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // @Validated만 사용 시 해당 객체에 설정되어 있는 검증과 다를 시 예외가터짐.
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindExceptionError(BindException e) {
        ErrorResponse errorResponse = new ErrorResponse("ERROR", "Exception", 400, e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
