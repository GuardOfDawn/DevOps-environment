import tool.GetPath;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:58 PM
 */
public class Test {
    public static void main(String[] args) {
//        JenkinsProjStat jenkinsProjStat = new JenkinsProjStatImpl();
//        Map<String, String> map = jenkinsProjStat.getLastBuild("test");
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//        System.out.println(jenkinsProjStat.getFrequency("test"));
        System.out.println(GetPath.getResourcesPath());
    }
}
