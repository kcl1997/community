package life.majiang.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;

/**
 * 项目名： community
 * 包名:    life.majiang.community.service
 * 文件名   QuestionService
 * 创建者
 * 创建时间: 2020/3/18 11:37 PM
 * 描述  不仅仅是可以使用QuestionMapper 也可以使用User
 *      起到一个组装作用
 *         中间层
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param page
     * @param size
     * @return 返回page对象 包括5个问题，和页码信息
     */
    public PaginationDTO list(Integer page, Integer size) {

        //返回page对象
        PaginationDTO paginationDTO = new PaginationDTO();
        //总数
        Integer totolCount = questionMapper.count();
        //总页数
        Integer totolPage;
        if(totolCount % size == 0){
            totolPage = totolCount / size;
        }else{
            totolPage = totolCount / size + 1;
        }
        //设置paginatioinDTO 里面的全部参数
        paginationDTO.setPaginationDTO(totolPage,page,size);


        if(page < 1) page = 1;
        if(page > totolPage) page = totolPage;
        Integer offset = size * (page-1);
        //questionDTquestionDTOListOList对象包含用户头像信息
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questions = questionMapper.list(offset,size);
        for(Question question : questions){
           User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //把question对象的属性拷贝到questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOs(questionDTOList);
        return paginationDTO;
    }

    /**
     * 查询用户发起的问题
     */
    public  PaginationDTO list(Integer userId,Integer page,Integer size){
        //返回page对象
        PaginationDTO paginationDTO = new PaginationDTO();
        //总数
        Integer totolCount = questionMapper.countByUserId(userId);
        //总页数
        Integer totolPage;
        if(totolCount % size == 0){
            totolPage = totolCount / size;
        }else{
            totolPage = totolCount / size + 1;
        }
        //设置paginatioinDTO 里面的全部参数
        paginationDTO.setPaginationDTO(totolPage,page,size);


        if(page < 1) page = 1;
        if(page > totolPage) page = totolPage;
        Integer offset = size * (page-1);
        //questionDTquestionDTOListOList对象包含用户头像信息
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        for(Question question : questions){
            User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //把question对象的属性拷贝到questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOs(questionDTOList);
        return paginationDTO;
    }

    /**
     * 获取问题详细界面
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id){
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 更新或者插入qeuestion
     * 如果question 有id，则是更新数据
     * 如果没有id，说明是插入新的question
     * @param question
     */
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}

