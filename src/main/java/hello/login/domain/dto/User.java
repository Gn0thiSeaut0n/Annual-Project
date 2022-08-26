package hello.login.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class User {
    @NotEmpty
    private String user_id; // 로그인 ID
    @NotEmpty
    private String user_pw; // 로그인 PASSWORD
    @NotEmpty
    private String user_name; // 사용자 이름
    @NotEmpty
    private String responsibilities_of_office;
    @NotEmpty
    private String department;
    @NotEmpty
    private String start_year;
    @NotEmpty
    private String user_number;
    @NotEmpty
    private String birthday;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String auth;
}