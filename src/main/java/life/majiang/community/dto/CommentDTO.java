package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   CommentDTO
 * 创建者
 * 创建时间: 2020/4/13 9:27 PM
 * 描述  ${TODO}
 */

@Data
public class CommentDTO {

    private Long id;
    private Long parentId;

    /**
     * 父类类型
     *  type == 1 一级回复  parentId 对应的是 question id
     *  type == 2 二级回复  parentId 对应的是  comment  id
     */
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;

    private User user;

}

