package hello.login.domain.service;

import hello.login.domain.dao.CertificateDAO;
import hello.login.domain.dto.Certificate;
import hello.login.web.util.JasyptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CertificateService {

    private final CertificateDAO certificateDAO;

    public void insertCertificate(Certificate certificate) {
        certificate.setIdentification_no_back(JasyptUtil.encrypt(certificate.getIdentification_no_back()));
        certificateDAO.insertCertificate(certificate);
    }

    public List<Certificate> selectCertificateAll(Map<String, Object> pageParam) {
        return certificateDAO.selectCertificateAll(pageParam);
    }

    public void approveCertificate(String doc_no) {
        certificateDAO.approveCertificate(doc_no);
    }

    public Certificate findByDocNoDetail(String doc_no) {
        return certificateDAO.findByDocNoDetail(doc_no);
    }

    public void deleteCertificate(String doc_no) {
        certificateDAO.deleteCertificate(doc_no);
    }

    public String savePDF(String doc_no) {
        Certificate certificate = certificateDAO.findByDocNoDetail(doc_no);
        certificate.setIdentification_no_back(JasyptUtil.decrypt(certificate.getIdentification_no_back()));
        certificateDAO.updateSocialNumber(certificate);
        return certificate.getIdentification_no_back();
    }

    public void reEncrypt(String doc_no) {
        Certificate certificate = certificateDAO.findByDocNoDetail(doc_no);
        certificate.setIdentification_no_back(JasyptUtil.encrypt(certificate.getIdentification_no_back()));
        certificateDAO.updateSocialNumber(certificate);
    }

    public String insertCertificateCareer(Certificate certificate) {
        certificateDAO.insertCertificateCareer(certificate);
        return certificateDAO.getRecentDocNo();
    }

    public String insertCertificateEn(Certificate certificate) {
        certificateDAO.insertCertificateEn(certificate);
        return  certificateDAO.getRecentDocNo();
    }

    public int findByAllCertificateCnt(Map<String, String> searchParam) {
        return certificateDAO.findByAllCertificateCnt(searchParam);
    }
}
