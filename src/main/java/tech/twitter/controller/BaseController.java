package tech.twitter.controller;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


public class BaseController {
    @ExceptionHandler
    public
    @ResponseBody
    String defaultErrorHandler(HttpServletRequest req, ModelMap modelMap, Exception e) throws Exception {
        //System.out.println(e.getMessage());
        //modelMap.addAttribute("MESSAGE","something went wrong");
        return "alien";
        //return "error";
    }
}
