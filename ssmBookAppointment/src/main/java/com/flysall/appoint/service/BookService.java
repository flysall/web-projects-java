package com.flysall.appoint.service;

import com.flysall.appoint.dto.AppointExecution;
import com.flysall.appoint.entity.Appointment;
import com.flysall.appoint.entity.Book;
import com.flysall.appoint.entity.Student;

import java.util.List;

public interface BookService {
    /**
     * 查询一本书
     */
    Book getById(long bookId);

    /**
     * 查询所有书
     */
    List<Book> getList();

    /**
     * 登陆时验证数据库中是否存在该学生
     * @param studentId
     * @param password
     * @return
     */
    Student validateStu(Long studentId, Long password);

    /**
     * 按书名查询书籍
     * @param name
     * @return
     */
    List<Book> getSomeList(String name);

    /**
     * 查询某个学生预约信息
     * @param studentId
     * @return
     */
    List<Appointment> getAppointByStu(long studentId);

    /**
     * 预约图书
     * @param bookId
     * @param studentId
     * @return
     */
    AppointExecution appoint(long bookId, long studentId);
}
