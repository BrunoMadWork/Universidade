package ejb;

import javax.ejb.Remote;

import data.UserData;


@Remote
public interface RegisterBeanRemote {
      public void addNewUser(UserData guy);// Adds a new user to the database
      public  String RetriveLogin(String name);
}

