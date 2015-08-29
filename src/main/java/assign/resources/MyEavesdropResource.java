package assign.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import assign.domain.Meeting;
import assign.domain.Meetings;
import assign.domain.MeetingsCount;
import assign.domain.Project;
import assign.domain.Projects;
import assign.domain.Year;
import assign.etl.DBLoader;

@Path("/myeavesdrop/")
public class MyEavesdropResource {

	public MyEavesdropResource() {
		
	}
	
	private DBLoader loader;
	
	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
		System.out.println("Inside helloworld");
		return "Hello world ";
	}
	
	@GET
	@Path("/meetingcount")
	@Produces("application/xml")
	public StreamingOutput getMeetingCount(@Context HttpServletResponse response) {
		loader = new DBLoader();
		final Projects projects = new Projects();
		
		try {
			
			List<Meeting> m = loader.getAllMeetings();
			ArrayList<Project> p = getProjectCount(m);
			projects.setProjects(p);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new StreamingOutput() {
        public void write(OutputStream outputStream) throws IOException, WebApplicationException {
           outputProjects(outputStream, projects);
        }
     };
	}
	
	private ArrayList<Project> getProjectCount(List<Meeting> m) {
		ArrayList<Project> project = new ArrayList<Project>();
		
		String meetingName = "";
		String year = "";
		
		for (Meeting e : m) {
			
			if (e.getTeamName().equals(meetingName)) {
				int index = getProjectIndex(project, e.getTeamName());
				Project z = project.get(index);
				int count = Integer.parseInt(z.getCount()) + 1;
				z.setCount(Integer.toString(count));
			}
			else {
				meetingName = e.getTeamName();
				
				// new meeting so create a new project with that name
				Project p = new Project();
				p.setName(meetingName);
				p.setCount("1");
				
				project.add(p);
				
				
			}
		}
		
		
		return project;
	}
	
	private int getProjectIndex(ArrayList<Project> p, String name) {
		int index = 0;
		
		for (Project z : p) {
			if (z.getName().equals(name))
				return index;
			else
				++index;
		}
		
		return -1;
	}
	
	private int getYearIndex(List<Year> y, String year) {
		int index = 0;
		
		for (Year z : y) {
			
			if (z.getYear().equals(year))
				return index;
			else
				++index;
		}
		
		return -1;
	}
	
	
	protected void outputMeetingsCount(OutputStream os, MeetingsCount meeting) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(MeetingsCount.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(meeting, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
	protected void outputMeetings(OutputStream os, Meetings meeting) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Meetings.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(meeting, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
	protected void outputProjects(OutputStream os, Projects project) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Projects.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(project, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
}
