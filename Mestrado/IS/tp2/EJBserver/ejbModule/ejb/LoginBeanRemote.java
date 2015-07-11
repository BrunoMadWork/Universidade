package ejb;


import java.util.ArrayList;

import javax.ejb.Remote;

import toDatabase.DbNews;
import data.UserData;

@Remote
public interface LoginBeanRemote {

	public  UserData RetriveLogin(String name, String pass);
	public  ArrayList<UserData> Allusers();
	public  ArrayList<UserData> Getselecteduser(long loginid);
	public  void Updateuser(long loginid, String name, String username, String email, String password);
	public  void Deleteuser(long loginid);
}