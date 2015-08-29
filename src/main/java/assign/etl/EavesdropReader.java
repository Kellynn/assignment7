package assign.etl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EavesdropReader {
	
	String url;
	Logger logger; 
	
	public EavesdropReader(String url) {
		this.url = url;
		
		logger = Logger.getLogger("EavesdropReader");
	}
	
	/*
	 * Return a map where the contents of map is a single entry:
	 * <this.url, List-of-parsed-entries>
	 */
	public Map<String, List<String>> readData() {
		
		logger.info("Inside readData.");
		
		Map<String, List<String>> data = new HashMap<String, List<String>>();
		
		// Read and parse data from this.url
		ArrayList<String> meetings = new ArrayList<String>();
		try {
			String source = this.url;	
			Document doc = Jsoup.connect(source).get();
		    Elements links = doc.select("body a");
		    
		    ListIterator<Element> iter = links.listIterator();
		    while(iter.hasNext()) {
		    		Element e = (Element) iter.next();
		    		String s = e.html();
		    		s = s.replaceAll("/", "");
		    		
		    		
		    		if (!s.equals("Name") && !s.equals("Last modified") && !s.equals("Size") && !s.equals("Description") 
		    				&& !s.equals("Parent Directory") && s.endsWith(".log.txt") == true)
		    			meetings.add(s);
		    }	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		data.put(this.url, meetings);
		
		return data;
	}
}