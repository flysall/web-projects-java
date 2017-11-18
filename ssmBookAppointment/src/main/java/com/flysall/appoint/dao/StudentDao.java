package com.flysall.appoint.dao;

import com.flysall.appoint.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentDao {
    /**
     * 向数据库验证输入密码是否正确
     */

    Student quaryStudent(@Param("studentId") long studentId, @Param("password") long password);
}
