package service;

import dao.UserDao;
import dao.UserDaoImpl;
import interfaces.JenkinsUser;
import interfaces.SonarUser;
import user.JenkinsUserImpl;
import user.SonarUserImpl;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	private JenkinsUser jenkinsUser;
	private SonarUser sonarUser;
	
	public UserServiceImpl(){
		userDao = new UserDaoImpl();
		jenkinsUser = new JenkinsUserImpl();
		sonarUser = new SonarUserImpl();
	}

	@Override
	public String register(String userName, String password) {
		String registerRes = null;
		//search database for username
		boolean res = userDao.isUserExist(userName);
		if(!res){
			//check jenkins and sonarqube for username
			if(jenkinsUser.createJenkinsUser(userName, password)
					&&sonarUser.createSonarUser(userName, password)){
				//register user
				boolean res2 = userDao.register(userName, password);
				if(res2){
					registerRes = "注册成功";
				}
				else{
					registerRes = "注册失败";
				}
			}
			else{
				registerRes = "注册失败，用户名已存在";
			}
		}
		else{
			registerRes = "注册失败，用户名已存在";
		}
		return registerRes;
	}
	
	@Override
	public boolean login(String userName,String password){
		return userDao.login(userName,password);
	}

}
