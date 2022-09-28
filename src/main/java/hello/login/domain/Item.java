package hello.login.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String dateName;
    private String locdate;
    private String isHoliday;

}
