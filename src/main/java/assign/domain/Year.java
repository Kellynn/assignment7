package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "year")
@XmlAccessorType(XmlAccessType.FIELD)
public class Year {
	
	private String year;
	private int count;
	
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

}
