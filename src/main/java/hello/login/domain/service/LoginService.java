package hello.login.domain.service;

import hello.login.domain.dao.EtcDAO;
import hello.login.domain.dto.Annual;
import hello.login.domain.dto.LoginForm;
import hello.login.domain.dto.User;
import hello.login.domain.dao.LoginDAO;
import hello.login.web.util.JasyptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginDAO loginDAO;
    private final EtcDAO etcDAO;

    public User login(LoginForm form) {
        // DB 비밀번호 복호화
        String decrypt_user_pw = JasyptUtil.decrypt(etcDAO.selectCurrentPwd(form.getUser_id()));

        return loginDAO.findByUserId(form.getUser_id())
                .filter(u -> decrypt_user_pw.equals(form.getUser_pw()))
                .orElse(null);
    }

    public Annual annual(String user_id) {
        return loginDAO.findByAnnual(user_id)
                .filter(a -> a.getUser_id().equals(user_id))
                .orElse(null);
    }
}