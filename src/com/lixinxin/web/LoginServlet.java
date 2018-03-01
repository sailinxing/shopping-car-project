package com.lixinxin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.lixinxin.Utils.C3P0Utils;
import com.lixinxin.domain.User;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// 初始化，存入登录计数
	private int loginCount = 0;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		context.setAttribute("Count", loginCount);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 验证码判断
		HttpSession session = request.getSession();
		String word = (String) session.getAttribute("words");
		String checkcode = request.getParameter("checkcode");
		if (checkcode != null & !"".equals(checkcode)) {
			// 用户名、密码判断
			if (checkcode.equals(word)) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
				String sql = "select * from users where username=? and password=?";
				User user = null;
				try {
					user = qr.query(sql, new BeanHandler<User>(User.class), username, password);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (user != null) {
					// 记录登录的人数
					loginCount = (int) this.getServletContext().getAttribute("Count");
					loginCount++;
					// 记录上次登录时间
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = new Date();
					String time = sdf.format(date);
					Cookie cookie = new Cookie("time", time);
					response.addCookie(cookie);
					Cookie[] cookies = request.getCookies();
					response.getWriter().write("<html>");
					response.getWriter().write("<body>");
					response.getWriter().write(user.getUsername() + "欢迎您登录！您是第" + loginCount + "位访客;");
					this.getServletContext().setAttribute("Count", loginCount);
					Cookie co = null;
					if (cookies != null) {
						for (Cookie c : cookies) {
							if (c.getName().equals("time")) {
								co = c;
							}
						}
						if (co != null) {
							time = co.getValue();
							response.getWriter().write("您上次登录的时间是：" + time);
						} else {
							response.getWriter().write("您是第一次登录");
						}
					}
					response.getWriter().write("<br/><a href='/day14_shopping/index.html'>进入空间</a><br/>");
					response.getWriter().write("</html>");
					response.getWriter().write("</body>");
				} else {
					// 登录错误回显
					request.setAttribute("message", "用户名或者密码错误！");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				// 验证码错误回显
				request.setAttribute("message", "验证码输入错误！");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			// 未输验证码错误回显
			request.setAttribute("message", "请输入验证码！");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}