package servlet;

import org.apache.log4j.PropertyConfigurator;
import tool.GetPath;

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
        String path = GetPath.getResourcesPath();
        path = path.substring(0, path.length() - 16);
        System.setProperty("WORK_DIR", path);
        String file = getInitParameter("log4j");
        if (file != null) {
            PropertyConfigurator.configure(file);
        }
    }
}
