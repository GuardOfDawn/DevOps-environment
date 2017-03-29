package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.ProjectService;
import service.ProjectServiceImpl;

/**
 * Servlet implementation class CreateProjectServlet
 */
public class CreateProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProjectService projectService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProjectServlet() {
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
				String projectName = request.getParameter("projectname");
				String projectKey = request.getParameter("projectkey");
				String userName = String.valueOf(session.getAttribute("username"));
				boolean res = projectService.createProject(userName,projectName, projectKey);
				if(res){
					
				}
				else{
					
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
