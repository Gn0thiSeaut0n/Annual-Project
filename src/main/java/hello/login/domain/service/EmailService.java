package hello.login.domain.service;

import hello.login.domain.dao.EmailDAO;
import hello.login.domain.dto.History;
import hello.login.domain.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class EmailService {
	private final JavaMailSender emailSender;
	private final SpringTemplateEngine templateEngine;
	private final EmailDAO emailDAO;
	
	/**
	 * 이메일 발송
	 * @param type 유형
	 * @param history 연차 Data
	 * @param loginMember 결재/반려 업무 대상자
	 * @throws Exception
	 */

	@Async
	public void sendMail(String type, History history, User loginMember) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		//템플릿에 전달할 데이터 설정
		HashMap<String, String> emailValues = new HashMap<>();

		if(loginMember.getAuth().equals("admin")) type = "최종 승인";

		switch (type) {
			case "상신": {
				String[] manager = emailDAO.selectToManager();
				mailTemplate(helper, emailValues, history, manager, type, " 신청입니다.");
				break;
			}
			case "승인": {
				String[] admin = emailDAO.selectToAdmin();
				mailTemplate(helper, emailValues, history, admin, type, " 결재 요청입니다.");
				break;
			}
			case "반려": {
				String[] user = { history.getEmail() };
				mailTemplate(helper, emailValues, history, user, type, "는 반려되었습니다.");
				break;
			}
			case "최종 승인": {
				String[] user = { history.getEmail() };
				mailTemplate(helper, emailValues, history, user, type, "가 최종 승인되었습니다.");
				break;
			}
		}

        Context context = new Context();
        emailValues.forEach((key, value)->{
            context.setVariable(key, value);
        });

        //메일 내용 설정 : 템플릿 프로세스
        String html = templateEngine.process("mail/mailForm", context);
        helper.setText(html, true);

        //템플릿에 들어가는 이미지 cid로 삽입
//        helper.addInline("image", new ClassPathResource("static/images/xxx.png"));

        emailSender.send(message);
	}

	private static void mailTemplate(MimeMessageHelper helper, HashMap<String, String> emailValues, History history, String[] to, String type, String message) throws MessagingException {
		String info = history.getTime();

		if(StringUtils.hasText(history.getHoliday())) {
			info = info + "[" + history.getHoliday() + "]";
		}

		helper.setSubject("[연차 "+ type + "] " + history.getUser_name() + "님의 연차" + message);
		helper.setTo(to);
		emailValues.put("text", history.getUser_name() + "님의 연차" + message);
		emailValues.put("date", history.getStart_date() + " ~ " + history.getEnd_date());
		emailValues.put("type", info);
		emailValues.put("reason", history.getReason());
	}
}