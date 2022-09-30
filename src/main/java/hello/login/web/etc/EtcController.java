package hello.login.web.etc;

import hello.login.domain.dto.*;
import hello.login.domain.service.AnnualService;
import hello.login.domain.service.EtcService;

import hello.login.web.argumentresolver.Login;
import hello.login.web.util.JasyptUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EtcController {

    private final EtcService etcService;
    private final AnnualService annualService;

    @PostMapping("/application")
    public ResponseEntity application(@Validated History history) throws IOException {
        etcService.insertApplicationHistory(history);
        etcService.updateAnnual(Map.of("user_id", history.getUser_id(), "use_annual", "" + getUseAnnual(history)));
        log.info(history.toString());

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteHistory/{history_id}")
    public ResponseEntity deleteHistory(@PathVariable String history_id, @Login User loginMember) {
        etcService.updateAnnual(Map.of(
                "user_id", loginMember.getUser_id(), "use_annual", Float.valueOf(getHistory(loginMember.getUser_id(), history_id).getUse_annual())));
        etcService.deleteHistory(history_id);
        annualService.deleteFileInfo(history_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/apprHistory/{history_id}")
    public ResponseEntity apprHistory(@PathVariable String history_id, @Login User loginMember) {
    	
        etcService.updateAppr(Map.of("history_id", history_id, "type", loginMember.getAuth()));
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
