package hello.login.web.user;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

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
        return new ResponseEntity(HttpStatus.OK);
    }
}
