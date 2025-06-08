package com.g.backend.mapper;

import com.g.backend.entity.Employees;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeesMapper {
    List<Employees> selectAllEmployees();

    @Select("select * from employees where id = #{id}")
    Employees selectByEmployeeId(Integer id);

    void insert(Employees employees);

    void update(Employees employees);

    @Delete("delete from employees WHERE id = #{id}")
    void deleteById(Integer id);
}
