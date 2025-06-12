package com.g.backend.mapper;

import com.g.backend.entity.Articles;
import com.g.backend.entity.Employees;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticlesMapper {
    List<Articles> selectAllArticles(Articles Articles);

    @Select("select * from Articles where id = #{id}")
    Articles selectByArticlesId(Integer id);

    void insert(Articles Articles);

    void update(Articles Articles);

    @Delete("delete from Articles WHERE id = #{id}")
    void deleteById(Integer id);

}
