package hello.login.domain.service;

import hello.login.domain.dao.AnnualDAO;
import hello.login.domain.dto.AnnualList;
import hello.login.domain.dto.History;
import hello.login.domain.dto.MonthAndDayList;
import hello.login.domain.dto.UserAnnual;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnnualService {

    private final AnnualDAO annualDAO;

    public int findByAllHistoryCnt(Map<String, String> searchParam) {
        return annualDAO.findByAllHistoryCnt(searchParam);
    }

    public List<History> findByAllHistoryPaging(Map<String, Object> pageParam) {
        return annualDAO.findByAllHistoryPaging(pageParam);
    }

    public int findByAllUserAnnualCnt(Map<String, String> pageParam) {
        return annualDAO.findByAllUserAnnualCnt(pageParam);
    }

    public List<UserAnnual> findByAllUserAnnualPaging(Map<String, Object> pageParam) {
        return annualDAO.findByAllUserAnnualPaging(pageParam);
    }

    public List<MonthAndDayList> selectAnnualMonth(Map<String, String> map) {
        return annualDAO.selectAnnualMonth(map);
    }

    public UserAnnual selectTotalAnnualMonth(Map<String, String> year) {
        return annualDAO.selectTotalAnnualMonth(year);
    }

    public AnnualList findByAllAnnual() {
        return annualDAO.findByAllAnnual();
    }

    public void annualUpdate(AnnualList annualList) {
        annualDAO.annualUpdate(annualList);
    }
}