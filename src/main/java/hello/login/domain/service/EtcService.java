package hello.login.domain.service;

import hello.login.domain.dao.EtcDAO;
import hello.login.domain.dto.AnnualList;
import hello.login.domain.dto.History;
import hello.login.domain.dto.MonthAndDayList;
import hello.login.domain.dto.UserAnnual;
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

    public void updateAnnual(String user_id, Float use_annual) {
        etcDAO.updateAnnual(Map.of("user_id", user_id, "use_annual", ""+use_annual));
    }

    public List<History> findByHistory(String user_id) {
        return etcDAO.findByHistory(user_id);
    }

    public void deleteHistory(String history_id) {
        etcDAO.deleteHistory(history_id);
    }

    public void updateAppr(String history_id, String type) {
        etcDAO.updateAppr(Map.of("history_id", history_id, "type", type));
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

    public UserAnnual selectTotalAnnualMonth(String year, String user) {
        return etcDAO.selectTotalAnnualMonth(Map.of("year", year, "user_id", user));
    }

    public AnnualList findByAllAnnual() {
        return etcDAO.findByAllAnnual();
    }

    public void annualUpdate(AnnualList annualList) {
        etcDAO.annualUpdate(annualList);
    }
    
    public List<History> calendarHistory(String year, String month) {
    	List<History> list = etcDAO.calendarHistory(Map.of("year", year, "month", month));

    	JSONObject jsonObj = new JSONObject();
    	JSONArray jsonArr = new JSONArray();
    	
    	HashMap<String, Object> hash = new HashMap<>();
    	
    	Calendar cal = Calendar.getInstance();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	
    	
    	for(int i = 0; i < list.size(); i++) {
    		
    		hash.put("title", list.get(i).getUser_name());
    		hash.put("textColor", "#000000");

    		switch (list.get(i).getTime()) {
    		
	    		case "연차": {
	    			hash.put("backgroundColor", "#FCE4D4");
	    			
	    			if(list.get(i).getStart_date().equals(list.get(i).getEnd_date())) {
	    				
	    				hash.put("start", list.get(i).getStart_date());
	            		hash.put("end", list.get(i).getEnd_date());
	            		//hash.put("allDay", true);
	    			} else {
	    				Date date = null;
	    				try {
							date = df.parse(list.get(i).getEnd_date());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				cal.setTime(date);
	    				cal.add(Calendar.DATE, 1);

	    				hash.put("start", list.get(i).getStart_date());
	            		hash.put("end", df.format(cal.getTime()));
	            		//hash.put("allDay", true);
	    			}
	    			
	    			break;
	    		}
	    		
	    		case "오전": {
	    			hash.put("start", list.get(i).getStart_date()+"T09:00:00");
		    		hash.put("end", list.get(i).getEnd_date());
		    		hash.put("backgroundColor", "#FF0000");
		    		//hash.put("allDay", false);
		    		break;
	    		}
	    		
	    		case "오후": {	    			
	    			hash.put("start", list.get(i).getStart_date()+"T14:00:00");
	    			hash.put("end", list.get(i).getEnd_date());
	    			hash.put("backgroundColor", "#0000FF");
	    			//hash.put("allDay", false);
	    			break;
	    		}
    		}
    		
    		jsonObj = new JSONObject(hash);
    		jsonArr.add(jsonObj);
    	}
        return jsonArr;
    }
}

