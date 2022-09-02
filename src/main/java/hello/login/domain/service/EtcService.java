package hello.login.domain.service;

import hello.login.domain.dao.EtcDAO;
import hello.login.domain.dto.History;
import hello.login.domain.dto.MonthAndDayList;
import hello.login.domain.dto.UserAnnual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EtcService {

    private final EtcDAO etcDAO;

    public void insertApplicationHistory(History history) {
        etcDAO.insertApplicationHistory(history);
    }

    public void updateAnnual(String user_id, Float use_annual) {
        etcDAO.updateAnnual(Map.of("user_id", user_id, "use_annual", ""+use_annual));
    }

    public List<History> findByHistory(String user_id) {
        return etcDAO.findByHistory(user_id);
    }

    public List<History> findByAllHistory() {
        return etcDAO.findByAllHistory();
    }

    public void deleteHistory(String history_id) {
        etcDAO.deleteHistory(history_id);
    }

    public void updateAppr(String history_id) {
        etcDAO.updateAppr(history_id);
    }

    public List<UserAnnual> findByAllUserAnnualPaging(Map<String, Object> pageParam) {
        return etcDAO.findByAllUserAnnualPaging(pageParam);
    }

    public int findByHistoryAllCnt(String user_id) {
        return etcDAO.findByHistoryAllCnt(user_id);
    }

    public List<History> findByHistoryPaging(int startIndex, int pageSize, String user_id) {
        return etcDAO.findByHistoryPaging(Map.of("startIndex", startIndex, "pageSize", pageSize, "user_id", user_id));
    }

    public int findByAllHistoryCnt(String year, String month, String user_name) {
        return etcDAO.findByAllHistoryCnt(Map.of("year", year, "month", month, "user_name", user_name));
    }

    public List<History> findByAllHistoryPaging(Map<String, Object> pageParam) {
        return etcDAO.findByAllHistoryPaging(pageParam);
    }

    public List<MonthAndDayList> selectAnnualMonth(String year, String user) {
        return etcDAO.selectAnnualMonth(Map.of("year", year,"user_id", user));
    }

    public String selectCurrentPwd(String user_id) {
        return etcDAO.selectCurrentPwd(user_id);
    }

    public void updatePwd(String encrypt_user_pw, String user_id) {
        Map<String, String> userParam = new HashMap<>();
        userParam.put("user_pw", encrypt_user_pw);
        userParam.put("user_id", user_id);
        etcDAO.updatePwd(userParam);
    }

    public int findByAllUserAnnualCnt(String year, String user_name) {
        return etcDAO.findByAllUserAnnualCnt(Map.of("year", year, "user_name", user_name));
    }
}

//    public List<String> selectAllDate() {
//        return etcDAO.selectAllDate();
//    }
//}
