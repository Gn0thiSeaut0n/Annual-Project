package hello.login.domain.dao;

import hello.login.domain.dto.AnnualList;
import hello.login.domain.dto.History;
import hello.login.domain.dto.MonthAndDayList;
import hello.login.domain.dto.UserAnnual;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnnualDAO {

    int findByAllHistoryCnt(Map<String, String> searchParam);

    List<History> findByAllHistoryPaging(Map<String, Object> pageParam);

    int findByAllUserAnnualCnt(Map<String, String> pageParam);

    List<UserAnnual> findByAllUserAnnualPaging(Map<String, Object> pageParam);

    List<MonthAndDayList> selectAnnualMonth(Map<String, String> map);

    UserAnnual selectTotalAnnualMonth(Map<String, String> year);

    AnnualList findByAllAnnual();

    void annualUpdate(AnnualList annualList);

    History findByFileId(String file_id);

    void companionHistory(String history_id);

    void updateHistory(Map<String, String> map);
}
