package hello.login.web.config.advice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@NoArgsConstructor
public class ErrorResponse {

	private String code;
	private String message;
	private Integer status;
	private List<FieldError> errors;

	public ErrorResponse(String code, String message, Integer status, BindingResult bindingResult) {
		this.code = code;
		this.message = message;
		this.status = status;
		this.errors = bindingResult.getFieldErrors().stream()
				.map((innerObject) -> new FieldError(innerObject.getObjectName(), innerObject.getField(), "필수값을 입력해주세요."))
				.collect(Collectors.toList());
	}

}
