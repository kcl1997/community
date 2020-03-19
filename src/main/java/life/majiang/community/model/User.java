package life.majiang.community.model;

import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.model
 * 文件名   User
 * 创建者
 * 创建时间: 2020/3/16 12:40 AM
 * 描述  ${TODO}
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}

