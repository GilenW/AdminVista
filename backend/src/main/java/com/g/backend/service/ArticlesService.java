package com.g.backend.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.g.backend.entity.Account;
import com.g.backend.entity.Articles;
import com.g.backend.exception.CustomException;
import com.g.backend.mapper.ArticlesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlesService {

    @Resource
    private ArticlesMapper articlesMapper;


    public void add(Articles articles) {
        articles.setTime(DateUtil.now());
        articlesMapper.insert(articles);
    }

    public void update(Articles articles) {
        articlesMapper.update(articles);
    }

    public void deleteById(Integer id) {
        articlesMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            articlesMapper.deleteById(id);
        }
    }

    public List<Articles> selectAllArticles(Articles articles) {
        return articlesMapper.selectAllArticles(articles);
    }

    public Articles selectByArticlesId(Integer id) {
        return articlesMapper.selectByArticlesId(id);

    }

    public PageInfo<Articles> selectPageArticles(Articles articlesObj,
                                                 Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Articles> articlesList =
                articlesMapper.selectAllArticles(articlesObj);
        return PageInfo.of(articlesList);
    }



}
