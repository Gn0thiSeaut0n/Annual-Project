package hello.login.domain.dao;

import hello.login.domain.dto.Annual;
import hello.login.domain.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface LoginDAO {

    Optional<User> findByUserId(String user_id);

    Optional<Annual> findByAnnual(String str);
}
