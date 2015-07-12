package database;

import java.io.InputStream;
import java.util.List;

import entity.Model;


public interface ModelServiceInterface {

	// ��ѯģ��
	public Model searchModelByID(int id);
	public List<Model> searchModelsByUserStatus(int userID, String status);
	public List<Model> searchModelsSort(String style, String status, String candown, String sort);
	
	// ����ģ��
	public Model createModel(int userID, String name);
	 
	// ����ģ���ı���Ϣ���ļ���ͼƬ
	public Boolean updateModelInformation(int id, String name, float length, float width,
			float height, String style, String description);
	public Boolean updateModelFile(int id, byte[] file);
	
	public Boolean createModelImage(int id, byte[] image);
	public Boolean deleteModelImage(int id, int number);
	
	// �޸�ģ��״̬
	public Boolean updateModelStatus(int id, String status);
	
	// ��ȡģ��ͼƬ
	public InputStream readModelImage(int id, int number);
	
	public InputStream readModelFile(int id);
}
