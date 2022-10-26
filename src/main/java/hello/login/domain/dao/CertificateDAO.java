package hello.login.domain.dao;

import hello.login.domain.dto.Certificate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CertificateDAO {
    void insertCertificate(Certificate certificate);

    List<Certificate> selectCertificateAll(Map<String, Object> pageParam);

    void approveCertificate(String doc_no);

    Certificate findByDocNoDetail(String doc_no);

    void deleteCertificate(String doc_no);

    void updateSocialNumber(Certificate certificate);

    void insertCertificateCareer(Certificate certificate);

    String getRecentDocNo();

    void insertCertificateEn(Certificate certificate);

    int findByAllCertificateCnt(Map<String, String> searchParam);
}
