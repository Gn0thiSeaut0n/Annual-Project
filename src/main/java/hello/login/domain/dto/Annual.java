package hello.login.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Annual {

    @NotEmpty
    private String user_id; // 유저 ID
    @NotEmpty
    private String total_annual; // 전체 연차
    @NotEmpty
    private String use_annual; // 사용 연차
    
    private String adjust; // 조정 연차

}
