package assign.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "meetings")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeetingsCount {
	
	@XmlElement(name = "count")
	private int count;
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

}