package hello.login.web.etc;

import hello.login.domain.dto.*;
import hello.login.domain.service.EtcService;
import hello.login.web.argumentresolver.Login;
import hello.login.web.util.JasyptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
        log.info(history.toString());

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public String mypage(@Login User loginMember, @RequestParam(defaultValue = "1") int page, Model model) {

        Pagination pagination = new Pagination(etcService.findByHistoryAllCnt(loginMember.getUser_id()), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("history", etcService.findByHistoryPaging(pagination.getStartIndex(), pagination.getPageSize(), loginMember.getUser_id()));
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

    @GetMapping("/memberManagement")
    public String memberManagement(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "") String year,
                                   @RequestParam(defaultValue = "") String user_name,
                                   Model model) {

        Pagination pagination = new Pagination(etcService.findByAllUserAnnualCnt(year, user_name), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("allUser", etcService.findByAllUserAnnualPaging(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(),
                "year", year, "user_name", user_name)));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchParam", Map.of("year", year, "user_name", user_name));

        return "info/memberManagement";
    }

    @GetMapping("/memberManagement/{year}/{user}")
    public String memberManagementDetail(@Login User loginMember, @PathVariable String year, @PathVariable String user, Model model) {

        Map<Integer, Map> bigBox = new HashMap<>();
        Map<Integer, String> smallBox = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 31; j++) {
                smallBox.put(j, "");
            }
            bigBox.put(i, new HashMap<>(smallBox));
        }

        etcService.selectAnnualMonth(year, user).stream().forEach( // 박스 12개 만들 것
                (data) -> {
                    // 한 유저의 휴가가 하루에 여러 개를 쓴다면 해당 부분 수정
                    bigBox.get(Integer.valueOf(data.getMonth())).put(Integer.valueOf(data.getDay()), data.getApplication_year());
                }
        );

        model.addAttribute("bigBox", bigBox);
        model.addAttribute("user", loginMember);
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

    @GetMapping("/selectCurrentPwd")
    public String selectCurrentPwdPage() {
        return "info/selectPwd";
    }

    @PostMapping("/selectCurrentPwd")
    public ResponseEntity selectCurrentPwd(@RequestBody User user, @Login User loginMember) {
        // DB 비밀번호 복호화
        String decrypt_user_pw = JasyptUtil.decrypt(etcService.selectCurrentPwd(loginMember.getUser_id()));

        // 복호화한 비밀번호와 같은지 비교
        if (!user.getUser_pw().equals(decrypt_user_pw)) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/updatePwd")
    public String updatePwdPage(HttpServletRequest request, Model model) {
        return "info/updatePwd";
    }

    @PutMapping("/updatePwd")
    public ResponseEntity updatePwd(@RequestBody User user, @Login User loginMember) {
        // 암호화
        String encrypt_user_pw = JasyptUtil.encrypt(user.getUser_pw());
        etcService.updatePwd(encrypt_user_pw, loginMember.getUser_id());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/memberRegister")
    public String memberRegisterPage(@Login User loginMember, Model model) {
        model.addAttribute("user", loginMember);
        return "info/memberRegister";
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
