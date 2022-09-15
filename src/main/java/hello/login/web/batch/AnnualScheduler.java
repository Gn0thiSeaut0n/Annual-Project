package hello.login.web.batch;

import hello.login.domain.dao.BatchDAO;
import hello.login.domain.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnnualScheduler {

    private final BatchDAO batchDAO;

    // "* * * * * *" 초(0~59), 분(0~59), 시(0~23), 일(1~31), 월(1~12), 요일(0~7)
    @Scheduled(cron = "0 0 1 * * *")   // 매일 새벽 1시
    public void annualTest() {
        log.info("연차 초기화 실행");

        List<User> userList = batchDAO.findByAllUser();     // 유저 아이디를 구한다

        for (User userInfo : userList) {
            String user_id = userInfo.getUser_id();
            String userAnnual = batchDAO.findByUserAnnual(user_id); // 유저가 몇 년차인지 구한다

            //신입인 경우
            if (userAnnual.equals("0")) {
                // 유저가 몇 개월차인지 구한다
                updateUser(user_id, batchDAO.findByUserMonth(user_id));
            } else {
                // 유저에 맞는 연차수를 구한다
                updateUser(user_id, batchDAO.findByAnnual(userAnnual));
            }

            // 유저 입사일 구하기
            String start_year = userInfo.getStart_year().substring(5);

            // 현재 날짜 구하기
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String nowTime = sdf.format(new Date());

            // start_year | nowTime
            //   09-14    |  09-12 -> result = 1
            //   09-14    |  09-14 -> result = 0 -> 년도 상관없이 입사일이랑 현재날짜랑 동일할경우 사용연차 초기화
            //   09-14    |  09-16 -> result = -1
            if (start_year.compareTo(nowTime) == 0) {
                 batchDAO.userAnnualInit(user_id);
            }
        }
    }

    private void updateUser(String user_id, String month) {
        batchDAO.updateUserAnnual(Map.of("user_id", user_id, "total_annual", month));
    }
}
