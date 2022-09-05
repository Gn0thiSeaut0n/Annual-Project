package hello.login.domain.dao;

import hello.login.domain.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    int duplicateId(String user_id);

    void userRegister(User user);
}
