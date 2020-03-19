package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   QuestionDTO
 * 创建者
 * 创建时间: 2020/3/18 11:34 PM
 * 描述  基本属性和Question一样
 *        比question多了一个成员user，
 *        因为question保存的是user 的id， 想要获取avatar_url 必须要关联
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}

