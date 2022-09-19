package hello.login.domain.service;

import hello.login.domain.dao.EtcDAO;
import hello.login.domain.dto.History;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public void updateAnnual(Map<String, Object> map) {
        etcDAO.updateAnnual(map);
    }

    public List<History> findByHistory(String user_id) {
        return etcDAO.findByHistory(user_id);
    }

    public void deleteHistory(String history_id) {
        etcDAO.deleteHistory(history_id);
    }

    public void updateAppr(Map<String, String> map) {
        etcDAO.updateAppr(map);
    }

	public int findByHistoryAllCnt(String user_id) {
		return etcDAO.findByHistoryAllCnt(user_id);
	}

	public List<History> findByHistoryPaging(Map<String, Object> pageParam) {
		return etcDAO.findByHistoryPaging(pageParam);
	}

	public String selectCurrentPwd(String user_id) {
		return etcDAO.selectCurrentPwd(user_id);
	}

	public void updatePwd(Map<String, String> map) {
		etcDAO.updatePwd(map);
	}

}

