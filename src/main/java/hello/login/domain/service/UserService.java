package hello.login.domain.service;

import hello.login.domain.dao.UserDAO;
import hello.login.domain.dto.User;
import hello.login.web.util.JasyptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${cypher.key}")
    String cypherKey;

    private final UserDAO userDAO;

    public int duplicateId(String user_id) {
        return userDAO.duplicateId(user_id);
    }

    public void userRegister(User user) {
        user.setUser_pw(JasyptUtil.encrypt(cypherKey));
        userDAO.userRegister(user);
    }

    public List<User> findByAllUserPaging(Map<String, Object> pageParam) {
        return userDAO.findByAllUserPaging(pageParam);
    }

    public int findByAllUserCnt(Map<String, Object> pageParam) {
        return userDAO.findByAllUserCnt(pageParam);
    }

    public User findByUserDetail(String user_id) {
        return userDAO.findByUserDetail(user_id);
    }

    public void userUpdate(User user) {
        userDAO.userUpdate(user);
    }

    public void userDelete(User user) {
        userDAO.userDeleteApplication(user);
        userDAO.userDeleteAnnualStatus(user);
        userDAO.userDelete(user);
    }

    public void passwordInit(User user) {
        user.setUser_pw(JasyptUtil.encrypt(cypherKey));
        userDAO.passwordInit(user);
    }

	public void annualRegister(User user) {
		userDAO.annualRegister(user);
	}
}
