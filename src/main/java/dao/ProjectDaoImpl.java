package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ProjectInfo;

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
			String sql = "insert into project(projectname,projectkey,createtime) values(?,?,?);";
        	PreparedStatement prep = conn.prepareStatement(sql);
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
			prep.close();
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
			prep.close();
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
            preState.close();
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Map<String, String> getNameKeyMapping() {
		Map<String, String> res = new HashMap<String,String>();
		try{
			Connection conn = daoHelper.getConnection();
			
			String sql = "select projectname,projectkey from project;";
            PreparedStatement preState = conn.prepareStatement(sql);
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {  
            	res.put(rs.getString("projectname"), rs.getString("projectkey"));
            }
            rs.close();
            preState.close();
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(String projectName, String projectKey) {
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
        	PreparedStatement prep = conn.prepareStatement("update project set projectkey=? where projectname=?;");
			prep.setString(1, projectKey);
			prep.setString(2, projectName);
			int ret = prep.executeUpdate();
			if(ret>0){
            	res = true;
			}
			else{
				res = false;
			}
			prep.close();
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ProjectInfo getProjectInfo(String projectName) {
		ProjectInfo project = null;
		try{
			Connection conn = daoHelper.getConnection();
			
			String sql = "select * from project where projectname=?;";
            PreparedStatement preState = conn.prepareStatement(sql);
            preState.setString(1, projectName);
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {  
            	project = new ProjectInfo();
            	project.setProjectName(projectName);
            	project.setProjectKey(rs.getString("projectkey"));
            	project.setCreateTime(rs.getString("createtime"));
            	project.setRepository(rs.getString("repository"));
            	project.setArtifact(rs.getString("artifact"));
            }
            rs.close();
            preState.close();
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return project;
	}

	@Override
	public boolean addProject(ProjectInfo project) {
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
			String sql = "insert into project(projectname,projectkey,createtime,repository,artifact) values(?,?,?,?,?);";
        	PreparedStatement prep = conn.prepareStatement(sql);
			prep.setString(1, project.getProjectName());
			prep.setString(2, project.getProjectKey());
			prep.setString(3, project.getCreateTime());
			prep.setString(4, project.getRepository());
			prep.setString(5, project.getArtifact());
			int ret = prep.executeUpdate();
			if(ret>0){
            	res = true;
			}
			else{
				res = false;
			}
			prep.close();
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(String projectName, String column, String value) {
		boolean res = false;
		try{
			Connection conn = daoHelper.getConnection();
        	PreparedStatement prep = conn.prepareStatement("update project set "+column+"=? where projectname=?;");
			prep.setString(1, value);
			prep.setString(2, projectName);
			int ret = prep.executeUpdate();
			if(ret>0){
            	res = true;
			}
			else{
				res = false;
			}
			prep.close();
			daoHelper.closeConnection(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

}
