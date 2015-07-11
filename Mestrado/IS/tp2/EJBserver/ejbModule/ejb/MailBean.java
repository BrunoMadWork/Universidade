package ejb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import toDatabase.DbNews;
import toDatabase.DbRegion;

import java.util.logging.Level;
import java.util.logging.Logger;
@Stateless
@LocalBean
public class MailBean {
	@PersistenceContext(name="TestPersistence")
	EntityManager em;
	@Resource(name = "java:jboss/mail/gmail")
	private Session session;




	public MailBean() {
		// TODO Auto-generated constructor stub
	}
	//http://khozzy.blogspot.pt/2013/10/how-to-send-mails-from-jboss-wildfly.html
	@Schedule(dayOfWeek= "Sun,Mon,Tue,Wed,Thu,Fri,Sat", hour="18", minute="50")
	private void agendado() {
		ArrayList<String> mails= GetUsers();
		String receivers="";
		for(int i=0;i<mails.size();i++){
			if(!(mails.get(i).equals("null"))){
				System.out.println(mails.get(i));
				receivers=receivers+ " ,"+ mails.get(i);

			}

		}
		sendMail(receivers);

	}
	private void sendMail(String receiver){
		System.out.println("Ta a tentar mandar mail");
		Message message = new MimeMessage(session);
		try {
			String datepicker = "";
			Date dataatual = new Date();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			datepicker = dateFormat.format(dataatual);
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(receiver));
			message.setSubject("News Digest");
			ArrayList<DbNews> noticias=GetNoticias();
			String temp=noticias.size() + " news found on database - " + datepicker + " \n\n";
			System.out.println(noticias.size());
			if(noticias.size()!=0){
				for(int i=0;i<noticias.size();i++){
					if(!(noticias.get(i).getTitle() == null)){
						temp=temp+noticias.get(i).getTitle()+"\n";
					
					
					if(!(noticias.get(i).getAuthor() == null) || noticias.get(i).getAuthor().equals(" ")){
						temp=temp+noticias.get(i).getAuthor()+"\n\n";
					}
					else
						temp=temp+"<no author> \n";
					
					if(!(noticias.get(i).getHighlits() == null)){
						temp=temp+"--------------- Highlights --------------- \n";
						temp=temp+noticias.get(i).getHighlits()+"\n\n";
					}
					else
						temp=temp+"<no highlights> \n\n";
					
					if(!(noticias.get(i).getText() == null)){
						temp=temp+noticias.get(i).getText()+"\n";
					}
					else
						temp=temp+"<no text> \n";
					}


					temp=temp+"\n\n\n";
				}
			}else{
				temp="0 news found on database - " + datepicker + " \n\n";
			}
			message.setText(temp);
			Transport.send(message);
		} catch (MessagingException e) {
			Logger.getLogger(MailBean.class.getName()).log(Level.WARNING, "Cannot send mail", e);
		}
		System.out.println("Sent!");
	}
	private  ArrayList<DbNews> GetNoticias()
	{
		String datepicker = "";
		Date dataatual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		datepicker = dateFormat.format(dataatual);
	
		String[] parts = datepicker.split("/");
		String mes = parts[0];
		String dia = parts[1];
		String ano = parts[2];
		String mess = devolvemes(Integer.parseInt(mes));
		
		Query q = em.createQuery(" from DbNews where day=:d and month=:m and year=:y");
		q.setParameter("d", Integer.parseInt(dia));
    	q.setParameter("m", mess); 
    	q.setParameter("y", Integer.parseInt(ano)); 
		
		ArrayList<DbNews> news = null;
		news = (ArrayList<DbNews>) q.getResultList();
		System.out.println("It got back the news");
		return news;
	}
	private ArrayList<String> GetUsers(){

		ArrayList<String> arrays=null;
		Query q = em.createQuery("Select email from UserData");
		arrays=(ArrayList<String>) q.getResultList();
		return arrays;
	}
	
	public String devolvemes(int mes)
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
}
