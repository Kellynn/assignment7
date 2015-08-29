package assign.etl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import assign.domain.Meeting;
import assign.domain.Meetings;

import java.util.logging.*;

public class DBLoader {
	private SessionFactory sessionFactory;
	
	Logger logger;
	
	public DBLoader() {
		// A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        
        logger = Logger.getLogger("EavesdropReader");
	}
	
	public void loadData(Map<String, List<String>> data) throws Exception {
		logger.info("Inside loadData.");
		for (String s : data.keySet()) {
			List<String> values = data.get(s);
			
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			Long meetingId = null;
			try {
				tx = session.beginTransaction();
				Meeting newMeeting = new Meeting( s, values.get(1), values.get(2), values.get(0), new Long(1)); 
				session.save(newMeeting);
			    meetingId = newMeeting.getId();
			    tx.commit();
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
					throw e;
				}
			}
			finally {
				session.close();			
			}
		}
	}

	
	public List<Meeting> getMeetings(String name, String year) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Meeting.class).
        		add(Restrictions.eq("teamName", name)).add(Restrictions.eq("year", year));
		
		List<Meeting> meetings = criteria.list();
		
		return meetings;
	}
	
	public List<Meeting> getAllMeetings() throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Meeting.class);
		
		List<Meeting> meetings = criteria.list();
		
		return meetings;
	}
	
	
	public int countMeetings(String name, String year) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Meeting.class).
        		add(Restrictions.eq("teamName", name)).add(Restrictions.eq("year", year));
		List<Meeting> meetings = criteria.list();
		
		if (meetings.size() == 0)
			return 0;
		else
			return meetings.size();
	}
	
	
}
