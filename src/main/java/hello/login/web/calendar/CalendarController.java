package hello.login.web.calendar;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.login.domain.dto.User;
import hello.login.domain.service.CalendarService;
import hello.login.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CalendarController {
	@Autowired
	private final CalendarService calendarService;
	
	@GetMapping("/viewCalendar")
    public String viewCalendar(@Login User loginMember,
				    		@RequestParam(defaultValue = "") String year,
				            @RequestParam(defaultValue = "") String month,
              				Model model) {

        model.addAttribute("user", loginMember);
        model.addAttribute("searchParam", Map.of("year", year, "month", month));
        return "info/viewCalendar";
    }
    
    @GetMapping("/viewAnnual")
    @ResponseBody
    public List<Map<String, Object>> viewAnnual(
    		@RequestParam(defaultValue = "") String year,
            @RequestParam(defaultValue = "") String month) {
    	
    	JSONArray jsonArr = (JSONArray) calendarService.calendarHistory(year, month);
    	return jsonArr;
    }
}
