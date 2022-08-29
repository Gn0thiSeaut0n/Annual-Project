package hello.login.web.interceptor;

import hello.login.domain.dto.User;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        // 개발용
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        User user = new User();
        user.setUser_id("root");
//        user.setUser_id("tester");
        user.setUser_pw("1234");
        user.setUser_name("휴먼엔시스");
//        user.setUser_name("테스트");
        user.setSex("M");
        user.setResponsibilities_of_office("관리자");
        user.setDepartment("-");
        user.setBirthday(("1900-01-01"));
        user.setStart_year("2022-08-04");
        user.setUser_number("010-0000-0000");
        user.setAuth("admin");
//        user.setAuth("tester");
        session.setAttribute(SessionConst.LOGIN_MEMBER,user);
        // 개발용 end

//        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
//            log.info("미인증 사용자 요청");
//            //로그인으로 redirect
//            response.sendRedirect("/login" + requestURI);
//            return false;
//        }

        return true;
    }
}
