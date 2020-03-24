package life.majiang.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;

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
        //User dbUser = userMapper.findByAccountId(user.getAccountId());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);

        //数据库没有改用户
        if(dbUsers.size() == 0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            //数据库中的数据(稍微有一点变化)
            User dbUser = dbUsers.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);

        }


    }
}

