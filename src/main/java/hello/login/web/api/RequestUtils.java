package hello.login.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Slf4j
public class RequestUtils {

    private static String secretKey = "F%2BQOWb5uEY0zYNjCOb%2Bpc43GXxBJsU6EkJ4xK2hF7qwqlXX%2BHBq9EfQnPixbp4IgTqQnxrPt4uXP2poyIRNolg%3D%3D";

    public static Map<String, Object> holidayInfoAPI(String year) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo"); /*URL*/
        urlBuilder.append("?serviceKey=" + secretKey); /*Service Key*/
        urlBuilder.append("&numOfRows=100"); /*한 페이지 결과 수*/
        urlBuilder.append("&solYear=" + year); /*연 */
        urlBuilder.append("&_type=json"); /* json으로 요청 */

        URL url = new URL(urlBuilder.toString());
        log.info("url = {}", url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return string2Map(sb.toString());
    }

    /**
     * Json String을 Hashmap으로 반환
     *
     * @param json
     * @return
     */
    public static Map<String, Object> string2Map(String json) throws ParseException {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);
            ObjectMapper mapper = new ObjectMapper();

            Map map = (Map) ((Map) jsonObject.get("response")).get("body");
            if (!map.get("items").equals("")) {
                map = ((Map) map.get("items"));

                try {
                    map = mapper.readValue(map.toString(), Map.class);
                    log.info("map = {}", map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return map;
    }
}