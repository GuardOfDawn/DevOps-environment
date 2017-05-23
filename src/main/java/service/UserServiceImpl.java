package service;

import org.apache.log4j.Logger;

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
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
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
			boolean jenkinsR = jenkinsUser.createJenkinsUser(userName, password);
			boolean sonarR = sonarUser.createSonarUser(userName, password);
			logger.warn("create Jenkins user's result:"+jenkinsR);
			logger.warn("create sonar user's result:"+sonarR);
			if(jenkinsR&&sonarR){
				//register user
				boolean res2 = userDao.register(userName, password);
				if(res2){
					registerRes = "register success";
				}
				else{
					registerRes = "Failed to register.";
				}
			}
			else{
				registerRes = "Failed to register,the user name has been used";
			}
		}
		else{
			registerRes = "Failed to register,the user name has been used.";
		}
		return registerRes;
	}
	
	@Override
	public boolean login(String userName,String password){
		return userDao.login(userName,password);
	}

}
