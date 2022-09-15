package hello.login.web;

import hello.login.domain.dto.Annual;
import hello.login.domain.dto.User;
import hello.login.domain.service.LoginService;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;

    @GetMapping("/")
    public String homeLogin(@Login User loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "login/loginForm";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("user", loginMember);
        Annual annual = loginService.annual(loginMember.getUser_id());
        model.addAttribute("annual", annual);
        return "home";
    }

}