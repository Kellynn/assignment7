package assign.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "meetings" )
//@XmlType(propOrder = {"team_meeting_name", "year", "meeting_name", "links"})
public class Meeting {
	
	private Long id;

	private String name;
    private String teamName;
    private String year;
    private String link;
    
    public Meeting() {
    	// this form used by Hibernate
    }
    
    public Meeting(String name, String teamName, String year, String link) {
    	// for application use, to create new events
    	this.name = name;
    	this.year = year;
    	this.teamName = teamName;
    	this.link = link;
    }
    
    public Meeting(String name, String teamName, String year, String link, Long providedId) {
    	// for application use, to create new events
    	this.name = name;
    	this.year = year;
    	this.teamName = teamName;
    	this.link = link;
    	this.id = providedId;
    }    
    
    @Id    
	@GeneratedValue(generator="increment")    
	@GenericGenerator(name="increment", strategy = "increment")
    @XmlTransient
    public Long getId() {
		return id;
    }

    private void setId(Long id) {
		this.id = id;
    }

	@Column(name = "YEAR")
	@XmlElement(name = "year")
    public String getYear() {
		return year;
    }

    public void setYear(String year) {
		this.year = year;
    }
    

	@Column(name = "NAME")
	@XmlElement(name = "meeting_name")
    public String getName() {
		return name;
    }

    public void setName(String name) {
		this.name = name;
    }
    
	@Column(name = "TEAM_NAME")
	@XmlElement(name = "team_meeting_name")
    public String getTeamName() {
		return teamName;
    }

    public void setTeamName(String name) {
		this.teamName = name;
    }
    
	@Column(name = "LINKS")
	@XmlElement(name = "link")
    public String getLink() {
		return link;
    }

    public void setLink(String link) {
		this.link = link;
    }

}

