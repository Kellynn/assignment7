package assign.etl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;

public class Transformer {
	
	Logger logger;
	
	public Transformer() {
		logger = Logger.getLogger("Transformer");		
	}
	
	public Map<String, List<String>> transform(Map<String, List<String>> data) {
		logger.info("Inside transform.");

		String name;
		String teamName;
		String year;
		String link;

		Map<String, List<String>> newData = new HashMap<String, List<String>>();

		Set s = data.keySet();
		Iterator itr = s.iterator();
		String k = (String) itr.next();
		List<String> values = data.get(k);
		
		for (String x : values) {
			ArrayList<String> parameters = new ArrayList<String>();
			
			link = k + "/" + x;
			parameters.add(link);
			
			System.out.println("Checking this: " + k);
			parameters.add(getTeamName(k));
			
			
			year = k.substring(k.length() - 4);
			
			parameters.add(year);
			
			name = x;
			
			newData.put(x, parameters);
		}
		
		return newData;
	}
	
	public String getTeamName(String url) {
		String s = "";
		int i = 40;
		char c = url.charAt(i);
		
		while (c != '/') {
			s = s + c;
			
			c = url.charAt(++i);
		}
		
		return s;
	}
}
