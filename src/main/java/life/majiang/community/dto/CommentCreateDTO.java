package life.majiang.community.dto;

import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   CommentCreateDTO
 * 创建者
 * 创建时间: 2020/3/30 8:54 PM
 * 描述  回复类
 */

/**
 * json post 请求得到的数据
 */

@Data
public class CommentCreateDTO {
    /**
     * question 的 id
     */
    private Long parentId;
    private String content;

    /**
     * 父类类型
     *  type == 1 一级回复
     *  type == 2 二级回复
     */
    private Integer type;
}

