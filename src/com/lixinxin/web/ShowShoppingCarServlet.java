package com.lixinxin.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.lixinxin.Utils.C3P0Utils;
import com.lixinxin.domain.Product;

public class ShowShoppingCarServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		Cookie[] cookies = request.getCookies();
		String car = "";
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("car")) {
					car = c.getValue();
				}
			}
		} else {
			response.getWriter().write("您还未在购物车加入任何东西！");
		}
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String[] pids = car.split("-");
		response.getWriter().write("<html>");
		response.getWriter().write("<body>");
		String sql = "select * from product where pid=?";
		for (String pid : pids) {
			try {
				Product product = qr.query(sql, new BeanHandler<Product>(Product.class),pid);
				response.getWriter().write("产品名：" + product.getPname() + "    价格:" + product.getPrice()+"<br/>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		response.getWriter().write("<a href='/day14_shopping'>返回</a><br/>");
		response.getWriter().write("</html>");
		response.getWriter().write("</body>");		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}