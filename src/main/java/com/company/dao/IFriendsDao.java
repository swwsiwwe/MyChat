package com.company.dao;

import com.company.domain.Friends;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IFriendsDao {
    @Insert("insert into friends(username,friend) values(#{username},#{friend})")
    void insert(Friends friends);

    @Select("select * from friends where username=#{username}")
    List<Friends> select(@Param("username") String username);

    @Select("select * from friends where username=#{username} and friend=#{friend}")
    Friends selectF(@Param("username") String username,@Param("friend")String friend);

    @Delete("delete from friends where username=#{username} and friend=#{friend}")
    void delete(@Param("username") String username,@Param("friend") String friend);
}
