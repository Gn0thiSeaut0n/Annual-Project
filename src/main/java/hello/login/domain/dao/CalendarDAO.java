package hello.login.domain.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import hello.login.domain.dto.History;

@Mapper
public interface CalendarDAO {

	List<History> calendarHistory(Map<String, Object> map);
}
