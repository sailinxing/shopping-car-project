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
	// ��ʼ���������¼����
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
		// ��֤���ж�
		HttpSession session = request.getSession();
		String word = (String) session.getAttribute("words");
		String checkcode = request.getParameter("checkcode");
		if (checkcode != null & !"".equals(checkcode)) {
			// �û����������ж�
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
					// ��¼��¼������
					loginCount = (int) this.getServletContext().getAttribute("Count");
					loginCount++;
					// ��¼�ϴε�¼ʱ��
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = new Date();
					String time = sdf.format(date);
					Cookie cookie = new Cookie("time", time);
					response.addCookie(cookie);
					Cookie[] cookies = request.getCookies();
					response.getWriter().write("<html>");
					response.getWriter().write("<body>");
					response.getWriter().write(user.getUsername() + "��ӭ����¼�����ǵ�" + loginCount + "λ�ÿ�;");
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
							response.getWriter().write("���ϴε�¼��ʱ���ǣ�" + time);
						} else {
							response.getWriter().write("���ǵ�һ�ε�¼");
						}
					}
					response.getWriter().write("<br/><a href='/day14_shopping/index.html'>����ռ�</a><br/>");
					response.getWriter().write("</html>");
					response.getWriter().write("</body>");
				} else {
					// ��¼�������
					request.setAttribute("message", "�û��������������");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				// ��֤��������
				request.setAttribute("message", "��֤���������");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			// δ����֤��������
			request.setAttribute("message", "��������֤�룡");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}