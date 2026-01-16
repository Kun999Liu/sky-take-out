package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author {liukun}
 * @e-mail:liukunjsj@163.com
 * @Date: 2026/01/12/ 21:29
 * @description
 */
@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User getByOpenId(String openid);

    /**
     * 插入用户数据
     * @param user
     */
    void insert(User user);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getById(Long userId);
}
