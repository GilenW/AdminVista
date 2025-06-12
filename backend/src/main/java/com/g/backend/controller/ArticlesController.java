package com.g.backend.controller;


import com.g.backend.common.Result;
import com.g.backend.entity.Articles;
import com.g.backend.service.ArticlesService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@Slf4j
public class ArticlesController {

    @Resource
    private ArticlesService articlesService;


    @PostMapping("/add")
    public Result add(@RequestBody Articles articles) {
        articlesService.add(articles);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Articles articles) {
        articlesService.update(articles);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        articlesService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        articlesService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectAllArticles")
    public Result selectAll(Articles articles) {
        List<Articles> articlesList =
                articlesService.selectAllArticles(articles);
        return Result.success(articlesList);
    }

    @GetMapping("/selectByArticlesId/{id}")
    public Result selectById(@PathVariable Integer id) {
        Articles articles = articlesService.selectByArticlesId(id);
        return Result.success(articles);
    }

    @GetMapping("/selectPageArticles")
    public Result selectPageArticles(@RequestParam(required = false) String searchName,
                                  @RequestParam(defaultValue =
                                          "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize) {
        Articles articlesObj = new Articles();
        articlesObj.setTitle(searchName);
        PageInfo<Articles> articlesList =
                articlesService.selectPageArticles(articlesObj, pageNum,
                        pageSize);
        return Result.success(articlesList);
    }
}
