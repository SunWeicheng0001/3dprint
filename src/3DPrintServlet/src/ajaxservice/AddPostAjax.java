package ajaxservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.PostService;
import database.ShoppingService;
import service.GetLoginUserService;
import entity.Material;
import entity.Shopcar;
import entity.User;
import util.Util;

/**
 * �û��������ӡ�
 */
public class AddPostAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException{
		
		// ����û��Ƿ��Ѿ���¼
		User user = GetLoginUserService.service(req);
		if(user == null){
			return;
		}

		String first = req.getParameter("first");
		String second = req.getParameter("second");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		if(Util.isEmpty(first) || Util.isEmpty(second) || Util.isEmpty(title) || Util.isEmpty(content)){
			return;
		}
		
		Boolean success = PostService.getPostService().addPost(user.getId(), first, second, title, content);
		resp.getWriter().print(wrapJSON(success));
	}
	
	private String wrapJSON(Boolean success){
		return "{" + Util.quotation("success") + ":" + success + "}";
	}

}
