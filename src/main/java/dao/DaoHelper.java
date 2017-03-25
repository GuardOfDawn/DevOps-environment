package dao;

import java.sql.Connection;

public interface DaoHelper {
	
	public Connection getConnection();
	
	public void closeConnection(Connection con);

}
