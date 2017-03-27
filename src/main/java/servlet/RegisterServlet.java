package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    	userService = new UserServiceImpl();
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
			if(session.getAttribute("username")!=null){
				response.sendRedirect(request.getContextPath() + "/homepage");
			}
			else{
				String userName = request.getParameter("username");
				String password = request.getParameter("password");
				String registerRes = userService.register(userName, password);
				if(registerRes.equals("register success")){
					session.setAttribute("username", userName);
					request.setAttribute("username", userName);
					request.setAttribute("password", password);
					RequestDispatcher rd = request.getRequestDispatcher("/registerSuccess");
					rd.forward(request, response);
				}
				else{
					request.setAttribute("username", userName);
					request.setAttribute("registerRes", registerRes);
					RequestDispatcher rd = request.getRequestDispatcher("/register");
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
