package com.g.backend.service;

import com.g.backend.entity.Employees;
import com.g.backend.mapper.EmployeesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService {

    @Resource
    private EmployeesMapper employeesMapper;

    public List<Employees> selectAllEmployees() {

        return employeesMapper.selectAllEmployees();

    }

    public Employees selectByEmployeeId(Integer id) {
        return employeesMapper.selectByEmployeeId(id);
    }

    public PageInfo<Employees> selectPageEmployees(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employees> employeesList = employeesMapper.selectAllEmployees();
        return PageInfo.of(employeesList);
    }
}
