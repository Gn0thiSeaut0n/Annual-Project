package hello.login.web.errors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class Error implements ErrorController {
    private String ERROR_TEMPLATES_PATH = "errors/";

    // 에러 페이지 추가 시 주석 풀고 진행
    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) throws InterruptedException {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

//        if(status != null){
//            int statusCode = Integer.valueOf(status.toString());
//            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return ERROR_TEMPLATES_PATH + "404";
//            }
//           else if(statusCode == HttpStatus.FORBIDDEN.value()){
//                return ERROR_TEMPLATES_PATH + "500";
//            }
//        }

//        return ERROR_TEMPLATES_PATH + "404";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}