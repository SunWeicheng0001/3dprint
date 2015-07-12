package database;

import java.util.List;

import entity.Collection;
import entity.Comment;

public interface UserLikeCollectionCommentServiceInterface {

	// �Ƿ����ޡ����ղ�
	public Boolean isUserlike(int userID, int modelID);
	public Boolean isCollection(int userID, int modelID);
	
	// ���ޡ�������ȡ��
	public Boolean addUserlike(int userID, int modelID, boolean like);
	public Boolean cancelUserlike(int userID, int modelID);
	
	// �ղء�ȡ��
	public Boolean addCollection(int userID, int modelID);
	public Boolean cancelColletion(int userID, int modelID);
	
	// ���ӡ���������
	public Boolean addComment(int userID, int modelID, String comment);
	public List<Comment> searchCommentByModelStatus(int modelID, String status);
	public List<Comment> searchCommentByStatus(String status);
	public List<Comment> searchCommentByStatusOrderByTime(String status, boolean asc);
	public List<Comment> searchCommentByStatusOrderByModelID(String status, boolean asc);
	public List<Comment> searchCommentByStatusOrderByCommenterID(String status, boolean asc);
	public List<Comment> searchCommentByStatusOrderByTimeLimitByModel(String status, boolean asc, int modelID);
	public List<Comment> searchCommentByStatusOrderByModelIDLimitByModel(String status, boolean asc, int modelID);
	public List<Comment> searchCommentByStatusOrderByCommenterIDLimitByModel(String status, boolean asc, int modelID);
	public List<Comment> searchCommentByStatusOrderByTimeLimitByUser(String status, boolean asc, int userID);
	public List<Comment> searchCommentByStatusOrderByModelIDLimitByUser(String status, boolean asc, int userID);
	public List<Comment> searchCommentByStatusOrderByCommenterIDLimitByUser(String status, boolean asc, int userID);
	
	// ��������״̬
	public Boolean updateCommentStatus(int id, String status);
	
	// ��ѯ�û��������ղ�
	public List<Collection> searchCollectionsByUserID(int userID);
 }
