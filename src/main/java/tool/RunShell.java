package tool;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Author: stk
 * Date: 3/19/17
 * Time: 6:28 PM
 */
public class RunShell {
    private static Logger logger = Logger.getLogger(RunShell.class);

    /**
     * Run linux shell command.
     *
     * @param cmd Command line
     * @return Execution results
     */
    public static String runShell(String[] cmd) {
        String result = "";
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            ps.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            result = sb.toString();
            logger.info("Shell execution result: " + result);
        } catch (Exception e) {
            logger.error("Execute shell command error.\n", e);
        }
        return result;
    }
}
