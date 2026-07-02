package tech.twitter.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tech.twitter.database.GenericDB;
import tech.twitter.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/")
public class MainController extends BaseController {

    private static Logger logger = Logger.getLogger(MainController.class);

//    @RequestMapping(method = RequestMethod.GET, value = "/helloworld")
//    public String getQuiz(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
//        return "hello";
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/signup")
    public String signUp(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public
    @ResponseBody
    SignupResponse signUpData(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        boolean user_created=false;
        String message="";
        if(GenericDB.getCount(tech.twitter.tables.Member.MEMBER, tech.twitter.tables.Member.MEMBER.EMAIL.eq(member.email))>0){
            message="User already exist for this email id!";
        }
        else{
            member.role="cm";
            new GenericDB<Member>().addRow(tech.twitter.tables.Member.MEMBER,member);
            user_created=true;
            message="User Created!";
        }
        return new SignupResponse(message,user_created);
    }



//    @RequestMapping(method = RequestMethod.POST, value = "/handle")
//    public
//    @ResponseBody
//    String handleEncrypt(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
//        return "ok";
//    }
}