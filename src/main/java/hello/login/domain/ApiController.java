package hello.login.domain;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ApiController {

        @GetMapping("/holidayInfoAPI")
        public ResponseEntity holidayInfoAPI() {

            List<Item> responseHolidayArr = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                String year = String.valueOf(LocalDate.now().plusYears(i).getYear());
                try {
                    Map<String, Object> body = RequestUtils.holidayInfoAPI(year);
                    if (body.containsKey("item")) {
                        List<HashMap<String, Object>> item = (ArrayList<HashMap<String, Object>>) body.get("item");
                        for (HashMap<String, Object> itemMap : item) {
                            responseHolidayArr.add(new Item(getStr(itemMap, "dateName"), getStr(itemMap, "locdate"), getStr(itemMap, "isHoliday")));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            return ResponseEntity.ok().body(responseHolidayArr);
        }

    private static String getStr(HashMap<String, Object> itemMap, String key) {
        return itemMap.get(key).toString();
    }
}
