package com.flysall.appoint.dao;

import com.flysall.appoint.entity.Appointment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AppointmentDao {
    int insertAppointment(@Param("bookId") long bookId, @Param("studentId") long studentId);

    /**
     * 通过学生ID查询已经预约的书籍
     * @param studentId
     * @return
     */
    List<Appointment> quaryAndReturn(long studentId);
}
