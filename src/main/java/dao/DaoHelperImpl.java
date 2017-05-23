package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import tool.GetPath;

public class DaoHelperImpl implements DaoHelper{
	
	private static DaoHelperImpl baseDao = new DaoHelperImpl();
	
	private static final String FILENAME = "devopsSystem.db";
	
	private String path = null;
	
	private static final String CREATE_TABLE_USER = "CREATE TABLE if not exists `user`("
			+ "`username` varchar(50) NOT NULL,"
			+ "`password` varchar(50) NOT NULL);";
	
	private static final String CREATE_TABLE_JOIN = "CREATE TABLE if not exists `join`("
			+ "`projectname` varchar(50) NOT NULL,"
			+ "`username` varchar(50) NOT NULL,"
			+ "`jointime` varchar(20) NOT NULL);";
	
	private static final String CREATE_TABLE_PROJECT = "CREATE TABLE if not exists `project`("
			+ "`projectname` varchar(50) NOT NULL,"
			+ "`projectkey` varchar(100) NOT NULL,"
			+ "`createtime` varchar(20) NOT NULL,"
			+ "`repository` varchar(200) NOT NULL,"
			+ "`artifact` varchar(200));";
	
	private DaoHelperImpl(){
		String str = GetPath.getResourcesPath().replace("\\", "/");
        this.path = str.substring(0, str.length() - 15);
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
			stat.executeUpdate(CREATE_TABLE_JOIN);
			stat.executeUpdate(CREATE_TABLE_PROJECT);
			stat.close();
			closeConnection(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
