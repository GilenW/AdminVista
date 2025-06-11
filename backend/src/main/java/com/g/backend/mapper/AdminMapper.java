package com.g.backend.mapper;

import com.g.backend.entity.Admin;
import com.g.backend.entity.Employees;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {
    List<Admin> selectAllAdmin(Admin admin);

    @Select("select * from admin where id = #{id}")
    Admin selectByAdminId(Integer id);

    void insert(Admin admin);

    void update(Admin admin);

    @Delete("delete from admin WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("select * from admin where username=#{username}")
    Admin selectByUsername(String username);
}
