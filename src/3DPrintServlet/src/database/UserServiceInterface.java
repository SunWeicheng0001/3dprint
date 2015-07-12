package database;

import java.io.InputStream;
import java.util.List;

import entity.User;

public interface UserServiceInterface {

	// �����û���
	public User searchUserByID(int id);
	public User searchUserByName(String name);
	public List<User> searchUsersByAuthorityStatus(String authority, String status);
	
	// ������ͨ�û�������Ա
	public User createUser(String name, String password);
	public User createManager(String name, String password);
	
	// �����û���Ϣ��ͷ������
	public Boolean updateUserInformation(int id, String email, String telephone, String qq);
	public Boolean updateUserImage(int id, byte[] image);
	public Boolean updateUserPassword(int id, String oldpassword, String newpassword);
	
	// �޸��û�״̬
	public Boolean updateUserStatus(int id, String status);
	
	// ��ȡ�û�ͷ��
	public InputStream readUserImage(int id);
	
}
