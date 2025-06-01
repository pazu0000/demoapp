package com.example.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoRepository {
    @Insert("insert into demos (title, content, period, importance) values (#{title}, #{content}, #{period}, #{importance})")
    void insert(String title, String content, String period, Character importance);
}
