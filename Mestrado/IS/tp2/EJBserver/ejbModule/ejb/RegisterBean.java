package ejb;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.UserData;




/**
 * Session Bean implementation class RegisterEBean
 */
@Stateless
public class RegisterBean implements RegisterBeanRemote {
	@PersistenceContext(name="TestPersistence")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public RegisterBean() {
       System.out.println("11111111");
    }

	@Override
	public void addNewUser(UserData guy) {
	    System.out.println("3333333");
	    //merge or persist
		em.persist(guy);
	    System.out.println("4444444");

	}
	
	public String RetriveLogin(String username) {
		System.out.println("Verificação de username");

		Query q = em.createQuery(" from UserData where login_name = :t");
		q.setParameter("t", username);

		ArrayList<UserData> dudes = null;
		UserData dude = null;
		dudes= (ArrayList<UserData>) q.getResultList();
		
		if(!dudes.isEmpty()){
			System.out.println("Username existe");
			return "yes";
		}
		else
		{
			return "no";
		}
		
	}

}
