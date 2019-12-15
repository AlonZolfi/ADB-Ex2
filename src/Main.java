import java.util.Date;
import java.util.List;
import java.util.Map;

import hib.Mediaitems;

public class Main {
	public static void main(String[] args) {
		//System.out.println(Assignment.isExistUsername("hilalon"));
		//System.out.println(Assignment.insertUser("hilalon","1234","a","z","08","09","1994"));
		/*List<Mediaitems> topNItems = Assignment.getTopNItems(1);
		for (Mediaitems mi : topNItems) {
			System.out.println(mi.getMid());
		}*/
		//System.out.println(Assignment.validateUser("hilalon","1234"));
		//System.out.println(Assignment.validateAdministrator("admin1","admin1"));
		//Assignment.insertToHistory("1", "18");
		Map<String, Date> map = Assignment.getHistory("1");
		for(Map.Entry<String, Date> ent: map.entrySet()) {
			System.out.println(ent.getKey()+ent.getValue()+"");
		}
		
		
	
	}
}
