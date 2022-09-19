package hello.login.domain.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import hello.login.domain.dao.CalendarDAO;
import hello.login.domain.dto.History;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {
	private final CalendarDAO calendarDAO;
	
    public List<History> calendarHistory(String year, String month) {
    	List<History> list = calendarDAO.calendarHistory(Map.of("year", year, "month", month));

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
	    			hash.put("backgroundColor", "#D6F0F3");
	    			
	    			if(list.get(i).getStart_date().equals(list.get(i).getEnd_date())) {
	    				hash.put("start", list.get(i).getStart_date());
	            		hash.put("end", list.get(i).getEnd_date());
	    			} else {
	    				Date date = null;
	    				try {
							date = df.parse(list.get(i).getEnd_date());
						} catch (ParseException e) {
							e.printStackTrace();
						}
	    				cal.setTime(date);
	    				cal.add(Calendar.DATE, 1);

	    				hash.put("start", list.get(i).getStart_date());
	            		hash.put("end", df.format(cal.getTime()));
	    			}
	    			break;
	    		}
	    		
	    		case "오전": {
	    			hash.put("start", list.get(i).getStart_date()+"T09:00:00");
		    		hash.put("end", list.get(i).getEnd_date());
		    		hash.put("backgroundColor", "#FF0000");
		    		break;
	    		}
	    		
	    		case "오후": {	    			
	    			hash.put("start", list.get(i).getStart_date()+"T14:00:00");
	    			hash.put("end", list.get(i).getEnd_date());
	    			hash.put("backgroundColor", "#0000FF");
	    			break;
	    		}
    		}
    		
    		jsonObj = new JSONObject(hash);
    		jsonArr.add(jsonObj);
    	}
        return jsonArr;
    }
}
