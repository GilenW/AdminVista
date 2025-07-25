package com.g.backend.controller;

import com.g.backend.common.Result;
import com.g.backend.entity.Account;
import com.g.backend.entity.Admin;
import com.g.backend.entity.Employees;
import com.g.backend.exception.CustomException;
import com.g.backend.service.AdminService;
import com.g.backend.service.EmployeesService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PutMapping("/updatePassword")
    public Result changePassword(@RequestBody Account account) {
        String role = account.getRole();
        Integer id = account.getId();
        Account currentAccount = null;
        if("ADMIN".equals(role)) {
            currentAccount = adminService.selectByAdminId(id);
        }else if("EMP".equals(role)) {
            currentAccount = employeesService.selectByEmployeeId(id);
        }
        if(currentAccount.getPassword().equals(account.getPassword())) {
            currentAccount.setPassword(account.getNewPassword());
            if ("ADMIN".equals(role)) {
                adminService.update((Admin) currentAccount);
            } else if ("EMP".equals(role)) {
                employeesService.update((Employees) currentAccount);
            }
        }else{
            throw new CustomException("500","Password do not match the odd " +
                    "password");
        }
        return Result.success();

    }


    @GetMapping("/pieChart")
    public Result pieChart() {
        Map<String, Integer> map = new HashMap<>();
        Employees employees = new Employees();
        employees.setName("");
        List<Employees> employeesList =
                employeesService.selectAllEmployees(employees);
        Set<String> positionList =
                employeesList.stream().map(Employees::getPosition).collect(Collectors.toSet());
        for(String position : positionList) {
            long count = employeesList.stream().filter(employee -> position.equals(employee.getPosition())).count();
            map.put(position, (int) count);
        }
        return Result.success(map);
    }
}

