package com.g.backend.controller;

import com.g.backend.common.Result;
import com.g.backend.entity.Employees;
import com.g.backend.service.EmployeesService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Slf4j
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

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        log.info("ids : {}", ids);
        employeesService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectAllEmployees")
    public Result selectAll(Employees employees) {
        List<Employees> employeesList =
                employeesService.selectAllEmployees(employees);
        return Result.success(employeesList);
    }

    @GetMapping("/selectByEmployeeId/{id}")
    public Result selectById(@PathVariable Integer id) {
        Employees employees = employeesService.selectByEmployeeId(id);
        return Result.success(employees);
    }

    @GetMapping("/selectPageEmployees")
    public Result selectPageEmployees(@RequestParam(required = false) String searchName,
                                      @RequestParam(defaultValue =
            "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize) {
        Employees employees = new Employees();
        employees.setName(searchName);
        PageInfo<Employees> employList =
                employeesService.selectPageEmployees(employees, pageNum,
                        pageSize);
        return Result.success(employList);
    }
}
