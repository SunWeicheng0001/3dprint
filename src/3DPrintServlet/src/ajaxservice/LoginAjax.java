package ajaxservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.GetLoginUserService;
import util.Util;
import database.UserService;
import entity.User;

/**
 * �û���¼����֤�û����û��������롣���Cookie��¼�û��ĵ�¼״̬�����ص�¼������������
 */
public class LoginAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException{
		
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		if(Util.isEmpty(userName) || Util.isEmpty(password)){
			return;
		}
		userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
		password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
		
		User user = GetLoginUserService.service(req);
		// �Ѿ���¼������Ҫ����֤
		if(user != null){
			resp.getWriter().print(wrapJSON(false, "unknown"));
			return;
		}
		
		user = UserService.getUserService().searchUserByName(userName);
		
		// ��֤��ͨ��
		if(user == null || !password.equals(user.getPassword())){
			resp.getWriter().print(wrapJSON(false, "unknown"));
			return;
		}
		
		// ��֤ͨ�������Cookie
		Cookie nameCookie = new Cookie("userName", user.getName());
		Cookie idCookie = new Cookie("userID", "" + user.getId());
		nameCookie.setPath("/");
		idCookie.setPath("/");
		resp.addCookie(nameCookie);
		resp.addCookie(idCookie);
		
		resp.getWriter().print(wrapJSON(true, user.getAuthority()));
	}
	
	public static boolean isLogin(HttpServletRequest req, String userName){
		Cookie[] cookies = req.getCookies();
		if(cookies == null){
			return false;
		}
		for(int i=0; i<cookies.length; i++){
			Cookie cookie = cookies[i];
			if(cookie.getName().equals("userName") && cookie.getValue().equals(userName)){
				return true;
			}
		}
		return false;
	}
	
	public static String getUserID(HttpServletRequest req){
		Cookie[] cookies = req.getCookies();
		if(cookies == null){
			return null;
		}
		for(int i=0; i<cookies.length; i++){
			Cookie cookie = cookies[i];
			if(cookie.getName().equals("userID")){
				return cookie.getValue();
			}
		}
		return null;
	}
	
	private String wrapJSON(boolean success, String authority){
		return "{" + Util.quotation("success") + ":" + success
				+ "," + Util.quotation("authority") + ":" + Util.quotation(authority) + "}";
	}
}