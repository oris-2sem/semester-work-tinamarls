package ru.itis.delivery.controllers.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode == 404) {
            return "error/404";
        } else if (statusCode >= 500) {
            return "error/errorServer";
        }

        return "error/error";
    }

    @GetMapping("/no-access")
    public String noAccessErrorPage(){
        return "error/noAccess";
    }

    @GetMapping("/for-anonymous")
    public String forAnonymousPage(){
        return "error/forAnonymous";
    }

}
