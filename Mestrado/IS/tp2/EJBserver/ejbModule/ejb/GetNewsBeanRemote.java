package ejb;

import java.util.ArrayList;

import javax.ejb.Remote;

import generated.News;
import toDatabase.DbNews;

@Remote
public interface GetNewsBeanRemote {
	public  ArrayList<DbNews> Getnews();
	public  ArrayList<DbNews> Getlatestnews(String region);
	public  ArrayList<DbNews> Getselectednew(long chave);
	public  ArrayList<DbNews> Getsearchnew(ArrayList<String> termos);
	public  ArrayList<DbNews> Getadvancedsearch(String caixa, ArrayList<String> termos, String selected, String date, int dia, int mes, int ano);
	public  ArrayList<String> Getauthors();
	public  ArrayList<DbNews> Getnewsbyauthor(String author);
}
