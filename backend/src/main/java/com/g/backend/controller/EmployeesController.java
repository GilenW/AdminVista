package com.g.backend.controller;

import com.g.backend.common.Result;
import com.g.backend.entity.Employees;
import com.g.backend.service.EmployeesService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Resource
    private EmployeesService employeesService;

    @PostMapping("/add")
    public Result add(@RequestBody Employees employees) {
        employeesService.add(employees);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Employees employees) {
        employeesService.update(employees);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        employeesService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectAllEmployees")
    public Result selectAll() {
        List<Employees> employeesList =  employeesService.selectAllEmployees();
        return Result.success(employeesList);
    }

    @GetMapping("/selectByEmployeeId/{id}")
    public Result selectById(@PathVariable Integer id) {
        Employees employees = employeesService.selectByEmployeeId(id);
        return Result.success(employees);
    }

    @GetMapping("/selectPageEmployees")
    public Result selectPageEmployees(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize) {
        PageInfo<Employees> employees =
                employeesService.selectPageEmployees(pageNum, pageSize);
        return Result.success(employees);
    }
}
