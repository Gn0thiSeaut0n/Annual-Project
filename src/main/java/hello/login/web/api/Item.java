package hello.login.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String dateName;
    private String locdate;
    private String isHoliday;

}
