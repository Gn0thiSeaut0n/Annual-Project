package hello.login.domain.service;

import hello.login.domain.dao.UserDAO;
import hello.login.domain.dto.User;
import hello.login.web.util.JasyptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDAO userDAO;

    public int duplicateId(String user_id) {
        return userDAO.duplicateId(user_id);
    }

    public void userRegister(User user) {
        user.setUser_pw(JasyptUtil.encrypt("gbajsdpstltm"));
        userDAO.userRegister(user);
    }
}
