package servlet;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Author: stk
 * Date: 3/31/17
 * Time: 7:21 PM
 */
public class Log4JInitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Initiate log4j
     *
     * @throws ServletException ServletException
     */
    public void init() throws ServletException {
        String file = getInitParameter("log4j");
        if (file != null) {
            PropertyConfigurator.configure(file);
        }
    }
}
