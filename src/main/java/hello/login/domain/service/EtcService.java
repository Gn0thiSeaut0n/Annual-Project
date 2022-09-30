package hello.login.domain.service;

import hello.login.domain.dao.EtcDAO;
import hello.login.domain.dto.File;
import hello.login.domain.dto.History;
import hello.login.domain.dto.UploadFile;
import hello.login.web.file.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EtcService {

    private final EtcDAO etcDAO;
    private final FileStore fileStore;

    public void insertApplicationHistory(History history) throws IOException {

        etcDAO.insertApplicationHistory(history);

        if (history.getUploadFiles() != null) {
            List<UploadFile> uploadFiles = fileStore.storeFiles(history.getUploadFiles());

            for (UploadFile uploadFile : uploadFiles) {
                File file = new File();
                file.setFile_uuid(uploadFile.getStoreFileName());
                file.setOrigin_file_name(uploadFile.getUploadFileName());
                etcDAO.insertFile(file);
            }
        }
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

}

