package hello.login.domain.dao;

import hello.login.domain.dto.History;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailDAO {
	String[] selectToManager();
	
	History selectToUser(String history);

	String[] selectToAdmin();
}
