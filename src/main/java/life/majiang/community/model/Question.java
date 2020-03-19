package life.majiang.community.model;

import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.model
 * 文件名   Question
 * 创建者
 * 创建时间: 2020/3/18 3:11 PM
 * 描述  ${TODO}
 */
@Data
public class Question {
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


}

