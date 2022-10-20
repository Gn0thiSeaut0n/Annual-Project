package hello.login.domain.dao;

import org.apache.ibatis.annotations.Mapper;

import hello.login.domain.dto.History;

@Mapper
public interface EmailDAO {
	String[] selectToManager();
	
	History selectToUser(String history);

	String[] selectToAdmin();
}
