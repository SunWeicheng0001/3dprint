package ajaxservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ShoppingService;
import service.GetLoginUserService;
import entity.User;
import util.Util;

/**
 * �ı䶩��״̬������Ա����
 */
public class ChangeOrdersStatusAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException{
		
		// ����û��Ƿ��Ѿ���¼
		User user = GetLoginUserService.service(req);
		if(user == null || !"manager".equals(user.getAuthority())){
			System.out.println("not mamager");
			return;
		}

		String status = req.getParameter("status");
		String orderIDs = req.getParameter("orderIDs");
		String prices = req.getParameter("prices");
		if(Util.isEmpty(status) || Util.isEmpty(orderIDs)){
			System.out.println("status or orderIDs are empty.");
			return;
		}
		
		status = new String(status.getBytes("ISO-8859-1"), "UTF-8");

		String[] orderIDStrs = orderIDs.split(";");
		int count = 0;
		if(status.equals("checked")){
			String[] pricesStrs = prices.split(";");
			if(orderIDStrs.length != pricesStrs.length){
				System.out.println("the number of orderIDs and prices are not equal.");
				return;
			}
			for(int i=0; i<orderIDStrs.length; i++){
				int orderID = Integer.parseInt(orderIDStrs[i]);
				float price = Float.parseFloat(pricesStrs[i]);
				Boolean success = ShoppingService.getShoppingService().updateOrderPriceStatus(orderID, price, status);
				count += success?1:0;
			}
		}else{
			for(String orderIDStr : orderIDStrs){
				int orderID = Integer.parseInt(orderIDStr);
				Boolean success = ShoppingService.getShoppingService().updateOrderStatus(orderID, status);
				count += success?1:0;
			}
		}
		resp.getWriter().print(wrapJSON(count));
	}
	
	private String wrapJSON(Integer count){
		return "{" + Util.quotation("count") + ":" + count + "}";
	}
}
