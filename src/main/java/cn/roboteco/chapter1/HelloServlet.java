package cn.roboteco.chapter1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* 1. 继承HttpServlet，让他成为一个Servlet类
* 2. 覆盖父类的doGet方法，用于接收GET请求
* 3. 在doGet方法中获取当前系统时间，并将其放入HTTPServletRequest对象中，最后转发到/WEB-INF/jsp/hello.jsp中
* 4. 使用WebServlet注解并配置请求路径，对外发布Servlet服务
*
*/
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        req.setAttribute("currentTime", currentTime);
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
    }
}
