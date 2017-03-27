package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
    	super();
    	userService = new UserServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		boolean cookieFound = false;
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			// Look through all the cookies and see if the
			// cookie with the login info is there.
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("LoginCookie")) {
					cookieFound = true;
					break;
				}
			}
		}
		
		if(session.getAttribute("username")==null){
			String loginValue = request.getParameter("username");
			boolean isLoginAction = (null == loginValue) ? false : true;
			if (isLoginAction) { // User is logging in
				if (cookieFound) { // If the cookie exists update the value only
				// if changed
					if (!loginValue.equals(cookie.getValue())) {
						cookie.setValue(loginValue);
						response.addCookie(cookie);
					}
				} else {
					// If the cookie does not exist, create it and set value
					cookie = new Cookie("LoginCookie", loginValue);
					cookie.setMaxAge(Integer.MAX_VALUE);
					// System.out.println("Add cookie");
					response.addCookie(cookie);
				}

				// create a session to show that we are logged in
				boolean loginRes = userService
						.login(loginValue,request.getParameter("password"));
				if(loginRes){
					session = request.getSession(true);
					session.setMaxInactiveInterval(5*60);
					session.setAttribute("username", loginValue);
					
					response.sendRedirect(request.getContextPath() + "/homePage");
				}
				else{
					request.setAttribute("loginRes","You have an error with your name or password");
					RequestDispatcher rd = request.getRequestDispatcher("/login");
					rd.forward(request, response);
				}
				
			} else {
				// Display the login page. If the cookie exists, set login
				response.sendRedirect(request.getContextPath() + "/login");
			}
			
		}
		else{
			response.sendRedirect(request.getContextPath() + "/homePage");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
