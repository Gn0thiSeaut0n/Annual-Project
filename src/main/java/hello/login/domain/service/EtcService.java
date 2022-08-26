package hello.login.domain.service;

import hello.login.domain.dao.EtcDAO;
import hello.login.domain.dto.History;
import hello.login.domain.dto.User;
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
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("use_annual", ""+use_annual);
        etcDAO.updateAnnual(map);
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

    public List<UserAnnual> findByAllUserAnnual(String year) {
        return etcDAO.findByAllUserAnnual(year);
    }
}
