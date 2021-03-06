package com.flysall.appoint.web;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flysall.appoint.dto.AppointExecution;
import com.flysall.appoint.dto.Result;
import com.flysall.appoint.entity.Appointment;
import com.flysall.appoint.entity.Book;
import com.flysall.appoint.entity.Student;
import com.flysall.appoint.enums.AppointStateEnum;
import com.flysall.appoint.exception.NoNumberException;
import com.flysall.appoint.exception.RepeatAppointException;
import com.flysall.appoint.service.BookService;
import com.flysall.appoint.service.Impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/books")
public class BookController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookService bookService;

    /**
     * 获取图书列表
     * @param model
     * @return
     */
    @RequestMapping(value="/list", method = RequestMethod.GET)
    private String List(Model model){
        List<Book> list = bookService.getList();
        model.addAttribute("list", list);
        return "list";
    }

    /**
     * 搜索是否存在某本书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/search", method = RequestMethod.POST)
    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //从页面接收值
        String name=req.getParameter("name");
        name = name.trim();
        //向页面传值
        req.setAttribute("name", name);
        req.setAttribute("list", bookService.getSomeList(name));
        req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
    }

    /**
     * 查看某书的详细情况
     * @param bookId
     * @param model
     * @return
     */
    @RequestMapping(value="/{bookId}/detail", method = RequestMethod.GET)
    private String detail(@PathVariable("bookId") Long bookId, Model model){
        if(bookId == null){
            return "redirct:/book/list";
        }
        Book book = bookService.getById(bookId);
        if(book == null){
            return "redirect:/book/list";
        }
        model.addAttribute("book", book);
        System.out.println(book);
        return "detail";
    }

    /**
     * 验证输入的用户名，密码是否正确
     * 此处即位前后端json数据交互
     * @param studentId
     * @param password
     * @return
     */
    @RequestMapping(value="/verify", method = RequestMethod.POST, produces =
            {"application/json; charset=utf-8"})
    @ResponseBody
    private Map validate(Long studentId, Long password) {
        Map resultMap = new HashMap();
        Student student = null;
        System.out.println("验证函数");
        student = bookService.validateStu(studentId, password);
        System.out.println("输入的学号、密码: " + studentId + ":" + password);
        System.out.println("查询到的: " + student.getStudentId() + ":" + student.getPasswork());

        if (student != null) {
            System.out.println("SUCCESS");
            resultMap.put("result", "SUCCESS");
            return resultMap;
        } else {
            resultMap.put("result", "FAILED");
            return resultMap;
        }
    }

    /**
     * 此处用ajax将result对象传递到前端,交由js代码处理
     * @param bookId
     * @param studentId
     * @return
     */
    @RequestMapping(value="{bookId}/appoint", method=RequestMethod.POST, produces={
            "application/json; charset=utf-8"})
    @ResponseBody
    private Result<AppointExecution> execute(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId){
        Result<AppointExecution> result;
        AppointExecution execution = null;
        try{
            execution = bookService.appoint(bookId, studentId);
            result = new Result<AppointExecution>(true, execution);
            return result;
        } catch(NoNumberException e1){
            execution = new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
            result = new Result<AppointExecution>(true, execution);
            return result;
        } catch(RepeatAppointException e2){
            execution = new AppointExecution(bookId, AppointStateEnum.REPEAT_APPOINT);
            result = new Result<AppointExecution>(true, execution);
            return result;
        } catch(Exception e){
            execution = new AppointExecution(bookId, AppointStateEnum.INNER_ERROR);
            result = new Result<AppointExecution>(true, execution);
            return result;
        }
    }
    @RequestMapping(value="/appoint")
    private String appointBooks(@RequestParam("studentId") long studentId, Model model){
        List<Appointment> appointList = new ArrayList<Appointment>();
        appointList = bookService.getAppointByStu(studentId);
        model.addAttribute("appointList", appointList);
        return "appointBookList";
    }
}
