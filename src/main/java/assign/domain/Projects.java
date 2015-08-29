package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class Projects {
	
	@XmlElement(name = "project")
    private List<Project> projects = null;
 
    public List<Project> getProjects() {
        return projects;
    }
 
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
