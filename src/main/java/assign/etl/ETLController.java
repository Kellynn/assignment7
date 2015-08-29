package assign.etl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ETLController {
	
	EavesdropReader reader;
	EavesdropReader reader2;
	Transformer transformer;
	DBLoader loader;
	
	public ETLController() {
		transformer = new Transformer();
		loader = new DBLoader();
	}
	
	public static void main(String[] args) {
		ETLController etlController = new ETLController();
		etlController.performETLActions();
	}

	private void performETLActions() {		
		try {
			ArrayList<String> projects = getProjects();
			

			
			for (String p : projects) {
				ArrayList<String> years = getYears(p);
				
				for (String y : years) {
					String url = "http://eavesdrop.openstack.org/meetings/" + p + "/" + y;
					reader = new EavesdropReader(url);
					Map<String, List<String>> data = reader.readData();
					Map<String, List<String>> transformedData = transformer.transform(data);
					loader.loadData(transformedData);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private ArrayList<String> getYears(String team) {
		ArrayList<String> years = new ArrayList<String>();
		
		try {
			String source = "http://eavesdrop.openstack.org/meetings/" + team;	
			Document doc = Jsoup.connect(source).get();
		    Elements links = doc.select("body a");
		    
		    ListIterator<Element> iter = links.listIterator();
		    while(iter.hasNext()) {
		    		Element e = (Element) iter.next();
		    		String s = e.html();
		    		s = s.replaceAll("/", "");
		    		
		    		if (!s.equals("Name") && !s.equals("Last modified") && !s.equals("Size") && !s.equals("Description") 
		    				&& !s.equals("Parent Directory"))
		    			years.add(s);
		    		
		    		System.out.println("Added s:" + s);
		    }	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return years;
	}
	
	private ArrayList<String> getProjects() {
		ArrayList<String> projects = new ArrayList<String>();
		
		try {
			String source = "http://eavesdrop.openstack.org/meetings/";	
			Document doc = Jsoup.connect(source).get();
		    Elements links = doc.select("body a");
		    
		    ListIterator<Element> iter = links.listIterator();
		    while(iter.hasNext()) {
		    		Element e = (Element) iter.next();
		    		String s = e.html();
		    		s = s.replaceAll("/", "");
		    		
		    		if (!s.equals("Name") && !s.equals("Last modified") && !s.equals("Size") && !s.equals("Description") 
		    				&& !s.equals("Parent Directory"))
		    			projects.add(s);
		    		
		    		System.out.println("********Added project:" + s);
		    }	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return projects;
	}
}