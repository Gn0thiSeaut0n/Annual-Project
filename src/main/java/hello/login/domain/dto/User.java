package hello.login.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class User {
    @NotEmpty
    private String user_id; // 로그인 ID
    private String user_pw; // 로그인 PASSWORD
    @NotEmpty
    private String user_name; // 사용자 이름
    @NotEmpty
    private String responsibilities_of_office;
    @NotEmpty
    private String department;
    @NotEmpty
    private String start_year;
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String user_number;
    @NotEmpty
    private String birthday;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String auth;
    @Email
    private String email;
    @NotEmpty
    private String user_address;
}