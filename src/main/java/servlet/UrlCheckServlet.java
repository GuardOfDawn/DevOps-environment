package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import tool.HttpUtils;

/**
 * Servlet implementation class UrlCheckServlet
 */
public class UrlCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UrlCheckServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrlCheckServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String checkUrl = request.getParameter("checkUrl");
		boolean checkResult = HttpUtils.checkUrl(checkUrl);
		logger.info("result of checking url("+checkUrl+"):"+checkResult);
		response.setContentType("text/html;charset=utf-8");  
        PrintWriter out = response.getWriter();
        out.println(String.valueOf(checkResult));
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
