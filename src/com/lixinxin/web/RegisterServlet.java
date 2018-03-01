package com.lixinxin.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.lixinxin.Utils.C3P0Utils;
import com.lixinxin.domain.User;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		String word = (String) session.getAttribute("words");
		String checkcode = request.getParameter("checkcode");
		if (checkcode != null & !"".equals(checkcode)) {
			if (checkcode.equals(word)) {
				Map<String, String[]> map = request.getParameterMap();
				User user = new User();
				try {
					BeanUtils.populate(user, map);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
				String sql = "insert into users(username,password,name,email,birthday,sex) values(?,?,?,?,?,?)";
				try {
					int rows = qr.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
							user.getBirthday(), user.getSex());
					if (rows > 0) {
						response.sendRedirect("/day14_shopping/skip.html");
						System.out.println("注册成功！");
					} else {
						System.out.println("注册失败！");
						response.sendRedirect("/day14_shopping/register.html");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				response.getWriter().write("<html>");
				response.getWriter().write("<body>");
				response.getWriter().write("验证码错误！<br/><a href='/day14_shopping/register.html'>重新注册</a><br/>");
				response.getWriter().write("</html>");
				response.getWriter().write("</body>");
			}
		} else {
			response.getWriter().write("<html>");
			response.getWriter().write("<body>");
			response.getWriter().write("请输入验证码！<br/><a href='/day14_shopping/register.html'>重新注册</a><br/>");
			response.getWriter().write("</html>");
			response.getWriter().write("</body>");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}