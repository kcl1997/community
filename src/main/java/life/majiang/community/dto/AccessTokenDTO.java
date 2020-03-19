package life.majiang.community.dto;

import lombok.Data;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   AccessTokenDTO
 * 创建者
 * 创建时间: 2020/3/15 5:32 PM
 * 描述  ${TODO}
 */

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String rediret_uri;
    private String state;


}

