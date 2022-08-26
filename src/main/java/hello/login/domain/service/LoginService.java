package hello.login.domain.service;

import hello.login.domain.dto.Annual;
import hello.login.domain.dto.LoginForm;
import hello.login.domain.dto.User;
import hello.login.domain.dao.LoginDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginDAO loginDAO;

    public User login(LoginForm form) {
        return loginDAO.findByUserId(form.getUser_id())
                .filter(u -> u.getUser_pw().equals(form.getUser_pw()))
                .orElse(null);
    }

    public Annual annual(String user_id) {
        return loginDAO.findByAnnual(user_id)
                .filter(a -> a.getUser_id().equals(user_id))
                .orElse(null);
    }
}