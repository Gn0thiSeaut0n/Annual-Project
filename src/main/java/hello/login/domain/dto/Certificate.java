package hello.login.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Certificate {

    private String doc_no;
    private String type;
    private String status_code;
    private String user_id;
    private String user_address;
    private String department;
    private String responsibilities_of_office;
    private String user_name;
    private String identification_no_front;
    //@NotBlank
    private String identification_no_back;
    private String start_year;
    //@NotBlank
    private String purpose;
    private String note;

    private String apply_date;

    private String email;
}
