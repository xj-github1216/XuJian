package com.igeek.login.servelt;

import com.igeek.login.entity.User;
import com.igeek.login.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "UserServlet" ,urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求中的编码集
        request.setCharacterEncoding("UTF-8");

        //获得请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String timeLength = request.getParameter("timeLength");

        UserService service = new UserService();

        if (username != null && password!=null){
            User user = service.login(username,password);

            if (user!=null){
                String state = user.getState();
                switch (state){
                    case "0":
                        request.setAttribute("msg","当前账号未审核，请耐心等待");
                        request.getRequestDispatcher("userLogin.jsp").forward(request,response);
                        break;
                    case "1":

                        //简化登录，通过Cookie存储姓名和密码
                        if (timeLength!=null && !timeLength.equals("0")){
                            int day = Integer.parseInt(timeLength);

                            //1.创建Cookie
                            //编码
                            Cookie usernameCookie = new Cookie("usernameCookie", URLEncoder.encode(username,"UTF-8"));
                            Cookie passwordCookie = new Cookie("passwordCookie",URLEncoder.encode(password,"UTF-8"));

                            //设置有效时长，存储在浏览器所在得PC机上
                            usernameCookie.setMaxAge(day*24*60*60);
                            passwordCookie.setMaxAge(day*24*60*60);

                            //响应至客户端
                            response.addCookie(usernameCookie);
                            response.addCookie(passwordCookie);

                        }

                        request.getRequestDispatcher("success.jsp").forward(request,response);

                        break;
                    case "2":
                        request.setAttribute("msg","当前账户审核失败，请重新提交信息");
                        request.getRequestDispatcher("userLogin.jsp").forward(request,response);
                        break;
                }
            }else {
                request.setAttribute("msg","姓名和密码不匹配，登陆失败");
                request.getRequestDispatcher("userLogin.jsp").forward(request,response);
            }
        }else {
            //请求属性
            request.setAttribute("msg","姓名或者密码有误，登录失败");
            request.getRequestDispatcher("fail.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
