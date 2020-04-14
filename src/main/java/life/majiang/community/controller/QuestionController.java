package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import life.majiang.community.service.QuestionService;

/**
 * 项目名： community
 * 包名:    life.majiang.community.controller
 * 文件名   QuestionController
 * 创建者
 * 创建时间: 2020/3/20 5:41 PM
 * 描述  游客，其他人员 查看问题
 */


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")String id, Model model, HttpServletRequest request){

        QuestionDTO questionDTO = questionService.getById(Long.parseLong(id));

        List<CommentDTO> commentDTOS = commentService.listByTargetId(Long.parseLong(id), CommentTypeEnum.QUESTION);

        /**
         * 添加阅读数功能
         * 累加阅读数
         */
        questionService.incView(Long.parseLong(id));

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOS);
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "question";
    }

}

