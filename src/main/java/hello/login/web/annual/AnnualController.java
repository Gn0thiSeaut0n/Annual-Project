package hello.login.web.annual;

import hello.login.domain.dto.AnnualList;
import hello.login.domain.dto.Pagination;
import hello.login.domain.dto.User;
import hello.login.domain.service.AnnualService;
import hello.login.web.argumentresolver.Login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AnnualController {

    private final AnnualService annualService;

    @GetMapping("/selectAll")
    public String selectAll(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "") String year,
                            @RequestParam(defaultValue = "") String month,
                            @RequestParam(defaultValue = "") String user_name,
                            Model model) {

        Pagination pagination = new Pagination(annualService.findByAllHistoryCnt(Map.of("year", year, "month", month, "user_name", user_name)), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("history", annualService.findByAllHistoryPaging(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(),
                "year", year, "month", month, "user_name", user_name)));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchParam", Map.of("year", year, "month", month, "user_name", user_name));
        return "annual/selectAll";
    }

    @GetMapping("/annualStatus")
    public String annualStatus(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "") String year,
                                   @RequestParam(defaultValue = "") String user_name,
                                   Model model) {

        Pagination pagination = new Pagination(annualService.findByAllUserAnnualCnt(Map.of("year", year, "user_name", user_name)), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("allUser", annualService.findByAllUserAnnualPaging(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(),
                "year", year, "user_name", user_name)));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchParam", Map.of("year", year, "user_name", user_name));

        return "annual/annualStatus";
    }

    @GetMapping("/annualStatus/{year}/{user}")
    public String annualStatusDetail(@Login User loginMember, @PathVariable String year, @PathVariable String user, Model model) {

        Map<Integer, Map> bigBox = new HashMap<>();
        Map<Integer, String> smallBox = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 31; j++) {
                smallBox.put(j, "");
            }
            bigBox.put(i, new HashMap<>(smallBox));
        }

        annualService.selectAnnualMonth(Map.of("year", year, "user_id", user)).stream().forEach(
                (data) -> {
                    // 한 유저의 휴가가 하루에 여러 개를 쓴다면 해당 부분 수정
                    bigBox.get(Integer.valueOf(data.getMonth())).put(Integer.valueOf(data.getDay()), data.getApplication_year());
                }
        );

        model.addAttribute("monthTot", annualService.selectTotalAnnualMonth(Map.of("year", year, "user_id", user)));
        model.addAttribute("bigBox", bigBox);
        model.addAttribute("user", loginMember);
        return "annual/annualStatusDetail";
    }

    @GetMapping("/annualManagement")
    public String annualManagement(@Login User loginMember, Model model) {
        AnnualList annualList = annualService.findByAllAnnual();

        model.addAttribute("annual", annualList);
        model.addAttribute("user", loginMember);
        return "annual/annualManagement";
    }

    @PutMapping("/annualUpdate")
    public ResponseEntity annualUpdate(@Login User loginMember, @RequestBody AnnualList annualList) {
        annualService.annualUpdate(annualList);
        return new ResponseEntity(HttpStatus.OK);
    }
}
