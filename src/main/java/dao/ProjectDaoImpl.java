package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao{

	private DaoHelper daoHelper;
	
	public ProjectDaoImpl(){
		this.daoHelper = DaoHelperImpl.getBaseDaoInstance();
	}

	@Override
	public boolean addProject(String projectName, String projectKey, String time) {
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
        	PreparedStatement prep = conn.prepareStatement("insert into project values(?,?,?);");
			prep.setString(1, projectName);
			prep.setString(2, projectKey);
			prep.setString(3, time);
			int ret = prep.executeUpdate();
			if(ret>0){
            	res = true;
			}
			else{
				res = false;
			}
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean deleteProject(String projectName) {
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
        	PreparedStatement prep = conn.prepareStatement("delete from project where projectname=?;");
			prep.setString(1, projectName);
			int ret = prep.executeUpdate();
			if(ret>0){
            	res = true;
			}
			else{
				res = false;
			}
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<String> getList(String column, String value, String retColumn) {
		List<String> res = new ArrayList<String>();
		try{
			Connection conn = daoHelper.getConnection();
			String condition = "";
			if(column!=null){
				condition = " where "+column+"=?";
			}
			String sql = "select "+retColumn+" from project"+condition+" order by createtime;";
            PreparedStatement preState = conn.prepareStatement(sql);
            if(column!=null){
                preState.setString(1, value);
            }
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {  
            	res.add(rs.getString(retColumn));
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
