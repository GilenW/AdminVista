package com.g.backend.service;


import cn.hutool.core.util.StrUtil;
import com.g.backend.entity.Account;
import com.g.backend.entity.Admin;
import com.g.backend.entity.Employees;
import com.g.backend.exception.CustomException;
import com.g.backend.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;


    public void add(Admin admin) {
        String username = admin.getUsername();
        Admin dbAdmin = adminMapper.selectByUsername(username);
        if (dbAdmin != null) {
            throw new CustomException("500", "Username is already taken");
        }
        if(StrUtil.isBlank(admin.getPassword())) {
            admin.setPassword("admin");
        }
        if(StrUtil.isBlank(admin.getName())) {
            admin.setName(username);
        }
        admin.setRole("ADMIN");
        adminMapper.insert(admin);
    }

    public void update(Admin admin) {
        adminMapper.update(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteById(id);
        }
    }

    public List<Admin> selectAllAdmin(Admin admin) {
        return adminMapper.selectAllAdmin(admin);
    }

    public Admin selectByAdminId(Integer id) {
        return adminMapper.selectByAdminId(id);

    }

    public PageInfo<Admin> selectPageAdmin(Admin adminObj, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> adminList =
                adminMapper.selectAllAdmin(adminObj);
        return PageInfo.of(adminList);
    }


    public Admin login(Account admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        Admin dbAdmin = adminMapper.selectByUsername(username);
        if (dbAdmin == null) {
            throw new CustomException("500", "No user found");
        }
        if (!password.equals(dbAdmin.getPassword())) {
            throw new CustomException("500", "Wrong password");
        }
        return dbAdmin;
    }


}
