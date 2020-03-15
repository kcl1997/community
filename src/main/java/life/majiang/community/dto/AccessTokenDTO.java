package life.majiang.community.dto;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   AccessTokenDTO
 * 创建者
 * 创建时间: 2020/3/15 5:32 PM
 * 描述  ${TODO}
 */
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String rediret_uri;
    private String state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRediret_uri() {
        return rediret_uri;
    }

    public void setRediret_uri(String rediret_uri) {
        this.rediret_uri = rediret_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}

