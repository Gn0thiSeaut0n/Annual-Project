package hello.login.web.user;

import hello.login.domain.dto.Pagination;
import hello.login.domain.dto.User;
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
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/userManagement")
    public String userManagement(@Login User loginMember, @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "") String user_id,
                                 @RequestParam(defaultValue = "") String user_name,
                                 Model model) {

        Pagination pagination = new Pagination(userService.findByAllUserCnt(Map.of("user_id", user_id, "user_name", user_name)), page);

        model.addAttribute("user", loginMember);
        model.addAttribute("userInfo", userService.findByAllUserPaging(Map.of(
                "startIndex", pagination.getStartIndex(), "pageSize", pagination.getPageSize(),
                "user_id", user_id, "user_name", user_name)));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchParam", Map.of("user_name", user_name, "user_id", user_id));
        return "info/userManagement";
    }

    @GetMapping("/userRegister")
    public String userRegisterPage(@Login User loginMember, Model model) {
        model.addAttribute("user", loginMember);
        return "info/userRegister";
    }

    @PostMapping("/duplicateId")
    @ResponseBody
    public int duplicatedId(@RequestBody User user) {
        return userService.duplicateId(user.getUser_id());
    }

    @PostMapping("/userRegister")
    public ResponseEntity userRegister(@RequestBody @Validated User user) {
        userService.userRegister(user);
        userService.annualRegister(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/userDetail/{user_id}")
    public String userDetail(@Login User loginMember, Model model, @PathVariable String user_id) {
        model.addAttribute("user", loginMember);
        model.addAttribute("userInfo", userService.findByUserDetail(user_id));
        return "info/userDetail";
    }

    @PostMapping("/userUpdate")
    public ResponseEntity userUpdate(@RequestBody @Validated User user) {
        userService.userUpdate(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/userDelete")
    public ResponseEntity userDelete(@RequestBody User user) {
        userService.userDelete(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/passwordInit")
    public ResponseEntity passwordInit(@RequestBody User user) {
        userService.passwordInit(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
