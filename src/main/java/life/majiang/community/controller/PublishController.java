package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;

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
    @Autowired
    private QuestionService questionService;

    /**
     *  Post Mapping
     *  发布或者修改问题内容
     */
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description")String description,
                            @RequestParam("tag")String tag,
                            @RequestParam("id")Integer id,
                            HttpServletRequest request,
                            Model model){
        //如果用户未登录，登录失败，保存页面原有的标题，描述，tag
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);


        User user = (User) request.getSession().getAttribute("user");
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
        question.setId(id);

        questionService.createOrUpdate(question);

        //发布成功,挑战到首页
        return "redirect:/index";
    }

    /**
     * 根据问题id，显示问题详细内容
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,Model model){
        //获取问题，并显示到question界面上面去
        Question question = questionMapper.selectByPrimaryKey(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        //未更新做准备
        model.addAttribute("id",question.getId());

        //当点击发布按钮时，应该是更新按钮
        return "publish";
    }

}

