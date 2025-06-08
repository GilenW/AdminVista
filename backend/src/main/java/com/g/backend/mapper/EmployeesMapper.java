package com.g.backend.mapper;

import com.g.backend.entity.Employees;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeesMapper {
    List<Employees> selectAllEmployees();

    @Select("select * from employees where id = #{id}")
    Employees selectByEmployeeId(Integer id);
}
