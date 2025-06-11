package com.g.backend.controller;

import com.g.backend.common.Result;
import com.g.backend.entity.Account;
import com.g.backend.entity.Employees;
import com.g.backend.service.AdminService;
import com.g.backend.service.EmployeesService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Resource
    private EmployeesService employeesService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account result = null;
        if("ADMIN".equals(account.getRole())) {
            result = adminService.login(account);
        }else if("EMP".equals(account.getRole())) {
            result = employeesService.login(account);
        }

        return Result.success(result);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Employees employees) {
        employeesService.register(employees);
        return Result.success();
    }



}

