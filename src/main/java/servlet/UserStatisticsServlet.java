package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserStat;
import service.UserStatService;
import service.UserStatServiceImpl;

/**
 * Servlet implementation class UserStatisticsServlet
 */
public class UserStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserStatService userStatService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserStatisticsServlet() {
        super();
        userStatService = new UserStatServiceImpl();
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
				UserStat userStat = userStatService.getUserStatistics(userName);
				request.setAttribute("userStat", userStat);
				RequestDispatcher rd = request.getRequestDispatcher("/userStatistics");
				rd.forward(request, response);
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
