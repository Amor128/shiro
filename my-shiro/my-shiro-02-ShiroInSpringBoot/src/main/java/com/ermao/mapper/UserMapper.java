package com.ermao.mapper;

import com.ermao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ermao
 * Date: 2021/9/20 11:20
 */
@Mapper
@Repository
public interface UserMapper {
	User selectUserByUsername(@Param("username") String username);
}
