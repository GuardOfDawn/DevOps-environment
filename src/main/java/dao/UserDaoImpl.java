package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao{
	
	private DaoHelper daoHelper;
	
	public UserDaoImpl(){
		this.daoHelper = DaoHelperImpl.getBaseDaoInstance();
	}

	@Override
	public boolean register(String userName, String password) {
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
			String sql = "select * from user where username=?;";
            PreparedStatement preState = conn.prepareStatement(sql);
            preState.setString(1, userName);
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if(count<=0){
            	PreparedStatement prep = conn.prepareStatement("insert into user values(?,?);");
    			prep.setString(1, userName);
    			prep.setString(2, password);
    			int ret = prep.executeUpdate();
    			if(ret>0){
                	res = true;
    			}
    			else{
    				res = false;
    			}
            }
            else{
            	res = false;
            }
            rs.close(); 
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean isUserExist(String userName) {
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
			String sql = "select * from user where username=?;";
            PreparedStatement preState = conn.prepareStatement(sql);
            preState.setString(1, userName);
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {  
                count++;
            }
            if(count<=0){
            	res = false;
            }
            else{
            	res = true;
            }
            rs.close(); 
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public boolean login(String userName,String password){
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
			String sql = "select * from user where username=? and password=?;";
            PreparedStatement preState = conn.prepareStatement(sql);
            preState.setString(1, userName);
            preState.setString(2, password);
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {  
                count++;
            }
            if(count<=0){
            	res = false;
            }
            else{
            	res = true;
            }
            rs.close(); 
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

}
