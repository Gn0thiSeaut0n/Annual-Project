package hello.login.web.annual;

import hello.login.domain.dao.EmailDAO;
import hello.login.domain.dto.*;
import hello.login.domain.service.AnnualService;
import hello.login.domain.service.EmailService;
import hello.login.web.argumentresolver.Login;

import hello.login.web.batch.AnnualScheduler;
import hello.login.web.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AnnualController {

    private final AnnualService annualService;
    private final AnnualScheduler annualScheduler;
    private final FileStore fileStore;
    private final EmailService emailService;
    private final EmailDAO emailDAO;

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
        model.addAttribute("fileList", annualService.findByAllFileList());
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
        Map<Integer, Map> smallBox = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 31; j++) {
                smallBox.put(j, new HashMap());
            }
            bigBox.put(i, new HashMap<>(smallBox));
        }

        annualService.selectAnnualMonth(Map.of("year", year, "user_id", user)).stream().forEach(
                (data) -> {
                    Map inSmallBox = new HashMap<>();
                    inSmallBox.put("day", data.getApplication_year());

                    if(data.getHoliday() != null) {
                        inSmallBox.put("holiday", data.getHoliday());
                    }

                    bigBox.get(Integer.valueOf(data.getMonth())).put(Integer.valueOf(data.getDay()), inSmallBox);
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
        annualScheduler.annualInit();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/companionHistory/{history_id}/{user_id}/{application_year}")
    public ResponseEntity companionHistory(@ModelAttribute History history, @Login User loginMember) throws Exception{
        History mailObj = emailDAO.selectToUser(history.getHistory_id());
        emailService.sendMail("반려", mailObj, loginMember);
        annualService.companionHistory(history.getHistory_id());
        annualService.updateHistory(Map.of("user_id", history.getUser_id(), "application_year", history.getApplication_year()));
        annualService.deleteFileInfo(history.getHistory_id());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/allFileDownload")
    public void fileDown(@RequestParam Map<String, Object> param, HttpServletResponse response) {
        String storeFileName = (String) param.get("file_uuid");
        String uploadFileName = (String) param.get("file_name");
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        try {
            URL url = new URL("file:" + fileStore.getFullPath(storeFileName));
            response.setHeader("Content-disposition", "attachment;filename=" + encodedUploadFileName);
            response.setContentType("application/octer-stream");
            InputStream is = url.openStream();

            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
            int len;
            byte[] buf = new byte[1024];

            while ((len = is.read(buf)) > 0) {
                outs.write(buf, 0 , len);
            }

            outs.close();

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @GetMapping("/fileDownload/{file_uuid}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String file_uuid) throws MalformedURLException {

        History history = annualService.findByFile(file_uuid);

        String storeFileName = history.getFile_uuid();
        String uploadFileName = history.getOrigin_file_name();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @GetMapping("/annualManageStatus")
    public String annualManageStatus(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "") String year,
                                     Model model) {

        Pagination pagination = new Pagination(annualService.findByAllUserAnnualStatusCnt(), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("pagination", pagination);
        model.addAttribute("allUser", annualService.findByAllUserAnnualStatusPaging(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(),"year", year)));

        return "annual/annualManageStatus";
    }
}
