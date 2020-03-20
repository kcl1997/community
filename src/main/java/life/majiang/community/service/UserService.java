package life.majiang.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;

/**
 * 项目名： community
 * 包名:    life.majiang.community.service
 * 文件名   UserService
 * 创建者
 * 创建时间: 2020/3/20 11:33 PM
 * 描述  ${TODO}
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录账号时，判断数据库是否存在用户
     * 如果没有则插入操作，否则更新token
     * @param user
     */
    public void createOrUpdaete(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }


    }
}

