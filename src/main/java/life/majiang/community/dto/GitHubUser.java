package life.majiang.community.dto;

/**
 * 项目名： community
 * 包名:    life.majiang.community.dto
 * 文件名   GItHubUser
 * 创建者
 * 创建时间: 2020/3/15 7:32 PM
 * 描述  ${TODO}
 */
public class GitHubUser {
    private String login;
    private String name;
    private Long id;
    //描述信息
    private String bio;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}

