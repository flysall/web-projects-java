package com.flysall.appoint.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flysall.appoint.entity.Book;

public interface BookDao {
    /**
     * 根据id查询图书
     */
    Book queryById(long id);

    List<Book> querySome(String name);

    List<Book> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    int reduceNumber(long bookId);
}
