package ajaxservice;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;

/**
 * �û�ע����ɾ���û���Cookie�����ؽ�����������
 * @author delin
 *
 */
public class LogoutAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException{
		
		// ����֮ǰ��Cookie
		Cookie nameCookie = new Cookie("userName", null);
		Cookie idCookie = new Cookie("userID", null);
		nameCookie.setPath("/");
		idCookie.setPath("/");
		nameCookie.setMaxAge(0);
		idCookie.setMaxAge(0);
		resp.addCookie(nameCookie);
		resp.addCookie(idCookie);
		
		resp.getWriter().print(wrapJSON(true));
	}
	
	private String wrapJSON(boolean success){
		return "{" + Util.quotation("success") + ":" + success + "}";
	}
}