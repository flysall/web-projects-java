package com.flysall.appoint.service.Impl;

import com.flysall.appoint.dao.AppointmentDao;
import com.flysall.appoint.dao.BookDao;
import com.flysall.appoint.dao.StudentDao;
import com.flysall.appoint.dto.AppointExecution;
import com.flysall.appoint.entity.Appointment;
import com.flysall.appoint.entity.Book;
import com.flysall.appoint.entity.Student;
import com.flysall.appoint.enums.AppointStateEnum;
import com.flysall.appoint.exception.AppointException;
import com.flysall.appoint.exception.NoNumberException;
import com.flysall.appoint.exception.RepeatAppointException;
import com.flysall.appoint.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookDao bookDao;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private StudentDao studentDao;

    public Book getById(long bookId){
        return bookDao.queryById(bookId);
    }

    public List<Book> getList(){
        return bookDao.queryAll(0, 1000);
    }

    public Student validateStu(Long studentId, Long password){
        return studentDao.quaryStudent(studentId, password);
    }

    public List<Book> getSomeList(String name){
        return bookDao.querySome(name);
    }

    public List<Appointment> getAppointByStu(long studentId){
        return appointmentDao.quaryAndReturn(studentId);
    }

    @Transactional
    public AppointExecution appoint(long bookId, long studentId){
        try{
            int update = bookDao.reduceNumber(bookId); //书籍库存减一
            if(update == 0){
                throw new NoNumberException("no number"); //已无库存
            } else{
                int insert = appointmentDao.insertAppointment(bookId, studentId);
                if(insert <= 0){
                    throw new RepeatAppointException("repeat appoint"); //重复预约
                } else{ //预约成功
                    return new AppointExecution(bookId, AppointStateEnum.SUCCESS);
                }
            }
        } catch (NoNumberException e1){
            throw e1;
        } catch (RepeatAppointException e2){
            throw e2;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            //将所有编译期间异常转换为运行期间异常
            throw new AppointException("appoint inner error: " + e.getMessage());
        }
    }
}
