package hello.login.web.certificate;

import hello.login.domain.dto.Certificate;
import hello.login.domain.dto.User;
import hello.login.domain.service.CertificateService;
import hello.login.domain.service.UserService;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final UserService userService;

    @GetMapping("/certificateList")
    public String certificateListPage(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "") String year,
                                      @RequestParam(defaultValue = "") String month,
                                      @RequestParam(defaultValue = "") String user_name,
                                      @Validated Certificate certificate,
                                      Model model) {
        List<Certificate> certificateList = certificateService.selectCertificateAll();
        model.addAttribute("user", loginMember);
        model.addAttribute("certificate", certificateList);
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
    public ResponseEntity PostApplyCertificatePage(@Validated Certificate certificate) {
        certificateService.insertCertificate(certificate);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/approve/{doc_no}")
    public ResponseEntity ApproveCertificatePage(@PathVariable String doc_no) {
        certificateService.approveCertificate(doc_no);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{doc_no}")
    public ResponseEntity deleteCertificate(@PathVariable String doc_no) {
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