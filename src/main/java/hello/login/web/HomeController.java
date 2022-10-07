package hello.login.web;

import hello.login.web.api.Item;
import hello.login.web.api.RequestUtils;
import hello.login.domain.dto.Annual;
import hello.login.domain.dto.User;
import hello.login.domain.service.LoginService;
import hello.login.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;

    @GetMapping("/")
    public String homeLogin(@Login User loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "login/loginForm";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("user", loginMember);
        Annual annual = loginService.annual(loginMember.getUser_id());
        model.addAttribute("annual", annual);
        model.addAttribute("holidays", holiday());
        return "home";
    }

    List<Item> holiday(){

        List<Item> holiday = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            String year = String.valueOf(LocalDate.now().plusYears(i).getYear());
            try {
                Map<String, Object> body = RequestUtils.holidayInfoAPI(year);
                if (body.containsKey("item")) {
                    List<HashMap<String, Object>> item = (ArrayList<HashMap<String, Object>>) body.get("item");
                    for (HashMap<String, Object> itemMap : item) {
                        holiday.add(new Item(getStr(itemMap, "dateName"), getStr(itemMap, "locdate"), getStr(itemMap, "isHoliday")));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return holiday;

    }

    private String getStr(HashMap<String, Object> itemMap, String key) {
        return itemMap.get(key).toString();
    }

}