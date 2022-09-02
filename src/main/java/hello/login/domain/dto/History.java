package hello.login.domain.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class History {

    private String history_id;
    private String user_id;
    private String user_name;
    private String responsibilities_of_office;
    private String department;
    private String start_year;
    private String birthday;
    private String sex;
    private String user_number;
    private String total_annual;
    private String use_annual;
    private String appr_yn;
    private String appr_yn2;
    private String request_date;
    
    @NotNull
    private String application_year;
    @NotEmpty
    private String start_date;
    @NotEmpty
    private String end_date;
    @Mapper
    private String time;
    @NotEmpty
    private String reason;

}
