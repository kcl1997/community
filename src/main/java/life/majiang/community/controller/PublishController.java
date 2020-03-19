package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;

/**
 * 项目名： community
 * 包名:    life.majiang.community.controller
 * 文件名   PUblishController
 * 创建者
 * 创建时间: 2020/3/18 12:20 AM
 * 描述  ${TODO}
 */
@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description")String description,
                            @RequestParam("tag")String tag,
                            HttpServletRequest request,
                            Model model){
        //如果用户未登录，登录失败，保存页面原有的标题，描述，tag
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        User user = null;
        try {
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length > 0){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("token")){
                        String  token =  cookie.getValue();
                        user = userMapper.findByToken(token);
                        if(user != null) request.getSession().setAttribute("user",user);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user == null || user.getName().length() < 1){
            model.addAttribute("error","用户未登录");
            System.out.println("用户未登录");
            return "publish";
        }


        //后端校验
        if(title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return  "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","描述不能为空");
            return  "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }



        Question question  = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        //发布成功,挑战到首页
        return "redirect:/index";
    }
}

