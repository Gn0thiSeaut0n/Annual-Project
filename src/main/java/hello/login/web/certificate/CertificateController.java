package hello.login.web.certificate;

import hello.login.domain.dto.Certificate;
import hello.login.domain.dto.Pagination;
import hello.login.domain.dto.User;
import hello.login.domain.service.CertificateService;
import hello.login.domain.service.EmailService;
import hello.login.domain.service.UserService;
import hello.login.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/certificateList")
    public String certificateListPage(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "") String select_search,
                                      @RequestParam(defaultValue = "") String input_search,
                                      Model model) {
        Pagination pagination = new Pagination(certificateService.findByAllCertificateCnt(Map.of(
                "select_search", select_search, "input_search", input_search)), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("certificate", certificateService.selectCertificateAll(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(),
                "select_search", select_search, "input_search", input_search)));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchParam", Map.of("select_search", select_search, "input_search", input_search));

        List<String> typeOfSearch = new ArrayList<>();
        typeOfSearch.add("문서번호");
        typeOfSearch.add("사번");
        typeOfSearch.add("성명");
        typeOfSearch.add("종류");
        model.addAttribute("selection", typeOfSearch);

        return "certificate/certificateList";
    }

    @GetMapping("/applyCertificate/{user_id}")
    public String ApplyCertificatePage(@Login User loginMember, Model model, @PathVariable String user_id) {
        model.addAttribute("userInfo", userService.findByUserDetail(user_id));
        model.addAttribute("user", loginMember);
        return "certificate/applyCertificate";
    }

    @GetMapping("/viewCertificate/{doc_no}")
    public String UpdateCertificatePage(@Login User loginMember, Model model, @PathVariable String doc_no) {
        model.addAttribute("docInfo", certificateService.findByDocNoDetail(doc_no));
        model.addAttribute("user", loginMember);
        return "certificate/viewCertificate";
    }

    @PostMapping("/apply")
    public ResponseEntity PostApplyCertificatePage(@Validated Certificate certificate, @Login User loginMember) throws MessagingException {
        emailService.sendCertificateMail("상신", certificate, loginMember);
        certificateService.insertCertificate(certificate);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/approve/{doc_no}")
    public ResponseEntity ApproveCertificatePage(@PathVariable String doc_no, @Login User loginMember) throws MessagingException {
        Certificate certificate = certificateService.findByDocNoDetail(doc_no);
        if(!certificate.getUser_id().substring(0, 3).equals("tmp") && (loginMember.getAuth().equals("manager") || loginMember.getAuth().equals("admin")) && certificate.getStatus_code().equals("0")) {
            emailService.sendCertificateMail("승인", certificate, loginMember);
        }
        certificateService.approveCertificate(doc_no);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{doc_no}")
    public ResponseEntity deleteCertificate(@PathVariable String doc_no, @Login User loginMember) throws MessagingException {
        Certificate certificate = certificateService.findByDocNoDetail(doc_no);
        if(!certificate.getUser_id().substring(0, 3).equals("tmp") && (loginMember.getAuth().equals("manager") || loginMember.getAuth().equals("admin")) && certificate.getStatus_code().equals("0")) {
            emailService.sendCertificateMail("반려", certificate, loginMember);
        }
        certificateService.deleteCertificate(doc_no);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/viewCertificate/{doc_no}/save")
    public ResponseEntity savePDF(@PathVariable String doc_no) {
        String num = certificateService.savePDF(doc_no);
        return new ResponseEntity(Map.of("decryptNum", num), HttpStatus.OK);
    }

    @PutMapping("/reEncrypt/{doc_no}")
    public ResponseEntity reEncrypt(@PathVariable String doc_no) {
        certificateService.reEncrypt(doc_no);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/applyCareerCertificate/{user_id}")
    public String ApplyCareerCertificatePage(@Login User loginMember, Model model, @PathVariable String user_id) {
        model.addAttribute("userInfo", userService.findByUserDetail(user_id));
        model.addAttribute("user", loginMember);
        return "certificate/applyCertificateCareer";
    }

    @PostMapping("/applyCareer")
    public ResponseEntity PostApplyCareerCertificatePage(@Validated Certificate certificate) {
        return new ResponseEntity(Map.of("getDocNo", certificateService.insertCertificateCareer(certificate)), HttpStatus.OK);
    }

    @GetMapping("/applyCertificateEn/{user_id}")
    public String ApplyCertificateEnPage(@Login User loginMember, Model model, @PathVariable String user_id) {
        model.addAttribute("userInfo", userService.findByUserDetail(user_id));
        model.addAttribute("user", loginMember);
        return "certificate/applyCertificateEn";
    }

    @PostMapping("/applyEn")
    public ResponseEntity PostApplyCertificateEnPage(@Validated Certificate certificate) {
        return new ResponseEntity(Map.of("getDocNo", certificateService.insertCertificateEn(certificate)), HttpStatus.OK);
    }
}