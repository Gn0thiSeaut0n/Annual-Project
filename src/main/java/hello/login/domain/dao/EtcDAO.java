package hello.login.domain.dao;

import hello.login.domain.dto.File;
import hello.login.domain.dto.History;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface EtcDAO {

    void insertApplicationHistory(History history);

    void updateAnnual(Map<String, Object> map);

    List<History> findByHistory(String user_id);

    void deleteHistory(String history_id);

    void updateAppr(Map<String, String> map);

    int findByHistoryAllCnt(String user_id);

    List<History> findByHistoryPaging(Map<String, Object> pageParam);

    String selectCurrentPwd(String user_id);

    void updatePwd(Map<String, String> userParam);

    void insertFile(File file);

    void updateHistory(Map<String, Object> map);
}
