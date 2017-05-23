package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import service.ProjectService;
import service.ProjectServiceImpl;

/**
 * Servlet implementation class ProjectInfoChangeServlet
 */
public class ProjectInfoChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(ProjectInfoChangeServlet.class);
	
	private ProjectService projectService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectInfoChangeServlet() {
        super();
        projectService = new ProjectServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		else{
			if(session.getAttribute("username")==null){
				session.invalidate();
				session = null;
				response.sendRedirect(request.getContextPath() + "/login");
			}
			else{
				String changeTarget = request.getParameter("changeTarget");
				String changeContent = request.getParameter("changeContent");
				String projectName = request.getParameter("projectName");
				if("artifact".equals(changeTarget)){
					boolean res = projectService.updateProjectArtifact(projectName, changeContent);
					logger.info("change artifact of project("+projectName+"):"+res);
					response.setContentType("text/html;charset=utf-8");  
			        PrintWriter out = response.getWriter();
			        out.println(String.valueOf(res));
			        out.close();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
