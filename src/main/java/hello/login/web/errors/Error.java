package hello.login.web.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class Error implements ErrorController {
    private String ERROR_TEMPLATES_PATH = "/errors/";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return ERROR_TEMPLATES_PATH + "404";
            }

            if(statusCode == HttpStatus.FORBIDDEN.value()){
                return ERROR_TEMPLATES_PATH + "500";
            }
        }
        log.info("에러탔다");
        log.info("status = {}", status);

        return "redirect:/";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}