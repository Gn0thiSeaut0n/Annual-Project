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

    public History findByFile(String file_uuid) {
        return annualDAO.findByFile(file_uuid);
    }

    public void companionHistory(String history_id) {
        annualDAO.companionHistory(history_id);
    }

    public void updateHistory(Map<String, String> map) {
        annualDAO.updateHistory(map);
    }

    public void deleteFileInfo(String history_id) {
        annualDAO.deleteFileInfo(history_id);
    }

    public List<UserAnnual> findByAllUserAnnualStatusPaging(Map<String, Object> pageParam) {
        return annualDAO.findByAllUserAnnualStatusPaging(pageParam);
    }

    public int findByAllUserAnnualStatusCnt() {
        return annualDAO.findByAllUserAnnualStatusCnt();
    }

    public List<History> findByAllFileList() {
        return annualDAO.findByAllFileList();
    }
}
