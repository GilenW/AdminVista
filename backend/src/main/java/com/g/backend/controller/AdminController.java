package com.g.backend.controller;

import com.g.backend.common.Result;
import com.g.backend.entity.Admin;
import com.g.backend.entity.Employees;
import com.g.backend.service.AdminService;
import com.g.backend.service.EmployeesService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    @Resource
    private AdminService adminService;


    @PostMapping("/add")
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        adminService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectAllAdmin")
    public Result selectAll(Admin admin) {
        List<Admin> adminList =
                adminService.selectAllAdmin(admin);
        return Result.success(adminList);
    }

    @GetMapping("/selectByAdminId/{id}")
    public Result selectById(@PathVariable Integer id) {
        Admin admin = adminService.selectByAdminId(id);
        return Result.success(admin);
    }

    @GetMapping("/selectPageAdmin")
    public Result selectPageAdmin(@RequestParam(required = false) String searchName,
                                      @RequestParam(defaultValue =
                                              "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize) {
        Admin adminObj = new Admin();
        adminObj.setName(searchName);
        PageInfo<Admin> adminList =
                adminService.selectPageAdmin(adminObj, pageNum,
                        pageSize);
        return Result.success(adminList);
    }
}
