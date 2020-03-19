package life.majiang.community.dto;

import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   GItHubUser
 * 创建者
 * 创建时间: 2020/3/15 7:32 PM
 * 描述  ${TODO}
 */

@Data
public class GitHubUser {
    private String login;
    private String name;
    private Long id;
    //描述信息
    private String bio;
    private String avatarUrl;


}

