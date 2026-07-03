package tech.twitter.controller;

import org.apache.log4j.Logger;
import org.jooq.Condition;
import org.jooq.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.twitter.database.GenericDB;
import tech.twitter.entity.LoginResponse;
import tech.twitter.entity.Member;
import tech.twitter.entity.SignupResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    private static Logger logger = Logger.getLogger(LoginController.class);

//    @RequestMapping(method = RequestMethod.GET, value = "/admin")
//    public String adminLogin(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
//        return "adminlogin";
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/user")
//    public String userLogin(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
//        return "userlogin";
//    }
    @RequestMapping(method=RequestMethod.GET,value = "/welcome")
    public String welcome(ModelMap modelMap,HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest){
        return "welcomelogin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/welcome")
    public
    @ResponseBody
    LoginResponse handleLogin(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        //response body means what ever we give here that will be printed rather than the file.jsp
        Condition condition = tech.twitter.tables.Member.MEMBER.EMAIL.eq(member.email).and(tech.twitter.tables.Member.MEMBER.PASSWORD.eq(member.password));
        List<Member> x = (List<Member>) GenericDB.getRows(tech.twitter.tables.Member.MEMBER,Member.class,condition,1);
        if(x!=null && x.size()>0) {
            //condition is met, email and password are entered in RequestBody are matched and exist in Database
            Member memberTemp = x.get(0);
            ControllerUtils.setUserSession(request,memberTemp);
            return new LoginResponse(memberTemp.id,"Logged in successfully",true);
        }else{
            //wrong combination
            return new LoginResponse(null,"Wrong Combination",false);
        }

    }

}