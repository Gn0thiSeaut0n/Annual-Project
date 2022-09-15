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

