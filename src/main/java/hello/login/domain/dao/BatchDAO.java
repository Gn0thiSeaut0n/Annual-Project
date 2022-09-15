package hello.login.domain.dao;

import hello.login.domain.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BatchDAO {

    List<User> findByAllUser();

    String findByUserAnnual(String user_id);

    String findByUserMonth(String user_id);

    void updateUserAnnual(Map<String, String> user);

    String findByAnnual(String userAnnual);

    void userAnnualInit(String user_id);
}
