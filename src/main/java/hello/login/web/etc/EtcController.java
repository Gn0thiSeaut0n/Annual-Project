package hello.login.web.etc;

import hello.login.domain.dto.History;
import hello.login.domain.dto.Pagination;
import hello.login.domain.dto.MonthAndDayList;
import hello.login.domain.dto.User;
import hello.login.domain.dto.UserAnnual;
import hello.login.domain.service.EtcService;
import hello.login.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EtcController {

    private final EtcService etcService;

    @PostMapping("/application")
    public ResponseEntity application(@RequestBody @Validated History history) {
        etcService.insertApplicationHistory(history);
        etcService.updateAnnual(history.getUser_id(), getUseAnnual(history));

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public String mypage(@Login User loginMember, @RequestParam(defaultValue = "1") int page, Model model) {

        int totalListCnt = etcService.findByHistoryAllCnt(loginMember.getUser_id());
        Pagination pagination = new Pagination(totalListCnt, page);

        List<History> history = etcService.findByHistoryPaging(pagination.getStartIndex(), pagination.getPageSize(), loginMember.getUser_id());

        model.addAttribute("user", loginMember);
        model.addAttribute("history", history);
        model.addAttribute("pagination", pagination);
        return "info/mypage";
    }

    @GetMapping("/selectAll")
    public String selectAll(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "") String year,
                            @RequestParam(defaultValue = "") String month,
                            @RequestParam(defaultValue = "") String user_name,
                            Model model) {

        Pagination pagination = new Pagination(etcService.findByAllHistoryCnt(year, month, user_name), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("history", etcService.findByAllHistoryPaging(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(),
                "year", year, "month", month, "user_name", user_name)));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchParam", Map.of("year", year, "month", month, "user_name", user_name));
        return "info/selectAll";
    }

    @GetMapping("/memberManagement/{year}")
    public String memberManagement(@Login User loginMember, @PathVariable String year, Model model) {
        List<UserAnnual> allUserAnnual = etcService.findByAllUserAnnual(year);
        log.info("allUser = {}", allUserAnnual.toString());

        model.addAttribute("user", loginMember);
        model.addAttribute("allUser", allUserAnnual);

        return "info/memberManagement";
    }

    @GetMapping("/memberManagement/{year}/{user}")
    public String memberManagementDetail(@Login User loginMember, @PathVariable String year, @PathVariable String user, Model model) {

        List<MonthAndDayList> monthAndDayLists = etcService.selectAnnualMonth(year, user);
        log.info("month test = {}", monthAndDayLists.toString());

        model.addAttribute("user", loginMember);
        model.addAttribute("days", monthAndDayLists);
        return "info/memberManagementDetail";
    }

    @DeleteMapping("/deleteHistory/{history_id}")
    public ResponseEntity deleteHistory(@PathVariable String history_id, @Login User loginMember) {
        etcService.updateAnnual(loginMember.getUser_id(), Float.valueOf(getHistory(loginMember.getUser_id(), history_id).getUse_annual()));
        etcService.deleteHistory(history_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/apprHistory/{history_id}")
    public ResponseEntity apprHistory(@PathVariable String history_id) {
        etcService.updateAppr(history_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private float getUseAnnual(History history) {
        return Float.valueOf(history.getUse_annual()) + Float.valueOf(history.getApplication_year());
    }

    private History getHistory(String user_id, String history_id) {
        return etcService.findByHistory(user_id).stream()
                .filter(history -> history.getHistory_id().equals(history_id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
