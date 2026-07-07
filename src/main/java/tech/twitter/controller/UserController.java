package tech.twitter.controller;

import org.jooq.Condition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.twitter.database.GenericDB;
import tech.twitter.entity.Follower;
import tech.twitter.entity.Member;
import tech.twitter.entity.Tweet;
import tech.twitter.entity.TweetUI;
import tech.twitter.global.SysProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {


    @RequestMapping(method = RequestMethod.POST, value = "/create-post")
    @ResponseBody
    public String createTweet(@RequestBody String data, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Tweet tweet = new Tweet(data, null, new Date().getTime(), ControllerUtils.getUserId(httpServletRequest));
        new GenericDB<Tweet>().addRow(tech.twitter.tables.Tweet.TWEET, tweet);
        return "Posted successfully";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/welcome")
    public String welcomePage(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request, Model model) {
        Member member = ControllerUtils.getCurrentMember(request);
        modelMap.addAttribute("NAME", member.name);
        modelMap.addAttribute("Model_Member", member);
        return "welcome";

    }

    @RequestMapping(method = RequestMethod.POST, value = "/public-tweet/{id}")
    public
    @ResponseBody
    List<TweetUI> fetchTweet(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

        Condition condition = tech.twitter.tables.Tweet.TWEET.ID.lessThan(id);
        List<Tweet> data = (List<Tweet>) GenericDB.getRows(tech.twitter.tables.Tweet.TWEET,Tweet.class,condition,3, tech.twitter.tables.Tweet.TWEET.ID.desc());
        Set<Long> memberIds = new HashSet<Long>();
        for(Tweet tweet:data){
            memberIds.add(tweet.author_id);
        }
        HashMap<Long,Member> memberHashMap = new HashMap<Long, Member>();
        Condition memberCondition = tech.twitter.tables.Member.MEMBER.ID.in(memberIds);
        List<Member> members = (List<Member>) GenericDB.getRows(tech.twitter.tables.Member.MEMBER,Member.class,memberCondition,null);

        for(Member member: members){
            memberHashMap.put(member.id,member);
        }

        ArrayList<TweetUI> tweetUIS = new ArrayList<TweetUI>();
        for(Tweet tweet:data){
            Member member = memberHashMap.get(tweet.author_id);
            TweetUI tweetUI = new TweetUI(tweet,member);
            tweetUIS.add(tweetUI);

        }
        return tweetUIS;
    }



    @RequestMapping(method = RequestMethod.GET, value = "/recommendations")
    public String welcome(ModelMap modelMap, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Model model) {
        Member member = ControllerUtils.getCurrentMember(httpServletRequest);
        //access only when he is loggined
        List<Member> members = (List<Member>) GenericDB.getRows(tech.twitter.tables.Member.MEMBER, Member.class, null, null, tech.twitter.tables.Member.MEMBER.ID.desc());
        //members data
        ArrayList<Long> memberIds = new ArrayList<Long>();
        for(Member member1:members){
            memberIds.add(member1.id);
        }
        Condition condition = tech.twitter.tables.Follower.FOLLOWER.USER_ID.eq(member.id).and(tech.twitter.tables.Follower.FOLLOWER.FOLLOWING_ID.in(memberIds));
        List<Follower> followerRows = (List<Follower>) GenericDB.getRows(tech.twitter.tables.Follower.FOLLOWER,Follower.class,condition,null);

        Set<Long> followedMemberIds = new HashSet<Long>();
        for(Follower follower:followerRows){
            followedMemberIds.add(follower.following_id);
        }

        for(Member memberTemp: members){
            if(followedMemberIds.contains(memberTemp.id)){
                //this member is followed already !
                memberTemp.is_followed = true;
            }
        }

        modelMap.addAttribute("NAME", member.name);
        modelMap.addAttribute("LOGGEDID",member.id);
        modelMap.addAttribute("RECOMMENDATIONS", members);
        preloadVariables(modelMap,member.id);
        return "recommendations";
    }

    private void preloadVariables(ModelMap modelMap,Long currentMemberId){
        modelMap.addAttribute("USER_IMAGE","/images/profile-image/"+currentMemberId+".jpeg");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/follow-member/{member_id}")
    @ResponseBody
    public String followMember(@PathVariable("member_id") Long memberId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //the memberId in requestBody is the memberid that we followed
        //and the person who loggedin member id came by ContolUtils.getId(requesthttp)
        //the Follower table add row with those memberids
        Long currentUserId = ControllerUtils.getUserId(httpServletRequest);
        if (currentUserId != null && memberId != null && memberId != currentUserId) {
            Follower follower = new Follower(currentUserId, memberId);
            new GenericDB<Follower>().addRow(tech.twitter.tables.Follower.FOLLOWER, follower);
            return "Connected Successfully";
        }
        return "Not permitted! ";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/unfollow-member/{member_id}")
    @ResponseBody
    public String unfollowMember(@PathVariable("member_id") Long memberId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long currentUserId = ControllerUtils.getUserId(httpServletRequest);
        if (currentUserId != null && memberId != null && memberId != currentUserId) {
            Condition condition = tech.twitter.tables.Follower.FOLLOWER.USER_ID.eq(currentUserId).and(tech.twitter.tables.Follower.FOLLOWER.FOLLOWING_ID.eq(memberId));
            GenericDB.deleteRow(tech.twitter.tables.Follower.FOLLOWER,condition);
            return "unfollowed Successfully";
        }
        return "Not permitted! ";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/followed")
    public String followedMembers(ModelMap modelMap, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Model model) {
        Long currUserId = ControllerUtils.getUserId(httpServletRequest);
        //access only when he is loggined
        //since there is no response body we just return the file.jsp
        //step1-> Find the rows in follower table with userId as the currUserId that is loggedin.
        //step2-> we have rows of userId|followerId
        //step3-> Loop over the List and get members for each id == followerId
        //step4-> Send the membersArrayList to an attribute in modelMap, the .jsp code iterate over and prints the members one by one beautifully
        List<Follower> followedPeople = (List<Follower>) GenericDB.getRows(tech.twitter.tables.Follower.FOLLOWER,Follower.class, tech.twitter.tables.Follower.FOLLOWER.USER_ID.eq(currUserId),null);
        List<Member> membersFollowed = new LinkedList<Member>();
        for( Follower followPerson:followedPeople) {
            Member member = new GenericDB<Member>().getRow(tech.twitter.tables.Member.MEMBER, Member.class, tech.twitter.tables.Member.MEMBER.ID.eq(followPerson.following_id));
            membersFollowed.add(member);
        }
        preloadVariables(modelMap,currUserId);
        modelMap.addAttribute("FOLLOWING",membersFollowed);
        return "followed";//this is same as recommendations.jsp but we print only our following people
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile-image/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile, HttpServletRequest request) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        String path = "";
        try {
            Long currentMemberId = ControllerUtils.getUserId(request);
            path = saveUploadedFile(uploadfile,currentMemberId);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity responseEntity = new ResponseEntity(path, new HttpHeaders(), HttpStatus.OK);
        return responseEntity;
    }

    private static String saveUploadedFile(MultipartFile uploadfile, Long currentMemberId) {
        try {
            String path = SysProperties.getBaseDir()+"/images/profile-image/"+currentMemberId+".jpeg";
            uploadfile.transferTo( new File(path));
            return "/images/profile-image/"+currentMemberId+".jpeg";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @RequestMapping(method=RequestMethod.GET,value="/update")
    public String updateUser(ModelMap modelMap, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){

        return "updateuser";
    }

}