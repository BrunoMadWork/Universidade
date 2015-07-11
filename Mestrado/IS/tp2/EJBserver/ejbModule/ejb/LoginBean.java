package ejb;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;







import toDatabase.DbNews;
import data.UserData;

/**
 * Session Bean implementation class LoginBean
 */
@Stateless
public class LoginBean implements LoginBeanRemote {
	@PersistenceContext(name="TestPersistence")
	EntityManager em;
	public LoginBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserData RetriveLogin(String name, String pass) {
		System.out.println("Login Bean - tentativa de login");

		Query q = em.createQuery(" from UserData where login_name = :t and password = :p");
		q.setParameter("t", name);
		q.setParameter("p", pass);

		ArrayList<UserData> dudes = null;
		UserData dude = null;
		dudes= (ArrayList<UserData>) q.getResultList();
		
		if(!dudes.isEmpty()){
			dude=dudes.get(0);
			System.out.println("Login Bean - Dados certos");
		}
		else
		{
			System.out.println("Login Bean - Dados errados");
		}
		// Close an application-managed entity manager.

		if(dude==null){
			return new UserData("no","no","no","no");
		}
		return dude;
	}
	
	public  ArrayList<UserData> Allusers()
	{
		System.out.println("All users function");
		Query q = em.createQuery(" from UserData");
		ArrayList<UserData> users = null;
		users = (ArrayList<UserData>) q.getResultList();
		return users;
	}
	
	public  ArrayList<UserData> Getselecteduser(long loginid)
    {
    	Query q = em.createQuery(" from UserData where loginid = :l");
    	q.setParameter("l", loginid);
    	ArrayList<UserData> user = null;
    	user = (ArrayList<UserData>) q.getResultList();
    	
		System.out.println("It got back the user");
    	return user;
    }
	
	public void Deleteuser(long loginid)
	{
		System.out.println("Delete user");
		Query q = em.createQuery("DELETE FROM UserData WHERE loginid=:l");
    	q.setParameter("l", loginid);
    	q.executeUpdate();
	}
	
	public void Updateuser(long loginid, String name, String username, String email, String password)
	{
		System.out.println("Updateuser- function");
		System.out.println("Name: "+ name);
		System.out.println("Username: "+ username);
		System.out.println("Email: "+ email);
		System.out.println("Password: "+ password);
		if(password == null || password.equals(""))
		{
			System.out.println("Update user - password vazia");
			Query q = em.createQuery("UPDATE UserData SET name=:n, login_name= :u, email=:e WHERE loginid=:l");
	    	q.setParameter("n", name);
	    	q.setParameter("u", username);
	    	q.setParameter("e", email);
	    	q.setParameter("l", loginid);
	    	q.executeUpdate();
		}
		else
		{
			System.out.println("Update user - password cheia");
			Query q = em.createQuery("UPDATE UserData SET name=:n, login_name= :u, email=:e, password=:p WHERE loginid=:l ");
			q.setParameter("n", name);
	    	q.setParameter("u", username);
	    	q.setParameter("e", email);
	    	q.setParameter("l", loginid);
	    	q.setParameter("p", password);
	    	q.executeUpdate();
		}
	}

}
