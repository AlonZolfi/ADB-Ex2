package Assignment;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.*;
import hib.*;
import HibernateUtil.HibernateUtil;

public class Assignment {

	public static boolean isExistUsername(String username) {
		try{
			Session session = HibernateUtil.currentSession();
			String hqlQuery = 
					"from Users user" + 
					" where user.username = '" + username + "'";
			@SuppressWarnings("unchecked")
			List<Users> users = session.createQuery(hqlQuery).list();
			if (users.size() > 0)
				return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return false;
	}

	public static String insertUser(String username, String password, String first_name, String last_name, String day_of_birth, String month_of_birth, String year_of_birth) {
		if (isExistUsername(username))
			return null;
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(first_name);
		user.setLastName(last_name);
		Timestamp dateOfBirth = getTimeStamp(day_of_birth, month_of_birth, year_of_birth);
		if (dateOfBirth==null)
			throw new IllegalArgumentException();
		user.setDateOfBirth(dateOfBirth);
		user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

		Integer userID = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			userID = (Integer) session.save(user);
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();

		}
		finally {
			HibernateUtil.closeSession();
		}

		return userID + "";
	}

	@SuppressWarnings("deprecation")
	private static Timestamp getTimeStamp(String day_of_birth, String month_of_birth, String year_of_birth) {
		try {
			int dayOfBirth = Integer.parseInt(day_of_birth);
			int monthOfBirth = Integer.parseInt(month_of_birth) - 1;
			int yearOfBirth = Integer.parseInt(year_of_birth);
			if(dayOfBirth >= 1 && dayOfBirth <= 31 && monthOfBirth >= 0 && monthOfBirth <= 11 && yearOfBirth >= 1900) {
				return new Timestamp(yearOfBirth, monthOfBirth, dayOfBirth, 0, 0, 0, 0);
			}
		}
		catch(Exception e) {
			System.out.println("Invalid Date");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Mediaitems> getTopNItems (int top_n){
		List<Mediaitems> mediaItems = null;
		try {
			Session session = HibernateUtil.currentSession();
			String hqlQuery = 
					"FROM Mediaitems mi" +
					" WHERE ROWNUM <=" + top_n + 
					" ORDER BY mi.mid DESC";
			mediaItems = session.createQuery(hqlQuery).list();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return mediaItems;
	}
	
	public static String validateUser(String username, String password) {
		try{
			Session session = HibernateUtil.currentSession();
			String hqlQuery = 
					"FROM Users user" + 
					" WHERE user.username = '" + username + "'";
			@SuppressWarnings("unchecked")
			List<Users> users = session.createQuery(hqlQuery).list();
			for (Users user : users) {
				if (user.getPassword().equals(password))
					return user.getUserid()+"";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return "Not Found";
	}
		
	public static String validateAdministrator(String username, String password) {
		try{
			Session session = HibernateUtil.currentSession();
			String hqlQuery = 
					"from Administrators admin" + 
					" where admin.username = '" + username + "'";
			@SuppressWarnings("unchecked")
			List<Administrators> admins = session.createQuery(hqlQuery).list();
			for (Administrators admin : admins) {
				if (admin.getPassword().equals(password))
					return admin.getAdminid()+"";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return "Not Found";
	}

	public static void insertToHistory (String userid, String mid) {
		HistoryId historyID = null;
		try {
			historyID = new HistoryId();
			historyID.setMid(Integer.parseInt(mid));
			historyID.setUserid(Integer.parseInt(userid));
			historyID.setViewtime(new Timestamp(System.currentTimeMillis()));
			History history = new History();
			history.setId(historyID);
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.save(history);
			tx.commit();
			System.out.println("The insertion to history table was successful " + historyID.getViewtime().toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
	}
	
	public static Map<String, Date> getHistory(String userid){
		Map<String, Date> map = null;
		try{
			Session session = HibernateUtil.currentSession();
			String hqlQuery =
					"select his.mediaitems.title, his.id.viewtime" + 
					" from History his" + 
					" where his.id.userid = " + userid +
					" order by viewtime asc";
			@SuppressWarnings("unchecked")
			List<Object[]> historyData = session.createQuery(hqlQuery).list();
			map = listToMap(historyData);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return map;
		
	}
	
	private static Map<String, Date> listToMap(List<Object[]> historyData) {
		Map <String, Date> map = new HashMap<>();
		for( Object[] obj: historyData) {
			map.put((String)obj[0], (Timestamp)obj[1]);			
		}
		final Map<String, Date> sortedByCount = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sortedByCount;
	}

	public static void insertToLog (String userid) {
		try {
			LoginlogId loginID = new LoginlogId();
			loginID.setUserid(Integer.parseInt(userid));
			loginID.setLogintime(new Timestamp(System.currentTimeMillis()));
			Loginlog login = new Loginlog();
			login.setId(loginID);
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.save(login);
			tx.commit();
			System.out.println("The insertion to log table was successful " + loginID.getLogintime().toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
	}
	
	public static int getNumberOfRegistredUsers(int n) {
		int user_count = 0;
		try{
			Timestamp tmp = new Timestamp(System.currentTimeMillis());
			Timestamp ts = Timestamp.from(tmp.toInstant().minus(n, ChronoUnit.DAYS));
			List<Users> users = getUsers();
			for(Users user: users) {
				if(user.getRegistrationDate().after(ts))
					user_count++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return user_count;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Users> getUsers(){
		List<Users> users = null;
		try{
			Session session = HibernateUtil.currentSession();
			String hqlQuery = "FROM Users";
			users = session.createQuery(hqlQuery).list();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return users;
	}
	
	public static Users getUser(String userid) {
		Users user = null;
		try{
			Session session = HibernateUtil.currentSession();
			user = (Users) session.get(Users.class,Integer.parseInt(userid));
			return user;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession();
		}
		return user;
		
	}

}
