package dao;

public interface UserDao {
	
	public boolean isUserExist(String userName);
	
	public boolean register(String userName,String password);
	
	public boolean login(String userName,String password);
	
}
