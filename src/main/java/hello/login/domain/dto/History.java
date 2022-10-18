package hello.login.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private String holiday;

    //파일 필드
    private String file_id;
    private String file_uuid;
    private String origin_file_name;
    private List<MultipartFile> uploadFiles;

    @NotNull
    private String application_year;
    @NotEmpty
    private String start_date;
    @NotEmpty
    private String end_date;
    @NotEmpty
    private String time;
    @NotEmpty
    private String reason;

    private String email;
}
