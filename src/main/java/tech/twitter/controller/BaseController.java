package tech.twitter.controller;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//this catches the exception happens in any get request controller
public class BaseController {
    @ExceptionHandler

    //@ResponseBody used only when message needs to print
    public String defaultErrorHandler(HttpServletRequest req,  Exception e) throws Exception {
        //System.out.println(e.getMessage());
        //modelMap.addAttribute("MESSAGE","something went wrong");
        return "alien";
        //return "error";
    }
}
