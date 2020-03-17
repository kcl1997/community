package life.majiang.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import life.majiang.community.model.User;

/**
 * 项目名： community
 * 包名:    life.majiang.community.mapper
 * 文件名   UserMapper
 * 创建者
 * 创建时间: 2020/3/16 12:38 AM
 * 描述  ${TODO}
 */


@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) " +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
     void insert(User user);
}

