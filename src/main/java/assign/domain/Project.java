package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
	
	private String name;
	private String count;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getCount() {
		return count;
	}

}
