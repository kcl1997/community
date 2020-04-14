package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;

/**
 * 项目名： community
 * 包名:    life.majiang.community.controller
 * 文件名   ProfileController
 * 创建者
 * 创建时间: 2020/3/20 12:14 AM
 * 描述  我的问题，我的回复，选项卡
 */

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;



    /**
     *  根据传递的action的值，跳转 到profile 获取相应的问题列表
     * profile 界面有有很多选项卡，action 代表 "id"， 如question，replies
     * @return
     */
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size){



        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/index";
        }


        if("question".equals(action)){
            model.addAttribute("section","question");
            //同时传递界面标题
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            //同时传递界面标题
            model.addAttribute("sectionName",   "最新回复");
        }

        //我的问题列表
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);

        return "profile";
    }
}

