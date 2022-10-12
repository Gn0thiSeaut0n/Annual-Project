package hello.login.domain.dao;

import hello.login.domain.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {

    int duplicateId(String user_id);

    void userRegister(User user);

    List<User> findByAllUserPaging(Map<String, Object> pageParam);

    int findByAllUserCnt(Map<String, Object> pageParam);

    User findByUserDetail(String user_id);

    void userUpdate(User user);

    void userDelete(User user);

    void passwordInit(User user);

	void annualRegister(User user);

    void userDeleteApplication(User user);

    void userDeleteAnnualStatus(User user);
}
