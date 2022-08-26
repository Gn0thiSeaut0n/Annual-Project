package hello.login.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    private String user_id;

    @NotEmpty
    private String user_pw;
}
