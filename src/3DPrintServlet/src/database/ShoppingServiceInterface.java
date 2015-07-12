package database;
import entity.Material;
import entity.Order;
import entity.Shopcar;

import java.util.List;
public interface ShoppingServiceInterface {

	// ���롢�Ƴ������ҹ��ﳵ
	public Boolean addShopcar(int userID, int modelID, int number);
	public Boolean cancelShopcar(int id);
	public List<Shopcar> searchShopcarsByUser(int userID);
	public Shopcar searchShopcarByID(int id);
	
	// ���Ҳ���
	public List<Material> searchMaterialsByStatus(String status);
	public Material searchMaterialByID(int id);
	
	// ��������
	public Boolean addOrder(int userID, int modelID, int materialID, int number, float price, String status);

	public List<Order> searchOrdersByStatus(String status);
	public List<Order> searchOrdersByUserStatus(int userID, String status);
	
	// �޸Ķ���
	public Boolean updateOrderStatus(int id, String status);
	public Boolean updateOrderPriceStatus(int id, float price, String status);
	public Boolean updateOrderStatusByUser(int id, int userID, String status);
}
