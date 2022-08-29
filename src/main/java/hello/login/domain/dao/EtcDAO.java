package hello.login.domain.dao;

import hello.login.domain.dto.History;
import hello.login.domain.dto.User;
import hello.login.domain.dto.UserAnnual;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EtcDAO {

    void insertApplicationHistory(History history);

    void updateAnnual(Map<String, String> map);

    List<History> findByHistory(String user_id);

    List<History> findByAllHistory();

    void deleteHistory(String history_id);

    void updateAppr(String history_id);

    List<User> findByAllUser();

    List<UserAnnual> findByAllUserAnnual(String year);

    int findByHistoryAllCnt(String user_id);

    List<History> findByHistoryPaging(Map<String, Object> pageParam);

    int findByAllHistoryCnt(Map<String, String> searchParam);

    List<History> findByAllHistoryPaging(Map<String, Object> pageParam);
}
