package life.majiang.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     *
     * @param page
     * @param size
     *  主页显示的问题
     * @return 返回page对象 包括5个问题，和页码信息
     */
    public PaginationDTO list(Integer page, Integer size) {

        //返回page对象
        PaginationDTO paginationDTO = new PaginationDTO();
        //总数
        Integer totolCount = (int)questionMapper.countByExample(new QuestionExample());
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
        /**
         *  拿到该页的问题list
         *  原来手写的sql语句
         *  List<Question> questions = questionMapper.list(offset,size);
         */
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));

        /**
         * 按照时间排序
         * 这是按照页面来排序，排序的结果只在一个页面内，对全局无效，好像没啥用
         */
//        Collections.sort(questions, new Comparator<Question>(){
//            public int compare(Question o1, Question o2) {
//                //排序属性
//                if(o1.getGmtCreate() < o2.getGmtCreate()){
//                    return -1;
//                }
//                if(o1.getGmtCreate() == o2.getGmtCreate()){
//                    return 0;
//                }
//                return 1;
//            }
//        });

        //questionDTO 使 question 与 user 关联
        for(Question question : questions){
            User user =  userMapper.selectByPrimaryKey(question.getCreator());
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
     * 我的问题列表，根据用户id -> question的creator 查找QuestionList
     * 查询用户发起的问题
     */
    public  PaginationDTO list(Long userId,Integer page,Integer size){
        //返回page对象
        PaginationDTO paginationDTO = new PaginationDTO();
        //总数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totolCount = (int)questionMapper.countByExample(questionExample);

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
        /**
         *  List<Question> questions = questionMapper.listByUserId(userId,offset,size);
         */
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        for(Question question : questions){
            User user =  userMapper.selectByPrimaryKey(question.getCreator());
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
     * 根据question id 查看问题
     * 获取问题详细界面
     * @param id
     * @return
     */
    public QuestionDTO getById(Long id){
        Question question = questionMapper.selectByPrimaryKey(id);
        /**
         *
         * 处理异常
         * 问题没找到  @ControllerAdvice 进行处理
         */
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        //                         source   target
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
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
            //默认值
            question.setLikeCount(0);
            question.setCommentCount(0);
            question.setViewCount(0);

            questionMapper.insertSelective(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());

            /**
             * 如果问题id未找到，处理异常  @ControllerAdvice进行处理
             */
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }



    /**
     * 访问question ，增加阅读数
     * @param id
     */
    public void incView(Long id) {

        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);

        questionExtMapper.incView(question);

    }
}

