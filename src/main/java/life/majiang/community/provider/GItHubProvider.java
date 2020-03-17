package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;

import java.io.IOException;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名： community
 * 包名:    life.majiang.community.provider
 * 文件名   GItHubProvider
 * 创建者
 * 创建时间: 2020/3/15 5:29 PM
 * 描述  ${TODO}
 */

@Component
public class GItHubProvider {

    /**
     * 根据获取的code码
     * 获取用户的 accessToken字符串
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try(Response response = client.newCall(request).execute()){
            String result = response.body().string();
            //获取到 access_token=9b6349c00082438d7819610a4e51f1d7a0bc3be3&scope=&token_type=bearer
            System.out.println(result);
            String access_token = result.split("&")[0].split("=")[1];
            System.out.println(access_token);
            return access_token;
        }catch (Exception e){

        }
        return null;
    }

    /**
     * 第三步，获取到GitHub服务器发回来的accessToken后，获取用户的信息
     * 根据用户accessToken 获取用户的信息
     * @param accessToken
     * @return
     */
    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            //System.out.println(str);
            //string json对象 -> java类对象
            GitHubUser githubUser = JSON.parseObject(str, GitHubUser.class);
            return githubUser;

        }catch (IOException e){

        }
        return null;

    }

}

