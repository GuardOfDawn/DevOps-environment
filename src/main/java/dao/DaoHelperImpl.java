package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoHelperImpl implements DaoHelper{
	
	private static DaoHelperImpl baseDao = new DaoHelperImpl();
	
	private static final String FILENAME = "devopsSystem.db";
	
	private String path = null;
	
	private static final String CREATE_TABLE_USER = "CREATE TABLE if not exists `user`("
			+ "`username` varchar(50) NOT NULL,"
			+ "`password` varchar(50) NOT NULL);";
	
	private static final String CREATE_TABLE_PROJECT = "CREATE TABLE if not exists `project`("
			+ "`projectid` varchar(50) NOT NULL,"
			+ "`projectname` varchar(50) NOT NULL,"
			+ "`description` varchar(256));";
	
	private static final String CREATE_TABLE_JOIN = "CREATE TABLE if not exists `join`("
			+ "`projectid` varchar(50) NOT NULL,"
			+ "`username` varchar(50) NOT NULL);";
	
	private DaoHelperImpl(){
		String str = System.getProperty("user.dir").replace("\\", "/");
		this.path = str.substring(0, str.length()-4).concat("/webapps/devops-system/");
		this.initDataBase();
	}
	
	public static DaoHelperImpl getBaseDaoInstance(){
		return baseDao;
	}

	public Connection getConnection() {
		Connection connection = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+path+FILENAME);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection con) {
		if(con!=null)
		{
			try{
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initDataBase(){
		try{
			Connection connection = getConnection();
			Statement stat = connection.createStatement();
			stat.executeUpdate(CREATE_TABLE_USER);
			stat.executeUpdate(CREATE_TABLE_PROJECT);
			stat.executeUpdate(CREATE_TABLE_JOIN);
			closeConnection(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
