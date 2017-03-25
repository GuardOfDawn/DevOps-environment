package service;

public interface UserService {

	public String register(String userName,String password);
	
	public boolean login(String userName,String password);
	
}
