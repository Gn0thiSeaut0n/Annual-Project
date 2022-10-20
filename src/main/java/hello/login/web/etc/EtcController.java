package hello.login.web.etc;

import hello.login.domain.dao.EmailDAO;
import hello.login.domain.dto.History;
import hello.login.domain.dto.Pagination;
import hello.login.domain.dto.User;
import hello.login.domain.service.AnnualService;
import hello.login.domain.service.EmailService;
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

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EtcController {

    private final EtcService etcService;
    private final AnnualService annualService;
    private final EmailService emailService;

    @PostMapping("/application")
    public ResponseEntity application(@Validated History history, @Login User loginMember) throws Exception {
        etcService.insertApplicationHistory(history);
        if (history.getHoliday().equals("")) {
            etcService.updateAnnual(Map.of("user_id", history.getUser_id(), "use_annual", "" + getUseAnnual(history)));
        }
        emailService.sendMail("상신", history, loginMember);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteHistory/{history_id}")
    public ResponseEntity deleteHistory(@PathVariable String history_id, @Login User loginMember) {
        if (getHistory(loginMember.getUser_id(), history_id).getHoliday() == null) {
            etcService.updateHistory(Map.of(
                    "user_id", loginMember.getUser_id(), "application_year", Float.valueOf(getHistory(loginMember.getUser_id(), history_id).getApplication_year())));
        }
        etcService.deleteHistory(history_id);
        annualService.deleteFileInfo(history_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/apprHistory/{history_id}")
    public ResponseEntity apprHistory(@PathVariable String history_id, @Login User loginMember) throws Exception {
        etcService.updateAppr(Map.of("history_id", history_id, "type", loginMember.getAuth()));
        History history = emailService.selectToUser(history_id);
        emailService.sendMail("승인", history, loginMember);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/myPage")
    public String myPage(@Login User loginMember, @RequestParam(defaultValue = "1") int page, Model model) {

        Pagination pagination = new Pagination(etcService.findByHistoryAllCnt(loginMember.getUser_id()), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("history", etcService.findByHistoryPaging(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(), "user_id", loginMember.getUser_id()
        )));
        model.addAttribute("pagination", pagination);
        model.addAttribute("fileList", annualService.findByAllFileList());
        return "info/myPage";
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
    public String updatePwdPage() {
        return "info/updatePwd";
    }

    @PutMapping("/updatePwd")
    public ResponseEntity updatePwd(@RequestBody User user, @Login User loginMember) {
        // 암호화
        etcService.updatePwd(Map.of("user_pw", JasyptUtil.encrypt(user.getUser_pw()), "user_id", loginMember.getUser_id()));
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
