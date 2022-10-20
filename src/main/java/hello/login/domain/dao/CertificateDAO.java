package hello.login.domain.dao;

import hello.login.domain.dto.Certificate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CertificateDAO {
    void insertCertificate(Certificate certificate);

    List<Certificate> selectCertificateAll();

    void approveCertificate(String doc_no);

    Certificate findByDocNoDetail(String doc_no);

    void deleteCertificate(String doc_no);

    void updateSocialNumber(Certificate certificate);

    void insertCertificateCareer(Certificate certificate);

    String getRecentDocNo();

    void insertCertificateEn(Certificate certificate);
}
