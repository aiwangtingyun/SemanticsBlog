package com.wang.mapper;

import com.wang.bean.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//@Mapper
public interface BlogMapper {

    List<Blog> findTop(Pageable pageable);

    Page<Blog> findByQuery(String query, Pageable pageable);

    Integer updateViews(Long id);

    List<String> findGroupYear();

    List<Blog> findByYear(String year);
}
