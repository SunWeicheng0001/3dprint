package ajaxservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.PostService;
import database.UserService;
import entity.User;
import service.GetLoginUserService;
import util.Util;

/**
 * �û�֧�֡��������ӡ�
 */
public class AddPostsupportAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException{

		// ����û��Ƿ��Ѿ���¼
		User user = GetLoginUserService.service(req);
		if(user == null){
			return;
		}
				
		String postID = req.getParameter("postID");
		String support = req.getParameter("support");
		
		System.out.println(postID);
		System.out.println(support);
		
		if(Util.isEmpty(postID) || Util.isEmpty(support)){
			return;
		}
		
		Boolean success = PostService.getPostService().addPostsupport(user.getId(), Integer.parseInt(postID), Boolean.parseBoolean(support));
		
		resp.getWriter().print(wrapJSON(success != null && success));
	}
	
	private String wrapJSON(boolean success){
		return "{" + Util.quotation("success") + ":" + success + "}";
	}

}
