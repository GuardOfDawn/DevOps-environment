package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Project;
import service.ProjectService;
import service.ProjectServiceImpl;

/**
 * Servlet implementation class ProjectDetailServlet
 */
public class ProjectDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProjectService projectService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectDetailServlet() {
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
				String userName = String.valueOf(session.getAttribute("username"));
				List<String> projectList = projectService.getUserJoinList(userName);
				request.setAttribute("projectList", projectList);
				String projectName = request.getParameter("projectName");
				if(projectName==null){
					if(projectList!=null&&projectList.size()>0){
						projectName = projectList.get(0);
						Project project = projectService.getProjectDetail(projectName);
						request.setAttribute("project", project);
						RequestDispatcher rd = request.getRequestDispatcher("/projectDetail");
						rd.forward(request, response);
					}
					else{
						RequestDispatcher rd = request.getRequestDispatcher("/noProject");
						rd.forward(request, response);
					}
				}
				else{
					Project project = projectService.getProjectDetail(projectName);
					request.setAttribute("project", project);
					RequestDispatcher rd = request.getRequestDispatcher("/projectDetail");
					rd.forward(request, response);
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
