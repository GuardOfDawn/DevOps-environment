package testService;

import model.IssueSeverity;
import model.IssueType;

public class IssueModelTest {

	public static void main(String[] args) {
		IssueSeverity[] severities = IssueSeverity.values();
		for(int i=0;i<severities.length;i++){
			System.out.println(String.valueOf(severities[i]).toLowerCase());
		}
		IssueType[] types = IssueType.values();
		for(int i=0;i<types.length;i++){
			System.out.println(String.valueOf(types[i]).toLowerCase());
		}
	}
	
}
