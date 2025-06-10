package com.g.backend.service;

import com.g.backend.entity.Employees;
import com.g.backend.mapper.EmployeesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class EmployeesService {

    @Resource
    private EmployeesMapper employeesMapper;



    public List<Employees> selectAllEmployees(Employees employees) {

        return employeesMapper.selectAllEmployees(employees);

    }

    public Employees selectByEmployeeId(Integer id) {
        return employeesMapper.selectByEmployeeId(id);
    }

    public PageInfo<Employees> selectPageEmployees(Employees employees,
                                                   Integer pageNum,
                                                   Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employees> employeesList =
                employeesMapper.selectAllEmployees(employees);
        return PageInfo.of(employeesList);
    }

    public void add(Employees employees) {
        employeesMapper.insert(employees);
    }

    public void update(Employees employees) {
        employeesMapper.update(employees);
    }

    public void deleteById(Integer id) {
        employeesMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            employeesMapper.deleteById(id);
        }
    }
}
