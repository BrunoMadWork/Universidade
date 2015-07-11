package ejb;


import generated.News;
import toDatabase.DbNews;
import generated.Regions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.javafx.collections.MappingChange.Map;


/**
 * Session Bean implementation class GetNewsBean
 */
@Stateless
public class GetNewsBean implements GetNewsBeanRemote {
	@PersistenceContext(name="TestPersistence")
	EntityManager em;
	public GetNewsBean() {
        // TODO Auto-generated constructor stub
	}
	
    public ArrayList<Regions> Getregions(){
    	Query q = em.createQuery(" from Regions");
    	ArrayList<Regions> regions = null;
		regions= (ArrayList<Regions>) q.getResultList();
		System.out.println("It got back the news");
		return regions;
    }
    
    public  ArrayList<DbNews> Getnews()
    {
    	Query q = em.createQuery(" from DbNews");
    	ArrayList<DbNews> news = null;
		news = (ArrayList<DbNews>) q.getResultList();
		System.out.println("It got back the news");
    	return news;
    }
    
    public  ArrayList<DbNews> Getlatestnews(String region)
    {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	TreeMap<Date, Long> map = new TreeMap<Date, Long>();
    	Query q;
    	
    	if(region.equals("latest"))
    	{
    		q = em.createQuery(" from DbNews where year != 0");
    	}
    	else
    	{
    	    q = em.createQuery(" select p from DbRegion t JOIN t.news p where year != 0 and t.area = :r");
    		q.setParameter("r", region);
    	}
    	
    	ArrayList<DbNews> news = null;
    	ArrayList<DbNews> newsfinal = new ArrayList<DbNews>();
    	news = (ArrayList<DbNews>) q.getResultList();
		for(int i=0;i<news.size();i++)
		{
			int dia = news.get(i).getDay();
			int ano = news.get(i).getYear();
			int hora = news.get(i).getTime();
			String mes = news.get(i).getMonth();
			
			String datacorreta = arranjadata(dia, mes, ano, hora);
			
			try {
				Date dataconvertida = dateFormat.parse(datacorreta);
				map.put(dataconvertida,news.get(i).getChave());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		NavigableMap mapa =  map.descendingMap();
		
		for (Object key : mapa.keySet()) {
			System.out.println("Key : " + key.toString() + " Value : "
				+ map.get(key));

			for(int i=0;i<news.size();i++)
			{
				if(news.get(i).getChave() == map.get(key))
				{
					newsfinal.add(news.get(i));
				}
			}
		}
	  
    	return newsfinal;
    }
    
    public  ArrayList<String> Getauthors()
    {
    	ArrayList<String> array = new ArrayList<String>();
    	ArrayList<String> temp = new ArrayList<String>();
    	ArrayList<String> finall = new ArrayList<String>();
    	Query q = em.createQuery("select distinct author from DbNews order by author");
    	array = (ArrayList<String>) q.getResultList();
    	System.out.println(array.size());
    	for(String author : array)
    	{
    		if(author != null)
    		{
	    		String[] parts = author.split(",");
	    		String nome = parts[0];
	    		String[] parts2 = nome.split("By ");
	    		//String nome2 = parts2[2];
	    		//temp.add(nome);
	    		for(int i=0;i<parts2.length;i++)
	    		{
	    			if(i==1)
	    			{
	    				temp.add(parts2[i]);
	    			}
	    		}
	    		
    		}
    	}
    	
    	HashSet<String> set = new HashSet<>();

    	for(String aut : temp)
    	{
    		if (!set.contains(aut)) 
    		{
    			finall.add(aut);
    			set.add(aut);
    		}
    	}
    	
    	return finall;
    }
    
    public  ArrayList<DbNews> Getnewsbyauthor(String author)
    {
    	System.out.println("Function Getnewsbyauthor");
    	ArrayList<DbNews> array = null;
    	Query q = em.createQuery(" from DbNews where author LIKE ('%' || :a || '%')");
    	q.setParameter("a", author);
    	array = (ArrayList<DbNews>) q.getResultList();
    	System.out.println(array.size());
    	return array;
    }
    
    public  ArrayList<DbNews> Getselectednew(long chave)
    {
    	Query q = em.createQuery(" from DbNews where chave = :c");
    	q.setParameter("c", chave);
    	ArrayList<DbNews> news = null;
		news = (ArrayList<DbNews>) q.getResultList();
		for(int i=0;i<news.size();i++)
		{
			System.out.println(news.get(i).getText());
		}
		System.out.println("It got back the news");
    	return news;
    }
    
    public  ArrayList<DbNews> Getsearchnew(ArrayList<String> termos)
    {
    	ArrayList<String> query = new ArrayList<String>();
    	String um = "from DbNews where";
    	query.add(um);
    	int letra = 64;
    	for(int i=0;i<termos.size();i++) 
    	{
    		String para = "";
    		if(i == termos.size()-1)
    		{
    			letra++;
    			para = " highlits LIKE ('%' || " + ":" +(char)letra+" || '%')";
    		}
    		else
    		{
    			letra++;
    			para = " highlits LIKE ('%' || " + ":" +(char)letra+" || '%') AND";
    		}
    		query.add(para);
    	}
    	
    	String queryfinal = "";
    	
    	for(int j=0;j<=termos.size();j++) 
    	{
    		queryfinal += query.get(j);
    		
    	}
    	
    	System.out.println(queryfinal);
    	
    	Query q = em.createQuery(queryfinal);
    	int letra2 = 64;
    	for(int k=0;k<termos.size();k++) 
    	{
    		letra2++;
    		String param = Character.toString((char)letra2);
    		q.setParameter(param, termos.get(k));
    	}
    	
    	ArrayList<DbNews> news = null;
		news = (ArrayList<DbNews>) q.getResultList();
		System.out.println("Encontrou: "+news.size());
    	return news;
    }
    
    public  ArrayList<DbNews> Getadvancedsearch(String caixa, ArrayList<String> termos, String selected, String date, int dia, int mes, int ano)
    {
    	System.out.println("Advanced Search Function");
    	String month = devolvemes(mes);
    	ArrayList<DbNews> temp = new ArrayList<DbNews>();
    	ArrayList<DbNews> news = new ArrayList<DbNews>();
    	
    	if("author".equals(selected))
    	{
    		System.out.println("Autor");
    		String autor = "By " + caixa;
    		if("exact".equals(date))
    		{
    			String mess = devolvemes(mes);
    			System.out.println("Autor - data exacta");
    			Query q = em.createQuery(" from DbNews where author LIKE ('%' || :a || '%') and (day = :d and month = :m and year = :y)");
            	q.setParameter("a", autor);
            	q.setParameter("d", dia);
            	q.setParameter("m", mess);
            	q.setParameter("y", ano);
            	news = (ArrayList<DbNews>) q.getResultList();
    		}else if("older".equals(date))
    		{	
    			System.out.println("Autor - mais antiga que");
    			String partequery = olderthan(mes);
    			Query q = em.createQuery(" from DbNews where author LIKE ('%' || :a || '%') and (day <= :d and "+ partequery +" and year <= :y)");
            	q.setParameter("a", autor);
            	q.setParameter("d", dia);
            	q.setParameter("y", ano);
            	news = (ArrayList<DbNews>) q.getResultList();
    		}else if("morerec".equals(date))
    		{
    			System.out.println("Autor - mais recente que");
    			String partequery = morerecent(mes);
    			Query q = em.createQuery(" from DbNews where author LIKE ('%' || :a || '%') and (day >= :d and "+ partequery +" and year >= :y)");
            	q.setParameter("a", autor);
            	q.setParameter("d", dia);
            	q.setParameter("y", ano); 
            	news = (ArrayList<DbNews>) q.getResultList();
    		}
   		
    	} // FIM IF AUTHOR
    	else if("high".equals(selected))
    	{
    		System.out.println("Highlights");
    		ArrayList<DbNews> array = new ArrayList<DbNews>();
			array = Getsearchnew(termos);
			
			if("exact".equals(date))
    		{
				System.out.println("Highlights - exacta");
				for(DbNews noticia : array) 
				{
					String mess = devolvemes(mes);
	    			Query q = em.createQuery(" from DbNews where day = :d and month = :m and year = :y and chave = :c");
	            	q.setParameter("d", dia);
	            	q.setParameter("m", mess);
	            	q.setParameter("y", ano);
	            	q.setParameter("c", noticia.getChave());
	            	temp = (ArrayList<DbNews>) q.getResultList();
	            	
	            	if(temp.size() >= 1)
	            	{
	            		for(DbNews not : temp) 
	    				{
	            			news.add(not);
	    				}
	            	}
				}
    		}else if("older".equals(date))
    		{	
    			System.out.println("Highlights - mais antiga que");
    			for(DbNews noticia : array) 
				{
	    			String partequery = olderthan(mes);
	    			Query q = em.createQuery(" from DbNews where day <= :d and "+ partequery +" and year <= :y and chave = :c");
	    			q.setParameter("d", dia);
	            	q.setParameter("y", ano);
	            	q.setParameter("c", noticia.getChave());
	            	
	            	temp = (ArrayList<DbNews>) q.getResultList();
	            	
	            	if(temp.size() >= 1)
	            	{
	            		for(int i=0;i<temp.size();i++)
	            		{
	            			news.add(temp.get(i));
	            			//System.out.println("aqui também");
	            		}
	            	}
				}
    		}else
    		{
    			System.out.println("Highlights - mais recente que");
    			for(DbNews noticia : array) 
				{
	    			String partequery = morerecent(mes);
	    			Query q = em.createQuery(" from DbNews where day >= :d and "+ partequery +" and year >= :y and chave = :c");
	    			q.setParameter("d", dia);
	            	q.setParameter("y", ano);
	            	q.setParameter("c", noticia.getChave());
	            	
	            	temp = (ArrayList<DbNews>) q.getResultList();
	            	
	            	if(temp.size() >= 1)
	            	{
	            		for(DbNews not : temp) 
	    				{
	            			news.add(not);
	    				}
	            	}
				}
    		}
    	} //FIM IF HIGHLIGHTS
    	else
    	{
    		if("exact".equals(date))
    		{
    			String mess = devolvemes(mes);
    			Query q = em.createQuery(" from DbNews day = :d and month = :m and year = :y");
            	q.setParameter("d", dia);
            	q.setParameter("m", mess);
            	q.setParameter("y", ano);
            	news = (ArrayList<DbNews>) q.getResultList();
    		}else if("older".equals(date))
    		{	
    			String partequery = olderthan(mes);
    			Query q = em.createQuery(" from DbNews where day <= :d and "+ partequery +" and year <= :y");
            	q.setParameter("d", dia);
            	q.setParameter("y", ano);
            	news = (ArrayList<DbNews>) q.getResultList();
    		}else
    		{
    			String partequery = morerecent(mes);
    			Query q = em.createQuery(" from DbNews where day >= :d and "+ partequery +" and year >= :y");
            	q.setParameter("d", dia);
            	q.setParameter("y", ano); 
            	news = (ArrayList<DbNews>) q.getResultList();
    		}
    	} // FIM IF ANY NEWS
    	System.out.println("Tamanho array: "+news.size());
    	return news;
    }
    
    public static String morerecent(int mes)
    {
    	String query = "";
        switch (mes) {
            case 1:  query = "month = 'blablabla'";
                     break;
            case 2:  query = "month != 'January'";
                     break;
            case 3:  query = "month != 'January' and month != 'February'";
                     break;
            case 4:  query = "month != 'January' and month != 'February' and month != 'March'";
                     break;
            case 5:  query = "month != 'January' and month != 'February' and month != 'March' and month != 'April'";
                     break;
            case 6:  query = "month != 'January' and month != 'February' and month != 'March' and month != 'April' and month != 'May'";
                     break;
            case 7:  query = "month != 'January' and month != 'February' and month != 'March' and month != 'April' and month != 'May' and month != 'June'";
                     break;
            case 8:  query = "month = 'August' or month = 'September' or month = 'October' or month = 'November' or month = 'December'";
                     break;
            case 9:  query = "month = 'September' or month = 'October' or month = 'November' or month = 'December'";
                     break;
            case 10: query = "month = 'October' or month = 'November' or month = 'December'";
                     break;
            case 11: query = "month = 'November' or month = 'December'";
                     break;
            case 12: query = "month = 'December'";
                     break;
        }
        return query;
    }
    
    public static String olderthan(int mes)
    {
    	String query = "";
        switch (mes) {
            case 1:  query = "month = 'January'";
                     break;
            case 2:  query = "month = 'January' or month = 'February'";
                     break;
            case 3:  query = "month = 'January' or month = 'February' or month = 'March'";
                     break;
            case 4:  query = "month = 'January' or month = 'February' or month = 'March' or month = 'April'";
                     break;
            case 5:  query = "month = 'January' or month = 'February' or month = 'March' or month = 'April' or month = 'May'";
                     break;
            case 6:  query = "month = 'January' or month = 'February' or month = 'March' or month = 'April' or month = 'May' or month = 'June'";
                     break;
            case 7:  query = "month != 'August' and month != 'September' and month != 'October' and month != 'November' and month != 'December'";
                     break;
            case 8:  query = "month != 'September' and month != 'October' and month != 'November' and month != 'December'";
                     break;
            case 9:  query = "month != 'October' and month != 'November' and month != 'December'";
                     break;
            case 10: query = "month != 'November' and month != 'December'";
                     break;
            case 11: query = "month != 'December'";
                     break;
            case 12: query = "month != 'blablabla'";
                     break;
        }
        return query;
    }
    
    public static String devolvemes(int mes)
    {
    	String month = "";
        switch (mes) {
            case 1:  month = "January";
                     break;
            case 2:  month = "February";
                     break;
            case 3:  month = "March";
                     break;
            case 4:  month = "April";
                     break;
            case 5:  month = "May";
                     break;
            case 6:  month = "June";
                     break;
            case 7:  month = "July";
                     break;
            case 8:  month = "August";
                     break;
            case 9:  month = "September";
                     break;
            case 10: month = "October";
                     break;
            case 11: month = "November";
                     break;
            case 12: month = "December";
                     break;
        }
        return month;
    }
    
    public static String arranjadata(int dia, String mes, int ano, int hora)
	{
		int num = 0;
		String horanova = "";
		String modificada = "";
		switch(mes)
		{
		case "January": num = 1;
						break;
		case "February": num = 2;
						break;
		case "March": num = 3;
						break;
		case "April": num = 4;
						break;
		case "May": num = 5;
						break;
		case "June": num = 6;
						break;
		case "July": num = 7;
						break;
		case "August": num = 8;
						break;
		case "September": num = 9;
						break;
		case "October": num = 10;
						break;
		case "November": num = 11;
						break;
		case "December": num = 12;
						break;
		}
		
		int length = String.valueOf(hora).length();
		if (length == 1)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			horanova = "" + "00:0" + primeiro;
		}
		else if (length == 2)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
			horanova = "" + "00:" + primeiro + segundo ;
		}
		else if(length == 3)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
			int terceiro = Integer.parseInt(Integer.toString(hora).substring(2, 3));
			
			horanova = "" + primeiro + ":" + segundo + terceiro;

		}
		else if (length == 4)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
			int terceiro = Integer.parseInt(Integer.toString(hora).substring(2, 3));
			int quarto = Integer.parseInt(Integer.toString(hora).substring(3, 4));
			
			horanova = "" + primeiro + segundo + ":" + terceiro + quarto;

		}

		modificada = dia + "/" + num + "/" + ano + " " + horanova;

		return modificada;
	}

}
