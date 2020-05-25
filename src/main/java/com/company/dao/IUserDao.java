package com.company.dao;

import com.company.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {
    @Select("select * from user where username=#{username}")
    User select(String username);

    @Insert("insert into user(username,password,ip) values(#{username},#{password},#{ip})")
    void insert(User user);

    @Delete("delete * from user where username = #{username}")
    void delete(String username);
}
