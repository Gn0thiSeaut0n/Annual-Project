package hello.login.web.etc;

import hello.login.domain.dto.History;
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

import java.util.List;

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
    public String mypage(@Login User loginMember, Model model) {
        List<History> history = etcService.findByHistory(loginMember.getUser_id());
        model.addAttribute("user", loginMember);
        model.addAttribute("history", history);
        return "info/mypage";
    }

    @GetMapping("/selectAll")
    public String selectAll(@Login User loginMember, Model model) {
        List<History> history = etcService.findByAllHistory();
        model.addAttribute("user", loginMember);
        model.addAttribute("history", history);
        return "info/selectAll";
    }

    @GetMapping("/memberManagement/{year}")
    public String memberManagement(@Login User loginMember, @PathVariable String year, Model model) {
        log.info("year - test = {}",year);
        List<UserAnnual> allUserAnnual = etcService.findByAllUserAnnual(year);
        log.info("allUser = {}", allUserAnnual.toString());

        model.addAttribute("user", loginMember);
        model.addAttribute("allUser", allUserAnnual);

        return "info/memberManagement";
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
